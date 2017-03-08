var qrm007;
(function (qrm007) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                qremt_Retire_Pay_Item_SEL_1: "pr/core/retirement/payitem/findBycompanyCode",
                qremt_Retire_Pay_Item_UPD_1: "pr/core/retirement/payitem/update"
            };
            function qremt_Retire_Pay_Item_SEL_1() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.qremt_Retire_Pay_Item_SEL_1)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.qremt_Retire_Pay_Item_SEL_1 = qremt_Retire_Pay_Item_SEL_1;
            function qremt_Retire_Pay_Item_UPD_1(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.qremt_Retire_Pay_Item_UPD_1, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.qremt_Retire_Pay_Item_UPD_1 = qremt_Retire_Pay_Item_UPD_1;
        })(service = a.service || (a.service = {}));
    })(a = qrm007.a || (qrm007.a = {}));
})(qrm007 || (qrm007 = {}));
