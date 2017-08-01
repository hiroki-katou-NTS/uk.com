module cps008.a.service {
    let paths = {
        getAllMaintenanceLayout: "ctx/bs/person/maintenance/findAll"
    };

     /**
     * Get list Maintenance Layout
     */
        export function getAllMaintenanceLayout(): JQueryPromise<Array<viewmodel.MaintenanceLayout>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax("com",paths.getAllMaintenanceLayout)
                .done(function(res: Array<any>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

    

}