module nts.uk.pr.view.qmm017.share.model {

    import getText = nts.uk.resource.getText;

    export enum SCREEN_MODE {
        NEW = 0,
        UPDATE = 1,
        ADD_HISTORY =2,
        UPDATE_FORMULA = 3
    }
    export enum TAKEOVER_METHOD {
        FROM_LAST_HISTORY = 0,
        FROM_BEGINNING = 1
    }

    export enum MODIFY_METHOD {
        DELETE = 0,
        UPDATE = 1
    }

    export enum FORMULA_SETTING_METHOD {
        BASIC_SETTING = 0,
        DETAIL_SETTING = 1
    }

    export function getFormulaSettingMethodEnumModel () {
        return [
            new EnumModel(FORMULA_SETTING_METHOD.BASIC_SETTING, getText('Enum_FormulaSettingMethod_BASIC_SETTING')),
            new EnumModel(FORMULA_SETTING_METHOD.DETAIL_SETTING, getText('Enum_FormulaSettingMethod_DETAIL_SETTING'))
        ];
    }

    export enum NESTED_USE_CLS {
        NOT_USE = 0,
        USE = 1
    }

    export function getNestedUseClsEnumModel () {
        return [
            new EnumModel(NESTED_USE_CLS.USE, getText('Enum_NestedUseCls_USE')),
            new EnumModel(NESTED_USE_CLS.NOT_USE, getText('Enum_NestedUseCls_NOT_USE'))
        ];
    }

    export enum ROUNDING_POSITION {
        ONE_YEN = 0,
        TEN_YEN = 1,
        ONE_HUNDRED_YEN = 2,
        ONE_THOUSAND_YEN = 3
    }

    export function getRoudingPositionEnumModel () {
        return [
            new EnumModel(ROUNDING_POSITION.ONE_YEN, getText('Enum_RoundingPosition_ONE_YEN')),
            new EnumModel(ROUNDING_POSITION.TEN_YEN, getText('Enum_RoundingPosition_TEN_YEN')),
            new EnumModel(ROUNDING_POSITION.ONE_HUNDRED_YEN, getText('Enum_RoundingPosition_ONE_HUNDRED_YEN')),
            new EnumModel(ROUNDING_POSITION.ONE_THOUSAND_YEN, getText('Enum_RoundingPosition_ONE_THOUSAND_YEN'))
        ];
    }

    export enum ROUNDING {
        ROUND_UP = 0,
        TRUNCATION = 1,
        DOWN_1_UP_2 = 2,
        DOWN_2_UP_3 = 3,
        DOWN_3_UP_4 = 4,
        DOWN_4_UP_5 = 5,
        DOWN_5_UP_6 = 6,
        DOWN_6_UP_7 = 7,
        DOWN_7_UP_8 = 8,
        DOWN_8_UP_9 = 9
    }

    export function getRoundingEnumModel () {
        return [
            new EnumModel(ROUNDING.ROUND_UP, getText('Enum_Rounding_ROUND_UP')),
            new EnumModel(ROUNDING.TRUNCATION, getText('Enum_Rounding_TRUNCATION')),
            new EnumModel(ROUNDING.DOWN_1_UP_2, getText('Enum_Rounding_DOWN_1_UP_2')),
            new EnumModel(ROUNDING.DOWN_2_UP_3, getText('Enum_Rounding_DOWN_2_UP_3')),
            new EnumModel(ROUNDING.DOWN_3_UP_4, getText('Enum_Rounding_DOWN_3_UP_4')),
            new EnumModel(ROUNDING.DOWN_4_UP_5, getText('Enum_Rounding_DOWN_4_UP_5')),
            new EnumModel(ROUNDING.DOWN_5_UP_6, getText('Enum_Rounding_DOWN_5_UP_6')),
            new EnumModel(ROUNDING.DOWN_6_UP_7, getText('Enum_Rounding_DOWN_6_UP_7')),
            new EnumModel(ROUNDING.DOWN_7_UP_8, getText('Enum_Rounding_DOWN_7_UP_8')),
            new EnumModel(ROUNDING.DOWN_8_UP_9, getText('Enum_Rounding_DOWN_8_UP_9'))
        ];
    }

    export enum REFERENCE_MONTH {
        CURRENT_MONTH = 0,
        ONE_MONTH_AGO = 1,
        TWO_MONTH_AGO = 2,
        THREE_MONTH_AGO = 3,
        FOUR_MONTH_AGO = 4,
        FIVE_MONTH_AGO = 5,
        SIX_MONTH_AGO = 6,
        SEVEN_MONTH_AGO = 7,
        EIGHT_MONTH_AGO = 8,
        NINE_MONTH_AGO = 9,
        TEN_MONTH_AGO = 10,
        ELEVEN_MONTH_AGO = 11,
        TWELVE_MONTH_AGO = 12
    }

