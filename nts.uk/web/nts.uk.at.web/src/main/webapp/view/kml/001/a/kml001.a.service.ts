module kml001.a.service {
    var paths: any = {
        personCostCalculationSelect: "at/budget/premium/findBycompanyID",
        personCostCalculationInsert: "at/budget/premium/insert",
        personCostCalculationUpdate: "at/budget/premium/update",
        personCostCalculationDelete: "at/budget/premium/delete",
        extraTimeSelect: "pr/core/retirement/payitem/findBycompanyCode"
    }
    
    export function personCostCalculationSelect(): JQueryPromise<any> {
       return nts.uk.request.ajax(paths.personCostCalculationSelect);
    }
   
    export function personCostCalculationInsert(command): JQueryPromise<any> {
       return nts.uk.request.ajax(paths.personCostCalculationInsert, command);
    }
    
    export function personCostCalculationUpdate(command): JQueryPromise<any> {
       return nts.uk.request.ajax(paths.personCostCalculationUpdate, command);
    }
    
    export function personCostCalculationDelete(command): JQueryPromise<any> {
       return nts.uk.request.ajax(paths.personCostCalculationDelete, command);
    }
    
    export function extraTimeSelect(): JQueryPromise<any> {
       return nts.uk.request.ajax(paths.extraTimeSelect);
    }
    
}