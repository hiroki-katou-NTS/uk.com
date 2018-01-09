module nts.uk.at.view.kal003.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getAllData: "at/function/alarm/checkcondition/findAll/{0}",
        registerData: "at/function/alarm/checkcondition/register",
        deleteData: "at/function/alarm/checkcondition/delete"
    }

    export function getAllData(category: number): JQueryPromise<any> {
        let _path = format(paths.getAllData, category);
        return nts.uk.request.ajax("at", _path);
    };

    export function registerData(data: any): JQueryPromise<any> {
        return ajax("at", paths.registerData, data);
    };

    export function deleteData(data: any): JQueryPromise<any> {
        return ajax("at", paths.deleteData, data);
    }

}