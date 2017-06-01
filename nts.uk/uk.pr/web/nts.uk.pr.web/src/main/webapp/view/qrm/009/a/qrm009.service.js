var qrm009;
(function (qrm009) {
    var service;
    (function (service) {
        var paths = {
            exportRetirementPaymentPDF: "/screen/pr/qrm009/saveAsPdf"
        };
        function exportRetirementPaymentPDF(query) {
            var dfd = $.Deferred();
            nts.uk.request.exportFile(paths.exportRetirementPaymentPDF, query)
                .done(function (res) {
                dfd.resolve(res);
            })
                .fail(function (res) {
                dfd.reject(res);
            });
            return dfd.promise();
        }
        service.exportRetirementPaymentPDF = exportRetirementPaymentPDF;
    })(service = qrm009.service || (qrm009.service = {}));
})(qrm009 || (qrm009 = {}));
//# sourceMappingURL=qrm009.service.js.map