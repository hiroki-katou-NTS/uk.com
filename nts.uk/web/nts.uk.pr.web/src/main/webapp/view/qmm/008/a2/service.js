var __extends = (this && this.__extends) || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
};
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
                    var a2;
                    (function (a2) {
                        var service;
                        (function (service) {
                            var servicePath = {
                                getAllOfficeItem: "pr/insurance/social/findall",
                                getAllHistoryOfOffice: "pr/insurance/social/history",
                                getAllPensionOfficeAndHistory: "ctx/pr/core/insurance/social/pensionrate/findAllHistory",
                                getPensionItemDetail: "ctx/pr/core/insurance/social/pensionrate/find",
                                registerPensionRate: "ctx/pr/core/insurance/social/pensionrate/create",
                                updatePensionRate: "ctx/pr/core/insurance/social/pensionrate/update",
                                removePensionRate: "ctx/pr/core/insurance/social/pensionrate/remove",
                                getAllRoundingItem: "pr/insurance/social/find/rounding"
                            };
                            var Service = (function (_super) {
                                __extends(Service, _super);
                                function Service(path) {
                                    _super.call(this, path);
                                }
                                Service.prototype.findHistoryByUuid = function (id) {
                                    return nts.uk.request.ajax(servicePath.getPensionItemDetail + "/" + id);
                                };
                                return Service;
                            }(view.base.simplehistory.service.BaseService));
                            service.Service = Service;
                            service.instance = new Service({
                                historyMasterPath: 'ctx/pr/core/insurance/social/pensionrate/masterhistory',
                                createHisotyPath: 'ctx/pr/core/insurance/social/pensionrate/history/create',
                                deleteHistoryPath: 'ctx/pr/core/insurance/social/pensionrate/history/delete',
                                updateHistoryStartPath: 'ctx/pr/core/insurance/social/pensionrate/history/update/start'
                            });
                            function findInsuranceOffice(key) {
                                var dfd = $.Deferred();
                                var findPath = servicePath.getAllOfficeItem + ((key != null && key != '') ? ('?key=' + key) : '');
                                nts.uk.request.ajax(findPath).done(function (data) {
                                    dfd.resolve(data);
                                });
                                return dfd.promise();
                            }
                            service.findInsuranceOffice = findInsuranceOffice;
                            function findAllRounding() {
                                var dfd = $.Deferred();
                                var roundingList = [
                                    new model.finder.Enum('0', '切り上げ'),
                                    new model.finder.Enum('1', '切捨て'),
                                    new model.finder.Enum('2', '四捨五入'),
                                    new model.finder.Enum('3', '五捨五超入'),
                                    new model.finder.Enum('4', '五捨六入')
                                ];
                                dfd.resolve(roundingList);
                                return dfd.promise();
                            }
                            service.findAllRounding = findAllRounding;
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
                            function getAllPensionOfficeItem() {
                                var dfd = $.Deferred();
                                var findPath = servicePath.getAllPensionOfficeAndHistory;
                                nts.uk.request.ajax(findPath).done(function (data) {
                                    var returnData = data;
                                    dfd.resolve(returnData);
                                });
                                return dfd.promise();
                            }
                            service.getAllPensionOfficeItem = getAllPensionOfficeItem;
                            function registerPensionRate(data) {
                                return nts.uk.request.ajax(servicePath.registerPensionRate, data);
                            }
                            service.registerPensionRate = registerPensionRate;
                            function updatePensionRate(data) {
                                return nts.uk.request.ajax(servicePath.updatePensionRate, data);
                            }
                            service.updatePensionRate = updatePensionRate;
                            function removePensionRate(historyId) {
                                var data = { historyId: historyId };
                                return nts.uk.request.ajax(servicePath.removePensionRate, data);
                            }
                            service.removePensionRate = removePensionRate;
                            var model;
                            (function (model) {
                                ;
                                ;
                                var finder;
                                (function (finder) {
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
                        })(service = a2.service || (a2.service = {}));
                    })(a2 = qmm008.a2 || (qmm008.a2 = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=service.js.map