import React, { Component } from 'react';
import { Chart, Line } from 'react-chartjs-2';



class TimelineChart extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: {
                labels: this.props.X,
                datasets: [{
                    label: "Timeline",
                    data: this.props.Y,
                    spanGaps: true,
                    fill: 'origin',
                    lineTension: 0,
                    borderWidth: 0,
                }],
            }
        }
        //this.createTimeline()
    }

    createTimeline(){
        let X = []
        let Y = []
        let xObj = this.props.data.X
        let yObj = this.props.data.Y
        for(var i = 0; i < this.getObjSize(xObj); i++){
            X.push(parseInt(xObj[i]))
            Y.push(parseInt(yObj[i]))
        }
        console.log(typeof(X), Y)
        this.setState({
            data: {
                labels: Y,
                datasets: [{
                    label: "Timeline",
                    data: X
                }],
            }
        })

    }
    componentDidMount() {
        console.log(this.state.timeData)

    }
    getObjSize(obj) {
        var size = 0, key;
        for (key in obj) {
            if (obj.hasOwnProperty(key)) size++;
        }
        return size;
    }
    /* createTimeline() {
        let end = this.state.endOfCombat[this.getObjSize(this.state.endOfCombat) - 1]
        this.setState({
            data: {
                labels: this.createTimeArray(end),
                datasets: [{
                    label: "Timeline",
                    data: this.createTimeData(end)
                }],
            }
        })
    } */

    createTimeData(end) {
        let ooc = this.state.endOfCombat
        let ic = this.state.startOfCombat
        let arr = []
        var inCombat = false
        for (var i = 0; i < end; i++) {
            for (var j = 0; j < this.getObjSize(this.state.endOfCombat); i++) {
                if (!inCombat) {
                    if (i >= this.state.startOfCombat[j] && i < this.state.endOfCombat[j]) {
                        inCombat = true
                    }
                    else inCombat = false
                }
            }
            if (inCombat) {
                // eslint-disable-next-line no-unused-expressions
                arr.push[1]
            }
            // eslint-disable-next-line no-unused-expressions
            else arr.push[0]
        }
        console.log(arr.length, arr)
        return arr
    }

    createTimeArray(limit) {
        let arr = []
        for (var i = 0; i < limit; i++) {
            arr.push(i)
        }
        return arr
    }

    componentWillReceiveProps(nextProps) {
        if (this.state.ooc != nextProps.ooc) {
            this.setState({
                ooc: nextProps.ooc
            })
        }
        if (this.state.ic != nextProps.ic) {
            this.setState({
                ic: nextProps.ic
            })
        }
        if (this.state.timeData != nextProps.data) {
            this.setState({
                timeData: nextProps.data
            })
        }
    }

    componentWillUnmount() {
        this.mounted = false;
    }


    setInitialStartDate() {
        let ts = new Date().getTime()
        let start = new Date(ts)
        start = start.setMonth(start.getMonth() - 1)
        return start
    }

    componentDidUpdate(prevProps) {

    }

    render() {
        return (
            <div className="chart-container">


                <Line

                    height={80}
                    width={window.innerWidth - 20}
                    options={{
                        elements: {
                            point:{
                                radius: 0
                            }
                        },
                        scales: {
                            yAxes: [{
                                ticks: {
                                    beginAtZero: true,
                                    display: false,
                                },
                                gridLines: {
                                    color: "rgba(0, 0, 0, 0)",
                                }   
                            }],
                            xAxes: [{
                                ticks: {
                                    display: true
                                }
                            }]

                        }
                    }}
                    data={this.state.data} />

            </div>


        )
    }
}

export default TimelineChart;
