var qmm018;
(function (qmm018) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                qapmt_Ave_Pay_SEL_1: "pr/core/averagepay/findByCompanyCode",
                qapmt_Ave_Pay_INS_1: "pr/core/averagepay/register",
                qapmt_Ave_Pay_UPD_1: "pr/core/averagepay/update",
                qcamt_Item_SEL_4: "pr/proto/item/findall/avepay/time",
                qcamt_Item_SEL_8: "pr/proto/item/find/{0}/{1}",
                qcamt_Item_UPD_2: "pr/proto/item/findall/avepay/time"
            };
            function qapmt_Ave_Pay_SEL_1() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.qapmt_Ave_Pay_SEL_1)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.qapmt_Ave_Pay_SEL_1 = qapmt_Ave_Pay_SEL_1;
            function qapmt_Ave_Pay_INS_1(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.qapmt_Ave_Pay_INS_1, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.qapmt_Ave_Pay_INS_1 = qapmt_Ave_Pay_INS_1;
            function qapmt_Ave_Pay_UPD_1(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.qapmt_Ave_Pay_UPD_1, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.qapmt_Ave_Pay_UPD_1 = qapmt_Ave_Pay_UPD_1;
            function qcamt_Item_SEL_4() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.qcamt_Item_SEL_4)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.qcamt_Item_SEL_4 = qcamt_Item_SEL_4;
            function qcamt_Item_SEL_8(categoryAtr, itemCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(nts.uk.text.format(paths.qcamt_Item_SEL_8, categoryAtr, itemCode))
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.qcamt_Item_SEL_8 = qcamt_Item_SEL_8;
            function qcamt_Item_UPD_2(categoryAtr, itemCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(nts.uk.text.format(paths.qcamt_Item_UPD_2, categoryAtr, itemCode))
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.qcamt_Item_UPD_2 = qcamt_Item_UPD_2;
        })(service = a.service || (a.service = {}));
    })(a = qmm018.a || (qmm018.a = {}));
})(qmm018 || (qmm018 = {}));
