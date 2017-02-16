module qmm018.b.service {
    var paths: any = {
        getItemList: "pr/proto/item/findall/bycategory/0"
    }
    
    export function getItemList(): JQueryPromise<Array<any>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getItemList)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}