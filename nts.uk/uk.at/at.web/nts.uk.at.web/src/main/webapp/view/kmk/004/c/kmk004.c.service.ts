module nts.uk.at.view.kmk004.c {
    
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            // Find AlreadySetting for component KCP001 (get all Domain "")
            // TODO: path
            findAllEmploymentSetting: 'ctx/at/shared/employment/statutory/worktime/employment/findall',
            
            findEmploymentSetting: 'screen/at/kmk004/employment/getDetails',
            saveEmploymentSetting: 'screen/at/kmk004/employment/save',
            removeEmploymentSetting: 'screen/at/kmk004/employment/delete'
        }
        
        // EMPLOYMENT
        export function saveEmploymentSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveEmploymentSetting, command);
        }

        export function findEmploymentSetting(request: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findEmploymentSetting, request);
        }

        // Find AlreadySetting for component KCP001
        export function findAllEmploymentSetting(year: number, empCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAllEmploymentSetting + '/' + year + '/' + empCode);
        }

        export function removeEmploymentSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeEmploymentSetting, command);
        }
    }
}