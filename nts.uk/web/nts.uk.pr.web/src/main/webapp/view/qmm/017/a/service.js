var nts;
(function (nts) {
    var qmm017;
    (function (qmm017) {
        var service;
        (function (service) {
            var paths = {
                getAllFormula: "pr/formula/formulaMaster/getAllFormula",
                findFormula: "pr/formula/formulaMaster/findFormula",
                getFormulaDetail: "pr/formula/formulaMaster/getFormulaDetail",
                registerFormulaMaster: "pr/formula/formulaMaster/addFormulaMaster"
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
            function findFormula(formulaCode, historyId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("pr", paths.findFormula + "/" + formulaCode + "/" + historyId)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findFormula = findFormula;
            function getFormulaDetail(formulaCode, historyId, difficultyAtr) {
                var dfd = $.Deferred();
                nts.uk.request.ajax("pr", paths.getFormulaDetail + "/" + formulaCode + "/" + historyId + "/" + difficultyAtr)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getFormulaDetail = getFormulaDetail;
            function registerFormulaMaster(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.registerFormulaMaster, command).done(function () {
                    dfd.resolve();
                }).fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.registerFormulaMaster = registerFormulaMaster;
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
            var FormulaDetailDto = (function () {
                function FormulaDetailDto() {
                }
                return FormulaDetailDto;
            }());
            model.FormulaDetailDto = FormulaDetailDto;
            var FormulaEasyNotUseCondition = (function () {
                function FormulaEasyNotUseCondition() {
                }
                return FormulaEasyNotUseCondition;
            }());
            model.FormulaEasyNotUseCondition = FormulaEasyNotUseCondition;
            var FormulaEasyUseCondition = (function () {
                function FormulaEasyUseCondition() {
                }
                return FormulaEasyUseCondition;
            }());
            model.FormulaEasyUseCondition = FormulaEasyUseCondition;
        })(model = qmm017.model || (qmm017.model = {}));
    })(qmm017 = nts.qmm017 || (nts.qmm017 = {}));
})(nts || (nts = {}));
