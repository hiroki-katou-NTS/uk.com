var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm002;
                (function (qmm002) {
                    var b;
                    (function (b) {
                        var service;
                        (function (service) {
                            var paths = {
                                removeListBank: "basic/system/bank/remove/list"
                            };
                            function removeBank(data) {
                                var path = paths.removeListBank;
                                return nts.uk.request.ajax("com", path, data);
                            }
                            service.removeBank = removeBank;
                        })(service = b.service || (b.service = {}));
                    })(b = qmm002.b || (qmm002.b = {}));
                })(qmm002 = view.qmm002 || (view.qmm002 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm002.b.service.js.map