    export function getReferenceMonthEnumModel () {
        return [
            new EnumModel(REFERENCE_MONTH.CURRENT_MONTH, getText('Enum_ReferenceMonth_CURRENT_MONTH')),
            new EnumModel(REFERENCE_MONTH.ONE_MONTH_AGO, getText('Enum_ReferenceMonth_ONE_MONTH_AGO')),
            new EnumModel(REFERENCE_MONTH.TWO_MONTH_AGO, getText('Enum_ReferenceMonth_TWO_MONTH_AGO')),
            new EnumModel(REFERENCE_MONTH.THREE_MONTH_AGO, getText('Enum_ReferenceMonth_THREE_MONTH_AGO')),
            new EnumModel(REFERENCE_MONTH.FOUR_MONTH_AGO, getText('Enum_ReferenceMonth_FOUR_MONTH_AGO')),
            new EnumModel(REFERENCE_MONTH.FIVE_MONTH_AGO, getText('Enum_ReferenceMonth_FIVE_MONTH_AGO')),
            new EnumModel(REFERENCE_MONTH.SIX_MONTH_AGO, getText('Enum_ReferenceMonth_SIX_MONTH_AGO')),
            new EnumModel(REFERENCE_MONTH.SEVEN_MONTH_AGO, getText('Enum_ReferenceMonth_SEVEN_MONTH_AGO')),
            new EnumModel(REFERENCE_MONTH.EIGHT_MONTH_AGO, getText('Enum_ReferenceMonth_EIGHT_MONTH_AGO')),
            new EnumModel(REFERENCE_MONTH.NINE_MONTH_AGO, getText('Enum_ReferenceMonth_NINE_MONTH_AGO')),
            new EnumModel(REFERENCE_MONTH.TEN_MONTH_AGO, getText('Enum_ReferenceMonth_TEN_MONTH_AGO')),
            new EnumModel(REFERENCE_MONTH.ELEVEN_MONTH_AGO, getText('Enum_ReferenceMonth_ELEVEN_MONTH_AGO')),
            new EnumModel(REFERENCE_MONTH.TWELVE_MONTH_AGO, getText('Enum_ReferenceMonth_TWELVE_MONTH_AGO'))
        ];
    }

    export enum MASTER_BRANCH_USE {
        NOT_USE = 0,
        USE = 1
    }

    export function getMasterBranchUseEnumModel () {
        return [
            new EnumModel(MASTER_BRANCH_USE.NOT_USE, getText('Enum_MasterBranchUse_NOT_USE')),
            new EnumModel(MASTER_BRANCH_USE.USE, getText('Enum_MasterBranchUse_USE'))
        ];
    }

    export enum MASTER_USE {
        EMPLOYMENT = 0,
        DEPARTMENT = 1,
        CLASSIFICATION = 2,
        JOB_TITLE = 3,
        SALARY_CLASSIFICATION = 4,
        SALARY_FROM = 5
    }

    export function getMasterUseEnumModel () {
        return [
            new EnumModel(MASTER_USE.EMPLOYMENT, getText('Enum_MasterUse_EMPLOYMENT')),
            new EnumModel(MASTER_USE.DEPARTMENT, getText('Enum_MasterUse_DEPARTMENT')),
            new EnumModel(MASTER_USE.CLASSIFICATION, getText('Enum_MasterUse_CLASSIFICATION')),
            new EnumModel(MASTER_USE.JOB_TITLE, getText('Enum_MasterUse_JOB_TITLE')),
            new EnumModel(MASTER_USE.SALARY_CLASSIFICATION, getText('Enum_MasterUse_SALARY_CLS')),
            new EnumModel(MASTER_USE.SALARY_FROM, getText('Enum_MasterUse_SALARY_FORM'))
        ];
    }

    export enum CALCULATION_FORMULA_CLS {
        FIXED_VALUE = 0,
        FORMULA = 1,
        DEFINITION_FORMULA = 2,
    }

    export function getCalculationFormulaClsEnumModel () {
        return [
            new EnumModel(CALCULATION_FORMULA_CLS.FIXED_VALUE, getText('Enum_CalculationFormulaCls_FIXED_VALUE')),
            new EnumModel(CALCULATION_FORMULA_CLS.FORMULA, getText('Enum_CalculationFormulaCls_FORMULA')),
            new EnumModel(CALCULATION_FORMULA_CLS.DEFINITION_FORMULA, getText('Enum_CalculationFormulaCls_DEFINITION_FORMULA'))
        ];
    }

    export enum FORMULA_TYPE {
        CALCULATION_FORMULA_TYPE_1 = 0,
        CALCULATION_FORMULA_TYPE_2 = 1,
        CALCULATION_FORMULA_TYPE_3 = 2
    }

    export function getFormulaTypeEnumModel () {
        return [
            new EnumModel(FORMULA_TYPE.CALCULATION_FORMULA_TYPE_1, getText('Enum_FormulaType_CALCULATION_FORMULA_TYPE_1')),
            new EnumModel(FORMULA_TYPE.CALCULATION_FORMULA_TYPE_2, getText('Enum_FormulaType_CALCULATION_FORMULA_TYPE_2')),
            new EnumModel(FORMULA_TYPE.CALCULATION_FORMULA_TYPE_3, getText('Enum_FormulaType_CALCULATION_FORMULA_TYPE_3'))
        ];
    }

