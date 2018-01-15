module nts.uk.at.view.ksc001.k {
    export module service {
        var paths = {
            findAllError: "at/schedule/exelog/findAllError",
            exportError: "at/schedule/exelog/error/export"
        }

        /**
         * call service findAllError
         */
        export function findAllError(executionId: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findAllError + "/" + executionId);
        }

        /**
         * export error to csv file 
         */
        export function exportError(executionId: string): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportError + "/" + executionId);
        }
    }
}