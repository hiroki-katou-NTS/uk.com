module nts.uk.at.view.kmw006.f.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getAllData: "at/function/alarm/checkcondition/findAll/{0}",
        getBusTypeByCodes: "at/record/worktypeselection/getNamesByCodes"
    }

    export function getAllData(category: number): JQueryPromise<any> {
        let _path = format(paths.getAllData, category);
        return ajax("at", _path);
    };
    
    export function getBusTypeNamesByCodes(data: Array<string>): JQueryPromise<any> {
        return ajax("at", paths.getBusTypeByCodes, data);
    }
    
}