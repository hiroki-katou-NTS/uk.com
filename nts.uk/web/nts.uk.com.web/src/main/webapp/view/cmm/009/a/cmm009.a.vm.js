var cmm009;
(function (cmm009) {
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
                    self.selectedCodes_His = ko.observable(null);
                    self.itemHist = ko.observable(null);
                    self.arr = ko.observableArray([]);
                    self.currentItem = ko.observable(new viewmodel.model.InputField(new viewmodel.model.Dto(), true));
                    self.memo = ko.observable(new viewmodel.model.InputMemo(new viewmodel.model.MemoDto));
                    self.dirty_DetailPartment = new nts.uk.ui.DirtyChecker(self.currentItem);
                    self.dirty_Memo = new nts.uk.ui.DirtyChecker(self.memo);
                    self.dirty_ListHistory = new nts.uk.ui.DirtyChecker(self.itemHistId);
                    self.notAlert = ko.observable(true);
                    self.notAlertHist = ko.observable(true);
                    self.checkDirtyBtn = ko.observable(false);
                    self.dataSource = ko.observableArray([]);
                    self.dataSource2 = ko.observableArray([]);
                    self.singleSelectedCode = ko.observable(null);
                    self.selectedCodes_treegrid = ko.observableArray([]);
                    self.headers = ko.observableArray(["", ""]);
                    self.lengthTreeCurrent = ko.observable(null);
                    self.numberItemNew = ko.observable(0);
                    self.A_INP_MEMO = ko.observable(null);
                    self.currentItem_treegrid = ko.observable(null);
                    self.checknull = ko.observable(null);
                    self.listDtoUpdateHierachy = ko.observable(null);
                    self.dtoAdd = ko.observable(null);
                    self.checkConditionAddHist = ko.observable('');
                    self.newEndDate = ko.observable(null);
                    self.arrayItemEdit = ko.observableArray([]);
                    self.item_hist_selected = ko.observable(null);
                    self.item_dep_selected = ko.observable(null);
                    self.memobyHistoryId = ko.observable(null);
                    self.singleSelectedCode.subscribe(function (codeChangeds) {
                        var _dt = self.dataSource();
                        var _code = self.singleSelectedCode();
                        var current = self.findHira(_code, _dt);
                        if (codeChangeds == null) {
                            return;
                        }
                        if (!self.notAlert()) {
                            self.notAlert(true);
                            return;
                        }
                        if (self.dirty_DetailPartment.isDirty() || self.dirty_Memo.isDirty() || self.dirty_ListHistory.isDirty()) {
                            nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。 select").ifYes(function () {
                                self.currentItem(new viewmodel.model.InputField(current, false));
                                self.memo().A_INP_MEMO(self.memobyHistoryId());
                                if (current.historyId == "") {
                                    self.resetInput();
                                }
                                if (self.dirty_ListHistory.isDirty()) {
                                    var _dt_1 = self.itemHistId();
                                    _dt_1.splice(0, 1);
                                    _dt_1[0].endDate = "9999/12/31";
                                    self.itemHistId(_dt_1);
                                    self.notAlertHist(false);
                                    self.selectedCodes_His(self.itemHistId()[0].startDate);
                                    self.dirty_ListHistory.reset();
                                }
                                self.resetDirty();
                            }).ifNo(function () {
                                self.notAlert(false);
                                if (current.historyId == "") {
                                    self.resetInput();
                                }
                                else if (self.checkDirtyBtn() == true) {
                                    self.singleSelectedCode("999");
                                    $("#A_INP_CODE").focus();
                                }
                                else {
                                    self.singleSelectedCode(self.currentItem().A_INP_CODE());
                                }
                            });
                        }
                        else {
                            self.currentItem(new viewmodel.model.InputField(current, false));
                            if (current.historyId == "") {
                                self.resetInput();
                            }
                            self.resetDirty();
                        }
                    });
                    self.selectedCodes_His.subscribe((function (codeChanged) {
                        if (codeChanged == null) {
                            return;
                        }
                        if (!self.notAlertHist()) {
                            self.notAlertHist(true);
                            return;
                        }
                        var itemHisCurrent = self.itemHist();
                        if (self.dirty_DetailPartment.isDirty() || self.dirty_Memo.isDirty() || self.dirty_ListHistory.isDirty()) {
                            nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。 select").ifYes(function () {
                                self.findHist_Dep(self.itemHistId(), codeChanged);
                                if (self.itemHist() != null) {
                                    if (self.itemHist().historyId != "") {
                                        if (self.dirty_ListHistory.isDirty()) {
                                            for (var i = 0; i < self.itemHistId().length; i++) {
                                                if (self.itemHistId()[i].historyId == "") {
                                                    var item = self.itemHistId()[i];
                                                    self.itemHistId.remove(item);
                                                }
                                            }
                                            self.notAlertHist(false);
                                            self.selectedCodes_His(self.itemHistId()[0].startDate);
                                            self.dirty_ListHistory.reset();
                                        }
                                        self.historyId(self.itemHist().historyId);
                                        var dfd = $.Deferred();
                                        a.service.getAllDepartmentByHistId(self.historyId())
                                            .done(function (department_arr) {
                                            self.dataSource(department_arr);
                                            if (self.dataSource().length > 0) {
                                                self.filteredData2 = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "children"));
                                                self.singleSelectedCode(self.dataSource()[0].departmentCode);
                                                var current = self.findHira(self.singleSelectedCode(), self.dataSource());
                                                self.currentItem().A_INP_DEPNAME(current.name);
                                                self.currentItem().A_INP_FULLNAME(current.fullName);
                                                self.currentItem().A_INP_OUTCODE(current.externalCode);
                                                self.dirty_DetailPartment.reset();
                                            }
                                        }).fail(function (error) {
                                            alert(error.message);
                                        });
                                        a.service.getMemoByHistId(self.historyId())
                                            .done(function (memo) {
                                            if (memo != null) {
                                                self.memo().A_INP_MEMO(memo.memo);
                                                self.memobyHistoryId(memo.memo);
                                                self.dirty_Memo.reset();
                                            }
                                        }).fail(function (error) {
                                            alert(error.message);
                                        });
                                        dfd.resolve();
                                        return dfd.promise();
                                    }
                                    else {
                                    }
                                }
                            }).ifNo(function () {
                                self.notAlertHist(false);
                                self.selectedCodes_His(itemHisCurrent.startDate);
                            });
                        }
                        else {
                            self.findHist_Dep(self.itemHistId(), codeChanged);
                            if (self.itemHist() != null) {
                                if (self.itemHist().historyId != "") {
                                    for (var i = 0; i < self.itemHistId().length; i++) {
                                        if (self.itemHistId()[i].historyId == "") {
                                            var item = self.itemHistId()[i];
                                            self.itemHistId.remove(item);
                                        }
                                    }
                                    self.historyId(self.itemHist().historyId);
                                    var dfd = $.Deferred();
                                    a.service.getAllDepartmentByHistId(self.historyId())
                                        .done(function (department_arr) {
                                        self.dataSource(department_arr);
                                        if (self.dataSource().length > 0) {
                                            self.filteredData2 = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "children"));
                                            self.singleSelectedCode(self.dataSource()[0].departmentCode);
                                            self.dirty_DetailPartment.reset();
                                        }
                                    }).fail(function (error) {
                                        alert(error.message);
                                    });
                                    a.service.getMemoByHistId(self.historyId())
                                        .done(function (memo) {
                                        if (memo != null) {
                                            self.memo().A_INP_MEMO(memo.memo);
                                            self.memobyHistoryId(memo.memo);
                                            self.dirty_Memo.reset();
                                        }
                                    }).fail(function (error) {
                                        alert(error.message);
                                    });
                                    dfd.resolve();
                                    return dfd.promise();
                                }
                                else {
                                }
                            }
                        }
                    }));
                    $(document).delegate("#tree-up-down-up", "click", function () {
                        self.checkConditionAddHist("clickbtnupdown");
                        self.updateHirechyOfBtnUpDown();
                    });
                    $(document).delegate("#tree-up-down-down", "click", function () {
                        self.checkConditionAddHist("clickbtnupdown");
                        self.updateHirechyOfBtnUpDown();
                    });
                }
                ScreenModel.prototype.resetDirty = function () {
                    var self = this;
                    self.dirty_DetailPartment.reset();
                    self.dirty_Memo.reset();
                    self.dirty_ListHistory.reset();
                };
                ScreenModel.prototype.register = function () {
                    var self = this;
                    self.item_hist_selected(self.selectedCodes_His());
                    self.item_dep_selected(self.currentItem().A_INP_CODE());
                    if (self.checknull() === true && self.itemHistId().length == 1 && self.checkInput()) {
                        var dto = new model.AddDepartmentDto(self.currentItem().A_INP_CODE(), null, "9999/12/31", self.currentItem().A_INP_OUTCODE(), self.currentItem().A_INP_FULLNAME(), "001", self.currentItem().A_INP_DEPNAME(), self.itemaddHist.startDate, self.memo().A_INP_MEMO(), null);
                        var arr = new Array;
                        arr.push(dto);
                        var dfd = $.Deferred();
                        a.service.addDepartment(arr)
                            .done(function (mess) {
                            location.reload();
                        }).fail(function (error) {
                            if (error.message == "ER026") {
                                alert("trung companyCode");
                            }
                        });
                        dfd.resolve();
                        return dfd.promise();
                    }
                    if (self.currentItem().A_INP_CODE_ENABLE() == false && self.checkInput() && self.checkConditionAddHist() == '') {
                        var dfd = $.Deferred();
                        var hisdto = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                        var _dt = self.dataSource();
                        var _code = self.singleSelectedCode();
                        var current = self.findHira(_code, _dt);
                        var dto = new model.AddDepartmentDto(self.currentItem().A_INP_CODE(), current.historyId, hisdto.endDate, self.currentItem().A_INP_OUTCODE(), self.currentItem().A_INP_FULLNAME(), current.hierarchyCode, self.currentItem().A_INP_DEPNAME(), hisdto.startDate, self.memo().A_INP_MEMO(), null);
                        var arr = new Array;
                        arr.push(dto);
                        a.service.upDateListDepartment(arr)
                            .done(function (mess) {
                            location.reload();
                        }).fail(function (error) {
                            if (error.message == "ER005") {
                                alert("ko ton tai");
                            }
                        });
                        dfd.resolve();
                        return dfd.promise();
                    }
                    if (self.numberItemNew() == 1 && self.checkInput()) {
                        var self = this;
                        var dfd = $.Deferred();
                        var hisdto = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                        var _dto_1 = new model.AddDepartmentDto(self.currentItem().A_INP_CODE(), hisdto.historyId, hisdto.endDate, self.currentItem().A_INP_OUTCODE(), self.currentItem().A_INP_FULLNAME(), self.dtoAdd().hierarchyCode, self.currentItem().A_INP_DEPNAME(), hisdto.startDate, self.memo().A_INP_MEMO(), null);
                        var data = self.listDtoUpdateHierachy();
                        var arr_1 = new Array;
                        arr_1.push(_dto_1);
                        if (data != null) {
                            a.service.upDateListDepartment(data)
                                .done(function (mess) {
                                var dfd2 = $.Deferred();
                                a.service.addDepartment(arr_1)
                                    .done(function (mess) {
                                    self.start();
                                    location.reload();
                                })
                                    .fail(function (error) {
                                    if (error.message == "ER026") {
                                        alert("入力した " + _dto_1.departmentCode + "は既に存在しています。\r\n " + _dto_1.departmentCode + "  を確認してください。 ");
                                    }
                                });
                                dfd2.resolve();
                                return dfd2.promise();
                            }).fail(function (error) {
                                if (error.message == "ER005") {
                                    alert("ko ton tai");
                                }
                            });
                            dfd.resolve();
                            return dfd.promise();
                        }
                        else {
                            var dfd2 = $.Deferred();
                            a.service.addDepartment(arr_1)
                                .done(function (mess) {
                                self.start();
                                location.reload();
                            })
                                .fail(function (error) {
                                if (error.message == "ER026") {
                                    alert("入力した " + _dto_1.departmentCode + "は既に存在しています。\r\n " + _dto_1.departmentCode + "  を確認してください。 ");
                                }
                            });
                            dfd2.resolve();
                            return dfd2.promise();
                        }
                    }
                    if (self.checkConditionAddHist() == "AddhistoryFromLatest") {
                        var _dt_2 = self.dataSource2();
                        if (_dt_2.length > 0) {
                            _dt_2[0].memo = self.memo().A_INP_MEMO();
                        }
                        self.dataSource2(_dt_2);
                        var dfd2 = $.Deferred();
                        a.service.addListDepartment(self.dataSource2())
                            .done(function (mess) {
                            var dfd2 = $.Deferred();
                            var _dto = new model.AddDepartmentDto("", self.itemHistId()[1].historyId, self.itemHistId()[1].endDate, "", "", "", "", "", "addhistoryfromlatest", null);
                            var arr = new Array;
                            arr.push(_dto);
                            a.service.upDateEndDate(arr)
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
                    if (self.checkConditionAddHist() == "AddhistoryFromBeggin") {
                        if (self.checkInput()) {
                            var _dto = new model.AddDepartmentDto(self.currentItem().A_INP_CODE(), null, self.itemHistId()[0].endDate, self.currentItem().A_INP_OUTCODE(), self.currentItem().A_INP_FULLNAME(), "001", self.currentItem().A_INP_DEPNAME(), self.itemHistId()[0].startDate, self.memo().A_INP_MEMO(), null);
                            var arr1 = new Array;
                            arr1.push(_dto);
                            var dfd2 = $.Deferred();
                            a.service.addListDepartment(arr1)
                                .done(function (mess) {
                                var dfd2 = $.Deferred();
                                var _dto = new model.AddDepartmentDto("", self.itemHistId()[1].historyId, self.itemHistId()[1].endDate, "", "", "", "", "", "addhistoryfromlatest", null);
                                var arr = new Array;
                                arr.push(_dto);
                                a.service.upDateEndDate(arr)
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
                    if (self.checkConditionAddHist() == "clickbtnupdown") {
                        var _dt_3 = self.arrayItemEdit();
                        var dfd = $.Deferred();
                        if (self.arrayItemEdit().length > 1) {
                            a.service.upDateListDepartment(_dt_3)
                                .done(function (done) {
                                location.reload();
                            }).fail(function (error) {
                                alert(error.message);
                            });
                            dfd.resolve();
                            return dfd.promise();
                        }
                    }
                    self.item_dep_selected(null);
                };
                ScreenModel.prototype.updateHirechyOfBtnUpDown = function () {
                    var self = this;
                    var _dt = self.dataSource();
                    var _code = self.singleSelectedCode();
                    var current = self.findHira(_code, _dt);
                    var parrent = self.findParent(_code, _dt);
                    var hisdto = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                    if (parrent) {
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
                            item.memo = self.memo().A_INP_MEMO();
                            if (self.arrayItemEdit().length > 0) {
                                var _dt2 = self.arrayItemEdit();
                                var isDuplicateItem = _.filter(_dt2, function (item1) {
                                    return item1.departmentCode == item.departmentCode;
                                });
                                if (isDuplicateItem.length > 0) {
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
                                self.updateHierachy1(item);
                            }
                        }
                    }
                    else {
                        var chc = current.hierarchyCode;
                        var changeIndexChild = _.filter(_dt, function (item) {
                            return item;
                        });
                        for (var i in changeIndexChild) {
                            var item = changeIndexChild[i];
                            var itemHierachy = item.hierarchyCode;
                            var j = parseInt(i) + 1;
                            item.hierarchyCode = item.hierarchyCode.substr(0, item.hierarchyCode.length - 1) + j;
                            item.startDate = hisdto.startDate;
                            item.endDate = hisdto.endDate;
                            item.memo = self.memo().A_INP_MEMO();
                            if (self.arrayItemEdit().length > 0) {
                                var _dt2 = self.arrayItemEdit();
                                var isDuplicateItem = _.filter(_dt2, function (item1) {
                                    return item1.departmentCode == item.departmentCode;
                                });
                                if (isDuplicateItem.length > 0) {
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
                                self.updateHierachy1(item);
                            }
                        }
                    }
                };
                ScreenModel.prototype.deletebtn = function () {
                    var self = this;
                    var _dt = self.dataSource();
                    var _dtflat = nts.uk.util.flatArray(_dt, 'children');
                    var _code = self.singleSelectedCode();
                    var current = self.findHira(_code, _dt);
                    var deleteobj = new model.DepartmentDeleteDto(current.departmentCode, current.historyId, current.hierarchyCode);
                    if (_dtflat.length < 2) {
                        return;
                    }
                    else if (_dt.length < 2 && current.hierarchyCode.length == 3) {
                        return;
                    }
                    nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function () {
                        var dfd2 = $.Deferred();
                        a.service.deleteDepartment(deleteobj)
                            .done(function () {
                            var _dt = self.dataSource();
                            var _code = self.singleSelectedCode();
                            var current = self.findHira(_code, _dt);
                            var parrent = self.findParent(_code, _dt);
                            if (parrent) {
                                var index = parrent.children.indexOf(current);
                                var phc = parrent.hierarchyCode;
                                var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
                                var changeIndexChild = _.filter(parrent['children'], function (item) {
                                    return item.hierarchyCode.length == current.hierarchyCode.length && parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) > chc;
                                });
                                for (var i in changeIndexChild) {
                                    var item = changeIndexChild[i];
                                    var itemAddHierachy = (parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) - 1) + "";
                                    while ((itemAddHierachy + "").length < 3)
                                        itemAddHierachy = "0" + itemAddHierachy;
                                    item.hierarchyCode = phc + itemAddHierachy;
                                    item.editIndex = true;
                                    if (item.children.length > 0) {
                                        self.updateHierachy2(item, phc + itemAddHierachy);
                                    }
                                }
                                var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function (item) { return item.editIndex; });
                                if (editObjs.length > 0) {
                                    var currentHis = self.itemHist();
                                    for (var k = 0; k < editObjs.length; k++) {
                                        editObjs[k].startDate = currentHis.startDate;
                                        editObjs[k].endDate = currentHis.endDate;
                                        editObjs[k].memo = self.memo().A_INP_MEMO();
                                    }
                                }
                                self.listDtoUpdateHierachy(editObjs);
                            }
                            else {
                                var index = _dt.indexOf(current);
                                var phc = current.hierarchyCode;
                                var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
                                var changeIndexChild = _.filter(_dt, function (item) {
                                    return item.hierarchyCode.length == current.hierarchyCode.length && parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) > chc;
                                });
                                for (var i in changeIndexChild) {
                                    var item = changeIndexChild[i];
                                    var itemAddHierachy = (parseInt(item.hierarchyCode.substr(item.hierarchyCode.length - 3, 3)) - 1) + "";
                                    while ((itemAddHierachy + "").length < 3)
                                        itemAddHierachy = "0" + itemAddHierachy;
                                    item.hierarchyCode = itemAddHierachy;
                                    item.editIndex = true;
                                    if (item.children.length > 0) {
                                        self.updateHierachy2(item, itemAddHierachy);
                                    }
                                }
                                var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function (item) { return item.editIndex; });
                                if (editObjs.length > 0) {
                                    var currentHis = self.itemHist();
                                    for (var k = 0; k < editObjs.length; k++) {
                                        editObjs[k].startDate = currentHis.startDate;
                                        editObjs[k].endDate = currentHis.endDate;
                                        editObjs[k].memo = self.memo().A_INP_MEMO();
                                    }
                                }
                                self.listDtoUpdateHierachy(editObjs);
                            }
                            var data = self.listDtoUpdateHierachy();
                            if (data != null) {
                                var dfd = $.Deferred();
                                a.service.upDateListDepartment(data)
                                    .done(function (mess) {
                                    location.reload();
                                }).fail(function (error) {
                                    if (error.message == "ER005") {
                                        alert("ko ton tai");
                                    }
                                });
                                dfd.resolve();
                                return dfd.promise();
                            }
                        })
                            .fail(function () { });
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
                    if (self.currentItem().A_INP_CODE() == "") {
                        alert("コードが入力されていません。");
                        $("#A_INP_CODE").focus();
                        return false;
                    }
                    else if (self.currentItem().A_INP_DEPNAME() == "") {
                        alert("名称 が入力されていません。");
                        $("#A_INP_DEPNAME").focus();
                        return false;
                    }
                    return true;
                };
                ScreenModel.prototype.openCDialog = function () {
                    var self = this;
                    if (self.dirty_DetailPartment.isDirty() || self.dirty_Memo.isDirty() || self.dirty_ListHistory.isDirty()) {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function () {
                            var current = self.findHira(self.singleSelectedCode(), self.dataSource());
                            if (self.dirty_ListHistory.isDirty()) {
                                var _dt = self.itemHistId();
                                _dt.splice(0, 1);
                                _dt[0].endDate = "9999/12/31";
                                self.itemHistId(_dt);
                                self.dirty_ListHistory.reset();
                                self.selectedCodes_His(_dt[0].startDate);
                            }
                            self.memo().A_INP_MEMO(self.memobyHistoryId());
                            self.currentItem().A_INP_DEPNAME(current.name);
                            self.currentItem().A_INP_FULLNAME(current.fullName);
                            self.currentItem().A_INP_OUTCODE(current.externalCode);
                            self.OpenCDialogCheckDirty();
                            self.resetDirty();
                        }).ifNo(function () {
                        });
                    }
                    else {
                        self.OpenCDialogCheckDirty();
                    }
                };
                ScreenModel.prototype.OpenCDialogCheckDirty = function () {
                    var self = this;
                    if (self.checknull() == true) {
                        nts.uk.ui.windows.setShared('datanull', "datanull");
                        nts.uk.ui.windows.sub.modal('/view/cmm/009/c/index.xhtml', { title: '明細レイアウトの作成＞履歴追加' }).onClosed(function () {
                            var itemAddHistory = nts.uk.ui.windows.getShared('itemHistory');
                            if (itemAddHistory !== undefined) {
                                self.notAlertHist(false);
                                var itemadd = new viewmodel.model.HistoryDto(itemAddHistory.startYearMonth, "9999/12/31", "");
                                self.itemaddHist = itemadd;
                                self.itemHistId().push(self.itemaddHist);
                                self.selectedCodes_His(self.itemaddHist.startDate);
                                self.resetInput();
                                if (itemAddHistory.memo !== null) {
                                    self.memo().A_INP_MEMO(itemAddHistory.memo);
                                }
                            }
                        });
                    }
                    else {
                        if (self.selectedCodes_His() == null)
                            return false;
                        nts.uk.ui.windows.setShared('datanull', "notnull");
                        nts.uk.ui.windows.setShared('startDateOfHis', self.itemHistId()[0].startDate);
                        nts.uk.ui.windows.sub.modal('/view/cmm/009/c/index.xhtml', { title: '明細レイアウトの作成＞履歴追加' }).onClosed(function () {
                            var itemAddHistory = nts.uk.ui.windows.getShared('itemHistory');
                            if (itemAddHistory.checked == true) {
                                self.notAlertHist(false);
                                var add = new viewmodel.model.HistoryDto(itemAddHistory.startYearMonth, "9999/12/31", "");
                                var arr = self.itemHistId();
                                arr.unshift(add);
                                var startDate = new Date(itemAddHistory.startYearMonth);
                                startDate.setDate(startDate.getDate() - 1);
                                var strStartDate = startDate.getFullYear() + '/' + (startDate.getMonth() + 1) + '/' + startDate.getDate();
                                arr[1].endDate = strStartDate;
                                self.itemHistId(arr);
                                self.selectedCodes_His(itemAddHistory.startYearMonth);
                                if (itemAddHistory.memo !== null) {
                                    self.memo().A_INP_MEMO(itemAddHistory.memo);
                                }
                                var _dt = self.dataSource();
                                var hisdto_1 = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                                var _dt2 = _.forEach(nts.uk.util.flatArray(_dt, 'children'), function (item) {
                                    item.historyId = null;
                                    item.startDate = hisdto_1.startDate;
                                    item.endDate = hisdto_1.endDate;
                                });
                                self.checkConditionAddHist("AddhistoryFromLatest");
                                self.dataSource2(_dt2);
                            }
                            else {
                                self.notAlertHist(false);
                                var add = new viewmodel.model.HistoryDto(itemAddHistory.startYearMonth, "9999/12/31", "");
                                var arr = self.itemHistId();
                                arr.unshift(add);
                                var startDate = new Date(itemAddHistory.startYearMonth);
                                startDate.setDate(startDate.getDate() - 1);
                                var strStartDate = startDate.getFullYear() + '/' + (startDate.getMonth() + 1) + '/' + startDate.getDate();
                                arr[1].endDate = strStartDate;
                                self.itemHistId(arr);
                                self.selectedCodes_His(self.itemHistId()[0].startDate);
                                if (itemAddHistory.memo !== null) {
                                    self.memo().A_INP_MEMO(itemAddHistory.memo);
                                }
                                self.dataSource(null);
                                self.resetInput();
                                self.checkConditionAddHist("AddhistoryFromBeggin");
                            }
                        });
                    }
                };
                ScreenModel.prototype.openDDialog = function () {
                    var self = this;
                    if (self.dirty_DetailPartment.isDirty() || self.dirty_Memo.isDirty() || self.dirty_ListHistory.isDirty()) {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function () {
                            var current = self.findHira(self.singleSelectedCode(), self.dataSource());
                            if (self.dirty_ListHistory.isDirty()) {
                                var _dt = self.itemHistId();
                                _dt.splice(0, 1);
                                _dt[0].endDate = "9999/12/31";
                                self.itemHistId(_dt);
                                self.selectedCodes_His(_dt[0].startDate);
                                self.memo().A_INP_MEMO(self.memobyHistoryId());
                                self.currentItem().A_INP_DEPNAME(current.name);
                                self.currentItem().A_INP_FULLNAME(current.fullName);
                                self.currentItem().A_INP_OUTCODE(current.externalCode);
                            }
                            self.memo().A_INP_MEMO(self.memobyHistoryId());
                            self.currentItem().A_INP_DEPNAME(current.name);
                            self.currentItem().A_INP_FULLNAME(current.fullName);
                            self.currentItem().A_INP_OUTCODE(current.externalCode);
                            self.openDDialogCheckDirty();
                            self.resetDirty();
                        }).ifNo(function () {
                        });
                    }
                    else {
                        self.openDDialogCheckDirty();
                        self.dirty_DetailPartment.reset();
                        self.dirty_Memo.reset();
                        self.dirty_ListHistory.isDirty(false);
                    }
                };
                ScreenModel.prototype.openDDialogCheckDirty = function () {
                    var self = this;
                    if (self.selectedCodes_His() == null)
                        return false;
                    var hisdto = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                    var index = _.findIndex(self.itemHistId(), function (obj) { return obj == hisdto; });
                    hisdto.index = index;
                    nts.uk.ui.windows.setShared('itemHist', hisdto);
                    nts.uk.ui.windows.sub.modal('/view/cmm/009/d/index.xhtml', { title: '明細レイアウトの作成＞履歴の編集' }).onClosed(function () {
                        var newstartDate = nts.uk.ui.windows.getShared('newstartDate');
                        var isRadiocheck = nts.uk.ui.windows.getShared('isradio');
                        if (isRadiocheck == "1") {
                            var dfd = $.Deferred();
                            a.service.deleteHistory(self.itemHistId()[0].historyId)
                                .done(function () {
                                var dfd = $.Deferred();
                                if (self.itemHistId().length < 2) {
                                    location.reload();
                                }
                                a.service.updateEndDateByHistoryId(self.itemHistId()[1].historyId)
                                    .done(function () {
                                    location.reload();
                                })
                                    .fail(function () { });
                                dfd.resolve();
                                return dfd.promise();
                            })
                                .fail(function () {
                            });
                            dfd.resolve();
                            return dfd.promise();
                        }
                        else if (isRadiocheck == "2") {
                            var hisdto_2 = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                            var indexItemHist = _.findIndex(self.itemHistId(), function (obj) { return obj == hisdto_2; });
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
                            var obj = new model.updateDateMY(hisdto_2.historyId, his2, newstartDate, newEndDateRep);
                            var dfd = $.Deferred();
                            a.service.upDateStartDateandEndDate(obj)
                                .done(function () {
                                location.reload();
                            })
                                .fail(function () { });
                            dfd.resolve();
                            return dfd.promise();
                        }
                    });
                };
                ScreenModel.prototype.insertItemUp = function () {
                    var self = this;
                    if (self.dirty_DetailPartment.isDirty() || self.dirty_Memo.isDirty() || self.dirty_ListHistory.isDirty()) {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。 up").ifYes(function () {
                            var current = self.findHira(self.singleSelectedCode(), self.dataSource());
                            self.memo().A_INP_MEMO(self.memobyHistoryId());
                            self.currentItem().A_INP_DEPNAME(current.name);
                            self.currentItem().A_INP_FULLNAME(current.fullName);
                            self.currentItem().A_INP_OUTCODE(current.externalCode);
                            self.insertItemUpCheckDirty();
                            if (self.dirty_ListHistory.isDirty()) {
                                var _dt = self.itemHistId();
                                _dt.splice(0, 1);
                                _dt[0].endDate = "9999/12/31";
                                self.itemHistId(_dt);
                                var _dt2 = self.listDtoUpdateHierachy();
                                if (_dt2 != null) {
                                    _.forEach(nts.uk.util.flatArray(_dt2, 'children'), function (item) {
                                        item.historyId = self.itemHistId()[0].historyId;
                                        item.startDate = self.itemHistId()[0].startDate;
                                        item.endDate = self.itemHistId()[0].endDate;
                                    });
                                }
                                self.notAlertHist(false);
                                self.selectedCodes_His(self.itemHistId()[0].startDate);
                                self.dirty_ListHistory.reset();
                            }
                            self.resetDirty();
                        }).ifNo(function () {
                        });
                    }
                    else {
                        self.insertItemUpCheckDirty();
                    }
                    self.checkDirtyBtn(true);
                };
                ScreenModel.prototype.insertItemUpCheckDirty = function () {
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
                            var newObj = new model.Dto('', "999", "", "", "", "", hierachyItemadd, "情報を登録してください", current.startDate, []);
                            if (parrent) {
                                var index = parrent.children.indexOf(current);
                                var phc = parrent.hierarchyCode;
                                var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
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
                                        self.updateHierachy2(item1, phc + itemAddH);
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
                                        editObjs[k].memo = self.memo().A_INP_MEMO();
                                        editObjs[k].workPlaceCode = editObjs[k].departmentCode;
                                    }
                                }
                                self.dtoAdd(newObj);
                                self.listDtoUpdateHierachy(editObjs);
                            }
                            else {
                                var index = _dt.indexOf(current);
                                var phc = current.hierarchyCode;
                                var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
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
                                        self.updateHierachy2(item2, itemAddH);
                                    }
                                }
                                newObj.hierarchyCode = hierachyItemadd;
                                var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function (item) { return item.editIndex; });
                                if (editObjs.length > 0) {
                                    var currentHis = self.itemHist();
                                    for (var k = 0; k < editObjs.length; k++) {
                                        editObjs[k].startDate = currentHis.startDate;
                                        editObjs[k].endDate = currentHis.endDate;
                                        editObjs[k].memo = self.memo().A_INP_MEMO();
                                        editObjs[k].workPlaceCode = editObjs[k].departmentCode;
                                    }
                                }
                                self.dtoAdd(newObj);
                                self.listDtoUpdateHierachy(editObjs);
                                _dt.splice(index, 0, newObj);
                            }
                            self.dataSource(_dt);
                            self.numberItemNew(1);
                            self.notAlert(false);
                            self.singleSelectedCode(newObj.departmentCode);
                            self.resetInput();
                        }
                    }
                    else {
                        alert("more than 889 item");
                    }
                };
                ScreenModel.prototype.resetInput = function () {
                    var self = this;
                    self.currentItem().A_INP_CODE("");
                    self.currentItem().A_INP_CODE_ENABLE(true);
                    self.currentItem().A_INP_DEPNAME("");
                    self.currentItem().A_INP_FULLNAME("");
                    self.currentItem().A_INP_OUTCODE("");
                    $("#A_INP_CODE").focus();
                };
                ScreenModel.prototype.updateHierachy1 = function (item) {
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
                        if (self.arrayItemEdit().length > 0) {
                            var _dt2 = self.arrayItemEdit();
                            var isDuplicateItem = _.filter(_dt2, function (item1) {
                                return item1.departmentCode == itemCon.departmentCode;
                            });
                            if (isDuplicateItem.length > 0) {
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
                            self.updateHierachy1(itemCon);
                        }
                    }
                };
                ScreenModel.prototype.updateHierachy2 = function (item, hierarchyCode) {
                    var self = this;
                    for (var i in item.children) {
                        var con = item.children[i];
                        var hierachy = con.hierarchyCode.substr(0, hierarchyCode.length);
                        var ii = con.hierarchyCode.replace(hierachy, hierarchyCode);
                        con.hierarchyCode = ii;
                        con.editIndex = true;
                        if (con.children.length > 0) {
                            self.updateHierachy2(con, hierarchyCode);
                        }
                    }
                };
                ScreenModel.prototype.insertItemDown = function () {
                    var self = this;
                    if (self.dirty_DetailPartment.isDirty() || self.dirty_Memo.isDirty()) {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function () {
                            var current = self.findHira(self.singleSelectedCode(), self.dataSource());
                            self.memo().A_INP_MEMO(self.memobyHistoryId());
                            self.currentItem().A_INP_DEPNAME(current.name);
                            self.currentItem().A_INP_FULLNAME(current.fullName);
                            self.currentItem().A_INP_OUTCODE(current.externalCode);
                            self.insertItemDownCheckDirty();
                            if (self.dirty_ListHistory.isDirty()) {
                                var _dt = self.itemHistId();
                                _dt.splice(0, 1);
                                _dt[0].endDate = "9999/12/31";
                                self.itemHistId(_dt);
                                var _dt2 = self.listDtoUpdateHierachy();
                                if (_dt2 != null) {
                                    _.forEach(nts.uk.util.flatArray(_dt2, 'children'), function (item) {
                                        item.historyId = self.itemHistId()[0].historyId;
                                        item.startDate = self.itemHistId()[0].startDate;
                                        item.endDate = self.itemHistId()[0].endDate;
                                    });
                                }
                                self.notAlertHist(false);
                                self.selectedCodes_His(self.itemHistId()[0].startDate);
                                self.dirty_ListHistory.reset();
                            }
                            self.resetDirty();
                        }).ifNo(function () {
                        });
                    }
                    else {
                        self.insertItemDownCheckDirty();
                    }
                    self.checkDirtyBtn(true);
                };
                ScreenModel.prototype.insertItemDownCheckDirty = function () {
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
                            var newObj = new model.Dto('', "999", "", "", "", "", hierachyItemadd, "情報を登録してください", current.startDate, []);
                            if (parrent) {
                                var index = parrent.children.indexOf(current);
                                var phc = parrent.hierarchyCode;
                                var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
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
                                        self.updateHierachy2(item, phc + itemAddH);
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
                                        editObjs[k].memo = self.memo().A_INP_MEMO();
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
                                var phc = current.hierarchyCode;
                                var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
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
                                        self.updateHierachy2(item, itemAddH);
                                    }
                                }
                                newObj.hierarchyCode = hierachyItemadd;
                                var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function (item) { return item.editIndex; });
                                if (editObjs.length > 0) {
                                    var currentHis = self.itemHist();
                                    for (var k = 0; k < editObjs.length; k++) {
                                        editObjs[k].startDate = currentHis.startDate;
                                        editObjs[k].endDate = currentHis.endDate;
                                        editObjs[k].memo = self.memo().A_INP_MEMO();
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
                            self.notAlert(false);
                            self.singleSelectedCode(newObj.departmentCode);
                            self.resetInput();
                        }
                    }
                    else {
                        alert("more than 889 item");
                    }
                };
                ScreenModel.prototype.insertItemEnd = function () {
                    var self = this;
                    if (self.dirty_DetailPartment.isDirty() || self.dirty_Memo.isDirty()) {
                        nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function () {
                            var current = self.findHira(self.singleSelectedCode(), self.dataSource());
                            self.memo().A_INP_MEMO(self.memobyHistoryId());
                            self.currentItem().A_INP_DEPNAME(current.name);
                            self.currentItem().A_INP_FULLNAME(current.fullName);
                            self.currentItem().A_INP_OUTCODE(current.externalCode);
                            self.insertItemEndCheckDirty();
                            if (self.dirty_ListHistory.isDirty()) {
                                var _dt = self.itemHistId();
                                _dt.splice(0, 1);
                                _dt[0].endDate = "9999/12/31";
                                self.itemHistId(_dt);
                                var _dt2 = self.listDtoUpdateHierachy();
                                if (_dt2 != null) {
                                    _.forEach(nts.uk.util.flatArray(_dt2, 'children'), function (item) {
                                        item.historyId = self.itemHistId()[0].historyId;
                                        item.startDate = self.itemHistId()[0].startDate;
                                        item.endDate = self.itemHistId()[0].endDate;
                                    });
                                }
                                self.notAlertHist(false);
                                self.selectedCodes_His(self.itemHistId()[0].startDate);
                                self.dirty_ListHistory.reset();
                            }
                        }).ifNo(function () {
                        });
                    }
                    else {
                        self.insertItemEndCheckDirty();
                    }
                    self.checkDirtyBtn(true);
                };
                ScreenModel.prototype.insertItemEndCheckDirty = function () {
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
                                var newObj = new model.Dto('', "999", "", "", "", "", hierachy_current + hierachyItemadd, "情報を登録してください", current.startDate, []);
                                current.children.push(newObj);
                                var currentHis = self.itemHist();
                                newObj.startDate = currentHis.startDate;
                                newObj.endDate = currentHis.endDate;
                                newObj.memo = self.memo().A_INP_MEMO();
                                self.dtoAdd(newObj);
                                self.listDtoUpdateHierachy();
                                self.dataSource(_dt);
                                self.numberItemNew(1);
                                self.notAlert(false);
                                self.singleSelectedCode(newObj.departmentCode);
                                self.resetInput();
                            }
                            else {
                                alert("hierarchy item current is 10 ,not push item child to tree");
                            }
                        }
                    }
                    else {
                        alert("more than 889 item");
                    }
                };
                ScreenModel.prototype.getAllData = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllDepartment().done(function (departmentQueryResult) {
                        var departmentQueryResultmodel = departmentQueryResult;
                        if (departmentQueryResult.departments.length > 0) {
                            self.dataSource(departmentQueryResult.departments);
                        }
                        if (departmentQueryResult.memo) {
                            self.A_INP_MEMO(departmentQueryResult.memo.memo);
                            self.memobyHistoryId(departmentQueryResult.memo.memo);
                        }
                        if (departmentQueryResultmodel.histories.length > 0) {
                            self.itemHistId(departmentQueryResultmodel.histories);
                            if (self.dataSource().length > 0) {
                                self.filteredData2 = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "children"));
                                self.numberItemNew(0);
                            }
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllDepartment().done(function (departmentQueryResult) {
                        var departmentQueryResultmodel = departmentQueryResult;
                        if (departmentQueryResultmodel.histories == null) {
                            nts.uk.ui.windows.setShared('datanull', "datanull");
                            self.checknull(true);
                            self.openCDialog();
                        }
                        else {
                            if (departmentQueryResult.departments.length > 0) {
                                self.dataSource(departmentQueryResult.departments);
                            }
                            if (departmentQueryResult.memo) {
                                self.memo().A_INP_MEMO(departmentQueryResult.memo.memo);
                                self.memobyHistoryId(departmentQueryResult.memo.memo);
                                self.dirty_Memo.reset();
                            }
                            if (departmentQueryResultmodel.histories.length > 0) {
                                self.itemHistId(departmentQueryResultmodel.histories);
                                self.dirty_ListHistory.reset();
                                if (self.dataSource().length > 0) {
                                    self.filteredData2 = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "children"));
                                    self.singleSelectedCode(departmentQueryResult.departments[0].departmentCode);
                                    self.selectedCodes_His(self.itemHistId()[0].startDate);
                                    self.numberItemNew(0);
                                }
                            }
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                    });
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var model;
            (function (model) {
                var InputField = (function () {
                    function InputField(departmentdto, enable) {
                        var self = this;
                        self.A_INP_CODE = ko.observable(departmentdto.departmentCode);
                        self.A_INP_DEPNAME = ko.observable(departmentdto.name);
                        self.A_INP_FULLNAME = ko.observable(departmentdto.fullName);
                        self.A_INP_OUTCODE = ko.observable(departmentdto.externalCode);
                        self.A_INP_CODE_ENABLE = ko.observable(enable);
                    }
                    InputField.prototype.refresh = function () {
                        var self = this;
                        self.A_INP_CODE_ENABLE(true);
                        self.A_INP_CODE("");
                        self.A_INP_DEPNAME("");
                        self.A_INP_FULLNAME("");
                        self.A_INP_OUTCODE("");
                    };
                    return InputField;
                }());
                model.InputField = InputField;
                var InputMemo = (function () {
                    function InputMemo(memodto) {
                        var self = this;
                        self.A_INP_MEMO = ko.observable(memodto.memo);
                    }
                    return InputMemo;
                }());
                model.InputMemo = InputMemo;
                var ListHist = (function () {
                    function ListHist(memodto) {
                        var self = this;
                        self.A_INP_MEMO = ko.observable(memodto.memo);
                    }
                    return ListHist;
                }());
                model.ListHist = ListHist;
                var DepartmentQueryResult = (function () {
                    function DepartmentQueryResult() {
                    }
                    return DepartmentQueryResult;
                }());
                model.DepartmentQueryResult = DepartmentQueryResult;
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
                        self.displayDate = startDate + " ~ " + endDate;
                    }
                    return HistoryDto;
                }());
                model.HistoryDto = HistoryDto;
                var Dto = (function () {
                    function Dto(companyCode, departmentCode, historyId, endDate, externalCode, fullName, hierarchyCode, name, startDate, children) {
                        var self = this;
                        self.companyCode = companyCode;
                        self.departmentCode = departmentCode;
                        self.historyId = historyId;
                        self.endDate = endDate;
                        self.externalCode = externalCode;
                        self.fullName = fullName;
                        self.hierarchyCode = hierarchyCode;
                        self.name = name;
                        self.startDate = startDate;
                        self.children = children;
                    }
                    return Dto;
                }());
                model.Dto = Dto;
                var AddDepartmentDto = (function () {
                    function AddDepartmentDto(departmentCode, historyId, endDate, externalCode, fullName, hierarchyCode, name, startDate, memo, children) {
                        var self = this;
                        self.memo = memo;
                        self.departmentCode = departmentCode;
                        self.historyId = historyId;
                        self.endDate = endDate;
                        self.externalCode = externalCode;
                        self.fullName = fullName;
                        self.hierarchyCode = hierarchyCode;
                        self.name = name;
                        self.startDate = startDate;
                        self.children = children;
                    }
                    return AddDepartmentDto;
                }());
                model.AddDepartmentDto = AddDepartmentDto;
                var DepartmentDeleteDto = (function () {
                    function DepartmentDeleteDto(departmentCode, historyId, hierarchyCode) {
                        var self = this;
                        self.departmentCode = departmentCode;
                        self.hierarchyCode = hierarchyCode;
                        self.historyId = historyId;
                    }
                    return DepartmentDeleteDto;
                }());
                model.DepartmentDeleteDto = DepartmentDeleteDto;
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
    })(a = cmm009.a || (cmm009.a = {}));
})(cmm009 || (cmm009 = {}));
//# sourceMappingURL=cmm009.a.vm.js.map