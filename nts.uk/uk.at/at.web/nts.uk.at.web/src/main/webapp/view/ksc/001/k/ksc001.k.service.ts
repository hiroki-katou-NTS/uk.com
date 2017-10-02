module nts.uk.at.view.ksc001.k {
    export module service {
        var paths = {
           findLstEmployeeError: "",
        }
        
        /**
         * call service find findLstEmployeeError
         */
        export function findExecutionDetail(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findLstEmployeeError);
        }
        
        export module model {

        }

    }
}