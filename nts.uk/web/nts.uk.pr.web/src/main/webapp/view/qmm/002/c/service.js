var qmm002;
(function (qmm002) {
    var c;
    (function (c) {
        var service;
        (function (service) {
            var paths = {
                tranfer: "basic/system/bank/branch/tranfer",
                getBankList: "basic/system/bank/find/all"
            };
            function tranferBranch(data) {
                var dfd = $.Deferred();
                nts.uk.request.ajax('com', paths.tranfer, data)
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
//# sourceMappingURL=service.js.map