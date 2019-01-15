module nts.uk.at.view.ksm005.a {
    import getText = nts.uk.resource.getText;
    export module service {
        export function saveAsExcel(mode: string, standardDate: string, startDate: string, endDate: string): JQueryPromise<any> {
            let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            let programName = program[1] != null ? program[1] : "";
            return nts.uk.request.exportFile('/masterlist/report/print', 
                {domainId: 'MonthlyWorkSetting', domainType: 'KSM005' + programName, 
                languageId: 'ja', reportType: 0, mode: mode, 
                baseDate : moment.utc(standardDate).format(),
                startDate : moment.utc(startDate).format(), 
                endDate : moment.utc(endDate).format()});
        }
    }
}