module nts.uk.pr.view.qmm017.share.model {

    import getText = nts.uk.resource.getText;

    export enum FORMULA_SETTING_METHOD {
        SIMPLE_SETTING = 0,
        DETAIL_SETTING = 1
    }

    export enum NESTED_USE_CLS {
        NOT_USE = 0,
        USE = 1
    }

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
    // 要素設定
    export enum ELEMENT_SETTING {
        ONE_DIMENSION = 0,
        TWO_DIMENSION = 1,
        THREE_DIMENSION = 2,
        QUALIFICATION = 3,
        FINE_WORK = 4
    }
    // マスタ数値区分
    export enum MASTER_NUMERIC_INFORMATION {
        MASTER_FIELD = 0,
        NUMERIC_ITEM = 1
    }
    // 要素種類
    export enum ELEMENT_TYPE {
        M001 = '雇用',
        M002 = '部門',
        M003 = '分類',
        M004 = '職位',
        M005 = '給与分類',
        M006 = '資格',
        M007 = '精皆勤レベル',
        N001 = '年齢',
        N002 = '勤続年数',
        N003 = '家族人数',
    }

    export enum MASTER_BRANCH_USE {
        NOT_USE = 0,
        USE = 1
    }

    export enum MASTER_USE {
        EMPLOYMENT = 0,
        DEPARTMENT = 1,
        CLASSIFICATION = 2,
        JOB_TITLE = 3,
        SALARY_CLASSIFICATION = 4,
        SALARY_FROM = 5
    }

    export function getElementEnumModel () {
        return [
            new EnumModel(ELEMENT_SETTING.ONE_DIMENSION, '一次元'),
            new EnumModel(ELEMENT_SETTING.TWO_DIMENSION, '二次元'),
            new EnumModel(ELEMENT_SETTING.THREE_DIMENSION, '三次元'),
            new EnumModel(ELEMENT_SETTING.QUALIFICATION, '資格'),
            new EnumModel(ELEMENT_SETTING.FINE_WORK, '精皆勤')
        ];
    }

    export function getFormulaSettingMethodEnumModel () {
        return [
            new EnumModel(FORMULA_SETTING_METHOD.SIMPLE_SETTING, 'かんたん設定'),
            new EnumModel(FORMULA_SETTING_METHOD.DETAIL_SETTING, '詳細設定')
        ];
    }

    export function getNestedUseClsEnumModel () {
        return [
            new EnumModel(NESTED_USE_CLS.USE, '利用可能'),
            new EnumModel(NESTED_USE_CLS.NOT_USE, '利用不可')
        ];
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


    export function getMasterBranchUseEnumModel () {
        return [
            new EnumModel(MASTER_BRANCH_USE.NOT_USE, '利用しない'),
            new EnumModel(MASTER_BRANCH_USE.USE, '利用する')
        ];
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
            this.masterBranchUse(params ? params.masterBranchUse : MASTER_BRANCH_USE.NOT_USE);
            this.historyID(params ? params.historyID : null);
            this.displayMasterUse = ko.computed(function() {
                return this.masterUseItem()[this.masterUse()].name;
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
                return this.nestedAtrItem()[this.nestedAtr()].name;
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
        constructor(params: IBasicCalculationFormula) {
            this.masterUseCode(params ? params.masterUseCode : null);
            this.calculationFormulaClassification(params ? params.calculationFormulaClassification : null);
            this.basicCalculationFormula(params ? params.basicCalculationFormula : null);
            this.premiumRate(params ? params.premiumRate : null);
            this.roundingMethod(params ? params.roundingMethod : null);
            this.roundingResult(params ? params.roundingResult : null);
            this.adjustmentClassification(params ? params.adjustmentClassification : null);
            this.formulaType(params ? params.formulaType : null);
            this.standardAmountClassification(params ? params.standardAmountClassification : null);
            this.standardFixedValue(params ? params.standardFixedValue : null);
            this.targetItemCodeList(params ? params.targetItemCodeList : []);
            this.baseItemClassification(params ? params.baseItemClassification : null);
            this.baseItemFixedValue(params ? params.baseItemFixedValue : null);
            this.attendanceItem(params ? params.attendanceItem : null);
            this.coefficientClassification(params ? params.coefficientClassification : null);
            this.coefficientFixedValue(params ? params.coefficientFixedValue : null);
            this.historyID(params ? params.historyID : null);
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
            let fixedElementValue = this.fixedElement();
            this.elementName(ELEMENT_TYPE[fixedElementValue]);
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