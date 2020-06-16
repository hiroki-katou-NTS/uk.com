module kdp003.k.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        getData: 'hr/notice/report/regis/person/document/findAll'
    };

    export function getData(param: any) {
        return ajax('hr', paths.getData, param);
    }
}