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
                qcamt_Item_SEL_5: "pr/proto/item/findall/avepay/time",
                qcamt_Item_Salary_SEL_2: "",
                qcamt_Item_Salary_SEL_3: "",
                qcamt_Item_Salary_SEL_4: "",
                qcamt_Item_Salary_UPD_2: "",
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
            function qcamt_Item_SEL_5(categoryAtr, itemCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(nts.uk.text.format(paths.qcamt_Item_SEL_5, categoryAtr, itemCode))
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.qcamt_Item_SEL_5 = qcamt_Item_SEL_5;
            function qcamt_Item_Salary_SEL_2(itemCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(nts.uk.text.format(paths.qcamt_Item_Salary_SEL_2, itemCode))
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.qcamt_Item_Salary_SEL_2 = qcamt_Item_Salary_SEL_2;
            function qcamt_Item_Salary_SEL_3() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.qcamt_Item_Salary_SEL_3)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.qcamt_Item_Salary_SEL_3 = qcamt_Item_Salary_SEL_3;
            function qcamt_Item_Salary_SEL_4() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.qcamt_Item_Salary_SEL_4)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.qcamt_Item_Salary_SEL_4 = qcamt_Item_Salary_SEL_4;
            function qcamt_Item_Salary_UPD_2(itemCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(nts.uk.text.format(paths.qcamt_Item_Salary_UPD_2, itemCode))
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.qcamt_Item_Salary_UPD_2 = qcamt_Item_Salary_UPD_2;
        })(service = a.service || (a.service = {}));
    })(a = qmm018.a || (qmm018.a = {}));
})(qmm018 || (qmm018 = {}));
