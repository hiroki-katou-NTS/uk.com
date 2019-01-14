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
            export function saveAsExcel(languageId: string, startDate: any, endDate: any): JQueryPromise<any> {
                return nts.uk.request.exportFile('/masterlist/report/print', { domainId: "RegisterTime",    
                                                                               domainType: "KMK008 ３６協定時間の登録", 
                                                                               languageId: languageId, 
                                                                               reportType: 0,
                                                                               startDate: startDate,
                                                                               endDate: endDate  });
            }
}
