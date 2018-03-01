module nts.uk.com.view.cmf001.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getAllData: "at/function/alarm/checkcondition/findAll/{0}",
        getOneData: "at/function/alarm/checkcondition/findOne/{0}/{1}",
        registerData: "at/function/alarm/checkcondition/register",
        deleteData: "at/function/alarm/checkcondition/delete"
    }

    export function getAllData(category: number): JQueryPromise<any> {
        let _path = format(paths.getAllData, category);
        return ajax("at", _path);
    };
    
    export function getOneData(category: number, code: string): JQueryPromise<any> {
        let _path = format(paths.getOneData, category, code);
        return ajax("at", _path);
    };

    export function registerData(data: any): JQueryPromise<any> {
        return ajax("at", paths.registerData, data);
    };

    export function deleteData(data: any): JQueryPromise<any> {
        return ajax("at", paths.deleteData, data);
    }
    
}