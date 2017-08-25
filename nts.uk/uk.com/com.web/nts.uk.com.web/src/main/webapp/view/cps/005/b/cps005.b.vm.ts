module nts.uk.com.view.cps005.b {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
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
                let categoryId = "AF3714DE-507B-4E9D-BA61-4B16948A5872";
                new service.Service().getAllPerInfoItemDefByCtgId(categoryId).done(function(data: IItemData) { 
                    self.currentItemData(new ItemDataModel(data)); 
                    self.currentItemData().perInfoItemSelectCode(data.personInfoItemList ? data.personInfoItemList[0].id : "");  
//                    if(!data) return;
//                    self.currentItemData(data);            
//                    self.currentItemData().personInfoItemList(_.map(data.personInfoItemList, item => { return new PersonInfoItemShowListModel(item) }));
//                    self.currentItemData().dataTypeEnum = data.dataTypeEnum || new Array();
//                    self.currentItemData().stringItemTypeEnum = data.stringItemTypeEnum || new Array();
//                    self.currentItemData().stringItemDataTypeEnum = data.stringItemDataTypeEnum || new Array();
//                    self.currentItemData().dateItemTypeEnum = data.dateItemTypeEnum || new Array();
//                    self.currentItemData().perInfoItemSelectCode(data.personInfoItemList ? data.personInfoItemList[0].id : "");
                    dfd.resolve();
                });
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
        personInfoItemList: KnockoutObservableArray<PersonInfoItemShowListModel> = ko.observableArray([]);
        perInfoItemSelectCode: KnockoutObservable<string> = ko.observable("");
        currentItemSelected: KnockoutObservable<PersonInfoItem> = ko.observable(new PersonInfoItem(null));
        dataTypeEnum: Array<any> = [
            { value: 1, localizedName: "文字列(String)" },
            { value: 2, localizedName: "数値(Numeric)" },
            { value: 3, localizedName: "日付(Date)" },
            { value: 4, localizedName: "時間(Time)" },
            { value: 5, localizedName: "時刻(TimePoint)" },
            { value: 6, localizedName: "選択(Selection)" },
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
            { code: 0, name: nts.uk.resource.getText("CPS005_51") },
        ];
        numericItemMinusAtrEnum: Array<any> = [
            { code: 1, name: nts.uk.resource.getText("CPS005_46") },
            { code: 0, name: nts.uk.resource.getText("CPS005_47") },
        ];
        //Enum : dataTypeEnum is selected value 3 -日付(Date)
        dateItemTypeEnum: Array<any> = [
            { value: 1, localizedName: "年月日 (YearMonthDay)" },
            { value: 2, localizedName: "年月 (YearMonth)" },
            { value: 3, localizedName: "年 (Year)" },
        ];
        constructor(data: IItemData) {
            let self = this;
            if (!data) return;
            self.personInfoItemList(_.map(data.personInfoItemList, item => { return new PersonInfoItemShowListModel(item) }));
            self.dataTypeEnum = data.dataTypeEnum || new Array();
            self.stringItemTypeEnum = data.stringItemTypeEnum || new Array();
            self.stringItemDataTypeEnum = data.stringItemDataTypeEnum || new Array();
            self.dateItemTypeEnum = data.dateItemTypeEnum || new Array();
            //subscribe select category code
            self.perInfoItemSelectCode.subscribe(newItemId => {
                if (textUK.isNullOrEmpty(newItemId)) return;
                nts.uk.ui.errors.clearAll();
                new service.Service().getPerInfoItemDefById(newItemId).done(function(data: IPersonInfoItem) {
                    self.currentItemSelected(new PersonInfoItem(data));
                });
            });
        }
    }

    export class PersonInfoItem {
        id: string = "";
        itemName: KnockoutObservable<string> = ko.observable("");
        fixedAtr: boolean;
        dataType: KnockoutObservable<number> = ko.observable(1)
        stringItem: KnockoutObservable<StringItemModel> = ko.observable(new StringItemModel(null));
        numericItem: KnockoutObservable<NumericItemModel> = ko.observable(new NumericItemModel(null));
        dateItem: KnockoutObservable<DateItemModel> = ko.observable(new DateItemModel(null));
        timeItem: KnockoutObservable<TimeItemModel> = ko.observable(new TimeItemModel(null));
        timePointItem: KnockoutObservable<TimePointItemModel> = ko.observable(new TimePointItemModel(null));
        selectionItem: KnockoutObservable<SelectionItemModel> = ko.observable(new SelectionItemModel(null));

        historyClassificationSelected: KnockoutObservable<number> = ko.observable(1);
        historyTypesSelected: KnockoutObservable<number> = ko.observable(1);
        singleMultipleTypeSelected: KnockoutObservable<number> = ko.observable(1);
        //all visiable
        historyTypesDisplay: KnockoutObservable<boolean> = ko.observable(false);
        fixedIsSelected: KnockoutObservable<boolean> = ko.observable(false);
        constructor(data: IPersonInfoItem) {
            let self = this;
            if (!data) return;
            self.id = data.id || "";
            self.itemName(data.itemName || "");
            self.fixedAtr = data.fixedAtr == 1 ? true : false;
            //self.dataType(data.dataType || 1);
            self.dataType(2);
            switch (self.dataType()) {
                case 1:
                    self.stringItem(new StringItemModel(data.stringItem));
                    break;
                case 2:
                    self.numericItem(new NumericItemModel(data.numericItem));
                    break;
                case 3:
                    self.dateItem(new DateItemModel(data.dateItem));
                    break;
                case 4:
                    self.timeItem(new TimeItemModel(data.timeItem));
                    break;
                case 5:
                    self.timePointItem(new TimePointItemModel(data.timePointItem));
                    break;
                case 6:
                    self.selectionItem(new SelectionItemModel(data.selectionItem));
                    break;
            }
            self.fixedIsSelected(self.fixedAtr);
        }
    }

    export class StringItemModel {
        stringItemType: KnockoutObservable<number> = ko.observable(4);
        stringItemLength: KnockoutObservable<number> = ko.observable(null);
        stringItemDataType: KnockoutObservable<number> = ko.observable(2);
        constructor(data: IStringItem) {
            let self = this;
            if (!data) return;
            self.stringItemType(data.stringItemType || 4);
            self.stringItemLength(data.stringItemLength || null);
            self.stringItemDataType(data.stringItemDataType || 2);
        }
    }
    export class NumericItemModel {
        numericItemMin: KnockoutObservable<number> = ko.observable(null);
        numericItemMax: KnockoutObservable<number> = ko.observable(null);
        numericItemAmount: KnockoutObservable<number> = ko.observable(1);
        numericItemMinus: KnockoutObservable<number> = ko.observable(1);
        decimalPart: KnockoutObservable<number> = ko.observable(0);
        integerPart: KnockoutObservable<number> = ko.observable(0);
        constructor(data: INumericItem) {
            let self = this;
            if (!data) return;
            self.numericItemMin(data.NumericItemMin || null);
            self.numericItemMax(data.NumericItemMax || null);
            self.numericItemAmount(data.numericItemAmount || 1);
            self.numericItemMinus(data.numericItemMinus || 1);
            self.decimalPart(data.decimalPart || 0);
            self.integerPart(data.integerPart || 0);
        }
    }
    export class TimeItemModel {
        timeItemMin: KnockoutObservable<number> = ko.observable(null);
        timeItemMax: KnockoutObservable<number> = ko.observable(null);
        constructor(data: ITimeItem) {
            let self = this;
            if (!data) return;
            self.timeItemMin(data.min || null);
            self.timeItemMax(data.max || null);
        }
    }
    export class TimePointItemModel {
        timePointItemMin: KnockoutObservable<number> = ko.observable(null);
        timePointItemMax: KnockoutObservable<number> = ko.observable(null);
        constructor(data: ITimePointItem) {
            let self = this;
            if (!data) return;
            self.timePointItemMin(data.timePointItemMin || null);
            self.timePointItemMax(data.timePointItemMax || null);
        }
    }
    export class DateItemModel {
        dateItemType: KnockoutObservable<number> = ko.observable(1);
        constructor(data: IDateItem) {
            let self = this;
            if (!data) return;
            self.dateItemType(data.dateItemType || 1);
        }
    }
    export class SelectionItemModel {
        selectionItemRefType: KnockoutObservable<number> = ko.observable(null);
        selectionItemRefCode: KnockoutObservable<number> = ko.observable(null);
        constructor(data: ISelectionItem) {
            let self = this;
            if (!data) return;
            self.selectionItemRefType(data.selectionItemRefType || null);
            self.selectionItemRefCode(data.selectionItemRefCode || null);
        }
    }

    export class PersonInfoItemShowListModel {
        id: string;
        itemName: string;
        constructor(data: IPersonInfoItemShowList) {
            let self = this;
            if (!data) return;
            self.id = data.id || null;
            self.itemName = data.itemName || null;
        }
    }
    interface IItemData {
        dataTypeEnum: any;
        stringItemTypeEnum: any;
        stringItemDataTypeEnum: any;
        dateItemTypeEnum: any;
        personInfoItemList: Array<IPersonInfoItemShowList>;
    }
    interface IPersonInfoItemShowList {
        id: string;
        itemName: string;
    }

    interface IPersonInfoItem {
        id: string;
        itemName: string;
        fixedAtr: number;
        itemType?: number;
        dataType?: number;
        dateItem?: IDateItem;
        stringItem?: IStringItem;
        numericItem?: INumericItem;
        timeItem?: ITimeItem;
        timePointItem?: ITimePointItem;
        selectionItem?: ISelectionItem;
    }
    interface IStringItem {
        stringItemType: number;
        stringItemLength: number;
        stringItemDataType: number;
    }
    interface INumericItem {
        NumericItemMin: number;
        NumericItemMax: number;
        numericItemAmount: number;
        numericItemMinus: number;
        decimalPart: number;
        integerPart: number;
    }
    interface ITimeItem {
        min: number;
        max: number;
    }
    interface ITimePointItem {
        timePointItemMin: number;
        timePointItemMax: number;
    }
    interface IDateItem {
        dateItemType: number;
    }
    interface ISelectionItem {
        selectionItemRefType: number;
        selectionItemRefCode: number;
    }

}

