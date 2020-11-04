module nts.uk.com.view.ccg013.z.service {
    import model = nts.uk.com.view.ccg.model;

    // Service paths.
    var servicePath = {
        findBySystem: "sys/portal/standardmenu/findAllDisplay",
        getEditMenuBar: "sys/portal/webmenu/edit",
    }
    
    export function findBySystem(): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(servicePath.findBySystem);
    }
    
    export function getEditMenuBar(): JQueryPromise<EditMenuBarDto> {
        return nts.uk.request.ajax("com", servicePath.getEditMenuBar);
    }
}
