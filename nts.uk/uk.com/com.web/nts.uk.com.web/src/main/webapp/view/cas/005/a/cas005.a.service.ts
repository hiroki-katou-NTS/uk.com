module nts.uk.com.view.cas005.a {
    export module service {
        var paths = {
            getListWebMenu : "ctx/sys/auth/webmenu/getlistwebmenu",
            getAllWorkPlaceFunction :"at/auth/workplace/wplmanagementauthority/WorkPlaceFunction/getlistworkplacefunction"
        }
        
        //get list web menu 
        export function getListWebMenu() : JQueryPromise<any>{
            return nts.uk.request.ajax("com",paths.getListWebMenu);
        }
        //get list WorkPlace Function
        export function getAllWorkPlaceFunction() : JQueryPromise<any>{
            return nts.uk.request.ajax("at",paths.getAllWorkPlaceFunction);
        }
        
    }
}
    