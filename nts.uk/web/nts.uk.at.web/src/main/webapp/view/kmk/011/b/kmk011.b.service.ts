module kmk011.b.service {
     var paths = {
        getAllDivReason: "at/record/divreason/getalldivreason/"
    }

    /**
    * get all history
    */
    export function getAllDivReason(divTimeId: string): JQueryPromise<Array<viewmodel.model.Item>> {
        var dfd = $.Deferred<Array<viewmodel.model.Item>>();
        nts.uk.request.ajax("at", paths.getAllDivReason,divTimeId).done(function(res: Array<viewmodel.model.Item>) {
            dfd.resolve(res);
        }).fail(function(res) {
            dfd.reject(res);
        })
        return dfd.promise();
    }   
}