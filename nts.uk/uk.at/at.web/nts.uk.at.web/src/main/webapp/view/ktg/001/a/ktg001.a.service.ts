module nts.uk.at.view.ktg001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths: any = {
        // checkDisplay/{ym}/{closureId}
        getData: "screen/at/ktg001/checkDisplay/{0}/{1}"
    }
    export function getData(ym: any, closureId: any): JQueryPromise<any> {
        return ajax("at", format(paths.getData, ym, closureId));
    }
}  