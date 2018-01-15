module nts.uk.at.view.kdw001.c {
    export module service {
        var paths: any = {
            findById: "ctx/at/shared/workrule/closure/findById/",
            findPeriodById: "ctx/at/shared/workrule/closure/findPeriodById/"
        }
        export function findById(closureId): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findById + closureId);
        }
        export function findPeriodById(closureId): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findPeriodById + closureId);
        }

    }
}
