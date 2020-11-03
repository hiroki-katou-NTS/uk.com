module knr001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths = {
        getAll: "",
        getDetails: "",
        saveData: ""
    };

    /**
    * Get list Maintenance Layout
    */
    export function getAll() {
        return ajax(paths.getAll);
    }

    export function getDetails(lid) {
        return ajax(format(paths.getDetails, lid));
    }

   /**
    * add  Maintenance Layout
    */
    export function saveData(data: any) {
        return ajax(paths.saveData, data);
    }



}