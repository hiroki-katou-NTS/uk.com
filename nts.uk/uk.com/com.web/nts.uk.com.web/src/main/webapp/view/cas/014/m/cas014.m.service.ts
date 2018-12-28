module nts.uk.com.view.cas014.m {
    export module service {
        // Service paths.
        var servicePath = {
            saveAsExcel: "file/at/personrole/saveAsExcel"
        }
        export function saveAsExcel(languageId: string, date: string): JQueryPromise<any> {
                return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "JobInfo", domainType: "CAS014ロールセットの付与", languageId: languageId, reportType: 0, baseDate: date});
        }
    }

}