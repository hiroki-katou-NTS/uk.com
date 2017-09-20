module nts.uk.com.view.cmm011.b {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            getListWkpConfigHistory: "bs/employee/workplace/configHist",
            registerWkpConfig: "bs/employee/workplace/registerConfig"
        };
        //get list history
        export function findLstWkpConfigHistory(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getListWkpConfigHistory);
        }
        //register history
        export function registerWkpConfig(data): JQueryPromise<void>{
            return nts.uk.request.ajax(servicePath.registerWkpConfig,data);
        }
         
        /**
        * Model namespace.
        */
        export module model {
            
        }
    }
}
