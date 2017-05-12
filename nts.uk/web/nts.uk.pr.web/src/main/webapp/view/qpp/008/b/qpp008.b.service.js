var qpp008;
(function (qpp008) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var paths = {
                getComparingPrintSet: "report/payment/comparing/setting/find",
                inserUpdatetData: "report/payment/comparing/setting/insertUpdateData",
                deleteData: "report/payment/comparing/setting/deleteData",
            };
            function getComparingPrintSet() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getComparingPrintSet).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (error) {
                    dfd.reject(error);
                });
                return dfd.promise();
            }
            service.getComparingPrintSet = getComparingPrintSet;
            function insertUpdateData(inserUpdateData) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.inserUpdatetData, inserUpdateData).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (error) {
                    dfd.reject(error);
                });
                return dfd.promise();
            }
            service.insertUpdateData = insertUpdateData;
            function deleteData() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.deleteData).done(function (res) {
                    dfd.resolve(res);
                }).fail(function (error) {
                    dfd.reject(error);
                });
                return dfd.promise();
            }
            service.deleteData = deleteData;
        })(service = b.service || (b.service = {}));
    })(b = qpp008.b || (qpp008.b = {}));
})(qpp008 || (qpp008 = {}));
//# sourceMappingURL=qpp008.b.service.js.map