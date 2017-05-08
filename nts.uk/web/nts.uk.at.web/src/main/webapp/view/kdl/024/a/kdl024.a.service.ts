module kdl024.a.service {
    var paths = {
        getExternalBudgetList: "at/schedule/budget/external/findallexternalbudget",
        insertExternalBudget: "at/schedule/budget/external/insertexternalbudget",
        updateExternalBudget: "at/schedule/budget/external/updateexternalbudget",
        deleteExternalBudget: "at/schedule/budget/external/deleteexternalbudget"
    }
    /**
     * get list External Budget
     */
    export function getListExternalBudget(): JQueryPromise<Array<model.ExternalBudgetDto>> {
        //        var dfd = $.Deferred<Array<any>>();
        //        nts.uk.request.ajax("at", paths.getExternalBudgetList)
        //            .done(function(res: Array<any>) {
        //                dfd.resolve(res);
        //            })
        //            .fail(function(res) {
        //                dfd.reject(res);
        //            })
        //        return dfd.promise();
        return nts.uk.request.ajax("at", paths.getExternalBudgetList);
    }
    /**
     * Update Budget
     * 
     */
    export function updateExternalBudget(updateBudgetCmd: any) {
        var dfd = $.Deferred<Array<any>>();
        //TODO --> update List
        let command = {} as model.ExternalBudgetDto;
        command.externalBudgetCode = updateBudgetCmd.externalBudgetCode();
        command.externalBudgetName = updateBudgetCmd.externalBudgetName();
        command.budgetAtr = Number(updateBudgetCmd.budgetAtr());
        command.unitAtr = updateBudgetCmd.unitAtr();

        nts.uk.request.ajax(paths.updateExternalBudget, command)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    //Insert 
    export function insertExternalBudget(insertBudgetCmd: any) {
        var dfd = $.Deferred<Array<any>>();
        //TODO --> update List
        let command = {} as model.ExternalBudgetDto;
        command.externalBudgetCode = insertBudgetCmd.externalBudgetCode();
        command.externalBudgetName = insertBudgetCmd.externalBudgetName();
        command.budgetAtr = Number(insertBudgetCmd.budgetAtr());
        command.unitAtr = insertBudgetCmd.unitAtr();
        nts.uk.request.ajax(paths.insertExternalBudget, command)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    //Delelte
    //Insert 
    export function deleteExternalBudget(deleteBudgetCmd: any) {
        var dfd = $.Deferred<Array<any>>();
        //TODO --> update List
        let command = {} as model.ExternalBudgetDto;
        command.externalBudgetCode = deleteBudgetCmd.externalBudgetCode();
        command.externalBudgetName = deleteBudgetCmd.externalBudgetName();
        command.budgetAtr = Number(deleteBudgetCmd.budgetAtr());
        command.unitAtr = deleteBudgetCmd.unitAtr();
        nts.uk.request.ajax(paths.deleteExternalBudget, command)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export module model {
        export class ExternalBudgetDto {
            externalBudgetCode: string;
            externalBudgetName: string;
            budgetAtr: number;
            unitAtr: number;
        }
    }



}