module nts.uk.at.view.kmf004.h.service {
    
    var paths: any = {
        getAll: "at/shared/relationship/getAll",
        update: "at/shared/relationship/update",
        insert: "at/shared/relationship/add",
        remove: "at/shared/relationship/delete"
    }
    
    export function getAll(){
        return nts.uk.request.ajax(paths.getAll);    
    }    
    
    export function update(command: viewmodel.Relationship): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.update, command);    
    }
    
    export function insert(command: viewmodel.Relationship): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.insert, command);    
    }
    
    export function remove(command: viewmodel.Relationship): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.remove, command);    
    }
}   