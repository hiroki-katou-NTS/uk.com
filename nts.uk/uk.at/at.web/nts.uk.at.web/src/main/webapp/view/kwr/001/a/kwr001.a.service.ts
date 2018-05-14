module nts.uk.at.view.kwr001.a {
    export module service {
        var paths = {
           getDataStartPage: "at/function/dailyworkschedule/startPage",
           exportExcel: "screen/at/dailyschedule/export"
        }
        
        export function getDataStartPage(isExist: boolean): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getDataStartPage + SLASH + isExist);
        }
        
        export function saveCharacteristic(companyId: string, userId: string, obj: any): void {
            nts.uk.characteristics.save("WorkScheduleOutputCondition" + 
                                                    "_companyId_" + companyId + 
                                                    "_employeeId_" + userId, obj);
        }
        
        export function restoreCharacteristic(companyId: string, userId: string): JQueryPromise<any> {
            return nts.uk.characteristics.restore("WorkScheduleOutputCondition" + 
                                                    "_companyId_" + companyId +  
                                                    "_employeeId_" + userId);
        }
        
        export function exportExcel(): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.exportExcel);
        }
        
        const SLASH = "/";
        
    }
}