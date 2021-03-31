module nts.uk.at.view.ksp001.c.service {
    let paths: any = {
        changeMenuName: "sys/portal/webmenu/smartphonemenu/changeMenuName",
        getMenuSpecial: "sys/portal/webmenu/smartphonemenu/getMenuSpecial"
    }

    export function changeMenuName(standardMenus: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.changeMenuName, standardMenus);
    }
    
    export function getMenuSpecial(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getMenuSpecial);
    }

}