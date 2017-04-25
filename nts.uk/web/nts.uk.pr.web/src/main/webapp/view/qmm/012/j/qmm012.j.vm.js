var qmm012;
(function (qmm012) {
    var j;
    (function (j) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    //gridlist
                    this.dataSource = ko.observableArray([]);
                    this.updateSource = ko.observableArray([]);
                    this.currentGroupCode = ko.observable(0);
                    this.oldGroupCode = ko.observable(0);
                    var self = this;
                    //gridlist
                    self.columns = ko.observableArray([
                        { headerText: "コード", key: "itemCode", dataType: "string", width: "40px" },
                        { headerText: "名称", key: "itemName", dataType: "string", width: "180px" },
                        { headerText: "略名", key: "itemAbName", dataType: "string", width: "160px" },
                        { headerText: "英語", key: "itemAbNameE", dataType: "string", width: "140px" },
                        { headerText: "略名多言語", key: "itemAbNameO", dataType: "string", width: "160px" }
                    ]);
                    self.columnSettings = ko.observableArray([
                        { columnKey: "itemCode", editorOptions: { type: "numeric", disabled: true } },
                        { columnKey: "itemName", editorOptions: { type: "string", disabled: true } },
                        { columnKey: "itemAbName", editorOptions: { type: "string", disabled: true } },
                        { columnKey: "itemAbNameE", validation: true },
                        { columnKey: "itemAbNameO", validation: true }
                    ]);
                    self.currentGroupCode.subscribe(function (newValue) {
                        $("#J_Lst_ItemList").igGridUpdating("endEdit", true, true);
                        if (newValue != self.oldGroupCode()) {
                            self.activeDirty(function () {
                                self.reLoadGridData();
                                $(".title").text(self.genTitleText(newValue));
                            }, function () {
                                self.reLoadGridData();
                                $(".title").text(self.genTitleText(newValue));
                            }, function () {
                                self.currentGroupCode(self.oldGroupCode());
                                $("#sidebar").ntsSideBar("active", self.oldGroupCode());
                            });
                        }
                    });
                    self.LoadGridData();
                }
                ScreenModel.prototype.genTitleText = function (GroupCode) {
                    var result = "";
                    switch (GroupCode) {
                        case 0:
                            result = "支給項目";
                            break;
                        case 1:
                            result = "控除項目";
                            break;
                        case 2:
                            result = "勤怠項目";
                            break;
                        case 3:
                            result = "記事項目";
                            break;
                        case 9:
                            result = "その他";
                            break;
                    }
                    return result;
                };
                ScreenModel.prototype.reLoadGridData = function () {
                    var self = this;
                    j.service.findAllItemMasterByCategory(self.currentGroupCode()).done(function (MasterItems) {
                        self.dataSource(MasterItems);
                        $("#J_Lst_ItemList").igGrid("option", "dataSource", self.dataSource());
                        self.dirty = new nts.uk.ui.DirtyChecker(self.dataSource);
                        self.oldGroupCode(self.currentGroupCode());
                    });
                };
                ScreenModel.prototype.LoadGridData = function () {
                    var self = this;
                    j.service.findAllItemMasterByCategory(self.currentGroupCode()).done(function (MasterItems) {
                        self.dataSource(MasterItems);
                        self.dirty = new nts.uk.ui.DirtyChecker(self.dataSource);
                        self.BindGrid();
                        self.oldGroupCode(self.currentGroupCode());
                    });
                };
                ScreenModel.prototype.ChangeGroup = function (GroupCode) {
                    var self = this;
                    self.currentGroupCode(GroupCode);
                };
                ScreenModel.prototype.BindGrid = function () {
                    var self = this;
                    $("#J_Lst_ItemList").igGrid({
                        primaryKey: "itemCode",
                        columns: self.columns(),
                        dataSource: self.dataSource(),
                        width: "760px",
                        height: "500px",
                        autoCommit: true,
                        features: [
                            {
                                name: "Updating",
                                editCellEnding: self.saveData.bind(self),
                                enableAddRow: false,
                                editMode: "cell",
                                enableDeleteRow: false,
                                cancelTooltip: "Click to cancel",
                                columnSettings: self.columnSettings()
                            },
                            {
                                name: "RowSelectors"
                            },
                            {
                                name: "Selection"
                            }]
                    });
                };
                ScreenModel.prototype.saveData = function (evt, ui) {
                    var self = this;
                    if (ui.columnKey == "itemAbNameE" || ui.columnKey == "itemAbNameO") {
                        var item = _.find(self.dataSource(), function (ItemModel) {
                            return ItemModel.itemCode == ui.rowID;
                        });
                        if (item) {
                            if (self.validate(ui.value)) {
                                var itemUpdate = _.find(self.updateSource(), function (ItemModel) {
                                    return ItemModel.itemCode == ui.rowID;
                                });
                                if (itemUpdate) {
                                    var index = self.updateSource().indexOf(itemUpdate);
                                    itemUpdate[ui.columnKey] = ui.value;
                                }
                                else {
                                    item[ui.columnKey] = ui.value;
                                    self.updateSource().push(item);
                                }
                            }
                            else {
                                return false;
                            }
                        }
                    }
                };
                ScreenModel.prototype.validate = function (value) {
                    var result = true;
                    if (value != "") {
                        var n = 0;
                        $('#J_Lst_ItemList').ntsError('clear');
                        $('.ui-igedit').removeClass("errorValidate");
                        for (var _i = 0, value_1 = value; _i < value_1.length; _i++) {
                            var char = value_1[_i];
                            var p = value.charCodeAt(value.indexOf(char));
                            if (p < 128) {
                                n++;
                            }
                            else
                                n += 2;
                        }
                        if (n > 12) {
                            $('#J_Lst_ItemList').ntsError('set', 'Max length for this input is 12');
                            $('.ui-igedit').addClass("errorValidate");
                            result = false;
                        }
                    }
                    return result;
                };
                ScreenModel.prototype.updateData = function () {
                    var self = this;
                    $("#J_Lst_ItemList").igGridUpdating("endEdit", true, true);
                    if (self.updateSource().length) {
                        j.service.updateNameItemMaster(self.updateSource()).done(function (res) {
                            //after update, need clear array
                            self.updateSource([]);
                            self.reLoadGridData();
                        }).fail(function (res) {
                            nts.uk.ui.dialog.alert(res);
                        });
                    }
                };
                ScreenModel.prototype.activeDirty = function (MainFunction, YesFunction, NoFunction) {
                    var self = this;
                    if (self.dirty ? !self.dirty.isDirty() : true) {
                        MainFunction();
                    }
                    else {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。  ").ifYes(function () {
                            if (YesFunction)
                                YesFunction();
                        }).ifNo(function () {
                            if (NoFunction)
                                NoFunction();
                        });
                    }
                };
                ScreenModel.prototype.CloseDialog = function () {
                    var self = this;
                    $("#J_Lst_ItemList").igGridUpdating("endEdit", true, true);
                    self.activeDirty(function () { nts.uk.ui.windows.close(); }, function () { nts.uk.ui.windows.close(); });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = j.viewmodel || (j.viewmodel = {}));
    })(j = qmm012.j || (qmm012.j = {}));
})(qmm012 || (qmm012 = {}));
