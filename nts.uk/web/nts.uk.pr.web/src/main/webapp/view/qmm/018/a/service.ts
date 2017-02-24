module qmm018.a.service {
    var paths: any = {
        qapmt_Ave_Pay_SEL_1: "pr/core/averagepay/findByCompanyCode",
        qapmt_Ave_Pay_INS_1: "pr/core/averagepay/register",
        qapmt_Ave_Pay_UPD_1: "pr/core/averagepay/update",
        qcamt_Item_SEL_4: "pr/proto/item/findall/avepay/time",
        qcamt_Item_SEL_8: "pr/proto/item/find/{0}/{1}",
        qcamt_Item_UPD_2: "pr/proto/item/findall/avepay/time"
    }
    
    export function qapmt_Ave_Pay_SEL_1(): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.qapmt_Ave_Pay_SEL_1)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function qapmt_Ave_Pay_INS_1(command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.qapmt_Ave_Pay_INS_1, command)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function qapmt_Ave_Pay_UPD_1(command): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.qapmt_Ave_Pay_UPD_1, command)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function qcamt_Item_SEL_4(): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.qcamt_Item_SEL_4)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function qcamt_Item_SEL_8(categoryAtr, itemCode): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(nts.uk.text.format(paths.qcamt_Item_SEL_8, categoryAtr, itemCode))
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function qcamt_Item_UPD_2(categoryAtr, itemCode): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(nts.uk.text.format(paths.qcamt_Item_UPD_2, categoryAtr, itemCode))
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}