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
                    self.itemList = ko.observableArray([
                        new BoxModel(1, '履歴を削除する '),
                        new BoxModel(2, '履歴を修正する')
                    ]);
                    self.selectedId = ko.observable(2);
                    self.enable = ko.observable(true);
                    self.selectedId.subscribe((function (codeChanged) {
                        if (codeChanged == 1) {
                            self.inp_003_enable(false);
                        }
                        else {
                            self.inp_003_enable(true);
                        }
                    }));
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.startDateUpdate(nts.uk.ui.windows.getShared('startUpdate'));
                    self.endDateUpdate(nts.uk.ui.windows.getShared('endUpdate'));
                    self.inp_003(self.startDateUpdate());
                    dfd.resolve();
                    return dfd.promise();
                };
                ScreenModel.prototype.positionHis = function () {
                    var self = this;
                    if (self.selectedId() == 2) {
                        if (self.inp_003() >= self.endDateUpdate() || self.inp_003() <= self.startDateUpdate()) {
                            alert("ERRRR");
                            return;
                        }
                        else {
                            nts.uk.ui.windows.setShared('startUpdateNew', self.inp_003());
                            nts.uk.ui.windows.setShared('check_d', self.selectedId());
                            nts.uk.ui.windows.close();
                        }
                    }
                    else {
                        self.inp_003(null);
                        nts.uk.ui.windows.setShared('startUpdateNew', self.inp_003());
                        nts.uk.ui.windows.setShared('check_d', self.selectedId());
                        nts.uk.ui.windows.close();
                    }
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
