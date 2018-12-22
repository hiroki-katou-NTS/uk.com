module nts.uk.com.view.cmm051.m {
    import exportFile = nts.uk.request.exportFile;
    export module service {
        // Service paths.
        var servicePath = {
            saveAsExcel: "person/report/masterData"
        }
        export function saveAsExcel(languageId: string, date: string): JQueryPromise<any> {
            let _params = { domainId: "WorkPlaceSelection", 
                        domainType: "CMM051職場管理者の登録", 
                        languageId: languageId, 
                        reportType: 0, startDate : date };
            return exportFile('/masterlist/report/print', _params);           
        }
    }

}