    export enum ROUNDING_METHOD {
        ROUND_OFF = 0,
        ROUND_UP = 1,
        TRUNCATION = 2,
        DO_NOTHING = 3
    }
    export function getRoundingMethodEnumModel () {
        return [
            new EnumModel(ROUNDING_METHOD.ROUND_OFF, getText('QMM017_168')),
            new EnumModel(ROUNDING_METHOD.ROUND_UP, getText('QMM017_169')),
            new EnumModel(ROUNDING_METHOD.TRUNCATION, getText('QMM017_170')),
            new EnumModel(ROUNDING_METHOD.DO_NOTHING, getText('QMM017_171'))
        ];
    }

    export enum ADJUSTMENT_CLASSIFICATION {
        NOT_ADJUST = 0,
        PLUS_ADJUST = 1,
        MINUS_ADJUST = 2,
        PLUS_MINUS_ADJUST = 3,
    }

    export function getAdjustmentClsEnumModel () {
        return [
            new EnumModel(ADJUSTMENT_CLASSIFICATION.NOT_ADJUST, getText('Enum_AdjustmentCls_NOT_ADJUST')),
            new EnumModel(ADJUSTMENT_CLASSIFICATION.PLUS_ADJUST, getText('Enum_AdjustmentCls_PLUS_ADJUST')),
            new EnumModel(ADJUSTMENT_CLASSIFICATION.MINUS_ADJUST, getText('Enum_AdjustmentCls_MINUS_ADJUST')),
            new EnumModel(ADJUSTMENT_CLASSIFICATION.PLUS_MINUS_ADJUST, getText('Enum_AdjustmentCls_PLUS_MINUS_ADJUST'))
        ];
    }

    export enum ROUNDING_RESULT {
        ROUND_OFF = 0,
        ROUND_UP = 1,
        TRUNCATION = 2
    }

    export function getRoundingResultEnumModel () {
        return [
            new EnumModel(ROUNDING_RESULT.ROUND_OFF, getText('QMM017_168')),
            new EnumModel(ROUNDING_RESULT.ROUND_UP, getText('QMM017_169')),
            new EnumModel(ROUNDING_RESULT.TRUNCATION, getText('QMM017_170'))
        ];
    }

    export enum STANDARD_AMOUNT_CLS {
        FIXED_AMOUNT = 0,
        PAYMENT_ITEM = 1,
        DEDUCTION_ITEM = 2,
        COMPANY_UNIT_PRICE_ITEM = 3,
        INDIVIDUAL_UNIT_PRICE_ITEM = 4
    }

    export function getStandardAmountClsEnumModel () {
        return [
            new EnumModel(STANDARD_AMOUNT_CLS.FIXED_AMOUNT, getText('Enum_StandardAmountCls_FIXED_AMOUNT')),
            new EnumModel(STANDARD_AMOUNT_CLS.PAYMENT_ITEM, getText('Enum_StandardAmountCls_PAYMENT_ITEM')),
            new EnumModel(STANDARD_AMOUNT_CLS.DEDUCTION_ITEM, getText('Enum_StandardAmountCls_DEDUCTION_ITEM')),
            new EnumModel(STANDARD_AMOUNT_CLS.COMPANY_UNIT_PRICE_ITEM, getText('Enum_StandardAmountCls_COMPANY_UNIT_PRICE_ITEM')),
            new EnumModel(STANDARD_AMOUNT_CLS.INDIVIDUAL_UNIT_PRICE_ITEM, getText('Enum_RoundingMethod_INDIVIDUAL_UNIT_PRICE_ITEM'))
        ];
    }

    export enum COEFFICIENT_CLASSIFICATION {
        FIXED_VALUE = 0,
        WORKDAY = 1,
        WORKDAY_AND_HOLIDAY = 2
    }

    export function getCoefficientClassificationEnumModel () {
        return [
            new EnumModel(COEFFICIENT_CLASSIFICATION.FIXED_VALUE, getText('Enum_CoefficientCls_FIXED_VALUE')),
            new EnumModel(COEFFICIENT_CLASSIFICATION.WORKDAY, getText('Enum_CoefficientCls_WORKDAY')),
            new EnumModel(COEFFICIENT_CLASSIFICATION.WORKDAY_AND_HOLIDAY, getText('Enum_CoefficientCls_WORKDAY_AND_HOLIDAY'))
        ];
    }

    export enum BASE_ITEM_CLASSIFICATION {
        FIXED_VALUE = 0,
        STANDARD_DAY = 1,
        WORKDAY = 2,
        ATTENDANCE_DAY = 3,
        ATTENDANCE_DAY_AND_HOLIDAY = 4,
        REFERENCE_TIME = 5,
        SERVICE_DAY_MUL_REFER_TIME = 6,
        WORKDAY_MUL_REFER_TIME = 7,
        WORKDAY_AND_HOLIDAY_MUL_REFER_TIME = 8,
        ATTENDANCE_TIME = 9
    }

