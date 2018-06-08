module cps001.f.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'getData': 'basic/organization/empfilemanagement/getlistdocfile/{0}',
        'savedata': 'basic/organization/empfilemanagement/savedocfile',
        'updateCtgdata': 'basic/organization/empfilemanagement/updatectgdocfile',
        'updatedata': 'basic/organization/empfilemanagement/updatedata',
        'deletedata': 'basic/organization/empfilemanagement/deletedata/{0}',
        'permision': 'ctx/pereg/functions/auth/find-all/{0}'
    };

    export function getData(employeeId: any) {
        return ajax(format(paths.getData, employeeId));
    }

    export function savedata(command: any) {
        return ajax(paths.savedata, command);
    }

    export function updatedata(command: any) {
        return ajax(paths.savedata, command);
    }

    export function deletedata(fileid: any) {
        return ajax(format(paths.deletedata, fileid));
    }

    export function updateCtgdata(command: any) {
        return ajax(paths.updateCtgdata, command);
    }

    export function getCurrentEmpPermision() {
        let roleId = null;
        return ajax(paths.permision, roleId);
    }
}