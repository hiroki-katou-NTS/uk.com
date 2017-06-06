module kdl007.a.service {
    var paths = {
        getPayRiseItem: ""
    }
    /**
    * get all item pay rise
    */
    export function getAllItem(): JQueryPromise<Array<viewmodel.model.ItemModel>> {
        return nts.uk.request.ajax("at", paths.getPayRiseItem);
    }
}