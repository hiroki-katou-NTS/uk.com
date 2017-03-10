var qet002;
(function (qet002) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var servicePath = {
                printService: "/screen/pr/qet002/generate",
            };
            function printService(query) {
                var dfd = $.Deferred();
                nts.uk.request.exportFile(servicePath.printService, query).done(function () {
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
