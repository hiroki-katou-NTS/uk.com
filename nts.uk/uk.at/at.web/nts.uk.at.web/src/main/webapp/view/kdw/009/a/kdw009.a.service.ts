module nts.uk.at.view.kdw009.a.service {
    
    var paths: any = {
        getAll: "at/record/businesstype/findAll"
//        update: "ctx/at/record/workrecord/erroralarm/update"
    }
    
    export function getAll(){
        return nts.uk.request.ajax(paths.getAll);    
    }
    
//    export function update(command){
//        return nts.uk.request.ajax(paths.update, command);    
//    }
    
}          