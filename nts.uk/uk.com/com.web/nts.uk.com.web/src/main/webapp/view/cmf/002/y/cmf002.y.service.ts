module nts.uk.com.view.cmf002.y {
        import ajax = nts.uk.request.ajax;
        import format = nts.uk.text.format;
    export module service {
        var paths = {
            getExterOutExecLog: "exio/exi/execlog/getExterOutExecLog/{0}",
            getStdOutputCondSet: "exio/exi/execlog/getCndSet/{0}"
        }
    
        export function getExterOutExecLog(storeProcessingId: string): JQueryPromise<any> {
            let _path = format(paths.getExterOutExecLog, storeProcessingId);
            return ajax('com', _path);
        }
    
        export function getStdOutputCondSet(storeProcessingId: string): JQueryPromise<any> {
            let _path = format(paths.getStdOutputCondSet, storeProcessingId);
            return ajax('com', _path);
        }
    }
}