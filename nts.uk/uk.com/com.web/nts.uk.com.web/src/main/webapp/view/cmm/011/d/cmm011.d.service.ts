module nts.uk.com.view.cmm011.d {
    export module service {
        /**
         *  Service paths
         */
        let servicePath: any = {
            saveWorkplaceHistory: "bs/employee/workplace/history/save",
        };
        
        /**
         * saveWorkplaceHistory
         */
        export function saveWorkplaceHistory(command: any): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.saveWorkplaceHistory, command);
        }
        
        /**
        * Model namespace.
        */
        export module model {
            
        }
    }
}
