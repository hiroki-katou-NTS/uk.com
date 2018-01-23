module nts.uk.at.view.kmf004.g.service {
    
    var paths: any = {
        findAll: "at/shared/relationship/findAll",
        findAllGrantRelationship: "at/shared/grantrelationship/findAll/",
//        update: "at/shared/grantrelationship/update",
        insert: "at/shared/grantrelationship/add",
        remove: "at/shared/grantrelationship/delete"
    }
   
    export function findAll(specialHolidayCode: String): JQueryPromise<any>{
        return nts.uk.request.ajax(paths.findAllGrantRelationship+specialHolidayCode);    
    }  
    
    export function findAllGrantRelationship(specialHolidayCode: String): JQueryPromise<any>{
        return nts.uk.request.ajax(paths.findAllGrantRelationship+specialHolidayCode);    
    }    
    
//    export function update(command: viewmodel.GrantRelationship): JQueryPromise<void>{
//        return nts.uk.request.ajax(paths.update, command);    
//    }
    
    export function insert(command: viewmodel.GrantRelationship): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.insert, command);    
    }
    
    export function remove(command: viewmodel.GrantRelationship): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.remove, command);    
    }
}   