module cmm044.g.service {
    var servicePath = {
        addPerson: "sys/portal/webmenu/addPerson"
    }
    
    export function addPerson(agent: any){
        return nts.uk.request.ajax("com", servicePath.addPerson, agent);
    }

}