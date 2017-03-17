var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var service;
        (function (service) {
            var paths = {
                getAllFormula: "pr/formula/formulaMaster/getAllFormula",
                findFormulaByCode: "pr/formula/formulaMaster/findFormula",
                findFormulaHistoryByCode: "pr/formula/formulaHistory/findFormulaHistoryByCode"
            };
            function getAllFormula() {
                var dfd = $.Deferred();
                nts.uk.request.ajax("pr", paths.getAllFormula)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getAllFormula = getAllFormula;
            function findFormulaByCode(formulaCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("pr", paths.findFormulaByCode + "/" + formulaCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findFormulaByCode = findFormulaByCode;
            function findFormulaHistoryByCode(formulaCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("pr", paths.findFormulaHistoryByCode + "/" + formulaCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findFormulaHistoryByCode = findFormulaHistoryByCode;
        })(service = qmm017.service || (qmm017.service = {}));
        var model;
        (function (model) {
            var FormulaDto = (function () {
                function FormulaDto() {
                }
                return FormulaDto;
            }());
            model.FormulaDto = FormulaDto;
            var FormulaHistoryDto = (function () {
                function FormulaHistoryDto() {
                }
                return FormulaHistoryDto;
            }());
            model.FormulaHistoryDto = FormulaHistoryDto;
        })(model = qmm017.model || (qmm017.model = {}));
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
