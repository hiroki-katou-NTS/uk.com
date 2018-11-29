module nts.uk.pr.view.qmm016.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getAllWageTable: "ctx/pr/core/wageprovision/wagetable/get-all-wagetable",
        getWageTable: "ctx/pr/core/wageprovision/wagetable/get-wagetable-by-code/{0}", 
        addWageTable: "ctx/pr/core/wageprovision/wagetable/addWageTable",
        updateWageTable: "ctx/pr/core/wageprovision/wagetable/updateWageTable",
        createOneDimentionWageTable: "ctx/pr/core/wageprovision/wagetable/create-1d-wage-table"
    }
    
    export function getAllWageTable(): JQueryPromise<any> {
        return ajax('pr', paths.getAllWageTable);
    }
    
    export function getWageTableByCode(code: string): JQueryPromise<any> {
        return ajax('pr', format(paths.getWageTable, code));
    }
    
    export function addNewWageTable(data): JQueryPromise<any> {
        return ajax('pr', paths.addWageTable, data);
    }
    
    export function updateWageTable(data): JQueryPromise<any> {
        return ajax('pr', paths.updateWageTable, data);
    }
    
    export function createOneDimentionWageTable(data): JQueryPromise<any> {
        return ajax('pr', paths.createOneDimentionWageTable, data);
    }
}
