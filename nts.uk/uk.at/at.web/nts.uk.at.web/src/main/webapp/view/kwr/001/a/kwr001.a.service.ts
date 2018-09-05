module nts.uk.at.view.kwr001.a {
    export module service {
        
        const SLASH = "/";
        
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
        
        export function exportExcel(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportExcel, data);
        }
    }
} 

//# sourceMappingURL=http://localhost:8080/nts.uk.at.web/view/kwr/001/a/kwr001.a.vm.ts