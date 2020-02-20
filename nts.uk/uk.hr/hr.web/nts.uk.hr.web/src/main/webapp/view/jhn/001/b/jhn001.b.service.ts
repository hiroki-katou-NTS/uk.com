module jhn001.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        getListReportSaveDraft: 'hr/notice/report/regis/person/getAll-SaveDraft',
        remove:                 'hr/notice/report/regis/person/remove',
    };


    export function getListReportSaveDraft() {
        return ajax('hr', paths.getListReportSaveDraft);
    }

    export function removeData(objRemove) {
        return ajax('hr' , paths.remove, objRemove);
    }



}