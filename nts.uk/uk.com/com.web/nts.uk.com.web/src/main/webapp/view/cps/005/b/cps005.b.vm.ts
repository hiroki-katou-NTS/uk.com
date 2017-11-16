module nts.uk.com.view.cps005.b {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import info = nts.uk.ui.dialog.info;
    import alertError = nts.uk.ui.dialog.alertError;
    import getShared = nts.uk.ui.windows.getShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            currentItemData: KnockoutObservable<ItemDataModel>;
            isUpdate: boolean = false;
            isEnableButtonProceed: KnockoutObservable<boolean>;
            categoryId: string = "";
            constructor() {
                let self = this,
                    dataItemModel = new ItemDataModel(null);
                self.currentItemData = ko.observable(dataItemModel);
                self.categoryId = getShared("categoryId");
                self.isEnableButtonProceed = ko.observable(true);
            }

            startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                block.invisible();
                new service.Service().getAllPerInfoItemDefByCtgId(self.categoryId).done(function(data: IItemData) {
                    self.currentItemData(new ItemDataModel(data));
                    if (data && data.personInfoItemList && data.personInfoItemList.length > 0) {
                        self.currentItemData().perInfoItemSelectCode(data.personInfoItemList ? data.personInfoItemList[0].id : "");
                        self.isUpdate = true;
                        self.currentItemData().isEnableButtonProceed(true);
                    } else {
                        self.register();
                    }
                    block.clear();
                    dfd.resolve();
                });
                return dfd.promise();
            }

            reloadData(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                self.currentItemData().personInfoItemList([]);
                new service.Service().getAllPerInfoItemDefByCtgId(self.categoryId).done(function(data: IItemData) {
                    if (data && data.personInfoItemList && data.personInfoItemList.length > 0) {
                        self.currentItemData().personInfoItemList(_.map(data.personInfoItemList, item => { return new PersonInfoItemShowListModel(item) }));
                        self.isUpdate = true;
                        self.currentItemData().isEnableButtonProceed(true);
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
                self.currentItemData().isEnableButtonProceed(true);
                self.currentItemData().isEnableButtonDelete(false);
            }

            addUpdateData() {
                let self = this,
                    newItemDef;

                block.invisible();

                newItemDef = new UpdateItemModel(self.currentItemData().currentItemSelected());

                self.checkRequired(newItemDef);

                if (self.isUpdate == true) {

                    newItemDef.perInfoCtgId = self.categoryId;
                    newItemDef.singleItem.referenceCode = "Hard Code";
                    new service.Service().updateItemDef(newItemDef).done(function(data: string) {
                        if (data) {
                            info({ messageId: data }).then(() => { info({ messageId: "Msg_15" }).then(() => { block.clear(); }); });
                        } else {
                            info({ messageId: "Msg_15" }).then(() => { block.clear(); });
                        }
                        self.reloadData();
                        self.currentItemData().perInfoItemSelectCode("");
                        self.currentItemData().perInfoItemSelectCode(newItemDef.perInfoItemDefId);
                    }).fail(error => {

                        alertError({ messageId: error.message });
                        block.clear();

                    });
                } else {
                    newItemDef = new AddItemModel(self.currentItemData().currentItemSelected());
                    newItemDef.perInfoCtgId = self.categoryId;
                    newItemDef.singleItem.referenceCode = "Hard Code";
                    new service.Service().addItemDef(newItemDef).done(function(data: string) {
                        self.reloadData().done(() => {
                            self.currentItemData().perInfoItemSelectCode(data);
                        });
                        info({ messageId: "Msg_15" }).then(() => { block.clear(); });
                    }).fail(error => {

                        alertError({ messageId: error.message });
                        block.clear();

                    });
                }
            }

            removeData() {
                let self = this,
                    removeModel = new RemoveItemModel(self.currentItemData().perInfoItemSelectCode());
                block.invisible();
                if (!self.currentItemData().perInfoItemSelectCode()) return;
                let indexItemDelete = _.findIndex(self.currentItemData().personInfoItemList(), function(item) { return item.id == removeModel.perInfoItemDefId; });
                confirm({ messageId: "Msg_18" }).ifYes(() => {
                    new service.Service().removeItemDef(removeModel).done(function(data: string) {
                        if (data) {
                            info({ messageId: data }).then(() => { block.clear(); });
                            block.clear();
                            return;
                        }
                        self.reloadData().done(() => {
                            let itemListLength = self.currentItemData().personInfoItemList().length;
                            if (itemListLength === 0) {
                                self.register();
                                block.clear();
                                return;
                            }
                            if (itemListLength - 1 >= indexItemDelete) {
                                self.currentItemData().perInfoItemSelectCode(self.currentItemData().personInfoItemList()[indexItemDelete].id);
                                block.clear();
                                return;
                            }
                            if (itemListLength - 1 < indexItemDelete) {
                                self.currentItemData().perInfoItemSelectCode(self.currentItemData().personInfoItemList()[itemListLength - 1].id);
                                block.clear();
                                return;
                            }
                        });
                        info({ messageId: "Msg_16" }).then(() => { block.clear(); });

                    }).fail(error => {
                        alertError({ messageId: error.message });
                        block.clear();
                    });
                }).ifNo(() => {
                    block.clear();
                    return;
                })
            }

            closedDialog() {
                nts.uk.ui.windows.close();
            }

            checkRequired(newItemDef: any) {

                if (newItemDef.singleItem.dataType === 1) {
                    if (newItemDef.singleItem.stringItemLength === null) {
                        $("#stringItemLength").focus();
                        block.clear();
                        return;
                    }
                }

                if (newItemDef.singleItem.dataType === 2) {
                    if (newItemDef.singleItem.integerPart === null) {
                        $("#integerPart").focus();
                        block.clear();
                        return;
                    } else if (newItemDef.singleItem.decimalPart === null) {
                        $("#decimalPart").focus();
                        block.clear();
                        return;
                    }
                }

                if (newItemDef.singleItem.dataType === 4) {
                    if (newItemDef.singleItem.timeItemMin === null) {
                        $("#timeItemMin").focus();
                        newItemDef.singleItem.hintTimeMin("");
                        block.clear();
                        return;
                    } else if (newItemDef.singleItem.timeItemMax === null) {
                        $("#timeItemMax").focus();
                        newItemDef.singleItem.hintTimeMax("");
                        block.clear();
                        return;
                    }
                }

                if (newItemDef.singleItem.dataType === 5) {
                    if (newItemDef.singleItem.timePointItemMin === undefined) {
                        $("#timePointItemMin").focus();
                        block.clear();
                        return;
                    } else if (newItemDef.singleItem.timePointItemMax === undefined) {
                        $("#timePointItemMax").focus();
                        block.clear();
                        return;
                    }
                }
            }

            genTextTime(time) {
                return nts.uk.time.parseTime(time(), true).format();
            }

            isNotsetOrnull(value) {
                return value() == 0 || !value();
            }
        }
    }

    export class ItemDataModel {
        personInfoItemList: KnockoutObservableArray<PersonInfoItemShowListModel> = ko.observableArray([]);
        perInfoItemSelectCode: KnockoutObservable<string> = ko.observable("");
        currentItemSelected: KnockoutObservable<PersonInfoItem> = ko.observable(new PersonInfoItem(null));
        isEnableButtonProceed: KnockoutObservable<boolean> = ko.observable(true);
        isEnableButtonDelete: KnockoutObservable<boolean> = ko.observable(true);
        dataTypeEnum: Array<any> = new Array();
        //Enum : dataTypeEnum is selected value 1 - 文字列(String)
        stringItemTypeEnum: Array<any> = new Array();
        stringItemDataTypeEnum: Array<any> = new Array();
        //Enum : dataTypeEnum is selected value 2 - 数値(Numeric)
        numericItemAmountAtrEnum: Array<any> = [
            { code: 1, name: getText("CPS005_50") },
            { code: 0, name: getText("CPS005_51") },
        ];
        numericItemMinusAtrEnum: Array<any> = [
            { code: 1, name: getText("CPS005_46") },
            { code: 0, name: getText("CPS005_47") },
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
                        self.isEnableButtonProceed(true);
                        self.isEnableButtonDelete(true);
                        if (self.currentItemSelected().fixedAtr() == 1) {
                            self.isEnableButtonProceed(false);
                            self.isEnableButtonDelete(false);
                        }
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
        stringItemType: KnockoutObservable<number> = ko.observable(1);
        stringItemTypeText: KnockoutObservable<string> = ko.observable("");
        stringItemLength: KnockoutObservable<number> = ko.observable(null);
        stringItemDataType: KnockoutObservable<number> = ko.observable(2);
        stringItemDataTypeText: KnockoutObservable<string> = ko.observable("");
        constructor(data: IStringItem) {
            let self = this;
            if (!data) return;
            self.stringItemType(data.stringItemType || 1);
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
        decimalPart: KnockoutObservable<number> = ko.observable(null);
        integerPart: KnockoutObservable<number> = ko.observable(null);
        constructor(data: INumericItem) {
            let self = this;
            if (!data) return;
            self.numericItemMin(data.numericItemMin || null);
            self.numericItemMax(data.numericItemMax || null);
            self.numericItemAmount(data.numericItemAmount);
            self.numericItemMinus(data.numericItemMinus);
            self.decimalPart(data.decimalPart || null);
            self.integerPart(data.integerPart || null);
        }
    }
    export class TimeItemModel {
        timeItemMin: KnockoutObservable<number> = ko.observable(null);
        timeItemMax: KnockoutObservable<number> = ko.observable(null);
        hintTimeItemMin: KnockoutObservable<string> = ko.observable('0:00');
        hintTimeItemMax: KnockoutObservable<string> = ko.observable('0:00');
        constructor(data: ITimeItem) {
            let self = this;
            if (!data) return;
            self.timeItemMin(data.min || null);
            self.timeItemMax(data.max || null);
        }
    }
    export class TimePointItemModel {
        timePointItemMin: KnockoutObservable<number> = ko.observable();
        timePointItemMax: KnockoutObservable<number> = ko.observable();
        hintTimeMin: KnockoutObservable<string> = ko.observable('0:00');
        hintTimeMax: KnockoutObservable<string> = ko.observable('0:00');
        constructor(data: ITimePointItem) {
            let self = this;
            if (!data) return;
            self.timePointItemMin(data.timePointItemMin);
            self.timePointItemMax(data.timePointItemMax);
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
        selectionItemRefType: KnockoutObservable<number> = ko.observable(1);
        selectionItemRefTypeText: KnockoutObservable<string> = ko.observable("");
        selectionItemRefCode: KnockoutObservable<string> = ko.observable(null);
        constructor(data: ISelectionItem) {
            let self = this;
            if (!data) return;
            self.selectionItemRefType(data.selectionItemRefType || 1);
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
    export class AddItemModel {
        perInfoCtgId: string;
        itemName: string;
        singleItem: SingleItemAddModel;
        constructor(data: PersonInfoItem) {
            let self = this;
            if (!data) return;
            self.itemName = data.itemName();
            self.singleItem = new SingleItemAddModel(data);
        }
    }

    export class UpdateItemModel {
        perInfoItemDefId: string;
        perInfoCtgId: string;
        itemName: string;
        singleItem: SingleItemAddModel;
        constructor(data: PersonInfoItem) {
            let self = this;
            if (!data) return;
            self.perInfoItemDefId = data.id;
            self.itemName = data.itemName();
            self.singleItem = new SingleItemAddModel(data);
        }
    }
    export class RemoveItemModel {
        perInfoItemDefId: string;
        constructor(perInfoItemDefId: string) {
            let self = this;
            self.perInfoItemDefId = perInfoItemDefId;
        }
    }
    export class SingleItemAddModel {
        dataType: number = -1;
        // StringItem property
        stringItemLength: number = 0;
        stringItemType: number = 0;
        stringItemDataType: number = 0;
        // NumericItem property
        numericItemMinus: number = 0;
        numericItemAmount: number = 0;
        integerPart: number = 0;
        decimalPart: number = 0;
        numericItemMin: number = 0;
        numericItemMax: number = 0;
        // DateItem property
        dateItemType: number = 0;
        // TimeItem property
        timeItemMax: number = 0;
        timeItemMin: number = 0;
        // TimePointItem property
        timePointItemMin: number = 0;
        timePointItemMax: number = 0;
        // SelectionItem property
        referenceType: number = 0;
        referenceCode: string = "";
        constructor(data: PersonInfoItem) {
            let self = this;
            if (!data) return;
            self.dataType = data.dataType();
            if (data.stringItem()) {
                self.stringItemLength = data.stringItem().stringItemLength();
                self.stringItemType = data.stringItem().stringItemType();
                self.stringItemDataType = data.stringItem().stringItemDataType();
            }
            if (data.numericItem()) {
                self.numericItemMinus = data.numericItem().numericItemMinus();
                self.numericItemAmount = data.numericItem().numericItemAmount();
                self.integerPart = data.numericItem().integerPart();
                self.decimalPart = data.numericItem().decimalPart();
                self.numericItemMin = data.numericItem().numericItemMin();
                self.numericItemMax = data.numericItem().numericItemMax();
            }
            if (data.dateItem()) {
                self.dateItemType = data.dateItem().dateItemType();
            }
            if (data.timeItem()) {
                self.timeItemMax = data.timeItem().timeItemMax();
                self.timeItemMin = data.timeItem().timeItemMin();
            }
            if (data.timePointItem()) {
                self.timePointItemMin = data.timePointItem().timePointItemMin();
                self.timePointItemMax = data.timePointItem().timePointItemMax();
            }
            if (data.selectionItem()) {
                self.referenceType = data.selectionItem().selectionItemRefType();
                self.referenceCode = data.selectionItem().selectionItemRefCode();
            }
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
        numericItemMin: number;
        numericItemMax: number;
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
        selectionItemRefCode: string;
    }

}

