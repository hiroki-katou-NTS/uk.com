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
                    self.itemName_histId = ko.observable('');
                    self.historyId = ko.observable('');
                    self.selectedCodes_His = ko.observable('');
                    self.isEnable_histId = ko.observable(true);
                    self.itemHist = ko.observable(null);
                    self.code = ko.observable(null);
                    self.arr = ko.observableArray([]);
                    self.dataSource = ko.observableArray([]);
                    self.dataSource2 = ko.observableArray([]);
                    self.singleSelectedCode = ko.observable(null);
                    self.selectedCodes_treegrid = ko.observableArray([]);
                    self.headers = ko.observableArray(["", ""]);
                    self.lengthTreeCurrent = ko.observable(null);
                    self.lengthTreeBegin = ko.observable(null);
                    self.numberItemNew = ko.observable(0);
                    self.A_INP_002 = ko.observable(null);
                    self.A_INP_002_enable = ko.observable(false);
                    self.A_INP_003 = ko.observable(null);
                    self.A_INP_004 = ko.observable(null);
                    self.A_INP_007 = ko.observable(null);
                    self.A_INP_008 = ko.observable(null);
                    self.currentItem_treegrid = ko.observable(null);
                    self.checknull = ko.observable(null);
                    self.listDtothaydoi = ko.observable(null);
                    self.dtoAdd = ko.observable(null);
                    self.checkAddHist1 = ko.observable('');
                    self.newEndDate = ko.observable(null);
                    self.singleSelectedCode.subscribe(function (codeChangeds) {
                        if (self.code().code == "CODE_004") {
                            var _dt = self.dataSource();
                            var _code = self.singleSelectedCode();
                            var current = self.findHira(_code, _dt);
                            if (current.historyId == "") {
                                self.A_INP_002_enable(true);
                                self.A_INP_002("");
                                self.A_INP_003("");
                                self.A_INP_004("");
                                self.A_INP_007("");
                                $("#A_INP_002").focus();
                            }
                            else {
                                self.A_INP_002(current.departmentCode);
                                self.A_INP_003(current.name);
                                self.A_INP_004(current.fullName);
                                self.A_INP_007(current.externalCode);
                                self.A_INP_002_enable(false);
                            }
                        }
                        else if (self.code().code == "CODE_005") {
                            var _dt = self.dataSource();
                            var _code = self.singleSelectedCode();
                            var current = self.findHira(_code, _dt);
                            self.A_INP_002(current.departmentCode);
                            self.A_INP_003(current.name);
                            self.A_INP_004(current.fullName);
                            self.A_INP_007(current.externalCode);
                        }
                    });
                    self.selectedCodes_His.subscribe((function (codeChanged) {
                        if (self.code().code == "CODE_004") {
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
                                            self.A_INP_003(self.dataSource()[0].name);
                                            self.A_INP_004(self.dataSource()[0].fullName);
                                            if (self.dataSource()[0].externalCode != null)
                                                self.A_INP_007(self.dataSource()[0].externalCode);
                                        }
                                    }).fail(function (error) {
                                        alert(error.message);
                                    });
                                    a.service.getMemoByHistId(self.historyId())
                                        .done(function (memo) {
                                        if (memo != null) {
                                            self.A_INP_008(memo.memo);
                                        }
                                    }).fail(function (error) {
                                        alert(error.message);
                                    });
                                    dfd.resolve();
                                    return dfd.promise();
                                }
                                else {
                                    console.log("=== historyId null");
                                }
                            }
                        }
                        else if (self.code().code == "CODE_005") {
                        }
                    }));
                }
                ScreenModel.prototype.register = function () {
                    var self = this;
                    if (self.code().code == "CODE_004") {
                        if (self.checknull() === "landau" && self.itemHistId().length == 1 && self.checkInput()) {
                            var dto = new model.AddDepartmentDto(self.A_INP_002(), "", "9999-12-31", self.A_INP_007(), self.A_INP_004(), "001", self.A_INP_003(), self.itemaddHist.startDate, self.A_INP_008(), null);
                            var dfd = $.Deferred();
                            a.service.addDepartment(dto)
                                .done(function (mess) {
                                self.start();
                                location.reload();
                            }).fail(function (error) {
                                if (error.message == "ER026") {
                                    alert("trung companyCode");
                                }
                            });
                            dfd.resolve();
                            return dfd.promise();
                        }
                        if (self.A_INP_002_enable() == false && self.checkInput() && self.checkAddHist1() == '') {
                            var dfd = $.Deferred();
                            var hisdto = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                            var _dt = self.dataSource();
                            var _code = self.singleSelectedCode();
                            var current = self.findHira(_code, _dt);
                            var dto = new model.AddDepartmentDto(self.A_INP_002(), current.historyId, hisdto.endDate, self.A_INP_007(), self.A_INP_004(), current.hierarchyCode, self.A_INP_003(), hisdto.startDate, self.A_INP_008(), null);
                            var arr = new Array;
                            arr.push(dto);
                            debugger;
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
                            var _dto_1 = new model.AddDepartmentDto(self.A_INP_002(), hisdto.historyId, hisdto.endDate, self.A_INP_007(), self.A_INP_004(), self.dtoAdd().hierarchyCode, self.A_INP_003(), hisdto.startDate, self.A_INP_008(), null);
                            var data = self.listDtothaydoi();
                            var arr_1 = new Array;
                            arr_1.push(_dto_1);
                            debugger;
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
                        if (self.checkAddHist1() == "AddhistoryFromLatest") {
                            console.log(self.dataSource2());
                            var _dt_1 = self.dataSource2();
                            if (_dt_1.length > 0) {
                                _dt_1[0].memo = self.A_INP_008();
                            }
                            self.dataSource2(_dt_1);
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
                        if (self.checkAddHist1() == "AddhistoryFromBeggin") {
                            if (self.checkInput()) {
                                var _dto = new model.AddDepartmentDto(self.A_INP_002(), null, self.itemHistId()[0].endDate, self.A_INP_007(), self.A_INP_004(), "001", self.A_INP_003(), self.itemHistId()[0].startDate, self.A_INP_008(), null);
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
                    }
                };
                ScreenModel.prototype.deletebtn = function () {
                    var self = this;
                    if (self.code().code == "CODE_004") {
                        var _dt = self.dataSource();
                        var _dtflat = nts.uk.util.flatArray(_dt, 'children');
                        debugger;
                        var _code = self.singleSelectedCode();
                        var current = self.findHira(_code, _dt);
                        var deleteobj_1 = new model.DepartmentDeleteDto(current.departmentCode, current.historyId, current.hierarchyCode);
                        nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function () {
                            var dfd2 = $.Deferred();
                            a.service.deleteDepartment(deleteobj_1)
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
                                    debugger;
                                    for (var i in changeIndexChild) {
                                        var item1 = changeIndexChild[i];
                                        var itemAddH = (parseInt(item1.hierarchyCode.substr(item1.hierarchyCode.length - 3, 3)) - 1) + "";
                                        while ((itemAddH + "").length < 3)
                                            itemAddH = "0" + itemAddH;
                                        item1.hierarchyCode = phc + itemAddH;
                                        item1.editIndex = true;
                                        if (item1.children.length > 0) {
                                            self.updateHierachy2(item1, phc + itemAddH);
                                        }
                                    }
                                    var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function (item) { return item.editIndex; });
                                    if (editObjs.length > 0) {
                                        var currentHis = self.itemHist();
                                        for (var k = 0; k < editObjs.length; k++) {
                                            editObjs[k].startDate = currentHis.startDate;
                                            editObjs[k].endDate = currentHis.endDate;
                                            editObjs[k].memo = self.A_INP_008();
                                        }
                                    }
                                    self.listDtothaydoi(editObjs);
                                }
                                else {
                                    var index = _dt.indexOf(current);
                                    var phc = current.hierarchyCode;
                                    var chc = parseInt(current.hierarchyCode.substr(current.hierarchyCode.length - 3, 3));
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
                                            self.updateHierachy2(item2, itemAddH);
                                        }
                                    }
                                    var editObjs = _.filter(nts.uk.util.flatArray(self.dataSource(), 'children'), function (item) { return item.editIndex; });
                                    if (editObjs.length > 0) {
                                        var currentHis = self.itemHist();
                                        for (var k = 0; k < editObjs.length; k++) {
                                            editObjs[k].startDate = currentHis.startDate;
                                            editObjs[k].endDate = currentHis.endDate;
                                            editObjs[k].memo = self.A_INP_008();
                                        }
                                    }
                                    self.listDtothaydoi(editObjs);
                                }
                                var data = self.listDtothaydoi();
                                if (data != null) {
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
                    }
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
                    console.log(self.lengthTreeCurrent());
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
                                console.log("===" + self.currentItem_treegrid());
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
                    if (self.A_INP_002() == "") {
                        alert("コードが入力されていません。");
                        $("#A_INP_002").focus();
                        return false;
                    }
                    else if (self.A_INP_003() == "") {
                        alert("名称 が入力されていません。");
                        $("#A_INP_003").focus();
                        return false;
                    }
                    else if (self.A_INP_004() == "") {
                        alert("表示名称 が入力されていません。");
                        $("#A_INP_004").focus();
                        return false;
                    }
                    return true;
                };
                ScreenModel.prototype.openCDialog = function () {
                    var self = this;
                    if (self.checknull() == "landau") {
                        nts.uk.ui.windows.setShared('datanull', "datanull");
                        nts.uk.ui.windows.sub.modal('/view/cmm/009/c/index.xhtml', { title: '明細レイアウトの作成＞履歴追加' }).onClosed(function () {
                            var itemAddHistory = nts.uk.ui.windows.getShared('itemHistory');
                            if (itemAddHistory !== undefined) {
                                var itemadd = new viewmodel.model.HistoryDto(itemAddHistory.startYearMonth, "9999-12-31", "");
                                self.itemaddHist = itemadd;
                                self.itemHistId().push(self.itemaddHist);
                                self.selectedCodes_His(self.itemaddHist.startDate);
                                self.A_INP_002_enable(true);
                                self.A_INP_002("");
                                self.A_INP_003("");
                                self.A_INP_004("");
                                self.A_INP_007("");
                                $("#A_INP_002").focus();
                                if (itemAddHistory.memo !== null) {
                                    self.A_INP_008(itemAddHistory.memo);
                                }
                            }
                        });
                    }
                    else {
                        if (self.selectedCodes_His() == null)
                            return false;
                        console.log(self.selectedCodes_His() + "=== test== " + self.historyId());
                        nts.uk.ui.windows.setShared('datanull', "notnull");
                        nts.uk.ui.windows.setShared('startDateOfHis', self.itemHistId()[0].startDate);
                        nts.uk.ui.windows.sub.modal('/view/cmm/009/c/index.xhtml', { title: '明細レイアウトの作成＞履歴追加' }).onClosed(function () {
                            var itemAddHistory = nts.uk.ui.windows.getShared('itemHistory');
                            if (itemAddHistory.checked == true) {
                                var add = new viewmodel.model.HistoryDto(itemAddHistory.startYearMonth, "9999/12/31", "");
                                console.log(add);
                                var arr = self.itemHistId();
                                arr.unshift(add);
                                var startDate = new Date(itemAddHistory.startYearMonth);
                                startDate.setDate(startDate.getDate() - 1);
                                var strStartDate = startDate.getFullYear() + '-' + (startDate.getMonth() + 1) + '-' + startDate.getDate();
                                arr[1].endDate = strStartDate;
                                self.itemHistId(arr);
                                self.selectedCodes_His(itemAddHistory.startYearMonth);
                                self.A_INP_008(itemAddHistory.memo);
                                console.log(self.selectedCodes_His());
                                var _dt = self.dataSource();
                                var hisdto_1 = self.findHist_Dep(self.itemHistId(), self.selectedCodes_His());
                                var _dt2 = _.forEach(nts.uk.util.flatArray(self.dataSource(), 'children'), function (item) {
                                    item.historyId = "";
                                    item.startDate = hisdto_1.startDate;
                                    item.endDate = hisdto_1.endDate;
                                });
                                self.checkAddHist1("AddhistoryFromLatest");
                                self.dataSource2(_dt2);
                                debugger;
                            }
                            else {
                                var add = new viewmodel.model.HistoryDto(itemAddHistory.startYearMonth, "9999/12/31", "");
                                console.log(add);
                                var arr = self.itemHistId();
                                arr.unshift(add);
                                var startDate = new Date(itemAddHistory.startYearMonth);
                                startDate.setDate(startDate.getDate() - 1);
                                var strStartDate = startDate.getFullYear() + '-' + (startDate.getMonth() + 1) + '-' + startDate.getDate();
                                arr[1].endDate = strStartDate;
                                self.itemHistId(arr);
                                self.selectedCodes_His(self.itemHistId()[0].startDate);
                                self.dataSource(null);
                                self.A_INP_002("");
                                self.A_INP_002_enable(true);
                                self.A_INP_003("");
                                self.A_INP_004("");
                                self.A_INP_007("");
                                $("#A_INP_002").focus();
                                self.checkAddHist1("AddhistoryFromBeggin");
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
                    console.log(hisdto);
                    console.log(index);
                    nts.uk.ui.windows.setShared('itemHist', hisdto);
                    nts.uk.ui.windows.sub.modal('/view/cmm/009/d/index.xhtml', { title: '明細レイアウトの作成＞履歴の編集' }).onClosed(function () {
                        var newstartDate = nts.uk.ui.windows.getShared('newstartDate');
                        var isRadiocheck = nts.uk.ui.windows.getShared('isradio');
                        debugger;
                        if (isRadiocheck == "1") {
                            var dfd = $.Deferred();
                            a.service.deleteHistory(self.itemHistId()[0].historyId)
                                .done(function () {
                                console.log("done");
                                var dfd = $.Deferred();
                                a.service.updateEndDateByHistoryId(self.itemHistId()[1].historyId)
                                    .done(function () {
                                    location.reload();
                                })
                                    .fail(function () {
                                });
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
                    if (self.code().code == "CODE_004") {
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
                                            editObjs[k].memo = self.A_INP_008();
                                        }
                                    }
                                    self.dtoAdd(newObj);
                                    self.listDtothaydoi(editObjs);
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
                                            editObjs[k].memo = self.A_INP_008();
                                        }
                                    }
                                    self.dtoAdd(newObj);
                                    self.listDtothaydoi(editObjs);
                                    _dt.splice(index, 0, newObj);
                                }
                                self.dataSource(_dt);
                                self.numberItemNew(1);
                                self.singleSelectedCode(newObj.departmentCode);
                                self.resetInput();
                            }
                        }
                        else {
                            alert("more than 889 item");
                        }
                    }
                    else if (self.code().code == "CODE_005") {
                    }
                };
                ScreenModel.prototype.resetInput = function () {
                    var self = this;
                    self.A_INP_002("");
                    self.A_INP_002_enable(true);
                    self.A_INP_003("");
                    self.A_INP_004("");
                    self.A_INP_007("");
                    $("#A_INP_002").focus();
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
                    if (self.code().code == "CODE_004") {
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
                                            editObjs[k].memo = self.A_INP_008();
                                        }
                                    }
                                    self.dtoAdd(newObj);
                                    if (editObjs.length > 0) {
                                        self.listDtothaydoi(editObjs);
                                    }
                                    else {
                                        self.listDtothaydoi();
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
                                            editObjs[k].memo = self.A_INP_008();
                                        }
                                    }
                                    self.dtoAdd(newObj);
                                    if (editObjs.length > 0) {
                                        self.listDtothaydoi(editObjs);
                                    }
                                    else {
                                        self.listDtothaydoi();
                                    }
                                    _dt.splice(index + 1, 0, newObj);
                                }
                                self.dataSource(_dt);
                                self.numberItemNew(1);
                                self.singleSelectedCode(newObj.departmentCode);
                                self.resetInput();
                            }
                        }
                        else {
                            alert("more than 889 item");
                        }
                    }
                    else if (self.code().code == "CODE_005") {
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
                                var newObj = new model.Dto('', new Date().getTime() + "", "", "", "", "", hierachy_current + hierachyItemadd, "情報を登録してください", current.startDate, []);
                                current.children.push(newObj);
                                var currentHis = self.itemHist();
                                newObj.startDate = currentHis.startDate;
                                newObj.endDate = currentHis.endDate;
                                newObj.memo = self.A_INP_008();
                                self.dtoAdd(newObj);
                                self.listDtothaydoi();
                                self.dataSource(_dt);
                                self.numberItemNew(1);
                                self.singleSelectedCode(newObj.departmentCode);
                                self.A_INP_002("");
                                self.A_INP_003("");
                                $("#A_INP_002").focus();
                            }
                            else {
                                alert("hierarchy item current = 10 ,not push item child to tree");
                            }
                        }
                        console.log(self.dataSource());
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
                        dfd.resolve();
                    }).fail(function (error) {
                        console.log(error);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.start = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getCodeOfDepWP().done(function (code) {
                        self.code(code);
                        console.log(code);
                        if (self.code().code == "CODE_004") {
                            a.service.getAllDepartment().done(function (departmentQueryResult) {
                                var departmentQueryResultmodel = departmentQueryResult;
                                console.log(departmentQueryResult);
                                if (departmentQueryResultmodel.histories == null) {
                                    nts.uk.ui.windows.setShared('datanull', "datanull");
                                    self.checknull("landau");
                                    self.openCDialog();
                                }
                                else {
                                    if (departmentQueryResult.departments.length > 0) {
                                        self.dataSource(departmentQueryResult.departments);
                                    }
                                    if (departmentQueryResult.memo) {
                                        self.A_INP_008(departmentQueryResult.memo.memo);
                                    }
                                    if (departmentQueryResultmodel.histories.length > 0) {
                                        self.itemHistId(departmentQueryResultmodel.histories);
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
                                console.log(error);
                            });
                        }
                        else if (self.code().code == "CODE_005") {
                            a.service.getAllWorkplace().done(function (workplaceQueryResult) {
                                var workplaceQueryResult = workplaceQueryResult;
                                console.log(workplaceQueryResult);
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
                                        self.A_INP_008(workplaceQueryResult.memo.memo);
                                    }
                                    if (workplaceQueryResult.histories.length > 0) {
                                        self.itemHistId(workplaceQueryResult.histories);
                                        if (self.dataSource().length > 0) {
                                            self.filteredData2 = ko.observableArray(nts.uk.util.flatArray(self.dataSource(), "children"));
                                            self.singleSelectedCode(workplaceQueryResult.workPlaces[0].departmentCode);
                                        }
                                    }
                                }
                                dfd.resolve();
                            }).fail(function (error) {
                                console.log(error);
                            });
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
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
                var GetCodeDto = (function () {
                    function GetCodeDto() {
                    }
                    return GetCodeDto;
                }());
                model.GetCodeDto = GetCodeDto;
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