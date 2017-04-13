var qmm012;
(function (qmm012) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel(screenModel) {
                    this.enable = ko.observable(true);
                    this.selectedCode_B_001 = ko.observable(1);
                    this.GridlistItems_B_001 = ko.observableArray([]);
                    this.GridlistCurrentCode_B_001 = ko.observable('');
                    this.GridlistCurrentItem_B_001 = ko.observable(null);
                    this.GridCurrentItemAbName_B_001 = ko.observable('');
                    this.GridCurrentItemName_B_001 = ko.observable('');
                    this.GridCurrentDisplaySet_B_001 = ko.observable(false);
                    this.GridCurrentUniteCode_B_001 = ko.observable('');
                    this.GridCurrentCategoryAtr_B_001 = ko.observable(0);
                    this.GridCurrentCategoryAtrName_B_001 = ko.observable('');
                    this.GridCurrentCodeAndName_B_001 = ko.observable('');
                    this.B_INP_002_text = ko.observable('');
                    this.categoryAtr = -1;
                    this.checked_B_002 = ko.observable(false);
                    this.enable_B_INP_002 = ko.observable(false);
                    var self = this;
                    self.screenModel = screenModel;
                    self.ComboBoxItemList_B_001 = ko.observableArray([
                        new ComboboxItemModel(1, '全件'),
                        new ComboboxItemModel(2, '支給項目'),
                        new ComboboxItemModel(3, '控除項目'),
                        new ComboboxItemModel(4, '勤怠項目'),
                        new ComboboxItemModel(5, '記事項目'),
                        new ComboboxItemModel(6, 'その他項目')
                    ]);
                    self.selectedCode_B_001.subscribe(function (newValue) {
                        var categoryAtr;
                        switch (newValue) {
                            case 1:
                                categoryAtr = -1;
                                break;
                            case 2:
                                categoryAtr = 0;
                                break;
                            case 3:
                                categoryAtr = 1;
                                break;
                            case 4:
                                categoryAtr = 2;
                                break;
                            case 5:
                                categoryAtr = 3;
                                break;
                            case 6:
                                categoryAtr = 9;
                                break;
                        }
                        self.categoryAtr = categoryAtr;
                        self.LoadGridList();
                    });
                    self.checked_B_002.subscribe(function () {
                        self.LoadGridList();
                    });
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
                    self.GridlistCurrentCode_B_001.subscribe(function (newValue) {
                        var item = _.find(self.GridlistItems_B_001(), function (ItemModel) {
                            return ItemModel.itemCode == newValue;
                        });
                        self.GridlistCurrentItem_B_001(item);
                        self.B_INP_002_text(newValue);
                    });
                    self.GridlistCurrentItem_B_001.subscribe(function (itemModel) {
                        self.GridCurrentItemName_B_001(itemModel ? itemModel.itemName : '');
                        self.GridCurrentUniteCode_B_001(itemModel ? itemModel.uniteCode : '');
                        self.GridCurrentCategoryAtr_B_001(itemModel ? itemModel.categoryAtr : 0);
                        ChangeGroup(self.GridCurrentCategoryAtr_B_001());
                        self.GridCurrentCodeAndName_B_001(itemModel ? itemModel.itemCode + ' ' + itemModel.itemName : '');
                        self.GridCurrentDisplaySet_B_001(itemModel ? itemModel.displaySet == 1 ? true : false : false);
                        self.GridCurrentItemAbName_B_001(itemModel ? itemModel.itemAbName : '');
                        self.GridCurrentCategoryAtrName_B_001(itemModel ? itemModel.categoryAtrName : '');
                        if (self.GridlistCurrentCode_B_001() != undefined) {
                            self.enable_B_INP_002(false);
                        }
                    });
                    self.GridCurrentCategoryAtr_B_001.subscribe(function (newValue) {
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
                    self.texteditor_B_INP_002 = {
                        value: self.B_INP_002_text,
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            textalign: "left"
                        })),
                        enable: self.enable_B_INP_002
                    };
                    self.texteditor_B_INP_003 = {
                        value: self.GridCurrentItemName_B_001,
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            textalign: "left"
                        }))
                    };
                    self.texteditor_B_INP_004 = {
                        value: self.GridCurrentItemAbName_B_001,
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            textalign: "left"
                        }))
                    };
                }
                ScreenModel.prototype.GetCurrentItemMaster = function () {
                    var self = this;
                    var CurrentGroup = self.GridCurrentCategoryAtr_B_001();
                    var itemMaster = new b.service.model.ItemMaster(self.B_INP_002_text(), self.GridCurrentItemName_B_001(), self.GridCurrentCategoryAtr_B_001(), self.GridCurrentCategoryAtrName_B_001(), self.GridCurrentItemAbName_B_001(), self.GridlistCurrentItem_B_001() ? self.GridlistCurrentItem_B_001().itemAbNameO : self.GridCurrentItemAbName_B_001(), self.GridlistCurrentItem_B_001() ? self.GridlistCurrentItem_B_001().itemAbNameE : self.GridCurrentItemAbName_B_001(), self.GridCurrentDisplaySet_B_001() == true ? 1 : 0, self.GridCurrentUniteCode_B_001(), self.getCurrentZeroDisplaySet(), self.getCurrentItemDisplayAtr(), 1);
                    itemMaster.itemSalary = self.screenModel.screenModelC.GetCurrentItemSalary();
                    itemMaster.itemDeduct = self.screenModel.screenModelD.GetCurrentItemDeduct();
                    itemMaster.itemAttend = self.screenModel.screenModelE.getCurrentItemAttend();
                    return itemMaster;
                };
                ScreenModel.prototype.LoadGridList = function (ItemCode) {
                    var self = this;
                    var categoryAtr = self.categoryAtr;
                    var dispSet = self.checked_B_002() ? -1 : 0;
                    b.service.findAllItemMaster(categoryAtr, dispSet).done(function (MasterItems) {
                        self.GridlistItems_B_001(MasterItems);
                        if (self.GridlistItems_B_001().length > 0)
                            if (!ItemCode)
                                self.GridlistCurrentCode_B_001(self.GridlistItems_B_001()[0].itemCode);
                            else
                                self.GridlistCurrentCode_B_001(ItemCode);
                    }).fail(function (res) {
                        alert(res);
                    });
                };
                ScreenModel.prototype.DeleteDialog = function () {
                    var self = this;
                    var ItemMaster = self.GetCurrentItemMaster();
                    var index = self.GridlistItems_B_001.indexOf(self.GridlistCurrentItem_B_001());
                    b.service.deleteItemMaster(ItemMaster).done(function (any) {
                        if (index) {
                            var selectItemCode = void 0;
                            if (self.GridlistItems_B_001().length - 1 > 1) {
                                if (index < self.GridlistItems_B_001().length - 1)
                                    selectItemCode = self.GridlistItems_B_001()[index - 1].itemCode;
                                else
                                    selectItemCode = self.GridlistItems_B_001()[index - 2].itemCode;
                            }
                            else
                                selectItemCode = '';
                            self.LoadGridList(selectItemCode);
                        }
                    }).fail(function (res) {
                        alert(res);
                    });
                };
                ScreenModel.prototype.getCurrentZeroDisplaySet = function () {
                    var Result;
                    var self = this;
                    var CurrentGroup = self.GridCurrentCategoryAtr_B_001();
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
                };
                ScreenModel.prototype.getCurrentItemDisplayAtr = function () {
                    var Result;
                    var self = this;
                    var CurrentGroup = self.GridCurrentCategoryAtr_B_001();
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
                };
                ScreenModel.prototype.openADialog = function () {
                    var self = this;
                    nts.uk.ui.windows.sub.modal('../a/index.xhtml', { height: 480, width: 630, dialogClass: "no-close" }).onClosed(function () {
                        if (nts.uk.ui.windows.getShared('groupCode') != undefined) {
                            var groupCode = Number(nts.uk.ui.windows.getShared('groupCode'));
                            self.GridlistCurrentCode_B_001('');
                            self.GridCurrentCategoryAtr_B_001(groupCode);
                            self.enable_B_INP_002(true);
                        }
                    });
                };
                ScreenModel.prototype.submitData = function () {
                    var self = this;
                    var ItemMaster = self.GetCurrentItemMaster();
                    if (self.enable_B_INP_002()) {
                        self.addNewItemMaster(ItemMaster);
                    }
                    else {
                        self.updateItemMaster(ItemMaster);
                    }
                };
                ScreenModel.prototype.addNewItemMaster = function (ItemMaster) {
                    var self = this;
                    b.service.addItemMaster(ItemMaster).done(function (any) {
                        self.LoadGridList(ItemMaster.itemCode);
                    }).fail(function (res) {
                        alert(res);
                    });
                };
                ScreenModel.prototype.updateItemMaster = function (ItemMaster) {
                    var self = this;
                    b.service.updateItemMaster(ItemMaster).done(function (any) {
                        self.LoadGridList(ItemMaster.itemCode);
                    }).fail(function (res) {
                        alert(res);
                    });
                };
                ScreenModel.prototype.openJDialog = function () {
                    var self = this;
                    nts.uk.ui.windows.sub.modal('../j/index.xhtml', { height: 700, width: 970, dialogClass: "no-close" }).onClosed(function () {
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var ComboboxItemModel = (function () {
                function ComboboxItemModel(code, name) {
                    this.code = code;
                    this.name = name;
                }
                return ComboboxItemModel;
            }());
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qmm012.b || (qmm012.b = {}));
})(qmm012 || (qmm012 = {}));
