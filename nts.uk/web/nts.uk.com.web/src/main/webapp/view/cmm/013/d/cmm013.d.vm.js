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
                    self.enable = ko.observable(true);
                    self.cDelete = ko.observable(0);
                    self.sDateLast = ko.observable('');
                    self.histIdUpdate = ko.observable('');
                }
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.cDelete(nts.uk.ui.windows.getShared('CMM013_delete'));
                    self.startDateUpdate(nts.uk.ui.windows.getShared('CMM013_startDateUpdate'));
                    self.endDateUpdate(nts.uk.ui.windows.getShared('CMM013_endDateUpdate'));
                    self.sDateLast(nts.uk.ui.windows.getShared('CMM013_sDateLast'));
                    self.histIdUpdate(nts.uk.ui.windows.getShared('CMM013_historyIdUpdate'));
                    if (self.cDelete() == 1) {
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
                    if (self.cDelete() == 2) {
                        self.itemList = ko.observableArray([
                            new BoxModel(1, '履歴を修正する'),
                            new BoxModel(2, '履歴を修正する')
                        ]);
                        self.selectedId = ko.observable(1);
                    }
                    self.inp_003(self.startDateUpdate());
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.decision = function () {
                    var self = this;
                    if (self.selectedId() == 2 && self.cDelete() == 1) {
                        if (self.inp_003() >= self.endDateUpdate() || self.inp_003() <= self.startDateUpdate()) {
                            alert("nhap lai start Date");
                            return;
                        }
                    }
                    var dfd = $.Deferred();
                    if (self.selectedId() == 1 && self.cDelete() == 1) {
                        var jobHist = new d.service.model.JobHistDto(self.startDateUpdate(), '', self.endDateUpdate(), self.histIdUpdate());
                        var checkDelete = '1';
                        var checkUpdate = '0';
                    }
                    else {
                        checkDelete = '0';
                        var jobHist = new d.service.model.JobHistDto(self.startDateUpdate(), self.inp_003(), self.endDateUpdate(), self.histIdUpdate());
                        if (self.startDateUpdate() == self.sDateLast()) {
                            checkUpdate = '2';
                        }
                        else {
                            checkUpdate = '1';
                        }
                    }
                    var updateHandler = new d.service.model.UpdateHandler(jobHist, checkUpdate, checkDelete);
                    d.service.updateJobHist(updateHandler).done(function () {
                        alert('update thanh cong');
                        nts.uk.ui.windows.setShared('cmm013D_updateFinish', true, true);
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
        })(viewmodel = d.viewmodel || (d.viewmodel = {}));
    })(d = cmm013.d || (cmm013.d = {}));
})(cmm013 || (cmm013 = {}));
//# sourceMappingURL=cmm013.d.vm.js.map