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
                                addLaborInsuranceOffice: "ctx/pr/core/insurance/labor/add"
                            };
                            function findAllLaborInsuranceOffice() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAllLaborInsuranceOffice)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findAllLaborInsuranceOffice = findAllLaborInsuranceOffice;
                            function findLaborInsuranceOffice(code) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findLaborInsuranceOffice + "/" + code)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findLaborInsuranceOffice = findLaborInsuranceOffice;
                            function addLaborInsuranceOffice(laborInsuranceOffice, comanyCode) {
                                var dfd = $.Deferred();
                                var data = { laborInsuranceOffice: laborInsuranceOffice, comanyCode: comanyCode };
                                nts.uk.request.ajax(paths.addLaborInsuranceOffice, data)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.addLaborInsuranceOffice = addLaborInsuranceOffice;
                            var model;
                            (function (model) {
                                var LaborInsuranceOfficeDto = (function () {
                                    function LaborInsuranceOfficeDto(code, name, shortName, picName, picPosition, potalCode, prefecture, address1st, address2nd, kanaAddress1st, kanaAddress2nd, phoneNumber, citySign, officeMark, officeNoA, officeNoB, officeNoC, memo) {
                                        this.code = code;
                                        this.name = name;
                                        this.shortName = shortName;
                                        this.picName = picName;
                                        this.picPosition = picPosition;
                                        this.potalCode = potalCode;
                                        this.prefecture = prefecture;
                                        this.address1st = address1st;
                                        this.address2nd = address2nd;
                                        this.kanaAddress1st = kanaAddress1st;
                                        this.kanaAddress2nd = kanaAddress2nd;
                                        this.phoneNumber = phoneNumber;
                                        this.citySign = citySign;
                                        this.officeMark = officeMark;
                                        this.officeNoA = officeNoA;
                                        this.officeNoB = officeNoB;
                                        this.officeNoC = officeNoC;
                                        this.memo = memo;
                                    }
                                    return LaborInsuranceOfficeDto;
                                }());
                                model.LaborInsuranceOfficeDto = LaborInsuranceOfficeDto;
                                var LaborInsuranceOfficeInDto = (function () {
                                    function LaborInsuranceOfficeInDto(laborInsuranceOffice) {
                                        this.code = laborInsuranceOffice.code;
                                        this.name = laborInsuranceOffice.name;
                                    }
                                    return LaborInsuranceOfficeInDto;
                                }());
                                model.LaborInsuranceOfficeInDto = LaborInsuranceOfficeInDto;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm010.a || (qmm010.a = {}));
                })(qmm010 = view.qmm010 || (view.qmm010 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
