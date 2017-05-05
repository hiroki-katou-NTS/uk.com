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
                                findAllEmployee: "basic/organization/employment/findallemployments"
                            };
                            function findAllEmployee() {
                                return nts.uk.request.ajax("com", paths.findAllEmployee);
                            }
                            service.findAllEmployee = findAllEmployee;
                            var model;
                            (function (model) {
                                var EmploymentDto = (function () {
                                    function EmploymentDto() {
                                    }
                                    return EmploymentDto;
                                }());
                                model.EmploymentDto = EmploymentDto;
                            })(model = service.model || (service.model = {}));
                        })(service = h.service || (h.service = {}));
                    })(h = qpp021.h || (qpp021.h = {}));
                })(qpp021 = view.qpp021 || (view.qpp021 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp021.h.service.js.map