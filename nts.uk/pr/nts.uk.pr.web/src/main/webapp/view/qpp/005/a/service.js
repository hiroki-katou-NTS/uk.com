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
                        var service;
                        (function (service) {
                            var servicePath = {
                                getPaymentData: "/screen/pr/qpp005/paymentdata/find"
                            };
                            function getPaymentData(personId, employeeCode) {
                                var dfd = $.Deferred();
                                var query = {
                                    personId: personId,
                                    employeeCode: employeeCode
                                };
                                nts.uk.request.ajax(servicePath.getPaymentData, query).done(function (res) {
                                    var paymentResult = res;
                                    dfd.resolve(paymentResult);
                                }).fail(function (res) {
                                    alert('fail');
                                });
                                return dfd.promise();
                            }
                            service.getPaymentData = getPaymentData;
                            /**
                               * Model namespace.
                            */
                            var model;
                            (function (model) {
                                var PaymentDataResult = (function () {
                                    function PaymentDataResult() {
                                    }
                                    return PaymentDataResult;
                                }());
                                model.PaymentDataResult = PaymentDataResult;
                                // header
                                var PaymentDataHeaderModel = (function () {
                                    function PaymentDataHeaderModel() {
                                    }
                                    return PaymentDataHeaderModel;
                                }());
                                model.PaymentDataHeaderModel = PaymentDataHeaderModel;
                                // categories
                                var LayoutMasterCategoryModel = (function () {
                                    function LayoutMasterCategoryModel() {
                                    }
                                    return LayoutMasterCategoryModel;
                                }());
                                model.LayoutMasterCategoryModel = LayoutMasterCategoryModel;
                                // item
                                var DetailItemModel = (function () {
                                    function DetailItemModel() {
                                    }
                                    return DetailItemModel;
                                }());
                                model.DetailItemModel = DetailItemModel;
                                var DetailItemPositionModel = (function () {
                                    function DetailItemPositionModel() {
                                    }
                                    return DetailItemPositionModel;
                                }());
                                model.DetailItemPositionModel = DetailItemPositionModel;
                            })(model = service.model || (service.model = {}));
                        })(service = _005.service || (_005.service = {}));
                    })(_005 = qpp._005 || (qpp._005 = {}));
                })(qpp = view.qpp || (view.qpp = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
