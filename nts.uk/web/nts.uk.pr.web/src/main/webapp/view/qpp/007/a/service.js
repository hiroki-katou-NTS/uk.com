var qpp007;
(function (qpp007) {
    var a;
    (function (a) {
        var service;
        (function (service) {
            var servicePath = {
                getallOutputSetting: "ctx/pr/report/wageledger/outputsetting/findAll"
            };
            function getallOutputSetting() {
                return nts.uk.request.ajax(servicePath.getallOutputSetting);
            }
            service.getallOutputSetting = getallOutputSetting;
            var model;
            (function (model) {
                var OutputSetting = (function () {
                    function OutputSetting() {
                    }
                    return OutputSetting;
                }());
                model.OutputSetting = OutputSetting;
            })(model = service.model || (service.model = {}));
        })(service = a.service || (a.service = {}));
    })(a = qpp007.a || (qpp007.a = {}));
})(qpp007 || (qpp007 = {}));
