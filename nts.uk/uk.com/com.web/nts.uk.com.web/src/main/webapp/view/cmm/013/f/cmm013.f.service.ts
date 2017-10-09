module nts.uk.com.view.cmm013.f {
       
    import SequenceMasterSaveCommand = base.SequenceMasterSaveCommand;
    
    export module service {
        
        /**
         *  Service paths
         */
        var servicePath: any = {
            findAllSequenceMaster: "bs/employee/jobtitle/sequence/findAll",
            findBySequenceCode: "bs/employee/jobtitle/sequence/find",
            saveSequenceMaster: "bs/employee/jobtitle/sequence/save",
        };
        
        export function findAllSequenceMaster(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findAllSequenceMaster);
        }
        
        export function findBySequenceCode(sequenceCode: string): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.findBySequenceCode, {sequenceCode: sequenceCode});
        }
        
        export function saveSequenceMaster(command: SequenceMasterSaveCommand): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveSequenceMaster, command);
        }
                
        /**
        * Model namespace.
        */
        export module model {
            
        }
    }
}
