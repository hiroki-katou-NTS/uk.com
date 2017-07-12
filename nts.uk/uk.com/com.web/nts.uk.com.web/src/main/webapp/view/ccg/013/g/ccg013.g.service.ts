module cmm044.g.service {
    var servicePath = {
        addPerson: "sys/portal/webmenu/addPerson",
        deletePerson: "sys/portal/webmenu/deletePerson",
        findPerson: "sys/portal/webmenu/findPerson"
    }
    
    export function addPerson(agent: any){
        return nts.uk.request.ajax("com", servicePath.addPerson, agent);
    }
    
    export function deletePerson(agent: any){
        return nts.uk.request.ajax("com", servicePath.deletePerson, agent);
    }
    
    export function findPerson(personId){
        return nts.uk.request.ajax("com", servicePath.findPerson +"/"+ personId);
    }
}