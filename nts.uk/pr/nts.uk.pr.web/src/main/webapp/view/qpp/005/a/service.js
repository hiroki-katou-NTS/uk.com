var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp005;
                (function (qpp005) {
                    var service;
                    (function (service) {
                        var servicePath = {
                            getPaymentData: "/screen/pr/qpp005/paymentdata/find",
                            insertData: "pr/proto/paymentdata/insert",
                            updateData: "pr/proto/paymentdata/update"
                        };
                        function getPaymentData(personId, employeeCode) {
                            var dfd = $.Deferred();
                            var query = {
                                personId: personId,
                                employeeCode: employeeCode
                            };
                            nts.uk.request.ajax(servicePath.getPaymentData, query).done(function (res) {
                                dfd.resolve(res);
                            }).fail(function (res) {
                                alert('fail');
                            });
                            return dfd.promise();
                        }
                        service.getPaymentData = getPaymentData;
                        function register(paymentData) {
                            var data = ko.toJS(paymentData);
                            var isCreated = data.paymentHeader.isCreated;
                            if (isCreated) {
                                return nts.uk.request.ajax(servicePath.insertData, data);
                            }
                            return nts.uk.request.ajax(servicePath.updateData, data);
                        }
                        service.register = register;
                    })(service = qpp005.service || (qpp005.service = {}));
                })(qpp005 = view.qpp005 || (view.qpp005 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
