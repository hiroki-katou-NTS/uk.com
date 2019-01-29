module nts.uk.at.view.kmk004.c {
    
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            // Find AlreadySetting for component KCP001 (get all Domain "")
            findAllEmploymentSetting: 'screen/at/kmk004/employment/findAll',
            
            findEmploymentSetting: 'screen/at/kmk004/employment/getDetails',
            saveEmploymentSetting: 'screen/at/kmk004/employment/save',
            removeEmploymentSetting: 'screen/at/kmk004/employment/delete'
        }
        
        export function saveAsExcel(languageId: string, startDate : any, endDate: any): JQueryPromise<any> {
                let domainType = "KMK004" + nts.uk.resource.getText("KMK004_194");
                return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "SetWorkingHoursAndDays", domainType: domainType, languageId: languageId, reportType: 0, mode: 4, startDate: startDate, endDate: endDate});
        }
        
        // Find AlreadySetting for component KCP001
        
        export function findAllEmploymentSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAllEmploymentSetting);
        }
        
        // EMPLOYMENT
        
        export function findEmploymentSetting(year: number, empCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findEmploymentSetting + '/' + year + '/' + empCode);
        }
        
        export function saveEmploymentSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveEmploymentSetting, command);
        }

        export function removeEmploymentSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeEmploymentSetting, command);
        }
    }
}