module qmm018.b.service {
    var paths: any = {
        getItemList: "pr/proto/item/findall/bycategory/{0}"
    }
    
    export function getItemList(categoryAtr): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(nts.uk.text.format(paths.getItemList, categoryAtr))
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}