module nts.uk.com.view.cas001.e.service {
    var paths = {
        getAllPersonRole: "ctx/bs/person/roles/findAll",
        update: "ctx/bs/person/roles/update"
    }
    /**
     * Get All Person Role
     */
    export function getAllPersonRole() {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getAllPersonRole)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    /**
  *update Person Role
  */
    export function update(object: any) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.update,object)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}