    export function getBaseItemClsEnumModel () {
        return [
            new EnumModel(BASE_ITEM_CLASSIFICATION.FIXED_VALUE, getText('Enum_BaseItemCls_FIXED_VALUE')),
            new EnumModel(BASE_ITEM_CLASSIFICATION.STANDARD_DAY, getText('Enum_BaseItemCls_STANDARD_DAY')),
            new EnumModel(BASE_ITEM_CLASSIFICATION.WORKDAY, getText('Enum_BaseItemCls_WORKDAY')),
            new EnumModel(BASE_ITEM_CLASSIFICATION.ATTENDANCE_DAY, getText('Enum_BaseItemCls_ATTENDANCE_DAY')),
            new EnumModel(BASE_ITEM_CLASSIFICATION.ATTENDANCE_DAY_AND_HOLIDAY, getText('Enum_BaseItemCls_ATTENDANCE_DAY_AND_HOLIDAY')),
            new EnumModel(BASE_ITEM_CLASSIFICATION.REFERENCE_TIME, getText('Enum_BaseItemCls_REFERENCE_TIME')),
            new EnumModel(BASE_ITEM_CLASSIFICATION.SERVICE_DAY_MUL_REFER_TIME, getText('Enum_BaseItemCls_SERVICE_DAY_MUL_REFER_TIME')),
            new EnumModel(BASE_ITEM_CLASSIFICATION.WORKDAY_MUL_REFER_TIME, getText('Enum_BaseItemCls_WORKDAY_MUL_REFER_TIME')),
            new EnumModel(BASE_ITEM_CLASSIFICATION.WORKDAY_AND_HOLIDAY_MUL_REFER_TIME, getText('Enum_BaseItemCls_WORKDAY_AND_HOLIDAY_MUL_REFER_TIME')),
            new EnumModel(BASE_ITEM_CLASSIFICATION.ATTENDANCE_TIME, getText('Enum_BaseItemCls_ATTENDANCE_TIME'))
        ];
    }

    export enum LINE_ITEM_CATEGORY {
        PAYMENT_ITEM = 0,
        DEDUCTION_ITEM = 1,
        ATTENDANCE_ITEM = 2
    }

    export function getLineItemCategoryEnumModel () {
        return [
            new EnumModel(LINE_ITEM_CATEGORY.PAYMENT_ITEM, getText('Enum_LineItemCategory_PAYMENT_ITEM')),
            new EnumModel(LINE_ITEM_CATEGORY.DEDUCTION_ITEM, getText('Enum_LineItemCategory_DEDUCTION_ITEM')),
            new EnumModel(LINE_ITEM_CATEGORY.ATTENDANCE_ITEM, getText('Enum_LineItemCategory_ATTENDANCE_ITEM'))
        ];
    }

    export function getLineItemCategoryItem () {
        return ko.observableArray(getLineItemCategoryEnumModel());
    }

    export enum UNIT_PRICE_ITEM_CATEGORY {
        COMPANY_UNIT_PRICE_ITEM = 0,
        INDIVIDUAL_UNIT_PRICE_ITEM = 1
    }

    export function getUnitPriceItemCategoryEnumModel () {
        return [
            new EnumModel(UNIT_PRICE_ITEM_CATEGORY.COMPANY_UNIT_PRICE_ITEM, getText('Enum_UnitPriceItemCategory_COMPANY_UNIT_PRICE_ITEM')),
            new EnumModel(UNIT_PRICE_ITEM_CATEGORY.INDIVIDUAL_UNIT_PRICE_ITEM, getText('Enum_UnitPriceItemCategory_INDIVIDUAL_UNIT_PRICE_ITEM'))
        ];
    }

    export function getUnitPriceItemCategoryItem () {
        return ko.observableArray(getUnitPriceItemCategoryEnumModel());
    }

    export enum FUNCTION_CLASSIFICATION {
        ALL = 0,
        TIME_FUNCTION = 1,
        PAYROLL_SYSTEM = 2,
        LOGIC = 3,
        STRING_OPERATION = 4,
        DATETIME = 5,
        MATHEMATICS = 6
    }

    export function getFunctionClassificationEnumModel () {
        return [
            new EnumModel(FUNCTION_CLASSIFICATION.ALL, getText('Enum_FunctionCls_ALL'))
        ];
    }

    export function getFunctionClassificationItem () {
        return ko.observableArray(getFunctionClassificationEnumModel());
    }

    export enum SYSTEM_VARIABLE_CLASSIFICATION {
        ALL = 0
    }

    export function getSystemVariableClassificationEnumModel () {
        return [
            new EnumModel(FUNCTION_CLASSIFICATION.ALL, getText('Enum_SystemVariableCls_ALL'))
        ];
    }

    export function getSystemVariableClassificationItem () {
        return ko.observableArray(getSystemVariableClassificationEnumModel());
    }

    export enum FUNCTION_LIST {
        CONDITIONAL_EXPRESSION = 0,
        AND = 1,
        OR = 2,
        ROUND_OFF = 3,
        TRUNCATION = 4,
        ROUND_UP = 5,
        MAX_VALUE = 6,
        MIN_VALUE = 7,
        // pending, remove out of list
        NUMBER_OF_FAMILY_MEMBER = 11,
        ADDITIONAL_YEARMONTH = 8,
        YEAR_EXTRACTION = 9,
        MONTH_EXTRACTION = 10
    }

