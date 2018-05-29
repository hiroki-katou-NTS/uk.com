module nts.uk.at.view.kmf004.d.service {
    
    var paths: any = {
        getAllPer: "at/shared/yearserviceper/findAllPer/",
        update: "at/shared/yearserviceper/update",
        add: "at/shared/yearserviceper/add",
        remove: "at/shared/yearserviceper/delete"  
    }
    
    export function getAll(specialHolidayCode: String) : JQueryPromise<any>{
        return nts.uk.request.ajax(paths.getAllPer + specialHolidayCode);    
    }
    
    export function update(command: viewmodel.Per): JQueryPromise<Array<string>>{
        return nts.uk.request.ajax(paths.update, command);    
    }
    
    export function add(command: viewmodel.Per): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.add, command);    
    }
    
    export function remove(command: viewmodel.Per): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.remove, command);    
    }   
}          