module nts.uk.at.view.ksc001.i {
    export module service {
        var paths = {
            findAllCreator: "at/schedule/exelog/findAllCreator",
        }

        /**
         * call service findAllCreator
         */
        export function findAllCreator(executionId: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findAllCreator + "/" + executionId);
        }
    }
}
