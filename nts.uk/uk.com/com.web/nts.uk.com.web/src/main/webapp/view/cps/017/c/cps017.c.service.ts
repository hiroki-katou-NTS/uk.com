module nts.uk.com.view.cps017.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        //getAllSelection: "ctx/bs/person/info/setting/selection/findAllSelection/{0}",
        getAllPerInfoHistorySelection: "ctx/bs/person/info/setting/selection/findAllHistSelection/{0}",
        addHistoryData: "ctx/bs/person/info/setting/selection/addHistoryData"
    }

    //history datetime:
    export function getAllPerInfoHistorySelection(selectionItemId: string) {
        let _path = format(paths.getAllPerInfoHistorySelection, selectionItemId);
        return nts.uk.request.ajax("com", _path);
    }

    // add history data:
    export function addHistoryData(command) {
        return ajax(paths.addHistoryData, command);
    }
}

