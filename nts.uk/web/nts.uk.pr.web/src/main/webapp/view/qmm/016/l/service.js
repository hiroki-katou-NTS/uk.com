var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm016;
                (function (qmm016) {
                    var l;
                    (function (l) {
                        var service;
                        (function (service) {
                            var paths = {
                                findAllLaborInsuranceOffice: "ctx/pr/core/insurance/labor/findall",
                                findLaborInsuranceOffice: "ctx/pr/core/insurance/labor/findLaborInsuranceOffice",
                                addLaborInsuranceOffice: "ctx/pr/core/insurance/labor/add",
                                updateLaborInsuranceOffice: "ctx/pr/core/insurance/labor/update",
                                deleteLaborInsuranceOffice: "ctx/pr/core/insurance/labor/delete",
                            };
                            var model;
                            (function (model) {
                                var CertificationDto = (function () {
                                    function CertificationDto() {
                                    }
                                    return CertificationDto;
                                }());
                                model.CertificationDto = CertificationDto;
                                var CertifyGroupDto = (function () {
                                    function CertifyGroupDto() {
                                    }
                                    return CertifyGroupDto;
                                }());
                                model.CertifyGroupDto = CertifyGroupDto;
                                (function (MultipleTargetSetting) {
                                    MultipleTargetSetting[MultipleTargetSetting["BigestMethod"] = 0] = "BigestMethod";
                                    MultipleTargetSetting[MultipleTargetSetting["TotalMethod"] = 1] = "TotalMethod";
                                })(model.MultipleTargetSetting || (model.MultipleTargetSetting = {}));
                                var MultipleTargetSetting = model.MultipleTargetSetting;
                                var MultipleTargetSettingDto = (function () {
                                    function MultipleTargetSettingDto(code, name) {
                                        this.code = code;
                                        this.name = name;
                                    }
                                    return MultipleTargetSettingDto;
                                }());
                                model.MultipleTargetSettingDto = MultipleTargetSettingDto;
                            })(model = service.model || (service.model = {}));
                        })(service = l.service || (l.service = {}));
                    })(l = qmm016.l || (qmm016.l = {}));
                })(qmm016 = view.qmm016 || (view.qmm016 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
