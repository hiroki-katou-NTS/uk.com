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
                    self.listbox = ko.observableArray([]);
                    self.selectedCode = ko.observable('');
                    self.isEnable = ko.observable(true);
                    self.itemHist = ko.observable(null);
                    self.itemName = ko.observable('');
                    self.index_selected = ko.observable('');
                    self.startDateLast = ko.observable(null);
                    self.historyLast = ko.observable(null);
                    self.length = ko.observable(0);
                    self.startDateAddNew = ko.observable("");
                    self.checkRegister = ko.observable('0');
                    self.startDateUpdate = ko.observable(null);
                    self.endDateUpdate = ko.observable(null);
                    self.histUpdate = ko.observable(null);
                    self.startDateUpdateNew = ko.observable(null);
                    self.startDatePre = ko.observable(null);
                    self.jobHistory = ko.observable(null);
                    self.startDateLastEnd = ko.observable(null);
                    self.jobHistNew = ko.observable(null);
                    self.checkCoppyJtitle = ko.observable('0');
                    self.checkAddJhist = ko.observable('0');
                    self.checkAddJtitle = ko.observable('0');
                    self.checkUpdate = ko.observable('0');
                    self.checkDelete = ko.observable('0');
                    self.dataSource = ko.observableArray([]);
                    self.currentItem = ko.observable(null);
                    self.itemName = ko.observable('');
                    self.currentCode = ko.observable();
                    self.currentCodeList = ko.observableArray([]);
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
                        if (self.itemHist().historyId == 'landau') {
                            return;
                        }
                        if (self.checkRegister() == 'landau') {
                            self.checkChangeData();
                        }
                        else {
                            if (self.itemHist() != null) {
                                self.index_selected(self.itemHist().historyId);
                                self.startDateUpdate(self.itemHist().startDate);
                                self.endDateUpdate(self.itemHist().endDate);
                                self.histUpdate(self.itemHist().historyId);
                                var dfd = $.Deferred();
                                if (self.checkCoppyJtitle() == 'landau' && codeChanged == self.listbox()[0].startDate) {
                                    self.itemHist(self.findHist(self.listbox()[1].startDate));
                                    self.index_selected(self.itemHist().historyId);
                                }
                                a.service.findAllPosition(self.index_selected())
                                    .done(function (position_arr) {
                                    self.dataSource(position_arr);
                                    if (self.dataSource().length > 0) {
                                        self.currentCode('');
                                        self.currentCode(self.dataSource()[0].jobCode);
                                        self.inp_002_code(self.dataSource()[0].jobCode);
                                        self.inp_003_name(self.dataSource()[0].jobName);
                                        self.inp_005_memo(self.dataSource()[0].memo);
                                        self.selectedId(self.dataSource()[0].presenceCheckScopeSet);
                                    }
                                    else {
                                        self.initPosition();
                                    }
                                }).fail(function (error) {
                                    alert(error.message);
                                });
                                self.inp_002_enable(false);
                            }
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
                            a.service.getAllJobTitleRef(self.itemHist().historyId, self.currentItem().jobCode)
                                .done(function (jTref) {
                                console.log(jTref);
                                _.map(jTref, function (item) {
                                    var tmp = new model.GetAuth(item.jobCode, item.authCode, item.authName, item.refSet);
                                    self.dataRef.push(tmp);
                                    return tmp;
                                });
                                console.log(self.dataRef());
                                self.dataRefNew(self.dataRef());
                            });
                        }
                        else
                            self.inp_002_enable(true);
                    });
                    self.dataRef.subscribe(function (codeChanged) {
                    });
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllHistory().done(function (history_arr) {
                        if (history_arr.length > 0) {
                            self.listbox(history_arr);
                            var histStart = _.first(history_arr);
                            var hisEnd = _.last(history_arr);
                            self.selectedCode(histStart.startDate);
                            self.startDateUpdate(histStart.startDate);
                            self.endDateUpdate(histStart.endDate);
                            self.histUpdate(histStart.historyId);
                            self.startDateLast(histStart.startDate);
                            self.historyLast(histStart.historyId);
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
                ScreenModel.prototype.addJobHistory = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var jTitle = new model.ListPositionDto(self.inp_002_code(), self.inp_003_name(), self.selectedId(), self.inp_005_memo());
                    if (self.listbox()[0].historyId == 'landau') {
                        if (self.checkCoppyJtitle() == 'landau') {
                            self.checkAddJtitle('landau');
                        }
                        else if (self.checkInput() == true) {
                            self.checkAddJtitle('lanhai');
                        }
                        if (self.listbox().length == 1) {
                            var jHist = new model.ListHistoryDto('', self.startDateAddNew(), '', self.listbox()[0].historyId);
                            self.checkAddJhist('landau');
                        }
                        else if (self.listbox().length >= 1) {
                            var jHist = new model.ListHistoryDto('', self.startDateAddNew(), '', self.listbox()[1].historyId);
                            self.checkAddJhist('lanhai');
                        }
                    }
                    else {
                        self.checkAddJhist('0');
                        if (self.checkInput() == true) {
                            var jHist = new model.ListHistoryDto('', self.startDateAddNew(), '', self.itemHist().historyId);
                            if (self.dataSource().length != 0) {
                                for (var i = 0; i < self.dataSource().length; i++) {
                                    if (self.inp_002_code() == self.dataSource()[i].jobCode) {
                                        self.checkAddJtitle('chettoi');
                                        break;
                                    }
                                    else {
                                        self.checkAddJtitle('baroi');
                                    }
                                }
                            }
                            else {
                                self.checkAddJtitle('baroi');
                            }
                        }
                    }
                    var addHandler = new model.AfterAdd(jHist, jTitle, self.checkAddJhist(), self.checkAddJtitle());
                    if (self.checkRegister() != '0' || ((self.checkAddJtitle() == 'baroi' || self.checkAddJtitle() == 'chettoi') && self.checkAddJhist() == '0')) {
                        a.service.addHist(addHandler).done(function () {
                            alert('OK');
                            nts.uk.ui.windows.setShared('startNew', '', true);
                            self.checkRegister('0');
                            self.getAllJobTitleNew();
                        }).fail(function (res) {
                            alert(res.message);
                            dfd.reject(res);
                        });
                    }
                };
                ScreenModel.prototype.getAllJobTitleNew = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.currentCode(self.inp_002_code());
                    a.service.findAllPosition(self.index_selected()).done(function (position_arr) {
                        self.dataSource(position_arr);
                        self.inp_002_enable(false);
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.findPosition = function (value) {
                    var self = this;
                    return _.find(self.dataSource(), function (obj) {
                        return obj.jobCode === value;
                    });
                };
                ScreenModel.prototype.findHist = function (value) {
                    var self = this;
                    return _.find(self.listbox(), function (obj) {
                        return obj.startDate === value;
                    });
                };
                ScreenModel.prototype.findJobRef = function (jRef) {
                    var self = this;
                    var itemModel = null;
                    return _.find(self.dataRef(), function (obj) {
                        return obj.authCode == jRef.authCode;
                    });
                };
                ScreenModel.prototype.getAllJobHistAfterHandler = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllHistory().done(function (history_arr) {
                        if (history_arr.length > 0) {
                            self.listbox = ko.observableArray([]);
                            self.listbox([]);
                            self.selectedCode = ko.observable('');
                            self.selectedCode('');
                            self.listbox(history_arr);
                            var histStart = _.first(history_arr);
                            var hisEnd = _.last(history_arr);
                            self.selectedCode(histStart.startDate);
                            self.startDateUpdate(histStart.startDate);
                            self.endDateUpdate(histStart.endDate);
                            self.histUpdate(histStart.historyId);
                            self.startDateLast(histStart.startDate);
                            self.historyLast(histStart.historyId);
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
                ScreenModel.prototype.checkInput = function () {
                    var self = this;
                    if (self.inp_002_code() == '' || self.inp_003_name() == '') {
                        alert("nhap day du thong tin");
                        return false;
                    }
                    else {
                        return true;
                    }
                };
                ScreenModel.prototype.checkChangeData = function () {
                    var self = this;
                    if (self.checkRegister() == 'landau') {
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
                ScreenModel.prototype.openCDialog = function () {
                    var self = this;
                    if (self.checkChangeData() == false || self.checkChangeData() === undefined) {
                        var lstTmp = self.listbox();
                        nts.uk.ui.windows.setShared('Id_13', self.index_selected(), true);
                        nts.uk.ui.windows.setShared('startLast', self.startDateLast(), true);
                        nts.uk.ui.windows.sub.modal('/view/cmm/013/c/index.xhtml', { title: '履歴の追加', })
                            .onClosed(function () {
                            self.startDateUpdateNew('');
                            self.startDateAddNew(nts.uk.ui.windows.getShared('startNew'));
                            self.checkCoppyJtitle(nts.uk.ui.windows.getShared('copy_c'));
                            if (self.startDateAddNew() != '' && self.startDateAddNew() !== undefined) {
                                var add = new model.ListHistoryDto('', self.startDateAddNew(), '9999-12-31', '1');
                                //lstTmp.unshift(add)
                                self.listbox.unshift(add);
                                self.selectedCode(self.startDateAddNew());
                                var startDate = new Date(self.startDateAddNew());
                                startDate.setDate(startDate.getDate() - 1);
                                var strStartDate = startDate.getFullYear() + '-' + (startDate.getMonth() + 1) + '-' + startDate.getDate();
                                var update = new model.ListHistoryDto('', self.startDateUpdate(), strStartDate, self.histUpdate());
                                if (self.listbox().length > 1) {
                                    self.listbox.splice(1, 1, update);
                                    self.listbox.valueHasMutated();
                                }
                                console.log(self.listbox());
                                self.checkRegister('landau');
                            }
                            else {
                                return;
                            }
                        });
                    }
                };
                ScreenModel.prototype.openDDialog = function () {
                    var self = this;
                    if (self.checkChangeData() == false || self.checkChangeData() === undefined) {
                        var lstTmp = [];
                        self.startDateUpdateNew('');
                        console.log(self.endDateUpdate());
                        var cDelete = 0;
                        if (self.startDateUpdate() == self.listbox()[0].startDate) {
                            cDelete = 1;
                        }
                        else {
                            cDelete = 2;
                        }
                        nts.uk.ui.windows.setShared('startDateLast', self.startDateLastEnd(), true);
                        nts.uk.ui.windows.setShared('delete', cDelete, true);
                        nts.uk.ui.windows.setShared('Id_13Update', self.histUpdate(), true);
                        nts.uk.ui.windows.setShared('startUpdate', self.startDateUpdate(), true);
                        nts.uk.ui.windows.setShared('endUpdate', self.endDateUpdate(), true);
                        nts.uk.ui.windows.sub.modal('/view/cmm/013/d/index.xhtml', { title: '履歴の編集', })
                            .onClosed(function () {
                            var checkUpdate = nts.uk.ui.windows.getShared('Finish');
                            if (checkUpdate == true) {
                                self.getAllJobHistAfterHandler();
                            }
                            else {
                                return;
                            }
                        });
                    }
                };
                ScreenModel.prototype.registerPosition = function () {
                    var self = this;
                    self.startDateAddNew(nts.uk.ui.windows.getShared('startNew'));
                    self.checkCoppyJtitle(nts.uk.ui.windows.getShared('copy_c'));
                    if ((self.startDateAddNew() != null && self.startDateAddNew() !== undefined && self.startDateAddNew() != '')
                        || (self.checkInput() == true && self.inp_002_enable() == true)) {
                        self.addJobHistory();
                    }
                    else {
                        return;
                    }
                };
                ScreenModel.prototype.deletePosition = function () {
                    var self = this;
                    if (self.checkRegister() == 'landau') {
                        self.checkChangeData();
                    }
                    else {
                        var dfd = $.Deferred();
                        var item = new model.DeleteJobTitle(self.itemHist().historyId, self.currentItem().jobCode);
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
                    a.service.findAllPosition(self.index_selected()).done(function (position_arr) {
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
                var historyDto = (function () {
                    function historyDto(startDate, endDate, historyId) {
                        this.startDate = startDate;
                        this.endDate = endDate;
                        this.historyId = historyId;
                    }
                    return historyDto;
                }());
                model.historyDto = historyDto;
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
                    function JobRef(historyId, jobCode, authCode, refSet) {
                        this.historyId = historyId;
                        this.jobCode = jobCode;
                        this.authCode = authCode;
                        this.refSet = refSet;
                    }
                    return JobRef;
                }());
                model.JobRef = JobRef;
                var GetAuth = (function () {
                    function GetAuth(jobCode, authCode, authName, refSet) {
                        this.jobCode = ko.observable(jobCode);
                        this.authCode = ko.observable(authCode);
                        this.authName = ko.observable(authName);
                        this.refSet = ko.observable(refSet);
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
                var AuthLevel = (function () {
                    function AuthLevel(authCode, authName) {
                        this.authCode = authCode;
                        this.authName = authName;
                    }
                    return AuthLevel;
                }());
                model.AuthLevel = AuthLevel;
                var AfterAdd = (function () {
                    //add new and add old(3)/update(5)
                    function AfterAdd(listHistDto, listPositionDto, checkAddPositionHist, checkAddPosition) {
                        this.listHist = listHistDto;
                        this.listPosition = listPositionDto;
                        this.checkAddHist = checkAddPositionHist;
                        this.checkAdd = checkAddPosition;
                    }
                    return AfterAdd;
                }());
                model.AfterAdd = AfterAdd;
                var DeleteHistoryCommand = (function () {
                    function DeleteHistoryCommand(historyId) {
                        this.historyId = historyId;
                    }
                    return DeleteHistoryCommand;
                }());
                model.DeleteHistoryCommand = DeleteHistoryCommand;
            })(model = viewmodel.model || (viewmodel.model = {}));
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = cmm013.a || (cmm013.a = {}));
})(cmm013 || (cmm013 = {}));
//# sourceMappingURL=cmm013.a.vm.js.map