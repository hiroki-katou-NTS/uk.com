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
                    var q;
                    (function (q) {
                        var service;
                        (function (service) {
                            var paths = {
                                findLastestFormulaManual: "pr/formula/formulaMaster/findLastestFormulaManual/"
                            };
                            function findLastestFormulaManual(formulaCode) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax("pr", paths.findLastestFormulaManual + formulaCode)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findLastestFormulaManual = findLastestFormulaManual;
                        })(service = q.service || (q.service = {}));
                        var model;
                        (function (model) {
                            var FormulaManualDto = (function () {
                                function FormulaManualDto() {
                                }
                                return FormulaManualDto;
                            }());
                            model.FormulaManualDto = FormulaManualDto;
                        })(model = q.model || (q.model = {}));
                    })(q = qmm017.q || (qmm017.q = {}));
                })(qmm017 = view.qmm017 || (view.qmm017 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
