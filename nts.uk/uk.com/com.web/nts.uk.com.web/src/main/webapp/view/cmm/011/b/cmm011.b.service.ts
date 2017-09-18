module nts.uk.com.view.cmm011.b {
    export module service {
        /**
         *  Service paths
         */
        var servicePath: any = {
            getListWkpConfigHistory: "bs/employee/workplace/configHist"
        };
        
        export function findLstWkpConfigHistory(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getListWkpConfigHistory);
        }
        
        /**
        * Model namespace.
        */
        export module model {
            
        }
    }
}
