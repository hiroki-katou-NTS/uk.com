var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm017;
                (function (qmm017) {
                    var j;
                    (function (j) {
                        var service;
                        (function (service) {
                            var paths = {
                                registerFormulaHistory: "pr/formula/formulaHistory/addHistory",
                            };
                            function registerFormulaHistory(command) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.registerFormulaHistory, command).done(function () {
                                    dfd.resolve();
                                }).fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.registerFormulaHistory = registerFormulaHistory;
                        })(service = j.service || (j.service = {}));
                    })(j = qmm017.j || (qmm017.j = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=service.js.map