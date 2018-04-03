module nts.uk.at.view.ktg001.a.service {
    import ajax = nts.uk.request.ajax;
    
    var paths: any = {
        getData: "screen/at/ktg001/checkDisplay"
    }
    export function getData(): JQueryPromise<any> {
        return ajax("at", paths.getData);
    }
}  