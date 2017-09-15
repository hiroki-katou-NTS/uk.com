module nts.uk.at.view.kmk002.a {
    export module service {

        /**
         *  Service paths
         */
        let servicePath: any = {
            findOptionalItemDetail: 'ctx/at/record/optionalitem/find',
            saveOptionalItem: 'ctx/at/record/optionalitem/save',
            removeOptionalItem: 'ctx/at/record/optionalitem/remove',
            findOptionalItemHeaders: 'ctx/at/record/optionalitem/findall',
            findAllFormula: 'ctx/at/record/optionalitem/formula/findall',
            saveFormula: 'ctx/at/record/optionalitem/formula/save'
        };

        export function saveOptionalItem(command: model.OptionalItemDto): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveOptionalItem, command);
        }

        export function saveFormula(command: Array<model.CalcFormulaDto>): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveFormula, {listCalcFormula: command});
        }

        export function findOptionalItemDetail(): JQueryPromise<model.OptionalItemDto> {
            return nts.uk.request.ajax(servicePath.findCompanySetting + '/' + '');
        }

        export function findOptionalItemHeaders(): JQueryPromise<Array<model.OptionalItemHeader>> {
            return nts.uk.request.ajax(servicePath.findOptionalItemHeaders);
        }

        export function findAllFormula(): JQueryPromise<Array<model.CalcFormulaDto>> {
            return nts.uk.request.ajax(servicePath.findAllFormula);
        }

        export function removeOptionalItem(command: any): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.removeCompanySetting, command);
        }

        /**
         * Data Model
         */
        export module model {
            export interface OptionalItemHeader {
                optionalItemNo: string;
                optionalItemName: string;
                usageAtr: number;
                performanceAtr: number;
            }
            export interface OptionalItemDto {
                optionalItemNo: string;
                optionalItemName: string;
                optionalItemAtr: number;
                usageAtr: number;
                empConditionAtr: number;
                performanceAtr: number;
                calculationResultRange: CalculationResultRangeDto;
            }
            export interface CalculationResultRangeDto {
                upperCheck: boolean;
                lowerCheck: boolean;
                numberUpper: number;
                numberLower: number;
                amountUpper: number;
                amountLower: number;
                timeUpper: number;
                timeLower: number;
            }
            export interface CalcFormulaDto {
                formulaId: string;
                formulaName: string;
                optionalItemNo: string;
                formulaAtr: number;
                symbolValue: string;
                formulaSetting: any;
                monthlyRounding: any;
                dailyRounding: any;
            }
        }

    }
}
