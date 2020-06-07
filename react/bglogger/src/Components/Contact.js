import React from 'react';
import cettia from 'cettia-client';
import ChartInterface from './ChartInterface';
import TimelineChart from './TimelineChart';


class ROW extends React.Component {
    constructor(props) {
        super(props)
    }
    render() {
        return (
            <tr>
                <td>{this.props.obj.msg}</td>
                <td>{this.props.obj.api}</td>
            </tr>
        )
    }
}

class Contact extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            finish: this.props.finish,
            stream: this.props.stream,
            initiator: this.props.initiator
        }


    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.initiator != this.state.initiator) {
            this.setState({
                initiator: nextProps.initiator
            })
        }
        if (nextProps.stream != this.state.stream) {
            this.setState({
                stream: nextProps.stream
            })
        }
    }

    addMessage = ({ sender, text }) => console.log(`${sender} sends ${text}`);

    addRow = ({ sender, text }) => {
        var obj = { sender: sender, text: JSON.parse(text) }
        const { stream } = this.state;
        console.log(obj.text)
        var list = this.state.stream;
        list.unshift(obj);
        this.setState({ stream: list })
        console.log(this.state.stream)
        this.props.finish()
    }

    componentDidMount() {
        let uri = `http://localhost:8080/jpareststarter/cettia?token=log`;
        //let uri = 'http://161.35.221.47/bglogger/cettia?token=log';

        this.socket = cettia.open(uri);
        const addSystemMessage = text => this.addMessage({ sender: "system", text });
        const addListMessage = message => this.addRow(message)
        this.socket.on("message", message => addListMessage(message));
        this.socket.on("connecting", () => addSystemMessage("The socket starts a connection."));
        this.socket.on("open", () => addSystemMessage("The socket establishes a connection."));
        this.socket.on("close", () => addSystemMessage("All transports failed to connect or the connection was disconnected."));
        this.socket.on("waiting", (delay) => addSystemMessage(`The socket will reconnect after ${delay} ms`));
        this.socket.once("open", () => setInterval(() => this.socket.send("message", { text: `A message - ${Date.now()}` }), 5000));
    }

    createActionAnalysis(item) {
        let data = item.data
        let plots = item.plots
        console.log(plots)
        if (this.getObjSize(plots) > 0) {
            return (<div>
                <tr>
                    {plots.map(plot => <td>{this.createPlot(plot, 3)}</td>)}
                </tr>
            </div>)
        }
    }

    createDamageAnalysis(item) {
        let data = item.data
        let plots = item.plots
        if (this.getObjSize(plots) > 0) {
            return (<div>
                <tr>
                    {plots.map(plot => <td>{this.createPlot(plot, 4)}</td>)}
                </tr>
            </div>)
        }
    }
    createDPSAnalysis(item) {
        let data = item.data
        let plots = item.plots
        if (this.getObjSize(plots) > 0) {
            return (<div>
                <tr>
                    {plots.map(plot => <td>{this.createPlot(plot, 2)}</td>)}
                </tr>
            </div>)
        }
    }
    createDPSAnalysisTable(item) {
        let data = item.data
        let names = item.Name.toString().split(',')
        return (
            <td><tr><td>{names[0]}</td><td>{data[0]}</td></tr>
                <tr><td>{names[1]}</td><td>{data[1]}</td></tr>
                <tr><td>{names[2]}</td><td>{data[2]}</td></tr></td>
        )
    }


    getObjSize(obj) {
        var size = 0, key;
        for (key in obj) {
            if (obj.hasOwnProperty(key)) size++;
        }
        return size;
    }

    createDataline(column) {
        column.map()

    }

    createPlot(plot, division) {
        return <ChartInterface chartname={plot.Name} X={plot.Y} Y={plot.X} division={division} />
    }

    createTimeline(data) {
        return <TimelineChart data={data} />
    }

    render() {
        return (
            <div>
                <table className="tg">
                    <thead>
                        <th>{this.state.initiator}</th>
                    </thead>
                    <tbody>
                        {this.state.stream.map(item => {
                           if (item.sender !== undefined && item.sender.toString().includes("Analysis.CombatTimeAnalysis")) {
                                console.log(item.text)
                                /* return (
                                    <div>
                                        <tr><th>Combat Time Analysis</th></tr>
                                        <tr><td>{this.createTimeline(item.text.data)}</td></tr>
                                    </div>
                                    //<tr><td>{item.data[0].Name}</td></tr>
                                ) */
                            }
                        })}
                        {this.state.stream.map(item => {
                            if (item.sender !== undefined && item.sender.toString().includes("Analysis.ActionAnalysis")) {
                                console.log(item.text)
                                return (
                                    <div>
                                        <tr><th>Action Analysis</th></tr>
                                        <tr><td>{this.createActionAnalysis(item.text)}</td></tr>
                                    </div>
                                    //<tr><td>{item.data[0].Name}</td></tr>
                                )
                            }
                        })}
                        {this.state.stream.map(item => {
                            if (item.sender !== undefined && item.sender.toString().includes("Analysis.DamageAnalysis")) {
                                console.log(item.text)
                                return (
                                    <div>
                                        <tr><th>Damage Analysis</th></tr>
                                        <tr><td>{this.createDamageAnalysis(item.text)}</td></tr>
                                    </div>
                                    //<tr><td>{item.data[0].Name}</td></tr>
                                )
                            }
                        })}
                        {this.state.stream.map(item => {
                            if (item.sender !== undefined && item.sender.toString().includes("Analysis.CombatDpsAnalysis")) {
                                console.log(item.text.data)
                                return (
                                    <div>
                                        <tr><th>DPS Analysis</th><th></th></tr>
                                        <tr><td>{this.createDPSAnalysis(item.text)}</td><td>{this.createDPSAnalysisTable(item.text.data[0])}</td></tr>
                                    </div>
                                    //<tr><td>{item.data[0].Name}</td></tr>
                                )
                            }
                        })}




                    </tbody>

                </table>
            </div>
        );
    }
}

export default Contact;