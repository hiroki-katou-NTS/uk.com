module nts.uk.com.view.cps006.b.viewmodel {
    import alert = nts.uk.ui.dialog.alert;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import block = nts.uk.ui.block;
    import dialog = nts.uk.ui.dialog.info;
    export class ScreenModel {

        itemInfoDefList: KnockoutObservableArray<ItemInfoDef> = ko.observableArray([]);

        currentCode: KnockoutObservable<string> = ko.observable('');

        columns: KnockoutObservableArray<any> = ko.observableArray([
            { headerText: '', prop: 'itemCode', width: 100, hidden: true },
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

        constructor() {
            let self = this;

            self.currentCode.subscribe(function(newValue) {

                let newItem = _.find(self.itemInfoDefList(), function(o) { return o.itemCode == newValue; });

                self.currentItem(newItem);

                self.itemNameText(newItem.itemName);

                self.isRequired(newItem.isRequired);

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

                    self.currentCode(itemInfoDefList[0].itemCode);

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

            switch (self.currentItem().itemTypeState.dataTypeState.stringItemDataType) {
                case 1:
                    return getText('Enum_StringItemDataType_FIXED_LENGTH');
                case 2:
                    return getText('Enum_StringItemDataType_VARIABLE_LENGTH');
            }
        }

        genStringItemTypeText() {
            let self = this;

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

            switch (self.currentItem().itemTypeState.dataTypeState.numericItemMinus) {
                case 0:
                    return getText('Enum_NumericItemMinus_NO');
                case 1:
                    return getText('Enum_NumericItemMinus_YES');
            }
        }
        
        genDateTypeText() {
            let self = this;

            switch (self.currentItem().itemTypeState.dataTypeState.numericItemMinus) {
                case 1:
                    return getText('Enum_DateType_YEARMONTHDAY');
                case 2:
                    return getText('Enum_DateType_YEARMONTH');
                case 3:
                    return getText('Enum_DateType_YEAR');
            }

        }



    }

    function makeIcon(value, row) {
        if (value == '1')
            return "×";
        return '';
    }

    export interface IItemInfoDef {
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


