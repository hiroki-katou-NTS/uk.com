module qmm018.a.service {
    var paths: any = {
        qapmt_Ave_Pay_SEL_1: "pr/core/averagepay/findByCompanyCode",
        qapmt_Ave_Pay_INS_1: "pr/core/averagepay/register",
        qapmt_Ave_Pay_UPD_1: "pr/core/averagepay/update",
        qcamt_Item_SEL_5: "pr/proto/item/findall/avepay/time",
        qcamt_Item_Salary_SEL_2: "",
        qcamt_Item_Salary_SEL_3: "",
        qcamt_Item_Salary_SEL_4: "",
        qcamt_Item_Salary_UPD_2: "",
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
    
    export function qcamt_Item_SEL_5(categoryAtr, itemCode): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(nts.uk.text.format(paths.qcamt_Item_SEL_5, categoryAtr, itemCode))
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function qcamt_Item_Salary_SEL_2(itemCode): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(nts.uk.text.format(paths.qcamt_Item_Salary_SEL_2, itemCode))
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function qcamt_Item_Salary_SEL_3(): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.qcamt_Item_Salary_SEL_3)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export function qcamt_Item_Salary_SEL_4(): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(paths.qcamt_Item_Salary_SEL_4)
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    
    
    export function qcamt_Item_Salary_UPD_2(itemCode): JQueryPromise<any> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax(nts.uk.text.format(paths.qcamt_Item_Salary_UPD_2, itemCode))
            .done(function(res: any) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
}