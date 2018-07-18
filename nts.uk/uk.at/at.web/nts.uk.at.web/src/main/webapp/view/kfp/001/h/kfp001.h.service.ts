module nts.uk.com.view.kfp001.h.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    var paths = {
        getLogData: "ctx/at/record/optionalaggr/findbyperiod/{0}/{1}"
    }

    export function getLogData(start: string, end: string): JQueryPromise<any> {
        let _path = format(paths.getLogData, start, end);
        return ajax("at", _path);
    };
    
}