module nts.uk.at.view.ksm005.a {
    import getText = nts.uk.resource.getText;
    export module service {
        export function saveAsExcel(mode: string, standardDate: string, startDate: string, endDate: string): JQueryPromise<any> {
            let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            let domainType = "KSM005";
            if (program.length > 1) {
                program.shift();
                domainType = domainType + program.join(" ");
            }
            return nts.uk.request.exportFile('/masterlist/report/print', 
                {domainId: 'MonthlyWorkSetting', domainType: domainType, 
                languageId: 'ja', reportType: 0, mode: mode, 
                baseDate : moment.utc(standardDate).format(),
                startDate : moment.utc(startDate).format(), 
                endDate : moment.utc(endDate).format()});
        }
    }
}