/* eslint-disable no-throw-literal */
const URL = "http://161.35.221.47/bglogger";



class ApiFacade {
    //Insert utility-methods from a latter step (d) here
    makeOptions(method, body) {
        var opts = {
            method: method,
            headers: {
                "Content-type": "application/json",
                'Accept': 'application/json',
            }
        }   
        if (body) {
            opts.body = JSON.stringify(body);
        }
        return opts;
    }

    startAnalyzation = async (playername, data) => {
        const options = this.makeOptions("POST",  { initiator: playername, data: data});
        const res = await fetch(URL+"/api/analyze/postlog", options)
        const json = await res.json();
        if (!res.ok) {
            throw { status: res.status, fullError: json }
        }
        return json;
    }

    stopAnalyzation = async () => {
        const options = this.makeOptions("POST", true);
        const res = await fetch("/api/backtest/stop", options)
        const json = await res.json();
        if (!res.ok) {
            throw { status: res.status, fullError: json }
        }
        return json;
    }
}

const facade = new ApiFacade();

export default facade;
