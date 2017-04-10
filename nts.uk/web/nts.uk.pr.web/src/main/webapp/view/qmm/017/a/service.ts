module nts.qmm017 {
    export module service {
        var paths: any = {
            getAllFormula: "pr/formula/formulaMaster/getAllFormula",
            findFormula: "pr/formula/formulaMaster/findFormula",
            getFormulaDetail: "pr/formula/formulaMaster/getFormulaDetail",
            registerFormulaMaster: "pr/formula/formulaMaster/addFormulaMaster",
            updateFormula: "pr/formula/formulaMaster/updateFormulaMaster",
            getListCompanyUnitPrice: "pr/proto/unitprice/findbydate/",
            getListPersonalUnitPrice: "pr/core/rule/employment/unitprice/personal/find/all",
            getListItemMaster: "pr/core/item/findall/category/"
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
                .done(function(res) {
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
        
        export function updateFormulaMaster(command): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.updateFormula, command).done(function(){
                dfd.resolve();    
            }).fail(function(res){
                dfd.reject(res);
            });
            return dfd.promise();
        }
        
        export function getListCompanyUnitPrice(baseDate): JQueryPromise<Array<model.CompanyUnitPriceDto>> {
            var dfd = $.Deferred<Array<model.CompanyUnitPriceDto>>();
            nts.uk.request.ajax("pr", paths.getListCompanyUnitPrice + baseDate)
                .done(function(res: Array<model.CompanyUnitPriceDto>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        export function getListPersonalUnitPrice(): JQueryPromise<Array<model.PersonalUnitPriceDto>> {
            var dfd = $.Deferred<Array<model.PersonalUnitPriceDto>>();
            nts.uk.request.ajax("pr", paths.getListPersonalUnitPrice)
                .done(function(res: Array<model.PersonalUnitPriceDto>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        export function getListItemMaster(categoryAtr): JQueryPromise<Array<model.ItemMasterDto>> {
            var dfd = $.Deferred<Array<model.ItemMasterDto>>();
            nts.uk.request.ajax("pr", paths.getListItemMaster + categoryAtr)
                .done(function(res: Array<model.ItemMasterDto>) {
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
            easyFormula: Array<FormulaEasyDto>;
            // formula manual
            formulaContent: string;
            referenceMonthAtr: number;
            roundAtr: number;
            roundDigit: number;
        }

        export class FormulaEasyDto {
            easyFormulaCode: string;
            value: number;
            referenceMasterNo: string;
            fixFormulaAtr: number;
            formulaDetail: FormulaEasyDetailDto;
            constructor() {
            }
        }

        export class FormulaEasyDetailDto {
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
            constructor() {    
            }
        }
        
        export class CompanyUnitPriceDto {
            companyUnitPriceCode: string;
            companyUnitPriceName: string;
        }

        export class PersonalUnitPriceDto {
            personalUnitPriceCode: string;
            personalUnitPriceName: string;
        }

        export class ItemMasterDto {
            itemCode: string;
            itemName: string;
        }
    }
}