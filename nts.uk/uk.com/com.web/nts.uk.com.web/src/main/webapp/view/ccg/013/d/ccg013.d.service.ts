module nts.uk.com.view.ccg013.d.service {
    import model = nts.uk.com.view.ccg.model;

    // Service paths.
    var servicePath = {
        findBySystem: "sys/portal/standardmenu/findAllDisplay",
    }
    
    export function findBySystem(): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(servicePath.findBySystem);
    }
}
