module nts.uk.com.view.cmf.r.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getLogResults: "ctx/pereg/person/info/setting/selection/getLogResults",
        getErrorLogs: "ctx/pereg/person/info/setting/selection/getErrorLogs",
        exportDatatoCsv: "ctx/pereg/person/info/setting/selection/exportDatatoCsv",
    }

    export function getLogResult(imexProcessID: string) {
        let _path = format(paths.getLogResults, imexProcessID);
        return nts.uk.request.ajax("com", _path);
    }

    export function getPerInfoSelectionItem(imexProcessID: string) {
        let _path = format(paths.getErrorLogs, imexProcessID);
        return nts.uk.request.ajax("com", _path);
    }
    
    export function exportDatatoCsv(imexProcessID: string) {
        let _path = format(paths.exportDatatoCsv, imexProcessID);
        return nts.uk.request.ajax("com", _path);
    }
}