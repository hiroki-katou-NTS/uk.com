module nts.uk.at.view.ktg001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths: any = {
        // checkDisplay/{ym}/{closureId}
        getData: "screen/at/ktg001/checkDisplay"
    }
    export function getData(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getData, param);
    }
}  