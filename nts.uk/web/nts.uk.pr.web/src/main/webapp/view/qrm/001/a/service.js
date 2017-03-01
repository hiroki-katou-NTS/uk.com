var qrm001;
(function (qrm001) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var paths = {
                getRetirementPaymentList: "pr/core/retirement/payment/findByCompanyCodeandPersonId/{0}",
                register: "pr/core/retirement/payment/register",
                update: "pr/core/retirement/payment/update",
                remove: "pr/core/retirement/payment/remove"
            };
            function getRetirementPaymentList(personId) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(nts.uk.text.format(paths.getRetirementPaymentList, personId))
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.getRetirementPaymentList = getRetirementPaymentList;
            function registerRetirementPaymentInfo(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.register, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.registerRetirementPaymentInfo = registerRetirementPaymentInfo;
            function updateRetirementPaymentInfo(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.update, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateRetirementPaymentInfo = updateRetirementPaymentInfo;
            function removeRetirementPaymentInfo(command) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.remove, command)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.removeRetirementPaymentInfo = removeRetirementPaymentInfo;
        })(service = a.service || (a.service = {}));
    })(a = qrm001.a || (qrm001.a = {}));
})(qrm001 || (qrm001 = {}));
