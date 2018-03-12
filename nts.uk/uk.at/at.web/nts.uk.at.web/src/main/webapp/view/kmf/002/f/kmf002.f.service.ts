module nts.uk.at.view.kmf002.f {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
            save: "at/shared/publicholidaymanagementusageunit/save",
            findAll: "at/shared/publicholidaymanagementusageunit/find",            
        };
        
        export function save(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.save);
        }
        
        export function findAll(): JQueryPromise<any> {
            return nts.uk.request.ajax("at", path.findAll);
        }  
    }
}