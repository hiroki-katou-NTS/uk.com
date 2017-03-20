var qmm034;
(function (qmm034) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.isCheckedDirty = false;
                    var self = this;
                    self.init();
                    self.currentCode.subscribe(function (oldcode) {
                        //check xem user co thay doi value cua cac field can check dirty
                        //neu isCheckedDirty = true thi stop action, neu = false thi action
                        if (!nts.uk.text.isNullOrEmpty(oldcode) && !self.isCheckedDirty) {
                            if (self.dirty1.isDirty() || self.dirty2.isDirty() || self.dirty3.isDirty()) {
                                if (confirm("Data is changed.Do you want to changing select row?") === false) {
                                    self.isCheckedDirty = true;
                                    return;
                                }
                                self.isCheckedDirty = false;
                            }
                        }
                    }, self, "beforeChange");
                    self.currentCode.subscribe(function (codeChanged) {
                        //neu isCheckedDirty = true thi stop action, neu = false thi action
                        if (self.isCheckedDirty) {
                            self.currentCode(self.currentEra().code);
                            self.isCheckedDirty = false;
                            return;
                        }
                        if (!nts.uk.text.isNullOrEmpty(codeChanged)) {
                            self.currentEra(self.getEra(codeChanged));
                            self.date(new Date(self.currentEra().startDate.toString()));
                            self.inputCode(self.currentEra().code);
                            self.inputName(self.currentEra().name);
                            self.currentCode(self.currentEra().code);
                            self.isDeleteEnable(true);
                            self.isEnableCode(true);
                            self.isUpdate(true);
                            qmm034.a.service.getFixAttribute(self.currentEra().eraHist).done(function (data) {
                                if (data === 0) {
                                    self.isEnableCode(false);
                                }
                            }).fail(function (error) {
                                alert(error.message);
                            });
                            self.dirty1 = new nts.uk.ui.DirtyChecker(self.inputCode);
                            self.dirty2 = new nts.uk.ui.DirtyChecker(self.inputName);
                            self.dirty3 = new nts.uk.ui.DirtyChecker(self.date);
                        }
                    });
                    //convert to Japan Emprise year
                    self.dateTime = ko.observable(nts.uk.time.yearInJapanEmpire(self.date()).toString());
                }
                ScreenModel.prototype.init = function () {
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.columns = ko.observableArray([
                        { headerText: '元号', key: 'code', width: 50 },
                        { headerText: '記号', key: 'name', width: 50 },
                        { headerText: '開始年月日', key: 'startDate', width: 80 },
                    ]);
                    self.currentEra = ko.observable((new EraModel('大明', 'S', new Date("1926/12/25"), 1, '95F5047A-5065-4306-A6B7-184AA676A1DE', new Date("1929/12/25"))));
                    self.currentCode = ko.observable(null);
                    self.date = ko.observable(new Date());
                    self.dateTime = ko.observable('');
                    self.inputCode = ko.observable('');
                    self.inputName = ko.observable('');
                    self.isDeleteEnable = ko.observable(false);
                    self.isEnableCode = ko.observable(false);
                    self.isUpdate = ko.observable(false);
                };
                ScreenModel.prototype.insertData = function () {
                    var self = this;
                    var eraName;
                    eraName = $('#A_INP_001').val();
                    var eraMark;
                    eraMark = $('#A_INP_002').val();
                    var startDate = self.date();
                    var endDate;
                    var eraHist = self.currentEra().eraHist;
                    var fixAttribute;
                    var dfd = $.Deferred();
                    var node;
                    node = new qmm034.a.service.model.EraDto(eraName, eraMark, startDate, endDate, fixAttribute, eraHist);
                    if (node.eraName == "") {
                        $("#A_INP_001").ntsError("set", "the era name must require");
                        return false;
                    }
                    if (node.eraMark == "") {
                        $("#A_INP_002").ntsError("set", "the era mark must require");
                        return false;
                    }
                    qmm034.a.service.addData(self.isUpdate(), node).done(function (result) {
                        self.reload().done(function () {
                            self.currentCode(eraName);
                            dfd.resolve();
                        });
                    }).fail(function (res) {
                        //alert(res.message);
                        $("#A_INP_003").ntsError("set", res.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.alertDelete = function () {
                    var self = this;
                    if (confirm("do you wanna delete") === true) {
                        self.deleteData();
                    }
                    else {
                        alert("you didnt delete!");
                    }
                };
                ScreenModel.prototype.reload = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    $.when(qmm034.a.service.getAllEras()).done(function (data) {
                        self.buildGridDataSource(data);
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.deleteData = function () {
                    var self = this;
                    var eraName;
                    eraName = $('#A_INP_001').val();
                    var eraMark;
                    eraMark = $('#A_INP_002').val();
                    var eraHist = self.currentEra().eraHist;
                    var startDate = self.date();
                    var dfd = $.Deferred();
                    var node;
                    node = new qmm034.a.service.model.EraDtoDelete(eraHist);
                    qmm034.a.service.deleteData(node).done(function (result) {
                        var rowIndex = _.findIndex(self.items(), function (item) {
                            return item.code == self.currentEra().code;
                        });
                        self.items.remove(function (item) {
                            return item.code == self.currentEra().code;
                        });
                        self.items.valueHasMutated();
                        if (self.items().length === 0) {
                            self.isUpdate(false);
                            self.refreshLayout();
                        }
                        else if (self.items().length === rowIndex) {
                            self.currentEra(self.items()[rowIndex - 1]);
                            self.currentCode(self.items()[rowIndex - 1].code);
                        }
                        else {
                            self.currentEra(self.items()[rowIndex]);
                            self.currentCode(self.items()[rowIndex].code);
                        }
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.getEra = function (codeNew) {
                    var self = this;
                    var era = _.find(self.items(), function (item) {
                        return item.code === codeNew;
                    });
                    return _.cloneDeep(era);
                };
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    // Page load dfd.
                    var dfd = $.Deferred();
                    // Resolve start page dfd after load all data.
                    $.when(qmm034.a.service.getAllEras()).done(function (data) {
                        self.buildGridDataSource(data);
                        self.currentEra = ko.observable((new EraModel('大明', 'S', new Date("1926/12/25"), 1, '95F5047A-5065-4306-A6B7-184AA676A1DE', new Date("1929/12/25"))));
                        if (self.items().length > 0) {
                            self.currentEra(_.cloneDeep(_.first(self.items())));
                            self.currentCode(self.currentEra().code);
                            self.inputCode(self.currentEra().code);
                            self.inputName(self.currentEra().name);
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        $("#A_INP_001").ntsError("set", res.message);
                        //alert(res.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.refreshLayout = function () {
                    var self = this;
                    if (!self.dirty1.isDirty() && !self.dirty2.isDirty() && !self.dirty3.isDirty()) {
                        self.currentEra(new EraModel('', '', new Date(), 1, '95F5047A-5065-4306-A6B7-184AA676A1DE', new Date("1929/12/25")));
                        self.currentCode(null);
                        self.isUpdate(false);
                        self.inputCode(null);
                        self.inputName(null);
                        self.date(new Date());
                        self.isDeleteEnable(false);
                        self.isEnableCode(true);
                        return;
                    }
                    if (confirm("Data is changed.Do you want to refresh?") === true) {
                        self.currentEra(new EraModel('', '', new Date(), 1, '95F5047A-5065-4306-A6B7-184AA676A1DE', new Date("1929/12/25")));
                        self.currentCode(null);
                        self.isUpdate(false);
                        self.inputCode(null);
                        self.inputName(null);
                        self.date(new Date());
                        self.isDeleteEnable(false);
                        self.isEnableCode(true);
                    }
                };
                ScreenModel.prototype.buildGridDataSource = function (items) {
                    var self = this;
                    self.items([]);
                    _.forEach(items, function (obj) {
                        self.items.push(new EraModel(obj.eraName, obj.eraMark, obj.startDate, obj.fixAttribute, obj.eraHist, obj.endDate));
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var EraModel = (function () {
                function EraModel(code, name, startDate, fixAttribute, eraHist, endDate) {
                    this.code = code;
                    this.name = name;
                    this.startDate = startDate;
                    this.fixAttribute = fixAttribute;
                    this.eraHist = eraHist;
                    this.endDate = endDate;
                }
                return EraModel;
            }());
            viewmodel.EraModel = EraModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm034.a || (qmm034.a = {}));
})(qmm034 || (qmm034 = {}));
