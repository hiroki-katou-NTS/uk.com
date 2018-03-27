module nts.uk.at.view.kmk004.c {
    
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            findEmploymentSetting: 'ctx/at/shared/employment/statutory/worktime/employment/find',
            findAllEmploymentSetting: 'ctx/at/shared/employment/statutory/worktime/employment/findall',
            saveEmploymentSetting: 'ctx/at/shared/employment/statutory/worktime/employment/save',
            removeEmploymentSetting: 'ctx/at/shared/employment/statutory/worktime/employment/remove'
        }
        
        // EMPLOYMENT
        export function saveEmploymentSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveEmploymentSetting, command);
        }

        export function findEmploymentSetting(request: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findEmploymentSetting, request);
        }

        export function findAllEmploymentSetting(year: number): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAllEmploymentSetting + '/' + year);
        }

        export function removeEmploymentSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeEmploymentSetting, command);
        }
    }
}