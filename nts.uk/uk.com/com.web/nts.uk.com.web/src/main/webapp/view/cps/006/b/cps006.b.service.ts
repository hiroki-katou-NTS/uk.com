module nts.uk.com.view.cps006.b.service {
    import ajax = nts.uk.request.ajax;
    var paths = {
        getItemInfoDefList: "ctx/bs/person/info/ctgItem/findby/categoryId/{0}",
    }

    export function getItemInfoDefList(categoryId): JQueryPromise<any> {
        return ajax(nts.uk.text.format(paths.getItemInfoDefList, categoryId));
    }

}
