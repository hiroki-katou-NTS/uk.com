module nts.uk.pr.view.qmm017.share.model {

    import getText = nts.uk.resource.getText;

    export enum SETTING_METHOD {
        SIMPLE_SETTING = 0,
        DETAIL_SETTING = 1
    }

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
        constructor(params: IFormula) {
            this.formulaCode(params ? params.formulaCode : null);
            this.formulaName(params ? params.formulaName : null);
            this.settingMethod(params ? params.settingMethod : null);
            this.nestedAtr(params ? params.nestedAtr : null);
            this.history(params? params.history : []);
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