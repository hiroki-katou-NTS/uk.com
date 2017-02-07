var qpp018;
(function (qpp018) {
    var c;
    (function (c) {
        var service;
        (function (service) {
            var servicePath = {
                getallCheckListPrintSetting: "ctx/pr/report/insurance/findAll"
            };
            function getallCheckListPrintSetting() {
                return nts.uk.request.ajax(servicePath.getallCheckListPrintSetting);
            }
            service.getallCheckListPrintSetting = getallCheckListPrintSetting;
            var model;
            (function (model) {
                var CheckListPrintSetting = (function () {
                    function CheckListPrintSetting() {
                    }
                    return CheckListPrintSetting;
                }());
                model.CheckListPrintSetting = CheckListPrintSetting;
            })(model = service.model || (service.model = {}));
        })(service = c.service || (c.service = {}));
    })(c = qpp018.c || (qpp018.c = {}));
})(qpp018 || (qpp018 = {}));
