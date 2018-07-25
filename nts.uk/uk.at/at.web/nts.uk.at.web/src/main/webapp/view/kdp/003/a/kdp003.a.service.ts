module nts.uk.at.view.kdp003.a {
    export module service {
        
        const SLASH = "/";
        
        var paths = {
            getDataStartPage: "at/function/statement/startPage",
            exportExcel: "screen/at/statement/export"
        }
        
        export function getDataStartPage(): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.getDataStartPage);
        }
        
        export function saveCharacteristic(companyId: string, userId: string, obj: any): void {
            nts.uk.characteristics.save("OutputConditionOfEmbossing" + 
                                                    "_companyId_" + companyId + 
                                                    "_userId_" + userId, obj);
        }
        
        export function restoreCharacteristic(companyId: string, userId: string): JQueryPromise<any> {
            return nts.uk.characteristics.restore("OutputConditionOfEmbossing" + 
                                                    "_companyId_" + companyId +  
                                                    "_userId_" + userId);
        }
        
        export function exportExcel(data: any): JQueryPromise<any> {
            return nts.uk.request.exportFile(paths.exportExcel, data);
        }
    }
}

