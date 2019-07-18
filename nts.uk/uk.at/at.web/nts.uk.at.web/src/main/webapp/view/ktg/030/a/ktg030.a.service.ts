module nts.uk.at.view.ktg030.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths: any = {
        // checkDisplay/{ym}/{closureId}
        getData: "screen/at/ktg030/checkDisplay/{0}/{1}"
    }
    export function getData(currentOrNextMonth: any, closureId: any): JQueryPromise<any> {
        return ajax("at", format(paths.getData, currentOrNextMonth, closureId));
    }
}  