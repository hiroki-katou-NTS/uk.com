var qmm025;
(function (qmm025) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                findAll: "pr/core/rule/law/tax/residential/input/findAll",
                getYearKey: "pr/proto/paymentdatemaster/processing/findbylogin",
                remove: "pr/core/rule/law/tax/residential/input/remove"
            };
            function findAll(yearKey) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findAll + "/" + yearKey)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findAll = findAll;
            function getYearKey() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getYearKey)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getYearKey = getYearKey;
            function remove(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.remove, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.remove = remove;
        })(service = a.service || (a.service = {}));
    })(a = qmm025.a || (qmm025.a = {}));
})(qmm025 || (qmm025 = {}));
