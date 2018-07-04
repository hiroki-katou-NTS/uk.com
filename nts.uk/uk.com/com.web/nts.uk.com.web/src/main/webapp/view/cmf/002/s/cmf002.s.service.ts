module nts.uk.com.view.cmf002.s {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths = {
            findExOutOpMng: "exio/exi/execlog/findExOutOpMng/{0}",
            deleteexOutOpMng: "exio/exi/execlog/deleteexOutOpMng",
            getExterOutExecLog: "exio/exi/execlog/getExterOutExecLog/{0}"
        }  
        
        export function findExOutOpMng(storeProcessingId: string): JQueryPromise<any> {
            let _path = format(paths.findExOutOpMng, storeProcessingId);
            return ajax('com', _path);
        }
        
        export function deleteexOutOpMng(command: any): JQueryPromise<any> {
            return ajax("com", paths.deleteexOutOpMng, command);
        }
        
        export function getExterOutExecLog(storeProcessingId: string): JQueryPromise<any> {
            let _path = format(paths.getExterOutExecLog, storeProcessingId);
            return ajax('com', _path);
        }
    }
}