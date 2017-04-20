var qet002;
(function (qet002) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var servicePath = {
                printService: "/screen/pr/qet002/generate",
            };
            // Print Report Service
            function printService(query) {
                var dfd = $.Deferred();
                var accquery = {
                    targetYear: query.targetYear(),
                    isLowerLimit: query.isLowerLimit(),
                    lowerLimitValue: query.lowerLimitValue(),
                    isUpperLimit: query.isUpperLimit(),
                    upperLimitValue: query.upperLimitValue()
                };
                nts.uk.request.exportFile(servicePath.printService, accquery).done(function () {
                    dfd.resolve();
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.printService = printService;
        })(service = a.service || (a.service = {}));
    })(a = qet002.a || (qet002.a = {}));
})(qet002 || (qet002 = {}));
