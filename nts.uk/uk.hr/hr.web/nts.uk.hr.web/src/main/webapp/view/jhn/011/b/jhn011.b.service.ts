module jhn011.b.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getAll: "ctx/pereg/person/maintenance/findAll",
        getDetails: "ctx/pereg/person/maintenance/findOne/{0}",
        saveData: "ctx/pereg/person/maintenance/saveLayout"
    };
    

    /**
    * Get list Maintenance Layout
    */
    export function getAll() {
        return ajax("com", paths.getAll);
    }

    export function getDetails(lid) {
        return ajax("com", format(paths.getDetails, lid));
    }

   /**
    * add  Maintenance Layout
    */
    export function saveData(data: any) {
        return ajax("com", paths.saveData, data);
    }
}