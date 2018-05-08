module nts.uk.com.view.cmf003.f {
    
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    export module service {
        
        var paths = {
            executionImportCsvData: "exio/exi/csvimport/execution",
            addErrorLog: "exio/exi/proccessLog/addErrorLog",
            check: "exio/exi/csvimport/check",
        }   
    
        /**
         * call service execution import data
         */
        export function executionImportCsvData(command: any): JQueryPromise<any> {
            return ajax('com', paths.executionImportCsvData, command);
        }
        
        /**
         * call service check import data
         */
        export function check(command: any): JQueryPromise<any> {
            return ajax('com', paths.check, command);
        }
        /**
        * add error log  
        */
        export function addErrorLog(command: any): JQueryPromise<any> {
            return ajax('com', paths.addErrorLog, command);
        }
    }
}