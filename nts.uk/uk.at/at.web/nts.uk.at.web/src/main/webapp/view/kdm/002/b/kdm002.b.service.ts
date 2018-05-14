module nts.uk.at.view.kdm002.b {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    
    export module service {
        var paths = {
            execution: "at/request/application/remainnumber/checkFunc/execution",
            exportDatatoCsv: "at/request/application/remainnumber/checkFunc/export"
        }
        /**
         * call service execution 
         */
        export function execution(command: any): JQueryPromise<any> {
            return ajax('at', paths.execution, command);
        }
        
        /**
         * download export file
         */
        export function exportDatatoCsv(data : viewmodel.IErrorLog[]): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportDatatoCsv, data);
        }
    }
}