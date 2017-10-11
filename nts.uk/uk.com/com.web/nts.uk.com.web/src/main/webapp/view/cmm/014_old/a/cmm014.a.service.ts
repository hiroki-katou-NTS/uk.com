module nts.uk.com.view.cmm014_old.a.service {
        
    /**
     *  Service paths
     */
    var servicePath: any = {
        findAllClassification: "basic/company/organization/classification/findAll",
        saveClassification: "basic/company/organization/classification/save",
        removeClassification: "basic/company/organization/classification/remove"
    }

    /**
     * Get list classification
     */
    export function findAllClassification(): JQueryPromise<Array<model.ClassificationFindDto>> {
        return nts.uk.request.ajax('com', servicePath.findAllClassification);
    }

    /**
    * update Classification
    */
    export function addClassification(classification: model.ClassificationFindDto) {
        return nts.uk.request.ajax('com', servicePath.saveClassification, classification);
    }

    /**
     * update Classification
     */
    export function updateClassification(classification: model.ClassificationFindDto) {
        return nts.uk.request.ajax('com', servicePath.saveClassification, classification);
    }

    /**
    * remove Classification
    */
    export function removeClassification(classification: model.RemoveClassificationCommand) {    
        return nts.uk.request.ajax('com', servicePath.removeClassification, classification);
    }  
    
    export module model {
        
        export class ClassificationFindDto {
            code: string;
            name: string;
            memo: string;
            
            constructor(code?: string, name?: string, memo?: string) {
                this.code = code;
                this.name = name;
                this.memo = memo;
            }
        }

        export class RemoveClassificationCommand {
            code: string;
            
            constructor(code: string) {
                this.code = code;
            }
        }
    }    
}
