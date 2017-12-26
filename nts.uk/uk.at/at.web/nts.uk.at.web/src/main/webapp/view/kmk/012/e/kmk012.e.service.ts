module nts.uk.at.view.kmk012.e {
    export module service {
        var paths = {
            insertDelArray: 'ctx/at/shared/workrule/closure/addClousureEmp',
            getClosureEmploy: 'ctx/at/shared/workrule/closure/getClosureEmploy'
        }

        /**
         * add closure history call service
         */
        export function insertDelArray(clousureEmpAddDto: any): JQueryPromise<void> {
            return nts.uk.request.ajax(paths.insertDelArray, clousureEmpAddDto);
        }
        
        export function getClosureEmploy(): JQueryPromise<any> {

            return nts.uk.request.ajax(paths.getClosureEmploy);

        }
    }
}