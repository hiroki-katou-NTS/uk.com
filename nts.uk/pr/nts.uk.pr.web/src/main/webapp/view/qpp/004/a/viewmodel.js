var qpp004;
(function (qpp004) {
    var a;
    (function (a) {
        var viewmodel;
        (function (viewmodel) {
            var ScreenModel = (function () {
                /**
                 * Init screen model.
                 */
                function ScreenModel() {
                    var self = this;
                    self.paymentDateProcessingList = ko.observableArray([]);
                    self.selectedPaymetnProcessing = ko.observable(null);
                    self.displayCurrentYearMonthProcessing = ko.observable(null);
                    self.currentPaymentDateProcessing = ko.observable(null);
                    self.selectedPaymetnProcessing.subscribe(function (newValue) {
                        var currentDateMaster = _.find(self.paymentDateProcessingList(), function (item) {
                            return item.processingNo == newValue;
                        });
                        self.currentPaymentDateProcessing(currentDateMaster);
                        self.displayCurrentYearMonthProcessing(nts.uk.text.formatYearMonth(currentDateMaster.currentProcessingYm));
                    });
                }
                /**
                 * Start page.
                 * Load all data which is need for binding data.
                 */
                ScreenModel.prototype.startPage = function () {
                    var self = this;
                    // Page load dfd.
                    var dfd = $.Deferred();
                    // Resolve start page dfd after load all data.
                    $.when(qpp004.a.service.getPaymentDateProcessingMasterList()).done(function (data) {
                        self.paymentDateProcessingList(data);
                        dfd.resolve();
                    }).fail(function (res) {
                    });
                    return dfd.promise();
                };
                /**
                 * Redirect to page process create data
                 */
                ScreenModel.prototype.redirectToCreateData = function () {
                    var self = this;
                    var data = self.currentPaymentDateProcessing();
                    data["displayCurrentProcessingYm"] = self.displayCurrentYearMonthProcessing();
                    nts.uk.request.jump("/view/qpp/004/b/index.xhtml", data);
                };
                return ScreenModel;
            }());
            viewmodel.ScreenModel = ScreenModel;
        })(viewmodel = a.viewmodel || (a.viewmodel = {}));
    })(a = qpp004.a || (qpp004.a = {}));
})(qpp004 || (qpp004 = {}));
