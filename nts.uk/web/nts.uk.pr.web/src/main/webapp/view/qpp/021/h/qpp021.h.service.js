var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp021;
                (function (qpp021) {
                    var h;
                    (function (h) {
                        var service;
                        (function (service) {
                            var paths = {
                                findAllEmployee: "basic/organization/employment/findallemployments",
                                findContactItemSettings: "ctx/pr/report/payment/contact/item/findSettings",
                                saveContactItemSettings: "ctx/pr/report/payment/contact/item/save"
                            };
                            function findAllEmployee() {
                                return nts.uk.request.ajax("com", paths.findAllEmployee);
                            }
                            service.findAllEmployee = findAllEmployee;
                            function findContactItemSettings(dto) {
                                return nts.uk.request.ajax(paths.findContactItemSettings, dto);
                            }
                            service.findContactItemSettings = findContactItemSettings;
                            function saveContactItemSettings(dto) {
                                var data = { dto: dto };
                                return nts.uk.request.ajax(paths.saveContactItemSettings, data);
                            }
                            service.saveContactItemSettings = saveContactItemSettings;
                            var model;
                            (function (model) {
                                var EmploymentDto = (function () {
                                    function EmploymentDto() {
                                    }
                                    return EmploymentDto;
                                }());
                                model.EmploymentDto = EmploymentDto;
                                var EmpCommentDto = (function () {
                                    function EmpCommentDto() {
                                    }
                                    return EmpCommentDto;
                                }());
                                model.EmpCommentDto = EmpCommentDto;
                                var ContactItemsSettingDto = (function () {
                                    function ContactItemsSettingDto() {
                                    }
                                    return ContactItemsSettingDto;
                                }());
                                model.ContactItemsSettingDto = ContactItemsSettingDto;
                                var EmpCommentFindDto = (function () {
                                    function EmpCommentFindDto() {
                                    }
                                    return EmpCommentFindDto;
                                }());
                                model.EmpCommentFindDto = EmpCommentFindDto;
                                var ContactItemsSettingFindDto = (function () {
                                    function ContactItemsSettingFindDto() {
                                    }
                                    return ContactItemsSettingFindDto;
                                }());
                                model.ContactItemsSettingFindDto = ContactItemsSettingFindDto;
                            })(model = service.model || (service.model = {}));
                        })(service = h.service || (h.service = {}));
                    })(h = qpp021.h || (qpp021.h = {}));
                })(qpp021 = view.qpp021 || (view.qpp021 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp021.h.service.js.map