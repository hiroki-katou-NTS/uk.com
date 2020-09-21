module nts.uk.at.view.kaf022.m.service {
    const paths: any = {
        getAll: "at/request/setting/workplace/requestbyworkplace/getAll",
        save: "at/request/setting/workplace/requestbyworkplace/save",
        delete: "at/request/setting/workplace/requestbyworkplace/delete",
        copy: "at/request/setting/workplace/requestbyworkplace/copy"
    };

    export function getAll(): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(paths.getAll);
    }

    export function registerSetting(command): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(paths.save, command);
    }

    export function deleteSetting(command): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(paths.delete, command);
    }

    export function copySetting(command): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(paths.copy, command);
    }

}