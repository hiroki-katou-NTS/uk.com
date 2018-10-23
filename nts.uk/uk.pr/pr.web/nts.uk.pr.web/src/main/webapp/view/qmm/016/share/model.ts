module nts.uk.pr.view.qmm016.share.model {

    export enum SCREEN_MODE {
        NEW = 0,
        UPDATE = 1
    }
    // 要素設定
    export enum ELEMENT_SETTING {
        FIRST_DIMENSION = 0,
        SECOND_DIMENSION = 1,
        THIRD_DIMENSION = 2,
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
        M0001 = 0,
        M0002 = 1,
        M0003 = 2,
        M0004 = 3,
        M0005 = 4,
        M0006 = 5,
        M0007 = 6,
        N0001 = 7,
        N0002 = 8,
        N0003 = 9,
    }

    export function getElementItemModel () {
        return [
            new ItemModel(ELEMENT_SETTING.FIRST_DIMENSION, '一次元'),
            new ItemModel(ELEMENT_SETTING.SECOND_DIMENSION, '二次元'),
            new ItemModel(ELEMENT_SETTING.THIRD_DIMENSION, '三次元'),
            new ItemModel(ELEMENT_SETTING.QUALIFICATION, '資格'),
            new ItemModel(ELEMENT_SETTING.FINE_WORK, '精皆勤')
        ];
    }

    export class ItemModel {
        value: number;
        name: string;

        constructor(value: number, name: string) {
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
        elementSettingItem1: KnockoutObservableArray<ItemModel> = ko.observableArray(getElementItemModel().splice(0, 3));
        elementSettingItem2: KnockoutObservableArray<ItemModel> = ko.observableArray(getElementItemModel().splice(3, 5));
        imagePath: KnockoutObservable<string> = ko.observable(null);
        constructor(params: IWageTable) {
            let self = this;
            this.cid(params ? params.cid : null);
            this.wageTableCode(params ? params.wageTableCode : null);
            this.wageTableName(params ? params.wageTableName : null);
            this.elementInformation(new ElementInformation(params ? params.elementInformation : null));
            this.elementSetting(params ? params.elementSetting : null);
            this.remarkInformation(params ? params.remarkInformation : null);
            this.history(params ? params.history.map(item => new GenericHistoryYearMonthPeriod(item)) : []);
            this.elementSetting.subscribe(newValue => {
                self.changeImagePath(newValue);
            });
            self.changeImagePath(params ? params.elementSetting : null);
        }
        changeImagePath (elementSetting: number) {
            let self = this;
            let imgName = "";
            switch (elementSetting) {
                case ELEMENT_SETTING.FIRST_DIMENSION: {imgName = "QMM017_1.png"; break;}
                case ELEMENT_SETTING.SECOND_DIMENSION: {imgName = "QMM017_2.png"; break;}
                case ELEMENT_SETTING.THIRD_DIMENSION: {imgName = "QMM017_3.png"; break;}
                case ELEMENT_SETTING.QUALIFICATION: {imgName = "QMM017_4.png"; break;}
                case ELEMENT_SETTING.FINE_WORK: {imgName = "QMM017_5.png"; break;}
            }
            if (imgName) self.imagePath("../resource/" + imgName);
            else self.imagePath("");
        }
    }

    // 要素情報
    export interface IElementInformation{
        firstDimensionElement: IElementAttribute,
        secondDimensionElement: IElementAttribute,
        thirdDimensionElement: IElementAttribute,
    }
    // 要素情報
    export class ElementInformation{
        firstDimensionElement: KnockoutObservable<ElementAttribute> = ko.observable(null);
        secondDimensionElement: KnockoutObservable<ElementAttribute> = ko.observable(null);
        thirdDimensionElement: KnockoutObservable<ElementAttribute> = ko.observable(null);
        constructor (params: IElementInformation) {
            this.firstDimensionElement(new ElementAttribute(params ? params.firstDimensionElement: null));
            this.secondDimensionElement(new ElementAttribute(params ? params.secondDimensionElement: null));
            this.thirdDimensionElement(new ElementAttribute(params ? params.thirdDimensionElement: null));
        }
    }


    // 要素の属性
    export interface IElementAttribute {
        masterNumericClassification: number,
        fixedElement: number,
        optionalAdditionalElement: string
    }
    // 要素の属性
    export class ElementAttribute {
        masterNumericClassification: KnockoutObservable<number> = ko.observable(null);
        fixedElement: KnockoutObservable<number> = ko.observable(null);
        optionalAdditionalElement: KnockoutObservable<string> = ko.observable(null);
        constructor (params: IElementAttribute) {
            this.masterNumericClassification(params ? params.masterNumericClassification : null);
            this.fixedElement(params ? params.fixedElement : null);
            this.optionalAdditionalElement(params ? params.optionalAdditionalElement : null);
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
            this.eligibleQualificationCode(params ? params.eligibleQualificationCode: []);
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
        lowerFrameLimit: number,
        upperFrameLimit: number
    }
    // Merge domain
    // 要素項目
    // 要素項目（マスタ）
    // 要素項目（数値）
    export class ElementItem {
        masterCode: KnockoutObservable<string> = ko.observable(null);
        frameNumber: KnockoutObservable<number> = ko.observable(null);
        lowerFrameLimit: KnockoutObservable<number> = ko.observable(null);
        upperFrameLimit: KnockoutObservable<number> = ko.observable(null);
        constructor (params: IElementItem) {
            this.masterCode(params ? params.masterCode : null);
            this.frameNumber(params ? params.frameNumber : null);
            this.lowerFrameLimit(params ? params.lowerFrameLimit : null);
            this.upperFrameLimit(params ? params.upperFrameLimit : null);
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
    export interface ICredentialGroupSetting {
        qualificationGroupCode: string;
        paymentMethod: number;
        eligibleQualificationCode: Array<string>;
        qualificationGroupName: string;
        companyID: string;
    }
    // 資格グループ設定
    export class CredentialGroupSetting {
        qualificationGroupCode: KnockoutObservable<string> = ko.observable(null);
        paymentMethod: KnockoutObservable<number> = ko.observable(null);
        eligibleQualificationCode: KnockoutObservableArray<string> = ko.observableArray([]);
        qualificationGroupName: KnockoutObservable<string> = ko.observable(null);
        companyID: KnockoutObservable<string> = ko.observable(null);
        constructor(params: ICredentialGroupSetting) {
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
    
    export interface IWageTableTreeNode {
        wageTableCode: string,
        wageTableName: string,
        nodeText: string,
        childs: Array<WageTableTreeNode>,
        identifier: string;
    }

    export class WageTableTreeNode {
        wageTableCode: string;
        wageTableName: string;
        nodeText: string;
        childs: Array<WageTableTreeNode>;
        identifier: string;
        constructor(params: IWageTableTreeNode) {
            this.wageTableCode = params ? params.wageTableCode : "";
            this.wageTableName = params ? params.wageTableName : "";
            this.nodeText = params ? params.nodeText : "";
            this.childs = params ? params.childs : [];
            this.identifier = params ? params.identifier : "";
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
        displayJapanStartYearMonth: KnockoutObservable<string> = ko.observable(null);
        constructor(params: IGenericHistoryYearMonthPeriod) {
            this.startMonth(params ? params.startMonth : "");
            this.endMonth(params ? params.endMonth : "");
            this.historyID(params ? params.historyID : "");
            this.displayJapanStartYearMonth(params ? nts.uk.time.yearmonthInJapanEmpire(params.startMonth).toString().split(' ').join('') : "")
        }
    }
}