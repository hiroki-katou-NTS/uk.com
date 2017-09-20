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
                itemNo: string;
                itemName: string;
                usageAtr: UsageAtr;
                performanceAtr: PerformanceAtr;
            }

            /**
             * OptionalItemDto
             */
            export interface OptionalItemDto {
                optionalItemNo: string;
                optionalItemName: string;
                optionalItemAtr: TypeAtr;
                usageAtr: UsageAtr;
                empConditionAtr: EmpConditionAtr;
                performanceAtr: PerformanceAtr;
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
                formulaAtr: TypeAtr;
                calcFormulaSetting: CalcFormulaSettingDto;
                monthlyRounding: RoundingDto;
                dailyRounding: RoundingDto;
            }
            /**
             * RoundingDto
             */
            export interface RoundingDto {
                numberRounding: NumberRounding;
                numberUnit: NumberUnit;
                timeRounding: TimeRounding;
                timeUnit: TimeUnit;
                amountRounding: AmountRounding;
                amountUnit: AmountUnit;
            }
            /**
             * CalcFormulaSettingDto
             */
            export interface CalcFormulaSettingDto {
                calcAtr: CalcAtr;
                formulaSetting: FormulaSettingDto;
                itemSelection: ItemSelectionDto;
            }
            /**
             * FormulaSettingDto
             */
            export interface FormulaSettingDto {
                minusSegment: MinusSegment;
                operator: Operator;
                leftItem: SettingItemDto;
                rightItem: SettingItemDto;
            }
            /**
             * SettingItemDto
             */
            export interface SettingItemDto {
                settingMethod: SettingMethod;
                dispOrder: SettingOrder;
                inputValue: number;
                formulaItemId: string;
            }
            /**
             * ItemSelectionDto
             */
            export interface ItemSelectionDto {
                minusSegment: MinusSegment;
                attendanceItems: Array<AttendanceItemDto>;
            }
            /**
             * SelectedAttendanceItemDto
             */
            export interface AttendanceItemDto {
                id: string;
                operator: Operator;
            }
            export enum EmpConditionAtr {
                NO_CONDITION = 0,
                WITH_CONDITION = 1
            }
            export enum PerformanceAtr {
                MONTHLY = 0,
                DAILY = 1
            }
            export enum UsageAtr {
                NOT_USED = 0,
                USED = 1
            }
            export enum TypeAtr {
                TIMES = 0,
                AMOUNT = 1,
                TIME = 2
            }
            export enum CalcAtr {
                ITEM_SELECTION = 0,
                FORMULA_SETTING = 1
            }
            export enum SettingOrder {
                LEFT = 1,
                RIGHT = 2
            }
            export enum SettingMethod {
                ITEM_SELECTION = 0,
                NUMERICAL_INPUT = 1
            }
            export enum MinusSegment {
                NOT_TREATED_AS_ZERO = 0,
                TREATED_AS_ZERO = 1
            }
            export enum Operator {
                ADD = 0,
                SUBTRACT = 1,
                MULTIPLY = 2,
                DIVIDE = 3
            }
            export enum NumberRounding {
                TRUNCATION = 0,
                ROUND_UP = 1,
                DOWN_4_UP_5 = 5
            }
            export enum NumberUnit {
                NONE = 0,
                INT_1_DIGITS = 1,
                INT_2_DIGITS = 2,
                INT_3_DIGITS = 3,
                INT_4_DIGITS = 4,
                INT_5_DIGITS = 5,
                INT_6_DIGITS = 6,
                INT_7_DIGITS = 7,
                INT_8_DIGITS = 8,
                INT_9_DIGITS = 9,
                INT_10_DIGITS = 10,
                INT_11_DIGITS = 11,
                DECIMAL_1ST = 12,
                DECIMAL_2ND = 13,
                DECIMAL_3RD = 14
            }
            export enum AmountRounding {
                TRUNCATION = 0,
                ROUND_UP = 1,
                DOWN_1_UP_2 = 2,
                DOWN_2_UP_3 = 3,
                DOWN_3_UP_4 = 4,
                DOWN_4_UP_5 = 5,
                DOWN_5_UP_6 = 6,
                DOWN_6_UP_7 = 7,
                DOWN_7_UP_8 = 8,
                DOWN_8_UP_9 = 9
            }
            export enum AmountUnit {
                ONE_YEN = 1,
                TEN_YEN = 10,
                ONE_HUNDRED_YEN = 100,
                ONE_THOUSAND_YEN = 1000
            }
            export enum TimeRounding {
                ROUNDING_DOWN = 0,
                ROUNDING_UP = 1,
                ROUNDING_DOWN_OVER = 2
            }
            export enum TimeUnit {
                ROUNDING_TIME_1MIN = 1,
                ROUNDING_TIME_5MIN = 5,
                ROUNDING_TIME_6MIN = 6,
                ROUNDING_TIME_10MIN = 10,
                ROUNDING_TIME_15MIN = 15,
                ROUNDING_TIME_20MIN = 20,
                ROUNDING_TIME_30MIN = 30,
                ROUNDING_TIME_60MIN = 60
            }
        }

    }
}
