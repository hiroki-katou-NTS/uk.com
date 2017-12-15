module nts.uk.at.view.kmk012.e {
    export module service {
        var paths = {
            getClosureEmploy: 'ctx/at/shared/workrule/closure/getClosureEmploy'
        }

        export function getClosureEmploy(startDate: any): JQueryPromise<any> {

            return nts.uk.request.ajax(paths.getClosureEmploy + "/" + startDate);

        }
    }
}