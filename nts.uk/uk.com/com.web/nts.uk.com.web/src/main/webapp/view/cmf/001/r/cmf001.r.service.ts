module nts.uk.com.view.cmf.r.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getLogResults: "ctx/exio/ws/exi/execlog/getLogResults",
        getErrorLogs: "ctx/exio/ws/exi/execlog/getErrorLogs",
        exportDatatoCsv: "ctx/exio/ws/exi/execlog/export/{processId}",
    }

    /**
    * Get log results by processId
    */
    export function getLogResults(imexProcessID: string) {
        let _path = format(paths.getLogResults, imexProcessID);
        return nts.uk.request.ajax("com", _path);
    }

    /**
    * Get error log by processId
    */
    export function getErrorLogs(imexProcessID: string) {
        let _path = format(paths.getErrorLogs, imexProcessID);
        return nts.uk.request.ajax("com", _path);
    }
    
    /**
     * download export file
     */
    export function exportDatatoCsv(processId: string): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.exportDatatoCsv + "/" +  processId);
    }
}