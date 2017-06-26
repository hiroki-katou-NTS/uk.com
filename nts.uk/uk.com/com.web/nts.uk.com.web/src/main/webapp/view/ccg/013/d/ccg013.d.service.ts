module nts.uk.com.view.ccg013.d.service {
    import model = nts.uk.com.view.ccg.model;

    // Service paths.
    var servicePath = {
        findBySystem: "sys/portal/standardmenu/findBySystem/{0}",
    }
    
    export function findBySystem(system: number): JQueryPromise<Array<any>> {
        var path = nts.uk.text.format(servicePath.findBySystem, system);
        return nts.uk.request.ajax(path);
    }
}
