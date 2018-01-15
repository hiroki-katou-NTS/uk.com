module nts.uk.sys.view.ccg013.j.service {
    var paths: any = {
        getTitleMenu: "sys/portal/titlemenu/findall",
    }
    
    export function getTitleMenu(): JQueryPromise<Array<any>>{
        return nts.uk.request.ajax("com", paths.getTitleMenu);
    }
    

}