module cps008.a.service {
    let paths = {
        getAllMaintenanceLayout: "ctx/bs/person/maintenance/findAll",
        addMaintenanceLayout: "ctx/bs/person/maintenance/add"
    };

    /**
    * Get list Maintenance Layout
    */
    export function getAllMaintenanceLayout(): JQueryPromise<Array<viewmodel.MaintenanceLayout>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.getAllMaintenanceLayout)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

      /**
      * add  Maintenance Layout
      */
    export function addMaintenanceLayout(model: any) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com", paths.addMaintenanceLayout, model)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }



}