module nts.uk.com.view.cmf001.q.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        addDataErrorLog : "ctx/exio/ws/exi/proccessLog/addErrorLog"
    }
    export function addDataErrorLog(data: any): JQueryPromise<any> {
        return ajax("com", paths.addDataErrorLog, data);
    };
}