module nts.uk.at.view.ksm005.a {
    import getText = nts.uk.resource.getText;
    export module service {
        export function saveAsExcel(mode: string, startDate: string, endDate: string): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', 
                {domainId: 'MonthlyWorkSetting', domainType: 'KSM005' + getText("KSM005_1"), 
                languageId: 'ja', reportType: 0, mode: mode, 
                startDate : moment.utc(startDate).format(), 
                endDate : moment.utc(endDate).format()});
        }
    }
}