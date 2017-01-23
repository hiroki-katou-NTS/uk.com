var qpp018;
(function (qpp018) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var servicePath = {
                getallInsuranceOffice: "/screen/pr/QPP018/getAll"
            };
            function getAllInsuranceOffice() {
                return nts.uk.request.ajax(servicePath.getallInsuranceOffice);
            }
            service.getAllInsuranceOffice = getAllInsuranceOffice;
            var model;
            (function (model) {
                var InsuranceOffice = (function () {
                    function InsuranceOffice() {
                    }
                    return InsuranceOffice;
                }());
                model.InsuranceOffice = InsuranceOffice;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = qpp018.a || (qpp018.a = {}));
})(qpp018 || (qpp018 = {}));
