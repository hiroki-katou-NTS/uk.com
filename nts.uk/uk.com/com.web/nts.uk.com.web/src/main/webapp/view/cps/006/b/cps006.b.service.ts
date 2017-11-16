module nts.uk.com.view.cps006.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getItemInfoDefList: "ctx/bs/person/info/ctgItem/findby/categoryId/{0}/{1}",
        getPerInfoItemDefById: "ctx/bs/person/info/ctgItem/findby/itemIdOfOtherCompany/{0}",
        updateItemChange: "ctx/bs/person/info/ctgItem/updateItemChange",
        setOrder: "ctx/bs/person/info/ctgItem/SetOrder"
    }

    export function getItemInfoDefList(categoryId, isAbolition): JQueryPromise<any> {
        return ajax(format(paths.getItemInfoDefList, categoryId, isAbolition));
    }

    export function getPerInfoItemDefById(itemId): JQueryPromise<any> {
        return ajax(format(paths.getPerInfoItemDefById, itemId))
    }

    export function updateItemChange(command): JQueryPromise<any> {
        return ajax(paths.updateItemChange, command)
    }

    export function SetOrder(command): JQueryPromise<any> {
        return ajax(paths.setOrder, command);
    }

}
