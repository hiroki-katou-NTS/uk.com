module nts.uk.com.view.cps017.m {
    import exportFile = nts.uk.request.exportFile;
    export module service {
        // Service paths.
        var servicePath = {
            saveAsExcel: "person/report/masterData"
        }
        export function saveAsExcel(languageId: string, date: string): JQueryPromise<any> {
            let _params = { domainId: "PersonSelectionItem", 
                        domainType: "CPS017個人情報の選択肢の登録", 
                        languageId: languageId, 
                        reportType: 0, option : date };
            return exportFile('/masterlist/report/print', _params);
        }
    }

}