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

        currentCategory: KnockoutObservable<PerInfoCategory>;

        ckbDisplayAbolition: KnockoutObservable<boolean> = ko.observable(false);

        ckbIsAbolition: KnockoutObservable<boolean> = ko.observable(false);

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

                self.ckbIsAbolition(newItem.isAbolition === 1 ? true : false);

            });

            self.ckbDisplayAbolition.subscribe(function(newValue) {

                self.loadDataForGrid();

            });
        }

        start(): JQueryPromise<any> {

            let self = this,
                dfd = $.Deferred();

            self.currentCategory = ko.observable(new PerInfoCategory('AF3714DE-507B-4E9D-BA61-4B16948A5872', 'CS00004', 'Du lịch', 1, 0, 1, 0));

            self.loadDataForGrid().done(function() {

                dfd.resolve();

            });

            return dfd.promise();
        }

        loadDataForGrid(): JQueryPromise<any> {

            let self = this,
                dfd = $.Deferred(),
                lastSelectedIndex = self.itemInfoDefList().indexOf(_.find(self.itemInfoDefList(), function(i) { return i.id == self.currentSelectId() })),
                selectedId;

            self.loadItemInfoDefList().done(function() {

                //set selected item for gridlist

                if (lastSelectedIndex != -1) {

                    let selectItem = _.find(self.itemInfoDefList(), function(i) { return i.id == self.currentSelectId() });

                    if (selectItem) {

                        selectedId = self.currentSelectId();

                    } else {

                        if (self.itemInfoDefList().length == 0) {

                            selectedId = '';

                        } else {

                            if (self.itemInfoDefList().length <= lastSelectedIndex) {

                                selectedId = self.itemInfoDefList()[self.itemInfoDefList().length - 1].id;

                            } else {

                                selectedId = self.itemInfoDefList()[lastSelectedIndex == 0 ? 0 : lastSelectedIndex].id;

                            }
                        }
                    }
                } else {

                    selectedId = self.itemInfoDefList()[0].id;
                }

                self.currentSelectId(selectedId);

                dfd.resolve();

            });
            return dfd.promise();
        }

        loadItemInfoDefList(): JQueryPromise<any> {
            let self = this,
                dfd = $.Deferred(),
                categoryId = self.currentCategory().id;
            service.getItemInfoDefList(categoryId, self.ckbDisplayAbolition()).done(function(itemInfoDefList: Array<IItemInfoDef>) {

                self.itemInfoDefList([]);

                for (let i of itemInfoDefList) {

                    self.itemInfoDefList().push(new ItemInfoDef(i));

                }

                self.itemInfoDefList.valueHasMutated();

                if (self.itemInfoDefList().length < 0) {



                }

                dfd.resolve();

            });
            return dfd.promise();
        }

        updateItemChange() {

            let self = this,

                command = {
                    id: self.currentItem().id,
                    itemName: self.itemNameText(),
                    isAbolition: self.ckbIsAbolition() === true ? 1 : 0,
                    isRequired: self.isRequired()
                }

            block.invisible();

            service.updateItemChange(command)
                .done(function() {

                    dialog({ messageId: "Msg_15" }).then(function() {

                        self.loadDataForGrid().done(function() {

                            block.clear();

                        });
                    });
                }).fail(function() {

                    dialog({ messageId: "Msg_233" });

                });

        }

        closeDialog(): void {

            nts.uk.ui.windows.close();

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

        genParamDisplayOrder() {
            let self = this,
                disPlayOrderArray = [];

            for (let i of self.itemInfoDefList()) {

                var item = {
                    id: i.id,
                    name: i.itemName
                }

                disPlayOrderArray.push(item);

            }

            return disPlayOrderArray;
        }

        OpenCDL022Modal() {

            let self = this,
                command;

            setShared('CDL020_PARAMS', self.genParamDisplayOrder());

            block.invisible();

            nts.uk.ui.windows.sub.modal('/view/cdl/022/a/index.xhtml', { title: '' }).onClosed(function(): any {

                if (!getShared('CDL020_VALUES')) {
                    block.clear();
                    return;
                }

                command = {
                    categoryId: self.currentCategory().id,
                    orderItemList: getShared('CDL020_VALUES')
                }

                service.SetOrder(command).done(function() {

                    self.loadDataForGrid().done(function() {

                        block.clear();
                    });

                });


            });
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
        itemDefaultName: string;
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
        itemDefaultName: string;
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
            this.itemDefaultName = data ? data.itemDefaultName : '';
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



}


