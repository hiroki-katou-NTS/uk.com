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
                    var b;
                    (function (b) {
                        var service;
                        (function (service) {
                            var paths = {
                                findAllSocialInsuranceOffice: "pr/insurance/social/findall/detail",
                                checkDuplicateCodeByImportData: "ctx/pr/core/insurance/labor/importser/checkDuplicateCode",
                                importData: "ctx/pr/core/insurance/labor/importser/importData"
                            };
                            function findAllSocialInsuranceOffice() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAllSocialInsuranceOffice)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findAllSocialInsuranceOffice = findAllSocialInsuranceOffice;
                            function checkDuplicateCodeByImportData(lstSocialInsuranceOfficeImportDto) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.checkDuplicateCodeByImportData, lstSocialInsuranceOfficeImportDto)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                    console.log("RES");
                                    console.log(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.checkDuplicateCodeByImportData = checkDuplicateCodeByImportData;
                            function importData(laborInsuranceOfficeImportDto) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.importData, laborInsuranceOfficeImportDto)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                    console.log("Import");
                                    console.log(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.importData = importData;
                            var model;
                            (function (model) {
                                var SocialInsuranceOfficeImportDto = (function () {
                                    function SocialInsuranceOfficeImportDto() {
                                    }
                                    return SocialInsuranceOfficeImportDto;
                                }());
                                model.SocialInsuranceOfficeImportDto = SocialInsuranceOfficeImportDto;
                                var LaborInsuranceOfficeImportOutDto = (function () {
                                    function LaborInsuranceOfficeImportOutDto() {
                                    }
                                    return LaborInsuranceOfficeImportOutDto;
                                }());
                                model.LaborInsuranceOfficeImportOutDto = LaborInsuranceOfficeImportOutDto;
                                var LaborInsuranceOfficeImportDto = (function () {
                                    function LaborInsuranceOfficeImportDto() {
                                        this.lstSocialInsuranceOfficeImport = [];
                                        this.checkUpdateDuplicateCode = 0;
                                    }
                                    return LaborInsuranceOfficeImportDto;
                                }());
                                model.LaborInsuranceOfficeImportDto = LaborInsuranceOfficeImportDto;
                                var LaborInsuranceOfficeCheckImportDto = (function () {
                                    function LaborInsuranceOfficeCheckImportDto() {
                                    }
                                    return LaborInsuranceOfficeCheckImportDto;
                                }());
                                model.LaborInsuranceOfficeCheckImportDto = LaborInsuranceOfficeCheckImportDto;
                            })(model = service.model || (service.model = {}));
                        })(service = b.service || (b.service = {}));
                    })(b = qmm010.b || (qmm010.b = {}));
                })(qmm010 = view.qmm010 || (view.qmm010 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
