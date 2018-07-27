module nts.uk.at.view.kdr001.a {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                findAll: "at/function/holidaysremaining/findAll",
                saveAsExcel: "at/function/holidaysremaining/report",
                getDate: "at/function/holidaysremaining/getDate",
                getPermissionOfEmploymentForm: "at/function/holidaysremaining/getPermissionOfEmploymentForm"
            };
        
        
        export function findAll() : JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAll);
        }
        
        export function saveAsExcel(data){
            return nts.uk.request.exportFile("at", path.saveAsExcel, data);
        }
        
        export function getDate() : JQueryPromise<any>{
            return nts.uk.request.ajax("at", path.getDate);
        }
        
        export function getPermissionOfEmploymentForm() : JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.getPermissionOfEmploymentForm);
        }
    }
}