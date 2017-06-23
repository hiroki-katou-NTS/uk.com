module ccg013.a.service {

    // Service paths.
    var servicePath = {
        find: "sys/portal/webmenu/find/{0}",
        findAllWebMenu: "sys/portal/webmenu/find",
        addWebMenu: "sys/portal/webmenu/add",
        updateWebMenu: "sys/portal/webmenu/update",
    }
    
    export function findWebMenu(webMenuCode: string): JQueryPromise<any> {
        var path = nts.uk.text.format(servicePath.find, webMenuCode);
        return nts.uk.request.ajax(path);
    }  
    
    export function loadWebMenu(): JQueryPromise<Array<any>> {
        var path = servicePath.findAllWebMenu;
        return nts.uk.request.ajax(path);
    }  
    
    export function addWebMenu(webMenu: any): JQueryPromise<any> {
        var path = servicePath.addWebMenu;
        return nts.uk.request.ajax(path, webMenu);
    }
    
    export function updateWebMenu(webMenu: any): JQueryPromise<any> {
        var path = servicePath.updateWebMenu;
        return nts.uk.request.ajax("com", path, webMenu);
    }
    
}