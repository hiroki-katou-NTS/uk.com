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
                    var b;
                    (function (b) {
                        var service;
                        (function (service) {
                            // Service paths.
                            var servicePath = {
                                getAllOfficeItem: "pr/insurance/social/findall",
                                getAllHistoryOfOffice: "pr/insurance/social/history",
                                //get all heal insurance for get history
                                getAllHealthOfficeAndHistory: "ctx/pr/core/insurance/social/healthrate/findAllHistory",
                                getHealthInsuranceItemDetail: "ctx/pr/core/insurance/social/healthrate/find",
                                //register+ update health
                                registerHealthRate: "ctx/pr/core/insurance/social/healthrate/create",
                                updateHealthRate: "ctx/pr/core/insurance/social/healthrate/update",
                                removeHealthRate: "ctx/pr/core/insurance/social/healthrate/remove",
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
                                    return nts.uk.request.ajax(servicePath.getHealthInsuranceItemDetail + "/" + id);
                                };
                                return Service;
                            }(view.base.simplehistory.service.BaseService));
                            service.Service = Service;
                            /**
                             * Service intance.
                             */
                            service.instance = new Service({
                                historyMasterPath: 'ctx/pr/core/insurance/social/healthrate/masterhistory',
                                createHisotyPath: 'ctx/pr/core/insurance/social/healthrate/history/create',
                                deleteHistoryPath: 'ctx/pr/core/insurance/social/healthrate/history/delete',
                                updateHistoryStartPath: 'ctx/pr/core/insurance/social/healthrate/history/update/start'
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
                        })(service = b.service || (b.service = {}));
                    })(b = qmm008.b || (qmm008.b = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm008.b.service.js.map