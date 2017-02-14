module cmm014.a.service {
    var paths = {
        getAllClassification: "pr/basic/organization/findAllClassification",
        addClassification: "pr/basic/organization/add",
        updateClassification: "pr/basic/organization/update",
        removeClassification: "pr/basic/organization/remove"
    }

    /**
     * Get list classification
     */
    
    export function getAllClassification(): JQueryPromise<Array<viewmodel.model.ClassificationDto>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getAllClassification)
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
        nts.uk.request.ajax(paths.addClassification, classification).done()
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
        nts.uk.request.ajax(paths.updateClassification, classification).done()
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
        nts.uk.request.ajax(paths.removeClassification, classification).done()
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }


}
