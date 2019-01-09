module nts.uk.at.view.ksm005.a {
    import getText = nts.uk.resource.getText;
    export module service {
        export function saveAsExcel(mode: string, standardDate: string, startDate: string, endDate: string): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', 
                {domainId: 'MonthlyWorkSetting', domainType: 'KSM005' + __viewContext.program.programName, 
                languageId: 'ja', reportType: 0, mode: mode, 
                baseDate : moment.utc(standardDate).format(),
                startDate : moment.utc(startDate).format(), 
                endDate : moment.utc(endDate).format()});
        }
    }
}