    export function getFunctionListEnumModel () {
        return [
            new EnumModel(FUNCTION_LIST.CONDITIONAL_EXPRESSION, getText('Enum_FunctionList_CONDITIONAL_EXPRESSION')),
            new EnumModel(FUNCTION_LIST.AND, getText('Enum_FunctionList_AND')),
            new EnumModel(FUNCTION_LIST.OR, getText('Enum_FunctionList_OR')),
            new EnumModel(FUNCTION_LIST.ROUND_OFF, getText('Enum_FunctionList_ROUND_OFF')),
            new EnumModel(FUNCTION_LIST.TRUNCATION, getText('Enum_FunctionList_TRUNCATION')),
            new EnumModel(FUNCTION_LIST.ROUND_UP, getText('Enum_FunctionList_ROUND_UP')),
            new EnumModel(FUNCTION_LIST.MAX_VALUE, getText('Enum_FunctionList_MAX_VALUE')),
            new EnumModel(FUNCTION_LIST.MIN_VALUE, getText('Enum_FunctionList_MIN_VALUE')),
            new EnumModel(FUNCTION_LIST.ADDITIONAL_YEARMONTH, getText('Enum_FunctionList_ADDITIONAL_YEARMONTH')),
            new EnumModel(FUNCTION_LIST.YEAR_EXTRACTION, getText('Enum_FunctionList_YEAR_EXTRACTION')),
            new EnumModel(FUNCTION_LIST.MONTH_EXTRACTION, getText('Enum_FunctionList_MONTH_EXTRACTION'))
        ];
    }

    export function getFunctionListEnumModelByType (type: number) {
        if (type == FUNCTION_CLASSIFICATION.ALL)
            return [
                new EnumModel(FUNCTION_LIST.CONDITIONAL_EXPRESSION, getText('Enum_FunctionList_CONDITIONAL_EXPRESSION')),
                new EnumModel(FUNCTION_LIST.AND, getText('Enum_FunctionList_AND')),
                new EnumModel(FUNCTION_LIST.OR, getText('Enum_FunctionList_OR')),
                new EnumModel(FUNCTION_LIST.ROUND_OFF, getText('Enum_FunctionList_ROUND_OFF')),
                new EnumModel(FUNCTION_LIST.TRUNCATION, getText('Enum_FunctionList_TRUNCATION')),
                new EnumModel(FUNCTION_LIST.ROUND_UP, getText('Enum_FunctionList_ROUND_UP')),
                new EnumModel(FUNCTION_LIST.MAX_VALUE, getText('Enum_FunctionList_MAX_VALUE')),
                new EnumModel(FUNCTION_LIST.MIN_VALUE, getText('Enum_FunctionList_MIN_VALUE')),
                new EnumModel(FUNCTION_LIST.ADDITIONAL_YEARMONTH, getText('Enum_FunctionList_ADDITIONAL_YEARMONTH')),
                new EnumModel(FUNCTION_LIST.YEAR_EXTRACTION, getText('Enum_FunctionList_YEAR_EXTRACTION')),
                new EnumModel(FUNCTION_LIST.MONTH_EXTRACTION, getText('Enum_FunctionList_MONTH_EXTRACTION'))
            ];
        if (type == FUNCTION_CLASSIFICATION.TIME_FUNCTION) return [];
        if (type == FUNCTION_CLASSIFICATION.PAYROLL_SYSTEM) return [];
        if (type == FUNCTION_CLASSIFICATION.LOGIC)
            return [
                new EnumModel(FUNCTION_LIST.CONDITIONAL_EXPRESSION, getText('Enum_FunctionList_CONDITIONAL_EXPRESSION')),
                new EnumModel(FUNCTION_LIST.AND, getText('Enum_FunctionList_AND')),
                new EnumModel(FUNCTION_LIST.OR, getText('Enum_FunctionList_OR'))
            ];
        if (type == FUNCTION_CLASSIFICATION.STRING_OPERATION) return [];
        if (type == FUNCTION_CLASSIFICATION.DATETIME)
            return [
                new EnumModel(FUNCTION_LIST.ADDITIONAL_YEARMONTH, getText('Enum_FunctionList_ADDITIONAL_YEARMONTH')),
                new EnumModel(FUNCTION_LIST.YEAR_EXTRACTION, getText('Enum_FunctionList_YEAR_EXTRACTION')),
                new EnumModel(FUNCTION_LIST.MONTH_EXTRACTION, getText('Enum_FunctionList_MONTH_EXTRACTION'))
            ];
        if (type == FUNCTION_CLASSIFICATION.MATHEMATICS)
            return [
                new EnumModel(FUNCTION_LIST.ROUND_OFF, getText('Enum_FunctionList_ROUND_OFF')),
                new EnumModel(FUNCTION_LIST.TRUNCATION, getText('Enum_FunctionList_TRUNCATION')),
                new EnumModel(FUNCTION_LIST.ROUND_UP, getText('Enum_FunctionList_ROUND_UP')),
                new EnumModel(FUNCTION_LIST.MAX_VALUE, getText('Enum_FunctionList_MAX_VALUE')),
                new EnumModel(FUNCTION_LIST.MIN_VALUE, getText('Enum_FunctionList_MIN_VALUE'))
            ];
        return [];
    }

    export function getFunctionListItem () {
        return ko.observableArray(getFunctionListEnumModel());
    }

