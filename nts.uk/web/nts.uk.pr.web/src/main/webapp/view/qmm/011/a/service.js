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
                                findAllHistoryUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/history/findall",
                                findHistoryUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/history/find",
                                detailHistoryUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/detail",
                                addUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/add",
                                updateUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/update",
                                findAllHistoryAccidentInsuranceRate: "pr/insurance/labor/accidentrate/history/findall",
                                findHistoryAccidentInsuranceRate: "pr/insurance/labor/accidentrate/history/find",
                                findAccidentInsuranceRate: "pr/insurance/labor/accidentrate/find",
                                addAccidentInsuranceRate: "pr/insurance/labor/accidentrate/add",
                                updateAccidentInsuranceRate: "pr/insurance/labor/accidentrate/update",
                                findAllInsuranceBusinessType: "pr/insurance/labor/businesstype/findall"
                            };
                            function addUnemployeeInsuranceRate(unemployeeInsuranceRateModel) {
                                var dfd = $.Deferred();
                                var data = {
                                    unemployeeInsuranceRate: service.convertUnemployeeInsuranceRateModelDTO(unemployeeInsuranceRateModel)
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
                            function updateUnemployeeInsuranceRate(unemployeeInsuranceRateModel) {
                                var dfd = $.Deferred();
                                var data = {
                                    unemployeeInsuranceRate: service.convertUnemployeeInsuranceRateModelDTO(unemployeeInsuranceRateModel)
                                };
                                nts.uk.request.ajax(paths.updateUnemployeeInsuranceRate, data)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.updateUnemployeeInsuranceRate = updateUnemployeeInsuranceRate;
                            function findAllHistoryUnemployeeInsuranceRate() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAllHistoryUnemployeeInsuranceRate)
                                    .done(function (res) {
                                    var convertRes;
                                    convertRes = [];
                                    for (var _i = 0, res_1 = res; _i < res_1.length; _i++) {
                                        var itemRes = res_1[_i];
                                        var historyUnemployeeInsuranceDto;
                                        historyUnemployeeInsuranceDto = new model.HistoryUnemployeeInsuranceDto();
                                        historyUnemployeeInsuranceDto.setDataHistory(itemRes);
                                        convertRes.push(historyUnemployeeInsuranceDto);
                                    }
                                    dfd.resolve(convertRes);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findAllHistoryUnemployeeInsuranceRate = findAllHistoryUnemployeeInsuranceRate;
                            function findHistoryUnemployeeInsuranceRate(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findHistoryUnemployeeInsuranceRate + "/" + historyId)
                                    .done(function (res) {
                                    var historyUnemployeeInsuranceDto;
                                    historyUnemployeeInsuranceDto = new model.HistoryUnemployeeInsuranceDto();
                                    historyUnemployeeInsuranceDto.setDataHistory(res);
                                    dfd.resolve(historyUnemployeeInsuranceDto);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findHistoryUnemployeeInsuranceRate = findHistoryUnemployeeInsuranceRate;
                            function detailHistoryUnemployeeInsuranceRate(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.detailHistoryUnemployeeInsuranceRate + "/" + historyId)
                                    .done(function (res) {
                                    var unemployeeInsuranceRateDto;
                                    unemployeeInsuranceRateDto = new model.UnemployeeInsuranceRateDto();
                                    unemployeeInsuranceRateDto.historyInsurance = new model.HistoryUnemployeeInsuranceDto();
                                    unemployeeInsuranceRateDto.historyInsurance.setDataHistory(res.historyInsurance);
                                    unemployeeInsuranceRateDto.rateItems = res.rateItems;
                                    unemployeeInsuranceRateDto.version = res.version;
                                    dfd.resolve(unemployeeInsuranceRateDto);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.detailHistoryUnemployeeInsuranceRate = detailHistoryUnemployeeInsuranceRate;
                            function addAccidentInsuranceRate(accidentInsuranceRateModel) {
                                var dfd = $.Deferred();
                                var data = {
                                    accidentInsuranceRate: service.convertAccidentInsuranceRateModelDTO(accidentInsuranceRateModel)
                                };
                                nts.uk.request.ajax(paths.addAccidentInsuranceRate, data)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.addAccidentInsuranceRate = addAccidentInsuranceRate;
                            function updateAccidentInsuranceRate(accidentInsuranceRateModel) {
                                var dfd = $.Deferred();
                                var data = {
                                    accidentInsuranceRate: service.convertAccidentInsuranceRateModelDTO(accidentInsuranceRateModel)
                                };
                                nts.uk.request.ajax(paths.updateAccidentInsuranceRate, data)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.updateAccidentInsuranceRate = updateAccidentInsuranceRate;
                            function findAllHistoryAccidentInsuranceRate() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAllHistoryAccidentInsuranceRate)
                                    .done(function (res) {
                                    var convertRes;
                                    convertRes = [];
                                    for (var _i = 0, res_2 = res; _i < res_2.length; _i++) {
                                        var itemRes = res_2[_i];
                                        var historyAccidentInsuranceDto;
                                        historyAccidentInsuranceDto = new model.HistoryAccidentInsuranceDto();
                                        historyAccidentInsuranceDto.setDataHistory(itemRes);
                                        convertRes.push(historyAccidentInsuranceDto);
                                    }
                                    dfd.resolve(convertRes);
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
                                    var historyAccidentInsuranceDto;
                                    historyAccidentInsuranceDto = new model.HistoryAccidentInsuranceDto();
                                    historyAccidentInsuranceDto.setDataHistory(res);
                                    dfd.resolve(historyAccidentInsuranceDto);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findHistoryAccidentInsuranceRate = findHistoryAccidentInsuranceRate;
                            function findAccidentInsuranceRate(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAccidentInsuranceRate + "/" + historyId)
                                    .done(function (res) {
                                    var accidentInsuranceRateDto;
                                    accidentInsuranceRateDto = new model.AccidentInsuranceRateDto();
                                    accidentInsuranceRateDto.historyInsurance = new model.HistoryAccidentInsuranceDto();
                                    accidentInsuranceRateDto.historyInsurance.setDataHistory(res.historyInsurance);
                                    accidentInsuranceRateDto.rateItems = res.rateItems;
                                    accidentInsuranceRateDto.version = res.version;
                                    dfd.resolve(accidentInsuranceRateDto);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findAccidentInsuranceRate = findAccidentInsuranceRate;
                            function convertHistoryUnemployeeInsuranceDto(historyUnemployeeInsuranceModel) {
                                var historyUnemployeeInsuranceDto;
                                historyUnemployeeInsuranceDto = new model.HistoryUnemployeeInsuranceDto();
                                historyUnemployeeInsuranceDto.historyId = historyUnemployeeInsuranceModel.historyId();
                                historyUnemployeeInsuranceDto.startMonthRage = historyUnemployeeInsuranceModel.startMonthRage();
                                historyUnemployeeInsuranceDto.endMonthRage = historyUnemployeeInsuranceModel.endMonthRage();
                                return historyUnemployeeInsuranceDto;
                            }
                            service.convertHistoryUnemployeeInsuranceDto = convertHistoryUnemployeeInsuranceDto;
                            function convertUnemployeeInsuranceRateItemSettingModelDTO(unemployeeInsuranceRateItemSettingModel) {
                                var unemployeeInsuranceRateItemSettingDto;
                                unemployeeInsuranceRateItemSettingDto
                                    = new model.UnemployeeInsuranceRateItemSettingDto(unemployeeInsuranceRateItemSettingModel.roundAtr(), unemployeeInsuranceRateItemSettingModel.rate());
                                return unemployeeInsuranceRateItemSettingDto;
                            }
                            service.convertUnemployeeInsuranceRateItemSettingModelDTO = convertUnemployeeInsuranceRateItemSettingModelDTO;
                            function convertUnemployeeInsuranceRateItemModelDTO(careerGroup, unemployeeInsuranceRateItemModel) {
                                var unemployeeInsuranceRateItemDto;
                                unemployeeInsuranceRateItemDto = new model.UnemployeeInsuranceRateItemDto(careerGroup, service.convertUnemployeeInsuranceRateItemSettingModelDTO(unemployeeInsuranceRateItemModel.companySetting), service.convertUnemployeeInsuranceRateItemSettingModelDTO(unemployeeInsuranceRateItemModel.personalSetting));
                                return unemployeeInsuranceRateItemDto;
                            }
                            service.convertUnemployeeInsuranceRateItemModelDTO = convertUnemployeeInsuranceRateItemModelDTO;
                            function convertUnemployeeInsuranceRateModelDTO(unemployeeInsuranceRateModel) {
                                var unemployeeInsuranceRateDto;
                                unemployeeInsuranceRateDto = new model.UnemployeeInsuranceRateDto();
                                unemployeeInsuranceRateDto.historyInsurance
                                    = convertHistoryUnemployeeInsuranceDto(unemployeeInsuranceRateModel.historyUnemployeeInsuranceModel);
                                unemployeeInsuranceRateDto.version = unemployeeInsuranceRateModel.version();
                                unemployeeInsuranceRateDto.rateItems = [];
                                unemployeeInsuranceRateDto.rateItems
                                    .push(service.convertUnemployeeInsuranceRateItemModelDTO(model.CareerGroupDto.Agroforestry, unemployeeInsuranceRateModel.unemployeeInsuranceRateItemAgroforestryModel));
                                unemployeeInsuranceRateDto.rateItems
                                    .push(service.convertUnemployeeInsuranceRateItemModelDTO(model.CareerGroupDto.Contruction, unemployeeInsuranceRateModel.
                                    unemployeeInsuranceRateItemContructionModel));
                                unemployeeInsuranceRateDto.rateItems
                                    .push(service.convertUnemployeeInsuranceRateItemModelDTO(model.CareerGroupDto.Other, unemployeeInsuranceRateModel.unemployeeInsuranceRateItemOtherModel));
                                return unemployeeInsuranceRateDto;
                            }
                            service.convertUnemployeeInsuranceRateModelDTO = convertUnemployeeInsuranceRateModelDTO;
                            function convertInsuBizRateItemModelDTO(insuBizType, accidentInsuranceRateDetailModel) {
                                var insuBizRateItemDto;
                                insuBizRateItemDto = new model.InsuBizRateItemDto(insuBizType, accidentInsuranceRateDetailModel.insuRate(), accidentInsuranceRateDetailModel.insuRound(), accidentInsuranceRateDetailModel.insuranceBusinessType());
                                return insuBizRateItemDto;
                            }
                            service.convertInsuBizRateItemModelDTO = convertInsuBizRateItemModelDTO;
                            function convertHistoryAccidentInsuranceDto(historyAccidentInsuranceRateModel) {
                                var historyAccidentInsuranceDto;
                                historyAccidentInsuranceDto = new model.HistoryAccidentInsuranceDto();
                                historyAccidentInsuranceDto.historyId = historyAccidentInsuranceRateModel.historyId();
                                historyAccidentInsuranceDto.startMonthRage = historyAccidentInsuranceRateModel.startMonthRage();
                                historyAccidentInsuranceDto.endMonthRage = historyAccidentInsuranceRateModel.endMonthRage();
                                return historyAccidentInsuranceDto;
                            }
                            service.convertHistoryAccidentInsuranceDto = convertHistoryAccidentInsuranceDto;
                            function convertAccidentInsuranceRateModelDTO(accidentInsuranceRateModel) {
                                var accidentInsuranceRateDto;
                                accidentInsuranceRateDto = new model.AccidentInsuranceRateDto();
                                accidentInsuranceRateDto.historyInsurance
                                    = convertHistoryAccidentInsuranceDto(accidentInsuranceRateModel.historyAccidentInsuranceRateModel);
                                accidentInsuranceRateDto.version = accidentInsuranceRateModel.version();
                                accidentInsuranceRateDto.rateItems = [];
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz1St, accidentInsuranceRateModel.accidentInsuranceRateBiz1StModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz2Nd, accidentInsuranceRateModel.accidentInsuranceRateBiz2NdModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz3Rd, accidentInsuranceRateModel.accidentInsuranceRateBiz3RdModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz4Th, accidentInsuranceRateModel.accidentInsuranceRateBiz4ThModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz5Th, accidentInsuranceRateModel.accidentInsuranceRateBiz5ThModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz6Th, accidentInsuranceRateModel.accidentInsuranceRateBiz6ThModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz7Th, accidentInsuranceRateModel.accidentInsuranceRateBiz7ThModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz8Th, accidentInsuranceRateModel.accidentInsuranceRateBiz8ThModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz9Th, accidentInsuranceRateModel.accidentInsuranceRateBiz9ThModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz10Th, accidentInsuranceRateModel.accidentInsuranceRateBiz10ThModel));
                                return accidentInsuranceRateDto;
                            }
                            service.convertAccidentInsuranceRateModelDTO = convertAccidentInsuranceRateModelDTO;
                            function findAllInsuranceBusinessType() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAllInsuranceBusinessType)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findAllInsuranceBusinessType = findAllInsuranceBusinessType;
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
                                    function HistoryInsuranceDto() {
                                        this.historyId = '';
                                        this.startMonthRage = '';
                                        this.endMonthRage = '';
                                        this.inforMonthRage = '';
                                    }
                                    HistoryInsuranceDto.prototype.setDataHistory = function (historyData) {
                                        this.historyId = historyData.historyId;
                                        this.startMonthRage = nts.uk.time.formatYearMonth(historyData.startMonthRage);
                                        this.endMonthRage = nts.uk.time.formatYearMonth(historyData.endMonthRage);
                                        this.inforMonthRage = this.startMonthRage + ' ~ ' + this.endMonthRage;
                                    };
                                    return HistoryInsuranceDto;
                                }());
                                model.HistoryInsuranceDto = HistoryInsuranceDto;
                                var HistoryInsuranceFindOutDto = (function () {
                                    function HistoryInsuranceFindOutDto() {
                                    }
                                    return HistoryInsuranceFindOutDto;
                                }());
                                model.HistoryInsuranceFindOutDto = HistoryInsuranceFindOutDto;
                                var HistoryUnemployeeInsuranceDto = (function (_super) {
                                    __extends(HistoryUnemployeeInsuranceDto, _super);
                                    function HistoryUnemployeeInsuranceDto() {
                                        _super.apply(this, arguments);
                                    }
                                    return HistoryUnemployeeInsuranceDto;
                                }(HistoryInsuranceDto));
                                model.HistoryUnemployeeInsuranceDto = HistoryUnemployeeInsuranceDto;
                                var HistoryUnemployeeInsuranceFindInDto = (function () {
                                    function HistoryUnemployeeInsuranceFindInDto() {
                                    }
                                    return HistoryUnemployeeInsuranceFindInDto;
                                }());
                                model.HistoryUnemployeeInsuranceFindInDto = HistoryUnemployeeInsuranceFindInDto;
                                var UnemployeeInsuranceRateFindOutDto = (function () {
                                    function UnemployeeInsuranceRateFindOutDto() {
                                    }
                                    return UnemployeeInsuranceRateFindOutDto;
                                }());
                                model.UnemployeeInsuranceRateFindOutDto = UnemployeeInsuranceRateFindOutDto;
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
                                var UnemployeeInsuranceFindInDto = (function () {
                                    function UnemployeeInsuranceFindInDto() {
                                    }
                                    return UnemployeeInsuranceFindInDto;
                                }());
                                model.UnemployeeInsuranceFindInDto = UnemployeeInsuranceFindInDto;
                                var AccidentInsuranceRateDto = (function () {
                                    function AccidentInsuranceRateDto() {
                                    }
                                    return AccidentInsuranceRateDto;
                                }());
                                model.AccidentInsuranceRateDto = AccidentInsuranceRateDto;
                                var AccidentInsuranceRateFindOutDto = (function () {
                                    function AccidentInsuranceRateFindOutDto() {
                                    }
                                    return AccidentInsuranceRateFindOutDto;
                                }());
                                model.AccidentInsuranceRateFindOutDto = AccidentInsuranceRateFindOutDto;
                                var HistoryAccidentInsuranceRateFindInDto = (function () {
                                    function HistoryAccidentInsuranceRateFindInDto() {
                                    }
                                    return HistoryAccidentInsuranceRateFindInDto;
                                }());
                                model.HistoryAccidentInsuranceRateFindInDto = HistoryAccidentInsuranceRateFindInDto;
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
                                var InsuranceBusinessTypeDto = (function () {
                                    function InsuranceBusinessTypeDto(bizNameBiz1St, bizNameBiz2Nd, bizNameBiz3Rd, bizNameBiz4Th, bizNameBiz5Th, bizNameBiz6Th, bizNameBiz7Th, bizNameBiz8Th, bizNameBiz9Th, bizNameBiz10Th, version) {
                                        this.bizNameBiz1St = bizNameBiz1St;
                                        this.bizNameBiz2Nd = bizNameBiz2Nd;
                                        this.bizNameBiz3Rd = bizNameBiz3Rd;
                                        this.bizNameBiz4Th = bizNameBiz4Th;
                                        this.bizNameBiz5Th = bizNameBiz5Th;
                                        this.bizNameBiz6Th = bizNameBiz6Th;
                                        this.bizNameBiz7Th = bizNameBiz7Th;
                                        this.bizNameBiz8Th = bizNameBiz8Th;
                                        this.bizNameBiz9Th = bizNameBiz9Th;
                                        this.bizNameBiz10Th = bizNameBiz10Th;
                                        this.version = version;
                                    }
                                    return InsuranceBusinessTypeDto;
                                }());
                                model.InsuranceBusinessTypeDto = InsuranceBusinessTypeDto;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qmm011.a || (qmm011.a = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
