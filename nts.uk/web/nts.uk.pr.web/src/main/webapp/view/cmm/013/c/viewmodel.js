var cmm013;
(function (cmm013) {
    var c;
    (function (c) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.label_002 = ko.observable(new Labels());
                    self.inp_003 = ko.observable(null);
                    self.historyId = ko.observable(null);
                    self.startDateLast = ko.observable('');
                    self.endDateUpdate = ko.observable('');
                    self.selectedId = ko.observable(1);
                    self.enable = ko.observable(true);
                    self.startDateLast = ko.observable(null);
                    self.historyIdLast = ko.observable(null);
                    self.length = ko.observable(0);
                    self.startDateAddNew = ko.observable("");
                    self.startDateUpdate = ko.observable(null);
                    self.endDateUpdate = ko.observable(null);
                    self.historyIdUpdate = ko.observable(null);
                    self.startDateUpdateNew = ko.observable(null);
                    self.startDatePre = ko.observable(null);
                    self.jobHistory = ko.observable(null);
                    self.selectedCode = ko.observable(null);
                    self.checkDelete = ko.observable(null);
                    self.listbox = ko.observableArray([]);
                }
                ScreenModel.prototype.addHist = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    if (self.listbox() === undefined || self.listbox() == null || self.listbox().length == 0) {
                        var jobHistNew = new model.ListHistoryDto('1', self.startDateAddNew(), '', '');
                    }
                    else {
                        var jobHistNew = new model.ListHistoryDto('0', self.startDateAddNew(), '', '');
                    }
                    c.service.addJobHist(jobHistNew).done(function () {
                        nts.uk.ui.windows.setShared('startNew', '', true);
                        self.getAllHist();
                    }).fail(function (res) {
                        alert('fail');
                    });
                };
                ScreenModel.prototype.add = function () {
                    var self = this;
                    if (self.inp_003() <= self.startDateLast()) {
                        alert("");
                        return;
                    }
                    else {
                        if (self.startDateLast() != null) {
                            var check = self.selectedId();
                        }
                        else {
                            var check = 2;
                        }
                        nts.uk.ui.windows.setShared('startNew', self.inp_003());
                        nts.uk.ui.windows.setShared('copy_c', check);
                        nts.uk.ui.windows.close();
                    }
                };
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.historyId(nts.uk.ui.windows.getShared('Id_13'));
                    self.startDateLast(nts.uk.ui.windows.getShared('startLast'));
                    self.endDateUpdate(nts.uk.ui.windows.getShared('endUpdate'));
                    if (self.startDateLast() != '' && self.startDateLast() != null) {
                        self.itemList = ko.observableArray([
                            new BoxModel(1, '最新の履歴（' + self.startDateLast() + '）から引き継ぐ  '),
                            new BoxModel(2, '全員参照不可')
                        ]);
                    }
                    else {
                        self.itemList = ko.observableArray([
                            new BoxModel(1, '全員参照不可')
                        ]);
                    }
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.getAllHist = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.selectedCode('');
                    self.listbox([]);
                    c.service.getAllHistory().done(function (history_arr) {
                        if (history_arr === undefined || history_arr.length === 0)
                            return;
                        self.listbox(history_arr);
                        _.forEach(history_arr, function (strHistory) {
                            self.listbox.push(strHistory);
                        });
                        var historyFirst = _.first(history_arr);
                        var historyLast = _.last(history_arr);
                        self.checkDelete(historyLast.startDate);
                        self.selectedCode(historyFirst.startDate);
                        self.startDateUpdate(historyFirst.startDate);
                        self.endDateUpdate(historyFirst.endDate);
                        self.historyIdUpdate(historyFirst.historyId);
                        self.startDateLast(historyFirst.startDate);
                        self.historyIdLast(historyFirst.historyId);
                        dfd.resolve(history_arr);
                    });
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
            })(model = viewmodel.model || (viewmodel.model = {}));
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = cmm013.c || (cmm013.c = {}));
})(cmm013 || (cmm013 = {}));
