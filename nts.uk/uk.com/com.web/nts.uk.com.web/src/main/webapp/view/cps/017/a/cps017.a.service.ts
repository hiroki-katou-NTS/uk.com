module nts.uk.com.view.cps017.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getAllSelectionItems: "ctx/bs/person/info/setting/selection/findAll",
        getPerInfoSelectionItem: "ctx/bs/person/info/setting/selection/findItem/{0}"
    }

    export function getAllSelectionItems() {
        return ajax(paths.getAllSelectionItems);
    }

    export function getPerInfoSelectionItem(selectionItemId: string) {
        let _path = format(paths.getPerInfoSelectionItem, selectionItemId);
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
