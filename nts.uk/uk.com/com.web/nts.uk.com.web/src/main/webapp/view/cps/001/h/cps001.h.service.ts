module cps001.h.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let parentPath = "record/remainnumber/resv-lea/";
    let paths: any = {
            getAll: "get-resv-lea/{0}",
            getByGrantDate: "getResvLeaByGrantDate"
    };
    
    export function getAll(){
        let employeeId: string = "a";
    return ajax('at',format(parentPath + paths.getAll, employeeId));
    }
    
    export function getByGrantDate(grantDate: Date){
        return ajax(parentPath + paths.getByGrantDate, grantDate);
    }
    
    export function getItemDef(){
        let ctgId: string = "a";
        return ajax('com',format("ctx/pereg/person/info/ctgItem/findby/ctg-cd/{0}", ctgId));
    }
}