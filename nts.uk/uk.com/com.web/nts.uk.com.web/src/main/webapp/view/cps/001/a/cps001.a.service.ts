module cps001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        layout: {
            getAll: "ctx/bs/person/maintenance/findAll",
            getDetails: "ctx/bs/person/maintenance/findOne/{0}"
        },
        category: {
            'getData': 'bs/employee/category/getAll/{0}',
            'getTabInfo': 'bs/employee/category/tabchildren/find/getTabDetail/{0}/{1}/{2}', //{employeeId}/{categoryId}/{infoId} 
        },
        person: {
            'getPerson': 'bs/employee/person/findByEmployeeId/{0}'
        },
        emp: {
            getFile: 'basic/organization/empfilemanagement/find/getAvaOrMap/{0}/{1}'
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
}