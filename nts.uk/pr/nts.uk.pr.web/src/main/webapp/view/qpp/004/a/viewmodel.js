var qpp004;
(function (qpp004) {
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
                $.when(qpp004.service.getPaymentDateProcessingMaster()).done(function (data) {
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
                alert('aaa');
            };
            return ScreenModel;
        }());
        viewmodel.ScreenModel = ScreenModel;
    })(viewmodel = qpp004.viewmodel || (qpp004.viewmodel = {}));
})(qpp004 || (qpp004 = {}));
