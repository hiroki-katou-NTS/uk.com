module nts.uk.com.view.cmf005.c.service {
    import ajax = nts.uk.request.ajax;

    var paths = {
        getSystemType: "ctx/sys/assist/systemtype/getlistsystemtype",
        getCategorys: "ctx/sys/assist/category/categorysytemtype/"
    }

    //Get list systemType
    export function getListSystemType(): JQueryPromise<any> {
        return ajax(paths.getSystemType);
    }

    //get list Category deletion
    export function getCategorysDeletion(sysType: number, searchText: string, listCategoryId: any): JQueryPromise<any> {
        return nts.uk.request.ajax("com", paths.getCategorys, sysType, searchText, listCategoryId);
    }



}