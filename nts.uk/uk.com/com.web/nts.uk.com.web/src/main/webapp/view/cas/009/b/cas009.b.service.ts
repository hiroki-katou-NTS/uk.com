module cas009.b.service {
    var paths: any = {
        getPerMissingMenu: "sys/portal/standardmenu/find/for/person/role",
        
    }
    
    /** Get PermissionSettingMenu */
    export function getPerMissingMenu(): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getPerMissingMenu);
    }

}