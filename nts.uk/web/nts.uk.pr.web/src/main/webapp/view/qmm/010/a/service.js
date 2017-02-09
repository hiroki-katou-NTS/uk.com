var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm010;
                (function (qmm010) {
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var paths = {
                                findAllLaborInsuranceOffice: "ctx/pr/core/insurance/labor/findall",
                                findLaborInsuranceOffice: "ctx/pr/core/insurance/labor/findLaborInsuranceOffice",
                                addLaborInsuranceOffice: "ctx/pr/core/insurance/labor/add",
                                updateLaborInsuranceOffice: "ctx/pr/core/insurance/labor/update",
                                deleteLaborInsuranceOffice: "ctx/pr/core/insurance/labor/delete",
                            };
                            function findAllLaborInsuranceOffice(companyCode) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAllLaborInsuranceOffice + "/" + companyCode)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findAllLaborInsuranceOffice = findAllLaborInsuranceOffice;
                            function findLaborInsuranceOffice(laborInsuranceOfficeFindInDto) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findLaborInsuranceOffice, laborInsuranceOfficeFindInDto)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findLaborInsuranceOffice = findLaborInsuranceOffice;
                            function addLaborInsuranceOffice(laborInsuranceOffice, companyCode) {
                                var dfd = $.Deferred();
                                var data = { laborInsuranceOffice: laborInsuranceOffice, companyCode: companyCode };
                                nts.uk.request.ajax(paths.addLaborInsuranceOffice, data)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                            }
                            service.addLaborInsuranceOffice = addLaborInsuranceOffice;
                            function deleteLaborInsuranceOffice(code, companyCode) {
                                var dfd = $.Deferred();
                                var data = { companyCode: companyCode, code: code };
                                nts.uk.request.ajax(paths.deleteLaborInsuranceOffice, data)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                            }
                            service.deleteLaborInsuranceOffice = deleteLaborInsuranceOffice;
                            var model;
                            (function (model) {
                                var LaborInsuranceOfficeDto = (function () {
                                    function LaborInsuranceOfficeDto() {
                                    }
                                    return LaborInsuranceOfficeDto;
                                }());
                                model.LaborInsuranceOfficeDto = LaborInsuranceOfficeDto;
                                var LaborInsuranceOfficeFindOutDto = (function () {
                                    function LaborInsuranceOfficeFindOutDto() {
                                    }
                                    return LaborInsuranceOfficeFindOutDto;
                                }());
                                model.LaborInsuranceOfficeFindOutDto = LaborInsuranceOfficeFindOutDto;
                                var LaborInsuranceOfficeFindInDto = (function () {
                                    function LaborInsuranceOfficeFindInDto() {
                                    }
                                    return LaborInsuranceOfficeFindInDto;
                                }());
                                model.LaborInsuranceOfficeFindInDto = LaborInsuranceOfficeFindInDto;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm010.a || (qmm010.a = {}));
                })(qmm010 = view.qmm010 || (view.qmm010 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
