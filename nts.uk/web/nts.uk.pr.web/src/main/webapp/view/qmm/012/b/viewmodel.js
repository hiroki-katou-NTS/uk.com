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
                    this.checked_B_002 = ko.observable(true);
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
                    });
                    self.GridlistCurrentItem_B_001.subscribe(function (itemModel) {
                        self.GridCurrentItemName_B_001(itemModel ? itemModel.itemName : '');
                        self.GridCurrentUniteCode_B_001(itemModel ? itemModel.uniteCode : '');
                        self.GridCurrentCategoryAtr_B_001(itemModel ? itemModel.categoryAtrValue : 0);
                        ChangeGroup(self.GridCurrentCategoryAtr_B_001());
                        self.GridCurrentCodeAndName_B_001(itemModel ? itemModel.itemCode + ' ' + itemModel.itemName : '');
                        self.GridCurrentDisplaySet_B_001(itemModel ? itemModel.displaySet == 1 ? true : false : '');
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
                    b.service.findAllItemMaster().done(function (MasterItems) {
                        for (var _i = 0, MasterItems_1 = MasterItems; _i < MasterItems_1.length; _i++) {
                            var item = MasterItems_1[_i];
                            self.GridlistItems_B_001.push(item);
                        }
                        if (self.GridlistItems_B_001().length > 0)
                            self.GridlistCurrentCode_B_001(self.GridlistItems_B_001()[0].itemCode);
                    }).fail(function (res) {
                        alert(res);
                    });
                    self.texteditor_B_INP_002 = {
                        value: self.GridlistCurrentCode_B_001,
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
                ScreenModel.prototype.DeleteDialog = function () {
                    var self = this;
                    b.service.deleteItemMaster(self.GridlistCurrentItem_B_001()).done(function (any) {
                        var index = self.GridlistItems_B_001().indexOf(self.GridlistCurrentItem_B_001());
                        if (index != undefined) {
                            self.GridlistItems_B_001().splice(index, 1);
                            if (self.GridlistItems_B_001().length - 1 > 1) {
                                if (index < self.GridlistItems_B_001().length - 1)
                                    self.GridlistCurrentCode_B_001(self.GridlistItems_B_001()[index].itemCode);
                                else
                                    self.GridlistCurrentCode_B_001(self.GridlistItems_B_001()[index - 1].itemCode);
                            }
                            else
                                self.GridlistCurrentCode_B_001(undefined);
                        }
                    }).fail(function (res) {
                        alert(res);
                    });
                };
                ScreenModel.prototype.openADialog = function () {
                    var self = this;
                    nts.uk.ui.windows.sub.modal('../a/index.xhtml', { height: 480, width: 630, dialogClass: "no-close" }).onClosed(function () {
                        var groupCode = Number(nts.uk.sessionStorage.getItemAndRemove('groupCode').value);
                        if (groupCode != undefined) {
                            self.GridlistCurrentCode_B_001(undefined);
                            self.GridCurrentCategoryAtr_B_001(groupCode);
                            self.enable_B_INP_002(true);
                        }
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
//# sourceMappingURL=viewmodel.js.map