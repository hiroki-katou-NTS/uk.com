module cmm014.a.service {
        
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
    export function findAllClassification(): JQueryPromise<Array<viewmodel.model.ClassificationModel>> {
        return nts.uk.request.ajax('com', servicePath.findAllClassification);
    }

    /**
    * update Classification
    */
    export function addClassification(classification: viewmodel.model.ClassificationModel) {
        return nts.uk.request.ajax('com', servicePath.saveClassification, classification);
    }

    /**
     * update Classification
     */
    export function updateClassification(classification: viewmodel.model.ClassificationModel) {
        return nts.uk.request.ajax('com', servicePath.saveClassification, classification);
    }

    /**
    * remove Classification
    */
    export function removeClassification(classification: viewmodel.model.RemoveClassificationCommand) {    
        return nts.uk.request.ajax('com', servicePath.removeClassification, classification);
    }  
}
