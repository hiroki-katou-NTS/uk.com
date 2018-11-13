module nts.uk.pr.view.qmm017.share.model {

    import getText = nts.uk.resource.getText;

    export enum SCREEN_MODE {
        NEW = 0,
        UPDATE = 1,
        ADD_HISTORY =2
    }
    export enum TAKEOVER_METHOD {
        FROM_LAST_HISTORY = 0,
        FROM_BEGINNING = 1
    }

    export enum MODIFY_METHOD {
        DELETE = 0,
        UPDATE = 1
    }

    export enum ELEMENT_TYPE {
        EMPLOYMENT = 'M001',
        DEPARTMENT = 'M002',
        CLASSIFICATION = 'M003',
        JOB_TITLE = 'M004',
        SALARY_CLASSIFICATION = 'M005',
        QUALIFICATION = 'M006',
        FINE_WORK = 'M007',
        AGE = 'N001',
        SERVICE_YEAR = 'N002',
        FAMILY_MEMBER = 'N003'
    }

    export function getElementTypeItemModel () {
        return [
            new ItemModel(ELEMENT_TYPE.EMPLOYMENT, '雇用'),
            new ItemModel(ELEMENT_TYPE.DEPARTMENT, '部門'),
            new ItemModel(ELEMENT_TYPE.CLASSIFICATION, '分類'),
            new ItemModel(ELEMENT_TYPE.JOB_TITLE, '職位'),
            new ItemModel(ELEMENT_TYPE.SALARY_CLASSIFICATION, '給与分類'),
            new ItemModel(ELEMENT_TYPE.QUALIFICATION, '資格'),
            new ItemModel(ELEMENT_TYPE.FINE_WORK, '精皆勤レベル'),
            new ItemModel(ELEMENT_TYPE.AGE, '年齢'),
            new ItemModel(ELEMENT_TYPE.SERVICE_YEAR, '勤続年数'),
            new ItemModel(ELEMENT_TYPE.FAMILY_MEMBER, '家族人数'),
        ];
    }

    export enum FORMULA_SETTING_METHOD {
        SIMPLE_SETTING = 0,
        DETAIL_SETTING = 1
    }

    export function getFormulaSettingMethodEnumModel () {
        return [
            new EnumModel(FORMULA_SETTING_METHOD.SIMPLE_SETTING, 'かんたん設定'),
            new EnumModel(FORMULA_SETTING_METHOD.DETAIL_SETTING, '詳細設定')
        ];
    }

    export enum NESTED_USE_CLS {
        NOT_USE = 0,
        USE = 1
    }

    export function getNestedUseClsEnumModel () {
        return [
            new EnumModel(NESTED_USE_CLS.USE, '利用可能'),
            new EnumModel(NESTED_USE_CLS.NOT_USE, '利用不可')
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
            new EnumModel(ROUNDING_POSITION.ONE_YEN, '1円'),
            new EnumModel(ROUNDING_POSITION.TEN_YEN, '10円'),
            new EnumModel(ROUNDING_POSITION.ONE_HUNDRED_YEN, '100円'),
            new EnumModel(ROUNDING_POSITION.ONE_THOUSAND_YEN, '1000円')
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

    export function getRoudingEnumModel () {
        return [
            new EnumModel(ROUNDING.ROUND_UP, '切り上げ '),
            new EnumModel(ROUNDING.TRUNCATION, '切り捨て'),
            new EnumModel(ROUNDING.DOWN_1_UP_2, '一捨二入'),
            new EnumModel(ROUNDING.DOWN_2_UP_3, '二捨三入'),
            new EnumModel(ROUNDING.DOWN_3_UP_4, '三捨四入'),
            new EnumModel(ROUNDING.DOWN_4_UP_5, '四捨五入'),
            new EnumModel(ROUNDING.DOWN_5_UP_6, '五捨六入'),
            new EnumModel(ROUNDING.DOWN_6_UP_7, '六捨七入'),
            new EnumModel(ROUNDING.DOWN_7_UP_8, '七捨八入'),
            new EnumModel(ROUNDING.DOWN_8_UP_9, '八捨九入')
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
            new EnumModel(REFERENCE_MONTH.CURRENT_MONTH, '当月 '),
            new EnumModel(REFERENCE_MONTH.ONE_MONTH_AGO, '１ヶ月前'),
            new EnumModel(REFERENCE_MONTH.TWO_MONTH_AGO, '２ヶ月前'),
            new EnumModel(REFERENCE_MONTH.THREE_MONTH_AGO, '３ヶ月前'),
            new EnumModel(REFERENCE_MONTH.FOUR_MONTH_AGO, '４ヶ月前'),
            new EnumModel(REFERENCE_MONTH.FIVE_MONTH_AGO, '５ヶ月前'),
            new EnumModel(REFERENCE_MONTH.SIX_MONTH_AGO, '６ヶ月前'),
            new EnumModel(REFERENCE_MONTH.SEVEN_MONTH_AGO, '７ヶ月前'),
            new EnumModel(REFERENCE_MONTH.EIGHT_MONTH_AGO, '８ヶ月前'),
            new EnumModel(REFERENCE_MONTH.NINE_MONTH_AGO, '９ヶ月前'),
            new EnumModel(REFERENCE_MONTH.TEN_MONTH_AGO, '１０ヶ月前'),
            new EnumModel(REFERENCE_MONTH.ELEVEN_MONTH_AGO, '１１ヶ月前'),
            new EnumModel(REFERENCE_MONTH.TWELVE_MONTH_AGO, '１２ヶ月前')
        ];
    }

    // 要素設定
    export enum ELEMENT_SETTING {
        ONE_DIMENSION = 0,
        TWO_DIMENSION = 1,
        THREE_DIMENSION = 2,
        QUALIFICATION = 3,
        FINE_WORK = 4
    }
    export function getElementSettingEnumModel () {
        return [
            new EnumModel(ELEMENT_SETTING.ONE_DIMENSION, '一次元'),
            new EnumModel(ELEMENT_SETTING.TWO_DIMENSION, '二次元'),
            new EnumModel(ELEMENT_SETTING.THREE_DIMENSION, '三次元'),
            new EnumModel(ELEMENT_SETTING.QUALIFICATION, '資格'),
            new EnumModel(ELEMENT_SETTING.FINE_WORK, '精皆勤')
        ];
    }
    // マスタ数値区分
    export enum MASTER_NUMERIC_INFORMATION {
        MASTER_FIELD = 0,
        NUMERIC_ITEM = 1
    }

    export function getMasterNumericInfoEnumModel () {
        return [
            new EnumModel(MASTER_NUMERIC_INFORMATION.MASTER_FIELD, 'マスタ項目'),
            new EnumModel(MASTER_NUMERIC_INFORMATION.NUMERIC_ITEM, '数値項目')
        ];
    }

    export enum MASTER_BRANCH_USE {
        NOT_USE = 0,
        USE = 1
    }

    export function getMasterBranchUseEnumModel () {
        return [
            new EnumModel(MASTER_BRANCH_USE.NOT_USE, '利用しない'),
            new EnumModel(MASTER_BRANCH_USE.USE, '利用する')
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
            new EnumModel(MASTER_USE.EMPLOYMENT, '雇用'),
            new EnumModel(MASTER_USE.DEPARTMENT, '部門'),
            new EnumModel(MASTER_USE.CLASSIFICATION, '分類'),
            new EnumModel(MASTER_USE.JOB_TITLE, '職位'),
            new EnumModel(MASTER_USE.SALARY_CLASSIFICATION, '給与分類'),
            new EnumModel(MASTER_USE.SALARY_FROM, '給与形態')
        ];
    }

    export enum CALCULATION_FORMULA_CLS {
        FIXED_VALUE = 0,
        FORMULA = 1,
        DEFINITION_FORMULA = 2,
    }

    export function getCalculationFormulaClsEnumModel () {
        return [
            new EnumModel(CALCULATION_FORMULA_CLS.FIXED_VALUE, '固定値'),
            new EnumModel(CALCULATION_FORMULA_CLS.FORMULA, '計算式'),
            new EnumModel(CALCULATION_FORMULA_CLS.DEFINITION_FORMULA, '既定計算式')
        ];
    }

    export enum FORMULA_TYPE {
        CALCULATION_FORMULA_TYPE_1 = 0,
        CALCULATION_FORMULA_TYPE_2 = 1,
        CALCULATION_FORMULA_TYPE_3 = 2
    }

    export function getFormulaTypeEnumModel () {
        return [
            new EnumModel(FORMULA_TYPE.CALCULATION_FORMULA_TYPE_1, '計算式タイプ１'),
            new EnumModel(FORMULA_TYPE.CALCULATION_FORMULA_TYPE_2, '計算式タイプ２'),
            new EnumModel(FORMULA_TYPE.CALCULATION_FORMULA_TYPE_3, '計算式タイプ３')
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
            new EnumModel(ROUNDING_METHOD.ROUND_OFF, '切り上げ'),
            new EnumModel(ROUNDING_METHOD.ROUND_UP, 'プラス調整'),
            new EnumModel(ROUNDING_METHOD.TRUNCATION, 'マイナス調整'),
            new EnumModel(ROUNDING_METHOD.DO_NOTHING, 'プラスマイナス反転')
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
            new EnumModel(ADJUSTMENT_CLASSIFICATION.NOT_ADJUST, '調整しない'),
            new EnumModel(ADJUSTMENT_CLASSIFICATION.PLUS_ADJUST, 'プラス調整'),
            new EnumModel(ADJUSTMENT_CLASSIFICATION.MINUS_ADJUST, 'マイナス調整'),
            new EnumModel(ADJUSTMENT_CLASSIFICATION.PLUS_MINUS_ADJUST, 'プラスマイナス反転')
        ];
    }

    export enum ROUNDING_RESULT {
        ROUND_OFF = 0,
        ROUND_UP = 1,
        TRUNCATION = 2
    }

    export function getRoundingResultEnumModel () {
        return [
            new EnumModel(ROUNDING_RESULT.ROUND_OFF, '四捨五入'),
            new EnumModel(ROUNDING_RESULT.ROUND_UP, '切り上げ'),
            new EnumModel(ROUNDING_RESULT.TRUNCATION, '切り捨て')
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
            new EnumModel(STANDARD_AMOUNT_CLS.FIXED_AMOUNT, '固定金額'),
            new EnumModel(STANDARD_AMOUNT_CLS.PAYMENT_ITEM, '支給項目'),
            new EnumModel(STANDARD_AMOUNT_CLS.DEDUCTION_ITEM, '控除項目'),
            new EnumModel(STANDARD_AMOUNT_CLS.COMPANY_UNIT_PRICE_ITEM, '会社単価項目'),
            new EnumModel(STANDARD_AMOUNT_CLS.INDIVIDUAL_UNIT_PRICE_ITEM, '個人単価項目')
        ];
    }

    export enum COEFFICIENT_CLASSIFICATION {
        FIXED_VALUE = 0,
        WORKDAY = 1,
        WORKDAY_AND_HOLIDAY = 2
    }

    export function getCoefficientClassificationEnumModel () {
        return [
            new EnumModel(COEFFICIENT_CLASSIFICATION.FIXED_VALUE, '固定値'),
            new EnumModel(COEFFICIENT_CLASSIFICATION.WORKDAY, '出勤日数'),
            new EnumModel(COEFFICIENT_CLASSIFICATION.WORKDAY_AND_HOLIDAY, '出勤日数＋年休使用数')
        ];
    }

    export enum BASE_ITEM_CLASSIFICATION {
        FIXED_VALUE = 0,
        STANDARD_DAY = 1,
        WORKDAY = 2,
        ATTENDANCE_DAY = 3,
        ATTENDANCE_DAY_AND_HOLIDAY = 4,
        REFERENCE_DATE_TIME = 5,
        SERVICE_DAY_MUL_REFER_TIME = 6,
        WORKDAY_MUL_REFER_TIME = 7,
        WORKDAY_AND_HOLIDAY_MUL_REFER_TIME = 8,
        ATTENDANCE_TIME = 9
    }

    export function getBaseItemClsEnumModel () {
        return [
            new EnumModel(BASE_ITEM_CLASSIFICATION.FIXED_VALUE, '固定値'),
            new EnumModel(BASE_ITEM_CLASSIFICATION.STANDARD_DAY, '基準日数'),
            new EnumModel(BASE_ITEM_CLASSIFICATION.WORKDAY, '要勤務日数'),
            new EnumModel(BASE_ITEM_CLASSIFICATION.ATTENDANCE_DAY, '出勤日数'),
            new EnumModel(BASE_ITEM_CLASSIFICATION.ATTENDANCE_DAY_AND_HOLIDAY, '出勤日数＋年休使用数'),
            new EnumModel(BASE_ITEM_CLASSIFICATION.REFERENCE_DATE_TIME, '基準日数×基準時間'),
            new EnumModel(BASE_ITEM_CLASSIFICATION.SERVICE_DAY_MUL_REFER_TIME, '要勤務日数×基準時間'),
            new EnumModel(BASE_ITEM_CLASSIFICATION.WORKDAY_MUL_REFER_TIME, '出勤日数×基準時間'),
            new EnumModel(BASE_ITEM_CLASSIFICATION.WORKDAY_AND_HOLIDAY_MUL_REFER_TIME, '（出勤日数＋年休使用数）×基準時間'),
            new EnumModel(BASE_ITEM_CLASSIFICATION.ATTENDANCE_TIME, '出勤時間'),
        ];
    }

    export enum LINE_ITEM_CATEGORY {
        PAYMENT_ITEM = 0,
        DEDUCTION_ITEM = 1,
        ATTENDANCE_ITEM = 2
    }

    export function getLineItemCategoryEnumModel () {
        return [
            new EnumModel(LINE_ITEM_CATEGORY.PAYMENT_ITEM, '支給項目'),
            new EnumModel(LINE_ITEM_CATEGORY.DEDUCTION_ITEM, '控除項目'),
            new EnumModel(LINE_ITEM_CATEGORY.ATTENDANCE_ITEM, '勤怠項目')
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
            new EnumModel(UNIT_PRICE_ITEM_CATEGORY.COMPANY_UNIT_PRICE_ITEM, '会社単価項目'),
            new EnumModel(UNIT_PRICE_ITEM_CATEGORY.INDIVIDUAL_UNIT_PRICE_ITEM, '個人単価項目')
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
            new EnumModel(FUNCTION_CLASSIFICATION.ALL, '全て'),
            new EnumModel(FUNCTION_CLASSIFICATION.TIME_FUNCTION, '勤怠系の関数'),
            new EnumModel(FUNCTION_CLASSIFICATION.PAYROLL_SYSTEM, '給与系の関数'),
            new EnumModel(FUNCTION_CLASSIFICATION.LOGIC, '論理'),
            new EnumModel(FUNCTION_CLASSIFICATION.STRING_OPERATION, '文字列操作'),
            new EnumModel(FUNCTION_CLASSIFICATION.DATETIME, '日付/時刻'),
            new EnumModel(FUNCTION_CLASSIFICATION.MATHEMATICS, '数学')
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
            new EnumModel(FUNCTION_CLASSIFICATION.ALL, '全て')
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
        NUMBER_OF_FAMILY_MEMBER = 8,
        ADDITIONAL_YEARMONTH = 9,
        YEAR_EXTRACTION = 10,
        MONTH_EXTRACTION = 11
    }

    export function getFunctionListEnumModel () {
        return [
            new EnumModel(FUNCTION_LIST.CONDITIONAL_EXPRESSION, '条件式'),
            new EnumModel(FUNCTION_LIST.AND, 'かつ'),
            new EnumModel(FUNCTION_LIST.OR, 'または'),
            new EnumModel(FUNCTION_LIST.ROUND_OFF, '四捨五入'),
            new EnumModel(FUNCTION_LIST.TRUNCATION, '切り捨て'),
            new EnumModel(FUNCTION_LIST.ROUND_UP, '切り上げ'),
            new EnumModel(FUNCTION_LIST.MAX_VALUE, '最大値'),
            new EnumModel(FUNCTION_LIST.MIN_VALUE, '最小値'),
            new EnumModel(FUNCTION_LIST.NUMBER_OF_FAMILY_MEMBER, '家族人数'),
            new EnumModel(FUNCTION_LIST.ADDITIONAL_YEARMONTH, '年月加算'),
            new EnumModel(FUNCTION_LIST.YEAR_EXTRACTION, '年抽出'),
            new EnumModel(FUNCTION_LIST.MONTH_EXTRACTION, '月抽出')
        ];
    }

    export function getFunctionListItem () {
        return ko.observableArray(getFunctionListEnumModel());
    }

    export enum SYSTEM_VARIABLE_LIST {
        SYSTEM_YMD_DATE = 0,
        SYSTEM_YM_DATE = 1,
        SYSTEM_Y_DATE = 2,
        PROCESSING_DATE = 3,
        PROCESSING_YEAR = 4,
        REFERENCE_TIME = 5,
        STANDARD_DAY = 6,
        WORKDAY = 7
    }

    export function getSystemVariableListEnumModel () {
        return [
            new EnumModel(SYSTEM_VARIABLE_LIST.SYSTEM_YMD_DATE, 'システム日付（年月日）'),
            new EnumModel(SYSTEM_VARIABLE_LIST.SYSTEM_YM_DATE, 'システム日付（年月）'),
            new EnumModel(SYSTEM_VARIABLE_LIST.SYSTEM_Y_DATE, 'システム日付（年）'),
            new EnumModel(SYSTEM_VARIABLE_LIST.PROCESSING_DATE, '処理年月'),
            new EnumModel(SYSTEM_VARIABLE_LIST.PROCESSING_YEAR, '処理年'),
            new EnumModel(SYSTEM_VARIABLE_LIST.REFERENCE_TIME, '基準時間'),
            new EnumModel(SYSTEM_VARIABLE_LIST.STANDARD_DAY, '基準日数'),
            new EnumModel(SYSTEM_VARIABLE_LIST.WORKDAY, '要勤務日数')
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

        constructor(value: string, name: string) {
            this.value = value;
            this.name = name;
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
        displayMasterUse: KnockoutObservable<string> = ko.observable(null);
        displayMasterBranchUse: KnockoutObservable<string> = ko.observable(null);

        constructor(params: IBasicFormulaSetting) {
            this.masterUse(params ? params.masterUse : MASTER_USE.EMPLOYMENT);
            this.masterBranchUse(params ? params.masterBranchUse : MASTER_BRANCH_USE.USE);
            this.historyID(params ? params.historyID : null);
            this.displayMasterUse = ko.computed(function() {
                return this.masterUse() != null ? this.masterUseItem()[this.masterUse()].name : null;
            }, this);
            this.displayMasterBranchUse = ko.computed(function() {
                return this.masterBranchUseItem()[this.masterBranchUse()].name;
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
            this.settingMethod(params ? params.settingMethod : FORMULA_SETTING_METHOD.SIMPLE_SETTING);
            this.nestedAtr(params ? params.nestedAtr : NESTED_USE_CLS.NOT_USE);
            this.history(params? params.history : []);
            this.displayNestedAtr = ko.computed(function() {
                return this.nestedAtrItem() != null ? this.nestedAtrItem()[this.nestedAtr()].name : null;
            }, this);
            this.isNotUseNestedAtr = ko.computed(function() {
                return this.nestedAtr() == model.NESTED_USE_CLS.NOT_USE;
            }, this);
            this.displaySettingMethod = ko.computed(function() {
                return this.formulaSettingMethodItem()[this.settingMethod()].name;
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
        roundingItem: KnockoutObservableArray<EnumModel> = ko.observableArray(getRoudingEnumModel());
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
        elementOrder: string;
        formulaElement: string;
    }

    export class DetailCalculationFormula {

        elementOrder: KnockoutObservable<string> = ko.observable(null);
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
        // display item
        displayFormulaType: KnockoutObservable<string> = ko.observable(null);
        displayFormulaImagePath: KnockoutObservable<string> = ko.observable(null);
        constructor(params: IBasicCalculationFormula) {
            this.masterUseCode(params ? params.masterUseCode : null);
            this.calculationFormulaClassification(params ? params.calculationFormulaClassification : CALCULATION_FORMULA_CLS.FIXED_VALUE);
            this.basicCalculationFormula(params ? params.basicCalculationFormula : null);
            this.premiumRate(params ? params.premiumRate : null);
            this.roundingMethod(params ? params.roundingMethod : null);
            this.roundingResult(params ? params.roundingResult : null);
            this.adjustmentClassification(params ? params.adjustmentClassification : null);
            this.formulaType(params ? params.formulaType : FORMULA_TYPE.CALCULATION_FORMULA_TYPE_1);
            this.standardAmountClassification(params ? params.standardAmountClassification : null);
            this.standardFixedValue(params ? params.standardFixedValue : null);
            this.targetItemCodeList(params ? params.targetItemCodeList : []);
            this.baseItemClassification(params ? params.baseItemClassification : null);
            this.baseItemFixedValue(params ? params.baseItemFixedValue : null);
            this.attendanceItem(params ? params.attendanceItem : null);
            this.coefficientClassification(params ? params.coefficientClassification : null);
            this.coefficientFixedValue(params ? params.coefficientFixedValue : null);
            this.historyID(params ? params.historyID : null);
            this.displayFormulaType = ko.computed(function() {
                return this.formulaType() != null ? this.formulaTypeItem()[this.formulaType()].name : null
            }, this);
            this.displayFormulaImagePath = ko.computed(function() {
                if (this.formulaType() == FORMULA_TYPE.CALCULATION_FORMULA_TYPE_1) return "../resource/QMM017_1.png";
                if (this.formulaType() == FORMULA_TYPE.CALCULATION_FORMULA_TYPE_2) return "../resource/QMM017_2.png";
                return "../resource/QMM017_3.png";
            }, this);
        }
    }

    // 賃金テーブル
    export interface IWageTable {
        cid: string,
        wageTableCode: string,
        wageTableName: string,
        elementInformation: IElementInformation,
        elementSetting: number,
        remarkInformation: string,
        history: Array<IGenericHistoryYearMonthPeriod>
    }
    // 賃金テーブル
    export class WageTable {
        cid: KnockoutObservable<string> = ko.observable(null);
        wageTableCode: KnockoutObservable<string> = ko.observable(null);
        wageTableName: KnockoutObservable<string> = ko.observable(null);
        elementInformation: KnockoutObservable<ElementInformation> = ko.observable(null);
        elementSetting: KnockoutObservable<number> = ko.observable(null);
        remarkInformation: KnockoutObservable<string> = ko.observable(null);
        history: KnockoutObservableArray<GenericHistoryYearMonthPeriod> = ko.observableArray([]);
        // display item
        imagePath: KnockoutObservable<string> = ko.observable(null);
        elementSettingDisplayText: KnockoutObservable<string> = ko.observable(null);
        constructor(params: IWageTable) {
            let self = this;
            this.cid(params ? params.cid : null);
            this.wageTableCode(params ? params.wageTableCode : null);
            this.wageTableName(params ? params.wageTableName : null);
            this.elementInformation(new ElementInformation(params ? params.elementInformation : null));
            this.elementSetting(params ? params.elementSetting : 0);
            this.remarkInformation(params ? params.remarkInformation : null);
            this.history(params ? params.history.map(item => new GenericHistoryYearMonthPeriod(item)) : []);
            this.elementSetting.subscribe(newValue => {
                self.changeImagePath(newValue);
            });
            self.changeImagePath(self.elementSetting());
        }
        changeImagePath (elementSetting: number) {
            let self = this;
            let imgName = "", elementSettingDisplayText = "";
            switch (elementSetting) {
                case ELEMENT_SETTING.ONE_DIMENSION: {
                    imgName = "QMM016_1.png";
                    elementSettingDisplayText = getText('QMM016_69');
                    break;
                }
                case ELEMENT_SETTING.TWO_DIMENSION: {
                    imgName = "QMM016_2.png";
                    elementSettingDisplayText = getText('QMM016_70');
                    break;
                }
                case ELEMENT_SETTING.THREE_DIMENSION: {
                    imgName = "QMM016_3.png";
                    elementSettingDisplayText = getText('QMM016_71');
                    break;
                }
                case ELEMENT_SETTING.QUALIFICATION: {
                    imgName = "QMM016_4.png";
                    elementSettingDisplayText = getText('QMM016_72');
                    break;
                }
                case ELEMENT_SETTING.FINE_WORK: {
                    imgName = "QMM016_5.png";
                    elementSettingDisplayText = getText('QMM016_73');
                    break;
                }
            }
            if (imgName){
                self.imagePath("../resource/" + imgName);
            }
            else{
                self.imagePath("");
            }
            self.elementSettingDisplayText(elementSettingDisplayText);
        }
    }

    // 要素情報
    export interface IElementInformation{
        oneDimensionElement: IElementAttribute,
        twoDimensionElement: IElementAttribute,
        threeDimensionElement: IElementAttribute,
    }
    // 要素情報
    export class ElementInformation{
        oneDimensionElement: KnockoutObservable<ElementAttribute> = ko.observable(null);
        twoDimensionElement: KnockoutObservable<ElementAttribute> = ko.observable(null);
        threeDimensionElement: KnockoutObservable<ElementAttribute> = ko.observable(null);
        constructor (params: IElementInformation) {
            this.oneDimensionElement(new ElementAttribute(params ? params.oneDimensionElement: null));
            this.twoDimensionElement(new ElementAttribute(params ? params.twoDimensionElement: null));
            this.threeDimensionElement(new ElementAttribute(params ? params.threeDimensionElement: null));
        }
    }


    // 要素の属性
    export interface IElementAttribute {
        masterNumericClassification: number,
        fixedElement: string,
        optionalAdditionalElement: string,
    }
    // 要素の属性
    export class ElementAttribute {
        masterNumericClassification: KnockoutObservable<number> = ko.observable(null);
        fixedElement: KnockoutObservable<string> = ko.observable(null);
        optionalAdditionalElement: KnockoutObservable<string> = ko.observable(null);
        // for display data
        elementName: KnockoutObservable<string> = ko.observable(null);
        constructor (params: IElementAttribute) {
            this.masterNumericClassification(params ? params.masterNumericClassification : null);
            this.fixedElement(params ? params.fixedElement : null);
            this.optionalAdditionalElement(params ? params.optionalAdditionalElement : null);
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