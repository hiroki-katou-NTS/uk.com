module nts.uk.at.view.kdw007.a.service {
    
    var paths: any = {
        getAll: "ctx/at/record/workrecord/erroralarm/getall",
        update: "ctx/at/record/workrecord/erroralarm/update"
    }
    
    export function getAll(){
        return nts.uk.request.ajax(paths.getAll);    
    }
    
    export function update(command){
        return nts.uk.request.ajax(paths.update, command);    
    }
    
}