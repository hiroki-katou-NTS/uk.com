module nts.uk.at.view.knr001.a.service {
    import ajax = nts.uk.request.ajax;

    let paths: any = {
        getAll: "screen/at/empinfoterminal/getall",
        getDetails: "screen/at/empinfoterminal/getdetails",
        getWorkLocationName: "screen/at/empinfoterminal/getworklocationname",
        register: "at/record/empinfoterminal/register",
        update: "at/record/empinfoterminal/update",
        delete: "at/record/empinfoterminal/delete",
        knrExport: "file/empinfoterminal/report/export"
    };

    /**
    * Get All
    * 就業情報端末の一覧表示を取得する
    */
    export function getAll(): JQueryPromise<any> {
        return ajax(paths.getAll);
    }

    /**
    * Get Details
    * 選択した端末の情報を取得する
    */
    export function getDetails(empInfoTerCode: any): JQueryPromise<any> {
        return ajax(paths.getDetails+ "/" + empInfoTerCode);
    }

    /**
    * Get Work Location Name
    */
    export function getworkLocationName(workLocationCD: any): JQueryPromise<any> {
        return ajax(paths.getWorkLocationName+ "/" + workLocationCD);
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
    export function removeEmpInfoTer(params: any): JQueryPromise<any> {
         return ajax(paths.delete, params);
    }

    /**
    * Save As Excel
    */    
    export function knrExport(): JQueryPromise<any> {
        return nts.uk.request.exportFile(paths.knrExport);
    };

}