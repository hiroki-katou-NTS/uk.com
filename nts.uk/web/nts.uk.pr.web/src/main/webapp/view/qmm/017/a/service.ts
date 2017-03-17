module nts.qmm017 {
    export module service {
        var paths: any = {
            getAllFormula: "pr/formula/formulaMaster/getAllFormula",
            findFormulaByCode: "pr/formula/formulaMaster/findFormula",
            findFormulaHistoryByCode: "pr/formula/formulaHistory/findFormulaHistoryByCode"
        }
        
        export function getAllFormula(): JQueryPromise<Array<model.FormulaDto>> {
            var dfd = $.Deferred<Array<model.FormulaDto>>();
            nts.uk.request.ajax("pr", paths.getAllFormula)
                .done(function(res: Array<model.FormulaDto>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        
        export function findFormulaByCode(formulaCode: string): JQueryPromise<model.FormulaDto> {
            var dfd = $.Deferred<model.FormulaDto>();
            nts.uk.request.ajax("pr", paths.findFormulaByCode + "/" + formulaCode)
                .done(function(res: model.FormulaDto) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        
        export function findFormulaHistoryByCode(formulaCode: string): JQueryPromise<Array<model.FormulaHistoryDto>> {
            var dfd = $.Deferred<Array<model.FormulaHistoryDto>>();
            nts.uk.request.ajax("pr", paths.findFormulaHistoryByCode + "/" + formulaCode)
                .done(function(res: Array<model.FormulaHistoryDto>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
    }

    export module model {
        export class FormulaDto {
            ccd: string;
            formulaCode: string;
            formulaName: string;
            difficultyAtr: number;
            constructor() {

            }
        }

        export class FormulaHistoryDto {
            companyCode: string;
            formulaCode: string;
            historyId: string;
            startDate: number;
            endDate: number;
            constructor() {

            }
        }
    }
}