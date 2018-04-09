module nts.uk.at.view.kmw006.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        checkStatus: "at/record/monthlyclosure/checkStatus/{0}",
        getBusTypeByCodes: "at/record/worktypeselection/getNamesByCodes"
    }

    export function checkStatus(closureId: number): JQueryPromise<any> {
        let _path = format(paths.checkStatus, closureId);
        return ajax("at", _path);
    };
        
    export function getBusTypeNamesByCodes(data: Array<string>): JQueryPromise<any> {
        return ajax("at", paths.getBusTypeByCodes, data);
    }
    
}