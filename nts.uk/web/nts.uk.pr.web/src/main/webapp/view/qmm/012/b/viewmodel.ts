module qmm012.b.viewmodel {
    export class ScreenModel {
        enable: KnockoutObservable<boolean> = ko.observable(true);
        //combobox
        //001
        ComboBoxItemList_B_001: KnockoutObservableArray<ComboboxItemModel>;
        selectedCode_B_001: KnockoutObservable<number> = ko.observable(1);
        //gridlist
        GridlistItems_B_001: KnockoutObservableArray<service.model.ItemMaster> = ko.observableArray([]);
        GridColumns_B_001: KnockoutObservableArray<any>;
        GridlistCurrentCode_B_001: KnockoutObservable<string> = ko.observable('');
        GridlistCurrentItem_B_001: KnockoutObservable<service.model.ItemMaster> = ko.observable(null);
        GridCurrentItemAbName_B_001: KnockoutObservable<string> = ko.observable('');
        GridCurrentItemName_B_001: KnockoutObservable<string> = ko.observable('');
        GridCurrentDisplaySet_B_001: KnockoutObservable<boolean> = ko.observable(false);
        GridCurrentUniteCode_B_001: KnockoutObservable<string> = ko.observable('');
        GridCurrentCategoryAtr_B_001: KnockoutObservable<number> = ko.observable(0);
        GridCurrentCategoryAtrName_B_001: KnockoutObservable<string> = ko.observable('');
        GridCurrentCodeAndName_B_001: KnockoutObservable<string> = ko.observable('');
        B_INP_002_text: KnockoutObservable<string> = ko.observable('');
        categoryAtr: number = -1;
        //Checkbox
        //B_002
        checked_B_002: KnockoutObservable<boolean> = ko.observable(false);
        //combobox
        //text editer
        texteditor_B_INP_002: any;
        texteditor_B_INP_003: any;
        texteditor_B_INP_004: any;
        enable_B_INP_002: KnockoutObservable<boolean> = ko.observable(false);
        screenModel: qmm012.b.ScreenModel;
        B_BTN_004_enable: KnockoutObservable<boolean> = ko.observable(true);
        constructor(screenModel: qmm012.b.ScreenModel) {
            let self = this;
            self.screenModel = screenModel;
            //set combobox data
            //001
            self.ComboBoxItemList_B_001 = ko.observableArray([
                new ComboboxItemModel(1, '全件'),
                new ComboboxItemModel(2, '支給項目'),
                new ComboboxItemModel(3, '控除項目'),
                new ComboboxItemModel(4, '勤怠項目'),
                new ComboboxItemModel(5, '記事項目'),
                new ComboboxItemModel(6, 'その他項目')
            ]);
            self.selectedCode_B_001.subscribe(function(newValue) {
                let categoryAtr;
                switch (newValue) {
                    case 1:
                        categoryAtr = -1
                        break;
                    case 2:
                        categoryAtr = 0
                        break;
                    case 3:
                        categoryAtr = 1
                        break;
                    case 4:
                        categoryAtr = 2
                        break;
                    case 5:
                        categoryAtr = 3
                        break;
                    case 6:
                        categoryAtr = 9
                        break;
                }
                self.categoryAtr = categoryAtr;
                self.LoadGridList();
            });
            self.checked_B_002.subscribe(function() {
                self.LoadGridList();
            })
            // set gridlist data
            self.GridColumns_B_001 = ko.observableArray([
                { headerText: '項目区分', prop: 'categoryAtrName', width: 80 },
                { headerText: 'コード', prop: 'itemCode', width: 50 },
                { headerText: '名称', prop: 'itemName', width: 150 },
                { headerText: '廃止', prop: 'displaySet', width: 20, formatter: makeIcon }
            ]);

            function makeIcon(val) {
                if (val == 1)
                    return "<div class = 'NoIcon' > </div>";
                return "";
            }
            self.GridlistCurrentCode_B_001.subscribe(function(newValue) {
                var item = _.find(self.GridlistItems_B_001(), function(ItemModel: service.model.ItemMaster) {
                    return ItemModel.itemCode == newValue;
                });
                self.GridlistCurrentItem_B_001(item);
                self.B_INP_002_text(newValue);
            });

            self.GridlistCurrentItem_B_001.subscribe(function(itemModel: service.model.ItemMaster) {
                self.GridCurrentItemName_B_001(itemModel ? itemModel.itemName : '');
                self.GridCurrentUniteCode_B_001(itemModel ? itemModel.uniteCode : '');
                self.GridCurrentCategoryAtr_B_001(itemModel ? itemModel.categoryAtr : 0);
                //Because there are many items in the same group  After set value , need call ChangeGroup function for Set Value to layout
                ChangeGroup(self.GridCurrentCategoryAtr_B_001());
                self.GridCurrentCodeAndName_B_001(itemModel ? itemModel.itemCode + ' ' + itemModel.itemName : '');
                self.GridCurrentDisplaySet_B_001(itemModel ? itemModel.displaySet == 1 ? true : false : false);
                self.GridCurrentItemAbName_B_001(itemModel ? itemModel.itemAbName : '');
                self.GridCurrentCategoryAtrName_B_001(itemModel ? itemModel.categoryAtrName : '');
                //when itemModel != undefined , need disable INP_002
                if (itemModel != undefined) {
                    self.enable_B_INP_002(false);
                }
            });

            self.GridCurrentCategoryAtr_B_001.subscribe(function(newValue) {
                //when change to different group, need  call ChangeGroup function for Set Value to layout
                ChangeGroup(newValue);
            });

            function ChangeGroup(newValue) {
                $('#screenC').hide();
                $('#screenD').hide();
                $('#screenE').hide();
                $('#screenF').hide();
                switch (newValue) {
                    case 0:
                        $('#screenC').show();
                        self.screenModel.screenModelC.CurrentItemMaster(self.GridlistCurrentItem_B_001());
                        break;
                    case 1:
                        $('#screenD').show();
                        self.screenModel.screenModelD.CurrentItemMaster(self.GridlistCurrentItem_B_001());
                        break;
                    case 2:
                        $('#screenE').show();
                        self.screenModel.screenModelE.CurrentItemMaster(self.GridlistCurrentItem_B_001());
                        break;
                    case 3:
                        $('#screenF').show();
                        self.screenModel.screenModelF.CurrentItemMaster(self.GridlistCurrentItem_B_001());
                        break;
                }
            }

            self.LoadGridList();
            self.enable_B_INP_002.subscribe(function(newValue) {
                if (newValue) {
                    self.setNewItemMode();
                } else {
                    self.setUpdateItemMode();
                }
            })
            self.B_INP_002_text.subscribe(function(newValue) {
                if (self.enable_B_INP_002()) {
                    if (newValue != '') {
                        $('#B_INP_002').ntsError('set', 'checking.....');
                        service.findItemByCategoryAndCode(self.GridCurrentCategoryAtr_B_001(), newValue).done(function(itemMaster: service.model.ItemMaster) {
                            $('#B_INP_002').ntsError('clear');
                            if (itemMaster != undefined)
                                $('#B_INP_002').ntsError('set', 'えらーです');
                            else
                                $('#B_INP_002').ntsError('clear');
                        });
                    }
                }

            });
            //set text editer data
            //INP_002
            self.texteditor_B_INP_002 = {
                value: self.B_INP_002_text,
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    textalign: "left"
                })),
                enable: self.enable_B_INP_002
            };
            //INP_003
            self.texteditor_B_INP_003 = {
                value: self.GridCurrentItemName_B_001,
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    textalign: "left"
                }))
            };
            //INP_004
            self.texteditor_B_INP_004 = {
                value: self.GridCurrentItemAbName_B_001,
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                    textmode: "text",
                    placeholder: "",
                    textalign: "left"
                }))
            };
        }
        setNewItemMode() {
            let self = this;
            self.GridlistCurrentCode_B_001('');
            self.B_BTN_004_enable(false);
            self.screenModel.screenModelC.C_BTN_001_enable(false);
            self.screenModel.screenModelC.C_BTN_002_enable(false);
            self.screenModel.screenModelD.D_BTN_001_enable(false);
            self.screenModel.screenModelD.D_BTN_002_enable(false);
        }
        setUpdateItemMode() {
            let self = this;
            $('#B_INP_002').ntsError('clear');
            self.B_BTN_004_enable(true);
            self.screenModel.screenModelC.C_BTN_001_enable(true);
            self.screenModel.screenModelC.C_BTN_002_enable(true);
            self.screenModel.screenModelD.D_BTN_001_enable(true);
            self.screenModel.screenModelD.D_BTN_002_enable(true);
        }
        GetCurrentItemMaster() {
            let self = this;
            let CurrentGroup = self.GridCurrentCategoryAtr_B_001();
            let itemMaster = new service.model.ItemMaster(
                self.B_INP_002_text(),
                self.GridCurrentItemName_B_001(),
                self.GridCurrentCategoryAtr_B_001(),
                self.GridCurrentCategoryAtrName_B_001(),
                self.GridCurrentItemAbName_B_001(),
                self.GridlistCurrentItem_B_001() ? self.GridlistCurrentItem_B_001().itemAbNameO : self.GridCurrentItemAbName_B_001(),
                self.GridlistCurrentItem_B_001() ? self.GridlistCurrentItem_B_001().itemAbNameE : self.GridCurrentItemAbName_B_001(),
                self.GridCurrentDisplaySet_B_001() == true ? 1 : 0,
                self.GridCurrentUniteCode_B_001(),
                self.getCurrentZeroDisplaySet(),
                self.getCurrentItemDisplayAtr(),
                1
            )
            itemMaster.itemSalary = self.screenModel.screenModelC.GetCurrentItemSalary();
            itemMaster.itemDeduct = self.screenModel.screenModelD.GetCurrentItemDeduct();
            itemMaster.itemAttend = self.screenModel.screenModelE.getCurrentItemAttend();
            itemMaster.itemPeriod = self.getCurrentItemPeriod();
            itemMaster.itemBDs = self.getCurrentItemBDs();
            return itemMaster;
        }
        getCurrentItemPeriod() {
            let self = this;
            let ItemPeriod: qmm012.h.service.model.ItemPeriod;
            switch (self.GridCurrentCategoryAtr_B_001()) {
                case 0:
                    ItemPeriod = self.screenModel.screenModelC.currentItemPeriod();
                    break;
                case 1:
                    ItemPeriod = self.screenModel.screenModelD.currentItemPeriod();
                    break;
            }
            return ItemPeriod;
        }
        getCurrentItemBDs() {
            let self = this;
            let ItemBDs: Array<qmm012.i.service.model.ItemBD>;
            switch (self.GridCurrentCategoryAtr_B_001()) {
                case 0:
                    ItemBDs = self.screenModel.screenModelC.currentItemBDs();
                    break;
                case 1:
                    ItemBDs = self.screenModel.screenModelD.currentItemBDs();
                    break;
            }
            return ItemBDs;
        }
        LoadGridList(ItemCode?) {
            let self = this;
            let categoryAtr = self.categoryAtr;
            let dispSet = self.checked_B_002() ? -1 : 0;
            service.findAllItemMaster(categoryAtr, dispSet).done(function(MasterItems: Array<service.model.ItemMaster>) {
                self.GridlistItems_B_001(MasterItems);
                //set selected first item in list
                if (self.GridlistItems_B_001().length > 0)
                    // if not itemcode parameter
                    if (!ItemCode)
                        self.GridlistCurrentCode_B_001(self.GridlistItems_B_001()[0].itemCode);
                    else
                        self.GridlistCurrentCode_B_001(ItemCode);
            }).fail(function(res) {
                alert(res);
            });
        }

        deleteItem() {
            let self = this;
            let ItemMaster = self.GetCurrentItemMaster();
            let index = self.GridlistItems_B_001.indexOf(self.GridlistCurrentItem_B_001());
            if (index >= 0) {
                nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function() {
                    service.deleteItemMaster(ItemMaster).done(function(any) {
                        //reload grid and set select code after delete item success
                        if (index) {
                            let selectItemCode;
                            if (self.GridlistItems_B_001().length - 1 > 1) {
                                if (index < self.GridlistItems_B_001().length - 1)
                                    selectItemCode = self.GridlistItems_B_001()[index - 1].itemCode;
                                else
                                    selectItemCode = self.GridlistItems_B_001()[index - 2].itemCode;
                            } else
                                selectItemCode = '';
                            self.LoadGridList(selectItemCode);
                        }
                    }).fail(function(res) {

                        alert(res);
                    });
                });
            }
        }

        getCurrentZeroDisplaySet() {
            let Result;
            let self = this;
            let CurrentGroup = self.GridCurrentCategoryAtr_B_001();
            switch (CurrentGroup) {
                case 0:
                    Result = self.screenModel.screenModelC.CurrentZeroDisplaySet();
                    break;
                case 1:
                    Result = self.screenModel.screenModelD.CurrentZeroDisplaySet();
                    break;
                case 2:
                    Result = self.screenModel.screenModelE.CurrentZeroDisplaySet();
                    break;
            }
            return Result;
        }
        getCurrentItemDisplayAtr() {
            let Result;
            let self = this;
            let CurrentGroup = self.GridCurrentCategoryAtr_B_001();
            switch (CurrentGroup) {
                case 0:
                    Result = self.screenModel.screenModelC.CurrentItemDisplayAtr();
                    break;
                case 1:
                    Result = self.screenModel.screenModelD.CurrentItemDisplayAtr();
                    break;
                case 2:
                    Result = self.screenModel.screenModelE.CurrentItemDisplayAtr();
                    break;
            }
            return Result;
        }
        openADialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('../a/index.xhtml', { height: 480, width: 630, dialogClass: "no-close" }).onClosed(function(): any {
                if (nts.uk.ui.windows.getShared('groupCode') != undefined) {
                    let groupCode = Number(nts.uk.ui.windows.getShared('groupCode'));
                    //set layout for new.
                    self.enable_B_INP_002(true);
                    self.GridCurrentCategoryAtr_B_001(groupCode);
                }
            });
        }
        submitData() {
            let self = this;
            let ItemMaster = self.GetCurrentItemMaster();
            //if self.enable_B_INP_002 == true is mean New mode
            if (self.enable_B_INP_002()) {
                self.addNewItemMaster(ItemMaster);
            } else {
                self.updateItemMaster(ItemMaster);
            }
        }
        addNewItemMaster(ItemMaster: service.model.ItemMaster) {
            let self = this;
            service.addItemMaster(ItemMaster).done(function(any) {
                self.LoadGridList(ItemMaster.itemCode);
            }).fail(function(res) {
                alert(res);
            });
        }
        updateItemMaster(ItemMaster: service.model.ItemMaster) {
            let self = this;
            service.updateItemMaster(ItemMaster).done(function(any) {
                self.LoadGridList(ItemMaster.itemCode);
            }).fail(function(res) {
                alert(res);
            });

        }

        openJDialog() {
            let self = this;
            nts.uk.ui.windows.sub.modal('../j/index.xhtml', { height: 700, width: 970, dialogClass: "no-close" }).onClosed(function(): any {
            });
        }
    }

    class ComboboxItemModel {
        code: number;
        name: string;

        constructor(code: number, name: string) {
            this.code = code;
            this.name = name;
        }
    }
}