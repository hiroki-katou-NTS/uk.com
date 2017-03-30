var cmm013;
(function (cmm013) {
    var d;
    (function (d) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.inp_003 = ko.observable(null);
                    self.inp_003_enable = ko.observable(true);
                    self.startDateNew = ko.observable('');
                    self.startDateUpdate = ko.observable('');
                    self.endDateUpdate = ko.observable('');
                    //D_SEL_001
                    self.enable = ko.observable(true);
                    self.deleteChecker = ko.observable(0);
                    self.startDateLast = ko.observable('');
                    self.histIdUpdate = ko.observable('');
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.deleteChecker(nts.uk.ui.windows.getShared('delete'));
                    self.startDateUpdate(nts.uk.ui.windows.getShared('startUpdate'));
                    self.endDateUpdate(nts.uk.ui.windows.getShared('endUpdate'));
                    self.startDateLast(nts.uk.ui.windows.getShared('startDateLast'));
                    self.histIdUpdate(nts.uk.ui.windows.getShared('Id_13Update'));
                    if (self.deleteChecker() == 1) {
                        self.itemList = ko.observableArray([
                            new BoxModel(1, '履歴を削除する '),
                            new BoxModel(2, '履歴を修正する')
                        ]);
                        self.selectedId = ko.observable(2);
                        self.selectedId.subscribe((function (codeChanged) {
                            if (codeChanged == 1) {
                                self.inp_003_enable(false);
                            }
                            else {
                                self.inp_003_enable(true);
                            }
                        }));
                    }
                    if (self.deleteChecker() == 2) {
                        self.itemList = ko.observableArray([
                            new BoxModel(1, '履歴を修正する')
                        ]);
                        self.selectedId = ko.observable(1);
                    }
                    self.inp_003(self.startDateUpdate());
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.positionHis = function () {
                    var self = this;
                    if (self.selectedId() == 2 && self.deleteChecker() == 1) {
                        if (self.inp_003() >= self.endDateUpdate() || self.inp_003() <= self.startDateUpdate()) {
                            alert("Re Input");
                            return;
                        }
                    }
                    var dfd = $.Deferred();
                    if (self.selectedId() == 1 && self.deleteChecker() == 1) {
                        var jobHist_1 = new model.ListHistoryDto(self.startDateUpdate(), '', self.endDateUpdate(), self.histIdUpdate());
                        var checkDelete = '1';
                        var checkUpdate = '0';
                    }
                    else {
                        checkDelete = '0';
                        var jobHist = new model.ListHistoryDto(self.startDateUpdate(), self.inp_003(), self.endDateUpdate(), self.histIdUpdate());
                        if (self.startDateUpdate() == self.startDateLast()) {
                            checkUpdate = '2';
                        }
                        else {
                            checkUpdate = '1';
                        }
                    }
                    var afterUpdate = new model.AfUpdate(jobHist, checkUpdate, checkDelete);
                    d.service.updateHist(afterUpdate).done(function () {
                        alert('update thanh cong');
                        nts.uk.ui.windows.setShared('Finish', true, true);
                        nts.uk.ui.windows.close();
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
            var BoxModel = (function () {
                function BoxModel(id, name) {
                    var self = this;
                    self.id = id;
                    self.name = name;
                }
                return BoxModel;
            }());
            var model;
            (function (model) {
                var AfUpdate = (function () {
                    function AfUpdate(jHist, checkUpdate, checkDelete) {
                        this.jHist = jHist;
                        this.checkUpdate = checkUpdate;
                        this.checkDelete = checkDelete;
                    }
                    return AfUpdate;
                }());
                model.AfUpdate = AfUpdate;
                var ListHistoryDto = (function () {
                    function ListHistoryDto(companyCode, startDate, endDate, historyId) {
                        this.companyCode = companyCode;
                        this.startDate = startDate;
                        this.endDate = endDate;
                        this.historyId = historyId;
                    }
                    return ListHistoryDto;
                }());
                model.ListHistoryDto = ListHistoryDto;
            })(model = viewmodel.model || (viewmodel.model = {}));
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = cmm013.d || (cmm013.d = {}));
})(cmm013 || (cmm013 = {}));
//# sourceMappingURL=cmm013.d.vm.js.map