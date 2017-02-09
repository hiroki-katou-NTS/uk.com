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
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var servicePath = {
                                getAllOfficeItem: "pr/insurance/social/findall",
                                getAllHistoryOfOffice: "pr/insurance/social/history",
                                getHealthInsuranceItemDetail: "ctx/pr/core/insurance/social/healthrate/findHealthInsuranceRate",
                                getPensionItemDetail: "ctx/pr/core/insurance/social/pensionrate/findPensionRate",
                                getAllRoundingItem: "list/rounding"
                            };
                            function findInsuranceOffice(key) {
                                var dfd = $.Deferred();
                                var findPath = servicePath.getAllOfficeItem + ((key != null && key != '') ? ('?key=' + key) : '');
                                nts.uk.request.ajax(findPath).done(function (data) {
                                    var OfficeItemList = convertToTreeList(data);
                                    dfd.resolve(OfficeItemList);
                                });
                                return dfd.promise();
                            }
                            service.findInsuranceOffice = findInsuranceOffice;
                            function convertToTreeList(data) {
                                var OfficeItemList = [];
                                data.forEach(function (item, index) {
                                    OfficeItemList.push(new model.finder.InsuranceOfficeItemDto('id' + index, item.name, item.code, []));
                                });
                                return OfficeItemList;
                            }
                            function findHistoryByOfficeCode(code) {
                                var dfd = $.Deferred();
                                var findPath = servicePath.getAllHistoryOfOffice + "/" + code;
                                nts.uk.request.ajax(findPath).done(function (data) {
                                    dfd.resolve(data);
                                });
                                return dfd.promise();
                            }
                            service.findHistoryByOfficeCode = findHistoryByOfficeCode;
                            function findAllRounding() {
                                var dfd = $.Deferred();
                                var findPath = servicePath.getAllRoundingItem;
                                var data = null;
                                var roundingList = [];
                                dfd.resolve(roundingList);
                                return dfd.promise();
                            }
                            service.findAllRounding = findAllRounding;
                            function getHealthInsuranceItemDetail(code) {
                                var dfd = $.Deferred();
                                var findPath = servicePath.getHealthInsuranceItemDetail + "/" + code;
                                nts.uk.request.ajax(findPath).done(function (data) {
                                    var healthInsuranceRateDetailData = data;
                                    dfd.resolve(healthInsuranceRateDetailData);
                                });
                                return dfd.promise();
                            }
                            service.getHealthInsuranceItemDetail = getHealthInsuranceItemDetail;
                            function getPensionItemDetail(code) {
                                var dfd = $.Deferred();
                                var findPath = servicePath.getPensionItemDetail + "/" + code;
                                nts.uk.request.ajax(findPath).done(function (data) {
                                    var pensionRateDetailData = data;
                                    dfd.resolve(pensionRateDetailData);
                                });
                                return dfd.promise();
                            }
                            service.getPensionItemDetail = getPensionItemDetail;
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
                                    var HistoryItemDto = (function () {
                                        function HistoryItemDto(id, name, code, childs) {
                                            this.id = id;
                                            this.name = name;
                                            this.code = code;
                                            this.childs = childs;
                                        }
                                        return HistoryItemDto;
                                    }());
                                    finder.HistoryItemDto = HistoryItemDto;
                                    var InsuranceOfficeItemDto = (function () {
                                        function InsuranceOfficeItemDto(id, name, code, childs) {
                                            this.id = id;
                                            this.name = name;
                                            this.code = code;
                                            this.childs = childs;
                                            if (childs.length == 0) {
                                                this.codeName = name;
                                            }
                                            else {
                                                this.codeName = code + "\u00A0" + "\u00A0" + "\u00A0" + name;
                                            }
                                        }
                                        return InsuranceOfficeItemDto;
                                    }());
                                    finder.InsuranceOfficeItemDto = InsuranceOfficeItemDto;
                                    var PensionRateDto = (function () {
                                        function PensionRateDto(historyId, companyCode, officeCode, applyRange, autoCalculate, funInputOption, rateItems, fundRateItems, roundingMethods, maxAmount, officeRate) {
                                            this.historyId = historyId;
                                            this.companyCode = companyCode;
                                            this.officeCode = officeCode;
                                            this.applyRange = applyRange;
                                            this.autoCalculate = autoCalculate;
                                            this.fundInputOption = funInputOption;
                                            this.rateItems = rateItems;
                                            this.fundRateItems = fundRateItems;
                                            this.roundingMethods = roundingMethods;
                                            this.maxAmount = maxAmount;
                                            this.officeRate = officeRate;
                                        }
                                        return PensionRateDto;
                                    }());
                                    finder.PensionRateDto = PensionRateDto;
                                    var PensionRateItemDto = (function () {
                                        function PensionRateItemDto(chargeRate, groupType, payType, genderType) {
                                            this.chargeRate = chargeRate;
                                            this.groupType = groupType;
                                            this.payType = payType;
                                            this.genderType = genderType;
                                        }
                                        return PensionRateItemDto;
                                    }());
                                    finder.PensionRateItemDto = PensionRateItemDto;
                                    var FundRateItemDto = (function () {
                                        function FundRateItemDto(chargeRate, groupType, chargeType, genderType, payType) {
                                            this.chargeRate = chargeRate;
                                            this.groupType = groupType;
                                            this.chargeType = chargeType;
                                            this.genderType = genderType;
                                            this.payType = payType;
                                        }
                                        return FundRateItemDto;
                                    }());
                                    finder.FundRateItemDto = FundRateItemDto;
                                    var HealthInsuranceRateDto = (function () {
                                        function HealthInsuranceRateDto(historyId, companyCode, officeCode, applyRange, autoCalculate, rateItems, roundingMethods, maxAmount) {
                                            this.historyId = historyId;
                                            this.companyCode = companyCode;
                                            this.officeCode = officeCode;
                                            this.applyRange = applyRange;
                                            this.autoCalculate = autoCalculate;
                                            this.rateItems = rateItems;
                                            this.roundingMethods = roundingMethods;
                                            this.maxAmount = maxAmount;
                                        }
                                        return HealthInsuranceRateDto;
                                    }());
                                    finder.HealthInsuranceRateDto = HealthInsuranceRateDto;
                                    var HealthInsuranceRateItemDto = (function () {
                                        function HealthInsuranceRateItemDto(chargeRate, payType, healthInsuranceType) {
                                            this.chargeRate = chargeRate;
                                            this.payType = payType;
                                            this.healthInsuranceType = healthInsuranceType;
                                        }
                                        return HealthInsuranceRateItemDto;
                                    }());
                                    finder.HealthInsuranceRateItemDto = HealthInsuranceRateItemDto;
                                    var chargeRateItemDto = (function () {
                                        function chargeRateItemDto(companyRate, personalRate) {
                                            this.companyRate = companyRate;
                                            this.personalRate = personalRate;
                                        }
                                        return chargeRateItemDto;
                                    }());
                                    finder.chargeRateItemDto = chargeRateItemDto;
                                    var RoundingDto = (function () {
                                        function RoundingDto(payType, roundAtrs) {
                                            this.payType = payType;
                                            this.roundAtrs = roundAtrs;
                                        }
                                        return RoundingDto;
                                    }());
                                    finder.RoundingDto = RoundingDto;
                                    var HealthInsuranceAvgearnDto = (function () {
                                        function HealthInsuranceAvgearnDto() {
                                        }
                                        return HealthInsuranceAvgearnDto;
                                    }());
                                    finder.HealthInsuranceAvgearnDto = HealthInsuranceAvgearnDto;
                                    var RoundingItemDto = (function () {
                                        function RoundingItemDto() {
                                        }
                                        return RoundingItemDto;
                                    }());
                                    finder.RoundingItemDto = RoundingItemDto;
                                    var HealthInsuranceAvgearnValueDto = (function () {
                                        function HealthInsuranceAvgearnValueDto() {
                                        }
                                        return HealthInsuranceAvgearnValueDto;
                                    }());
                                    finder.HealthInsuranceAvgearnValueDto = HealthInsuranceAvgearnValueDto;
                                })(finder = model.finder || (model.finder = {}));
                                (function (PaymentType) {
                                    PaymentType[PaymentType["Salary"] = 0] = "Salary";
                                    PaymentType[PaymentType["Bonus"] = 1] = "Bonus";
                                })(model.PaymentType || (model.PaymentType = {}));
                                var PaymentType = model.PaymentType;
                                (function (HealthInsuranceType) {
                                    HealthInsuranceType[HealthInsuranceType["General"] = 0] = "General";
                                    HealthInsuranceType[HealthInsuranceType["Nursing"] = 1] = "Nursing";
                                    HealthInsuranceType[HealthInsuranceType["Basic"] = 2] = "Basic";
                                    HealthInsuranceType[HealthInsuranceType["Special"] = 3] = "Special";
                                })(model.HealthInsuranceType || (model.HealthInsuranceType = {}));
                                var HealthInsuranceType = model.HealthInsuranceType;
                                (function (InsuranceGender) {
                                    InsuranceGender[InsuranceGender["Male"] = 0] = "Male";
                                    InsuranceGender[InsuranceGender["Female"] = 1] = "Female";
                                    InsuranceGender[InsuranceGender["Unknow"] = 2] = "Unknow";
                                })(model.InsuranceGender || (model.InsuranceGender = {}));
                                var InsuranceGender = model.InsuranceGender;
                                (function (ChargeType) {
                                    ChargeType[ChargeType["Burden"] = 0] = "Burden";
                                    ChargeType[ChargeType["Exemption"] = 1] = "Exemption";
                                })(model.ChargeType || (model.ChargeType = {}));
                                var ChargeType = model.ChargeType;
                                (function (GroupType) {
                                    GroupType[GroupType["Personal"] = 0] = "Personal";
                                    GroupType[GroupType["Company"] = 1] = "Company";
                                })(model.GroupType || (model.GroupType = {}));
                                var GroupType = model.GroupType;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm008.a || (qmm008.a = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
