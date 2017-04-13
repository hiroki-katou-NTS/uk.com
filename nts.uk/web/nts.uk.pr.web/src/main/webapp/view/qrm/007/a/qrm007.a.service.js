var qrm007;
(function (qrm007) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                retirePayItemSelect: "pr/core/retirement/payitem/findBycompanyCode",
                retirePayItemUpdate: "pr/core/retirement/payitem/update"
            };
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
