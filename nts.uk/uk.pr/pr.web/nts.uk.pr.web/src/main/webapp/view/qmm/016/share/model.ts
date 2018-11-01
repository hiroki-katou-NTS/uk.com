module nts.uk.pr.view.qmm016.share.model {

    import getText = nts.uk.resource.getText;

    export enum SCREEN_MODE {
        NEW = 0,
        UPDATE = 1
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

    export enum QualificationPaymentMethod {
        PAY_ONLY_ONE_HIGHEST_BENEFIT = 0,
        ADD_MULTIPLE_APPLICABLE_AMOUNT = 1
    }

    export function getQualificationPaymentMethodItem() {
        return [
            new EnumModel(QualificationPaymentMethod.PAY_ONLY_ONE_HIGHEST_BENEFIT, '一番高い手当を1つだけ支給する'),
            new EnumModel(QualificationPaymentMethod.ADD_MULTIPLE_APPLICABLE_AMOUNT, '複数該当した金額を加算する')
        ];
    }

    export function getElementItemModel () {
        return [
            new EnumModel(ELEMENT_SETTING.ONE_DIMENSION, '一次元'),
            new EnumModel(ELEMENT_SETTING.TWO_DIMENSION, '二次元'),
            new EnumModel(ELEMENT_SETTING.THREE_DIMENSION, '三次元'),
            new EnumModel(ELEMENT_SETTING.QUALIFICATION, '資格'),
            new EnumModel(ELEMENT_SETTING.FINE_WORK, '精皆勤')
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
        // Item
        elementSettingItem1: KnockoutObservableArray<EnumModel> = ko.observableArray(getElementItemModel().splice(0, 3));
        elementSettingItem2: KnockoutObservableArray<EnumModel> = ko.observableArray(getElementItemModel().splice(3, 5));
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

    // 賃金テーブル内容
    export interface IWageTableContent {
        historyID: string,
        payment: Array<IElementsCombinationPaymentAmount>,
        qualificationGroupSetting: Array<IQualificationGroupSettingContent>
    }

    // 賃金テーブル内容
    export class WageTableContent {
        historyID: KnockoutObservable<string> = ko.observable(null);
        payment: KnockoutObservableArray<ElementsCombinationPaymentAmount> = ko.observableArray([]);
        qualificationGroupSetting: KnockoutObservableArray<QualificationGroupSettingContent> = ko.observableArray([]);
        // control item
        paymentMethodItem: KnockoutObservableArray<model.EnumModel> = ko.observableArray(getQualificationPaymentMethodItem());
        constructor(params: IWageTableContent) {
            this.historyID(params ? params.historyID : null);
            this.payment(params ? params.payment.map(item => new ElementsCombinationPaymentAmount(item)) : []);
            this.qualificationGroupSetting(params ? params.qualificationGroupSetting.map(item => new QualificationGroupSettingContent(item)) : []);
        }
    }

    // 資格グループ設定内容
    export interface IQualificationGroupSettingContent {
        paymentMethod: number,
        qualificationGroupCode: string,
        eligibleQualificationCode: Array<string>
    }

    // 資格グループ設定内容
    export class QualificationGroupSettingContent {
        paymentMethod: KnockoutObservable<number> = ko.observable(null);
        qualificationGroupCode: KnockoutObservable<string> = ko.observable(null);
        eligibleQualificationCode: KnockoutObservableArray<string> = ko.observableArray([]);
        constructor (params: IQualificationGroupSettingContent) {
            this.paymentMethod(params ? params.paymentMethod: null);
            this.qualificationGroupCode(params ? params.qualificationGroupCode: null);
            // this.eligibleQualificationCode([]);
        }
    }

    // 要素の組み合わせで支払う金額
    export interface IElementsCombinationPaymentAmount {
        wageTablePaymentAmount: number,
        elementAttribute: IContentElementAttribute
    }

    export class ElementsCombinationPaymentAmount {
        wageTablePaymentAmount: KnockoutObservable<number> = ko.observable(null);
        elementAttribute: KnockoutObservable<ContentElementAttribute> = ko.observable(null);
        constructor (params: IElementsCombinationPaymentAmount) {
            this.wageTablePaymentAmount(params ? params.wageTablePaymentAmount : null);
            this.elementAttribute(new ContentElementAttribute(params ? params.elementAttribute : null));
        }
    }

    // 要素属性
    export interface IContentElementAttribute {
        firstElementItem: IElementItem,
        secondElementItem: IElementItem,
        thirdElementItem: IElementItem
    }
    // 要素属性
    export class  ContentElementAttribute {
        firstElementItem: KnockoutObservable<ElementItem> = ko.observable(null);
        secondElementItem: KnockoutObservable<ElementItem> = ko.observable(null);
        thirdElementItem: KnockoutObservable<ElementItem> = ko.observable(null);
        constructor (params: IContentElementAttribute) {
            this.firstElementItem(new ElementItem(params ? params.firstElementItem : null));
            this.secondElementItem(new ElementItem(params ? params.secondElementItem : null));
            this.thirdElementItem(new ElementItem(params ? params.thirdElementItem : null));
        }
    }

    // Merge domain
    // 要素項目
    // 要素項目（マスタ）
    // 要素項目（数値）
    export interface IElementItem {
        masterCode: string,
        frameNumber: number,
        frameLowerLimit: number,
        frameUpperLimit: number
    }
    // Merge domain
    // 要素項目
    // 要素項目（マスタ）
    // 要素項目（数値）
    export class ElementItem {
        masterCode: KnockoutObservable<string> = ko.observable(null);
        frameNumber: KnockoutObservable<number> = ko.observable(null);
        frameLowerLimit: KnockoutObservable<number> = ko.observable(null);
        frameUpperLimit: KnockoutObservable<number> = ko.observable(null);
        constructor (params: IElementItem) {
            this.masterCode(params ? params.masterCode : null);
            this.frameNumber(params ? params.frameNumber : null);
            this.frameLowerLimit(params ? params.frameLowerLimit : null);
            this.frameUpperLimit(params ? params.frameUpperLimit : null);
        }
    }

    export interface IElementRangeSetting {
        historyID: string,
        firstElementRange: IElementRange,
        secondElementRange: IElementRange,
        thirdElementRange: IElementRange
    }

    export class ElementRangeSetting {
        historyID: KnockoutObservable<string> = ko.observable(null);
        firstElementRange: KnockoutObservable<ElementRange> = ko.observable(null);
        secondElementRange: KnockoutObservable<ElementRange> = ko.observable(null);
        thirdElementRange: KnockoutObservable<ElementRange> = ko.observable(null);
        constructor (params: IElementRangeSetting) {
            this.historyID(params ? params.historyID : null);
            this.firstElementRange(new ElementRange(params ? params.firstElementRange : null));
            this.secondElementRange(new ElementRange(params ? params.secondElementRange : null));
            this.thirdElementRange(new ElementRange(params ? params.thirdElementRange : null));
        }
    }

    // Merge domain
    // 要素範囲
    // 数値要素範囲
    export interface IElementRange {
        stepIncrement: number,
        rangeLowerLimit: number,
        rangeUpperLimit: number
    }
    // Merge domain
    // 要素範囲
    // 数値要素範囲
    export class ElementRange {
        stepIncrement: KnockoutObservable<number> = ko.observable(null);
        rangeLowerLimit: KnockoutObservable<number> = ko.observable(null);
        rangeUpperLimit: KnockoutObservable<number> = ko.observable(null);
        constructor (params: IElementRange) {
            this.stepIncrement(params ? params.stepIncrement : null);
            this.rangeLowerLimit(params ? params.rangeLowerLimit : null);
            this.rangeUpperLimit(params ? params.rangeUpperLimit : null);
        }
    }

    // 資格グループ設定
    export interface IQualificationGroupSetting {
        qualificationGroupCode: string;
        paymentMethod: number;
        eligibleQualificationCode: Array<string>;
        qualificationGroupName: string;
        companyID: string;
    }
    // 資格グループ設定
    export class QualificationGroupSetting {
        qualificationGroupCode: KnockoutObservable<string> = ko.observable(null);
        paymentMethod: KnockoutObservable<number> = ko.observable(null);
        eligibleQualificationCode: KnockoutObservableArray<string> = ko.observableArray([]);
        qualificationGroupName: KnockoutObservable<string> = ko.observable(null);
        companyID: KnockoutObservable<string> = ko.observable(null);
        // control item
        paymentMethodItem: KnockoutObservableArray<model.EnumModel> = ko.observableArray(getQualificationPaymentMethodItem());
        constructor(params: IQualificationGroupSetting) {
            this.qualificationGroupCode(params ? params.qualificationGroupCode : null);
            this.paymentMethod(params ? params.paymentMethod : null);
            this.eligibleQualificationCode(params ? params.eligibleQualificationCode : []);
            this.qualificationGroupName(params ? params.qualificationGroupName : null);
            this.companyID(params ? params.companyID : null);
        }
    }
    // 明細書項目
    export interface IStatementItem {
        cId: string;
        categoryAtr: number;
        itemNameCd: string;
        salaryItemId: string;
        defaultAtr: number;
        valueAtr: number;
        deprecatedAtr: number;
        socialInsuaEditableAtr: number;
        intergrateCd: number;
    }
    // 明細書項目
    export class StatementItem {
        cId: KnockoutObservable<string> = ko.observable('');
        categoryAtr: KnockoutObservable<number> = ko.observable(0);
        itemNameCd: KnockoutObservable<string> = ko.observable('');
        salaryItemId: KnockoutObservable<string> = ko.observable('');
        defaultAtr: KnockoutObservable<number> = ko.observable(0);
        valueAtr: KnockoutObservable<number> = ko.observable(0);
        deprecatedAtr: KnockoutObservable<number> = ko.observable(0);
        socialInsuaEditableAtr: KnockoutObservable<number> = ko.observable(0);
        intergrateCd: KnockoutObservable<number> = ko.observable(0);
        constructor(param: IStatementItem) {
            let self = this;
            self.cId(param ? param.cId : '');
            self.categoryAtr(param ? param.categoryAtr : 0);
            self.itemNameCd(param ? param.itemNameCd : '');
            self.salaryItemId(param ? param.salaryItemId : '');
            self.defaultAtr(param ? param.defaultAtr : 0);
            self.valueAtr(param ? param.valueAtr : 0);
            self.deprecatedAtr(param ? param.deprecatedAtr : 0);
            self.socialInsuaEditableAtr(param ? param.socialInsuaEditableAtr : 0);
            self.intergrateCd(param ? param.intergrateCd : 0);
        }
    }

    // 明細書項目名称
    export interface IStatementItemName {
        cId: string;
        salaryItemId: string;
        name: string;
        shortName: string;
        otherLanguageName: string;
        englishName: string
    }

    // 明細書項目名称
    export class StatementItemName {
        cId: KnockoutObservable<string> = ko.observable('');
        salaryItemId: KnockoutObservable<string> = ko.observable('');
        name: KnockoutObservable<string> = ko.observable('');
        shortName: KnockoutObservable<string> = ko.observable('');
        otherLanguageName: KnockoutObservable<string> = ko.observable('');
        englishName: KnockoutObservable<string> = ko.observable('');
        constructor(param: IStatementItemName) {
            let self = this;
            self.cId(param ? param.cId : '');
            self.salaryItemId(param ? param.salaryItemId : '');
            self.name(param ? param.name : '');
            self.shortName(param ? param.shortName : '');
            self.otherLanguageName(param ? param.otherLanguageName : '');
            self.englishName(param ? param.englishName : '');
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

    export interface IQualificationInformation {
        qualificationCode: string,
        qualificationName: string
    }

    export class QualificationInformation {
        qualificationCode: KnockoutObservable<string> = ko.observable(null);
        qualificationName: KnockoutObservable<string> = ko.observable(null);
        constructor (params: IQualificationInformation) {
            this.qualificationCode(params? params.qualificationCode: null);
            this.qualificationName(params? params.qualificationName: null);
        }
    }
}