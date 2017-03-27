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
                            // Service paths.
                            var servicePath = {
                                getAllOfficeItem: "pr/insurance/social/findall",
                                getAllHistoryOfOffice: "pr/insurance/social/history",
                                //get all heal insurance for get history
                                getAllHealthOfficeAndHistory: "ctx/pr/core/insurance/social/healthrate/findAllHistory",
                                getAllPensionOfficeAndHistory: "ctx/pr/core/insurance/social/pensionrate/findAllHistory",
                                //get health and pension data 
                                getHealthInsuranceItemDetail: "ctx/pr/core/insurance/social/healthrate/find",
                                getPensionItemDetail: "ctx/pr/core/insurance/social/pensionrate/find",
                                //register+ update health
                                registerHealthRate: "ctx/pr/core/insurance/social/healthrate/create",
                                updateHealthRate: "ctx/pr/core/insurance/social/healthrate/update",
                                removeHealthRate: "ctx/pr/core/insurance/social/healthrate/remove",
                                //register+ update pension
                                registerPensionRate: "ctx/pr/core/insurance/social/pensionrate/create",
                                updatePensionRate: "ctx/pr/core/insurance/social/pensionrate/update",
                                removePensionRate: "ctx/pr/core/insurance/social/pensionrate/remove",
                                getAllRoundingItem: "pr/insurance/social/find/rounding"
                            };
                            /**
                             * Function is used to load all InsuranceOfficeItem by key.
                             */
                            function findInsuranceOffice(key) {
                                // Init new dfd.
                                var dfd = $.Deferred();
                                var findPath = servicePath.getAllOfficeItem + ((key != null && key != '') ? ('?key=' + key) : '');
                                // Call ajax.
                                nts.uk.request.ajax(findPath).done(function (data) {
                                    // Convert json to model here.
                                    // Resolve.
                                    dfd.resolve(data);
                                });
                                // Ret promise.
                                return dfd.promise();
                            }
                            service.findInsuranceOffice = findInsuranceOffice;
                            /**
                             * Function is used to load all RoundingOption.
                             */
                            function findAllRounding() {
                                // Init new dfd.
                                var dfd = $.Deferred();
                                // Call ajax.
                                //            nts.uk.request.ajax(findPath).done(function(data) {
                                // Convert json to model here.
                                var roundingList = [
                                    new model.finder.Enum('0', '切り上げ'),
                                    new model.finder.Enum('1', '切捨て'),
                                    new model.finder.Enum('2', '四捨五入'),
                                    new model.finder.Enum('3', '五捨五超入'),
                                    new model.finder.Enum('4', '五捨六入')
                                ];
                                // Resolve.
                                dfd.resolve(roundingList);
                                //            });
                                // Ret promise.
                                return dfd.promise();
                            }
                            service.findAllRounding = findAllRounding;
                            /**
                             * Function is used to load health data of Office by office code.
                             */
                            function getHealthInsuranceItemDetail(code) {
                                // Init new dfd.
                                var dfd = $.Deferred();
                                var findPath = servicePath.getHealthInsuranceItemDetail + "/" + code;
                                // Call ajax.
                                nts.uk.request.ajax(findPath).done(function (data) {
                                    // Convert json to model here.
                                    var healthInsuranceRateDetailData = data;
                                    // Resolve.
                                    dfd.resolve(healthInsuranceRateDetailData);
                                });
                                // Ret promise.
                                return dfd.promise();
                            }
                            service.getHealthInsuranceItemDetail = getHealthInsuranceItemDetail;
                            /**
                            * Function is used to load health data of Office by office code.
                            */
                            function getAllHealthOfficeItem() {
                                // Init new dfd.
                                var dfd = $.Deferred();
                                var findPath = servicePath.getAllHealthOfficeAndHistory;
                                // Call ajax.
                                nts.uk.request.ajax(findPath).done(function (data) {
                                    // Convert json to model here.
                                    var returnData = data;
                                    // Resolve.
                                    dfd.resolve(returnData);
                                });
                                // Ret promise.
                                return dfd.promise();
                            }
                            service.getAllHealthOfficeItem = getAllHealthOfficeItem;
                            /**
                            * Function is used to load pension  data of Office by office code.
                            */
                            function getPensionItemDetail(code) {
                                // Init new dfd.
                                var dfd = $.Deferred();
                                var findPath = servicePath.getPensionItemDetail + "/" + code;
                                // Call ajax.
                                nts.uk.request.ajax(findPath).done(function (data) {
                                    // Convert json to model here.
                                    var pensionRateDetailData = data;
                                    // Resolve.
                                    dfd.resolve(pensionRateDetailData);
                                });
                                // Ret promise.
                                return dfd.promise();
                            }
                            service.getPensionItemDetail = getPensionItemDetail;
                            /**
                            * Function is used to load health data of Office by office code.
                            */
                            function getAllPensionOfficeItem() {
                                // Init new dfd.
                                var dfd = $.Deferred();
                                var findPath = servicePath.getAllPensionOfficeAndHistory;
                                // Call ajax.
                                nts.uk.request.ajax(findPath).done(function (data) {
                                    // Convert json to model here.
                                    var returnData = data;
                                    // Resolve.
                                    dfd.resolve(returnData);
                                });
                                // Ret promise.
                                return dfd.promise();
                            }
                            service.getAllPensionOfficeItem = getAllPensionOfficeItem;
                            /**
                            * Function is used to save new Health insurance rate with office code and history id.
                            */
                            function registerHealthRate(data) {
                                return nts.uk.request.ajax(servicePath.registerHealthRate, data);
                            }
                            service.registerHealthRate = registerHealthRate;
                            /**
                            * Function is used to update new Health insurance rate with office code and history id.
                            */
                            function updateHealthRate(data) {
                                return nts.uk.request.ajax(servicePath.updateHealthRate, data);
                            }
                            service.updateHealthRate = updateHealthRate;
                            /**
                            * Function is used to update new Health insurance rate with office code and history id.
                            */
                            function removeHealthRate(historyId) {
                                var data = { historyId: historyId };
                                return nts.uk.request.ajax(servicePath.removeHealthRate, data);
                            }
                            service.removeHealthRate = removeHealthRate;
                            /**
                            * Function is used to save new Pension rate with office code and history id.
                            */
                            function registerPensionRate(data) {
                                return nts.uk.request.ajax(servicePath.registerPensionRate, data);
                            }
                            service.registerPensionRate = registerPensionRate;
                            /**
                            * Function is used to update new Pension rate with office code and history id.
                            */
                            function updatePensionRate(data) {
                                return nts.uk.request.ajax(servicePath.updatePensionRate, data);
                            }
                            service.updatePensionRate = updatePensionRate;
                            /**
                            * Function is used to update new Health insurance rate with office code and history id.
                            */
                            function removePensionRate(historyId) {
                                var data = { historyId: historyId };
                                return nts.uk.request.ajax(servicePath.removePensionRate, data);
                            }
                            service.removePensionRate = removePensionRate;
                            /**
                            * Model namespace.
                            */
                            var model;
                            (function (model) {
                                var finder;
                                (function (finder) {
                                    //office DTO
                                    var InsuranceOfficeItemDto = (function () {
                                        function InsuranceOfficeItemDto(id, name, code, childs, codeName) {
                                            this.id = id;
                                            this.name = name;
                                            this.code = code;
                                            this.childs = childs;
                                            this.codeName = codeName;
                                        }
                                        return InsuranceOfficeItemDto;
                                    }());
                                    finder.InsuranceOfficeItemDto = InsuranceOfficeItemDto;
                                    //Pension DTO
                                    var PensionRateDto = (function () {
                                        function PensionRateDto(historyId, companyCode, officeCode, startMonth, endMonth, autoCalculate, fundInputApply, premiumRateItems, fundRateItems, roundingMethods, maxAmount, childContributionRate) {
                                            this.historyId = historyId;
                                            this.companyCode = companyCode;
                                            this.officeCode = officeCode;
                                            this.startMonth = startMonth;
                                            this.endMonth = endMonth;
                                            this.autoCalculate = autoCalculate;
                                            this.fundInputApply = fundInputApply;
                                            this.premiumRateItems = premiumRateItems;
                                            this.fundRateItems = fundRateItems;
                                            this.roundingMethods = roundingMethods;
                                            this.maxAmount = maxAmount;
                                            this.childContributionRate = childContributionRate;
                                        }
                                        return PensionRateDto;
                                    }());
                                    finder.PensionRateDto = PensionRateDto;
                                    var PensionRateItemDto = (function () {
                                        function PensionRateItemDto(payType, genderType, personalRate, companyRate) {
                                            this.payType = payType;
                                            this.genderType = genderType;
                                            this.personalRate = personalRate;
                                            this.companyRate = companyRate;
                                        }
                                        return PensionRateItemDto;
                                    }());
                                    finder.PensionRateItemDto = PensionRateItemDto;
                                    var FundRateItemDto = (function () {
                                        function FundRateItemDto(payType, genderType, burdenChargePersonalRate, burdenChargeCompanyRate, exemptionChargePersonalRate, exemptionChargeCompanyRate) {
                                            this.payType = payType;
                                            this.genderType = genderType;
                                            this.burdenChargePersonalRate = burdenChargePersonalRate;
                                            this.burdenChargeCompanyRate = burdenChargeCompanyRate;
                                            this.exemptionChargePersonalRate = exemptionChargePersonalRate;
                                            this.exemptionChargeCompanyRate = exemptionChargeCompanyRate;
                                        }
                                        return FundRateItemDto;
                                    }());
                                    finder.FundRateItemDto = FundRateItemDto;
                                    //health DTO
                                    var HealthInsuranceRateDto = (function () {
                                        function HealthInsuranceRateDto(historyId, companyCode, officeCode, startMonth, endMonth, autoCalculate, rateItems, roundingMethods, maxAmount) {
                                            this.historyId = historyId;
                                            this.companyCode = companyCode;
                                            this.officeCode = officeCode;
                                            this.startMonth = startMonth;
                                            this.endMonth = endMonth;
                                            this.autoCalculate = autoCalculate;
                                            this.rateItems = rateItems;
                                            this.roundingMethods = roundingMethods;
                                            this.maxAmount = maxAmount;
                                        }
                                        return HealthInsuranceRateDto;
                                    }());
                                    finder.HealthInsuranceRateDto = HealthInsuranceRateDto;
                                    var HealthInsuranceRateItemDto = (function () {
                                        function HealthInsuranceRateItemDto(payType, insuranceType, chargeRate) {
                                            this.chargeRate = chargeRate;
                                            this.payType = payType;
                                            this.insuranceType = insuranceType;
                                        }
                                        return HealthInsuranceRateItemDto;
                                    }());
                                    finder.HealthInsuranceRateItemDto = HealthInsuranceRateItemDto;
                                    var ChargeRateItemDto = (function () {
                                        function ChargeRateItemDto(companyRate, personalRate) {
                                            this.companyRate = companyRate;
                                            this.personalRate = personalRate;
                                        }
                                        return ChargeRateItemDto;
                                    }());
                                    finder.ChargeRateItemDto = ChargeRateItemDto;
                                    var OfficeItemDto = (function () {
                                        function OfficeItemDto() {
                                        }
                                        return OfficeItemDto;
                                    }());
                                    finder.OfficeItemDto = OfficeItemDto;
                                    var HistoryDto = (function () {
                                        function HistoryDto() {
                                        }
                                        return HistoryDto;
                                    }());
                                    finder.HistoryDto = HistoryDto;
                                    //common class for health and pension
                                    var RoundingDto = (function () {
                                        function RoundingDto(payType, roundAtrs) {
                                            this.payType = payType;
                                            this.roundAtrs = roundAtrs;
                                        }
                                        return RoundingDto;
                                    }());
                                    finder.RoundingDto = RoundingDto;
                                    var RoundingItemDto = (function () {
                                        function RoundingItemDto(personalRoundAtr, companyRoundAtr) {
                                            this.personalRoundAtr = personalRoundAtr;
                                            this.companyRoundAtr = companyRoundAtr;
                                        }
                                        return RoundingItemDto;
                                    }());
                                    finder.RoundingItemDto = RoundingItemDto;
                                    var AddNewHistoryDto = (function () {
                                        function AddNewHistoryDto() {
                                        }
                                        return AddNewHistoryDto;
                                    }());
                                    finder.AddNewHistoryDto = AddNewHistoryDto;
                                    var Enum = (function () {
                                        function Enum(code, name) {
                                            this.code = code;
                                            this.name = name;
                                        }
                                        return Enum;
                                    }());
                                    finder.Enum = Enum;
                                })(finder = model.finder || (model.finder = {}));
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm008.a || (qmm008.a = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
