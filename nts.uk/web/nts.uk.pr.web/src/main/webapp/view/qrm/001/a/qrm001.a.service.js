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
                return nts.uk.request.ajax(nts.uk.text.format(paths.getRetirementPaymentList, personId));
            }
            service.getRetirementPaymentList = getRetirementPaymentList;
            function registerRetirementPaymentInfo(command) {
                return nts.uk.request.ajax(paths.register, command);
            }
            service.registerRetirementPaymentInfo = registerRetirementPaymentInfo;
            function updateRetirementPaymentInfo(command) {
                return nts.uk.request.ajax(paths.update, command);
            }
            service.updateRetirementPaymentInfo = updateRetirementPaymentInfo;
            function removeRetirementPaymentInfo(command) {
                return nts.uk.request.ajax(paths.remove, command);
            }
            service.removeRetirementPaymentInfo = removeRetirementPaymentInfo;
        })(service = a.service || (a.service = {}));
    })(a = qrm001.a || (qrm001.a = {}));
})(qrm001 || (qrm001 = {}));
//# sourceMappingURL=qrm001.a.service.js.map