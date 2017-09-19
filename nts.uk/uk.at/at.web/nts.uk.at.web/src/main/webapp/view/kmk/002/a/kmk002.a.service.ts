module nts.uk.at.view.kmk002.a {
    export module service {

        /**
         *  Service paths
         */
        let servicePath: any = {
            findOptionalItemDetail: 'ctx/at/record/optionalitem/find',
            findOptionalItemHeaders: 'ctx/at/record/optionalitem/findall',
            findAllFormula: 'ctx/at/record/optionalitem/formula/findall',
            saveOptionalItem: 'ctx/at/record/optionalitem/save',
            saveFormula: 'ctx/at/record/optionalitem/formula/save'
        };

        export function saveOptionalItem(command: model.OptionalItemDto): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveOptionalItem, command);
        }

        export function saveFormula(command: Array<model.FormulaDto>): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.saveFormula, { listCalcFormula: command });
        }

        export function findOptionalItemDetail(): JQueryPromise<model.OptionalItemDto> {
            return nts.uk.request.ajax(servicePath.findCompanySetting + '/' + '');
        }

        export function findOptionalItemHeaders(): JQueryPromise<Array<model.OptionalItemHeader>> {
            return nts.uk.request.ajax(servicePath.findOptionalItemHeaders);
        }

        export function findAllFormula(): JQueryPromise<Array<model.FormulaDto>> {
            return nts.uk.request.ajax(servicePath.findAllFormula);
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
                settingItems: Array<SettingItemDto>;
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
        }

    }
}
