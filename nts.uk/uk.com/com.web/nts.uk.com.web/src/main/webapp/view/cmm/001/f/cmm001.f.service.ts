module nts.uk.com.view.cmm001.f {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            executionMasterCopyData: "sys/assist/mastercopy/data/execute",
            exportFileError: "sys/assist/mastercopy/data/log/export",
            interrupt: "sys/assist/mastercopy/data/interrupt"
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