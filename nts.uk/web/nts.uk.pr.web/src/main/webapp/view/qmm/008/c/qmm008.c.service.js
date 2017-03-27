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
                    var c;
                    (function (c) {
                        var service;
                        (function (service) {
                            // Service paths.
                            var servicePath = {
                                getAllOfficeItem: "pr/insurance/social/findall",
                                getAllHistoryOfOffice: "pr/insurance/social/history",
                                //get all heal insurance for get history
                                getAllPensionOfficeAndHistory: "ctx/pr/core/insurance/social/pensionrate/findAllHistory",
                                //get health and pension data 
                                getPensionItemDetail: "ctx/pr/core/insurance/social/pensionrate/find",
                                //register+ update pension
                                registerPensionRate: "ctx/pr/core/insurance/social/pensionrate/create",
                                updatePensionRate: "ctx/pr/core/insurance/social/pensionrate/update",
                                removePensionRate: "ctx/pr/core/insurance/social/pensionrate/remove",
                                getAllRoundingItem: "pr/insurance/social/find/rounding"
                            };
                            /**
                             * Normal service.
                             */
                            var Service = (function (_super) {
                                __extends(Service, _super);
                                function Service(path) {
                                    _super.call(this, path);
                                }
                                /**
                                 * Find history by id.
                                 */
                                Service.prototype.findHistoryByUuid = function (id) {
                                    return nts.uk.request.ajax(servicePath.getPensionItemDetail + "/" + id);
                                };
                                return Service;
                            }(view.base.simplehistory.service.BaseService));
                            service.Service = Service;
                            /**
                             * Service intance.
                             */
                            service.instance = new Service({
                                historyMasterPath: 'ctx/pr/core/insurance/social/pensionrate/masterhistory',
                                createHisotyPath: 'ctx/pr/core/insurance/social/pensionrate/history/create',
                                deleteHistoryPath: 'ctx/pr/core/insurance/social/pensionrate/history/delete',
                                updateHistoryStartPath: 'ctx/pr/core/insurance/social/pensionrate/history/update/start'
                            });
                            ////////////////////////
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
                                ;
                                ;
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
                        })(service = c.service || (c.service = {}));
                    })(c = qmm008.c || (qmm008.c = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
