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
                    self.label_003 = ko.observable(new Labels());
                    self.inp_002_code = ko.observable(null);
                    self.inp_002_enable = ko.observable(null);
                    self.inp_003_name = ko.observable(null);
                    self.inp_005_memo = ko.observable(null);
                    self.createdMode = ko.observable(true);
                    self.listbox = ko.observableArray([]);
                    self.selectedCode = ko.observable('');
                    self.isEnable = ko.observable(true);
                    self.itemHist = ko.observable(null);
                    self.index_selected = ko.observable('');
                    self.srtDateLast = ko.observable(null);
                    self.startDateLast = ko.observable(null);
                    self.startDateAddNew = ko.observable("");
                    self.checkRegister = ko.observable('0');
                    self.oldStartDate = ko.observable(null);
                    self.endDateUpdate = ko.observable(null);
                    self.startDateUpdateNew = ko.observable(null);
                    self.oldEndDate = ko.observable(null);
                    self.checkCoppyJtitle = ko.observable(true);
                    self.historyIdUpdate = ko.observable(null);
                    self.dataSource = ko.observableArray([]);
                    self.currentItem = ko.observable(null);
                    self.currentCode = ko.observable();
                    self.clickChange = ko.observable(false);
                    self.columns = ko.observableArray([
                        { headerText: 'コード', key: 'jobCode', width: 90 },
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
                    if (self.selectedId() == 0) {
                        $('#lst_003').addClass('disableClass');
                    }
                    self.enable = ko.observable(true);
                    self.notAlert = ko.observable(true);
                    self.dirty = new nts.uk.ui.DirtyChecker(self.dataSource);
                    $("#list-box").click(function (evt, ui) {
                        self.clickChange(true);
                    });
                    self.selectedCode.subscribe(function (codeChanged) {
                        self.itemHist(self.findHist(codeChanged));
                        self.oldStartDate(self.itemHist().startDate);
                        self.oldEndDate(self.itemHist().endDate);
                        self.srtDateLast(self.listbox()[0].startDate);
                        if (codeChanged == null) {
                            return;
                        }
                        if (!self.notAlert()) {
                            self.notAlert(true);
                            return;
                        }
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
                            a.service.findAllJobTitle(codeChanged).done(function (position_arr) {
                                self.dataSource(position_arr);
                                if (self.dataSource().length > 0) {
                                    var changedCode = self.clickChange() ? self.dataSource()[0].jobCode : self.inp_002_code() || self.dataSource()[0].jobCode;
                                    if (changedCode === self.currentCode()) {
                                        self.changedCode(changedCode);
                                    }
                                    else {
                                        self.currentCode(changedCode);
                                    }
                                }
                                self.clickChange(false);
                            }).fail(function (err) {
                                nts.uk.ui.dialog.alert(err.message);
                            });
                        }
                    });
                    self.currentCode.subscribe(function (codeChanged) {
                        if (codeChanged !== null && codeChanged !== undefined) {
                            self.changedCode(codeChanged);
                        }
                    });
                }
                ScreenModel.prototype.changedCode = function (value) {
                    var self = this;
                    self.currentItem(self.findPosition(value));
                    if (self.currentItem() != null) {
                        self.inp_002_code(self.currentItem().jobCode);
                        self.inp_003_name(self.currentItem().jobName);
                        self.inp_005_memo(self.currentItem().memo);
                        self.selectedId(self.currentItem().presenceCheckScopeSet);
                        self.inp_002_enable(false);
                        self.createdMode(false);
                        a.service.findByUseKt().done(function (res) {
                            if (res.use_Kt_Set === 1) {
                                a.service.getAllJobTitleAuth(self.currentItem().historyId, self.currentItem().jobCode).done(function (jTref) {
                                    if (jTref.length === 0) {
                                        $('.trLst003').hide();
                                    }
                                    else {
                                        $('.trLst003').show();
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
                            self.selectedId.subscribe(function (codeChanged) {
                                $('#lst_003').removeClass('disableClass');
                                if (codeChanged == 0) {
                                    $('#lst_003').show();
                                    $('#lst_003').addClass('disableClass');
                                }
                                else if (codeChanged == 1) {
                                    $('#lst_003').hide();
                                }
                                else {
                                    $('#lst_003').show();
                                }
                            });
                        });
                    }
                };
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.getHistory(dfd);
                    return dfd.promise();
                };
                ScreenModel.prototype.getHistory = function (dfd, selectedHistory) {
                    var self = this;
                    a.service.getAllHistory().done(function (history_arr) {
                        var listHistory = _.map(history_arr, function (item) {
                            return new model.ListHistoryDto(item.companyCode, item.startDate, item.endDate, item.historyId);
                        });
                        self.listbox(listHistory);
                        if (history_arr.length > 0) {
                            if (selectedHistory !== undefined && selectedHistory !== "1") {
                                var currentHist = self.findHist(selectedHistory);
                                self.selectedCode(currentHist.historyId);
                                self.endDateUpdate(currentHist.endDate);
                                self.startDateLast(currentHist.startDate);
                                self.historyIdUpdate(currentHist.historyId);
                            }
                            else {
                                var histStart = _.first(history_arr);
                                self.selectedCode(histStart.historyId);
                                self.endDateUpdate(histStart.endDate);
                                self.startDateLast(histStart.startDate);
                                self.historyIdUpdate(histStart.historyId);
                            }
                            var hisEnd = _.last(history_arr);
                            self.oldStartDate();
                            dfd.resolve(history_arr);
                        }
                        else {
                            self.dataSource([]);
                            self.initPosition();
                            self.srtDateLast(null);
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
                        var selectedHistory_1 = self.selectedCode();
                        a.service.registry(positionInfor).done(function () {
                            nts.uk.ui.windows.setShared('cmm013Insert', '', true);
                            nts.uk.ui.windows.setShared('cmm013Copy', '', true);
                            nts.uk.ui.windows.setShared('cmm013C_startDateNew', '', true);
                            self.selectedCode.valueHasMutated();
                            self.getHistory(dfd, selectedHistory_1);
                        }).fail(function (error) {
                            if (error.message === "ER005") {
                                alert("入力した*は既に存在しています。\r\n*を確認してください。");
                            }
                            if (error.message === "ER026") {
                                alert("更新対象のデータが存在しません。");
                            }
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
                        $('#inp_003').focus();
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
                ScreenModel.prototype.clearInit = function () {
                    var self = this;
                    self.inp_002_enable(true);
                    self.inp_002_code("");
                    self.inp_003_name("");
                    self.selectedId(1);
                    self.inp_005_memo("");
                    self.currentCode(null);
                    $("#inp_002").focus();
                };
                ScreenModel.prototype.initPosition = function () {
                    var self = this;
                    if (self.checkChangeData() == false || self.checkChangeData() === undefined) {
                        if (self.dirty.isDirty()) {
                            nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function () {
                                self.clearInit();
                            }).ifNo(function () {
                            });
                        }
                        else {
                            self.clearInit();
                        }
                    }
                };
                ScreenModel.prototype.checkChangeData = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    if (self.checkRegister() == '1') {
                        var retVal = confirm("Changed ?");
                        if (retVal == true && self.startDateAddNew() !== undefined && self.startDateAddNew() != '') {
                            self.registerPosition();
                            return true;
                        }
                        else {
                            var selectedHistory = self.selectedCode();
                            self.startDateAddNew('');
                            self.checkRegister('0');
                            self.getHistory(dfd, selectedHistory);
                            return false;
                        }
                    }
                };
                ScreenModel.prototype.openCDialog = function () {
                    var self = this;
                    if (self.checkChangeData() == false || self.checkChangeData() === undefined) {
                        var lstTmp = self.listbox();
                        nts.uk.ui.windows.setShared('CMM013_historyId', self.index_selected(), true);
                        nts.uk.ui.windows.setShared('CMM013_startDateLast', self.srtDateLast());
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
                                        a.service.findAllJobTitle(self.listbox()[1].historyId).done(function (position_arr) {
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
                    var dfd = $.Deferred();
                    if (self.checkChangeData() == false || self.checkChangeData() === undefined) {
                        var lstTmp = [];
                        self.startDateUpdateNew('');
                        nts.uk.ui.windows.setShared('cmm013HistoryId', self.selectedCode(), true);
                        nts.uk.ui.windows.setShared('cmm013StartDate', self.oldStartDate(), true);
                        nts.uk.ui.windows.setShared('cmm013EndDate', self.endDateUpdate(), true);
                        nts.uk.ui.windows.setShared('cmm013OldEndDate', self.oldEndDate(), true);
                        nts.uk.ui.windows.sub.modal('/view/cmm/013/d/index.xhtml', { title: '履歴の編集', })
                            .onClosed(function () {
                            if (!nts.uk.ui.windows.getShared('cancelDialog')) {
                                self.currentCode(self.selectedCode);
                                self.getHistory(dfd);
                            }
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
                        if (self.dataSource().length == 1) {
                            nts.uk.ui.dialog.confirm("選択している履歴の職位が1件のみのため、\r\n履歴の編集ボタンから履歴削除を行ってください。");
                        }
                        else {
                            nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function () {
                                a.service.deleteJobTitle(item).done(function (res) {
                                    self.getPositionList_afterDelete();
                                }).fail(function (res) {
                                    dfd.reject(res);
                                });
                            }).ifNo(function () {
                            });
                        }
                    }
                };
                ScreenModel.prototype.getPositionList_afterDelete = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.findAllJobTitle(self.selectedCode()).done(function (position_arr) {
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
                        self.startDate = moment.utc(startDate).format("YYYY/MM/DD");
                        self.endDate = moment.utc(endDate).format("YYYY/MM/DD");
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
                var DeleteobRefAuth = (function () {
                    function DeleteobRefAuth(companyCode, historyId, jobCode, authCode) {
                        this.companyCode = companyCode;
                        this.historyId = historyId;
                        this.jobCode = jobCode;
                        this.authCode = authCode;
                    }
                    return DeleteobRefAuth;
                }());
                model.DeleteobRefAuth = DeleteobRefAuth;
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
                var InputField = (function () {
                    function InputField(position, enable) {
                        this.inp_002_code = ko.observable(position.jobCode);
                        this.inp_003_name = ko.observable(position.jobName);
                        this.inp_004_notes = ko.observable(position.memo);
                        this.inp_002_enable = ko.observable(enable);
                    }
                    return InputField;
                }());
                model.InputField = InputField;
            })(model = viewmodel.model || (viewmodel.model = {}));
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm013.a || (cmm013.a = {}));
})(cmm013 || (cmm013 = {}));
//# sourceMappingURL=cmm013.a.vm.js.map