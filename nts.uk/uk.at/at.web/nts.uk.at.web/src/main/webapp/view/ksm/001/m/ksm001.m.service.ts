module nts.uk.com.view.ksm001.m {
    import exportFile = nts.uk.request.exportFile;
    export module service {
        // Service paths.
        var servicePath = {
            saveAsExcel: "person/report/masterData"
        }
        export function saveAsExcel(languageId: string, date: string): JQueryPromise<any> {
            return exportFile('/masterlist/report/print', { domainId: "ShiftEstimate", domainType: "目安時間・金額の登録", languageId: languageId, reportType: 0, option : date });
        }
    }

}