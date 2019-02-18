module nts.uk.at.view.kmk004.b {

    export module service {
        
        /**
         *  Service paths
         */
        var servicePath: any = {
            //Employee AlreadySetting (get all Domain "社員別通常勤務労働時間")
            findAlreadySetting: 'screen/at/kmk004/employee/findAll',
    
            findEmployeeSetting: 'screen/at/kmk004/employee/getDetails',
            saveEmployeeSetting: 'screen/at/kmk004/employee/save',
            removeEmployeeSetting: 'screen/at/kmk004/employee/delete',
            findprogramName : 'sys/portal/standardmenu/findProgramName/{0}/{1}'
        }
        
        export function findprogramName(programId: string, screenId: string): JQueryPromise<any> {
            return nts.uk.request.ajax('com', nts.uk.text.format(servicePath.findprogramName, programId, screenId));
        }
        
        export function saveAsExcel(domainType: string, startDate : any, endDate: any): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "SetWorkingHoursAndDays", domainType: domainType, languageId: 'ja', reportType: 0, mode: 4, startDate: startDate, endDate: endDate});
        }
        
        export function removeEmployeeSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeEmployeeSetting, command);
        }

        export function findEmployeeSetting(year: number, sid: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findEmployeeSetting + '/' + year + '/' + sid);
        }
        
        export function saveEmployeeSetting(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveEmployeeSetting, command);
        }
    
        // Find AlreadySetting for component
        export function findAlreadySetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAlreadySetting);
        }
    }
}