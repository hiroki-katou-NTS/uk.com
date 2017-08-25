module nts.uk.com.view.cps006.b.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog.info;
    export class ScreenModel {

        itemInfoDefList: KnockoutObservableArray<ItemInfoDef> = ko.observableArray([]);

        currentSelectId: KnockoutObservable<string> = ko.observable('');

        columns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: '', prop: 'id', width: 100, hidden: true },
            { headerText: getText('CPS006_15'), prop: 'itemName', width: 150 },
            { headerText: getText('CPS006_16'), prop: 'isAbolition', width: 50, formatter: makeIcon },
        ]);

        roundingRules: KnockoutObservableArray<any> = ko.observableArray([
            { code: '0', name: getText('CPS006_26') },
            { code: '1', name: getText('CPS006_27') }
        ]);

        selectedRuleCode: KnockoutObservable<number> = ko.observable(1);


        categoryType: KnockoutObservable<number> = ko.observable(1);

        isRequired: KnockoutObservable<number> = ko.observable(1);

        currentItem: KnockoutObservable<ItemInfoDef> = ko.observable(new ItemInfoDef(null));

        itemNameText: KnockoutObservable<string> = ko.observable('');

        currentCategory: KnockoutObservable<PerInfoCategory>;//= ko.observable(getShared('categoryRole'));

        ckbDisplayAbolition: KnockoutObservable<boolean> = ko.observable(false);

        constructor() {
            let self = this;

            self.currentSelectId.subscribe(function(newValue) {

                service.getPerInfoItemDefById(newValue).done(function(data: IItemInfoDef) {

                    self.currentItem(new ItemInfoDef(data));

                });


            });

            self.currentItem.subscribe(function(newItem) {

                self.itemNameText(newItem.itemName);

                self.isRequired(newItem.isRequired);

            });

            self.ckbDisplayAbolition.subscribe(function(newValue) { 

                self.loadItemInfoDefList(self.currentCategory().id);

            });
        }

        start(): JQueryPromise<any> {

            let self = this,
                dfd = $.Deferred();

            self.currentCategory = ko.observable(new PerInfoCategory('B28E5649-0CFF-475B-AE87-52A21DBEB6D8', 'CS00004', 'Du lịch', 1, 0, 1, 0));

            self.loadItemInfoDefList(self.currentCategory().id).done(function() {

                dfd.resolve(); 

            });

            return dfd.promise();
        }

        loadItemInfoDefList(categoryId): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred();
            service.getItemInfoDefList(categoryId).done(function(itemInfoDefList: Array<IItemInfoDef>) {

                for (let i of itemInfoDefList) {
                    self.itemInfoDefList().push(new ItemInfoDef(i));
                }

                if (itemInfoDefList.length > 0) {

                    self.currentSelectId(itemInfoDefList[0].id);

                } else {



                }

                dfd.resolve();

            });
            return dfd.promise();
        }

        genRequiredText() {
            let self = this;

            switch (self.isRequired()) {
                case 0:
                    return getText('CPS006_26');
                case 1:
                    return getText('CPS006_27');
            }
        }

        genDatatypeValueText() {
            let self = this;

            if (self.currentItem().itemTypeState == null) {

                return;
            }

            switch (self.currentItem().itemTypeState.dataTypeState.dataTypeValue) {
                case 1:
                    return getText('Enum_DataTypeValue_STRING');
                case 2:
                    return getText('Enum_DataTypeValue_NUMERIC');
                case 3:
                    return getText('Enum_DataTypeValue_DATE');
                case 4:
                    return getText('Enum_DataTypeValue_TIME');
                case 5:
                    return getText('Enum_DataTypeValue_TIMEPOINT');
                case 6:
                    return getText('Enum_DataTypeValue_SELECTION');
            }

        }

        genStringItemDataTypeText() {
            let self = this;
            if (self.currentItem().itemTypeState == null) {

                return;
            }
            switch (self.currentItem().itemTypeState.dataTypeState.stringItemDataType) {
                case 1:
                    return getText('Enum_StringItemDataType_FIXED_LENGTH');
                case 2:
                    return getText('Enum_StringItemDataType_VARIABLE_LENGTH');
            }
        }

        genStringItemTypeText() {
            let self = this;

            if (self.currentItem().itemTypeState == null) {

                return;
            }

            switch (self.currentItem().itemTypeState.dataTypeState.stringItemType) {
                case 1:
                    return getText('Enum_StringItemType_ANY');
                case 2:
                    return getText('Enum_StringItemType_ANYHALFWIDTH');
                case 3:
                    return getText('Enum_StringItemType_ALPHANUMERIC');
                case 4:
                    return getText('Enum_StringItemType_NUMERIC');
                case 5:
                    return getText('Enum_StringItemType_KANA');
            }
        }

        genStringNumericItemMinusText() {
            let self = this;

            if (self.currentItem().itemTypeState == null) {

                return;
            }

            switch (self.currentItem().itemTypeState.dataTypeState.numericItemMinus) {
                case 0:
                    return getText('Enum_NumericItemMinus_NO');
                case 1:
                    return getText('Enum_NumericItemMinus_YES');
            }
        }

        genDateTypeText() {
            let self = this;

            if (self.currentItem().itemTypeState == null) {

                return;
            }

            switch (self.currentItem().itemTypeState.dataTypeState.dateItemType) {
                case 1:
                    return getText('Enum_DateType_YEARMONTHDAY');
                case 2:
                    return getText('Enum_DateType_YEARMONTH');
                case 3:
                    return getText('Enum_DateType_YEAR');
            }

        }

        dataType() {
            let self = this;

            if (self.currentItem().itemTypeState == null) {

                return;
            }

            return self.currentItem().itemTypeState.dataTypeState.dataTypeValue;

        }


    }

    function makeIcon(value, row) {
        if (value == '1')
            return "×";
        return '';
    }

    export interface IItemInfoDef {
        id: string;
        perInfoCtgId: string;
        itemCode: string;
        itemName: string;
        isAbolition: number;
        isFixed: number;
        isRequired: number;
        systemRequired: number;
        requireChangable: number;
        dispOrder: number;
        itemTypeState: any;
    }

    export class ItemInfoDef {
        id: string;
        perInfoCtgId: string;
        itemCode: string;
        itemName: string;
        isAbolition: number;
        isFixed: number;
        isRequired: number;
        systemRequired: number;
        requireChangable: number;
        dispOrder: number;
        itemTypeState: any;

        constructor(data: IItemInfoDef) {

            this.id = data ? data.id : '';
            this.perInfoCtgId = data ? data.perInfoCtgId : '';
            this.itemCode = data ? data.itemCode : '';
            this.itemName = data ? data.itemName : '';
            this.isAbolition = data ? data.isAbolition : 0;
            this.isFixed = data ? data.isFixed : 0;
            this.isRequired = data ? data.isRequired : 0;
            this.systemRequired = data ? data.systemRequired : 0;
            this.requireChangable = data ? data.requireChangable : 0;
            this.dispOrder = data ? data.dispOrder : 0;
            this.itemTypeState = data ? data.itemTypeState : null;

        }

    }

    export class PerInfoCategory {
        id: string;
        categoryCode: string;
        categoryName: string;
        personEmployeeType: number;
        isAbolition: number;
        categoryType: number;
        isFixed: number;

        constructor(
            id: string,
            categoryCode: string,
            categoryName: string,
            personEmployeeType: number,
            isAbolition: number,
            categoryType: number,
            isFixed: number) {
            this.id = id;
            this.categoryCode = categoryCode;
            this.categoryName = categoryName;
            this.personEmployeeType = personEmployeeType;
            this.isAbolition = isAbolition;
            this.categoryType = categoryType;
            this.isFixed = isFixed
        }

    }

    export class CategoryRole {
        categoryId: string;
        itemCode: string;
        itemIsSetting: boolean;
        constructor(categoryId: string,
            itemCode: string,
            itemIsSetting: boolean) {
            this.categoryId = categoryId;
            this.itemCode = itemCode;
            this.itemIsSetting = itemIsSetting;

        }
    }

}


