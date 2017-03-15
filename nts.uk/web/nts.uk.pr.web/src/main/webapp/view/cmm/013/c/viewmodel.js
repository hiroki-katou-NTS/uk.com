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
                }
                ScreenModel.prototype.add = function () {
                    var self = this;
                    if (self.inp_003() <= self.startDateLast()) {
                        alert("");
                        return;
                    }
                    else {
                        if (self.startDateLast() != '' && self.startDateLast() != null) {
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
