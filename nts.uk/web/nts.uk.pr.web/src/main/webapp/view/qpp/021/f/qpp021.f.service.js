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
                    var f;
                    (function (f) {
                        var service;
                        (function (service) {
                            var paths = {
                                findRefundPadding: "ctx/pr/report/payment/refundsetting/refundpadding/printtype/two/find",
                                saveRefundPadding: "ctx/pr/report/payment/refundsetting/refundpadding/printtype/two/save"
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
                                var RefundPaddingTwoDto = (function () {
                                    function RefundPaddingTwoDto() {
                                    }
                                    return RefundPaddingTwoDto;
                                }());
                                model.RefundPaddingTwoDto = RefundPaddingTwoDto;
                            })(model = service.model || (service.model = {}));
                        })(service = f.service || (f.service = {}));
                    })(f = qpp021.f || (qpp021.f = {}));
                })(qpp021 = view.qpp021 || (view.qpp021 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp021.f.service.js.map