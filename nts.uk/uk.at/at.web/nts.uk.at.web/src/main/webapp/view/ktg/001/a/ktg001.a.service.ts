module nts.uk.at.view.ktg001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths: any = {
        getData: "screen/at/ktg001/checkDisplay/{0}"
    }
    export function getData(currentOrNextMonth: any): JQueryPromise<any> {
        return ajax("at", format(paths.getData, currentOrNextMonth));
    }
}  