var qpp021;
(function (qpp021) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                getRefundLayout: "report/payment/refundsetting/refundlayout/find/{0}",
                insertUpdateData: "report/payment/refundsetting/refundlayout/insertUpdateData",
            };
            function getRefundLayout(printType) {
                var dfd = $.Deferred();
                var _path = nts.uk.text.format(paths.getRefundLayout, printType);
                nts.uk.request.ajax(_path).done(function (data) {
                    dfd.resolve(data);
                }).fail(function (error) {
                    dfd.reject(error);
                });
                return dfd.promise();
            }
            service.getRefundLayout = getRefundLayout;
            function insertUpdateData(data) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.insertUpdateData, ko.toJS(data)).done(function () {
                    dfd.resolve();
                }).fail(function (error) {
                    dfd.reject(error);
                });
                return dfd.promise();
            }
            service.insertUpdateData = insertUpdateData;
        })(service = d.service || (d.service = {}));
    })(d = qpp021.d || (qpp021.d = {}));
})(qpp021 || (qpp021 = {}));
//# sourceMappingURL=qpp021.d.service.js.map