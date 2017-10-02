module nts.uk.com.view.cmm011.d {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            addWorkplaceHistory: "bs/employee/workplace/register/history",
        };
        
        export function registerWorkplaceHistory(data:any): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.addWorkplaceHistory,data);
        }
        
        /**
        * Model namespace.
        */
        export module model {
            
        }
    }
}
