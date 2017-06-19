module ccg013.a.service {

    // Service paths.
    var servicePath = {
        findAllWebMenu: "sys/portal/webmenu/find/all",
    }

    export function loadWebMenu(): JQueryPromise<Array<any>> {
        var path = servicePath.findAllWebMenu;
        return nts.uk.request.ajax(path)
    }   

}