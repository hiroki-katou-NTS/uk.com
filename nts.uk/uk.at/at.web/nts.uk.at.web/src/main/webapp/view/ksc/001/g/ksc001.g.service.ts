module nts.uk.at.view.ksc001.g {
    export module service {
        var paths = {
            findExecutionHist: "at/schedule/exelog/findAll",
        }

        /**
         * call service find findExecutionHist 
         */
        export function findExecutionList(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findExecutionHist, data);
        }
    }
}
