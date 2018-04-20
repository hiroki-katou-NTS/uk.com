module nts.uk.com.view.cmm001.f {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            executionMasterCopyData: "ctx/at/schedule/processbatch/execution",
            exportFileError: "ctx/at/schedule/processbatch/log/export",
            interrupt: "ctx/at/schedule/processbatch/interrupt"
            };
        
        /**
         * execution process copy
         */
        export function executionMasterCopyData(): JQueryPromise<any>{
            return nts.uk.request.ajax(path.executionMasterCopyData);
        }
        
        /**
         *  export error to csv service
         */
        export function exportFileError(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.exportFileError);
        }
        
        /**
         * pause process
         */
        export function pause(): JQueryPromise<any> {
            return nts.uk.request.ajax(path.interrupt);
        }
    }
    
    export module model {
        
    } 
}