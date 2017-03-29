var cmm013;
(function (cmm013) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.label_003 = ko.observable(new Labels());
                    self.label_005 = ko.observable(new Labels());
                    self.radiobox = ko.observable(new RadioBox());
                    self.adddata = ko.observable(null);
                    self.updatedata = ko.observable(null);
                    self.inp_002_code = ko.observable(null);
                    self.inp_002_enable = ko.observable(false);
                    self.inp_003_name = ko.observable(null);
                    self.inp_004 = ko.observable(new TextEditor_3());
                    self.inp_005_memo = ko.observable(null);
                    self.swb_001 = ko.observable(new SwitchButton);
                    self.swb_002 = ko.observable(new SwitchButton);
                    self.swb_003 = ko.observable(new SwitchButton);
                    self.swb_004 = ko.observable(new SwitchButton);
                    self.length = ko.observable(null);
                    self.historyIdLast = ko.observable(null);
                    self.startDateLast = ko.observable(null);
                    self.listbox = ko.observableArray([]);
                    self.selectedCode = ko.observable(null);
                    self.checkDelete = ko.observable(null);
                    self.itemHist = ko.observable(null);
                    self.itemName = ko.observable('');
                    self.index_selected = ko.observable('');
                    self.checkUpOrDel = ko.observable(null);
                    self.startDateUpdate = ko.observable(null);
                    self.endDateUpdate = ko.observable(null);
                    self.checkUp = ko.observable(null);
                    self.checkDel = ko.observable(null);
                    self.startDateLast = ko.observable(null);
                    self.historyIdLast = ko.observable(null);
                    self.length = ko.observable(0);
                    self.startDateAddNew = ko.observable(null);
                    self.check = ko.observable('0');
                    self.startDateUpdate = ko.observable(null);
                    self.historyIdUpdate = ko.observable(null);
                    self.startDateUpdateNew = ko.observable(null);
                    self.jobHistory = ko.observable(null);
                    self.srtDateLast = ko.observable(null);
                    self.dataSource = ko.observableArray([]);
                    self.currentItem = ko.observable(null);
                    self.currentItem2 = ko.observable(null);
                    self.isEnable = ko.observable(true);
                    self.itemName = ko.observable('');
                    self.currentCode = ko.observable(null);
                    self.start_Date = ko.observable(null);
                    self.end_Date = ko.observable(null);
                    self.currentCodeList = ko.observableArray([]);
                    self.columns = ko.observableArray([
                        { headerText: 'コード', key: 'jobCode', width: 80 },
                        { headerText: '名称', key: 'jobName', width: 100 }
                    ]);
                    self.itemList = ko.observableArray([
                        new BoxModel(0, '全員参照可能'),
                        new BoxModel(1, '全員参照不可'),
                        new BoxModel(2, 'ロール毎に設定')
                    ]);
                    self.selectedId = ko.observable(0);
                    self.enable = ko.observable(true);
                    self.createdMode = ko.observable(true);
                    self.currentCode.subscribe((function (codeChanged) {
                        self.currentItem(self.findPosition(codeChanged));
                        if (self.currentItem() != null) {
                            self.createdMode(false);
                            self.inp_002_code(self.currentItem().jobCode);
                            self.inp_003_name(self.currentItem().jobName);
                            self.inp_005_memo(self.currentItem().memo);
                        }
                        else {
                            self.createdMode(true);
                        }
                    }));
                }
                ScreenModel.prototype.checkPage = function () {
                    var self = this;
                    if (self.inp_002_code() == '' || self.inp_003_name() == '') {
                        alert("が入力されていません。");
                        return false;
                    }
                    else {
                        return true;
                    }
                };
                ScreenModel.prototype.checkData = function () {
                    var self = this;
                    if (self.check() == '1') {
                        var retVal = confirm("Changed.Register OK?");
                        if (retVal == true) {
                            self.addHist();
                            return true;
                        }
                        else {
                            self.startDateAddNew(null);
                            self.check('0');
                            return false;
                        }
                    }
                };
                ScreenModel.prototype.getAllJobHistLast = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllHistory().done(function (lstHistory) {
                        if (lstHistory === undefined || lstHistory.length === 0) {
                            self.openCDialog();
                            dfd.resolve();
                        }
                        else {
                            self.listbox = ko.observableArray([]);
                            self.listbox([]);
                            self.selectedCode = ko.observable('');
                            self.selectedCode('');
                            self.listbox(lstHistory);
                            var historyFirst = _.first(lstHistory);
                            var historyLast = _.last(lstHistory);
                            self.selectedCode(historyFirst.startDate);
                            self.startDateUpdate(historyFirst.startDateString);
                            self.endDateUpdate(historyFirst.endDateString);
                            self.historyIdUpdate(historyFirst.historyId);
                            self.startDateLast(historyFirst.startDateString);
                            self.historyIdLast(historyFirst.historyId);
                            self.startDateLast(historyLast.startDateString);
                            dfd.resolve(lstHistory);
                        }
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.initRegisterPosition = function () {
                    var self = this;
                    self.inp_002_enable(true);
                    self.createdMode(true);
                    self.inp_002_code("");
                    self.inp_003_name("");
                    self.inp_005_memo("");
                    self.currentCode(null);
                    $("#inp_002").focus();
                };
                ScreenModel.prototype.findHist = function (value) {
                    var self = this;
                    var itemModel = null;
                    _.find(self.listbox(), function (obj) {
                        if (obj.historyId == value) {
                            itemModel = obj;
                        }
                    });
                    return itemModel;
                };
                ScreenModel.prototype.findPosition = function (value) {
                    var self = this;
                    var itemModel = null;
                    _.find(self.dataSource(), function (obj) {
                        if (obj.jobCode == value) {
                            itemModel = obj;
                        }
                    });
                    return itemModel;
                };
                ScreenModel.prototype.addPosition = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    if (self.checkPage()) {
                        if (self.dataSource().length === 0) {
                            var position = new viewmodel.model.ListPositionDto(self.inp_002_code(), self.inp_003_name(), self.inp_005_memo());
                            position.historyId = self.selectedCode();
                            a.service.addPosition(position).done(function () {
                                self.getPosition_first();
                            }).fail(function (res) {
                                dfd.reject(res);
                            });
                        }
                        if (!self.createdMode()) {
                            var currentItem_1 = self.currentItem();
                            currentItem_1.jobCode = self.inp_002_code();
                            currentItem_1.jobName = self.inp_003_name();
                            currentItem_1.memo = self.inp_005_memo();
                            a.service.updatePosition(currentItem_1).done(function () {
                                self.updatedata(currentItem_1);
                                self.getPositionList_afterUpdate();
                            }).fail(function (res) {
                                alert("更新対象のデータが存在しません。");
                                dfd.reject(res);
                            });
                        }
                        else {
                            var position_new_1 = new viewmodel.model.ListPositionDto(self.inp_002_code(), self.inp_003_name(), self.inp_005_memo());
                            position_new_1.historyId = self.selectedCode();
                            a.service.addPosition(position_new_1).done(function () {
                                self.adddata(position_new_1);
                                self.currentCode(self.adddata().jobCode);
                                self.getPositionList_afterAdd();
                            }).fail(function (res) {
                                alert("入力した は既に存在しています。");
                                dfd.reject(res);
                            });
                        }
                    }
                };
                ScreenModel.prototype.deletePosition = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var item = new model.DeletePositionCommand(self.currentItem().jobCode, self.currentItem().historyId);
                    self.index_of_itemDelete = self.dataSource().indexOf(self.currentItem());
                    a.service.deletePosition(item).done(function (res) {
                        self.getPositionList_aftefDelete();
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                };
                ScreenModel.prototype.getPositionList_aftefDelete = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.findAllPosition(self.index_selected()).done(function (position_arr) {
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
                            self.initRegisterPosition();
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.getPositionList = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.findAllPosition(self.index_selected()).done(function (position_arr) {
                        self.dataSource(position_arr);
                        self.inp_002_code(self.dataSource()[0].jobCode);
                        self.inp_003_name(self.dataSource()[0].jobName);
                        self.inp_005_memo(self.dataSource()[0].memo);
                        if (self.dataSource().length > 1) {
                            self.currentCode(self.dataSource()[0].jobCode);
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.selectedCode.subscribe((function (codeChanged) {
                        self.itemHist(self.findHist(codeChanged));
                        if (self.itemHist() != null) {
                            self.index_selected(self.itemHist().historyId);
                            self.startDateUpdate(self.itemHist().startDateString);
                            self.endDateUpdate(self.itemHist().endDateString);
                            self.historyIdUpdate(self.itemHist().historyId);
                            var dfd = $.Deferred();
                            a.service.findAllPosition(self.index_selected()).done(function (position_arr) {
                                self.dataSource(position_arr);
                                if (self.dataSource().length > 0) {
                                    self.currentCode(self.dataSource()[0].jobCode);
                                    self.inp_002_code(self.dataSource()[0].jobCode);
                                    self.inp_003_name(self.dataSource()[0].jobName);
                                    self.inp_005_memo(self.dataSource()[0].memo);
                                }
                                dfd.resolve();
                            }).fail(function (error) {
                                alert(error.message);
                            });
                            dfd.resolve();
                            return dfd.promise();
                        }
                    }));
                    a.service.getAllHistory().done(function (history_arr) {
                        if (history_arr === undefined || history_arr.length === 0) {
                            self.openCDialog();
                        }
                        else {
                            self.listbox(_.map(history_arr, function (history) {
                                return new model.ListHistoryDto(history.companyCode, history.startDate, history.endDate, history.historyId);
                            }));
                            if (history_arr.length > 0) {
                                self.selectedCode(history_arr[0].historyId);
                            }
                            if (self.dataSource().length > 0) {
                                self.selectedCode = ko.observable(self.listbox()[0].startDateString);
                            }
                            var historyFirst = _.first(history_arr);
                            var historyLast = _.last(history_arr);
                            self.checkDelete(historyLast.startDate);
                            self.selectedCode(historyFirst.startDate);
                            self.startDateUpdate(historyFirst.startDate);
                            self.endDateUpdate(historyFirst.endDate);
                            self.historyIdUpdate(historyFirst.historyId);
                            self.startDateLast(historyFirst.startDate);
                            self.historyIdLast(historyFirst.historyId);
                            self.srtDateLast(historyLast.startDate);
                            dfd.resolve(history_arr);
                        }
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.getPosition_first = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.findAllPosition(self.index_selected()).done(function (position_arr) {
                        self.dataSource(position_arr);
                        self.currentCode(self.dataSource()[0].historyId);
                        var i = self.dataSource().length;
                        if (i > 0) {
                            self.inp_002_enable(false);
                            self.inp_002_code(self.dataSource()[0].jobCode);
                            self.inp_003_name(self.dataSource()[0].jobName);
                            self.inp_005_memo(self.dataSource()[0].memo);
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.getPositionList_afterUpdate = function () {
                    var self = this;
                    a.service.findAllPosition(self.index_selected()).done(function (position_arr) {
                        self.dataSource([]);
                        self.dataSource(position_arr);
                        if (position_arr.length) {
                            self.currentCode(self.updatedata().jobCode);
                        }
                    }).fail(function (error) {
                        alert(error.message);
                    });
                };
                ScreenModel.prototype.getPositionList_afterAdd = function () {
                    var self = this;
                    a.service.findAllPosition(self.index_selected()).done(function (position_arr) {
                        self.dataSource(position_arr);
                        self.inp_002_code(self.adddata().jobCode);
                        self.inp_002_enable(false);
                        self.inp_003_name(self.adddata().jobName);
                        self.inp_005_memo(self.adddata().memo);
                        if (self.dataSource().length) {
                            self.currentCode(self.adddata().jobCode);
                        }
                    }).fail(function (error) {
                        alert(error.message);
                    });
                };
                ScreenModel.prototype.addHist = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.startDateAddNew(nts.uk.ui.windows.getShared('startNew'));
                    var i = 0;
                    var currentHist = null;
                    for (i = 0; i < 1; i++) {
                        currentHist = new model.ListHistoryDto(self.listbox()[i].companyCode, self.listbox()[i].startDateString, '9999/12/31', self.listbox()[i].historyId);
                        break;
                    }
                    if (!currentHist) {
                        return;
                    }
                    if (!currentHist.historyId) {
                        a.service.addHist(currentHist).done(function () {
                            nts.uk.ui.windows.setShared('startNew', '', true);
                            self.startPage();
                        }).fail(function (res) {
                            alert('fail');
                            dfd.reject(res);
                        });
                    }
                    else {
                        a.service.updateHist(currentHist).done(function () {
                            nts.uk.ui.windows.setShared('startNew', '', true);
                            self.startPage();
                        }).fail(function (res) {
                            alert('fail');
                            dfd.reject(res);
                        });
                    }
                };
                ScreenModel.prototype.openCDialog = function () {
                    var self = this;
                    var histLs = [];
                    var dfd = $.Deferred();
                    nts.uk.ui.windows.setShared('Id_13', self.index_selected());
                    nts.uk.ui.windows.setShared('startUpdate', self.startDateUpdate());
                    nts.uk.ui.windows.sub.modal('/view/cmm/013/c/index.xhtml', { title: '画面ID：C', }).onClosed(function () {
                        var isCopy = nts.uk.ui.windows.getShared('copy_c');
                        self.selectedCode('');
                        if (isCopy == 2) {
                            self.startDateAddNew(nts.uk.ui.windows.getShared('startNew'));
                            var add = new model.ListHistoryDto('', self.startDateAddNew(), '9999/12/31', '');
                            self.listbox.unshift(add);
                        }
                        else if (isCopy == 1) {
                            self.startDateAddNew(nts.uk.ui.windows.getShared('startNew'));
                            var update = new model.ListHistoryDto('', self.startDateAddNew(), '9999/12/31', '');
                            self.listbox.unshift(update);
                        }
                    });
                };
                ScreenModel.prototype.getHistList_aftefDelete = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllHistory().done(function (hist_arr) {
                        self.listbox(hist_arr);
                        if (self.listbox().length > 0) {
                            if (self.index_of_itemDelete === self.listbox().length) {
                                self.start_Date(self.listbox()[self.index_of_itemDelete - 1].startDate);
                                self.end_Date(self.listbox()[self.index_of_itemDelete - 1].endDate);
                            }
                            else {
                                self.start_Date(self.listbox()[self.index_of_itemDelete].startDate);
                                self.end_Date(self.listbox()[self.index_of_itemDelete].endDate);
                            }
                        }
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.deleteHist = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    var item2 = new model.DeleteHistoryCommand(self.currentItem().historyId);
                    self.index_of_itemDelete = self.dataSource().indexOf(self.currentItem());
                    a.service.deleteHist(item2).done(function (res) {
                        self.getHistList_aftefDelete();
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                };
                ScreenModel.prototype.openDDialog = function () {
                    var self = this;
                    if (self.checkData() == false || self.checkData() === undefined) {
                        var lstTmp = [];
                        self.startDateUpdateNew('');
                        console.log(self.endDateUpdate());
                        var cDelete = 0;
                        if (self.startDateUpdate() == self.listbox()[0].startDateString) {
                            cDelete = 1;
                        }
                        else {
                            cDelete = 2;
                        }
                        nts.uk.ui.windows.setShared('startDateLast', self.startDateLast(), true);
                        nts.uk.ui.windows.setShared('check_d', cDelete, true);
                        nts.uk.ui.windows.setShared('historyIdUpdate', self.historyIdUpdate(), true);
                        nts.uk.ui.windows.setShared('startUpdate', self.startDateUpdate(), true);
                        nts.uk.ui.windows.setShared('endUpdate', self.endDateUpdate(), true);
                        nts.uk.ui.windows.sub.modal('/view/cmm/013/d/index.xhtml', { title: '画面ID：D', })
                        
                            .onClosed(function () {
                            var checkUpdate = nts.uk.ui.windows.getShared('updateFinish');
                            
                            if (checkUpdate == true) {
                                self.getAllJobHistLast();
                            }
                            else {
                                return;
                            }
                        });
                    }
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
            var RadioBox = (function () {
                function RadioBox() {
                    var self = this;
                    self.itemList = ko.observableArray([
                        new BoxModel(1, '全員参照可能'),
                        new BoxModel(2, '全員参照不可'),
                        new BoxModel(3, 'ロール毎に設定')
                    ]);
                    self.selectedId = ko.observable(1);
                    self.enable = ko.observable(true);
                }
                return RadioBox;
            }());
            var BoxModel = (function () {
                function BoxModel(id, name) {
                    var self = this;
                    self.id = id;
                    self.name = name;
                }
                return BoxModel;
            }());
            viewmodel.BoxModel = BoxModel;
            var TextEditor_3 = (function () {
                function TextEditor_3() {
                    var self = this;
                    self.texteditor = {
                        value: ko.observable(''),
                        constraint: 'ResidenceCode',
                        option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                            textmode: "text",
                            placeholder: "",
                            width: "10px",
                            textalign: "center"
                        })),
                        required: ko.observable(true),
                        enable: ko.observable(false),
                        readonly: ko.observable(false)
                    };
                }
                return TextEditor_3;
            }());
            viewmodel.TextEditor_3 = TextEditor_3;
            var SwitchButton = (function () {
                function SwitchButton() {
                    var self = this;
                    self.roundingRules = ko.observableArray([
                        { code: '1', name: '可能' },
                        { code: '2', name: '不可' },
                    ]);
                    self.selectedRuleCode = ko.observable(1);
                }
                return SwitchButton;
            }());
            viewmodel.SwitchButton = SwitchButton;
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
                        self.startDate = new Date(startDate);
                        self.startDateString = startDate;
                        self.endDate = new Date(endDate);
                        self.endDateString = endDate;
                        self.historyId = historyId;
                    }
                    return ListHistoryDto;
                }());
                model.ListHistoryDto = ListHistoryDto;
                var ListPositionDto = (function () {
                    function ListPositionDto(jobCode, jobName, memo) {
                        var self = this;
                        self.jobCode = jobCode;
                        self.jobName = jobName;
                        self.memo = memo;
                    }
                    return ListPositionDto;
                }());
                model.ListPositionDto = ListPositionDto;
                var DeletePositionCommand = (function () {
                    function DeletePositionCommand(jobCode, historyId) {
                        this.jobCode = jobCode;
                        this.historyId = historyId;
                    }
                    return DeletePositionCommand;
                }());
                model.DeletePositionCommand = DeletePositionCommand;
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