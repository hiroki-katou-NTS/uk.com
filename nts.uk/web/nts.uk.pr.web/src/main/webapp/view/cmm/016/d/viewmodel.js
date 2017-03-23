var cmmhoa013;
(function (cmmhoa013) {
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
                    self.cDelete = ko.observable(0);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.cDelete(nts.uk.ui.windows.getShared('CMM013_delete'));
                    self.startDateUpdate(nts.uk.ui.windows.getShared('CMM013_startDateUpdate'));
                    self.endDateUpdate(nts.uk.ui.windows.getShared('CMM013_endDateUpdate'));
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
                            new BoxModel(1, '履歴を修正する')
                        ]);
                        self.selectedId = ko.observable(1);
                    }
                    self.inp_003(self.startDateUpdate());
                    dfd.resolve();
                    return dfd.promise();
                };
                /**
                 * decision update job history
                 *
                 */
                ScreenModel.prototype.decision = function () {
                    var self = this;
                    if (self.selectedId() == 2 && self.cDelete() == 1) {
                        if (self.inp_003() >= self.endDateUpdate() || self.inp_003() <= self.startDateUpdate()) {
                            alert("nhap lai start Date");
                            return;
                        }
                    }
                    if (self.selectedId() == 1 && self.cDelete() == 1) {
                        nts.uk.ui.windows.setShared('cmm013D_startDateUpdateNew', '', true);
                        nts.uk.ui.windows.setShared('cmm013D_check', '1', true);
                        nts.uk.ui.windows.close();
                    }
                    else {
                        nts.uk.ui.windows.setShared('cmm013D_startDateUpdateNew', self.inp_003(), true);
                        nts.uk.ui.windows.setShared('cmm013D_check', '2', true);
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
    })(d = cmmhoa013.d || (cmmhoa013.d = {}));
})(cmmhoa013 || (cmmhoa013 = {}));
//# sourceMappingURL=viewmodel.js.map