import React from 'react';
import cettia from 'cettia-client';


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
            stream: [{
                "msg": "velkommen",
                "api": "Ingen"
            }
            ]
        }
        
        
    }

    addMessage = ({ sender, text }) => console.log(`${sender} sends ${text}`);

    addRow = ({sender,text}) =>{
        console.log("woo")
        var obj = {
            "msg": text,
            "api": sender
        }
        console.log("woo")
        const { stream } = this.state;
		var list = this.state.stream;
		list.unshift(obj);

		this.setState({ stream: list})

    }

    componentDidMount(){
        let uri = `http://localhost:8080/jpareststarter/cettia?token=log`;
        this.socket = cettia.open(uri);
        const addSystemMessage = text => this.addMessage({ sender: "system", text });
        const addListMessage = message => this.addRow(message)
        this.socket.on("message", message => addListMessage(message));
        this.socket.on("connecting", () => addSystemMessage("The socket starts a connection."));
        this.socket.on("open", () => addSystemMessage("The socket establishes a connection."));
        this.socket.on("close", () => addSystemMessage("All transports failed to connect or the connection was disconnected."));
        this.socket.on("waiting", (delay) => addSystemMessage(`The socket will reconnect after ${delay} ms`));
        this.socket.once("open", () => setInterval(() => this.socket.send("message", {text: `A message - ${Date.now()}`}), 5000));
    }

    render() {
        return (
            <div>
                <h1>
                    Dette er en liste der hele tiden modtager hvad det seneste kald til API'en er.
                </h1>
                <table className="tg">
                    <thead>
                        <th>Besked</th>
                        <th>API kaldt</th>
                    </thead>
                    {
                        this.state.stream.map(res => {
                            return (
                                <ROW obj={res} />
                            )
                        }

                        )
                    }
                </table>
            </div>
        );
    }
}

export default Contact;