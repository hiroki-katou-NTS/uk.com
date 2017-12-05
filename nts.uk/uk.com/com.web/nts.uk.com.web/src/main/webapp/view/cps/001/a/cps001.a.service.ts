module cps001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        layout: {
            getAll: "ctx/pereg/person/maintenance/findAll",
            getDetails: "ctx/pereg/person/maintenance/findOne/{0}"
        },
        category: {
            'getData': 'ctx/pereg/employee/category/getAll/{0}',
            'getTabInfo': 'ctx/pereg/layout/find/getTabDetail',
        },
        person: {
            'getPerson': 'bs/employee/person/findByEmployeeId/{0}'
        },
        emp: {
            getInfo: 'basic/organization/employee/get-info/{0}',
            getFile: 'basic/organization/empfilemanagement/find/getAvaOrMap/{0}/{1}',
            permision: 'ctx/pereg/roles/auth/get-self-auth',
        },
        file: '/shr/infra/file/storage/infor/{0}',
        saveData: ''
    };

    export function getPerson(id: string) {
        return ajax(format(paths.person.getPerson, id));
    }

    export function getCats(id: string) {
        return ajax(format(paths.category.getData, id));
    };

    export function getAvatar(id: string) {
        return ajax(format(paths.emp.getFile, id, 0));
    }

    export function getEmpInfo(id: string) {
        return ajax(format(paths.emp.getInfo, id));
    }

    export function getCurrentEmpPermision() {
        return ajax(paths.emp.permision);
    }

    export function getAllLayout() {
        return ajax(paths.layout.getAll);
    };

    export function getCurrentLayout(id: string) {
        return ajax(format(paths.layout.getDetails, id));
    }

    export function getData() {
        return ajax(paths.getData);
    }

    export function saveData(command) {
        return ajax(paths.saveData, command);
    }

    export function getFileInfo(id: string) {
        return ajax(paths.file, id);
    }

    export function getTabInfo(data: any) {
        return ajax(paths.category.getTabInfo, data);
    }
}