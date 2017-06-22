module nts.uk.sys.view.ccg013.c.service {
    var paths: any = {
        getEditMenuBar: "sys/portal/webmenu/edit",
    }
    
    export function getEditMenuBar(): JQueryPromise<Array<any>>{
        return nts.uk.request.ajax("com", paths.getEditMenuBar);
    }
    

}