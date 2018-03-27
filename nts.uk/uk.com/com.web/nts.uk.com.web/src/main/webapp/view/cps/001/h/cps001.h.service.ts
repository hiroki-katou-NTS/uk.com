module cps001.h.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let parentPath = "record/remainnumber/resv-lea/";
    let paths: any = {
            getAll: "get-resv-lea/{0}",
            getByGrantDate: "get-resv-lea-by-id/{0}"
    };
    
    export function getAll(){
        let employeeId: string = "a";
        return ajax('at',format(parentPath + paths.getAll, employeeId));
    }
    
    export function getByGrantDate(id: string){
        return ajax('at',parentPath + paths.getByGrantDate, {'id': id});
    }
    
    export function getItemDef(){
        let ctgId: string = "CS00038";
        return ajax('com',format("ctx/pereg/person/info/ctgItem/findby/ctg-cd/{0}", ctgId));
    }
}