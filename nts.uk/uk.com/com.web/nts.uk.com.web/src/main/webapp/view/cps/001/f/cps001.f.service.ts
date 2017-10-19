module cps001.f.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        'getData': 'basic/organization/empfilemanagement/getlistdocfile/{0}',
        'savedata': 'basic/organization/empfilemanagement/savedocfile',
    };

    export function getData(employeeId : any) {
        return ajax(format(paths.getData, employeeId));
    }

    export function savedata(command: any) {
        return ajax(paths.savedata, command);
    }
}