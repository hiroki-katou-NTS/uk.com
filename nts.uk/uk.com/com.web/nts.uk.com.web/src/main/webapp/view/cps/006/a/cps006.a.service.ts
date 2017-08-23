module nts.uk.com.view.cps006.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    let paths = {
        getAllCategory: "ctx/bs/person/info/ctg/findAll",
        getAllPerInfoItemDefByCtgId: "ctx/bs/person/info/ctgItem/findby/categoryId/{0}"
    }
    export function getAllCategory(): JQueryPromise<Array<any>> {
        return ajax(paths.getAllCategory);
    }

    export function getAllPerInfoItemDefByCtgId(categoryId: string): JQueryPromise<Array<any>> {
        return ajax(format(paths.getAllPerInfoItemDefByCtgId,categoryId));
    }
}
