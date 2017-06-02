var kmk011;
(function (kmk011) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.list = ko.observable();
                    self.enableUse = ko.observable();
                    self.enableSelect = ko.observable();
                    self.enableInput = ko.observable();
                    self.currentCode = ko.observable(1);
                    self.columns = ko.observableArray([
                        { headerText: nts.uk.resource.getText('KMK011_4'), key: 'divTimeId', width: 100 },
                        { headerText: nts.uk.resource.getText('KMK011_5'), key: 'divTimeName', width: 200 }
                    ]);
                    self.dataSource = ko.observableArray([]);
                    self.useSet = ko.observableArray([
                        { code: '1', name: nts.uk.resource.getText("Enum_UseAtr_Use") },
                        { code: '0', name: nts.uk.resource.getText("Enum_UseAtr_NotUse") },
                    ]);
                    self.divTimeName = ko.observable('');
                    self.timeItemName = ko.observable('');
                    self.checkErrSelect = ko.observable(true);
                    self.enable = ko.observable(true);
                    self.divTimeId = ko.observable(1);
                    self.itemDivTime = ko.observable(null);
                    self.selectUse = ko.observable(0);
                    self.alarmTime = ko.observable();
                    self.errTime = ko.observable();
                    self.selectSel = ko.observable();
                    self.selectInp = ko.observable();
                    self.checkErrInput = ko.observable(false);
                    self.checkErrSelect = ko.observable(false);
                    self.listDivItem = ko.observableArray([]);
                    self.lstItemSelected = ko.observableArray([]);
                    //subscribe currentCode
                    self.currentCode.subscribe(function (codeChanged) {
                        self.clearError();
                        if (codeChanged == 0) {
                            return;
                        }
                        self.selectUse(null);
                        self.itemDivTime(self.findDivTime(codeChanged));
                        self.alarmTime(self.convertTime(self.itemDivTime().alarmTime));
                        self.errTime(self.convertTime(self.itemDivTime().errTime));
                        self.selectUse(self.itemDivTime().divTimeUseSet);
                        self.selectSel(self.itemDivTime().selectSet.selectUseSet);
                        self.selectInp(self.itemDivTime().inputSet.selectUseSet);
                        self.divTimeId(self.itemDivTime().divTimeId);
                        self.divTimeName(self.itemDivTime().divTimeName);
                        a.service.getItemSelected(self.divTimeId()).done(function (lstItem) {
                            var listItemId = [];
                            for (var j = 0; j < lstItem.length; j++) {
                                listItemId[j] = lstItem[j].attendanceId;
                            }
                            a.service.getNameItemSelected(listItemId).done(function (lstName) {
                                self.lstItemSelected(lstName);
                                self.findTimeName(self.divTimeId());
                            });
                        });
                        if (self.itemDivTime().inputSet.cancelErrSelReason == 1) {
                            self.checkErrInput(true);
                        }
                        else {
                            self.checkErrInput(false);
                        }
                        if (self.itemDivTime().selectSet.cancelErrSelReason == 1) {
                            self.checkErrSelect(true);
                        }
                        else {
                            self.checkErrSelect(false);
                        }
                    });
                    //subscribe selectUse
                    self.selectUse.subscribe(function (codeChanged) {
                        if (codeChanged == 1) {
                            self.enableUse(true);
                            if (self.selectSel() == 1) {
                                self.enableSelect(true);
                            }
                            else {
                                self.enableSelect(false);
                            }
                            if (self.selectInp() == 1) {
                                self.enableInput(true);
                            }
                            else {
                                self.enableInput(false);
                            }
                        }
                        else {
                            self.enableUse(false);
                            self.enableSelect(false);
                            self.enableInput(false);
                        }
                    });
                    //subscribe selectSel
                    self.selectSel.subscribe(function (codeChanged) {
                        if (codeChanged == 1 && self.selectUse() == 1) {
                            self.enableSelect(true);
                        }
                        else {
                            self.enableSelect(false);
                        }
                    });
                    //subscribe selectInp
                    self.selectInp.subscribe(function (codeChanged) {
                        if (codeChanged == 1 && self.selectUse() == 1) {
                            self.enableInput(true);
                        }
                        else {
                            self.enableInput(false);
                        }
                    });
                }
                /**
                 * start page
                 * get all divergence time
                 * get all divergence name
                 */
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    a.service.getAllDivTime().done(function (lstDivTime) {
                        if (lstDivTime === undefined || lstDivTime.length == 0) {
                            self.dataSource();
                        }
                        else {
                            self.currentCode(0);
                            self.dataSource(lstDivTime);
                            var rdivTimeFirst = _.first(lstDivTime);
                            self.currentCode(rdivTimeFirst.divTimeId);
                        }
                        dfd.resolve();
                    });
                    return dfd.promise();
                };
                /**
                 * find Divergence Time is selected
                 */
                ScreenModel.prototype.findDivTime = function (value) {
                    var self = this;
                    var itemModel = null;
                    return _.find(self.dataSource(), function (obj) {
                        return obj.divTimeId == value;
                    });
                };
                ScreenModel.prototype.openBDialog = function () {
                    var self = this;
                    nts.uk.ui.windows.setShared('KMK011_divTimeId', self.divTimeId(), true);
                    nts.uk.ui.windows.sub.modal('/view/kmk/011/b/index.xhtml', { title: '選択肢の設定', });
                };
                ScreenModel.prototype.openDialog021 = function () {
                    var self = this;
                    a.service.getAllAttItem(1).done(function (lstAllItem) {
                        var listAllId = [];
                        for (var j = 0; j < lstAllItem.length; j++) {
                            listAllId[j] = lstAllItem[j].attendanceItemId;
                        }
                        var listIdSelect = [];
                        for (var i = 0; i < self.lstItemSelected().length; i++) {
                            listIdSelect[i] = self.lstItemSelected()[i].attendanceItemId;
                        }
                        nts.uk.ui.windows.setShared('AllAttendanceObj', listAllId, true);
                        nts.uk.ui.windows.setShared('SelectedAttendanceId', listIdSelect, true);
                        nts.uk.ui.windows.setShared('Multiple', true, true);
                        nts.uk.ui.windows.sub.modal('../../../kdl/021/a/index.xhtml', { title: '乖離時間の登録＞対象項目', }).onClosed(function () {
                            var list = nts.uk.ui.windows.getShared('selectedChildAttendace');
                            if (list == null || list === undefined)
                                return;
                            self.list(list);
                            var listUpdate = new Array();
                            for (var i = 0; i < list.length; i++) {
                                var itemUpdate = new model.DivergenceTimeItem(self.divTimeId(), list[i]);
                                listUpdate.push(itemUpdate);
                            }
                            a.service.getNameItemSelected(list).done(function (lstName) {
                                self.lstItemSelected(lstName);
                                self.findTimeName(self.divTimeId());
                            });
                        });
                    });
                };
                ScreenModel.prototype.Registration = function () {
                    var self = this;
                    $('.nts-input').trigger("validate");
                    _.defer(function () {
                        if (nts.uk.ui.errors.hasError() === false) {
                            var dfd = $.Deferred();
                            var select = new model.SelectSet(self.selectSel(), self.convert(self.checkErrSelect()));
                            var input = new model.SelectSet(self.selectInp(), self.convert(self.checkErrInput()));
                            var divTime = new model.DivergenceTime(self.divTimeId(), self.divTimeName(), self.selectUse(), self.convertInt(self.alarmTime()), self.convertInt(self.errTime()), select, input);
                            var listAdd = new Array();
                            if (self.list() != null) {
                                for (var k = 0; k < self.list().length; k++) {
                                    var add = new model.TimeItemSet(self.divTimeId(), self.list()[k]);
                                    listAdd.push(add);
                                }
                            }
                            var Object = new model.ObjectDivergence(divTime, listAdd);
                            a.service.updateDivTime(Object).done(function () {
                                self.getAllDivTimeNew();
                                nts.uk.ui.dialog.alert(nts.uk.resource.getMessage('Msg_15'));
                            }).fail(function (error) {
                                if (error.messageId == 'Msg_82') {
                                    $('#inpAlarmTime').ntsError('set', error);
                                }
                                else {
                                    $('#inpDialog').ntsError('set', error);
                                }
                            });
                            dfd.resolve();
                            return dfd.promise();
                        }
                    });
                };
                ScreenModel.prototype.convert = function (value) {
                    if (value == true) {
                        return 1;
                    }
                    else if (value == false) {
                        return 0;
                    }
                };
                ScreenModel.prototype.convertTime = function (value) {
                    var hours = Math.floor(value / 60);
                    var minutes = value % 60;
                    return (hours < 10 ? "0" + hours : hours) + ":" + (minutes < 10 ? "0" + minutes : minutes);
                };
                ScreenModel.prototype.convertInt = function (value) {
                    if (value == '' || value == null || value === undefined) {
                        return 0;
                    }
                    else {
                        var hours = value.substring(0, 2);
                        var minutes = value.substring(3, 5);
                        return (parseFloat(hours) * 60 + parseFloat(minutes));
                    }
                };
                ScreenModel.prototype.clearError = function () {
                    if ($('.nts-validate').ntsError("hasError") == true) {
                        $('.nts-validate').ntsError('clear');
                    }
                    if ($('.nts-editor').ntsError("hasError") == true) {
                        $('.nts-input').ntsError('clear');
                    }
                };
                //get all divergence time new
                ScreenModel.prototype.getAllDivTimeNew = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.dataSource();
                    a.service.getAllDivTime().done(function (lstDivTime) {
                        self.currentCode('');
                        self.dataSource(lstDivTime);
                        self.currentCode(self.divTimeId());
                        dfd.resolve();
                    }).fail(function (error) {
                        nts.uk.ui.dialog.alert(error.message);
                    });
                    dfd.resolve();
                    return dfd.promise();
                };
                //ghep ten hien thi 
                ScreenModel.prototype.findTimeName = function (divTimeId) {
                    var self = this;
                    self.timeItemName('');
                    var strName = '';
                    if (self.lstItemSelected().length < 1) {
                        self.timeItemName('');
                    }
                    else {
                        strName = self.lstItemSelected()[0].attendanceItemName;
                        if (self.lstItemSelected().length > 1) {
                            for (var j = 1; j < self.lstItemSelected().length; j++) {
                                strName = strName + ' + ' + self.lstItemSelected()[j].attendanceItemName;
                            }
                        }
                        self.timeItemName(strName);
                        strName = '';
                    }
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var model;
            (function (model) {
                var DivergenceTime = (function () {
                    function DivergenceTime(divTimeId, divTimeName, divTimeUseSet, alarmTime, errTime, selectSet, inputSet) {
                        var self = this;
                        self.divTimeId = divTimeId;
                        self.divTimeName = divTimeName;
                        self.divTimeUseSet = divTimeUseSet;
                        self.alarmTime = alarmTime;
                        self.errTime = errTime;
                        self.selectSet = selectSet;
                        self.inputSet = inputSet;
                    }
                    return DivergenceTime;
                }());
                model.DivergenceTime = DivergenceTime;
                var SelectSet = (function () {
                    function SelectSet(selectUseSet, cancelErrSelReason) {
                        this.selectUseSet = selectUseSet;
                        this.cancelErrSelReason = cancelErrSelReason;
                    }
                    return SelectSet;
                }());
                model.SelectSet = SelectSet;
                var DivergenceTimeItem = (function () {
                    function DivergenceTimeItem(divTimeId, attendanceId) {
                        this.divTimeId = divTimeId;
                        this.attendanceId = attendanceId;
                    }
                    return DivergenceTimeItem;
                }());
                model.DivergenceTimeItem = DivergenceTimeItem;
                var ItemSelected = (function () {
                    function ItemSelected(id, name) {
                        this.id = id;
                        this.name = name;
                    }
                    return ItemSelected;
                }());
                model.ItemSelected = ItemSelected;
                var AttendanceType = (function () {
                    function AttendanceType() {
                    }
                    return AttendanceType;
                }());
                model.AttendanceType = AttendanceType;
                var DivergenceItem = (function () {
                    function DivergenceItem() {
                    }
                    return DivergenceItem;
                }());
                model.DivergenceItem = DivergenceItem;
                var TimeItemSet = (function () {
                    function TimeItemSet(divTimeId, attendanceId) {
                        this.divTimeId = divTimeId;
                        this.attendanceId = attendanceId;
                    }
                    return TimeItemSet;
                }());
                model.TimeItemSet = TimeItemSet;
                var ObjectDivergence = (function () {
                    function ObjectDivergence(divTime, item) {
                        this.divTime = divTime;
                        this.timeItem = item;
                    }
                    return ObjectDivergence;
                }());
                model.ObjectDivergence = ObjectDivergence;
            })(model = viewmodel.model || (viewmodel.model = {}));
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = kmk011.a || (kmk011.a = {}));
})(kmk011 || (kmk011 = {}));
//# sourceMappingURL=kmk011.a.vm.js.map