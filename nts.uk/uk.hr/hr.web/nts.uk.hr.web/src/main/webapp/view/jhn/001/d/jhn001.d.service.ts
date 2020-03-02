module jhn001.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        getDetail: 'hr/notice/report/regis/person/sendback/get/{0}',
        save:      'hr/notice/report/regis/person/sendback/save',
    };


    export function getDetailRp(reportId) {
        return ajax(format(paths.getDetail,reportId));
    }

    export function saveData(obj) {
        return ajax('hr',paths.save, obj);
    }



}