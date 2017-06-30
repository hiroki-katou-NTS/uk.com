module nts.uk.sys.view.ccg013.b.service {
    var paths: any = {
        getEditMenuBar: "sys/portal/webmenu/edit",
    }
    
    export function getEditMenuBar(): JQueryPromise<EditMenuBarDto>{
        return nts.uk.request.ajax("com", paths.getEditMenuBar);
    }
    
    export interface EditMenuBarDto {
        listMenuClassification: Array<any>,
        listSelectedAtr: Array<any>,
        listStandardMenu: Array<MenuBarDto>,
        listSystem: Array<any>
    }
    
    export interface MenuBarDto {
        afterLoginDisplay: number,
        classification: number,
        code: string,
        companyId: string,
        displayName: string,
        displayOrder: number,
        logSettingDisplay: number,
        menuAtr: number,
        system: number,
        targetItems: string,
        url: string,
        webMenuSetting: number    
    }
}