    export enum SYSTEM_VARIABLE_LIST {
        SYSTEM_YMD_DATE = 0,
        SYSTEM_YM_DATE = 2,
        SYSTEM_Y_DATE = 1,
        PROCESSING_YEAR_MONTH = 3,
        PROCESSING_YEAR = 4,
        REFERENCE_TIME = 5,
        STANDARD_DAY = 6,
        WORKDAY = 7
    }

    export function getSystemVariableListEnumModel () {
        return [
            new EnumModel(SYSTEM_VARIABLE_LIST.SYSTEM_YMD_DATE, getText('Enum_SystemVariableList_SYSTEM_YMD_DATE')),
            new EnumModel(SYSTEM_VARIABLE_LIST.SYSTEM_YM_DATE, getText('Enum_SystemVariableList_SYSTEM_YM_DATE')),
            new EnumModel(SYSTEM_VARIABLE_LIST.SYSTEM_Y_DATE, getText('Enum_SystemVariableList_SYSTEM_Y_DATE')),
            new EnumModel(SYSTEM_VARIABLE_LIST.PROCESSING_YEAR_MONTH, getText('Enum_SystemVariableList_PROCESSING_YEAR_MONTH')),
            new EnumModel(SYSTEM_VARIABLE_LIST.PROCESSING_YEAR, getText('Enum_SystemVariableList_PROCESSING_YEAR')),
            new EnumModel(SYSTEM_VARIABLE_LIST.WORKDAY, getText('Enum_SystemVariableList_WORKDAY'))
        ];
    }

    export function getSystemVariableListItem () {
        return ko.observableArray(getSystemVariableListEnumModel());
    }

    export class EnumModel {
        value: number;
        name: string;

        constructor(value: number, name: string) {
            this.value = value;
            this.name = name;
        }
    }

    export class ItemModel {
        value: string;
        name: string;
        displayText: string;
        constructor(value: string, name: string) {
            this.value = value;
            this.name = name;
            if (!value) this.displayText = name;
            else this.displayText = value + "    " + name;
        }
    }



    // かんたん計算式設定
    export interface IBasicFormulaSetting {
        masterUse: number;
        masterBranchUse: number;
        historyID: string;
    }
    // かんたん計算式設定
    export class BasicFormulaSetting {
        masterUse: KnockoutObservable<number> = ko.observable(null);
        masterBranchUse: KnockoutObservable<number> = ko.observable(null);
        historyID: KnockoutObservable<string> = ko.observable(null);
        // control item
        masterUseItem: KnockoutObservableArray<EnumModel> = ko.observableArray(getMasterUseEnumModel());
        masterBranchUseItem: KnockoutObservableArray<EnumModel> = ko.observableArray(getMasterBranchUseEnumModel());
        // display item
        // C3_2 分岐条件 displayMasterUse + のマスタに該当する場合
        displayMasterUse: KnockoutObservable<string> = ko.observable(null);
        branchCondition: KnockoutObservable<string> = ko.observable(null);
        displayMasterBranchUse: KnockoutObservable<string> = ko.observable(null);
        constructor(params: IBasicFormulaSetting) {
            this.masterUse(params ? params.masterUse : MASTER_USE.EMPLOYMENT);
            this.masterBranchUse(params ? params.masterBranchUse : MASTER_BRANCH_USE.NOT_USE);
            this.historyID(params ? params.historyID : null);
            this.displayMasterUse = ko.computed(function() {
                return this.masterUse() != null ? this.masterUseItem()[this.masterUse()].name : null;
            }, this);
            this.branchCondition = ko.computed(function() {
                return this.displayMasterUse() + "のマスタに該当する場合";
            }, this);
            this.displayMasterBranchUse = ko.computed(function() {
                return this.masterBranchUse() != null ? this.masterBranchUseItem()[this.masterBranchUse()].name : null;
            }, this);

        }
    }

    export interface IFormula {
        formulaCode: string
        formulaName: string
        settingMethod: number
        nestedAtr: number
        history: Array<IGenericHistoryYearMonthPeriod>
    }

    export class Formula {
        formulaCode: KnockoutObservable<string> = ko.observable(null);
        formulaName: KnockoutObservable<string> = ko.observable(null);
        settingMethod: KnockoutObservable<number> = ko.observable(null);
        nestedAtr: KnockoutObservable<number> = ko.observable(null);
        history: KnockoutObservableArray<GenericHistoryYearMonthPeriod> = ko.observableArray([]);
        // control item
        formulaSettingMethodItem : KnockoutObservableArray<model.EnumModel> = ko.observableArray(getFormulaSettingMethodEnumModel());
        nestedAtrItem : KnockoutObservableArray<model.EnumModel> = ko.observableArray(getNestedUseClsEnumModel());
        isNotUseNestedAtr: KnockoutObservable<boolean> ;
        // display item
        displaySettingMethod: KnockoutObservable<string> = ko.observable(null);
        displayNestedAtr: KnockoutObservable<string> = ko.observable(null);
        constructor(params: IFormula) {
            this.formulaCode(params ? params.formulaCode : null);
            this.formulaName(params ? params.formulaName : null);
            this.settingMethod(params ? params.settingMethod : FORMULA_SETTING_METHOD.BASIC_SETTING);
            this.nestedAtr(params ? params.nestedAtr : NESTED_USE_CLS.NOT_USE);
            this.history(params? params.history : []);
            this.displayNestedAtr = ko.computed(function() {
                return this.nestedAtr() != null ? this.nestedAtrItem()[this.nestedAtr()].name : null;
            }, this);
            this.isNotUseNestedAtr = ko.computed(function() {
                return this.nestedAtr() == model.NESTED_USE_CLS.NOT_USE ;
            }, this);
            this.displaySettingMethod = ko.computed(function() {
                return this.settingMethod() != null ? this.formulaSettingMethodItem()[this.settingMethod()].name : null;
            }, this);
        }
    }

