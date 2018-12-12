module nts.uk.com.view.cps017.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    import exportFile = nts.uk.request.exportFile;
    var paths = {
        getAllSelectionItems: "ctx/pereg/person/info/setting/selection/findAll/true",
        getPerInfoSelectionItem: "ctx/pereg/person/info/setting/selection/findItem/{0}",
        getAllPerInfoHistorySelection: "ctx/pereg/person/info/setting/selection/findAllHistSelection/{0}",
        getAllOrderItemSelection: "ctx/pereg/person/info/setting/selection/findAllSelection/{0}",
        saveDataSelection: "ctx/pereg/person/info/setting/selection/addSelection",
        updateDataSelection: "ctx/pereg/person/info/setting/selection/updateSelection",
        removeDataSelection: "ctx/pereg/person/info/setting/selection/removeSelection",
        removeHistory: "ctx/pereg/person/info/setting/selection/removeHistory",
        reflUnrComp: "ctx/pereg/person/info/setting/selection/reflunrcomp",
        saveAsExcel: "person/report/masterData"
    }

    export function getAllSelectionItems() {
        return ajax(paths.getAllSelectionItems);
    }

    export function getPerInfoSelectionItem(selectionItemId: string) {
        let _path = format(paths.getPerInfoSelectionItem, selectionItemId);
        return nts.uk.request.ajax("com", _path);
    }

    export function getAllPerInfoHistorySelection(selectedId: string) {
        let _path = format(paths.getAllPerInfoHistorySelection, selectedId);
        return nts.uk.request.ajax("com", _path);
    }

    export function getAllOrderItemSelection(histId: string) {
        let _path = format(paths.getAllOrderItemSelection, histId);
        return nts.uk.request.ajax("com", _path);
    }

    //save data Selection:
    export function saveDataSelection(command) {
        return ajax(paths.saveDataSelection, command);
    }

    // update data Selection:
    export function updateDataSelection(command) {
        return ajax(paths.updateDataSelection, command);
    }

    // remove data selection:
    export function removeDataSelection(command) {
        return ajax(paths.removeDataSelection, command);
    }

    // remoe history:
    export function removeHistory(command) {
        return ajax(paths.removeHistory, command);
    }

    // Phan anh den cty:
    export function reflUnrComp(command) {
        return ajax(paths.reflUnrComp, command);
    }

    export function saveAsExcel(languageId: string, date: string): JQueryPromise<any> {
        return exportFile('/masterlist/report/print', { domainId: "PersonSelectionItem", domainType: "個人情報の選択肢の登録", languageId: languageId, reportType: 0, option : date });
    }

}

