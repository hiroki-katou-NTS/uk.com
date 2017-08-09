module cps008.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getAll: "ctx/bs/person/maintenance/findAll",
        getDetails: "ctx/bs/person/maintenance/findOne/",
        addMaintenanceLayout: "ctx/bs/person/maintenance/add"
    };

    /**
    * Get list Maintenance Layout
    */
    export function getAll() {
        return ajax(paths.getAll);
    }

    export function getDetails(lid) {
        return ajax(format(paths.getDetails + lid));
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