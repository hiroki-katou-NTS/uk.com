module jhn001.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
         getListReportSaveDraft: 'hr/notice/report/regis/person/getAll',
         deleteReport: 'hr/notice/report/regis/person/remove/{0}',
    };


    export function getListReportSaveDraft() {
        return ajax('hr', paths.getListReportSaveDraft);
    }
    
    export function deleteReport(reportId : any) {
        return ajax('hr', paths.deleteReport, reportId);
    }
    
    
    
}