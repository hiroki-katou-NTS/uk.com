var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm007;
                (function (qmm007) {
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var paths = {
                                getPaymentDateProcessingList: "pr/proto/paymentdatemaster/processing/findall"
                            };
                            function getPaymentDateProcessingList() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.getPaymentDateProcessingList)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.getPaymentDateProcessingList = getPaymentDateProcessingList;
                        })(service = a.service || (a.service = {}));
                    })(a = qmm007.a || (qmm007.a = {}));
                })(qmm007 = view.qmm007 || (view.qmm007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
