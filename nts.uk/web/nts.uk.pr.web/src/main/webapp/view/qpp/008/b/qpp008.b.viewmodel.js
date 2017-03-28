var qpp008;
(function (qpp008) {
    var b;
    (function (b) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.paymentDateProcessingList = ko.observableArray([]);
                    self.selectedPaymentDate = ko.observable(null);
                    self.checkedSel1 = ko.observable(true);
                    self.enableSel1 = ko.observable(false);
                    self.checkedSel2 = ko.observable(true);
                    self.enableSel2 = ko.observable(true);
                    self.checkedSel3 = ko.observable(true);
                    self.enableSel3 = ko.observable(true);
                    self.checkedSel4 = ko.observable(true);
                    self.enableSel4 = ko.observable(true);
                    self.departmentDate = ko.observable('2017/01/13' + 'の部門構成で集計します。');
                    self.roundingRules = ko.observableArray([
                        { code: '1', name: '表示する' },
                        { code: '2', name: '表示しない' },
                    ]);
                    self.selectedRuleCode1 = ko.observable(1);
                    self.roundingRules1 = ko.observableArray([
                        { code1: '1', name1: '表示する' },
                        { code1: '2', name1: '表示しない' },
                    ]);
                    self.selectedRuleCode = ko.observable(1);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    b.service.getPaymentDateProcessingList().done(function (data) {
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.closeDialog = function () {
                    nts.uk.ui.windows.close();
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = b.viewmodel || (b.viewmodel = {}));
    })(b = qpp008.b || (qpp008.b = {}));
})(qpp008 || (qpp008 = {}));
//# sourceMappingURL=qpp008.b.viewmodel.js.map