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

        export function getFormulaEnum(): JQueryPromise<model.FormulaEnumDto> {
            return nts.uk.request.ajax(servicePath.getFormulaEnum);
        }

        export function getOptItemEnum(): JQueryPromise<model.OptItemEnumDto> {
            return nts.uk.request.ajax(servicePath.getOptItemEnum);
        }

        export function saveOptionalItem(command: model.OptionalItemDto): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveOptionalItem, command);
        }

        export function saveFormula(command: model.FormulaCommand): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveFormula, command);
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
                calcAtr: number;
                formulaSetting: FormulaSettingDto;
                itemSelection: ItemSelectionDto;
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
                attendanceItemId: number;
                operator: number;
                operatorText?: string;
                attendanceItemName?: string;
            }
            export enum TypeAtr {
                NUMBER = 0,
                AMOUNT = 1,
                TIME = 2
            }
            export interface FormulaEnumDto {
                formulaAtr: EnumConstantDto[];
                calcAtr: EnumConstantDto[];
                minusSegment: EnumConstantDto[];
                operatorAtr: EnumConstantDto[];
                settingMethod: EnumConstantDto[];
                dispOrder: EnumConstantDto[];
                addSubAtr: EnumConstantDto[];
                amountRounding: RoundingDto;
                timeRounding: RoundingDto;
                numberRounding: RoundingDto;
            }
            export interface RoundingDto {
                unit: EnumConstantDto[];
                rounding: EnumConstantDto[];
            }
            export interface OptItemEnumDto {
                itemAtr: EnumConstantDto[];
                useAtr: EnumConstantDto[];
                empConditionAtr: EnumConstantDto[];
                performanceAtr: EnumConstantDto[];
            }
            export interface EnumConstantDto {
                value: number;
                fieldName: string;
                localizedName: string;
            }
            export interface FormulaCommand {
                optItemNo: string;
                calcFormulas: Array<FormulaDto>;
            }

            export class EnumAdaptor {

                /**
                 * Get number value of fieldName.
                 * @param: list enum
                 */
                public static valueOf(fieldName: string, enumConstant: EnumConstantDto[]): number {
                    let result;
                    let found = _.find(enumConstant, item => item.fieldName == fieldName);
                    if (found) {
                        result = found.value;
                    }
                    return result;
                }

                /**
                 * Get localizedName of number value.
                 * @param: list enum
                 */
                public static localizedNameOf(value: number, enumConstant: EnumConstantDto[]): string {
                    let result;
                    let found = _.find(enumConstant, item => item.value == value);
                    if (found) {
                        result = found.localizedName;
                    }
                    return result;
                }
            }
        }

    }
}
