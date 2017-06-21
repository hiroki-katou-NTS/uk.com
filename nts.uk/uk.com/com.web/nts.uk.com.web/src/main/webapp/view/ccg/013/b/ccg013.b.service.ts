module nts.uk.sys.view.ccg013.b.service {
    var paths: any = {
        getAllStandardMenu: "sys/portal/standardmenu/findAll",
    }
    
    export function getAllStandardMenu(): JQueryPromise<Array<viewmodel.StandardMenu>>{
        return nts.uk.request.ajax("com", paths.getAllStandardMenu);    
    }
    

}