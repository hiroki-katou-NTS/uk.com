module nts.uk.com.view.cmm013.d {
    
    export module service {
        
        /**
         *  Service paths
         */
        var servicePath: any = {
            saveJobTitleHistory: "bs/employee/jobtitle/history/save",
        };
                
        /**
         * saveWorkplaceHistory
         */
        export function saveJobTitleHistory(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveJobTitleHistory, command);
        }
        
        /**
        * Model namespace.
        */
        export module model {
            
        }
    }
}
