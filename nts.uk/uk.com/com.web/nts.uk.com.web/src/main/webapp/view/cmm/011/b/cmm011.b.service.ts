module nts.uk.com.view.cmm011.b {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            getListWkpConfigHistory: "bs/employee/workplace/configure/findAll",
            saveWkpConfig: "bs/employee/workplace/configure/save"
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
        * Model namespace.
        */
        export module model {
            
        }
    }
}
