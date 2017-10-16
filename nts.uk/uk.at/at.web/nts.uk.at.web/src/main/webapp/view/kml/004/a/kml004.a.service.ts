module nts.uk.at.view.kml004.a.service {
     var paths: any = {
        findAllCate: "at/schedule/schedulehorizontal/findAllCate/",
        findAllItem: "at/schedule/schedulehorizontal/findItem/",
        findAllSet: "at/schedule/schedulehorizontal/findSet",
        remove: "at/schedule/schedulehorizontal/delete",
        add: "at/schedule/schedulehorizontal/add",
        update: "at/schedule/schedulehorizontal/update",
    }
    
    export function getAll(){   
        return nts.uk.request.ajax(paths.findAllCate); 
    }
    
    export function getItem(){
        return nts.uk.request.ajax(paths.findAllItem);
    }
    
     export function getSet(){
        return nts.uk.request.ajax(paths.findAllSet);
    }
    
    export function update(aa: viewmodel.TotalCategory): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.update, aa);    
    }
    
    export function add(command: viewmodel.TotalCategory): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.add, command);    
    }
    
    export function remove(command: viewmodel.TotalCategory): JQueryPromise<void>{
        return nts.uk.request.ajax(paths.remove, command);    
    }
}