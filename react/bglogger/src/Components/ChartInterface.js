import React, { Component } from 'react';
import { Chart, Line} from 'react-chartjs-2';



class ChartInterface extends Component {
    constructor(props) {
        super(props);
        this.state = {
            division: this.props.division,
            data: {
                labels: this.props.Y,
                datasets: [{
                    label: this.props.chartname,
                    data: this.props.X
                }],
            }
        }

    }
    componentDidMount() {
        /* this.mounted = true
        if (this.mounted){
            Chart.pluginService.register({
                afterDraw: function (chart, easing) {
                    if (chart.tooltip._active && chart.tooltip._active.length) {
                        const activePoint = chart.controller.tooltip._active[0];
                        const ctx = chart.ctx;
                        const x = activePoint.tooltipPosition().x;
                        const y = activePoint.tooltipPosition().y;
                        const bottomY = chart.scales['y-axis-0'].bottom;
                        ctx.save();
                        ctx.beginPath();
                        ctx.moveTo(x, y);
                        ctx.lineTo(x, bottomY);
                        ctx.lineWidth = 0.5;
                        ctx.strokeStyle = '#ACADBC';
                        ctx.stroke();
                        ctx.save();
    
    
                    }
                }
            });
            this.intervalID = setInterval(
                () => this.update(),
                60000
            );
         */
        
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


    
    convertTimestampToDatetime(timestamp) {
        let time = parseInt(timestamp)
        var d = new Date(time);
        var h = this.addZero(d.getUTCHours());
        var m = this.addZero(d.getUTCMinutes());
        return d.getUTCDate() + '/' + (d.getUTCMonth() + 1) + '/' + d.getUTCFullYear() + "-" + h + ":" + m + ' UTC';
    }
    addZero(i) {
        if (i < 10) {
            i = "0" + i;
        }
        return i;
    }
    
    setChartData() {
        let labels = this.state.label1
        let data
        labels = labels.sort()
        if (this.state.showOrders) {
            data = {
                labels: labels.map(item => this.convertTimestampToDatetime(parseInt(item))),
                datasets: [{
                    type: 'bubble',
                    label: 'BUYS',
                    backgroundColor: 'green',
                    data: this.state.buys
                },
                {
                    type: 'bubble',
                    label: 'SELLS',
                    backgroundColor: 'red',
                    data: this.state.sells

                },
                {
                    label: this.props.cryptoName,
                    fill: 'true',
                    pointRadius: 0,
                    borderColor: '#473BF0',
                    borderWidth: 1,
                    pointBackgroundColor: 'white',
                    data: this.state.JSONdata.map(item => parseFloat(item[2]).toFixed(2))

                }
                ]
            }

        } else {
            data = {
                labels: labels.map(item => this.convertTimestampToDatetime(parseInt(item))),
                datasets: [
                    {
                        label: this.props.cryptoName,
                        fill: 'true',
                        pointRadius: 0,
                        borderColor: '#473BF0',
                        borderWidth: 1,
                        pointBackgroundColor: 'white',
                        data: this.state.JSONdata.map(item => parseFloat(item[2]).toFixed(2))

                    }
                ]
            }

        }
        this.setState({
            data: data,
            loading: false
        })
    }

    changeInterval(evt) {
        this.setState({
            interval: evt.target.value
        })
        this.getChartData()
    }

    render() {
        const windowWidth = window.innerWidth-20
        const width = parseInt(windowWidth/this.state.division)
        return (
            <div className="chart-container">

 
                            <Line

                                height={150}
                                width={width}
                                options={{
                                }}
                                data={this.state.data} />
                        
                </div>
            

        )
    }
}

export default ChartInterface;
