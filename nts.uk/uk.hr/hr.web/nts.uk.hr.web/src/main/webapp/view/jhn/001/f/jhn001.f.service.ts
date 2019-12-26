module jhn001.f.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'getData': 'hr/notice/report/regis/person/document/findAll',
        'addDocument': 'hr/notice/report/regis/person/document/add',
        'deleteDocument': 'hr/notice/report/regis/person/document/delete'
    };

    export function getData(param: any) {
        return ajax('hr' ,format(paths.getData, param));
    }

    export function savedata(command: any) {
          return ajax('hr' ,format(paths.addDocument, command));
    }

    export function deletedata(command: any) {
       return ajax('hr' ,format(paths.deleteDocument, command));
    }

}