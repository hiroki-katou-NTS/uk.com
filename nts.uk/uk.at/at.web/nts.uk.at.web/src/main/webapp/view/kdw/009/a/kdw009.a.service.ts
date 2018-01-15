module nts.uk.at.view.kdw009.a.service {
    
    var paths: any = {
        getAll: "at/record/businesstype/findAll",
        update: "at/record/businesstype/update",
        insert: "at/record/businesstype/add",
        remove: "at/record/businesstype/delete"
    }
    
    export function getAll(){
        return nts.uk.request.ajax(paths.getAll);    
    }
    
    export function update(command: viewmodel.BusinessType): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.update, command);    
    }
    
    export function insert(command: viewmodel.BusinessType): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.insert, command);    
    }
    
    export function remove(command: viewmodel.BusinessType): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.remove, command);    
    }
}          