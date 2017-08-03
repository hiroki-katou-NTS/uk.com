module nts.uk.com.view.cas001.d.service {
    import ajax = nts.uk.request.ajax;
    import format =nts.uk.text.format;
    var paths = {
        getAllCategory: "ctx/bs/person/roles/auth/category/findAllCategory/{0}"
    }
    /**
     * Get All Category
     */
    export function getAllCategory(roleId: string):JQueryPromise<Array<any>> {
      return  ajax(format(paths.getAllCategory, roleId));
    }
}
