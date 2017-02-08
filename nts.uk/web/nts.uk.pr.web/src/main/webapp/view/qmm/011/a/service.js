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
                var qmm011;
                (function (qmm011) {
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var paths = {
                                findAllHisotryUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/history/findall",
                                findHisotryUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/history/find",
                                detailHistoryUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/detailHistory",
                                addUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/add",
                                updateUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/update",
                                findAllHistoryAccidentInsuranceRate: "pr/insurance/labor/accidentrate/history/findall",
                                findHistoryAccidentInsuranceRate: "pr/insurance/labor/accidentrate/history/find",
                                detailHistoryAccidentInsuranceRate: "pr/insurance/labor/accidentrate/history/detail"
                            };
                            function addUnemployeeInsuranceRate(unemployeeInsuranceRateModel, historyUnemployeeInsuranceRateDto) {
                                var dfd = $.Deferred();
                                var data = {
                                    unemployeeInsuranceRate: service.convertUnemployeeInsuranceRateModelDTO(unemployeeInsuranceRateModel, historyUnemployeeInsuranceRateDto),
                                    companyCode: historyUnemployeeInsuranceRateDto.companyCode
                                };
                                nts.uk.request.ajax(paths.addUnemployeeInsuranceRate, data)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.addUnemployeeInsuranceRate = addUnemployeeInsuranceRate;
                            function updateUnemployeeInsuranceRate() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.updateUnemployeeInsuranceRate)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.updateUnemployeeInsuranceRate = updateUnemployeeInsuranceRate;
                            function findAllHisotryUnemployeeInsuranceRate() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAllHisotryUnemployeeInsuranceRate)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findAllHisotryUnemployeeInsuranceRate = findAllHisotryUnemployeeInsuranceRate;
                            function findHisotryUnemployeeInsuranceRate(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findHisotryUnemployeeInsuranceRate + "/" + historyId)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findHisotryUnemployeeInsuranceRate = findHisotryUnemployeeInsuranceRate;
                            function detailHistoryUnemployeeInsuranceRate(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.detailHistoryUnemployeeInsuranceRate + "/" + historyId)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.detailHistoryUnemployeeInsuranceRate = detailHistoryUnemployeeInsuranceRate;
                            function findAllHistoryAccidentInsuranceRate() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAllHistoryAccidentInsuranceRate)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findAllHistoryAccidentInsuranceRate = findAllHistoryAccidentInsuranceRate;
                            function findHistoryAccidentInsuranceRate(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findHistoryAccidentInsuranceRate + "/" + historyId)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findHistoryAccidentInsuranceRate = findHistoryAccidentInsuranceRate;
                            function detailHistoryAccidentInsuranceRate(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.detailHistoryAccidentInsuranceRate + "/" + historyId)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.detailHistoryAccidentInsuranceRate = detailHistoryAccidentInsuranceRate;
                            function convertUnemployeeInsuranceRateItemSettingModelDTO(unemployeeInsuranceRateItemSettingModel) {
                                var unemployeeInsuranceRateItemSettingDto;
                                unemployeeInsuranceRateItemSettingDto = new model.UnemployeeInsuranceRateItemSettingDto(unemployeeInsuranceRateItemSettingModel.roundAtr(), unemployeeInsuranceRateItemSettingModel.rate());
                                return unemployeeInsuranceRateItemSettingDto;
                            }
                            service.convertUnemployeeInsuranceRateItemSettingModelDTO = convertUnemployeeInsuranceRateItemSettingModelDTO;
                            function convertUnemployeeInsuranceRateItemModelDTO(careerGroup, unemployeeInsuranceRateItemModel) {
                                var unemployeeInsuranceRateItemDto;
                                unemployeeInsuranceRateItemDto = new model.UnemployeeInsuranceRateItemDto(careerGroup, service.convertUnemployeeInsuranceRateItemSettingModelDTO(unemployeeInsuranceRateItemModel.companySetting), service.convertUnemployeeInsuranceRateItemSettingModelDTO(unemployeeInsuranceRateItemModel.personalSetting));
                                return unemployeeInsuranceRateItemDto;
                            }
                            service.convertUnemployeeInsuranceRateItemModelDTO = convertUnemployeeInsuranceRateItemModelDTO;
                            function convertUnemployeeInsuranceRateModelDTO(unemployeeInsuranceRateModel, historyInsurance) {
                                var unemployeeInsuranceRateDto;
                                unemployeeInsuranceRateDto = new model.UnemployeeInsuranceRateDto();
                                unemployeeInsuranceRateDto.historyInsurance = historyInsurance;
                                unemployeeInsuranceRateDto.companyCode = historyInsurance.companyCode;
                                unemployeeInsuranceRateDto.rateItems = [];
                                unemployeeInsuranceRateDto.rateItems.push(service.convertUnemployeeInsuranceRateItemModelDTO(model.CareerGroupDto.Agroforestry, unemployeeInsuranceRateModel.unemployeeInsuranceRateItemAgroforestryModel));
                                unemployeeInsuranceRateDto.rateItems.push(service.convertUnemployeeInsuranceRateItemModelDTO(model.CareerGroupDto.Contruction, unemployeeInsuranceRateModel.unemployeeInsuranceRateItemContructionModel));
                                unemployeeInsuranceRateDto.rateItems.push(service.convertUnemployeeInsuranceRateItemModelDTO(model.CareerGroupDto.Other, unemployeeInsuranceRateModel.unemployeeInsuranceRateItemOtherModel));
                                return unemployeeInsuranceRateDto;
                            }
                            service.convertUnemployeeInsuranceRateModelDTO = convertUnemployeeInsuranceRateModelDTO;
                            var model;
                            (function (model) {
                                var YearMonth = (function () {
                                    function YearMonth(year, month) {
                                        this.year = year;
                                        this.month = month;
                                    }
                                    return YearMonth;
                                }());
                                model.YearMonth = YearMonth;
                                var MonthRange = (function () {
                                    function MonthRange(startMonth, endMonth) {
                                        this.startMonth = startMonth;
                                        this.endMonth = endMonth;
                                    }
                                    return MonthRange;
                                }());
                                model.MonthRange = MonthRange;
                                var RoundingMethodDto = (function () {
                                    function RoundingMethodDto(code, name) {
                                        this.code = code;
                                        this.name = name;
                                    }
                                    return RoundingMethodDto;
                                }());
                                model.RoundingMethodDto = RoundingMethodDto;
                                var UnemployeeInsuranceRateItemSettingDto = (function () {
                                    function UnemployeeInsuranceRateItemSettingDto(roundAtr, rate) {
                                        this.roundAtr = roundAtr;
                                        this.rate = rate;
                                    }
                                    return UnemployeeInsuranceRateItemSettingDto;
                                }());
                                model.UnemployeeInsuranceRateItemSettingDto = UnemployeeInsuranceRateItemSettingDto;
                                var UnemployeeInsuranceRateItemDto = (function () {
                                    function UnemployeeInsuranceRateItemDto(careerGroup, companySetting, personalSetting) {
                                        this.careerGroup = careerGroup;
                                        this.companySetting = companySetting;
                                        this.personalSetting = personalSetting;
                                    }
                                    return UnemployeeInsuranceRateItemDto;
                                }());
                                model.UnemployeeInsuranceRateItemDto = UnemployeeInsuranceRateItemDto;
                                var HistoryInsuranceDto = (function () {
                                    function HistoryInsuranceDto(historyId, monthRage, startMonthRage, endMonthRage) {
                                        this.historyId = historyId;
                                        this.monthRage = monthRage;
                                        this.startMonthRage = startMonthRage;
                                        this.endMonthRage = endMonthRage;
                                    }
                                    return HistoryInsuranceDto;
                                }());
                                model.HistoryInsuranceDto = HistoryInsuranceDto;
                                var HistoryUnemployeeInsuranceDto = (function (_super) {
                                    __extends(HistoryUnemployeeInsuranceDto, _super);
                                    function HistoryUnemployeeInsuranceDto() {
                                        _super.apply(this, arguments);
                                    }
                                    return HistoryUnemployeeInsuranceDto;
                                }(HistoryInsuranceDto));
                                model.HistoryUnemployeeInsuranceDto = HistoryUnemployeeInsuranceDto;
                                var UnemployeeInsuranceRateDto = (function () {
                                    function UnemployeeInsuranceRateDto() {
                                    }
                                    return UnemployeeInsuranceRateDto;
                                }());
                                model.UnemployeeInsuranceRateDto = UnemployeeInsuranceRateDto;
                                var HistoryAccidentInsuranceDto = (function (_super) {
                                    __extends(HistoryAccidentInsuranceDto, _super);
                                    function HistoryAccidentInsuranceDto() {
                                        _super.apply(this, arguments);
                                    }
                                    return HistoryAccidentInsuranceDto;
                                }(HistoryInsuranceDto));
                                model.HistoryAccidentInsuranceDto = HistoryAccidentInsuranceDto;
                                var AccidentInsuranceRateDto = (function () {
                                    function AccidentInsuranceRateDto() {
                                    }
                                    return AccidentInsuranceRateDto;
                                }());
                                model.AccidentInsuranceRateDto = AccidentInsuranceRateDto;
                                var InsuBizRateItemDto = (function () {
                                    function InsuBizRateItemDto(insuBizType, insuRate, insuRound, insuranceBusinessType) {
                                        this.insuBizType = insuBizType;
                                        this.insuRate = insuRate;
                                        this.insuRound = insuRound;
                                        this.insuranceBusinessType = insuranceBusinessType;
                                    }
                                    return InsuBizRateItemDto;
                                }());
                                model.InsuBizRateItemDto = InsuBizRateItemDto;
                                var InsuranceBusinessType = (function () {
                                    function InsuranceBusinessType(bizOrder, bizName) {
                                        this.bizOrder = bizOrder;
                                        this.bizName = bizName;
                                    }
                                    return InsuranceBusinessType;
                                }());
                                model.InsuranceBusinessType = InsuranceBusinessType;
                                (function (CareerGroupDto) {
                                    CareerGroupDto[CareerGroupDto["Agroforestry"] = 0] = "Agroforestry";
                                    CareerGroupDto[CareerGroupDto["Contruction"] = 1] = "Contruction";
                                    CareerGroupDto[CareerGroupDto["Other"] = 2] = "Other";
                                })(model.CareerGroupDto || (model.CareerGroupDto = {}));
                                var CareerGroupDto = model.CareerGroupDto;
                                (function (BusinessTypeEnumDto) {
                                    BusinessTypeEnumDto[BusinessTypeEnumDto["Biz1St"] = 1] = "Biz1St";
                                    BusinessTypeEnumDto[BusinessTypeEnumDto["Biz2Nd"] = 2] = "Biz2Nd";
                                    BusinessTypeEnumDto[BusinessTypeEnumDto["Biz3Rd"] = 3] = "Biz3Rd";
                                    BusinessTypeEnumDto[BusinessTypeEnumDto["Biz4Th"] = 4] = "Biz4Th";
                                    BusinessTypeEnumDto[BusinessTypeEnumDto["Biz5Th"] = 5] = "Biz5Th";
                                    BusinessTypeEnumDto[BusinessTypeEnumDto["Biz6Th"] = 6] = "Biz6Th";
                                    BusinessTypeEnumDto[BusinessTypeEnumDto["Biz7Th"] = 7] = "Biz7Th";
                                    BusinessTypeEnumDto[BusinessTypeEnumDto["Biz8Th"] = 8] = "Biz8Th";
                                    BusinessTypeEnumDto[BusinessTypeEnumDto["Biz9Th"] = 9] = "Biz9Th";
                                    BusinessTypeEnumDto[BusinessTypeEnumDto["Biz10Th"] = 10] = "Biz10Th";
                                })(model.BusinessTypeEnumDto || (model.BusinessTypeEnumDto = {}));
                                var BusinessTypeEnumDto = model.BusinessTypeEnumDto;
                                (function (TypeHistory) {
                                    TypeHistory[TypeHistory["HistoryUnemployee"] = 1] = "HistoryUnemployee";
                                    TypeHistory[TypeHistory["HistoryAccident"] = 2] = "HistoryAccident";
                                })(model.TypeHistory || (model.TypeHistory = {}));
                                var TypeHistory = model.TypeHistory;
                                (function (TypeActionInsuranceRate) {
                                    TypeActionInsuranceRate[TypeActionInsuranceRate["add"] = 1] = "add";
                                    TypeActionInsuranceRate[TypeActionInsuranceRate["update"] = 2] = "update";
                                })(model.TypeActionInsuranceRate || (model.TypeActionInsuranceRate = {}));
                                var TypeActionInsuranceRate = model.TypeActionInsuranceRate;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm011.a || (qmm011.a = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
