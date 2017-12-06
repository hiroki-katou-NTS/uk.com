module cps001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        layout: {
            getAll: "ctx/pereg/person/maintenance/findSimple/{0}",
            getDetails: "ctx/pereg/person/maintenance/findLayoutData",
        },
        category: {
            'getCats': 'ctx/pereg/employee/category/getall/{0}',
            'getDetails': 'ctx/pereg/layout/find/gettabdetail',
            'getTabsInfo': 'ctx/pereg/layout/find/getctgtab/{0}' //categoryId
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
        return ajax(format(paths.category.getCats, id));
    };

    export function getCatData(query: any) {
        return ajax(paths.category.getDetails, query);
    }

    export function getAvatar(id: string) {
        return ajax(format(paths.emp.getFile, id, 0));
    }

    export function getEmpInfo(id: string) {
        return ajax(format(paths.emp.getInfo, id));
    }

    export function getCurrentEmpPermision() {
        return ajax(paths.emp.permision);
    }

    export function getAllLayout(empId: string) {
        return ajax(format(paths.layout.getAll, empId));
    };

    export function getCurrentLayout(query: any) {
        return ajax(paths.layout.getDetails, query);
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
}