var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp007;
                (function (qpp007) {
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var servicePath = {
                                findAllSalaryOutputSetting: "ctx/pr/report/salary/outputsetting/findall",
                                saveAsPdf: "screen/pr/QPP007/saveAsPdf"
                            };
                            function findAllSalaryOutputSetting() {
                                return nts.uk.request.ajax(servicePath.findAllSalaryOutputSetting);
                            }
                            service.findAllSalaryOutputSetting = findAllSalaryOutputSetting;
                            function saveAsPdf(command) {
                                return nts.uk.request.exportFile(servicePath.saveAsPdf, command);
                            }
                            service.saveAsPdf = saveAsPdf;
                            var model;
                            (function (model) {
                                var SalaryOutputSettingHeaderDto = (function () {
                                    function SalaryOutputSettingHeaderDto() {
                                    }
                                    return SalaryOutputSettingHeaderDto;
                                }());
                                model.SalaryOutputSettingHeaderDto = SalaryOutputSettingHeaderDto;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qpp007.a || (qpp007.a = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
