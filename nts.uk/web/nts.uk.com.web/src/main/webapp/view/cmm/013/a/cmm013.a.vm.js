var cmm013;
(function (cmm013) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.jTitleRef = ko.observableArray([]);
                    self.dataRef = ko.observableArray([]);
                    self.dataRefNew = ko.observableArray([]);
                    self.label_002 = ko.observable(new Labels());
                    self.label_003 = ko.observable(new Labels());
                    self.label_004 = ko.observable(new Labels());
                    self.label_005 = ko.observable(new Labels());
                    self.label_006 = ko.observable(new Labels());
                    self.sel_002 = ko.observable('');
                    self.inp_002_code = ko.observable(null);
                    self.inp_002_enable = ko.observable(null);
                    self.inp_003_name = ko.observable(null);
                    self.inp_005_memo = ko.observable(null);
                    self.createdMode = ko.observable(true);
                    self.updatedata = ko.observable(null);
                    self.adddata = ko.observable(null);
                    self.listbox = ko.observableArray([]);
                    self.selectedCode = ko.observable('');
                    self.isEnable = ko.observable(true);
                    self.itemHist = ko.observable(null);
                    self.itemName = ko.observable('');
                    self.index_selected = ko.observable('');
                    self.srtDateLast = ko.observable(null);
                    self.startDateLast = ko.observable(null);
                    self.historyIdLast = ko.observable(null);
                    self.length = ko.observable(0);
                    self.startDateAddNew = ko.observable("");
                    self.checkRegister = ko.observable('0');
                    self.startDateUpdate = ko.observable(null);
                    self.oldStartDate = ko.observable(null);
                    self.endDateUpdate = ko.observable(null);
                    self.histIdUpdate = ko.observable(null);
                    self.startDateUpdateNew = ko.observable(null);
                    self.oldEndDate = ko.observable(null);
                    self.startDateLastEnd = ko.observable(null);
                    self.checkCoppyJtitle = ko.observable(true);
                    self.checkAddJhist = ko.observable('0');
                    self.checkAddJtitle = ko.observable('0');
                    self.checkUpdate = ko.observable('0');
                    self.checkDelete = ko.observable('0');
                    self.historyIdUpdate = ko.observable(null);
                    self.dataSource = ko.observableArray([]);
                    self.currentItem = ko.observable(null);
                    self.itemName = ko.observable('');
                    self.currentCode = ko.observable();
                    self.columns = ko.observableArray([
                        { headerText: 'コード', key: 'jobCode', width: 80 },
                        { headerText: '名称', key: 'jobName', width: 100 }
                    ]);
                    self.roundingRules = ko.observableArray([
                        { code: '0', name: '可能' },
                        { code: '1', name: '不可' },
                    ]);
                    self.selectedRuleCode = ko.observable(0);
                    self.itemList = ko.observableArray([
                        new BoxModel(0, '全員参照可能'),
                        new BoxModel(1, '全員参照不可'),
                        new BoxModel(2, 'ロール毎に設定')
                    ]);
                    self.selectedId = ko.observable(0);
                    self.enable = ko.observable(true);
                    self.selectedCode.subscribe(function (codeChanged) {
                        self.itemHist(self.findHist(codeChanged));
                        self.oldStartDate(self.itemHist().startDate);
                        self.oldEndDate(self.itemHist().endDate);
                        var chkCopy = nts.uk.ui.windows.getShared('cmm013Copy');
                        if (codeChanged === '1' && chkCopy) {
                            self.inp_002_enable(true);
                            $("#inp_002").focus();
                            return;
                        }
                        else {
                            if (self.listbox().length > 0 && self.listbox()[0].historyId === "1" && codeChanged !== "1") {
                                if (self.listbox().length > 1) {
                                    self.listbox()[1].endDate = '9999/12/31';
                                }
                                self.listbox.shift();
                            }
                            a.service.findAllPosition(codeChanged).done(function (position_arr) {
                                self.dataSource(position_arr);
                                if (self.dataSource().length > 0) {
                                    self.currentCode(self.inp_002_code() || self.dataSource()[0].jobCode);
                                }
                            }).fail(function (err) {
                                nts.uk.ui.dialog.alert(err.message);
                            });
                        }
                    });
                    self.currentCode.subscribe(function (codeChanged) {
                        self.currentItem(self.findPosition(codeChanged));
                        if (self.currentItem() != null) {
                            self.inp_002_code(self.currentItem().jobCode);
                            self.inp_003_name(self.currentItem().jobName);
                            self.inp_005_memo(self.currentItem().memo);
                            self.selectedId(self.currentItem().presenceCheckScopeSet);
                            self.inp_002_enable(false);
                            self.createdMode(false);
                            a.service.getAllJobTitleAuth(self.currentItem().historyId, self.currentItem().jobCode).done(function (jTref) {
                                if (jTref.length === 0) {
                                    $('.trLst003').css('visibility', 'hidden');
                                }
                                else {
                                    $('.trLst003').css('visibility', 'visible');
                                    self.dataRef([]);
                                    _.map(jTref, function (item) {
                                        var tmp = new model.GetAuth(item.jobCode, item.authCode, item.authName, item.referenceSettings);
                                        self.dataRef.push(tmp);
                                        return tmp;
                                    });
                                    self.dataRefNew(self.dataRef());
                                    self.createdMode(true);
                                }
                            });
                        }
                    });
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.getHistory(dfd);
                    return dfd.promise();
                };
                ScreenModel.prototype.getHistory = function (dfd) {
                    var self = this;
                    a.service.getAllHistory().done(function (history_arr) {
                        if (history_arr.length > 0) {
                            self.listbox(history_arr);
                            var histStart = _.first(history_arr);
                            var hisEnd = _.last(history_arr);
                            self.selectedCode(histStart.historyId);
                            self.srtDateLast(histStart.startDate);
                            self.endDateUpdate(histStart.endDate);
                            self.histIdUpdate(histStart.historyId);
                            self.startDateLast(histStart.startDate);
                            self.historyIdUpdate(histStart.historyId);
                            self.startDateLastEnd(hisEnd.startDate);
                            self.oldStartDate();
                            dfd.resolve(history_arr);
                        }
                        else {
                            self.openCDialog();
                            dfd.resolve();
                        }
                    });
                };
                ScreenModel.prototype.registerPosition = function () {
                    var self = this;
                    if (!self.checkPositionValue()) {
                        return;
                    }
                    else {
                        var chkInsert = nts.uk.ui.windows.getShared('cmm013Insert');
                        var chkCopy = nts.uk.ui.windows.getShared('cmm013Copy');
                        var jobInfor;
                        var dfd = $.Deferred();
                        var startDate = "";
                        if (chkInsert === true) {
                            startDate = nts.uk.ui.windows.getShared('cmm013C_startDateNew');
                        }
                        if (!chkCopy || !chkInsert) {
                            jobInfor = new model.jobTitle(self.inp_003_name(), self.inp_005_memo(), '99', self.selectedId(), '');
                        }
                        var positionInfor = new model.registryCommand(null, null, false, null, false, null, []);
                        if (self.selectedId() === 2) {
                            var refInfor = [];
                            var dataRef = ko.toJS(self.dataRef());
                            _.each(dataRef, function (obj) {
                                positionInfor.refCommand.push(new model.refJob(obj.authCode, obj.referenceSettings));
                            });
                        }
                        positionInfor.historyId = self.selectedCode();
                        positionInfor.startDate = startDate;
                        positionInfor.chkCopy = chkCopy;
                        positionInfor.jobCode = self.inp_002_code();
                        positionInfor.chkInsert = self.inp_002_enable();
                        positionInfor.positionCommand = jobInfor;
                        a.service.registry(positionInfor).done(function () {
                            nts.uk.ui.windows.setShared('cmm013Insert', '', true);
                            nts.uk.ui.windows.setShared('cmm013Copy', '', true);
                            nts.uk.ui.windows.setShared('cmm013C_startDateNew', '', true);
                            self.selectedCode.valueHasMutated();
                            self.getHistory(dfd);
                        });
                        return dfd.promise();
                    }
                };
                ScreenModel.prototype.checkPositionValue = function () {
                    var self = this;
                    if (self.inp_002_code() === "" || self.inp_002_code() === null) {
                        nts.uk.ui.dialog.alert("コードが入力されていません");
                        $('#inp_002').focus();
                        return false;
                    }
                    if (self.inp_003_name() === "" || self.inp_003_name() === null) {
                        nts.uk.ui.dialog.alert("名称が入力されていません");
                        $('inp_003').focus();
                        return false;
                    }
                    return true;
                };
                ScreenModel.prototype.findPosition = function (value) {
                    var self = this;
                    var result = _.find(self.dataSource(), function (obj) {
                        return obj.jobCode === value;
                    });
                    return (result) ? result : null;
                };
                ScreenModel.prototype.findHist = function (value) {
                    var self = this;
                    return _.find(self.listbox(), function (obj) {
                        return obj.historyId === value;
                    });
                };
                ScreenModel.prototype.initPosition = function () {
                    var self = this;
                    if (self.checkChangeData() == false || self.checkChangeData() === undefined) {
                        self.inp_002_enable(true);
                        self.inp_002_code("");
                        self.inp_003_name("");
                        self.selectedId(1);
                        self.inp_005_memo("");
                        self.currentCode(null);
                    }
                };
                ScreenModel.prototype.checkChangeData = function () {
                    var self = this;
                    if (self.checkRegister() == '1') {
                        var retVal = confirm("Changed ?");
                        if (retVal == true && self.startDateAddNew() !== undefined && self.startDateAddNew() != '') {
                            self.registerPosition();
                            return true;
                        }
                        else {
                            self.startDateAddNew('');
                            self.checkRegister('0');
                            self.getAllJobHistAfterHandler();
                            return false;
                        }
                    }
                };
                ScreenModel.prototype.getAllJobHistAfterHandler = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllHistory().done(function (history_arr) {
                        if (history_arr.length > 0) {
                            self.listbox(history_arr);
                            var histStart = _.first(history_arr);
                            var hisEnd = _.last(history_arr);
                            self.selectedCode(histStart.startDate);
                            self.srtDateLast(histStart.startDate);
                            self.endDateUpdate(histStart.endDate);
                            self.histIdUpdate(histStart.historyId);
                            self.startDateLast(histStart.startDate);
                            self.historyIdUpdate(histStart.historyId);
                            self.startDateLastEnd(hisEnd.startDate);
                            dfd.resolve(history_arr);
                        }
                        else {
                            self.openCDialog();
                            dfd.resolve();
                        }
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.openCDialog = function () {
                    var self = this;
                    if (self.checkChangeData() == false || self.checkChangeData() === undefined) {
                        var lstTmp = self.listbox();
                        nts.uk.ui.windows.setShared('CMM013_historyId', self.index_selected(), true);
                        nts.uk.ui.windows.setShared('CMM013_startDateLast', self.startDateLast(), true);
                        nts.uk.ui.windows.sub.modal('/view/cmm/013/c/index.xhtml', { title: '履歴の追加', })
                            .onClosed(function () {
                            self.startDateUpdateNew('');
                            self.startDateAddNew(nts.uk.ui.windows.getShared('cmm013C_startDateNew'));
                            self.checkCoppyJtitle(nts.uk.ui.windows.getShared('cmm013Copy'));
                            if (self.checkCoppyJtitle() == false) {
                                if (self.startDateAddNew() != '' && self.startDateAddNew() !== undefined) {
                                    var add = new model.ListHistoryDto('', self.startDateAddNew(), '9999/12/31', '1');
                                    self.initPosition();
                                    self.listbox.unshift(add);
                                    self.selectedCode('1');
                                    self.currentCode("");
                                    self.dataSource([]);
                                    $("#code").focus();
                                    var startDate = new Date(self.startDateAddNew());
                                    startDate.setDate(startDate.getDate() - 1);
                                    var strStartDate = startDate.getFullYear() + '/' + (startDate.getMonth() + 1) + '/' + startDate.getDate();
                                    var update = new model.ListHistoryDto('', self.startDateLast(), strStartDate, self.historyIdUpdate());
                                    if (self.listbox().length > 1) {
                                        self.listbox.splice(1, 1, update);
                                        self.listbox.valueHasMutated();
                                    }
                                    console.log(self.listbox());
                                }
                                else {
                                    return;
                                }
                            }
                            else {
                                if (self.startDateAddNew() != '' && self.startDateAddNew() !== undefined) {
                                    var add = new model.ListHistoryDto('', self.startDateAddNew(), '9999/12/31', '1');
                                    self.listbox.unshift(add);
                                    self.selectedCode('1');
                                    var startDate = new Date(self.startDateAddNew());
                                    startDate.setDate(startDate.getDate() - 1);
                                    var strStartDate = startDate.getFullYear() + '/' + (startDate.getMonth() + 1) + '/' + startDate.getDate();
                                    var update = new model.ListHistoryDto('', self.startDateLast(), strStartDate, self.historyIdUpdate());
                                    if (self.listbox().length > 1) {
                                        self.listbox.splice(1, 1, update);
                                        a.service.findAllPosition(self.listbox()[1].historyId).done(function (position_arr) {
                                            self.dataSource(position_arr);
                                            if (self.dataSource().length > 0) {
                                                self.currentCode(self.dataSource()[0].jobCode);
                                            }
                                        }).fail(function (err) {
                                            nts.uk.ui.dialog.alert(err.message);
                                        });
                                        self.listbox.valueHasMutated();
                                        self.inp_002_enable(false);
                                    }
                                    console.log(self.listbox());
                                }
                                else {
                                    return;
                                }
                            }
                        });
                    }
                };
                ScreenModel.prototype.openDDialog = function () {
                    var self = this;
                    if (self.checkChangeData() == false || self.checkChangeData() === undefined) {
                        var lstTmp = [];
                        self.startDateUpdateNew('');
                        nts.uk.ui.windows.setShared('cmm013HistoryId', self.selectedCode(), true);
                        nts.uk.ui.windows.setShared('cmm013StartDate', self.oldStartDate(), true);
                        nts.uk.ui.windows.setShared('cmm013EndDate', self.endDateUpdate(), true);
                        nts.uk.ui.windows.setShared('cmm013OldEndDate', self.oldEndDate(), true);
                        nts.uk.ui.windows.sub.modal('/view/cmm/013/d/index.xhtml', { title: '画面ID：D', })
                            .onClosed(function () {
                            var dfd = $.Deferred();
                            self.getHistory(dfd);
                            dfd.promise();
                        });
                    }
                };
                ScreenModel.prototype.deletePosition = function () {
                    var self = this;
                    if (self.checkRegister() == '1') {
                        self.checkChangeData();
                    }
                    else {
                        var dfd = $.Deferred();
                        var item = new model.DeleteJobTitle(self.selectedCode(), self.currentItem().jobCode);
                        self.index_of_itemDelete = self.dataSource().indexOf(self.currentItem());
                        a.service.deletePosition(item).done(function (res) {
                            self.getPositionList_afterDelete();
                        }).fail(function (res) {
                            dfd.reject(res);
                        });
                    }
                };
                ScreenModel.prototype.getPositionList_afterDelete = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.findAllPosition(self.selectedCode()).done(function (position_arr) {
                        self.dataSource = ko.observableArray([]);
                        self.dataSource(position_arr);
                        if (self.dataSource().length > 0) {
                            if (self.index_of_itemDelete === self.dataSource().length) {
                                self.currentCode(self.dataSource()[self.index_of_itemDelete - 1].jobCode);
                                self.inp_002_code(self.dataSource()[self.index_of_itemDelete - 1].jobCode);
                                self.inp_003_name(self.dataSource()[self.index_of_itemDelete - 1].jobName);
                                self.inp_005_memo(self.dataSource()[self.index_of_itemDelete - 1].memo);
                            }
                            else {
                                self.currentCode(self.dataSource()[self.index_of_itemDelete].jobCode);
                                self.inp_002_code(self.dataSource()[self.index_of_itemDelete].jobCode);
                                self.inp_003_name(self.dataSource()[self.index_of_itemDelete].jobName);
                                self.inp_005_memo(self.dataSource()[self.index_of_itemDelete].memo);
                            }
                        }
                        else {
                            self.initPosition();
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var Labels = (function () {
                function Labels() {
                    this.constraint = 'LayoutCode';
                    var self = this;
                    self.inline = ko.observable(true);
                    self.required = ko.observable(true);
                    self.enable = ko.observable(true);
                }
                return Labels;
            }());
            viewmodel.Labels = Labels;
            var BoxModel = (function () {
                function BoxModel(id, name) {
                    var self = this;
                    self.id = id;
                    self.name = name;
                }
                return BoxModel;
            }());
            viewmodel.BoxModel = BoxModel;
            var model;
            (function (model) {
                var ListHistoryDto = (function () {
                    function ListHistoryDto(companyCode, startDate, endDate, historyId) {
                        var self = this;
                        self.companyCode = companyCode;
                        self.startDate = startDate;
                        self.endDate = endDate;
                        self.historyId = historyId;
                    }
                    return ListHistoryDto;
                }());
                model.ListHistoryDto = ListHistoryDto;
                var ListPositionDto = (function () {
                    function ListPositionDto(jobCode, jobName, presenceCheckScopeSet, memo) {
                        var self = this;
                        self.jobCode = jobCode;
                        self.jobName = jobName;
                        self.presenceCheckScopeSet = presenceCheckScopeSet;
                        self.memo = memo;
                    }
                    return ListPositionDto;
                }());
                model.ListPositionDto = ListPositionDto;
                var JobRef = (function () {
                    function JobRef(historyId, jobCode, authCode, referenceSettings) {
                        this.historyId = historyId;
                        this.jobCode = jobCode;
                        this.authCode = authCode;
                        this.referenceSettings = referenceSettings;
                    }
                    return JobRef;
                }());
                model.JobRef = JobRef;
                var GetAuth = (function () {
                    function GetAuth(jobCode, authCode, authName, referenceSettings) {
                        this.jobCode = ko.observable(jobCode);
                        this.authCode = ko.observable(authCode);
                        this.authName = ko.observable(authName);
                        this.referenceSettings = ko.observable(referenceSettings);
                    }
                    return GetAuth;
                }());
                model.GetAuth = GetAuth;
                var DeleteJobTitle = (function () {
                    function DeleteJobTitle(historyId, jobCode) {
                        this.historyId = historyId;
                        this.jobCode = jobCode;
                    }
                    return DeleteJobTitle;
                }());
                model.DeleteJobTitle = DeleteJobTitle;
                var registryCommand = (function () {
                    function registryCommand(historyId, startDate, chkCopy, jobCode, chkInsert, positionCommand, refCommand) {
                        this.historyId = historyId;
                        this.startDate = startDate;
                        this.chkCopy = chkCopy;
                        this.jobCode = jobCode;
                        this.chkInsert = chkInsert;
                        this.positionCommand = positionCommand;
                        this.refCommand = refCommand;
                    }
                    return registryCommand;
                }());
                model.registryCommand = registryCommand;
                var jobTitle = (function () {
                    function jobTitle(jobName, memo, hiterarchyOrderCode, presenceCheckScopeSet, jobOutCode) {
                        this.jobName = jobName;
                        this.memo = memo;
                        this.hiterarchyOrderCode = hiterarchyOrderCode;
                        this.presenceCheckScopeSet = presenceCheckScopeSet;
                        this.jobOutCode = jobOutCode;
                    }
                    return jobTitle;
                }());
                model.jobTitle = jobTitle;
                var refJob = (function () {
                    function refJob(authorizationCode, referenceSettings) {
                        this.authorizationCode = authorizationCode;
                        this.referenceSettings = referenceSettings;
                    }
                    return refJob;
                }());
                model.refJob = refJob;
            })(model = viewmodel.model || (viewmodel.model = {}));
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm013.a || (cmm013.a = {}));
})(cmm013 || (cmm013 = {}));
//# sourceMappingURL=cmm013.a.vm.js.map