module nts.uk.com.view.cmf001.q {
    
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    export module service {
        
        var paths = {
            executionImportCsvData: "exio/exi/csvimport/execution",
        }   
    
        /**
         * call service execution import data
         */
        export function executionImportCsvData(command: any): JQueryPromise<any> {
            return ajax('com', paths.executionImportCsvData, command);
        }
            
    }
}