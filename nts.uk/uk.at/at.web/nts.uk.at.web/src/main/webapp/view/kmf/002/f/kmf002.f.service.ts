module nts.uk.at.view.kmf002.f {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            save: "bs/employee/publicholidaymanagementusageunit/save",
            findAll: "bs/employee/publicholidaymanagementusageunit/find",            
        };
        
        export function save(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.save);
        }
        
        export function findAll(): JQueryPromise<any> {
            return nts.uk.request.ajax("com", path.findAll);
        }  
    }
}