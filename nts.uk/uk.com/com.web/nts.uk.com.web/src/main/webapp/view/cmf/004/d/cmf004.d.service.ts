module nts.uk.com.view.cmf004.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        extractData: "ctx/sys/assist/datarestoration/extractData"
    }
    export function extractData(fileInfo): JQueryPromise<any> {
        return ajax(paths.extractData, fileInfo);
    }
}