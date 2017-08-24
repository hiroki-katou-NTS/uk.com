module nts.uk.com.view.cps006.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    var paths = {
        getItemInfoDefList: "ctx/bs/person/info/ctgItem/findby/categoryId/{0}",
        getPerInfoItemDefById: "ctx/bs/person/info/ctgItem/findby/itemId/{0}"
    }

    export function getItemInfoDefList(categoryId): JQueryPromise<any> {
        return ajax(format(paths.getItemInfoDefList, categoryId));
    }

    export function getPerInfoItemDefById(itemId): JQueryPromise<any> {
        return ajax(format(paths.getPerInfoItemDefById, itemId))
    }

}
