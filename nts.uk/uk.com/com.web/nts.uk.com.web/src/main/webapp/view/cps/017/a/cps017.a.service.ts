module nts.uk.com.view.cps017.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getAllSelectionItems: "ctx/bs/person/info/setting/selection/findAll",
        getPerInfoSelectionItem: "ctx/bs/person/info/setting/selection/findItem/{0}",
        getAllPerInfoHistorySelection: "ctx/bs/person/info/setting/selection/findAllHistSelection/{0}",
        getAllOrderItemSelection: "ctx/bs/person/info/setting/selection/findAllSelection/{0}"
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
    
    //
//    export function getAllOrderItemSelection() {
//        return ajax(paths.getAllOrderItemSelection);
//    }
    
    export function getAllOrderItemSelection(histId: string) {
        let _path = format(paths.getAllOrderItemSelection, histId);
        return nts.uk.request.ajax("com", _path);
    }
}




/*
module nts.uk.com.view.cps017.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let paths = {
    }
}
*/
