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
                                findLastestFormulaManual: "pr/formula/formulaMaster/findLastestFormulaManual/",
                                trialCalculate: "pr/formula/formulaMaster/trialCalculate/"
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
                            function trialCalculate(formulaContent) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax("pr", paths.trialCalculate, formulaContent)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.trialCalculate = trialCalculate;
                        })(service = q.service || (q.service = {}));
                        var model;
                        (function (model) {
                            var CalculatorDto = (function () {
                                function CalculatorDto() {
                                }
                                return CalculatorDto;
                            }());
                            model.CalculatorDto = CalculatorDto;
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
//# sourceMappingURL=qmm017.q.service.js.map