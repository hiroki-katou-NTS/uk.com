module nts.uk.at.view.kal003.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getAllData: "at/function/alarm/checkcondition/findAll/{0}",
        registerData: "at/function/alarm/checkcondition/register",
        deleteData: "at/function/alarm/checkcondition/delete",
        getAllFixedConData : "at/record/erroralarm/fixeddata/getallfixedcondata",
        getClsNameByCodes: "bs/employee/classification/getClsNameByCds",
        getEmpNameByCodes: "bs/employee/employment/findByCodes",
        getJobNameByCodes: "bs/employee/jobtitle/info/",
        getBusTypeByCodes: ""
    }

    export function getAllData(category: number): JQueryPromise<any> {
        let _path = format(paths.getAllData, category);
        return ajax("at", _path);
    };

    export function registerData(data: any): JQueryPromise<any> {
        return ajax("at", paths.registerData, data);
    };

    export function deleteData(data: any): JQueryPromise<any> {
        return ajax("at", paths.deleteData, data);
    }
    
    /**
     * get All Fixed Condition WorkRecord data 
     */
    export function getAllFixedConData() : JQueryPromise<Array<any>>{
        return nts.uk.request.ajax("at",paths.getAllFixedConData);
    }
    
    export function getClsNameByCodes(data: Array<string>): JQueryPromise<any> {
        return ajax("com", paths.getClsNameByCodes, {listClsCodes: data});
    } 
    
    export function getEmpNameByCodes(data: Array<string>): JQueryPromise<any> {
        return ajax("com", paths.getEmpNameByCodes, data);
    } 
}