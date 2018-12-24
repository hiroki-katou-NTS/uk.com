module nts.uk.com.view.kmk004.m {
    export module service {
        // Service paths.
        var servicePath = {
            saveAsExcel: "file/at/personrole/saveAsExcel"
        }
        export function saveAsExcel(languageId: string, startDate : any, endDate: any): JQueryPromise<any> {
                return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "SetWorkingHoursAndDays", domainType: "KMK004労働時間と日数の設定", languageId: languageId, reportType: 0, startDate: startDate, endDate: endDate});
        }
    }

}