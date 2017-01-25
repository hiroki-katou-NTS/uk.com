var qrm007;
(function (qrm007) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                //getRetirementPayItemList: "pr/proto/paymentdatemaster/processing/findall",
                getRetirementPayItemList: "pr/core/retirement/payitem/findBycompanyCode",
                updateRetirementPayItem: "pr/core/retirement/payitem/update"
            };
            function getRetirementPayItemList() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getRetirementPayItemList)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getRetirementPayItemList = getRetirementPayItemList;
            function updateRetirementPayItem(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.updateRetirementPayItem, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateRetirementPayItem = updateRetirementPayItem;
        })(service = a.service || (a.service = {}));
    })(a = qrm007.a || (qrm007.a = {}));
})(qrm007 || (qrm007 = {}));
