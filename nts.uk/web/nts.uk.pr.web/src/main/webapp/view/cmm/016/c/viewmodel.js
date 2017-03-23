var cmmhoa013;
(function (cmmhoa013) {
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
                    //C_SEL_001
                    self.selectedId = ko.observable(1);
                    self.enable = ko.observable(true);
                }
                /**
                 * Start page
                 * get start date last from screen A
                 */
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
                /**
                 * decision add history
                 * set start date new and send to screen A(main)
                 * then close screen C
                 */
                ScreenModel.prototype.decision = function () {
                    var self = this;
                    if (self.inp_003() <= self.startDateLast()) {
                        alert("nhap lai start Date");
                        return;
                    }
                    else {
                        if (self.startDateLast() != '' && self.startDateLast() != null) {
                            var check = self.selectedId();
                        }
                        else {
                            var check = 2;
                        }
                        console.log(check);
                        nts.uk.ui.windows.setShared('cmm013C_startDateNew', self.inp_003(), true);
                        nts.uk.ui.windows.setShared('cmm013C_copy', check, true);
                        nts.uk.ui.windows.close();
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
    })(c = cmmhoa013.c || (cmmhoa013.c = {}));
})(cmmhoa013 || (cmmhoa013 = {}));
//# sourceMappingURL=viewmodel.js.map