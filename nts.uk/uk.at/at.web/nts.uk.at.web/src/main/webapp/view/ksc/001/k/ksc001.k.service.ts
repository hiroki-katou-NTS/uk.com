module nts.uk.at.view.ksc001.k {
    export module service {
        var paths = {
            findLstEmployeeError: "at/schedule/exelog/",
            exportError: "at/schedule/exelog/error/export"
        }

        /**
         * call service find findLstEmployeeError
         */
        export function findExecutionDetail(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findLstEmployeeError);
        }

        export function exportError(executionId: string): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportError + "/" + executionId);
        }

        export module model {

        }

    }
}