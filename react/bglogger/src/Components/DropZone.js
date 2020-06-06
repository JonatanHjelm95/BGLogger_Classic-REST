import React, { Component } from 'react'
import Dropzone from 'react-dropzone'
import facade from '../apiFacade'
import Stream from './Stream'


class DropZone extends Component {
    constructor(props) {
        super(props);
        this.state = {
            validFile: false,
            processed: false,
            loading: false,
            analyzeInitiated: false,
            encodedData: '',
            combatLog: [],
            units: [],
            selectedUnit: '',
            selectedFile: '',
            formData: null,
        }
        this.analyze = this.analyze.bind(this)
    }

    validateFile(file) {
        const filename = file[0].path
        console.log(file[0].path)
        if (filename === 'WoWCombatLog.txt') {
            const formData = new FormData()
            this.setState({
                processed: false,
                validFile: filename === 'WoWCombatLog.txt',
                selectedFile: file[0]
            })
            formData.append('file', this.state.selectedFile)
            this.setState({
                formData: formData
            })
            console.log(this.state.formData)
        }
        else {
            console.log('invalid file')
        }
        this.processFile(file[0])
    }

    async processFile(file) {
        if (this.state.validFile) {

            const reader = new FileReader()
            reader.onload = async () => {
                const binaryStr = reader.result
                //const encodedData = base64.encode(binaryStr)
                let combatLog = binaryStr.split('\n')
                var unitNames = this.getAllUnitsFromFile(binaryStr)
                this.setState({
                    //encodedData: encodedData,
                    combatLog: combatLog,
                    units: unitNames,
                    processed: true
                })
                console.log(this.state.combatLog)
                this.createUnitSelectItems()
            }
            reader.readAsText(file)
        }
    }

    getAllUnitsFromFile(file) {

        var units = []
        var fileSplit = file.split('\n')
        for (var i = 1; i < fileSplit.length; i++) {
            var lineSplit = fileSplit[i].split(',')
            for (var j = 0; j < lineSplit.length; j++) {
                if (lineSplit[j].includes('Player')) {
                    if (lineSplit[j + 1])
                        units.push(lineSplit[j + 1])
                }
            }

        }
        return this.filter_list(this.removeDups(units))
    }

    filter_list(l) {
        return l.filter(x => x.includes('-'));
    }

    createUnitSelectItems() {
        let units = this.state.units.map(item => {
            return (<option key={item.replace('\"', '')} value={item.replace('\"', '')}>{item.replace('\"', '').replace('\"', '')}</option>)
        })
        this.setState({
            units: units
        })
    }

    removeDups(names) {
        let unique = {};
        names.forEach(function (i) {
            if (!unique[i]) {
                unique[i] = true;
            }
        });
        return Object.keys(unique);
    }

    async analyze(player, data) {
        const formData = data
        formData.append('initiator', player)
        await facade.uploadLog(formData)
        // await facade.startAnalyzation(player, data)
        this.setState({
            analyzeInitiated: true
        })
        
        //console.log(player)
        //console.log(JSON.stringify(data))
    }

    changeUnit(evt) {
        evt.persist()
        this.setState({
            selectedUnit: evt.target.value.replace('"', '')
        })
    }

    render() {
        return (
            <div>
                <Dropzone onDrop={file => this.validateFile(file)}>
                    {({ getRootProps, getInputProps }) => (
                        <section>
                            <div {...getRootProps()}>
                                <input {...getInputProps()} />
                                <p>Drop your CombatLog.txt here, or click to browse files</p>
                            </div>
                        </section>
                    )}
                </Dropzone>
                {this.state.processed ? (
                    <div>
                        <div className="unit-container">
                            <select className="unit-select" onChange={(evt) => this.changeUnit(evt)}>
                                <option value="" disabled selected>Select player</option>
                                {this.state.units}
                            </select>
                        </div>
                        {this.state.selectedUnit.length > 0 ? (<button onClick={(evt) => this.analyze(this.state.selectedUnit, this.state.formData)}>Analyze</button>) : (<div></div>)}
                        {this.state.analyzeInitiated ? (<Stream initiator={this.state.selectedUnit} />) : (<div></div>)}
                    </div>
                ) : (<div></div>)}



            </div>

        )
    }



}



export default DropZone;
