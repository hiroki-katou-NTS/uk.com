module nts.uk.com.view.cas001.c.service {
  var paths = {
        getAllPersonRole: "ctx/bs/person/roles/findAll"
    }
    /**
     * Get All Person Role
     */
    export function getAllPersonRole(){
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
}
