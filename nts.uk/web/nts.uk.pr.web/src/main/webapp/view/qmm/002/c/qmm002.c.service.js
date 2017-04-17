var qmm002;
(function (qmm002) {
    var c;
    (function (c) {
        var service;
        (function (service) {
            var paths = {
                transfer: "basic/system/bank/branch/transfer",
                getBankList: "basic/system/bank/find/all"
            };
            function tranferBranch(data) {
                var dfd = $.Deferred();
                nts.uk.request.ajax('com', paths.transfer, data)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.tranferBranch = tranferBranch;
            function getBankList() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("com", paths.getBankList)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getBankList = getBankList;
        })(service = c.service || (c.service = {}));
    })(c = qmm002.c || (qmm002.c = {}));
})(qmm002 || (qmm002 = {}));
//# sourceMappingURL=qmm002.c.service.js.map