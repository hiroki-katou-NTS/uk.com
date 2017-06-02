var qpp011;
(function (qpp011) {
    var f;
    (function (f) {
        var service;
        (function (service) {
            var paths = {
                findAllResidential: "pr/core/residential/findallresidential",
                getlistLocation: "pr/core/residential/getlistLocation",
                saveAsPdfB: "screen/pr/QPP011/saveAsPdfB",
                saveAsPdfC: "screen/pr/QPP011/saveAsPdfC",
                saveText: "screen/pr/QPP011/savePaymentData"
            };
            /**
             * Get list payment date processing.
             */
            function findAllResidential() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findAllResidential)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findAllResidential = findAllResidential;
            function getlistLocation() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.getlistLocation)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getlistLocation = getlistLocation;
            function saveAsPdf(check, command) {
                if (check == 1) {
                    var path = paths.saveAsPdfB;
                }
                else {
                    var path = paths.saveAsPdfC;
                }
                return nts.uk.request.exportFile(path, command);
            }
            service.saveAsPdf = saveAsPdf;
            function saveText(command) {
                var path = paths.saveText;
                return nts.uk.request.exportFile(path, command);
            }
            service.saveText = saveText;
        })(service = f.service || (f.service = {}));
    })(f = qpp011.f || (qpp011.f = {}));
})(qpp011 || (qpp011 = {}));
//# sourceMappingURL=qpp011.f.service.js.map