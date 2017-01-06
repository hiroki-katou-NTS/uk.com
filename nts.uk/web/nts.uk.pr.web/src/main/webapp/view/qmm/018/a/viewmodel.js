var qmm018;
(function (qmm018) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                function ScreenModel() {
                    var self = this;
                    self.paymentDateProcessingList = ko.observableArray([]);
                    self.selectedPaymentDate = ko.observable(null);
                    self.roundingRules = ko.observableArray([
                        { code: '1', name: 'left' },
                        { code: '2', name: 'right' }
                    ]);
                    self.selectedRuleCode = ko.observable(1);
                }
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    var dfd = $.Deferred();
                    qmm018.a.service.getPaymentDateProcessingList().done(function (data) {
                        self.paymentDateProcessingList(data);
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                ScreenModel.prototype.openSubWindow = function () {
                    nts.uk.ui.windows.sub.modal("/view/qmm/018/b/index.xhtml");
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qmm018.a || (qmm018.a = {}));
})(qmm018 || (qmm018 = {}));
