module nts.uk.at.view.kmk004.d {
    
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findWorkplaceSetting: 'ctx/at/shared/employment/statutory/worktime/workplace/find',
            findAllWorkplaceSetting: 'ctx/at/shared/employment/statutory/worktime/workplace/findall',
            saveWorkplaceSetting: 'ctx/at/shared/employment/statutory/worktime/workplace/save',
            removeWorkplaceSetting: 'ctx/at/shared/employment/statutory/worktime/workplace/remove'
        }
        
        // WORKPLACE
        export function saveWorkplaceSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveWorkplaceSetting, command);
        }

        export function findWorkplaceSetting(request: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findWorkplaceSetting, request);
        }

        export function findAllWorkplaceSetting(year: number): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAllWorkplaceSetting + '/' + year);
        }

        export function removeWorkplaceSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeWorkplaceSetting, command);
        }
    }
}