module nts.uk.com.view.cas005.a {
    export module service {
        var paths = {
            getListWebMenu : "ctx/sys/auth/webmenu/getlistwebmenu"
        }
        
        //get list web menu 
        export function getListWebMenu() : JQueryPromise<any>{
            return nts.uk.request.ajax("com",paths.getListWebMenu);
        }
        
    }
}
    