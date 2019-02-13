module nts.uk.pr.view.kmf001.a {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {

        };
        //export excel
        export function exportExcel(): JQueryPromise<any> {
            let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            let domainType = "KMF001";
            if (program.length > 1){
                program.shift();
                domainType = domainType + program.join(" ");
            }
            let _params = {
                domainId: "EmployeeSystem",
                domainType: domainType,
                languageId: "ja",
                reportType: 0
            };
            return nts.uk.request.exportFile('/masterlist/report/print', _params);
        }


    }
}
