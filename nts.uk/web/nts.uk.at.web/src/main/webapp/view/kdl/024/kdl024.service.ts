module kdl024.service {
    var path = {
        getExternalBudgetList: "at/findallexternalbudget",
        insertExternalBudget: "at/insertexternalbudget",
        updateExternalBudget: "at/updateexternalbudget",
        deleteExternalBudget: "at/deleteexternalbudget"
    }
    /**
     * get list External Budget
     */
    export function getListExternalBudget(): JQueryPromise<Array<model.ExternalBudgetDto>> {
        var dfd = $.Deferred<Array<any>>();
        //TODO-- service Get List

        return dfd.promise();
    }
    /**
     * Update Budget
     * 
     */
    export function updateExternalBudget(updateBudgetCmd : any){
        var dfd= $.Deferred<Array<any>>();  
        //TODO --> update List
        return dfd.promise();
    } 

    export module model {
        export class ExternalBudgetDto{
            externalBudgetCode : string;
            externalBudgetName : string;
            attribute :number;
            unit: number;
        }
    }



}