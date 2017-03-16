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
                                findAllCertification: "pr/wagetable/certification/findall",
                                findAllCertifyGroup: "pr/wagetable/certifygroup/findall",
                                findCertifyGroup: "pr/wagetable/certifygroup/find",
                                addCertifyGroup: "pr/wagetable/certifygroup/add",
                                updateCertifyGroup: "pr/wagetable/certifygroup/update",
                                deleteCertifyGroup: "pr/wagetable/certifygroup/delete",
                            };
                            function findAllCertification() {
                                return nts.uk.request.ajax(paths.findAllCertification);
                            }
                            service.findAllCertification = findAllCertification;
                            function findAllCertifyGroup() {
                                var dfd = $.Deferred();
                                return nts.uk.request.ajax(paths.findAllCertifyGroup);
                            }
                            service.findAllCertifyGroup = findAllCertifyGroup;
                            function findCertifyGroup(code) {
                                var dfd = $.Deferred();
                                return nts.uk.request.ajax(paths.findCertifyGroup + "/" + code);
                            }
                            service.findCertifyGroup = findCertifyGroup;
                            function addCertifyGroup(certifyGroupDto) {
                                var dfd = $.Deferred();
                                var data = { certifyGroupDto: certifyGroupDto };
                                return nts.uk.request.ajax(paths.addCertifyGroup, data);
                            }
                            service.addCertifyGroup = addCertifyGroup;
                            function updateCertifyGroup(certifyGroupDto) {
                                var dfd = $.Deferred();
                                var data = { certifyGroupDto: certifyGroupDto };
                                return nts.uk.request.ajax(paths.updateCertifyGroup, data);
                            }
                            service.updateCertifyGroup = updateCertifyGroup;
                            function deleteCertifyGroup(certifyGroupDeleteDto) {
                                var dfd = $.Deferred();
                                var data = { certifyGroupDeleteDto: certifyGroupDeleteDto };
                                return nts.uk.request.ajax(paths.deleteCertifyGroup, data);
                            }
                            service.deleteCertifyGroup = deleteCertifyGroup;
                            var model;
                            (function (model) {
                                var CertificationDto = (function () {
                                    function CertificationDto() {
                                    }
                                    return CertificationDto;
                                }());
                                model.CertificationDto = CertificationDto;
                                var CertificationFindInDto = (function () {
                                    function CertificationFindInDto() {
                                    }
                                    return CertificationFindInDto;
                                }());
                                model.CertificationFindInDto = CertificationFindInDto;
                                var CertifyGroupFindOutDto = (function () {
                                    function CertifyGroupFindOutDto() {
                                    }
                                    return CertifyGroupFindOutDto;
                                }());
                                model.CertifyGroupFindOutDto = CertifyGroupFindOutDto;
                                var CertifyGroupDto = (function () {
                                    function CertifyGroupDto() {
                                    }
                                    return CertifyGroupDto;
                                }());
                                model.CertifyGroupDto = CertifyGroupDto;
                                var CertifyGroupDeleteDto = (function () {
                                    function CertifyGroupDeleteDto() {
                                    }
                                    return CertifyGroupDeleteDto;
                                }());
                                model.CertifyGroupDeleteDto = CertifyGroupDeleteDto;
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
                                (function (TypeActionCertifyGroup) {
                                    TypeActionCertifyGroup[TypeActionCertifyGroup["add"] = 1] = "add";
                                    TypeActionCertifyGroup[TypeActionCertifyGroup["update"] = 2] = "update";
                                })(model.TypeActionCertifyGroup || (model.TypeActionCertifyGroup = {}));
                                var TypeActionCertifyGroup = model.TypeActionCertifyGroup;
                            })(model = service.model || (service.model = {}));
                        })(service = l.service || (l.service = {}));
                    })(l = qmm016.l || (qmm016.l = {}));
                })(qmm016 = view.qmm016 || (view.qmm016 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=service.js.map