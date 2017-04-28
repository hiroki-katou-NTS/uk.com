var qrm007;
(function (qrm007) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                retirePayItemSelect: "pr/core/retirement/payitem/findBycompanyCode",
                retirePayItemUpdate: "pr/core/retirement/payitem/update" //qremt_Retire_Pay_Item_UPD_1
            };
            // qremt_Retire_Pay_Item_SEL_1 function
            function retirePayItemSelect() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.retirePayItemSelect)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.retirePayItemSelect = retirePayItemSelect;
            // qremt_Retire_Pay_Item_UPD_1 function
            function retirePayItemUpdate(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.retirePayItemUpdate, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.retirePayItemUpdate = retirePayItemUpdate;
        })(service = a.service || (a.service = {}));
    })(a = qrm007.a || (qrm007.a = {}));
})(qrm007 || (qrm007 = {}));
