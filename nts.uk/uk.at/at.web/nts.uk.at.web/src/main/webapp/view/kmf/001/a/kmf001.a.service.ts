module nts.uk.pr.view.kmf001.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {

        };
        //export excel
        export function exportExcel(): JQueryPromise<any> {
            let _params = {
                domainId: "EmployeeSystem",
                domainType: "KMF001休暇の設定",
                languageId: "ja",
                reportType: 0
            };
            return nts.uk.request.exportFile('/masterlist/report/print', _params);
        }


    }
}
