var qmm018;
(function (qmm018) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getAveragePay: "pr/core/averagepay/findByCompanyCode",
                registerAveragePay: "pr/core/averagepay/register",
                updateAveragePay: "pr/core/averagepay/update",
                getItem: "pr/proto/item/findall/bycategory/{0}"
            };
            function getAveragePay() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getAveragePay)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAveragePay = getAveragePay;
            function registerAveragePay(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.registerAveragePay, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.registerAveragePay = registerAveragePay;
            function updateAveragePay(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.updateAveragePay, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateAveragePay = updateAveragePay;
            function getItem(categoryAtr) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(nts.uk.text.format(paths.getItem, categoryAtr))
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getItem = getItem;
        })(service = a.service || (a.service = {}));
    })(a = qmm018.a || (qmm018.a = {}));
})(qmm018 || (qmm018 = {}));
