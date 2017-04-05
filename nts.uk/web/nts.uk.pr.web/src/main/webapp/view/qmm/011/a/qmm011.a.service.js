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
                                findAllUnemployeeInsuranceRateHistory: "pr/insurance/labor/unemployeerate/history/findall",
                                findUnemployeeInsuranceRateHistory: "pr/insurance/labor/unemployeerate/history/find",
                                updateUnemployeeInsuranceRateHistory: "pr/insurance/labor/unemployeerate/history/update",
                                detailUnemployeeInsuranceRateHistory: "pr/insurance/labor/unemployeerate/detail",
                                addUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/add",
                                copyUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/copy",
                                deleteUnemployeeInsurance: "pr/insurance/labor/unemployeerate/delete",
                                updateUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/update",
                                findAllAccidentInsuranceRateHistory: "pr/insurance/labor/accidentrate/history/findall",
                                findAccidentInsuranceRateHistory: "pr/insurance/labor/accidentrate/history/find",
                                updateAccidentInsuranceRateHistory: "pr/insurance/labor/accidentrate/history/update",
                                findAccidentInsuranceRate: "pr/insurance/labor/accidentrate/find",
                                addAccidentInsuranceRate: "pr/insurance/labor/accidentrate/add",
                                copyAccidentInsuranceRate: "pr/insurance/labor/accidentrate/copy",
                                updateAccidentInsuranceRate: "pr/insurance/labor/accidentrate/update",
                                findAllInsuranceBusinessType: "pr/insurance/labor/businesstype/findall",
                                deleteAccidentInsuranceRate: "pr/insurance/labor/accidentrate/delete"
                            };
                            function updateUnemployeeInsuranceRateHistory(unemployeeInsuranceHistoryUpdateDto) {
                                return nts.uk.request.ajax(paths.updateUnemployeeInsuranceRateHistory, unemployeeInsuranceHistoryUpdateDto);
                            }
                            service.updateUnemployeeInsuranceRateHistory = updateUnemployeeInsuranceRateHistory;
                            function updateAccidentInsuranceRateHistory(accidentInsuranceHistoryUpdateDto) {
                                return nts.uk.request.ajax(paths.updateAccidentInsuranceRateHistory, accidentInsuranceHistoryUpdateDto);
                            }
                            service.updateAccidentInsuranceRateHistory = updateAccidentInsuranceRateHistory;
                            function deleteAccidentInsuranceRate(accidentInsuranceRateDeleteDto) {
                                var data = { accidentInsuranceRateDeleteDto: accidentInsuranceRateDeleteDto };
                                return nts.uk.request.ajax(paths.deleteAccidentInsuranceRate, data);
                            }
                            service.deleteAccidentInsuranceRate = deleteAccidentInsuranceRate;
                            function deleteUnemployeeInsurance(unemployeeInsuranceDeleteDto) {
                                return nts.uk.request.ajax(paths.deleteUnemployeeInsurance, unemployeeInsuranceDeleteDto);
                            }
                            service.deleteUnemployeeInsurance = deleteUnemployeeInsurance;
                            function addUnemployeeInsuranceRate(unemployeeInsuranceRateModel) {
                                var dfd = $.Deferred();
                                var data = {
                                    unemployeeInsuranceRate: service.convertUnemployeeInsuranceRateModelDTO(unemployeeInsuranceRateModel)
                                };
                                return nts.uk.request.ajax(paths.addUnemployeeInsuranceRate, data);
                            }
                            service.addUnemployeeInsuranceRate = addUnemployeeInsuranceRate;
                            function updateUnemployeeInsuranceRate(unemployeeInsuranceRateModel) {
                                var dfd = $.Deferred();
                                var data = {
                                    unemployeeInsuranceRate: service.convertUnemployeeInsuranceRateModelDTO(unemployeeInsuranceRateModel)
                                };
                                return nts.uk.request.ajax(paths.updateUnemployeeInsuranceRate, data);
                            }
                            service.updateUnemployeeInsuranceRate = updateUnemployeeInsuranceRate;
                            function findAllUnemployeeInsuranceRateHistory() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAllUnemployeeInsuranceRateHistory)
                                    .done(function (res) {
                                    var convertRes;
                                    convertRes = [];
                                    for (var _i = 0, res_1 = res; _i < res_1.length; _i++) {
                                        var itemRes = res_1[_i];
                                        var UnemployeeInsuranceHistoryDto;
                                        UnemployeeInsuranceHistoryDto = new model.UnemployeeInsuranceHistoryDto();
                                        UnemployeeInsuranceHistoryDto.setDataHistory(itemRes);
                                        convertRes.push(UnemployeeInsuranceHistoryDto);
                                    }
                                    dfd.resolve(convertRes);
                                });
                                return dfd.promise();
                            }
                            service.findAllUnemployeeInsuranceRateHistory = findAllUnemployeeInsuranceRateHistory;
                            function findUnemployeeInsuranceRateHistory(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findUnemployeeInsuranceRateHistory + "/" + historyId)
                                    .done(function (res) {
                                    var UnemployeeInsuranceHistoryDto;
                                    UnemployeeInsuranceHistoryDto = new model.UnemployeeInsuranceHistoryDto();
                                    UnemployeeInsuranceHistoryDto.setDataHistory(res);
                                    dfd.resolve(UnemployeeInsuranceHistoryDto);
                                });
                                return dfd.promise();
                            }
                            service.findUnemployeeInsuranceRateHistory = findUnemployeeInsuranceRateHistory;
                            function detailUnemployeeInsuranceRateHistory(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.detailUnemployeeInsuranceRateHistory + "/" + historyId)
                                    .done(function (res) {
                                    var unemployeeInsuranceRateDto;
                                    unemployeeInsuranceRateDto = new model.UnemployeeInsuranceRateDto();
                                    unemployeeInsuranceRateDto.historyInsurance = new model.UnemployeeInsuranceHistoryDto();
                                    unemployeeInsuranceRateDto.historyInsurance.historyId = res.historyInsurance.historyId;
                                    unemployeeInsuranceRateDto.historyInsurance.startMonth = res.historyInsurance.startMonth;
                                    unemployeeInsuranceRateDto.historyInsurance.endMonth = res.historyInsurance.endMonth;
                                    unemployeeInsuranceRateDto.rateItems = res.rateItems;
                                    unemployeeInsuranceRateDto.version = res.version;
                                    dfd.resolve(unemployeeInsuranceRateDto);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.detailUnemployeeInsuranceRateHistory = detailUnemployeeInsuranceRateHistory;
                            function copyAccidentInsuranceRate(accidentInsuranceRateCopyDto) {
                                return nts.uk.request.ajax(paths.copyAccidentInsuranceRate, accidentInsuranceRateCopyDto);
                            }
                            service.copyAccidentInsuranceRate = copyAccidentInsuranceRate;
                            function addAccidentInsuranceRate(accidentInsuranceRateModel) {
                                var data = {
                                    accidentInsuranceRate: service.convertAccidentInsuranceRateModelDTO(accidentInsuranceRateModel)
                                };
                                return nts.uk.request.ajax(paths.addAccidentInsuranceRate, data);
                            }
                            service.addAccidentInsuranceRate = addAccidentInsuranceRate;
                            function copyUnemployeeInsuranceRate(unemployeeInsuranceRateCopyDto) {
                                return nts.uk.request.ajax(paths.copyUnemployeeInsuranceRate, unemployeeInsuranceRateCopyDto);
                            }
                            service.copyUnemployeeInsuranceRate = copyUnemployeeInsuranceRate;
                            function updateAccidentInsuranceRate(accidentInsuranceRateModel) {
                                var data = {
                                    accidentInsuranceRate: service.convertAccidentInsuranceRateModelDTO(accidentInsuranceRateModel)
                                };
                                return nts.uk.request.ajax(paths.updateAccidentInsuranceRate, data);
                            }
                            service.updateAccidentInsuranceRate = updateAccidentInsuranceRate;
                            function findAllAccidentInsuranceRateHistory() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAllAccidentInsuranceRateHistory)
                                    .done(function (res) {
                                    var convertRes;
                                    convertRes = [];
                                    for (var _i = 0, res_2 = res; _i < res_2.length; _i++) {
                                        var itemRes = res_2[_i];
                                        var AccidentInsuranceHistoryDto;
                                        AccidentInsuranceHistoryDto = new model.AccidentInsuranceHistoryDto();
                                        AccidentInsuranceHistoryDto.setDataHistory(itemRes);
                                        convertRes.push(AccidentInsuranceHistoryDto);
                                    }
                                    dfd.resolve(convertRes);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findAllAccidentInsuranceRateHistory = findAllAccidentInsuranceRateHistory;
                            function findAccidentInsuranceRateHistory(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAccidentInsuranceRateHistory + "/" + historyId)
                                    .done(function (res) {
                                    var AccidentInsuranceHistoryDto;
                                    AccidentInsuranceHistoryDto = new model.AccidentInsuranceHistoryDto();
                                    AccidentInsuranceHistoryDto.setDataHistory(res);
                                    dfd.resolve(AccidentInsuranceHistoryDto);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findAccidentInsuranceRateHistory = findAccidentInsuranceRateHistory;
                            function findAccidentInsuranceRate(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAccidentInsuranceRate + "/" + historyId)
                                    .done(function (res) {
                                    var accidentInsuranceRateDto;
                                    accidentInsuranceRateDto = new model.AccidentInsuranceRateDto();
                                    accidentInsuranceRateDto.historyInsurance = new model.AccidentInsuranceHistoryDto();
                                    accidentInsuranceRateDto.historyInsurance.historyId = res.historyInsurance.historyId;
                                    accidentInsuranceRateDto.historyInsurance.endMonth = res.historyInsurance.endMonth;
                                    accidentInsuranceRateDto.historyInsurance.startMonth = res.historyInsurance.startMonth;
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
                            function convertUnemployeeInsuranceHistoryDto(UnemployeeInsuranceHistoryModel) {
                                var historyDto;
                                historyDto = new model.UnemployeeInsuranceHistoryDto();
                                historyDto.historyId = UnemployeeInsuranceHistoryModel.historyId();
                                historyDto.startMonth = UnemployeeInsuranceHistoryModel.startMonth();
                                historyDto.endMonth = UnemployeeInsuranceHistoryModel.endMonth();
                                return historyDto;
                            }
                            service.convertUnemployeeInsuranceHistoryDto = convertUnemployeeInsuranceHistoryDto;
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
                                    = convertUnemployeeInsuranceHistoryDto(unemployeeInsuranceRateModel.unemployeeInsuranceHistoryModel);
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
                            function convertAccidentInsuranceHistoryDto(accidentInsuranceRateHistoryModel) {
                                var accidentInsuranceHistoryDto;
                                accidentInsuranceHistoryDto = new model.AccidentInsuranceHistoryDto();
                                accidentInsuranceHistoryDto.historyId = accidentInsuranceRateHistoryModel.historyId();
                                accidentInsuranceHistoryDto.startMonthRage = accidentInsuranceRateHistoryModel.startMonthRage();
                                accidentInsuranceHistoryDto.startMonth = accidentInsuranceRateHistoryModel.startMonth();
                                accidentInsuranceHistoryDto.endMonthRage = accidentInsuranceRateHistoryModel.endMonthRage();
                                accidentInsuranceHistoryDto.endMonth = accidentInsuranceRateHistoryModel.endMonth();
                                return accidentInsuranceHistoryDto;
                            }
                            service.convertAccidentInsuranceHistoryDto = convertAccidentInsuranceHistoryDto;
                            function convertAccidentInsuranceRateModelDTO(accidentInsuranceRateModel) {
                                var accidentInsuranceRateDto;
                                accidentInsuranceRateDto = new model.AccidentInsuranceRateDto();
                                accidentInsuranceRateDto.historyInsurance
                                    = convertAccidentInsuranceHistoryDto(accidentInsuranceRateModel.accidentInsuranceRateHistoryModel);
                                accidentInsuranceRateDto.version = accidentInsuranceRateModel.version();
                                accidentInsuranceRateDto.rateItems = [];
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz1St, accidentInsuranceRateModel.biz1StModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz2Nd, accidentInsuranceRateModel.biz2NdModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz3Rd, accidentInsuranceRateModel.biz3RdModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz4Th, accidentInsuranceRateModel.biz4ThModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz5Th, accidentInsuranceRateModel.biz5ThModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz6Th, accidentInsuranceRateModel.biz6ThModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz7Th, accidentInsuranceRateModel.biz7ThModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz8Th, accidentInsuranceRateModel.biz8ThModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz9Th, accidentInsuranceRateModel.biz9ThModel));
                                accidentInsuranceRateDto.rateItems
                                    .push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz10Th, accidentInsuranceRateModel.biz10ThModel));
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
                                var UnemployeeInsuranceRateCopyDto = (function () {
                                    function UnemployeeInsuranceRateCopyDto() {
                                        this.historyIdCopy = '';
                                        this.startMonth = 0;
                                        this.addNew = false;
                                    }
                                    return UnemployeeInsuranceRateCopyDto;
                                }());
                                model.UnemployeeInsuranceRateCopyDto = UnemployeeInsuranceRateCopyDto;
                                var AccidentInsuranceRateCopyDto = (function () {
                                    function AccidentInsuranceRateCopyDto() {
                                        this.historyIdCopy = '';
                                        this.startMonth = 0;
                                        this.addNew = false;
                                    }
                                    return AccidentInsuranceRateCopyDto;
                                }());
                                model.AccidentInsuranceRateCopyDto = AccidentInsuranceRateCopyDto;
                                var HistoryInsuranceDto = (function () {
                                    function HistoryInsuranceDto() {
                                        this.historyId = '';
                                        this.startMonthRage = '';
                                        this.endMonthRage = '';
                                        this.inforMonthRage = '';
                                    }
                                    HistoryInsuranceDto.prototype.setDataHistory = function (historyData) {
                                        this.historyId = historyData.historyId;
                                        this.startMonthRage = nts.uk.time.formatYearMonth(historyData.startMonth);
                                        this.endMonthRage = nts.uk.time.formatYearMonth(historyData.endMonth);
                                        this.inforMonthRage = this.startMonthRage + ' ~ ' + this.endMonthRage;
                                        this.startMonth = historyData.startMonth;
                                        this.endMonth = historyData.endMonth;
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
                                var HistoryInsuranceInDto = (function () {
                                    function HistoryInsuranceInDto() {
                                    }
                                    return HistoryInsuranceInDto;
                                }());
                                model.HistoryInsuranceInDto = HistoryInsuranceInDto;
                                var UnemployeeInsuranceHistoryDto = (function (_super) {
                                    __extends(UnemployeeInsuranceHistoryDto, _super);
                                    function UnemployeeInsuranceHistoryDto() {
                                        _super.apply(this, arguments);
                                    }
                                    return UnemployeeInsuranceHistoryDto;
                                }(HistoryInsuranceDto));
                                model.UnemployeeInsuranceHistoryDto = UnemployeeInsuranceHistoryDto;
                                var UnemployeeInsuranceHistoryFindInDto = (function () {
                                    function UnemployeeInsuranceHistoryFindInDto() {
                                    }
                                    return UnemployeeInsuranceHistoryFindInDto;
                                }());
                                model.UnemployeeInsuranceHistoryFindInDto = UnemployeeInsuranceHistoryFindInDto;
                                var UnemployeeInsuranceHistoryUpdateDto = (function () {
                                    function UnemployeeInsuranceHistoryUpdateDto() {
                                    }
                                    return UnemployeeInsuranceHistoryUpdateDto;
                                }());
                                model.UnemployeeInsuranceHistoryUpdateDto = UnemployeeInsuranceHistoryUpdateDto;
                                var AccidentInsuranceHistoryUpdateDto = (function () {
                                    function AccidentInsuranceHistoryUpdateDto() {
                                    }
                                    return AccidentInsuranceHistoryUpdateDto;
                                }());
                                model.AccidentInsuranceHistoryUpdateDto = AccidentInsuranceHistoryUpdateDto;
                                var UnemployeeInsuranceRateFindOutDto = (function () {
                                    function UnemployeeInsuranceRateFindOutDto() {
                                    }
                                    return UnemployeeInsuranceRateFindOutDto;
                                }());
                                model.UnemployeeInsuranceRateFindOutDto = UnemployeeInsuranceRateFindOutDto;
                                var AccidentInsuranceRateDeleteDto = (function () {
                                    function AccidentInsuranceRateDeleteDto() {
                                    }
                                    return AccidentInsuranceRateDeleteDto;
                                }());
                                model.AccidentInsuranceRateDeleteDto = AccidentInsuranceRateDeleteDto;
                                var UnemployeeInsuranceDeleteDto = (function () {
                                    function UnemployeeInsuranceDeleteDto() {
                                    }
                                    return UnemployeeInsuranceDeleteDto;
                                }());
                                model.UnemployeeInsuranceDeleteDto = UnemployeeInsuranceDeleteDto;
                                var UnemployeeInsuranceRateDto = (function () {
                                    function UnemployeeInsuranceRateDto() {
                                    }
                                    return UnemployeeInsuranceRateDto;
                                }());
                                model.UnemployeeInsuranceRateDto = UnemployeeInsuranceRateDto;
                                var AccidentInsuranceHistoryDto = (function (_super) {
                                    __extends(AccidentInsuranceHistoryDto, _super);
                                    function AccidentInsuranceHistoryDto() {
                                        _super.apply(this, arguments);
                                    }
                                    return AccidentInsuranceHistoryDto;
                                }(HistoryInsuranceDto));
                                model.AccidentInsuranceHistoryDto = AccidentInsuranceHistoryDto;
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
                                var AccidentInsuranceRateHistoryFindInDto = (function () {
                                    function AccidentInsuranceRateHistoryFindInDto() {
                                    }
                                    return AccidentInsuranceRateHistoryFindInDto;
                                }());
                                model.AccidentInsuranceRateHistoryFindInDto = AccidentInsuranceRateHistoryFindInDto;
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
                                    CareerGroupDto[CareerGroupDto["Other"] = 1] = "Other";
                                    CareerGroupDto[CareerGroupDto["Contruction"] = 2] = "Contruction";
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
//# sourceMappingURL=qmm011.a.service.js.map