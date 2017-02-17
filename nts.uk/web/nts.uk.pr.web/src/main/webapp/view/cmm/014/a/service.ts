module cmm014.a.service {
    var paths = {
        getAllClassification: "basic/organization/findAllClassification",
        addClassification: "basic/organization/add",
        updateClassification: "basic/organization/update",
        removeClassification: "basic/organization/remove"
    }

    /**
     * Get list classification
     */
    
    export function getAllClassification(): JQueryPromise<Array<viewmodel.model.ClassificationDto>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.getAllClassification)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }

    /**
    * update Classification
    */

    export function addClassification(classification: viewmodel.model.ClassificationDto) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.addClassification, classification).done()
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }


    /**
     * update Classification
     */

    export function updateClassification(classification: viewmodel.model.ClassificationDto) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.updateClassification, classification).done()
            .fail(function(res) {
                dfd.reject(res);
            })

        return dfd.promise();
    }

    /**
    * remove Classification
    */

    export function removeClassification(classification: viewmodel.model.RemoveClassificationCommand) {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax("com",paths.removeClassification, classification).done()
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }


}
