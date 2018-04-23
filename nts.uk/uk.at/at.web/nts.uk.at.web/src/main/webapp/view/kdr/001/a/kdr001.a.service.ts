module nts.uk.at.view.kdr001.a {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                findAll: "at/function/holidaysremaining/findAll",
                saveAsExcel: "at/function/holidaysremaining/employee",
                getDate: "at/function/holidaysremaining/getDate"
            };
        
        
        export function findAll(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAll);
        }
        
        export function saveAsExcel(data) {
            return nts.uk.request.exportFile("at", path.saveAsExcel, data);
        }
        
        export function getDate() {
            return nts.uk.request.exportFile("at", path.getDate);
        }
    }
}