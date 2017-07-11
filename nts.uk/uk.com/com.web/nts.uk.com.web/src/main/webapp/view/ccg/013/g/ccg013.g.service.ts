module cmm044.g.service {
    var servicePath = {
        findBySystem: "sys/portal/standardmenu/findAllDisplay",
        addPerson: "sys/portal/webmenu/addPerson"
    }
    
    export function findBySystem(): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(servicePath.findBySystem);
    }
    
    export function addPerson(): JQueryPromise<Array<any>> {
        return nts.uk.request.ajax(servicePath.addPerson);
    }

}