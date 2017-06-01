var cmm011;
(function (cmm011) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.allowClick = ko.observable(true);
                    var self = this;
                    self.itemHistId = ko.observableArray([]);
                    self.historyId = ko.observable('');
                    self.enablebtnDelete = ko.observable(true);
                    self.enableDDialog = ko.observable(true);
                    self.enablebtnupdown = ko.observable(true);
                    self.selectedCodes_His = ko.observable('');
                    self.itemHist = ko.observable(null);
                    self.arr = ko.observableArray([]);
                    self.A_INP_CODE = ko.observable(null);
                    self.A_INP_NAME = ko.observable(null);
                    self.A_INP_FULLNAME = ko.observable(null);
                    self.A_INP_OUTCODE = ko.observable(null);
                    self.A_INP_CODE_enable = ko.observable(false);
                    self.A_INP_MEMO = ko.observable(null);
                    self.dataSource = ko.observableArray([]);
                    self.dataSourceToInsert = ko.observableArray([]);
                    self.singleSelectedCode = ko.observable(null);
                    self.selectedCodes_treegrid = ko.observableArray([]);
                    self.headers = ko.observableArray(["", ""]);
                    self.lengthTreeCurrent = ko.observable(null);
                    self.lengthTreeBegin = ko.observable(null);
                    self.numberItemNew = ko.observable(0);
                    self.currentItem_treegrid = ko.observable(null);
                    self.checknull = ko.observable(null);
                    self.listDtoUpdateHierachy = ko.observable(null);
                    self.dtoAdd = ko.observable(null);
                    self.checkAddHist1 = ko.observable('');
                    self.newEndDate = ko.observable(null);
                    self.arrayItemEdit = ko.observableArray([]);
                    self.singleSelectedCode.subscribe(function (codeChangeds) {
                        var _dt = self.dataSource();
                        var _code = self.singleSelectedCode();
                        var current = self.findHira(_code, _dt);
                        if (current.historyId == "") {
                            self.A_INP_CODE_enable(true);
                            self.A_INP_CODE("");
                            self.A_INP_NAME("");
                            self.A_INP_FULLNAME("");
                            self.A_INP_OUTCODE("");
                            $("#A_INP_CODE").focus();
                        }
                        else {
                            self.A_INP_CODE(current.departmentCode);
                            self.A_INP_NAME(current.name);
                            self.A_INP_FULLNAME(current.fullName);
                            self.A_INP_OUTCODE(current.externalCode);
                            self.A_INP_CODE_enable(false);
                        }
                    });
                    self.selectedCodes_His.subscribe((function (codeChanged) {
                        self.findHist_Dep(self.itemHistId(), codeChanged);
                        if (self.itemHist() != null) {
                            if (self.itemHist().historyId != "") {
                                self.enableBtn();
                                for (var i = 0; i < self.itemHistId().length; i++) {
                                    if (self.itemHistId()[i].historyId == "") {
                                        var item = self.itemHistId()[i];
                                        self.itemHistId.remove(item);
                                        var _dt = self.itemHistId();
                                        self.itemHistId([]);
                                        _dt[0].endDate = "9999/12/31";
                                        self.itemHistId(_dt);
                                    }
                                }
                                if (self.numberItemNew() == 1) {
                                    self.numberItemNew(0);
                                }
                                self.historyId(self.itemHist().historyId);
                                //get position by historyId
                                var dfd = $.Deferred();
                                a.service.getAllWorkPLaceByHistId(self.historyId())
                                    .done(function (department_arr) {
                                    self.dataSource(department_arr);
                                    if (self.dataSource().length > 0) {
                                        self.dataSourceFlat = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "children"));
                                        self.singleSelectedCode(self.dataSource()[0].departmentCode);
                                    }
                                }).fail(function (error) {
                                    alert(error.messageId);
                                });
                                a.service.getMemoWorkPLaceByHistId(self.historyId())
                                    .done(function (memo) {
                                    if (memo != null) {
                                        self.A_INP_MEMO(memo.memo);
                                    }
                                }).fail(function (error) {
                                    alert(error.messageId);
                                });
                                dfd.resolve();
                                return dfd.promise();
                            }
                            else {
                            }
                        }
                    }));
                    // event khi click button up
                    $(document).delegate("#tree-up-down-up", "click", function () {
                        self.checkAddHist1("clickbtnupdown");
                        self.updateHirechyOfBtnUpDown();
                    });
                    // event khi click button down
                    $(document).delegate("#tree-up-down-down", "click", function () {
                        self.checkAddHist1("clickbtnupdown");
                        self.updateHirechyOfBtnUpDown();
                    });
                }
                ScreenModel.prototype.register = function () {
                    var self = this;
                    self.enableBtn();
                    /*case add item lần đầu khi history == null*/
                    if (self.checknull() === "landau" && self.itemHistId().length == 1 && self.checkInput()) {
                        var dto = new model.AddWorkplaceDto(self.A_INP_CODE(), null, "9999/12/31", self.A_INP_OUTCODE(), self.A_INP_FULLNAME(), "001", self.A_INP_NAME(), self.itemaddHist.startDate, self.A_INP_MEMO(), self.A_INP_NAME(), "1", "1", null, null, null);
                        var dfd = $.Deferred();
                        var arr = new Array;
                        arr.push(dto);
                        a.service.addWorkPlace(arr)
                            .done(function (mess) {
                            location.reload();
                        }).fail(function (error) {
                            if (error.messageId == "ER005") {
                                alert("入力したコードは既に存在しています。\r\n コードを確認してください。 ");
                                $("#A_INP_CODE").focus();
                            }
                        });
                        dfd.resolve();
                        return dfd.promise();
                    }
                    /*case update item*/
                    if (self.A_INP_CODE_enable() == false && self.checkInput() && self.checkAddHist1() == '') {
                        var dfd = $.Deferred();
                        var hisdto_1 = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                        var current = self.findHira(self.singleSelectedCode(), self.dataSource());
                        var dto = new model.AddWorkplaceDto(self.A_INP_CODE(), hisdto_1.historyId, hisdto_1.endDate, self.A_INP_OUTCODE(), self.A_INP_FULLNAME(), current.hierarchyCode, self.A_INP_NAME(), hisdto_1.startDate, self.A_INP_MEMO(), current.shortName, current.parentChildAttribute1, current.parentChildAttribute2, null, null, null);
                        var wkpCodeItemUpdate_1 = self.A_INP_CODE();
                        var arr = new Array;
                        arr.push(dto);
                        a.service.upDateListWorkplace(arr)
                            .done(function (mess) {
                            self.getAllWorkPlaceByHistId(hisdto_1.historyId, wkpCodeItemUpdate_1);
                            self.numberItemNew(0);
                        }).fail(function (error) {
                            if (error.messageId == "ER06") {
                                alert("対象データがありません。");
                            }
                        });
                        dfd.resolve();
                        return dfd.promise();
                    }
                    /*case add item trong trường hợp histtory không thay đổi*/
                    if (self.numberItemNew() == 1 && self.checkInput()) {
                        var self = this;
                        var dfd = $.Deferred();
                        var hisdto_2 = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                        var _dto = new model.AddWorkplaceDto(self.A_INP_CODE(), hisdto_2.historyId, hisdto_2.endDate, self.A_INP_OUTCODE(), self.A_INP_FULLNAME(), self.dtoAdd().hierarchyCode, self.A_INP_NAME(), hisdto_2.startDate, self.A_INP_MEMO(), self.A_INP_FULLNAME(), "1", "1", null, null, null);
                        var wkpCodeItemUpdate_2 = self.A_INP_CODE();
                        var data = self.listDtoUpdateHierachy();
                        var arr_1 = new Array;
                        arr_1.push(_dto);
                        if (data != null) {
                            a.service.upDateListWorkplace(data)
                                .done(function (mess) {
                                var dfd2 = $.Deferred();
                                a.service.addWorkPlace(arr_1)
                                    .done(function (mess) {
                                    self.getAllWorkPlaceByHistId(hisdto_2.historyId, wkpCodeItemUpdate_2);
                                    self.numberItemNew(0);
                                })
                                    .fail(function (error) {
                                    if (error.messageId == "ER005") {
                                        alert("入力したコードは既に存在しています。\r\n コードを確認してください。 ");
                                        $("#A_INP_CODE").focus();
                                    }
                                });
                                dfd2.resolve();
                                return dfd2.promise();
                            }).fail(function (error) { });
                            dfd.resolve();
                            return dfd.promise();
                        }
                        else {
                            var dfd2 = $.Deferred();
                            a.service.addWorkPlace(arr_1)
                                .done(function (mess) {
                                self.getAllWorkPlaceByHistId(hisdto_2.historyId, wkpCodeItemUpdate_2);
                                self.numberItemNew(0);
                            })
                                .fail(function (error) {
                                if (error.messageId == "ER005") {
                                    alert("入力したコードは既に存在しています。\r\n コードを確認してください。 ");
                                    $("#A_INP_CODE").focus();
                                }
                            });
                            dfd2.resolve();
                            return dfd2.promise();
                        }
                    }
                    /*case add list workplace trong trường hợp thêm mới lịch sử từ lịch sử mới nhất*/
                    if (self.checkAddHist1() == "AddhistoryFromLatest") {
                        var _dt = self.dataSourceToInsert();
                        if (_dt.length > 0) {
                            _dt[0].memo = self.A_INP_MEMO();
                        }
                        self.dataSourceToInsert(_dt);
                        var dfd2 = $.Deferred();
                        a.service.addListWorkPlace(self.dataSourceToInsert())
                            .done(function (mess) {
                            var dfd2 = $.Deferred();
                            var _dto = new model.AddWorkplaceDto("", self.itemHistId()[1].historyId, self.itemHistId()[1].endDate, null, null, null, null, null, "addhistoryfromlatest", null, null, null, null, null, null);
                            var arr = new Array;
                            arr.push(_dto);
                            a.service.upDateEndDateWkp(arr)
                                .done(function () {
                                location.reload();
                            })
                                .fail(function () { });
                            self.start();
                        })
                            .fail(function (error) { });
                        dfd2.resolve();
                        return dfd2.promise();
                    }
                    /*case add  workplace trong trường hợp thêm mới lịch sử , listworkplace = null*/
                    if (self.checkAddHist1() == "AddhistoryFromBeggin") {
                        if (self.checkInput()) {
                            var _dto = new model.AddWorkplaceDto(self.A_INP_CODE(), null, self.itemHistId()[0].endDate, self.A_INP_OUTCODE(), self.A_INP_FULLNAME(), "001", self.A_INP_NAME(), self.itemHistId()[0].startDate, self.A_INP_MEMO(), null, "1", "1", null, null, null);
                            var arr1 = new Array;
                            arr1.push(_dto);
                            var dfd2 = $.Deferred();
                            a.service.addListWorkPlace(arr1)
                                .done(function (mess) {
                                var dfd2 = $.Deferred();
                                var _dto = new model.AddWorkplaceDto("", self.itemHistId()[1].historyId, self.itemHistId()[1].endDate, null, null, null, null, null, "addhistoryfromlatest", null, null, null, null, null, null);
                                var arr = new Array;
                                arr.push(_dto);
                                a.service.upDateEndDateWkp(arr)
                                    .done(function () {
                                    location.reload();
                                })
                                    .fail(function () { });
                                self.start();
                            })
                                .fail(function (error) { });
                            dfd2.resolve();
                            return dfd2.promise();
                        }
                    }
                    /*case khi click up down buron ==> *update lại hierachy của các item*/
                    if (self.checkAddHist1() == "clickbtnupdown") {
                        var _dt = self.arrayItemEdit();
                        var hisdto_3 = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                        var dfd = $.Deferred();
                        if (self.arrayItemEdit().length > 1) {
                            a.service.upDateListWorkplace(_dt)
                                .done(function (done) {
                                self.getAllWorkPlaceByHistId(hisdto_3.historyId, self.singleSelectedCode());
                            }).fail(function (error) {
                                alert(error.messageId);
                            });
                            dfd.resolve();
                            return dfd.promise();
                        }
                    }
                };
                ScreenModel.prototype.enableBtn = function () {
                    var self = this;
                    self.enablebtnDelete(true);
                    self.enableDDialog(true);
                    self.enablebtnupdown(true);
                };
                ScreenModel.prototype.disableBtn = function () {
                    var self = this;
                    self.enablebtnDelete(false);
                    self.enableDDialog(false);
                    self.enablebtnupdown(false);
                };
                ScreenModel.prototype.getAllWorkPlaceByHistId = function (historyId, workplaceCode) {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllWorkPLaceByHistId(historyId)
                        .done(function (department_arr) {
                        self.dataSource(department_arr);
                        if (self.dataSource().length > 0) {
                            self.dataSourceFlat = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "children"));
                            self.singleSelectedCode(workplaceCode);
                        }
                    }).fail(function (error) {
                        alert(error.messageId);
                    });
                };
                // update hirachy khi click btn up down
                ScreenModel.prototype.updateHirechyOfBtnUpDown = function () {
                    var self = this;
                    var _dt = self.dataSource();
                    var _code = self.singleSelectedCode();
                    var current = self.findHira(_code, _dt);
                    var parrent = self.findParent(_code, _dt);
                    var hisdto = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                    if (parrent) {
                        //Parent hirachy code
                        var phc = parrent.hierarchyCode;
                        var changeIndexChild = _.filter(parrent['children'], function (item) {
                            return item;
                        });
                        for (var i in changeIndexChild) {
                            var item = changeIndexChild[i];
                            var itemHierachy = item.hierarchyCode;
                            var j = parseInt(i) + 1;
                            item.hierarchyCode = item.hierarchyCode.substr(0, item.hierarchyCode.length - 1) + j;
                            item.startDate = hisdto.startDate;
                            item.endDate = hisdto.endDate;
                            item.workPlaceCode = item.departmentCode;
                            item.memo = self.A_INP_MEMO();
                            if (self.arrayItemEdit().length > 0) {
                                var _dt2 = self.arrayItemEdit();
                                var isDuplicateItem = _.filter(_dt2, function (item1) {
                                    return item1.departmentCode == item.departmentCode;
                                });
                                if (isDuplicateItem.length > 0) {
                                    // xóa isDuplicateItem
                                    _dt2 = jQuery.grep(_dt2, function (value) {
                                        return value.departmentCode != isDuplicateItem[0].departmentCode;
                                    });
                                    self.arrayItemEdit(_dt2);
                                    self.arrayItemEdit().push(item);
                                }
                                else {
                                    self.arrayItemEdit().push(item);
                                }
                            }
                            else {
                                self.arrayItemEdit().push(item);
                            }
                            if (item.children.length > 0) {
                                self.updateHierachyWhenclickUpdownBtn(item);
                            }
                        }
                    }
                    else {
                        //curtent hirachy code
                        var chc = current.hierarchyCode;
                        var changeIndexChilds = _.filter(_dt, function (item) {
                            return item;
                        });
                        for (var i in changeIndexChilds) {
                            var item = changeIndexChilds[i];
                            var itemHierachy = item.hierarchyCode;
                            var j = parseInt(i) + 1;
                            item.hierarchyCode = item.hierarchyCode.substr(0, item.hierarchyCode.length - 1) + j;
                            item.startDate = hisdto.startDate;
                            item.endDate = hisdto.endDate;
                            item.workPlaceCode = item.departmentCode;
                            item.memo = self.A_INP_MEMO();
                            if (self.arrayItemEdit().length > 0) {
                                var _dt2 = self.arrayItemEdit();
                                var isDuplicateItem = _.filter(_dt2, function (item1) {
                                    return item1.departmentCode == item.departmentCode;
                                });
                                if (isDuplicateItem.length > 0) {
                                    // xóa isDuplicateItem
                                    _dt2 = jQuery.grep(_dt2, function (value) {
                                        return value.departmentCode != isDuplicateItem[0].departmentCode;
                                    });
                                    self.arrayItemEdit(_dt2);
                                    self.arrayItemEdit().push(item);
                                }
                                else {
                                    self.arrayItemEdit().push(item);
                                }
                            }
                            else {
                                self.arrayItemEdit().push(item);
                            }
                            if (item.children.length > 0) {
                                self.updateHierachyWhenclickUpdownBtn(item);
                            }
                        }
                    }
                };
                ScreenModel.prototype.deletebtn = function () {
                    var self = this;
                    var hisdto = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                    var _dt = self.dataSource();
                    var _dtflat = nts.uk.util.flatArray(_dt, 'children');
                    var _code = self.singleSelectedCode();
                    var current = self.findHira(_code, _dt);
                    var deleteobj = new model.WorkPlaceDeleteDto(current.departmentCode, current.historyId, current.hierarchyCode);
                    if (_dtflat.length < 2) {
                        return;
                    }
                    else if (_dt.length < 2 && current.hierarchyCode.length == 3) {
                        return;
                    }
                    nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function () {
                        var dfd2 = $.Deferred();
                        a.service.deleteWorkPalce(deleteobj)
                            .done(function () {
                            var _dt = self.dataSource();
                            var _code = self.singleSelectedCode();
                            var current = self.findHira(_code, _dt);
                            var parrent = self.findParent(_code, _dt);
                            if (parrent) {
                                var index = parrent.children.indexOf(current);
                                //Parent hirachy code
                                var phc = parrent.hierarchyCode;
                                var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
                                // Thay đổi hirachiCode của các object bên dưới
                                var changeIndexChild = _.filter(parrent['children'], function (item) {
                                    return item.hierarchyCode.length == current.hierarchyCode.length && parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) > chc;
                                });
                                for (var i in changeIndexChild) {
                                    var item1 = changeIndexChild[i];
                                    var itemAddH = (parseInt(item1.hierarchyCode.substr(item1.hierarchyCode.length - 3, 3)) - 1) + "";
                                    while ((itemAddH + "").length < 3)
                                        itemAddH = "0" + itemAddH;
                                    item1.hierarchyCode = phc + itemAddH;
                                    item1.editIndex = true;
                                    if (item1.children.length > 0) {
                                        self.updateHierachyWhenInsertItem(item1, phc + itemAddH);
                                    }
                                }
                                var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function (item) { return item.editIndex; });
                                if (editObjs.length > 0) {
                                    var currentHis = self.itemHist();
                                    for (var k = 0; k < editObjs.length; k++) {
                                        editObjs[k].startDate = currentHis.startDate;
                                        editObjs[k].endDate = currentHis.endDate;
                                        editObjs[k].memo = self.A_INP_MEMO();
                                        editObjs[k].workPlaceCode = editObjs[k].departmentCode;
                                    }
                                }
                                self.listDtoUpdateHierachy(editObjs);
                            }
                            else {
                                var index = _dt.indexOf(current);
                                //Parent hirachy code
                                var phc = current.hierarchyCode;
                                var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
                                // Thay đổi hirachiCode của các object bên dưới
                                var changeIndexChild2 = _.filter(_dt, function (item) {
                                    return item.hierarchyCode.length == current.hierarchyCode.length && parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) > chc;
                                });
                                for (var i in changeIndexChild2) {
                                    var item2 = changeIndexChild2[i];
                                    var itemAddH = (parseInt(item2.hierarchyCode.substr(item2.hierarchyCode.length - 3, 3)) - 1) + "";
                                    while ((itemAddH + "").length < 3)
                                        itemAddH = "0" + itemAddH;
                                    item2.hierarchyCode = itemAddH;
                                    item2.editIndex = true;
                                    if (item2.children.length > 0) {
                                        self.updateHierachyWhenInsertItem(item2, itemAddH);
                                    }
                                }
                                var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function (item) { return item.editIndex; });
                                if (editObjs.length > 0) {
                                    var currentHis = self.itemHist();
                                    for (var k = 0; k < editObjs.length; k++) {
                                        editObjs[k].startDate = currentHis.startDate;
                                        editObjs[k].endDate = currentHis.endDate;
                                        editObjs[k].memo = self.A_INP_MEMO();
                                        editObjs[k].workPlaceCode = editObjs[k].departmentCode;
                                    }
                                }
                                self.listDtoUpdateHierachy(editObjs);
                            }
                            var data = self.listDtoUpdateHierachy();
                            if (data != null) {
                                var dfd = $.Deferred();
                                a.service.upDateListWorkplace(data)
                                    .done(function (mess) {
                                    var current = self.findHira(deleteobj.workplaceCode, self.dataSource());
                                    var _dt = nts.uk.util.flatArray(self.dataSource(), 'children');
                                    var selectedcode = "";
                                    var indexOfItemDelete = _.findIndex(_dt, function (o) { return o.departmentCode == deleteobj.workplaceCode; });
                                    if (indexOfItemDelete === _dt.length - 1 || current.children.length > 0) {
                                        selectedcode = (_dt[indexOfItemDelete - 1].departmentCode);
                                    }
                                    else {
                                        selectedcode = (_dt[indexOfItemDelete + 1].departmentCode);
                                    }
                                    self.getAllWorkPlaceByHistId(hisdto.historyId, selectedcode);
                                }).fail(function (error) { });
                                dfd.resolve();
                                return dfd.promise();
                            }
                        })
                            .fail(function (error) {
                            if (error.messageId == "ER06") {
                                alert("対象データがありません。");
                            }
                        });
                        dfd2.resolve();
                        return dfd2.promise();
                    }).ifNo(function () { });
                };
                ScreenModel.prototype.findHira = function (value, sources) {
                    var self = this;
                    if (!sources || !sources.length) {
                        return undefined;
                    }
                    sources = nts.uk.util.flatArray(sources, 'children');
                    self.lengthTreeCurrent(sources.length + 1);
                    return _.find(sources, function (item) { return item.departmentCode == value; });
                };
                ScreenModel.prototype.findParent = function (value, sources) {
                    var self = this, node;
                    if (!sources || !sources.length) {
                        return undefined;
                    }
                    sources = nts.uk.util.flatArray(sources, 'children');
                    self.lengthTreeCurrent(sources.length + 1);
                    return _.find(sources, function (item) { return _.find(item.children, function (child) { return child.departmentCode == value; }); });
                };
                ScreenModel.prototype.findHist_Dep = function (items, newValue) {
                    var self = this;
                    var node;
                    _.find(items, function (obj) {
                        if (!node) {
                            if (obj.startDate == newValue) {
                                node = obj;
                                self.itemHist(node);
                            }
                        }
                    });
                    return node;
                };
                ;
                //find history object
                ScreenModel.prototype.findHist = function (value) {
                    var self = this;
                    var itemModel = null;
                    _.find(self.itemHistId, function (obj) {
                        if (obj.startDate == value) {
                            itemModel = obj;
                        }
                    });
                    return itemModel;
                };
                ScreenModel.prototype.checkInput = function () {
                    var self = this;
                    if (self.A_INP_CODE() == "") {
                        alert("コードが入力されていません。");
                        $("#A_INP_CODE").focus();
                        return false;
                    }
                    else if (self.A_INP_NAME() == "") {
                        alert("名称 が入力されていません。");
                        $("#A_INP_NAME").focus();
                        return false;
                    }
                    return true;
                };
                ScreenModel.prototype.openCDialog = function () {
                    var self = this;
                    if (self.checknull() == "landau") {
                        nts.uk.ui.windows.setShared('datanull', "datanull");
                        nts.uk.ui.windows.sub.modal('/view/cmm/011/c/index.xhtml', { title: '明細レイアウトの作成＞履歴追加' }).onClosed(function () {
                            var itemAddHistory = nts.uk.ui.windows.getShared('itemHistory');
                            if (itemAddHistory) {
                                self.disableBtn();
                                var itemadd = new viewmodel.model.HistoryDto(itemAddHistory.startYearMonth, "9999/12/31", "");
                                self.itemaddHist = itemadd;
                                self.itemHistId().push(self.itemaddHist);
                                self.selectedCodes_His(self.itemaddHist.startDate);
                                self.resetInput();
                                if (itemAddHistory.memo !== null) {
                                    self.A_INP_MEMO(itemAddHistory.memo);
                                }
                                else {
                                    self.A_INP_MEMO("");
                                }
                            }
                        });
                    }
                    else {
                        if (self.selectedCodes_His() == null)
                            return false;
                        nts.uk.ui.windows.setShared('datanull', "notnull");
                        nts.uk.ui.windows.setShared('startDateOfHis', self.itemHistId()[0].startDate);
                        nts.uk.ui.windows.sub.modal('/view/cmm/011/c/index.xhtml', { title: '明細レイアウトの作成＞履歴追加' }).onClosed(function () {
                            var itemAddHistory = nts.uk.ui.windows.getShared('itemHistory');
                            if (itemAddHistory) {
                                self.disableBtn();
                                if (itemAddHistory.checked == true) {
                                    var add = new viewmodel.model.HistoryDto(itemAddHistory.startYearMonth, "9999/12/31", "");
                                    var arr = self.itemHistId();
                                    arr.unshift(add);
                                    var startDate = new Date(itemAddHistory.startYearMonth);
                                    startDate.setDate(startDate.getDate() - 1);
                                    var strStartDate = startDate.getFullYear() + '/' + (startDate.getMonth() + 1) + '/' + startDate.getDate();
                                    arr[1].endDate = strStartDate;
                                    self.itemHistId(arr);
                                    self.selectedCodes_His(itemAddHistory.startYearMonth);
                                    self.A_INP_MEMO(itemAddHistory.memo);
                                    var _dt = self.dataSource();
                                    var hisdto_4 = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                                    var _dt2 = _.forEach(nts.uk.util.flatArray(self.dataSource(), 'children'), function (item) {
                                        item.historyId = null;
                                        item.startDate = hisdto_4.startDate;
                                        item.endDate = hisdto_4.endDate;
                                        item.workPlaceCode = item.departmentCode;
                                    });
                                    self.checkAddHist1("AddhistoryFromLatest");
                                    self.dataSourceToInsert(_dt2);
                                }
                                else {
                                    var add = new viewmodel.model.HistoryDto(itemAddHistory.startYearMonth, "9999/12/31", "");
                                    var arr = self.itemHistId();
                                    arr.unshift(add);
                                    //self.itemHistId.unshift(add);
                                    var startDate = new Date(itemAddHistory.startYearMonth);
                                    startDate.setDate(startDate.getDate() - 1);
                                    var strStartDate = startDate.getFullYear() + '/' + (startDate.getMonth() + 1) + '/' + startDate.getDate();
                                    arr[1].endDate = strStartDate;
                                    self.itemHistId(arr);
                                    self.A_INP_MEMO(itemAddHistory.memo);
                                    self.selectedCodes_His(self.itemHistId()[0].startDate);
                                    self.dataSource(null);
                                    self.resetInput();
                                    self.checkAddHist1("AddhistoryFromBeggin");
                                }
                            }
                        });
                    }
                };
                ScreenModel.prototype.openDDialog = function () {
                    var self = this;
                    if (self.selectedCodes_His() == null)
                        return false;
                    var hisdto = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                    var index = _.findIndex(self.itemHistId(), function (obj) { return obj == hisdto; });
                    hisdto.index = index;
                    nts.uk.ui.windows.setShared('itemHist', hisdto);
                    nts.uk.ui.windows.sub.modal('/view/cmm/011/d/index.xhtml', { title: '明細レイアウトの作成＞履歴の編集' }).onClosed(function () {
                        var newstartDate = nts.uk.ui.windows.getShared('newstartDate');
                        var isRadiocheck = nts.uk.ui.windows.getShared('isradio');
                        if (isRadiocheck == "1") {
                            // delete thang his dau tien + delete memo
                            var dfd = $.Deferred();
                            a.service.deleteHistory(self.itemHistId()[0].historyId)
                                .done(function () {
                                // cap nhat endate thang sau --> 9999/12/31
                                var dfd = $.Deferred();
                                if (self.itemHistId().length < 2) {
                                    location.reload();
                                }
                                a.service.updateEndDateByHistoryId(self.itemHistId()[1].historyId)
                                    .done(function () {
                                    location.reload();
                                })
                                    .fail(function () {
                                });
                                dfd.resolve();
                                return dfd.promise();
                            })
                                .fail(function (error) {
                                if (error.messageId == "ER06") {
                                    alert("対象データがありません。");
                                }
                            });
                            dfd.resolve();
                            return dfd.promise();
                        }
                        else if (isRadiocheck == "2") {
                            var hisdto_5 = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                            var indexItemHist = _.findIndex(self.itemHistId(), function (obj) { return obj == hisdto_5; });
                            var his2 = "";
                            var endDate = "";
                            var newEndDateRep = "";
                            if (indexItemHist == self.itemHistId().length - 1) {
                                his2 = null;
                            }
                            else {
                                his2 = self.itemHistId()[indexItemHist + 1].historyId;
                                var newEndDate = new Date(newstartDate);
                                newEndDate.setDate(newEndDate.getDate() - 1);
                                newEndDateRep = newEndDate.getFullYear() + '/' + (newEndDate.getMonth() + 1) + '/' + newEndDate.getDate();
                            }
                            var obj_1 = new model.updateDateMY(hisdto_5.historyId, his2, newstartDate, newEndDateRep);
                            var dfd = $.Deferred();
                            a.service.upDateStartDateandEndDate(obj_1)
                                .done(function () {
                                var dfd2 = $.Deferred();
                                a.service.getAllHistory()
                                    .done(function (histories) {
                                    self.itemHistId(histories);
                                    self.selectedCodes_His(obj_1.newStartDate);
                                })
                                    .fail(function () { });
                                dfd2.resolve();
                                return dfd2.promise();
                            })
                                .fail(function () { });
                            dfd.resolve();
                            return dfd.promise();
                        }
                    });
                };
                ScreenModel.prototype.insertItemUp = function () {
                    var self = this;
                    if (self.lengthTreeCurrent() < 889) {
                        if (self.numberItemNew() == 0) {
                            var _dt = self.dataSource();
                            var _code = self.singleSelectedCode();
                            var current = self.findHira(_code, _dt);
                            var i = current.hierarchyCode.substr(current.hierarchyCode.length - 3, current.hierarchyCode.length);
                            var hierachyItemadd = (parseInt(i)) + "";
                            while ((hierachyItemadd + "").length < 3)
                                hierachyItemadd = "0" + hierachyItemadd;
                            var parrent = self.findParent(_code, _dt);
                            var newObj = new model.Dto('', "999", "", "", "", "", hierachyItemadd, "情報を登録してください", "情報を登録してください", current.startDate, []);
                            if (parrent) {
                                var index = parrent.children.indexOf(current);
                                //Parent hirachy code
                                var phc = parrent.hierarchyCode;
                                var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
                                // Thay đổi hirachiCode của các object bên dưới
                                var changeIndexChild = _.filter(parrent['children'], function (item) {
                                    return item.hierarchyCode.length == current.hierarchyCode.length && parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) >= chc;
                                });
                                for (var i in changeIndexChild) {
                                    var item1 = changeIndexChild[i];
                                    var itemAddH = (parseInt(item1.hierarchyCode.substr(item1.hierarchyCode.length - 3, 3)) + 1) + "";
                                    while ((itemAddH + "").length < 3)
                                        itemAddH = "0" + itemAddH;
                                    item1.hierarchyCode = phc + itemAddH;
                                    item1.editIndex = true;
                                    if (item1.children.length > 0) {
                                        self.updateHierachyWhenInsertItem(item1, phc + itemAddH);
                                    }
                                }
                                newObj.hierarchyCode = phc + hierachyItemadd;
                                parrent.children.splice(index, 0, newObj);
                                var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function (item) { return item.editIndex; });
                                if (editObjs.length > 0) {
                                    var currentHis = self.itemHist();
                                    for (var k = 0; k < editObjs.length; k++) {
                                        editObjs[k].startDate = currentHis.startDate;
                                        editObjs[k].endDate = currentHis.endDate;
                                        editObjs[k].memo = self.A_INP_MEMO();
                                        editObjs[k].workPlaceCode = editObjs[k].departmentCode;
                                    }
                                }
                                self.dtoAdd(newObj);
                                self.listDtoUpdateHierachy(editObjs);
                            }
                            else {
                                var index = _dt.indexOf(current);
                                //Parent hirachy code
                                var phc = current.hierarchyCode;
                                var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
                                // Thay đổi hirachiCode của các object bên dưới
                                var changeIndexChild2 = _.filter(_dt, function (item) {
                                    return item.hierarchyCode.length == current.hierarchyCode.length && parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) >= chc;
                                });
                                for (var i in changeIndexChild2) {
                                    var item2 = changeIndexChild2[i];
                                    var itemAddH = (parseInt(item2.hierarchyCode.substr(item2.hierarchyCode.length - 3, 3)) + 1) + "";
                                    while ((itemAddH + "").length < 3)
                                        itemAddH = "0" + itemAddH;
                                    item2.hierarchyCode = itemAddH;
                                    item2.editIndex = true;
                                    if (item2.children.length > 0) {
                                        self.updateHierachyWhenInsertItem(item2, itemAddH);
                                    }
                                }
                                newObj.hierarchyCode = hierachyItemadd;
                                var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function (item) { return item.editIndex; });
                                if (editObjs.length > 0) {
                                    var currentHis = self.itemHist();
                                    for (var k = 0; k < editObjs.length; k++) {
                                        editObjs[k].startDate = currentHis.startDate;
                                        editObjs[k].endDate = currentHis.endDate;
                                        editObjs[k].memo = self.A_INP_MEMO();
                                        editObjs[k].workPlaceCode = editObjs[k].departmentCode;
                                    }
                                }
                                self.dtoAdd(newObj);
                                self.listDtoUpdateHierachy(editObjs);
                                _dt.splice(index, 0, newObj);
                            }
                            self.dataSource(_dt);
                            self.numberItemNew(1);
                            self.singleSelectedCode(newObj.departmentCode);
                            self.resetInput();
                            self.disableBtn();
                        }
                        else if (self.numberItemNew() == 1) {
                            $("#A_INP_CODE").focus();
                            self.disableBtn();
                        }
                    }
                    else {
                        alert("maximum 889 item");
                    }
                };
                ScreenModel.prototype.resetInput = function () {
                    var self = this;
                    self.A_INP_CODE("");
                    self.A_INP_CODE_enable(true);
                    self.A_INP_NAME("");
                    self.A_INP_FULLNAME("");
                    self.A_INP_OUTCODE("");
                    $("#A_INP_CODE").focus();
                };
                // update hierachy when click button up down
                ScreenModel.prototype.updateHierachyWhenclickUpdownBtn = function (item) {
                    var self = this;
                    var hisdto = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                    for (var i in item.children) {
                        var itemCon = item.children[i];
                        var j = parseInt(i) + 1;
                        while ((j + "").length < 3)
                            j = "0" + j;
                        itemCon.hierarchyCode = item.hierarchyCode.substr(0, item.hierarchyCode.length) + j;
                        itemCon.startDate = hisdto.startDate;
                        itemCon.endDate = hisdto.endDate;
                        itemCon.workPlaceCode = itemCon.departmentCode;
                        if (self.arrayItemEdit().length > 0) {
                            var _dt2 = self.arrayItemEdit();
                            var isDuplicateItem = _.filter(_dt2, function (item1) {
                                return item1.departmentCode == itemCon.departmentCode;
                            });
                            if (isDuplicateItem.length > 0) {
                                // xóa isDuplicateItem
                                _dt2 = jQuery.grep(_dt2, function (value) {
                                    return value.departmentCode != isDuplicateItem[0].departmentCode;
                                });
                                self.arrayItemEdit(_dt2);
                                self.arrayItemEdit().push(itemCon);
                            }
                            else {
                                self.arrayItemEdit().push(itemCon);
                            }
                        }
                        else {
                            self.arrayItemEdit().push(itemCon);
                        }
                        if (itemCon.children.length > 0) {
                            self.updateHierachyWhenclickUpdownBtn(itemCon);
                        }
                    }
                };
                // update Hierachy when insert item to tree
                ScreenModel.prototype.updateHierachyWhenInsertItem = function (item, hierarchyCode) {
                    var self = this;
                    for (var i in item.children) {
                        var con = item.children[i];
                        var hierachy = con.hierarchyCode.substr(0, hierarchyCode.length);
                        var ii = con.hierarchyCode.replace(hierachy, hierarchyCode);
                        con.hierarchyCode = ii;
                        con.editIndex = true;
                        if (con.children.length > 0) {
                            self.updateHierachyWhenInsertItem(con, hierarchyCode);
                        }
                    }
                };
                ScreenModel.prototype.insertItemDown = function () {
                    var self = this;
                    if (self.lengthTreeCurrent() < 889) {
                        if (self.numberItemNew() == 0) {
                            var _dt = self.dataSource();
                            var _code = self.singleSelectedCode();
                            var current = self.findHira(_code, _dt);
                            var i = current.hierarchyCode.substr(current.hierarchyCode.length - 3, current.hierarchyCode.length);
                            var hierachyItemadd = (parseInt(i) + 1) + "";
                            while ((hierachyItemadd + "").length < 3)
                                hierachyItemadd = "0" + hierachyItemadd;
                            var parrent = self.findParent(_code, _dt);
                            var newObj = new model.Dto('', "999", "", "", "", "", hierachyItemadd, "情報を登録してください", "情報を登録してください", current.startDate, []);
                            if (parrent) {
                                var index = parrent.children.indexOf(current);
                                //Parent hirachy code
                                var phc = parrent.hierarchyCode;
                                var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
                                // Thay đổi hirachiCode của các object bên dưới
                                var changeIndexChild = _.filter(parrent['children'], function (item) {
                                    return item.hierarchyCode.length == current.hierarchyCode.length && parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) > chc;
                                });
                                for (var i in changeIndexChild) {
                                    var item = changeIndexChild[i];
                                    var itemAddH = (parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) + 1) + "";
                                    while ((itemAddH + "").length < 3)
                                        itemAddH = "0" + itemAddH;
                                    item.hierarchyCode = phc + itemAddH;
                                    item.editIndex = true;
                                    if (item.children.length > 0) {
                                        self.updateHierachyWhenInsertItem(item, phc + itemAddH);
                                    }
                                }
                                newObj.hierarchyCode = phc + hierachyItemadd;
                                parrent.children.splice(index + 1, 0, newObj);
                                var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function (item) { return item.editIndex; });
                                if (editObjs.length > 0) {
                                    var currentHis = self.itemHist();
                                    for (var k = 0; k < editObjs.length; k++) {
                                        editObjs[k].startDate = currentHis.startDate;
                                        editObjs[k].endDate = currentHis.endDate;
                                        editObjs[k].memo = self.A_INP_MEMO();
                                        editObjs[k].workPlaceCode = editObjs[k].departmentCode;
                                    }
                                }
                                self.dtoAdd(newObj);
                                if (editObjs.length > 0) {
                                    self.listDtoUpdateHierachy(editObjs);
                                }
                                else {
                                    self.listDtoUpdateHierachy();
                                }
                            }
                            else {
                                var index = _dt.indexOf(current);
                                //Parent hirachy code
                                var phc = current.hierarchyCode;
                                var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
                                // Thay đổi hirachiCode của các object bên dưới
                                var changeIndexChild2 = _.filter(_dt, function (item) {
                                    return item.hierarchyCode.length == current.hierarchyCode.length && parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) > chc;
                                });
                                for (var i in changeIndexChild2) {
                                    var item = changeIndexChild2[i];
                                    var itemAddH = (parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) + 1) + "";
                                    while ((itemAddH + "").length < 3)
                                        itemAddH = "0" + itemAddH;
                                    item.hierarchyCode = itemAddH;
                                    item.editIndex = true;
                                    if (item.children.length > 0) {
                                        self.updateHierachyWhenInsertItem(item, itemAddH);
                                    }
                                }
                                newObj.hierarchyCode = hierachyItemadd;
                                var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function (item) { return item.editIndex; });
                                if (editObjs.length > 0) {
                                    var currentHis = self.itemHist();
                                    for (var k = 0; k < editObjs.length; k++) {
                                        editObjs[k].startDate = currentHis.startDate;
                                        editObjs[k].endDate = currentHis.endDate;
                                        editObjs[k].memo = self.A_INP_MEMO();
                                        editObjs[k].workPlaceCode = editObjs[k].departmentCode;
                                    }
                                }
                                self.dtoAdd(newObj);
                                if (editObjs.length > 0) {
                                    self.listDtoUpdateHierachy(editObjs);
                                }
                                else {
                                    self.listDtoUpdateHierachy();
                                }
                                _dt.splice(index + 1, 0, newObj);
                            }
                            self.dataSource(_dt);
                            self.numberItemNew(1);
                            self.singleSelectedCode(newObj.departmentCode);
                            self.resetInput();
                            self.disableBtn();
                        }
                        else if (self.numberItemNew() == 1) {
                            $("#A_INP_CODE").focus();
                            self.disableBtn();
                        }
                        else {
                            alert("maximum 889 item");
                        }
                    }
                };
                ScreenModel.prototype.insertItemEnd = function () {
                    var self = this;
                    if (self.lengthTreeCurrent() < 889) {
                        if (self.numberItemNew() == 0) {
                            var _dt = self.dataSource();
                            var _code = self.singleSelectedCode();
                            var current = self.findHira(_code, _dt);
                            if (current.hierarchyCode.length < 30) {
                                var hierachy_current = current.hierarchyCode;
                                var length = current.children.length + 1;
                                var hierachyItemadd = length + "";
                                while ((hierachyItemadd + "").length < 3)
                                    hierachyItemadd = "0" + hierachyItemadd;
                                var newObj = new model.Dto('', "999", "", "", "", "", hierachy_current + hierachyItemadd, "情報を登録してください", "情報を登録してください", current.startDate, []);
                                current.children.push(newObj);
                                var currentHis = self.itemHist();
                                newObj.startDate = currentHis.startDate;
                                newObj.endDate = currentHis.endDate;
                                newObj.memo = self.A_INP_MEMO();
                                self.dtoAdd(newObj);
                                self.listDtoUpdateHierachy();
                                self.dataSource(_dt);
                                self.numberItemNew(1);
                                self.singleSelectedCode(newObj.departmentCode);
                                self.disableBtn();
                            }
                            else {
                                alert("hierarchy item current is 10 ,not push item child to tree");
                            }
                        }
                        else if (self.numberItemNew() == 1) {
                            $("#A_INP_CODE").focus();
                            self.disableBtn();
                        }
                    }
                    else {
                        alert("maximum 889 item");
                    }
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    // get init data workplace
                    a.service.getAllWorkplace().done(function (workplaceQueryResult) {
                        var workplaceQueryResult = workplaceQueryResult;
                        if (workplaceQueryResult.histories == null) {
                            nts.uk.ui.windows.setShared('datanull', "datanull");
                            self.checknull("landau");
                            self.openCDialog();
                        }
                        else {
                            if (workplaceQueryResult.workPlaces.length > 0) {
                                self.dataSource(workplaceQueryResult.workPlaces);
                            }
                            if (workplaceQueryResult.memo) {
                                self.A_INP_MEMO(workplaceQueryResult.memo.memo);
                            }
                            if (workplaceQueryResult.histories.length > 0) {
                                self.itemHistId(workplaceQueryResult.histories);
                                if (self.dataSource().length > 0) {
                                    self.dataSourceFlat = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "children"));
                                    self.singleSelectedCode(workplaceQueryResult.workPlaces[0].departmentCode);
                                    self.selectedCodes_His(self.itemHistId()[0].startDate);
                                    self.numberItemNew(0);
                                }
                            }
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            /**
                  * Model namespace.
               */
            var model;
            (function (model) {
                var DepartmentQueryResult = (function () {
                    function DepartmentQueryResult() {
                    }
                    return DepartmentQueryResult;
                }());
                model.DepartmentQueryResult = DepartmentQueryResult;
                var WorkPlaceQueryResult = (function () {
                    function WorkPlaceQueryResult() {
                    }
                    return WorkPlaceQueryResult;
                }());
                model.WorkPlaceQueryResult = WorkPlaceQueryResult;
                var MemoDto = (function () {
                    function MemoDto() {
                    }
                    return MemoDto;
                }());
                model.MemoDto = MemoDto;
                var HistoryDto = (function () {
                    function HistoryDto(startDate, endDate, historyId) {
                        var self = this;
                        self.endDate = endDate;
                        self.startDate = startDate;
                        self.historyId = historyId;
                    }
                    return HistoryDto;
                }());
                model.HistoryDto = HistoryDto;
                //    Department     
                var DtoWKP = (function () {
                    function DtoWKP(companyCode, departmentCode, historyId, endDate, externalCode, fullName, hierarchyCode, name, parentChildAttribute1, parentChildAttribute2, parentWorkCode1, parentWorkCode2, shortName, startDate, children) {
                        var self = this;
                        self.companyCode = companyCode;
                        self.departmentCode = departmentCode;
                        self.historyId = historyId;
                        self.endDate = endDate;
                        self.externalCode = externalCode;
                        self.fullName = fullName;
                        self.hierarchyCode = hierarchyCode;
                        self.name = name;
                        self.parentChildAttribute1 = parentChildAttribute1;
                        self.parentChildAttribute2 = parentChildAttribute2;
                        self.parentWorkCode1 = parentWorkCode1;
                        self.parentWorkCode2 = parentWorkCode2;
                        self.shortName = shortName;
                        self.startDate = startDate;
                        self.children = children;
                    }
                    return DtoWKP;
                }());
                model.DtoWKP = DtoWKP;
                // WOrkPLace Dto
                var Dto = (function () {
                    function Dto(companyCode, departmentCode, historyId, endDate, externalCode, fullName, hierarchyCode, name, display, startDate, children) {
                        var self = this;
                        self.companyCode = companyCode;
                        self.departmentCode = departmentCode;
                        self.historyId = historyId;
                        self.endDate = endDate;
                        self.externalCode = externalCode;
                        self.fullName = fullName;
                        self.hierarchyCode = hierarchyCode;
                        self.name = name;
                        self.display = display;
                        self.startDate = startDate;
                        self.children = children;
                    }
                    return Dto;
                }());
                model.Dto = Dto;
                var AddWorkplaceDto = (function () {
                    function AddWorkplaceDto(workPlaceCode, historyId, endDate, externalCode, fullName, hierarchyCode, name, startDate, memo, shortName, parentChildAttribute1, parentChildAttribute2, parentWorkCode1, parentWorkCode2, children) {
                        var self = this;
                        self.memo = memo;
                        self.workPlaceCode = workPlaceCode;
                        self.historyId = historyId;
                        self.endDate = endDate;
                        self.externalCode = externalCode;
                        self.fullName = fullName;
                        self.hierarchyCode = hierarchyCode;
                        self.name = name;
                        self.shortName = shortName;
                        self.startDate = startDate;
                        self.parentChildAttribute1 = parentChildAttribute1;
                        self.parentChildAttribute2 = parentChildAttribute2;
                        self.parentWorkCode1 = parentWorkCode1;
                        self.parentWorkCode2 = parentWorkCode2;
                        self.children = children;
                    }
                    return AddWorkplaceDto;
                }());
                model.AddWorkplaceDto = AddWorkplaceDto;
                var WorkPlaceDeleteDto = (function () {
                    function WorkPlaceDeleteDto(workplaceCode, historyId, hierarchyCode) {
                        var self = this;
                        self.workplaceCode = workplaceCode;
                        self.hierarchyCode = hierarchyCode;
                        self.historyId = historyId;
                    }
                    return WorkPlaceDeleteDto;
                }());
                model.WorkPlaceDeleteDto = WorkPlaceDeleteDto;
                var updateDateMY = (function () {
                    function updateDateMY(historyId1, historyId2, newStartDate, newEndDate) {
                        var self = this;
                        self.historyId1 = historyId1;
                        self.historyId2 = historyId2;
                        self.newStartDate = newStartDate;
                        self.newEndDate = newEndDate;
                    }
                    return updateDateMY;
                }());
                model.updateDateMY = updateDateMY;
            })(model = viewmodel.model || (viewmodel.model = {}));
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm011.a || (cmm011.a = {}));
})(cmm011 || (cmm011 = {}));
//# sourceMappingURL=cmm011.a.vm.js.map