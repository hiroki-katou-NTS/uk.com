module nts.uk.at.view.kmk004.d {
    
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            // TODO: servicePath of find AlreadySetting for component KCP004 
            findAllWorkplaceSetting: 'ctx/at/shared/employment/statutory/worktime/workplace/findall',
            
            findWorkplaceSetting: 'screen/at/kmk004/workplace/getDetails',
            saveWorkplaceSetting: 'screen/at/kmk004/workplace/save',
            removeWorkplaceSetting: 'screen/at/kmk004/workplace/delete'
        }
        
        // WORKPLACE
        export function saveWorkplaceSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveWorkplaceSetting, command);
        }

        export function findWorkplaceSetting(request: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findWorkplaceSetting, request);
        }

        export function findAllWorkplaceSetting(year: number, wkpId: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAllWorkplaceSetting + '/' + year + '/' + wkpId);
        }

        export function removeWorkplaceSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeWorkplaceSetting, command);
        }
    }
}