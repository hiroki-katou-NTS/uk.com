module cps008.c.service {
    let paths = {
        getDetailMaintenanceLayout: "ctx/bs/person/maintenance/coppy"
    };

    /**
    * Get list Maintenance Layout
    */
    export function updateOrRegisterlMaintenanceLayout(obj : any): JQueryPromise<viewmodel.UpdateMaintenanceLayoutCommand> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax("com", paths.getDetailMaintenanceLayout , obj)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

      



}