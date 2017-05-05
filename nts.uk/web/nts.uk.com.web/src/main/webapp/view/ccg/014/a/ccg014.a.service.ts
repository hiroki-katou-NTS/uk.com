module ccg014.a.service {
    var paths: any = {
        getAllTitleMenu: "sys/portal/titlemenu/findall",
        createTitleMenu: "sys/portal/titlemenu/create",
        deleteTitleMenu: "sys/portal/titlemenu/delete",
        updateTitleMenu: "sys/portal/titlemenu/update",
        
    }
    // Get TitleMenu
    export function getAllTitleMenu (): JQueryPromise<Array<viewmodel.model.TitleMenuDto>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.getAllTitleMenu)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

     /** Init TitleMenu */
     
    

}