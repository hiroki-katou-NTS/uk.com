module nts.uk.at.view.ksc001.g {
    export module service {
        var paths = {
           findExecutionHist: "",
        }
        
        /**
         * call service find findExecutionHist 
         */
        export function findExecutionList(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findExecutionHist);
        }
        
        export module model {

        }

    }
}
