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
                    var e;
                    (function (e) {
                        var service;
                        (function (service) {
                            var paths = {
                                findRefundPadding: "ctx/pr/report/payment/refundsetting/refundpadding/printtype/once/find",
                                saveRefundPadding: "ctx/pr/report/payment/refundsetting/refundpadding/printtype/once/save"
                            };
                            function findRefundPadding() {
                                return nts.uk.request.ajax(paths.findRefundPadding);
                            }
                            service.findRefundPadding = findRefundPadding;
                            function saveRefundPadding(dto) {
                                var data = { dto: dto };
                                return nts.uk.request.ajax(paths.saveRefundPadding, data);
                            }
                            service.saveRefundPadding = saveRefundPadding;
                            var model;
                            (function (model) {
                                var RefundPaddingOnceDto = (function () {
                                    function RefundPaddingOnceDto() {
                                    }
                                    return RefundPaddingOnceDto;
                                }());
                                model.RefundPaddingOnceDto = RefundPaddingOnceDto;
                            })(model = service.model || (service.model = {}));
                        })(service = e.service || (e.service = {}));
                    })(e = qpp021.e || (qpp021.e = {}));
                })(qpp021 = view.qpp021 || (view.qpp021 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp021.e.service.js.map