    export interface IDetailFormulaSetting {
        roundingMethod: number;
        roundingPosition: number;
        referenceMonth: number;
        detailCalculationFormula: Array<IDetailCalculationFormula>;
        historyId: string;
    }
    export class DetailFormulaSetting {
        roundingMethod: KnockoutObservable<number> = ko.observable(null);
        roundingPosition: KnockoutObservable<number> = ko.observable(null);
        referenceMonth: KnockoutObservable<number> = ko.observable(null);
        detailCalculationFormula: KnockoutObservableArray<IDetailCalculationFormula> = ko.observableArray([]);
        historyId: KnockoutObservable<string> = ko.observable(null);
        // control item
        roundingPositionItem: KnockoutObservableArray<EnumModel> = ko.observableArray(getRoudingPositionEnumModel());
        roundingItem: KnockoutObservableArray<EnumModel> = ko.observableArray(getRoundingEnumModel());
        referenceMonthItem: KnockoutObservableArray<EnumModel> = ko.observableArray(getReferenceMonthEnumModel());
        constructor(params: IDetailFormulaSetting) {
            this.roundingMethod(params ? params.roundingMethod : null);
            this.roundingPosition(params ? params.roundingPosition : null);
            this.referenceMonth(params ? params.referenceMonth : null);
            this.detailCalculationFormula(params ? params.detailCalculationFormula : []);
            this.historyId(params ? params.historyId : null);
        }
    }

    export interface IDetailCalculationFormula {
        elementOrder: number;
        formulaElement: string;
    }

    export class DetailCalculationFormula {

        elementOrder: KnockoutObservable<number> = ko.observable(null);
        formulaElement: KnockoutObservable<string> = ko.observable(null);
        constructor(params: IDetailCalculationFormula) {
            this.elementOrder(params ? params.elementOrder : null);
            this.formulaElement(params ? params.formulaElement : null);
        }
    }

    export interface IBasicCalculationFormula {
        calculationFormulaClassification: number;
        masterUseCode: string;
        historyID: string;
        basicCalculationFormula: number;
        standardAmountClassification: number;
        standardFixedValue: number;
        targetItemCodeList: Array<string>;
        attendanceItem: string;
        coefficientClassification: number;
        coefficientFixedValue: number;
        formulaType: number;
        roundingResult: number;
        adjustmentClassification: number;
        baseItemClassification: number;
        baseItemFixedValue: number;
        premiumRate: number;
        roundingMethod: number;
        masterUseName: string;
        displayAlreadySetting: string;
    }
    export class BasicCalculationFormula {
        // item
        calculationFormulaClassification: KnockoutObservable<number> = ko.observable(null);
        masterUseCode: KnockoutObservable<string> = ko.observable(null);
        historyID: KnockoutObservable<string> = ko.observable(null);
        basicCalculationFormula: KnockoutObservable<number> = ko.observable(null);
        standardAmountClassification: KnockoutObservable<number> = ko.observable(null);
        standardFixedValue: KnockoutObservable<number> = ko.observable(null);
        targetItemCodeList: KnockoutObservableArray<string> = ko.observableArray([]);
        attendanceItem: KnockoutObservable<string> = ko.observable(null);
        coefficientClassification: KnockoutObservable<number> = ko.observable(null);
        coefficientFixedValue: KnockoutObservable<number> = ko.observable(null);
        formulaType: KnockoutObservable<number> = ko.observable(null);
        roundingResult: KnockoutObservable<number> = ko.observable(null);
        adjustmentClassification: KnockoutObservable<number> = ko.observable(null);
        baseItemClassification: KnockoutObservable<number> = ko.observable(null);
        baseItemFixedValue: KnockoutObservable<number> = ko.observable(null);
        premiumRate: KnockoutObservable<number> = ko.observable(null);
        roundingMethod: KnockoutObservable<number> = ko.observable(null);

