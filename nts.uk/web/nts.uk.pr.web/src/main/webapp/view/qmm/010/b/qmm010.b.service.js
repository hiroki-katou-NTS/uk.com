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
                                checkDuplicateCodeByImportData: "ctx/pr/core/insurance/labor/importser/checkDuplicateCode",
                                importData: "ctx/pr/core/insurance/labor/importser/importData"
                            };
                            //Function connnection service check Duplicate Code By ImportData
                            function checkDuplicateCodeByImportData(socialInsuranceOfficeImportDto) {
                                //set up data respone
                                var dfd = $.Deferred();
                                //call server service
                                nts.uk.request.ajax(paths.checkDuplicateCodeByImportData, socialInsuranceOfficeImportDto)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.checkDuplicateCodeByImportData = checkDuplicateCodeByImportData;
                            //Function import data
                            function importData(laborInsuranceOfficeImportDto) {
                                //set up data respone
                                var dfd = $.Deferred();
                                //call server service
                                nts.uk.request.ajax(paths.importData, laborInsuranceOfficeImportDto)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.importData = importData;
                            var model;
                            (function (model) {
                                //Import SocialInsuranceOffice =>  SocialInsuranceOfficeInDto
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
                                        this.socialInsuranceOfficeImport = new SocialInsuranceOfficeImportDto();
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
//# sourceMappingURL=qmm010.b.service.js.map