module cps001.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'getListTemporaryEmpDelete': 'basic/organization/employee/getallemployeetodelete',
        'getDetail': 'basic/organization/employee/getdetailemployeetodelete/{0}'
    };

    export function getData() {
        return ajax(paths.getListTemporaryEmpDelete);
    }
    
     export function getDetail(code : any) {
        return ajax(format(paths.getData,code));
    }

    export function saveData(command) {
        return ajax(paths.saveData, command);
    }
}