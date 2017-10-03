module nts.uk.at.view.ksc001.i {
    export module service {
        var paths = {
           findLstEmployeeExe: "",
        }
        
        /**
         * call service find LstEmployeeExe
         */
        export function findExecutionDetail(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findLstEmployeeExe);
        }
        
        export module model {

        }

    }
}
