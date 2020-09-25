module nts.uk.at.view.kaf022.p.service {
    import ajax = nts.uk.request.ajax;

    const paths: any = {
        getAll: 'at/request/setting/company/applicationapproval/optionalitem/findall',
        getOne: 'at/request/setting/company/applicationapproval/optionalitem/findone',
        save: 'at/request/setting/company/applicationapproval/optionalitem/save',
        delete: 'at/request/setting/company/applicationapproval/optionalitem/delete',
        getOptionalItems: "ctx/at/record/optionalitem/findall"
    };
        
    export function getAll() {
        return ajax("at", paths.getAll);
    }

    export function getOptionalItems() {
        return ajax("at", paths.getOptionalItems);
    }

    export function getOne(code: string) {
        return ajax("at", paths.getOne, code);
    }

    export function saveSetting(command): JQueryPromise<void>{
        return ajax("at", paths.save, command);
    }

    export function deleteSetting(command): JQueryPromise<void>{
        return ajax("at", paths.delete, command);
    }
}