module nts.uk.at.view.ktg030.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths: any = {
        // checkDisplay/{ym}/{closureId}
        getData: "screen/at/ktg030/checkDisplay"
    }
    export function getData(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("at", paths.getData, param);
    }
}  