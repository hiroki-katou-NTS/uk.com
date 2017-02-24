module qmm018.b.service {
    var paths: any = {
        qcamt_Item_SEL_3: "pr/proto/item/findall/bycategory/{0}"
    }
    
    export function qcamt_Item_SEL_3(categoryAtr): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(nts.uk.text.format(paths.qcamt_Item_SEL_3, categoryAtr))
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}