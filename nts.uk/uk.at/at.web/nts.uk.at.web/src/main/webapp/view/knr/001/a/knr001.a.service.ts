module nts.uk.at.view.knr001.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;

    let paths: any = {
        getAll: "screen/at/empInfoTerminal/getAll",
        getDetails: "screen/at/empInfoTerminal/getDetails",
        register: "at/record/empinfoterminal/register",
        update: "at/record/empinfoterminal/update",
        delete: "at/record/empinfoterminal/delete",
        knrExport: "file/empInfoTerminal/report/export",
        getModel: "screen/at/empInfoTerminal/getModel"
    };

    /**
    * Get All
    */
    export function getAll(): JQueryPromise<any> {
        return ajax(paths.getAll);
    }

    /**
    * Get Details
    */
    export function getDetails(empInfoTerCode: any): JQueryPromise<any> {
        return ajax(paths.getDetails+ "/" + empInfoTerCode);
    }

   /**
    * Register
    */
    export function register(data: any): JQueryPromise<any> {
        return ajax(paths.register, data);
    }

    /**
    * Update
    */
    export function update(data: any): JQueryPromise<any> {
        return ajax(paths.update, data);
    }
    /**
    * Remove
    */
    export function removeEmpInfoTer(code): JQueryPromise<any> {
        return ajax(format(paths.getDetails, code));
    }

    /**
    * Save As Excel
    */    
    export function knrExport(): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.knrExport);
    };
    /**
     * Get 機種
     */
    export function getModel(data: any): JQueryPromise<any> {
        return ajax(paths.getModel, data);
    }

}