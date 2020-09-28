module nts.uk.at.view.kbt002.k {

    export module service {
        const paths = {
            exportCSV : "at/function/outputexechistory/exportCSV"
        }
    
        export function exportCSV(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.exportCSV, command);
        }
    }
}