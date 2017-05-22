var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp021;
                (function (qpp021) {
                    var b;
                    (function (b) {
                        var service;
                        (function (service) {
                            var paths = {
                                printService: "/file/paymentdata/print",
                                paymentReportPrint: "screen/pr/QPP021/saveAsPdf"
                            };
                            function printB(query) {
                                var dfd = $.Deferred();
                                dfd.resolve();
                                return dfd.promise();
                            }
                            service.printB = printB;
                            function paymentReportPrint(query) {
                                return nts.uk.request.exportFile(paths.paymentReportPrint, query);
                            }
                            service.paymentReportPrint = paymentReportPrint;
                            var model;
                            (function (model) {
                                var PaymentReportQueryDto = (function () {
                                    function PaymentReportQueryDto() {
                                    }
                                    return PaymentReportQueryDto;
                                }());
                                model.PaymentReportQueryDto = PaymentReportQueryDto;
                            })(model = service.model || (service.model = {}));
                        })(service = b.service || (b.service = {}));
                    })(b = qpp021.b || (qpp021.b = {}));
                })(qpp021 = view.qpp021 || (view.qpp021 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp021.b.service.js.map