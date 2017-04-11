var qmm034;
(function (qmm034) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    this.countStartDateChange = 1; //Biến này để tránh việc chạy hàm startDate.subscribe 2 lần
                    this.previousCurrentCode = null; //lưu giá trị của currentCode trước khi nó bị thay đổi
                    var self = this;
                    self.init();
                    self.date = ko.observable("");
                    self.startDate = ko.observable(moment().format("YYYY/MM/DD"));
                    self.startDate.subscribe(function (dateChange) {
                        if (self.countStartDateChange === 1) {
                            // event datePicker onchange
                            if ($('#A_INP_003').ntsError("hasError")) {
                                $("#A_INP_003").ntsError('clear');
                            }
                        }
                        else {
                            self.countStartDateChange = 1;
                        }
                        //self.currentEra().startDate(dateChange);
                        self.dateTime(nts.uk.time.yearInJapanEmpire(dateChange).toString());
                    });
                    self.currentCode.subscribe(function (codeChanged) {
                        if (!nts.uk.text.isNullOrEmpty(codeChanged) && self.currentCode() !== self.previousCurrentCode) {
                            if (self.dirtyObject.isDirty()) {
                                nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\nよろしいですか。?").ifYes(function () {
                                    self.processWhenCurrentCodeChange(codeChanged);
                                }).ifCancel(function () {
                                    self.currentCode(self.previousCurrentCode);
                                });
                            }
                            else {
                                self.processWhenCurrentCodeChange(codeChanged);
                            }
                        }
                    });
                    //convert to Japan Emprise year
                    self.dateTime = ko.observable(nts.uk.time.yearInJapanEmpire(self.currentEra().startDate()).toString());
                }
                ScreenModel.prototype.processWhenCurrentCodeChange = function (codeChanged) {
                    var self = this;
                    self.countStartDateChange += 1;
                    self.currentEra(self.getEra(codeChanged));
                    self.date(self.currentEra().startDate().toString());
                    self.startDate(self.currentEra().startDate());
                    self.isDeleteEnable(true);
                    self.isEnableCode(false);
                    self.isUpdate(true);
                    qmm034.a.service.getFixAttribute(self.currentEra().eraHist()).done(function (data) {
                        if (data === 0) {
                            self.isEnableCode(true);
                        }
                    });
                    if (self.dirtyObject !== undefined)
                        self.dirtyObject.reset();
                    self.previousCurrentCode = codeChanged;
                };
                ScreenModel.prototype.init = function () {
                    var self = this;
                    self.items = ko.observableArray([]);
                    self.columns = ko.observableArray([
                        { headerText: 'KEY', key: 'eraHist', width: 50, hidden: true },
                        { headerText: '元号', key: 'eraName', width: 50 },
                        { headerText: '記号', key: 'eraMark', width: 50 },
                        { headerText: '開始年月日', key: 'startDate', width: 80 },
                    ]);
                    self.currentEra = ko.observable((new EraModel('', '', moment.utc().toISOString(), 1, '', moment.utc().toISOString())));
                    self.currentCode = ko.observable(null);
                    self.date = ko.observable('');
                    self.dateTime = ko.observable('');
                    self.isDeleteEnable = ko.observable(false);
                    self.isEnableCode = ko.observable(false);
                    self.isUpdate = ko.observable(false);
                };
                ScreenModel.prototype.validateData = function () {
                    $(".nts-editor").ntsEditor("validate");
                    $("#A_INP_003").ntsEditor("validate");
                    if ($(".nts-editor").ntsError('hasError') || $("#A_INP_003").ntsError('hasError')) {
                        return false;
                    }
                    return true;
                };
                ScreenModel.prototype.insertData = function () {
                    var self = this;
                    var eraName;
                    eraName = $('#A_INP_001').val();
                    var eraMark;
                    eraMark = $('#A_INP_002').val();
                    var startDate = self.startDate();
                    var endDate;
                    var eraHist = self.currentEra().eraHist();
                    var fixAttribute;
                    var dfd = $.Deferred();
                    var node;
                    node = new qmm034.a.service.model.EraDto(eraName, eraMark, startDate, endDate, fixAttribute, eraHist);
                    if (!self.validateData()) {
                        return;
                    }
                    qmm034.a.service.addData(self.isUpdate(), node).done(function (result) {
                        self.dirtyObject.reset();
                        self.reload().done(function () {
                            self.currentCode(result === undefined ? self.currentEra().eraHist() : result.eraHist);
                            self.isDeleteEnable = ko.observable(false);
                            self.isEnableCode = ko.observable(false);
                            self.isUpdate = ko.observable(true);
                            var lastStartDate = _.maxBy(self.items(), function (o) {
                                return o.startDate;
                            });
                            dfd.resolve();
                        });
                    }).fail(function (res) {
                        $("#A_INP_003").ntsError("set", res.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.alertDelete = function () {
                    var self = this;
                    nts.uk.ui.dialog.confirm("データを削除します。\r\nよろしいですか？").ifYes(function () {
                        self.deleteData();
                    });
                };
                ScreenModel.prototype.reload = function () {
                    var dfd = $.Deferred();
                    var self = this;
                    $.when(qmm034.a.service.getAllEras()).done(function (data) {
                        self.items([]);
                        if (data.length > 0) {
                            self.items(data);
                            //self.date(self.currentEra().startDate().toString());
                            //self.currentCode(self.currentEra().eraHist());
                            self.isDeleteEnable(true);
                        }
                        dfd.resolve(data);
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
                    var eraHist = self.currentEra().eraHist();
                    var startDate = self.startDate();
                    var dfd = $.Deferred();
                    var node;
                    node = new qmm034.a.service.model.EraDtoDelete(eraHist);
                    var rowIndex = _.findIndex(self.items(), function (item) {
                        return item.eraName == self.currentEra().eraName();
                    });
                    qmm034.a.service.deleteData(node).done(function (result) {
                        self.reload().done(function (data) {
                            if (self.items().length === 0) {
                                self.refreshLayout();
                            }
                            else if (self.items().length === rowIndex) {
                                self.currentCode(self.items()[rowIndex - 1].eraHist);
                            }
                            else if (self.items().length < rowIndex) {
                                self.currentCode(self.items()[0].eraHist);
                            }
                            else {
                                self.currentCode(self.items()[rowIndex].eraHist);
                            }
                        });
                        dfd.resolve();
                    }).fail(function (error) {
                        alert(error.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.getEra = function (codeNew) {
                    var self = this;
                    var era = _.find(self.items(), function (item) {
                        return item.eraHist === codeNew;
                    });
                    if (era) {
                        return new EraModel(era.eraName, era.eraMark, era.startDate, era.fixAttribute, era.eraHist, era.endDate);
                    }
                    else {
                        return new EraModel("", "", moment.utc().toISOString(), 0, "", moment.utc().toISOString());
                    }
                };
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    // Page load dfd.
                    var dfd = $.Deferred();
                    // Resolve start page dfd after load all data.
                    $.when(qmm034.a.service.getAllEras()).done(function (data) {
                        if (data.length > 0) {
                            self.items(data);
                            self.currentEra(self.items()[0]);
                            self.dirtyObject = new nts.uk.ui.DirtyChecker(self.currentEra);
                            self.currentCode(self.currentEra().eraHist);
                        }
                        else {
                            self.dirtyObject = new nts.uk.ui.DirtyChecker(self.currentEra);
                            self.startWithEmptyData();
                        }
                        dfd.resolve();
                    }).fail(function (res) {
                        $("#A_INP_001").ntsError("set", res.message);
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.refreshLayout = function () {
                    var self = this;
                    self.clearError();
                    self.currentEra(new EraModel('', '', moment.utc().toISOString(), 1, '', moment.utc().toISOString()));
                    self.startDate(self.currentEra().startDate());
                    self.currentCode(null);
                    self.isDeleteEnable(false);
                    self.isEnableCode(true);
                    self.isUpdate(false);
                    if (self.dirtyObject !== undefined)
                        self.dirtyObject.reset();
                    $("#A_INP_001").focus();
                };
                ScreenModel.prototype.startWithEmptyData = function () {
                    var self = this;
                    self.isDeleteEnable(false);
                    self.isEnableCode(true);
                    self.isUpdate(false);
                    self.dirtyObject.reset();
                };
                ScreenModel.prototype.clearError = function () {
                    if ($(".nts-editor").ntsError('hasError'))
                        $(".nts-editor").ntsError('clear');
                    if ($("#A_INP_003").ntsError('hasError'))
                        $("#A_INP_003").ntsError('clear');
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var EraModel = (function () {
                function EraModel(eraName, eraMark, startDate, fixAttribute, eraHist, endDate) {
                    this.eraName = ko.observable(eraName);
                    this.eraMark = ko.observable(eraMark);
                    this.startDate = ko.observable(startDate);
                    this.endDate = ko.observable(endDate);
                    this.fixAttribute = ko.observable(fixAttribute);
                    this.eraHist = ko.observable(eraHist);
                }
                return EraModel;
            }());
            viewmodel.EraModel = EraModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm034.a || (qmm034.a = {}));
})(qmm034 || (qmm034 = {}));
//# sourceMappingURL=viewmodel.js.map