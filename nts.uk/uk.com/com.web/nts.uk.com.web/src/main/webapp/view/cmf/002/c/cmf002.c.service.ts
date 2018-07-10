module nts.uk.com.view.cmf002.c.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getAllCategoryItem: "exio/exo/condset/getAllCategoryItem/{0}",
        findByCode: "exio/exo/condset/findByCode/{0}/{1}"
    }

    export function getAllCategoryItem(categoryId: string): JQueryPromise<any> {
        return ajax(format(paths.getAllCategoryItem, categoryId));
    }
    
    export function findByCode(conditionSettingCode: string, outputItemCode: string): JQueryPromise<any> {
        return ajax(format(paths.findByCode, conditionSettingCode, outputItemCode));
    }
}