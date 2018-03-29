module nts.uk.at.view.kmk004.d {
    
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            // ervicePath of find AlreadySetting for component KCP004 
            findAllWorkplaceSetting: 'screen/at/kmk004/workplace/findAll',
            
            findWorkplaceSetting: 'screen/at/kmk004/workplace/getDetails',
            saveWorkplaceSetting: 'screen/at/kmk004/workplace/save',
            removeWorkplaceSetting: 'screen/at/kmk004/workplace/delete'
        }
        // Find AlreadySetting
        export function findAllWorkplaceSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAllWorkplaceSetting);
        }
        
        // WORKPLACE
        export function saveWorkplaceSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveWorkplaceSetting, command);
        }

        export function findWorkplaceSetting(year: number, wkpId: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findWorkplaceSetting + '/' + year + '/' + wkpId);
        }

        export function removeWorkplaceSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeWorkplaceSetting, command);
        }
    }
}