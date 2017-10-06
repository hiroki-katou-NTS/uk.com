module nts.uk.com.view.cmm013.c {
    
    export module service {
        
        /**
         *  Service paths
         */
        var servicePath: any = {
            findAllSequenceMaster: "bs/employee/jobtitle/sequence/findAll",
        };
           
        export function findAllSequenceMaster(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAllSequenceMaster);
        }
        
        /**
        * Model namespace.
        */
        export module model {
            
        }
    }
}
