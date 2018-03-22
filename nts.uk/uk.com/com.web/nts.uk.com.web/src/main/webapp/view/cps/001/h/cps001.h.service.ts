module cps001.h.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let parentPath = "at/record/remainnumber/resvLea/";
    let paths: any = {
            getAll: "getResvLea/{0}",
            getByGrantDate: "getResvLeaByGrantDate"
    };
    
    export function getAll(){
        let employeeId: string = "a";
    return ajax('at',format(parentPath + paths.getAll, employeeId));
    }
    
    export function getByGrantDate(grantDate: Date){
        return ajax(parentPath + paths.getByGrantDate, grantDate);
    }
    
}