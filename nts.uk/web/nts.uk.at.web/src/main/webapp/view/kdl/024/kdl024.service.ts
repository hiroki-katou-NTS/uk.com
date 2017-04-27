module kdl024.service {
    var paths = {
        getExternalBudgetList: "at/budget/findallexternalbudget",
        insertExternalBudget: "at/budget/insertexternalbudget",
        updateExternalBudget: "at/budget/updateexternalbudget",
        deleteExternalBudget: "at/budget/deleteexternalbudget"
    }
    /**
     * get list External Budget
     */
    export function getListExternalBudget(): JQueryPromise<Array<model.ExternalBudgetDto>> {
        var dfd = $.Deferred<Array<any>>();
        debugger;
        //TODO-- service Get List
        nts.uk.request.ajax(paths.getExternalBudgetList)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
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