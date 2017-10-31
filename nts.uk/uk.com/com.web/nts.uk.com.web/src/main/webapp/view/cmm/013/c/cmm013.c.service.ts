module nts.uk.com.view.cmm013.c {
    
    export module service {
        
        /**
         *  Service paths
         */
        var servicePath: any = {
            findAllSequenceMaster: "bs/employee/jobtitle/sequence/findAll",
            findBySequenceCode: "bs/employee/jobtitle/sequence/find",
        };
        
        /**
         * findAllSequenceMaster
         */   
        export function findAllSequenceMaster(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAllSequenceMaster);
        }
        
        /**
         * findBySequenceCode
         */
        export function findBySequenceCode(sequenceCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findBySequenceCode, {sequenceCode: sequenceCode});
        }
        
        /**
        * Model namespace.
        */
        export module model {
            
        }
    }
}
