module nts.qmm017 {
    export module service {
        var paths: any = {
            getAllFormula: "pr/formula/formulaMaster/getAllFormula",
            findFormula: "pr/formula/formulaMaster/findFormula",
            getFormulaDetail: "pr/formula/formulaMaster/getFormulaDetail",
            registerFormulaMaster: "pr/formula/formulaMaster/addFormulaMaster"
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

        export function findFormula(formulaCode: string, historyId: string): JQueryPromise<Array<model.FormulaDto>> {
            var dfd = $.Deferred<Array<model.FormulaDto>>();
            nts.uk.request.ajax("pr", paths.findFormula + "/" + formulaCode + "/" + historyId)
                .done(function(res: Array<model.FormulaDto>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        
        export function getFormulaDetail(formulaCode: string, historyId: string, difficultyAtr: number): JQueryPromise<model.FormulaDetailDto> {
            var dfd = $.Deferred<model.FormulaDetailDto>();
            nts.uk.request.ajax("pr", paths.getFormulaDetail + "/" + formulaCode + "/" + historyId + "/" + difficultyAtr)
                .done(function(res: model.FormulaDetailDto) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        
        export function registerFormulaMaster(command): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.registerFormulaMaster, command).done(function(){
                dfd.resolve();    
            }).fail(function(res){
                dfd.reject(res);
            });
            return dfd.promise();
        }
    }

    export module model {
        export class FormulaDto {
            ccd: string;
            formulaCode: string;
            formulaName: string;
            difficultyAtr: number;
            historyId: string;
            startDate: number;
            endDate: number;
            conditionAtr: number;
            refMasterNo: number;
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

        export class FormulaDetailDto {
            // formula easy
            fixFormulaAtr: number;
            easyFormula: Array<FormulaEasyNotUseCondition>;
            formulaEasyDetail: Array<FormulaEasyUseCondition>;
            // formula manual
            formulaContent: string;
            referenceMonthAtr: number;
            roundAtr: number;
            roundDigit: number;
        }

        export class FormulaEasyNotUseCondition {
            easyFormulaCode: string;
            value: number;
            refMasterNo: string;
        }

        export class FormulaEasyUseCondition {
            easyFormulaCode: string;
            easyFormulaName: string;
            easyFormulaTypeAtr: number;
            baseFixedAmount: number;
            baseAmountDevision: number;
            baseFixedValue: number;
            baseValueDevision: number;
            premiumRate: number;
            roundProcessingDevision: number;
            coefficientDivision: string;
            coefficientFixedValue: number;
            adjustmentDevision: number;
            totalRounding: number;
            maxLimitValue: number;
            minLimitValue: number;
            referenceItemCodes: Array<string>;
        }
    }
}