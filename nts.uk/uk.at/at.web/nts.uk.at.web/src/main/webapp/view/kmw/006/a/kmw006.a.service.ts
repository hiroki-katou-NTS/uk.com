module nts.uk.at.view.kmw006.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        checkStatus: "at/record/monthlyclosure/checkStatus/{0}",
        getInfors: "at/record/monthlyclosure/getInfors"
    }

    export function checkStatus(closureId: number): JQueryPromise<any> {
        let _path = format(paths.checkStatus, closureId);
        return ajax("at", _path);
    };
        
    export function getInfors(): JQueryPromise<any> {
        return ajax("at", paths.getInfors);
    }
    
}