module nts.uk.pr.view.qmm016.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    import exportFile = nts.uk.request.exportFile;
    var paths = {
        getAllWageTable: "ctx/pr/core/wageprovision/wagetable/get-all-wagetable",
        getWageTable: "ctx/pr/core/wageprovision/wagetable/get-wagetable-by-code/{0}",
        getWageTableContent: "ctx/pr/core/wageprovision/wagetable/get-wagetable-content/{0}/{1}",
        addWageTable: "ctx/pr/core/wageprovision/wagetable/addWageTable",
        updateWageTable: "ctx/pr/core/wageprovision/wagetable/updateWageTable",
        createOneDimentionWageTable: "ctx/pr/core/wageprovision/wagetable/create-1d-wage-table",
        createTwoDimentionWageTable: "ctx/pr/core/wageprovision/wagetable/create-2d-wage-table",
        createThreeDimentionWageTable: "ctx/pr/core/wageprovision/wagetable/create-3d-wage-table",
        getWageTableQualification: "ctx/pr/core/wageprovision/wagetable/get-wage-table-qualification/{0}/{1}",
        getWageTableByThirdDimension: "ctx/pr/core/wageprovision/wagetable/get-wagetable-content-by-third-dimension",
        exportFilePdf : "file/core/wageprovision/wagetable/exportExcel"
    };

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

    export function getWageTableContent(histId: string, wageTableCode: string): JQueryPromise<any> {
        return ajax('pr', format(paths.getWageTableContent, histId, wageTableCode));
    }

    export function getWageTableQualification(historyId: string, isInitScreen: boolean): JQueryPromise<any> {
        return ajax('pr', format(paths.getWageTableQualification, historyId, isInitScreen));
    }

    export function createTwoDimentionWageTable(data): JQueryPromise<any> {
        return ajax('pr', paths.createTwoDimentionWageTable, data);
    }

    export function createThreeDimentionWageTable(data): JQueryPromise<any> {
        return ajax('pr', paths.createThreeDimentionWageTable, data);
    }
    
    export function getWageTableByThirdDimension(data): JQueryPromise<any> {
        return ajax('pr', paths.getWageTableByThirdDimension, data);
    }
    export function exportExcel(data: any): JQueryPromise<any> {
        return nts.uk.request.exportFile( paths.exportFilePdf, data);
    }

}
