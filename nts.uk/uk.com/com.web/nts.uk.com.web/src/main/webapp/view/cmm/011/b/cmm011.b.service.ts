module nts.uk.com.view.cmm011.b {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            getListWkpConfigHistory: "bs/employee/workplace/config/findAll",
            saveWkpConfig: "bs/employee/workplace/config/save",
            removeWkpConfig: "bs/employee/workplace/config/remove",
        };
        
        /**
         * findLstWkpConfigHistory
         */
        export function findLstWkpConfigHistory(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getListWkpConfigHistory);
        }
        
        /**
         * saveWkpConfig
         */
        export function saveWkpConfig(command: any): JQueryPromise<void>{
            return nts.uk.request.ajax(servicePath.saveWkpConfig, command);
        }
        
        /**
         * removeWkpConfig
         */
        export function removeWkpConfig(command: any): JQueryPromise<void>{
            return nts.uk.request.ajax(servicePath.removeWkpConfig, command);
        }
         
        /**
        * Model namespace.
        */
        export module model {
            
        }
    }
}
