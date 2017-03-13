var qmm012;
(function (qmm012) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel(screenModel) {
                    this.enable = ko.observable(true);
                    this.isEnable = ko.observable(true);
                    this.isEditable = ko.observable(true);
                    //gridlist
                    this.GridlistItems_B_001 = ko.observableArray([]);
                    this.GridlistCurrentCode_B_001 = ko.observable();
                    this.GridlistCurrentItem_B_001 = ko.observable();
                    this.GridCurrentItemAbName_B_001 = ko.observable();
                    this.GridCurrentItemName_B_001 = ko.observable();
                    this.GridCurrentDisplaySet_B_001 = ko.observable(false);
                    this.GridCurrentUniteCode_B_001 = ko.observable();
                    this.GridCurrentCategoryAtr_B_001 = ko.observable();
                    this.GridCurrentCategoryAtrName_B_001 = ko.observable();
                    this.GridCurrentCodeAndName_B_001 = ko.observable();
                    //Checkbox
                    //B_002
                    this.checked_B_002 = ko.observable(true);
                    this.enable_B_INP_002 = ko.observable(false);
                    //textarea 
                    this.textArea = ko.observable("");
                    var self = this;
                    self.screenModel = screenModel;
                    //start combobox data
                    //001
                    self.ComboBoxItemList_B_001 = ko.observableArray([
                        new ComboboxItemModel('1', '蜈ｨ莉ｶ'),
                        new ComboboxItemModel('2', '謾ｯ邨ｦ鬆�逶ｮ'),
                        new ComboboxItemModel('3', '謗ｧ髯､鬆�逶ｮ'),
                        new ComboboxItemModel('4', '蜍､諤�鬆�逶ｮ'),
                        new ComboboxItemModel('5', '險倅ｺ矩��逶ｮ'),
                        new ComboboxItemModel('6', '縺昴�ｮ莉夜��逶ｮ')
                    ]);
                    self.selectedCode_B_001 = ko.observable('1');
                    // start gridlist
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
                        self.GridCurrentCategoryAtr_B_001(itemModel ? itemModel.categoryAtrValue : '');
                        self.GridCurrentCodeAndName_B_001(itemModel ? itemModel.itemCode + ' ' + itemModel.itemName : '');
                        self.GridCurrentDisplaySet_B_001(itemModel ? itemModel.displaySet == 1 ? true : false : '');
                        self.GridCurrentItemAbName_B_001(itemModel ? itemModel.itemAbName : '');
                        self.GridCurrentCategoryAtrName_B_001(itemModel ? itemModel.categoryAtrName : '');
                        if (self.GridlistCurrentCode_B_001()) {
                            self.enable_B_INP_002(false);
                            b.service.findItemperiod(self.GridCurrentCategoryAtr_B_001(), self.GridlistCurrentCode_B_001()).done(function (PeriodItem) {
                                self.screenModel.screenModelC.CurrentItemPeriod(PeriodItem);
                            }).fail(function (res) {
                                // Alert message
                                alert(res);
                            });
                        }
                    });
                    self.GridCurrentCategoryAtr_B_001.subscribe(function (newValue) {
                        //  self.screenModel.screenModelC.checked_C_012(false);
                        $('#screenC').hide();
                        $('#screenD').hide();
                        $('#screenE').hide();
                        $('#screenF').hide();
                        switch (newValue) {
                            case 0:
                                $('#screenC').show();
                                break;
                            case 1:
                                $('#screenD').show();
                                break;
                            case 2:
                                $('#screenE').show();
                                break;
                            case 3:
                                $('#screenF').show();
                                break;
                        }
                    });
                    b.service.findAllItemMaster().done(function (MasterItems) {
                        for (var _i = 0, MasterItems_1 = MasterItems; _i < MasterItems_1.length; _i++) {
                            var item = MasterItems_1[_i];
                            self.GridlistItems_B_001.push(item);
                        }
                        if (self.GridlistItems_B_001().length > 0)
                            self.GridlistCurrentCode_B_001(self.GridlistItems_B_001()[0].itemCode);
                    }).fail(function (res) {
                        // Alert message
                        alert(res);
                    });
                    //end gridlist
                    //text editer
                    //INP_002
                    self.texteditor_B_INP_002 = {
                        value: self.GridlistCurrentCode_B_001,
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
                        })),
                        enable: ko.observable(true)
                    };
                    //INP_004
                    self.texteditor_B_INP_004 = {
                        value: self.GridCurrentItemAbName_B_001,
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            textalign: "left"
                        })),
                        enable: ko.observable(true)
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
                        // Alert message
                        alert(res);
                    });
                };
                ScreenModel.prototype.openADialog = function () {
                    var self = this;
                    nts.uk.ui.windows.sub.modal('../a/index.xhtml', { height: 480, width: 630, dialogClass: "no-close" }).onClosed(function () {
                        var groupCode = nts.uk.ui.windows.getShared('groupCode');
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
