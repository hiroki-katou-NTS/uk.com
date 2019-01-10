module cps008.a.service {
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

    
    //saveAsExcel
        export function saveAsExcel(languageId: string): JQueryPromise<any> {
            return nts.uk.request.exportFile('/masterlist/report/print', {domainId: "Maintenance", languageId: languageId, domainType: "CPS008"+ __viewContext.program.programName, reportType: 0});
        }


}