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
                    self.selectedId = ko.observable(1);
                    self.enable = ko.observable(true);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    self.historyId(nts.uk.ui.windows.getShared('CMM013_historyId'));
                    self.startDateLast(nts.uk.ui.windows.getShared('CMM013_startDateLast'));
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
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                ScreenModel.prototype.add = function () {
                    var self = this;
                    if (self.checkTypeInput() == false) {
                        return;
                    }
                    else if (self.checkValueInput(self.inp_003()) == false) {
                        return;
                    }
                    else {
                        if (self.startDateLast() != '' && self.startDateLast() != null) {
                            var check = self.selectedId();
                        }
                        else {
                            var check = 2;
                        }
                        var date = new Date(self.inp_003());
                        var dateNew = date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate();
                        if (date.getMonth() < 9 && date.getDate() < 10) {
                            dateNew = date.getFullYear() + '/' + 0 + (date.getMonth() + 1) + '/' + 0 + date.getDate();
                        }
                        else {
                            if (date.getDate() < 10) {
                                dateNew = date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + 0 + date.getDate();
                            }
                            if (date.getMonth() < 9) {
                                dateNew = date.getFullYear() + '/' + 0 + (date.getMonth() + 1) + '/' + date.getDate();
                            }
                        }
                        if (self.checkValueInput(dateNew) == false) {
                            return;
                        }
                        nts.uk.ui.windows.setShared('cmm013C_startDateNew', dateNew, true);
                        nts.uk.ui.windows.setShared('cmm013C_copy', check, true);
                        nts.uk.ui.windows.setShared('cmm013Insert', true, true);
                        nts.uk.ui.windows.close();
                    }
                };
                ScreenModel.prototype.checkTypeInput = function () {
                    var self = this;
                    var date = new Date(self.inp_003());
                    if (date.toDateString() == 'Invalid Date') {
                        alert("nhap lai ngay theo dinh dang YYYY-MM-DD hoac YYYY/MM/DD hoac YYYY.MM.DD");
                        return false;
                    }
                    else {
                        return true;
                    }
                };
                ScreenModel.prototype.checkValueInput = function (value) {
                    var self = this;
                    if (value <= self.startDateLast()) {
                        alert("nhap lai start Date");
                        return false;
                    }
                    else {
                        return true;
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
            var BoxModel = (function () {
                function BoxModel(id, name) {
                    var self = this;
                    self.id = id;
                    self.name = name;
                }
                return BoxModel;
            }());
            viewmodel.BoxModel = BoxModel;
        })(viewmodel = c.viewmodel || (c.viewmodel = {}));
    })(c = cmm013.c || (cmm013.c = {}));
})(cmm013 || (cmm013 = {}));
//# sourceMappingURL=cmm013.c.vm.js.map