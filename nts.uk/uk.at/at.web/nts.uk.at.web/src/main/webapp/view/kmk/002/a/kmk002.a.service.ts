module nts.uk.at.view.kmk002.a {
    export module service {

        /**
         *  Service paths
         */
        let servicePath: any = {
            findOptionalItemDetail: 'ctx/at/record/optionalitem/find',
            findOptionalItemHeaders: 'ctx/at/record/optionalitem/findall',
            findFormulas: 'ctx/at/record/optionalitem/formula/findbyitemno',
            saveOptionalItem: 'ctx/at/record/optionalitem/save',
            saveFormula: 'ctx/at/record/optionalitem/formula/save',
            getFormulaEnum: 'ctx/at/record/optionalitem/formula/getenum',
            getOptItemEnum: 'ctx/at/record/optionalitem/getenum'
        };

        export function getFormulaEnum(): JQueryPromise<model.FormulaEnum> {
            return nts.uk.request.ajax(servicePath.getFormulaEnum);
        }

        export function getOptItemEnum(): JQueryPromise<model.OptItemEnum> {
            return nts.uk.request.ajax(servicePath.getOptItemEnum);
        }

        export function saveOptionalItem(command: model.OptionalItemDto): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveOptionalItem, command);
        }

        export function saveFormula(command: Array<model.FormulaDto>): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveFormula, { listCalcFormula: command });
        }

        export function findOptionalItemDetail(itemNo: string): JQueryPromise<model.OptionalItemDto> {
            return nts.uk.request.ajax(servicePath.findOptionalItemDetail + '/' + itemNo);
        }

        export function findOptionalItemHeaders(): JQueryPromise<Array<model.OptionalItemHeader>> {
            return nts.uk.request.ajax(servicePath.findOptionalItemHeaders);
        }

        export function findFormulas(itemNo: string): JQueryPromise<Array<model.FormulaDto>> {
            return nts.uk.request.ajax(servicePath.findFormulas + '/' + itemNo);
        }

        /**
         * Data Model
         */
        export module model {
            export interface OptionalItemHeader {
                itemNo: string;
                itemName: string;
                usageAtr: number;
                performanceAtr: number;
            }

            /**
             * OptionalItemDto
             */
            export interface OptionalItemDto {
                optionalItemNo: string;
                optionalItemName: string;
                optionalItemAtr: number;
                usageAtr: number;
                empConditionAtr: number;
                performanceAtr: number;
                calcResultRange: CalcResultRangeDto;
            }
            /**
             * CalcResultRangeDto
             */
            export interface CalcResultRangeDto {
                upperCheck: boolean;
                lowerCheck: boolean;
                numberUpper: number;
                numberLower: number;
                amountUpper: number;
                amountLower: number;
                timeUpper: number;
                timeLower: number;
            }
            /**
             * CalcFormulaDto
             */
            export interface FormulaDto {
                formulaId: string;
                formulaName: string;
                optionalItemNo: string;
                orderNo: number;
                symbolValue: string;
                formulaAtr: number;
                calcFormulaSetting: CalcFormulaSettingDto;
                monthlyRounding: RoundingDto;
                dailyRounding: RoundingDto;
            }
            /**
             * RoundingDto
             */
            export interface RoundingDto {
                numberRounding: number;
                numberUnit: number;
                timeRounding: number;
                timeUnit: number;
                amountRounding: number;
                amountUnit: number;
            }
            /**
             * CalcFormulaSettingDto
             */
            export interface CalcFormulaSettingDto {
                calcAtr: number;
                formulaSetting: FormulaSettingDto;
                itemSelection: ItemSelectionDto;
            }
            /**
             * FormulaSettingDto
             */
            export interface FormulaSettingDto {
                minusSegment: number;
                operator: number;
                leftItem: SettingItemDto;
                rightItem: SettingItemDto;
            }
            /**
             * SettingItemDto
             */
            export interface SettingItemDto {
                settingMethod: number;
                dispOrder: number;
                inputValue: number;
                formulaItemId: string;
            }
            /**
             * ItemSelectionDto
             */
            export interface ItemSelectionDto {
                minusSegment: number;
                attendanceItems: Array<AttendanceItemDto>;
            }
            /**
             * SelectedAttendanceItemDto
             */
            export interface AttendanceItemDto {
                id: string;
                operator: number;
            }
            export enum TypeAtr {
                NUMBER = 0,
                AMOUNT = 1,
                TIME = 2
            }
            export class FormulaEnum {
                formulaAtr: EnumConstantDto[];
                calcAtr: EnumConstantDto[];
                minusSegment: EnumConstantDto[];
                operatorAtr: EnumConstantDto[];
                settingMethod: EnumConstantDto[];
                dispOrder: EnumConstantDto[];
                addSubAtr: EnumConstantDto[];
                amountRounding: EnumConstantDto[];
                timeRounding: EnumConstantDto[];
                numberRounding: EnumConstantDto[];
            }
            export class OptItemEnum {
                formulaAtr: EnumConstantDto[];
                calcAtr: EnumConstantDto[];
                minusSegment: EnumConstantDto[];
                operatorAtr: EnumConstantDto[];
                settingMethod: EnumConstantDto[];
                dispOrder: EnumConstantDto[];
                addSubAtr: EnumConstantDto[];
                amountRounding: EnumConstantDto[];
                timeRounding: EnumConstantDto[];
                numberRounding: EnumConstantDto[];

            }
            export interface EnumConstantDto {
                value: number;
                fieldName: string;
                localizedName: string;
            }

//            export abstract class EnumAdaptor {
//                public valueOf(fieldName: string, enumConstant: EnumConstantDto[]): number {
//                    let result = _.find(enumConstant, item => item.fieldName == fieldName);
//                    if (result) {
//                        return result.value;
//                    }
//                    throw 'VALUE NOT FOUND';
//                }
//                public fieldNameOf(value: number, enumConstant: EnumConstantDto[]): string {
//                    let result = _.find(enumConstant, item => item.value == value);
//                    if (result) {
//                        return result.fieldName;
//                    }
//                    throw 'FIELDNAME NOT FOUND';
//                }
//            }
        }

    }
}
