module nts.uk.com.view.cps005.b {
    import getText = nts.uk.resource.getText;
    import alert = nts.uk.ui.dialog.alert;
    import confirm = nts.uk.ui.dialog.confirm;
    import modal = nts.uk.ui.windows.sub.modal;
    import getShared = nts.uk.ui.windows.getShared;
    import textUK = nts.uk.text;
    export module viewmodel {
        export class ScreenModel {
            currentItemData: KnockoutObservable<ItemDataModel>;
            isUpdate: boolean = false;
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
                    if (data && data.personInfoItemList && data.personInfoItemList.length > 0) {
                        self.currentItemData(new ItemDataModel(data));
                        self.currentItemData().perInfoItemSelectCode(data.personInfoItemList ? data.personInfoItemList[0].id : "");
                        self.isUpdate = true;
                    } else {
                        self.register();
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            reloadData(newItemName: string, itemId: string): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                let categoryId = "AF3714DE-507B-4E9D-BA61-4B16948A5872";
                new service.Service().getAllPerInfoItemDefByCtgId(categoryId).done(function(data: IItemData) {
                    if (data && data.personInfoItemList && data.personInfoItemList.length > 0) {
                        self.currentItemData(new ItemDataModel(data));
                        if (newItemName) {
                            let newItem = _.find(data.personInfoItemList, item => { return item.itemName == newItemName });
                            self.currentItemData().perInfoItemSelectCode(newItem ? newItem.id : "");
                        }
                        if(itemId){
                            self.currentItemData().perInfoItemSelectCode("");
                            self.currentItemData().perInfoItemSelectCode(itemId);
                        }
                        self.isUpdate = true;
                    } else {
                        self.register();
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }
            
            register() {
                let self = this;
                nts.uk.ui.errors.clearAll();
                self.currentItemData().perInfoItemSelectCode("");
                self.currentItemData().currentItemSelected(new PersonInfoItem(null));
                self.isUpdate = false;
                $("#item-name-control").focus();
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
        dataTypeEnum: Array<any> = new Array();
        //Enum : dataTypeEnum is selected value 1 - 文字列(String)
        stringItemTypeEnum: Array<any> = new Array();
        stringItemDataTypeEnum: Array<any> = new Array();
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
        dateItemTypeEnum: Array<any> = new Array();
        //Enum : dataTypeEnum is selected value 6 -選択(Selection)
        referenceTypeEnum: Array<any> = [
            { value: 1, localizedName: "等級区分 1" },
            { value: 2, localizedName: "等級区分 2" },
            { value: 3, localizedName: "等級区分 3" },
        ];
        constructor(data: IItemData) {
            let self = this;
            if (data) {
                self.personInfoItemList(_.map(data.personInfoItemList, item => { return new PersonInfoItemShowListModel(item) }));
                self.dataTypeEnum = data.dataTypeEnum || new Array();
                self.stringItemTypeEnum = data.stringItemTypeEnum || new Array();
                self.stringItemDataTypeEnum = data.stringItemDataTypeEnum || new Array();
                self.stringItemDataTypeEnum.reverse();
                self.dateItemTypeEnum = data.dateItemTypeEnum || new Array();
                //subscribe select category code
                self.perInfoItemSelectCode.subscribe(newItemId => {
                    if (textUK.isNullOrEmpty(newItemId)) return;
                    nts.uk.ui.errors.clearAll();
                    new service.Service().getPerInfoItemDefById(newItemId).done(function(data: IPersonInfoItem) {
                        self.currentItemSelected(new PersonInfoItem(data));
                        self.currentItemSelected().dataTypeText(_.find(self.dataTypeEnum, function(o) { return o.value == self.currentItemSelected().dataType(); }).localizedName);
                        self.currentItemSelected().stringItem().stringItemTypeText(_.find(self.stringItemTypeEnum, function(o) { return o.value == self.currentItemSelected().stringItem().stringItemType(); }).localizedName);
                        self.currentItemSelected().stringItem().stringItemDataTypeText(_.find(self.stringItemDataTypeEnum, function(o) { return o.value == self.currentItemSelected().stringItem().stringItemDataType(); }).localizedName);
                        self.currentItemSelected().numericItem().numericItemAmountText(_.find(self.numericItemAmountAtrEnum, function(o) { return o.code == self.currentItemSelected().numericItem().numericItemAmount(); }).name);
                        self.currentItemSelected().numericItem().numericItemMinusText(_.find(self.numericItemMinusAtrEnum, function(o) { return o.code == self.currentItemSelected().numericItem().numericItemMinus(); }).name);
                        self.currentItemSelected().dateItem().dateItemTypeText(_.find(self.dateItemTypeEnum, function(o) { return o.value == self.currentItemSelected().dateItem().dateItemType(); }).localizedName);
                        self.currentItemSelected().selectionItem().selectionItemRefTypeText(_.find(self.referenceTypeEnum, function(o) { return o.value == self.currentItemSelected().selectionItem().selectionItemRefType(); }).localizedName);

                    });
                });
            }
        }
    }

    export class PersonInfoItem {
        id: string = "";
        itemName: KnockoutObservable<string> = ko.observable("");
        fixedAtr: KnockoutObservable<number> = ko.observable(0);
        itemType: KnockoutObservable<number> = ko.observable(2);
        dataType: KnockoutObservable<number> = ko.observable(1);
        stringItem: KnockoutObservable<StringItemModel> = ko.observable(new StringItemModel(null));
        numericItem: KnockoutObservable<NumericItemModel> = ko.observable(new NumericItemModel(null));
        dateItem: KnockoutObservable<DateItemModel> = ko.observable(new DateItemModel(null));
        timeItem: KnockoutObservable<TimeItemModel> = ko.observable(new TimeItemModel(null));
        timePointItem: KnockoutObservable<TimePointItemModel> = ko.observable(new TimePointItemModel(null));
        selectionItem: KnockoutObservable<SelectionItemModel> = ko.observable(new SelectionItemModel(null));
        dataTypeText: KnockoutObservable<string> = ko.observable("");
        constructor(data: IPersonInfoItem) {
            let self = this;
            if (data) {
                self.id = data.id || "";
                self.itemName(data.itemName || "");
                self.fixedAtr(data.isFixed || 0);
                if (!data.itemTypeState) return;
                self.itemType(data.itemTypeState.itemType || 2);
                let dataTypeState = data.itemTypeState.dataTypeState;
                if (!dataTypeState) return;
                if (self.itemType() == 2) {
                    self.dataType(dataTypeState.dataTypeValue || 1);
                    switch (self.dataType()) {
                        case 1:
                            self.stringItem(new StringItemModel(dataTypeState));
                            break;
                        case 2:
                            self.numericItem(new NumericItemModel(dataTypeState));
                            break;
                        case 3:
                            self.dateItem(new DateItemModel(dataTypeState));
                            break;
                        case 4:
                            self.timeItem(new TimeItemModel(dataTypeState));
                            break;
                        case 5:
                            self.timePointItem(new TimePointItemModel(dataTypeState));
                            break;
                        case 6:
                            self.selectionItem(new SelectionItemModel(dataTypeState));
                            break;
                    }
                }
            }

        }
    }

    export class StringItemModel {
        stringItemType: KnockoutObservable<number> = ko.observable(4);
        stringItemTypeText: KnockoutObservable<string> = ko.observable("");
        stringItemLength: KnockoutObservable<number> = ko.observable(null);
        stringItemDataType: KnockoutObservable<number> = ko.observable(2);
        stringItemDataTypeText: KnockoutObservable<string> = ko.observable("");
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
        numericItemAmountText: KnockoutObservable<string> = ko.observable("");
        numericItemMinus: KnockoutObservable<number> = ko.observable(1);
        numericItemMinusText: KnockoutObservable<string> = ko.observable("");
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
        dateItemTypeText: KnockoutObservable<string> = ko.observable("");
        constructor(data: IDateItem) {
            let self = this;
            if (!data) return;
            self.dateItemType(data.dateItemType || 1);
        }
    }
    export class SelectionItemModel {
        selectionItemRefType: KnockoutObservable<number> = ko.observable(null);
        selectionItemRefTypeText: KnockoutObservable<string> = ko.observable("");
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
        isFixed: number;
        itemTypeState: IItemTypeState;
    }

    interface IItemTypeState {
        itemType: number;
        dataTypeState: any;
    }

    interface IStringItem {
        dataTypeValue: number;
        stringItemType: number;
        stringItemLength: number;
        stringItemDataType: number;
    }
    interface INumericItem {
        dataTypeValue: number;
        NumericItemMin: number;
        NumericItemMax: number;
        numericItemAmount: number;
        numericItemMinus: number;
        decimalPart: number;
        integerPart: number;
    }
    interface ITimeItem {
        dataTypeValue: number;
        min: number;
        max: number;
    }
    interface ITimePointItem {
        dataTypeValue: number;
        timePointItemMin: number;
        timePointItemMax: number;
    }
    interface IDateItem {
        dataTypeValue: number;
        dateItemType: number;
    }
    interface ISelectionItem {
        dataTypeValue: number;
        selectionItemRefType: number;
        selectionItemRefCode: number;
    }

}

