module nts.uk.at.view.ksc001.h {
    export module service {
        var paths = {
            findExecutionDetail: "at/schedule/exelog/createContent",
        }

        /**
         * call service find findExecutionDetail by execution Id
         */
        export function findExecutionDetail(executionId: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findExecutionDetail + '/' + executionId);
        }
    }
}
