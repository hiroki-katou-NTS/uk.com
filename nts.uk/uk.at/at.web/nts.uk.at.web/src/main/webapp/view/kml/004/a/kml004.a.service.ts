module nts.uk.at.view.kml004.a.service {
     var paths: any = {
        findAllCate: "at/schedule/schedulehorizontal/findAllCate/",
        findAllItem: "at/schedule/schedulehorizontal/findItem/",
        remove: "at/schedule/schedulehorizontal/delete",
        add: "at/schedule/schedulehorizontal/add",
        update: "at/schedule/schedulehorizontal/update",
    }
    
    export function getAll(){
        return nts.uk.request.ajax(paths.findAllCate); 
    }
    
    export function update(command: viewmodel.TotalCategory): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.update, command);    
    }
    
    export function add(command: viewmodel.TotalCategory): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.add, command);    
    }
    
    export function remove(command: viewmodel.TotalCategory): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.remove, command);    
    }
}