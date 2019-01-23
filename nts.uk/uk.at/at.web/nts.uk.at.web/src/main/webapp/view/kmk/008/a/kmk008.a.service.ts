module nts.uk.at.view.kmk008.a {
    export module service {
        export class Service {
            paths = {
                saveAsExcel: "file/at/worktypereport/saveAsExcel",         
             };
            constructor() { }

            public functionDemo(printType: number): JQueryPromise<any> {
                return null;
             }
            saveAsExcel(languageId: string, startDate: any, endDate: any): JQueryPromise<any> {
                let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
                let domainType = "KMK008";
                if (program.length > 1){
                    program.shift();
                    domainType = domainType + program.join(" ");
                }
                return nts.uk.request.exportFile('/masterlist/report/print', { domainId: "RegisterTime",    
                                                                               domainType: domainType,
                                                                               languageId: languageId, 
                                                                               reportType: 0,
                                                                               startDate: startDate,
                                                                               endDate: endDate  });
            }
}
}
    }