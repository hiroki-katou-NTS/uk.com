module nts.uk.at.view.ksp001.b.service {
    let paths: any = {
        getListMenu: "sys/portal/webmenu/smartphonemenu/getListMenu",
        getDataByRoleId: "sys/portal/webmenu/smartphonemenu/getDataByRoleId",
        saveData: "sys/portal/webmenu/smartphonemenu/saveData",
        getListMenuRole: "sys/portal/webmenu/smartphonemenu/getListMenuRole",
        getMenuSpecial: "sys/portal/webmenu/smartphonemenu/getMenuSpecial"
    }

    export function getListMenu(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getListMenu);
    }

    export function getDataByRoleId(param: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getDataByRoleId, param);
    }

    export function saveData(data: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.saveData, data);
    }

    export function getListMenuRole(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getListMenuRole);
    }

    export function getMenuSpecial(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getMenuSpecial);
    }

}
