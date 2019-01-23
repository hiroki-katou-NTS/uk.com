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
                let programName = program[1]!=null?program[1]:"";
                return nts.uk.request.exportFile('/masterlist/report/print', { domainId: "RegisterTime",    
                                                                               domainType: "KMK008"+programName, 
                                                                               languageId: languageId, 
                                                                               reportType: 0,
                                                                               startDate: startDate,
                                                                               endDate: endDate  });
            }
}
}
    }