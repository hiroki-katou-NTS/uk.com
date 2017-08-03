module nts.uk.com.view.cas001.a.service {
    import ajax = nts.uk.request.ajax;
    var paths = {
        getAllItem: "ctx/bs/person/roles/auth/item/findAllItem/{0}",
        getAllCategory: "ctx/bs/person/roles/auth/category/findAllCategory/{0}"
    }
    export function getAllItem(personCategory:string): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
         var _path = nts.uk.text.format(paths.getAllItem, personCategory);
        nts.uk.request.ajax(_path)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    export function getAllCategory(roleId: string): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
         var _path = nts.uk.text.format(paths.getAllCategory, roleId);
        nts.uk.request.ajax(_path)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }



}
