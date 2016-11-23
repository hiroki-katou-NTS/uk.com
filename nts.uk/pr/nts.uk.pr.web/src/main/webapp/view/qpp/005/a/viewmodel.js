var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp;
                (function (qpp) {
                    var _005;
                    (function (_005) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenModel = (function () {
                                function ScreenModel() {
                                    var self = this;
                                    self.isHandInput = ko.observable(true);
                                    self.paymentDataResult = ko.observable();
                                }
                                ScreenModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    _005.service.getPaymentData("A0001", "A001").done(function (paymentResult) {
                                        self.paymentDataResult(paymentResult);
                                        dfd.resolve(null);
                                    }).fail(function (res) {
                                        // Alert message
                                        alert(res);
                                    });
                                    // Return.
                                    return dfd.promise();
                                };
                                return ScreenModel;
                            }());
                            viewmodel.ScreenModel = ScreenModel;
                            ;
                        })(viewmodel = _005.viewmodel || (_005.viewmodel = {}));
                    })(_005 = qpp._005 || (qpp._005 = {}));
                })(qpp = view.qpp || (view.qpp = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
