var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm008;
                (function (qmm008) {
                    var e;
                    (function (e) {
                        var service;
                        (function (service) {
                            var servicePath = {
                                getOfficeDetailData: "pr/insurance/social/find",
                                updateOffice: "pr/insurance/social/update",
                                regiterOffice: "pr/insurance/social/create",
                                removeOffice: "pr/insurance/social/remove"
                            };
                            function getOfficeItemDetail(code) {
                                var dfd = $.Deferred();
                                var findPath = servicePath.getOfficeDetailData + "/" + code;
                                nts.uk.request.ajax(findPath).done(function (data) {
                                    dfd.resolve(data);
                                });
                                return dfd.promise();
                            }
                            service.getOfficeItemDetail = getOfficeItemDetail;
                            function register(data) {
                                return nts.uk.request.ajax(servicePath.regiterOffice, data);
                            }
                            service.register = register;
                            function update(data) {
                                return nts.uk.request.ajax(servicePath.updateOffice, data);
                            }
                            service.update = update;
                            function remove(officeCode) {
                                var data = { insuranceOfficeCode: officeCode };
                                return nts.uk.request.ajax(servicePath.removeOffice, data);
                            }
                            service.remove = remove;
                            function saveHistory(officeCode) {
                                var data = { insuranceOfficeCode: officeCode };
                                return nts.uk.request.ajax(servicePath.removeOffice, data);
                            }
                            service.saveHistory = saveHistory;
                            var model;
                            (function (model) {
                                var finder;
                                (function (finder) {
                                    var ChooseOption = (function () {
                                        function ChooseOption() {
                                        }
                                        return ChooseOption;
                                    }());
                                    finder.ChooseOption = ChooseOption;
                                    var OfficeItemDto = (function () {
                                        function OfficeItemDto(companyCode, code, name, shortName, picName, picPosition, potalCode, prefecture, address1st, address2nd, kanaAddress1st, kanaAddress2nd, phoneNumber, healthInsuOfficeRefCode1st, healthInsuOfficeRefCode2nd, pensionOfficeRefCode1st, pensionOfficeRefCode2nd, welfarePensionFundCode, officePensionFundCode, healthInsuCityCode, healthInsuOfficeSign, pensionCityCode, pensionOfficeSign, healthInsuOfficeCode, healthInsuAssoCode, memo) {
                                            this.companyCode = companyCode;
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
                                            this.healthInsuOfficeRefCode1st = healthInsuOfficeRefCode1st;
                                            this.healthInsuOfficeRefCode2nd = healthInsuOfficeRefCode2nd;
                                            this.pensionOfficeRefCode1st = pensionOfficeRefCode1st;
                                            this.pensionOfficeRefCode2nd = pensionOfficeRefCode2nd;
                                            this.welfarePensionFundCode = welfarePensionFundCode;
                                            this.officePensionFundCode = officePensionFundCode;
                                            this.healthInsuCityCode = healthInsuCityCode;
                                            this.healthInsuOfficeSign = healthInsuOfficeSign;
                                            this.pensionCityCode = pensionCityCode;
                                            this.pensionOfficeSign = pensionOfficeSign;
                                            this.healthInsuOfficeCode = healthInsuOfficeCode;
                                            this.healthInsuAssoCode = healthInsuAssoCode;
                                            this.memo = memo;
                                        }
                                        return OfficeItemDto;
                                    }());
                                    finder.OfficeItemDto = OfficeItemDto;
                                })(finder = model.finder || (model.finder = {}));
                            })(model = service.model || (service.model = {}));
                        })(service = e.service || (e.service = {}));
                    })(e = qmm008.e || (qmm008.e = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm008.e.service.js.map