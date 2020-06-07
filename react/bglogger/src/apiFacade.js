/* eslint-disable no-throw-literal */
const URL = "http://localhost:8080/jpareststarter";



class ApiFacade {
    //Insert utility-methods from a latter step (d) here
    makeOptions(method, body) {
        var opts = {
            method: method,
            headers: {
                "Content-type": "multipart/form-data"
            }
        }   
        if (body) {
            opts.body = body;
        }
        return opts;
    }

    startAnalyzation = async (playername, data) => {
        var formData = new FormData()
        formData.append("data",data)
        const options = this.makeOptions("POST",  formData);
        const res = await fetch("api/analyze/upload/"+playername, options)
        const json = await res.json();
        if (!res.ok) {
            throw { status: res.status, fullError: json }
        }
        return json;
    }
}

const facade = new ApiFacade();

export default facade;