        // control item
        calculationFormulaClassificationItem: KnockoutObservableArray<EnumModel> = ko.observableArray(getCalculationFormulaClsEnumModel());
        calculationFormulaFixedFormulaItem: KnockoutObservableArray<EnumModel> = ko.observableArray(getCalculationFormulaClsEnumModel().slice(0, 2));
        formulaTypeItem: KnockoutObservableArray<EnumModel> = ko.observableArray(getFormulaTypeEnumModel());
        standardAmountClassificationItem: KnockoutObservableArray<EnumModel> = ko.observableArray(getStandardAmountClsEnumModel());
        baseItemClassificationItem: KnockoutObservableArray<EnumModel> = ko.observableArray(getBaseItemClsEnumModel());
        roundingMethodItem: KnockoutObservableArray<EnumModel> = ko.observableArray(getRoundingMethodEnumModel());
        coefficientClassificationItem: KnockoutObservableArray<EnumModel> = ko.observableArray(getCoefficientClassificationEnumModel());
        roundingResultItem: KnockoutObservableArray<EnumModel> = ko.observableArray(getRoundingResultEnumModel());
        adjustmentClassificationItem: KnockoutObservableArray<EnumModel> = ko.observableArray(getAdjustmentClsEnumModel());
        // display item
        masterUseName: KnockoutObservable<string> = ko.observable(null);
        displayFormulaType: KnockoutObservable<string> = ko.observable(null);
        displayFormulaImagePath: KnockoutObservable<string> = ko.observable(null);

        displaySetting: KnockoutObservable<string> = ko.observable(null);

        constructor(params: IBasicCalculationFormula) {
            this.masterUseCode(params ? params.masterUseCode : null);
            this.calculationFormulaClassification(params ? params.calculationFormulaClassification : CALCULATION_FORMULA_CLS.FIXED_VALUE);
            this.basicCalculationFormula(params ? params.basicCalculationFormula : null);
            this.premiumRate(params ? params.premiumRate : null);
            this.roundingMethod(params ? params.roundingMethod : ROUNDING_METHOD.ROUND_OFF);
            this.roundingResult(params ? params.roundingResult : ROUNDING_RESULT.ROUND_OFF);
            this.adjustmentClassification(params ? params.adjustmentClassification : null);
            this.formulaType(params ? params.formulaType : null);
            this.standardAmountClassification(params ? params.standardAmountClassification : null);
            this.standardFixedValue(params ? params.standardFixedValue : null);
            this.baseItemClassification(params ? params.baseItemClassification : null);
            this.baseItemFixedValue(params ? params.baseItemFixedValue : null);
            this.attendanceItem(params ? params.attendanceItem : null);
            this.coefficientClassification(params ? params.coefficientClassification : null);
            this.coefficientFixedValue(params ? params.coefficientFixedValue : null);
            this.historyID(params ? params.historyID : null);
            this.targetItemCodeList(params ? params.targetItemCodeList : []);
            this.masterUseName(params ? params.masterUseName : null);
            this.displayFormulaType = ko.computed(function() {
                return this.formulaType() != null ? this.formulaTypeItem()[this.formulaType()].name : null
            }, this);
            this.displaySetting = ko.computed(function() {
                if (this.formulaType() != null) return "設定済み";
                return "";
            }, this);
            this.displayFormulaImagePath = ko.computed(function() {
                if (this.formulaType() == FORMULA_TYPE.CALCULATION_FORMULA_TYPE_1) return "../resource/QMM017_1.png";
                if (this.formulaType() == FORMULA_TYPE.CALCULATION_FORMULA_TYPE_2) return "../resource/QMM017_2.png";
                if (this.formulaType() == FORMULA_TYPE.CALCULATION_FORMULA_TYPE_3) return "../resource/QMM017_3.png";
                return null;
            }, this);
            ko.computed(function() {
                this.formulaType();
                nts.uk.ui.errors.clearAll();
            }, this);
        }
    }

    // 賃金テーブル
    export interface IWageTable {
        code: string,
        name: string
        remarkInformation: string,
    }
    // 賃金テーブル
    export class WageTable {
        code: KnockoutObservable<string> = ko.observable(null);
        name: KnockoutObservable<string> = ko.observable(null);
        remarkInformation: KnockoutObservable<string> = ko.observable(null);
        constructor(params: IWageTable) {
            let self = this;
            this.code(params ? params.code : null);
            this.name(params ? params.name : null);
            this.remarkInformation(params ? params.remarkInformation : null);
        }
    }

    // 年月期間の汎用履歴項目
    export interface IGenericHistoryYearMonthPeriod {
        startMonth: string;
        endMonth: string;
        historyID: string;
    }

    // 年月期間の汎用履歴項目
    export class GenericHistoryYearMonthPeriod {
        // Item
        startMonth: KnockoutObservable<string> = ko.observable(null);
        endMonth: KnockoutObservable<string> = ko.observable(null);
        historyID: KnockoutObservable<string> = ko.observable(null);
        // display item
        displayStartMonth: any;
        displayEndMonth: any;
        displayJapanStartYearMonth: any;

        constructor(params: IGenericHistoryYearMonthPeriod) {
            this.startMonth(params ? params.startMonth : "");
            this.endMonth(params ? params.endMonth : "");
            this.historyID(params ? params.historyID : "");
            this.displayStartMonth = ko.computed(function() {
                return this.startMonth() ? nts.uk.time.parseYearMonth(this.startMonth()).format() : "";
            }, this);
            this.displayEndMonth = ko.computed(function() {
                return this.endMonth() ? nts.uk.time.parseYearMonth(this.endMonth()).format() : "";
            }, this);
            this.displayJapanStartYearMonth = ko.computed(function() {
                return this.startMonth() ? nts.uk.time.yearmonthInJapanEmpire(this.startMonth()).toString().split(' ').join(''): "";
            }, this);
        }
    }
}