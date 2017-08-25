module nts.uk.com.view.cps005.b {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import service = nts.uk.com.view.cps005.a.service;
    import textUK = nts.uk.text;

    export module viewmodel {
        export class ScreenModel {
            currentItemData: KnockoutObservable<ItemDataModel>;
            constructor() {
                let self = this,
                    dataItemModel = new ItemDataModel(null);
                self.currentItemData = ko.observable(dataItemModel);
            }

            startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                dfd.resolve();
                return dfd.promise();
            }

            register() {

            }

            addUpdateData() {

            }

            removeData() {

            }

            closedDialog() {

            }
        }
    }

    export class ItemDataModel {
        personInfoItemList: KnockoutObservableArray<PersonInfoItem> = ko.observableArray([
            new PersonInfoItem({ itemCode: "I01", itemName: "Item 1", fixedAtr: 0, dataType: 1 }),
            new PersonInfoItem({ itemCode: "I02", itemName: "Item 2", fixedAtr: 1, dataType: 1 }),
            new PersonInfoItem({ itemCode: "I03", itemName: "Item 3", fixedAtr: 0, dataType: 1 }),
            new PersonInfoItem({ itemCode: "I04", itemName: "Item 4", fixedAtr: 1, dataType: 1 }),
            new PersonInfoItem({ itemCode: "I05", itemName: "Item 5", fixedAtr: 0, dataType: 1 }),
        ]);
        perInfoItemSelectCode: KnockoutObservable<string> = ko.observable("I01");
        currentItemSelected: KnockoutObservable<PersonInfoItem> = ko.observable(this.personInfoItemList()[0]);

        dataTypeEnum: Array<any> = [
            { value: 1, localizedName: "文字列(String)" },
            { value: 2, localizedName: "数値(Numeric)" },
            { value: 3, localizedName: "日付(Date)" },
            { value: 4, localizedName: "時間(Time)" },
            { value: 5, localizedName: "時刻(TimePoint)" },
            { value: 6, localizedName: ":選択(Selection)" },
        ];

        //Enum : dataTypeEnum is selected value 1 - 文字列(String)
        stringItemTypeEnum: Array<any> = [
            { value: 1, localizedName: "すべての文字(any)" },
            { value: 2, localizedName: "全ての半角文字(AnyHalfWidth)" },
            { value: 3, localizedName: "半角英数字(AlphaNumeric)" },
            { value: 4, localizedName: "半角数字(Numeric)" },
            { value: 5, localizedName: "全角カタカナ(Kana)" },
        ];

        stringItemDataTypeEnum: Array<any> = [
            { value: 2, localizedName: "可変長(VariableLength)" },
            { value: 1, localizedName: "固定長(FixedLength)" },
        ];

        //Enum : dataTypeEnum is selected value 2 - 数値(Numeric)
        numericItemAmountAtrEnum: Array<any> = [
            { code: 1, name: nts.uk.resource.getText("CPS005_50") },
            { code: 2, name: nts.uk.resource.getText("CPS005_51") },
        ];
        constructor(data: IItemData) {
            let self = this;
            if (!data) return;
            self.personInfoItemList = ko.observableArray(_.map(data.personInfoItemList, item => { return new PersonInfoItem(item) }));

            //subscribe select category code
            self.perInfoItemSelectCode.subscribe(newItemCode => {
                let cateType;
                if (textUK.isNullOrEmpty(newItemCode)) return;
                self.currentItemSelected(_.find(self.personInfoItemList(), item => { return item.itemCode == newItemCode }));
                self.currentItemSelected().fixedIsSelected(false);
                if (self.currentItemSelected().fixedAtr == true) {
                    self.currentItemSelected().fixedIsSelected(true);
                }
            });
        }
    }

    export class PersonInfoItem {
        itemCode: string = "";
        itemName: string = "";
        itemCodeKnockout: KnockoutObservable<string> = ko.observable("");
        itemNameKnockout: KnockoutObservable<string> = ko.observable("");
        fixedAtr: boolean;
        dataType: KnockoutObservable<number> = ko.observable(1)
        stringItemType: KnockoutObservable<number> = ko.observable(4);
        stringItemLeng: KnockoutObservable<number> = ko.observable(null);
        stringItemDataType: KnockoutObservable<number> = ko.observable(null);
        numericItemAmountAtr: KnockoutObservable<number> = ko.observable(null);


        historyClassificationSelected: KnockoutObservable<number> = ko.observable(1);
        historyTypesSelected: KnockoutObservable<number> = ko.observable(1);
        singleMultipleTypeSelected: KnockoutObservable<number> = ko.observable(1);
        //all visiable
        historyTypesDisplay: KnockoutObservable<boolean> = ko.observable(false);
        fixedIsSelected: KnockoutObservable<boolean> = ko.observable(false);
        constructor(data: IPersonInfoItem) {
            let self = this;
            self.itemCode = data.itemCode || "";
            self.itemName = data.itemName || "";
            self.itemCodeKnockout(data.itemCode || "");
            self.itemNameKnockout(data.itemName || "");
            self.fixedAtr = data.fixedAtr == 1 ? true : false;
            self.dataType(data.dataType || 1);
            self.stringItemType(data.stringItem.stringItemType || 4);
            self.stringItemLeng(data.stringItem.stringItemLeng || null);
            self.stringItemDataType(data.stringItem.stringItemLeng || null);
            self.numericItemAmountAtr(data.numericItem.numericItemAmountAtr || null);


            self.fixedIsSelected(self.fixedAtr);
            //subscribe select history type (1: history, 2: not history)
            self.historyClassificationSelected.subscribe(newHisClassification => {
                if (textUK.isNullOrEmpty(newHisClassification)) return;
                self.historyTypesDisplay(false);
                if (newHisClassification == 1) {
                    self.historyTypesDisplay(true);
                }
            });
        }
    }

    export class StringItemModel {
        stringItemType: KnockoutObservable<number> = ko.observable(null);
        stringItemLeng: KnockoutObservable<number> = ko.observable(null);
        stringItemDataType: KnockoutObservable<number> = ko.observable(null);
        constructor(data: IStringItem) {
            let self = this;
            if (!data) return;
            self.stringItemType(data.stringItemType || null);
            self.stringItemLeng(data.stringItemLeng || null);
            self.stringItemDataType(data.stringItemDataType || null);
        }
    }

    export class NumericItemModel {
        numericItemMin: KnockoutObservable<number> = ko.observable(null);;
        numericItemMax: KnockoutObservable<number> = ko.observable(null);;
        numericItemAmountAtr: KnockoutObservable<number> = ko.observable(null);;
        numericItemMinusAtr: KnockoutObservable<number> = ko.observable(null);;
        numericItemDecimalPart: KnockoutObservable<number> = ko.observable(null);;
        numericItemIntegerPart: KnockoutObservable<number> = ko.observable(null);;
        constructor(data: INumericItem) {
            let self = this;
            if (!data) return;
            self.numericItemMin(data.numericItemMin || null);
            self.numericItemMax(data.numericItemMax || null);
            self.numericItemAmountAtr(data.numericItemAmountAtr || null);
            self.numericItemMinusAtr(data.numericItemMinusAtr || null);
            self.numericItemDecimalPart(data.numericItemDecimalPart || null);
            self.numericItemIntegerPart(data.numericItemIntegerPart || null);
        }
    }

    export class TimeItemModel {
        timeItemMin: KnockoutObservable<number> = ko.observable(null);
        timeItemMax: KnockoutObservable<number> = ko.observable(null);
        constructor(data: ITimeItem) {
            let self = this;
            if (!data) return;
            self.timeItemMin(data.timeItemMin || null);
            self.timeItemMax(data.timeItemMax || null);
        }
    }

    export class TimePointItemModel {
        TimePointItemMinDayType: KnockoutObservable<number> = ko.observable(null);
        TimePointItemMinVal: KnockoutObservable<number> = ko.observable(null);
        TimePointItemMaxDayType: KnockoutObservable<number> = ko.observable(null);
        TimePointItemMaxVal: KnockoutObservable<number> = ko.observable(null);
        constructor(data: ITimePointItem) {
            let self = this;
            if (!data) return;
            self.TimePointItemMinDayType(data.TimePointItemMinDayType || null);
            self.TimePointItemMinVal(data.TimePointItemMinVal || null);
            self.TimePointItemMaxDayType(data.TimePointItemMaxDayType || null);
            self.TimePointItemMaxVal(data.TimePointItemMaxVal || null);
        }
    }

    export class DateItemModel {
        dateItemType: KnockoutObservable<number> = ko.observable(null);
        constructor(data: IDateItem) {
            let self = this;
            if (!data) return;
            self.dateItemType(data.dateItemType || null);
        }
    }
    
    export class selectionItemModel {
        selectionItemRefType: KnockoutObservable<number> = ko.observable(null);
        selectionItemRefCode: KnockoutObservable<number> = ko.observable(null);
        constructor(data: ISelectionItem) {
            let self = this;
            if (!data) return;
            self.selectionItemRefType(data.selectionItemRefType || null);
            self.selectionItemRefCode(data.selectionItemRefCode || null);
        }
    }
    
    interface IItemData {
        personInfoItemList: Array<IPersonInfoItem>;
    }

    interface IPersonInfoItem {
        itemCode: string;
        itemName: string;
        itemBaseName?: string;
        fixedAtr: number;
        dataType: number;
        stringItem?: IStringItem;
        numericItem?: INumericItem;
        itemType?: number;

    }

    interface IStringItem {
        stringItemType: number;
        stringItemLeng: number;
        stringItemDataType: number;
    }
    interface INumericItem {
        numericItemMin: number;
        numericItemMax: number;
        numericItemAmountAtr: number;
        numericItemMinusAtr: number;
        numericItemDecimalPart: number;
        numericItemIntegerPart: number;
    }
    interface ITimeItem {
        timeItemMin: number;
        timeItemMax: number;
    }
    interface ITimePointItem {
        TimePointItemMinDayType: number;
        TimePointItemMinVal: number;
        TimePointItemMaxDayType: number;
        TimePointItemMaxVal: number;
    }
    interface IDateItem {
        dateItemType: number;
    }
    interface ISelectionItem {
        selectionItemRefType: number;
        selectionItemRefCode: number;
    }

}

