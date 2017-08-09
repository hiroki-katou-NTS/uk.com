module nts.uk.com.view.cas001.d.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    var paths = {
        getAllCategory: "ctx/bs/person/roles/auth/category/find/{0}",
        update: "ctx/bs/person/roles/auth/category/update"
    }
    /**
     * Get All Category
     */
    export function getAllCategory(roleId: string) {
        return ajax(format(paths.getAllCategory, roleId));
    }

    /**
     * update category
     */
    export function updateCategory(command: any) {
        return ajax(paths.update, command);
    }
}
