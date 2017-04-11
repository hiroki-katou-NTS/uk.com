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
                    var k;
                    (function (k) {
                        var service;
                        (function (service) {
                            var paths = {
                                updateFormulaHistory: "pr/formula/formulaHistory/updateHistory",
                                removeFormulaHistory: "pr/formula/formulaHistory/removeHistory",
                            };
                            function updateFormulaHistory(command) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.updateFormulaHistory, command).done(function () {
                                    dfd.resolve();
                                }).fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.updateFormulaHistory = updateFormulaHistory;
                            function removeFormulaHistory(command) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.removeFormulaHistory, command).done(function () {
                                    dfd.resolve();
                                }).fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.removeFormulaHistory = removeFormulaHistory;
                        })(service = k.service || (k.service = {}));
                    })(k = qmm017.k || (qmm017.k = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
