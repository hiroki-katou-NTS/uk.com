var qet001;
(function (qet001) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var servicePath = {
                findOutputSettings: 'ctx/pr/report/wageledger/outputsetting/findAll',
                findOutputSettingDetail: 'ctx/pr/report/wageledger/outputsetting/find'
            };
            function findOutputSettingDetail(settingCode) {
                return nts.uk.request.ajax(servicePath.findOutputSettingDetail + '/' + settingCode);
            }
            service.findOutputSettingDetail = findOutputSettingDetail;
        })(service = b.service || (b.service = {}));
    })(b = qet001.b || (qet001.b = {}));
})(qet001 || (qet001 = {}));
