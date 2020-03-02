module nts.uk.pr.view.qmm016.share.model {

    import getText = nts.uk.resource.getText;
    import formatNumber = nts.uk.ntsNumber.formatNumber;
    import NumberEditorOption = nts.uk.ui.option.NumberEditorOption;

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
//    export enum ELEMENT_TYPE {
//        M001 = '雇用',
//        M002 = '部門',
//        M003 = '分類',
//        M004 = '職位',
//        M005 = '給与分類',
//        M006 = '資格',
//        M007 = '精皆勤レベル',
//        N001 = '年齢',
//        N002 = '勤続年数',
//        N003 = '家族人数'
//    }

    export enum QualificationPaymentMethod {
        ADD_MULTIPLE_APPLICABLE_AMOUNT = 0,
        PAY_ONLY_ONE_HIGHEST_BENEFIT   = 1
    }

    export function getQualificationPaymentMethodItem() {
        return [
            new EnumModel(QualificationPaymentMethod.ADD_MULTIPLE_APPLICABLE_AMOUNT, getText("Enum_Qualify_Pay_Method_Add_Multiple")),
            new EnumModel(QualificationPaymentMethod.PAY_ONLY_ONE_HIGHEST_BENEFIT, getText("Enum_Qualify_Pay_Method_Only_One_Highest"))
        ];
    }

    export function getElementItemModel () {
        return [
            new EnumModel(ELEMENT_SETTING.ONE_DIMENSION, getText("Enum_Element_Setting_One_Dimension")),
            new EnumModel(ELEMENT_SETTING.TWO_DIMENSION, getText("Enum_Element_Setting_Two_Dimension")),
            new EnumModel(ELEMENT_SETTING.THREE_DIMENSION, getText("Enum_Element_Setting_Three_Dimension")),
            new EnumModel(ELEMENT_SETTING.QUALIFICATION, getText("Enum_Element_Setting_Qualification")),
            new EnumModel(ELEMENT_SETTING.FINE_WORK, getText("Enum_Element_Setting_Fine_Work"))
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
        histories: Array<IGenericHistoryYearMonthPeriod>
    }
    // 賃金テーブル
    export class WageTable {
        cid: KnockoutObservable<string> = ko.observable(null);
        wageTableCode: KnockoutObservable<string> = ko.observable(null);
        wageTableName: KnockoutObservable<string> = ko.observable(null);
        elementInformation: KnockoutObservable<ElementInformation> = ko.observable(null);
        elementSetting: KnockoutObservable<number> = ko.observable(null);
        remarkInformation: KnockoutObservable<string> = ko.observable(null);
        histories: KnockoutObservableArray<GenericHistoryYearMonthPeriod> = ko.observableArray([]);
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
            this.histories(params ? params.histories.map(item => new GenericHistoryYearMonthPeriod(item)) : []);
            this.elementSetting.subscribe(newValue => {
                self.changeImagePath(newValue);
                this.elementInformation(new ElementInformation(null));
            });
            self.changeImagePath(self.elementSetting());
        }
        changeImagePath (elementSetting: number) {
            let self = this;
            let imgName = "", elementSettingDisplayText = "";
            switch (elementSetting) {
                case ELEMENT_SETTING.ONE_DIMENSION: {
                    imgName = "＃QMM017_1.png";
                    elementSettingDisplayText = getText('QMM016_69');
                    break;
                }
                case ELEMENT_SETTING.TWO_DIMENSION: {
                    imgName = "＃QMM017_2.png";
                    elementSettingDisplayText = getText('QMM016_70');
                    break;
                }
                case ELEMENT_SETTING.THREE_DIMENSION: {
                    imgName = "＃QMM017_3.png";
                    elementSettingDisplayText = getText('QMM016_71');
                    break;
                }
                case ELEMENT_SETTING.QUALIFICATION: {
                    imgName = "＃QMM017_4.png";
                    elementSettingDisplayText = getText('QMM016_72');
                    break;
                }
                case ELEMENT_SETTING.FINE_WORK: {
                    imgName = "＃QMM017_5.png";
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
        oneDimensionalElement: IElementAttribute,
        twoDimensionalElement: IElementAttribute,
        threeDimensionalElement: IElementAttribute,
    }
    // 要素情報
    export class ElementInformation{
        oneDimensionElement: KnockoutObservable<ElementAttribute> = ko.observable(null);
        twoDimensionElement: KnockoutObservable<ElementAttribute> = ko.observable(null);
        threeDimensionElement: KnockoutObservable<ElementAttribute> = ko.observable(null);
        constructor (params: IElementInformation) {
            this.oneDimensionElement(new ElementAttribute(params ? params.oneDimensionalElement: null));
            this.twoDimensionElement(new ElementAttribute(params ? params.twoDimensionalElement: null));
            this.threeDimensionElement(new ElementAttribute(params ? params.threeDimensionalElement: null));
        }
    }


    // 要素の属性
    export interface IElementAttribute {
        masterNumericClassification: number,
        fixedElement: string,
        optionalAdditionalElement: string,
        displayName: string
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
            this.elementName(params ? params.displayName : null);
        }
    }

    // 賃金テーブル内容
    export interface IWageTableContent {
        historyID: string,
        payments: Array<IElementsCombinationPaymentAmount>,
        qualificationGroupSettings: Array<IQualificationGroupSettingContent>
    }

    // 賃金テーブル内容
    export class WageTableContent {
        historyID: KnockoutObservable<string> = ko.observable(null);
        payment: KnockoutObservableArray<any> = ko.observableArray([]);
        qualificationGroupSetting: KnockoutObservableArray<QualificationGroupSettingContent> = ko.observableArray([]);
        // control item
        paymentMethodItem: KnockoutObservableArray<model.EnumModel> = ko.observableArray(getQualificationPaymentMethodItem());
        brandNew = true;
        constructor(params: any) {
            if (params) {
                this.historyID(params.historyID);
                this.brandNew = params.brandNew;
                if (!_.isEmpty(params.list1dElements)) {
                    this.payment(params.list1dElements.map(item => new ElementItem(item)));
                }
                if (!_.isEmpty(params.list2dElements)) {
                    this.payment(params.list2dElements.map(item => new TwoDmsElementItem(item)));
                }
                if (!_.isEmpty(params.list3dElements)) {
                    this.payment(params.list3dElements.map(item => new ThreeDmsElementItem(item)));
                }
//                if (!_.isEmpty(params.listWorkElements)) {
//                    this.payment(params.listWorkElements.map(item => new ThreeDmsElementItem(item)));
//                }
                this.qualificationGroupSetting(params && !_.isEmpty(params.qualificationGroupSettings) ? params.qualificationGroupSettings.map(item => new QualificationGroupSettingContent(item)) : []);
            }
            
        }
    }

    // 資格グループ設定内容
    export interface IQualificationGroupSettingContent {
        paymentMethod: number,
        qualificationGroupCode: string,
        qualificationGroupName: string;
        eligibleQualificationCode: Array<string>
        eligibleQualification: Array<IQualificationInformation>;
    }

    // 資格グループ設定内容
    export class QualificationGroupSettingContent {
        paymentMethod: KnockoutObservable<number> = ko.observable(null);
        displayPaymentMethod: KnockoutObservable<string> = ko.observable(null);
        qualificationGroupCode: KnockoutObservable<string> = ko.observable(null);
        qualificationGroupName: KnockoutObservable<string> = ko.observable(null);
        eligibleQualificationCode: KnockoutObservableArray<string> = ko.observableArray([]);
        eligibleQualification: KnockoutObservableArray<any> = ko.observableArray([]);
        constructor (params: IQualificationGroupSettingContent) {
            this.paymentMethod(params ? params.paymentMethod: null);
            this.qualificationGroupCode(params ? params.qualificationGroupCode: null);
            this.qualificationGroupName(params ? params.qualificationGroupName: null);
            //this.eligibleQualificationCode(params ? params.eligibleQualificationCode: []);
            this.eligibleQualificationCode(params ? params.eligibleQualificationCode.map(x => {
                return ko.mapping.fromJS(x)
            }) : []);
            this.getDisplayPaymentMethod(this.paymentMethod());
            this.paymentMethod.subscribe(newValue => {
                this.getDisplayPaymentMethod(newValue);
            })
        }
        getDisplayPaymentMethod (paymentMethod) {
            this.displayPaymentMethod(getQualificationPaymentMethodItem()[paymentMethod].name);
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
        masterName: KnockoutObservable<string> = ko.observable(null);
        frameNumber: KnockoutObservable<number> = ko.observable(null);
        frameLowerLimit: KnockoutObservable<number> = ko.observable(null);
        frameUpperLimit: KnockoutObservable<number> = ko.observable(null);
        paymentAmount: KnockoutObservable<number> = ko.observable(null);
        displayText: KnockoutObservable<string> = ko.observable(null);
        constructor (params: any) {
            this.masterCode(params && params.masterCode ? params.masterCode : null);
            this.masterName(params ? params.masterName : null);
            this.frameNumber(params ? params.frameNumber : null);
            this.frameLowerLimit(params ? params.frameLowerLimit : null);
            this.frameUpperLimit(params ? params.frameUpperLimit : null);
            this.paymentAmount(params ? params.paymentAmount : null);
            if (params != null) {
                if (params.masterCode == null) {
                    this.displayText(formatNumber(params.frameLowerLimit, new NumberEditorOption({ grouplength: 3, decimallength: 2 }))
                        + getText("QMM016_31")
                        + formatNumber(params.frameUpperLimit, new NumberEditorOption({ grouplength: 3, decimallength: 2 })));
                } else if (params.frameNumber == null) {
                    this.displayText(params.masterName);
                } else {
                    this.displayText(params.frameLowerLimit);
                }
            }
        }
    }
    
    export class TwoDmsElementItem {
        masterCode: KnockoutObservable<string> = ko.observable(null);
        masterName: KnockoutObservable<string> = ko.observable(null);
        frameNumber: KnockoutObservable<number> = ko.observable(null);
        frameLowerLimit: KnockoutObservable<number> = ko.observable(null);
        frameUpperLimit: KnockoutObservable<number> = ko.observable(null);
        listSecondDms: KnockoutObservableArray<ElementItem> = ko.observableArray([]);
        displayText: KnockoutObservable<string> = ko.observable(null);
        
        constructor (params: any) {
            this.masterCode(params && params.masterCode ? params.masterCode : null);
            this.masterName(params ? params.masterName : null);
            this.frameNumber(params ? params.frameNumber : null);
            this.frameLowerLimit(params ? params.frameLowerLimit : null);
            this.frameUpperLimit(params ? params.frameUpperLimit : null);
            this.listSecondDms(params && !_.isEmpty(params.listSecondDms) ? params.listSecondDms.map(item => new ElementItem(item)) : []);
            if (params != null) {
                if (params.masterCode == null) {
                    this.displayText(formatNumber(params.frameLowerLimit, new NumberEditorOption({ grouplength: 3, decimallength: 2 }))
                        + getText("QMM016_31")
                        + formatNumber(params.frameUpperLimit, new NumberEditorOption({ grouplength: 3, decimallength: 2 })));
                } else if (params.frameNumber == null) {
                    this.displayText(params.masterName);
                } else {
                    this.displayText(params.frameLowerLimit);
                }
            }
        }
    }
    
    export class ThreeDmsElementItem {
        masterCode: KnockoutObservable<string> = ko.observable(null);
        masterName: KnockoutObservable<string> = ko.observable(null);
        frameNumber: KnockoutObservable<number> = ko.observable(null);
        frameLowerLimit: KnockoutObservable<number> = ko.observable(null);
        frameUpperLimit: KnockoutObservable<number> = ko.observable(null);
        listFirstDms: KnockoutObservableArray<TwoDmsElementItem> = ko.observableArray([]);
        displayText: KnockoutObservable<string> = ko.observable(null);
        constructor (params: any) {
            this.masterCode(params && params.masterCode ? params.masterCode : null);
            this.masterName(params ? params.masterName : null);
            this.frameNumber(params ? params.frameNumber : null);
            this.frameLowerLimit(params ? params.frameLowerLimit : null);
            this.frameUpperLimit(params ? params.frameUpperLimit : null);
            this.listFirstDms(params && !_.isEmpty(params.listFirstDms) ? params.listFirstDms.map(item => new TwoDmsElementItem(item)) : []);
            if (params != null) {
                if (params.masterCode == null) {
                    this.displayText(formatNumber(params.frameLowerLimit, new NumberEditorOption({ grouplength: 3, decimallength: 2 }))
                        + getText("QMM016_31")
                        + formatNumber(params.frameUpperLimit, new NumberEditorOption({ grouplength: 3, decimallength: 2 })));
                } else if (params.frameNumber == null) {
                    this.displayText(params.masterName);
                } else {
                    this.displayText(params.frameLowerLimit);
                }
            }
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
        valueChanged: boolean = false;
        
        constructor (params: IElementRange) {
            this.stepIncrement(params ? params.stepIncrement : null);
            this.rangeLowerLimit(params ? params.rangeLowerLimit : null);
            this.rangeUpperLimit(params ? params.rangeUpperLimit : null);
            this.stepIncrement.subscribe(value => {
                this.valueChanged = true;
            });
            this.rangeLowerLimit.subscribe(value => {
                this.valueChanged = true;
            });
            this.rangeUpperLimit.subscribe(value => {
                this.valueChanged = true;
            });
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
        startMonth: number;
        endMonth: number;
        historyID: string;
    }

    // 年月期間の汎用履歴項目
    export class GenericHistoryYearMonthPeriod {

        // Item
        startMonth: KnockoutObservable<number> = ko.observable(null);
        endMonth: KnockoutObservable<number> = ko.observable(null);
        historyID: KnockoutObservable<string> = ko.observable(null);
        // display item
        displayStartMonth: any;
        displayEndMonth: any;
        displayJapanStartYearMonth: any;

        constructor(params: IGenericHistoryYearMonthPeriod) {
            this.startMonth(params ? params.startMonth : null);
            this.endMonth(params ? params.endMonth : null);
            this.historyID(params ? params.historyID : "");
            this.displayStartMonth = ko.computed(function() {
                return this.startMonth() ? nts.uk.time.formatYearMonth(this.startMonth()): "";
            }, this);
            this.displayEndMonth = ko.computed(function() {
                return this.endMonth() ? nts.uk.time.formatYearMonth(this.endMonth()) : "";
            }, this);
            this.displayJapanStartYearMonth = ko.computed(function() {
                return this.startMonth() ? "(" + nts.uk.time.yearmonthInJapanEmpire(this.startMonth()).toString().split(' ').join('') + ")" : "";
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