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
                    var g;
                    (function (g) {
                        var service;
                        (function (service) {
                            var paths = {
                                findRefundPadding: "ctx/pr/report/payment/refundsetting/refundpadding/printtype/three/find",
                                saveRefundPadding: "ctx/pr/report/payment/refundsetting/refundpadding/printtype/three/save"
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
                                var RefundPaddingThreeDto = (function () {
                                    function RefundPaddingThreeDto() {
                                    }
                                    return RefundPaddingThreeDto;
                                }());
                                model.RefundPaddingThreeDto = RefundPaddingThreeDto;
                            })(model = service.model || (service.model = {}));
                        })(service = g.service || (g.service = {}));
                    })(g = qpp021.g || (qpp021.g = {}));
                })(qpp021 = view.qpp021 || (view.qpp021 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp021.g.service.js.map