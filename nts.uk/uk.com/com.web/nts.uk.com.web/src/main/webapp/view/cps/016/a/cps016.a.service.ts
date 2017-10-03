module nts.uk.com.view.cps016.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getAllSelectionItems: "ctx/bs/person/info/setting/selection/findAll",
        getPerInfoSelectionItem: "ctx/bs/person/info/setting/selection/findItem/{0}",
        saveDataSelectionItem: "ctx/bs/person/info/setting/selection/addSelectionItem",
        updateDataSelectionItem: "ctx/bs/person/info/setting/selection/updateSelectionItem",
        removeDataSelectionItem: "ctx/bs/person/info/setting/selection/removeSelectionItem"
        
    }

    export function getAllSelectionItems() {
        return ajax(paths.getAllSelectionItems);
    }

    export function getPerInfoSelectionItem(selectionItemId: string) {
        let _path = format(paths.getPerInfoSelectionItem, selectionItemId);
        return nts.uk.request.ajax("com", _path);
    }

    export function saveDataSelectionItem(command) {
        return ajax(paths.saveDataSelectionItem, command);
    }
    
    export function updateDataSelectionItem(command) {
        return ajax(paths.updateDataSelectionItem, command);
    }
    
    export function removeDataSelectionItem(command) {
        return ajax(paths.removeDataSelectionItem, command);
    }
}