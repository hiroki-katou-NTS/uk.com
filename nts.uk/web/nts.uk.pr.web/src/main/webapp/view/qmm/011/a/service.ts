module nts.uk.pr.view.qmm011.a {
    export module service {

        var paths: any = {
            findAllHisotryUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/history/findall",
            findHisotryUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/history/find",
            detailHistoryUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/detailHistory",
            addUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/add",
            updateUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/update",
            findAllHistoryAccidentInsuranceRate: "pr/insurance/labor/accidentrate/history/findall",
            findHistoryAccidentInsuranceRate: "pr/insurance/labor/accidentrate/history/find",
            detailHistoryAccidentInsuranceRate: "pr/insurance/labor/accidentrate/history/detail"
        };

        //Function connection service add Unemployee Insurance Rate
        export function addUnemployeeInsuranceRate(unemployeeInsuranceRateModel: viewmodel.UnemployeeInsuranceRateModel,
            historyUnemployeeInsuranceRateDto: model.HistoryUnemployeeInsuranceRateDto): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = {
                unemployeeInsuranceRate: service.convertUnemployeeInsuranceRateModelDTO(unemployeeInsuranceRateModel, historyUnemployeeInsuranceRateDto),
                companyCode: historyUnemployeeInsuranceRateDto.companyCode
            };
            nts.uk.request.ajax(paths.addUnemployeeInsuranceRate, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        //Function connection service update Unemployee Insurance Rate
        export function updateUnemployeeInsuranceRate(): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.updateUnemployeeInsuranceRate)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        //Function connection service FindAll Labor Insurance Office
        export function findAllHisotryUnemployeeInsuranceRate(): JQueryPromise<Array<model.HistoryUnemployeeInsuranceRateDto>> {
            var dfd = $.Deferred<Array<model.HistoryUnemployeeInsuranceRateDto>>();
            nts.uk.request.ajax(paths.findAllHisotryUnemployeeInsuranceRate)
                .done(function(res: Array<model.HistoryUnemployeeInsuranceRateDto>) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        //Function connection service Find History By historyId
        export function findHisotryUnemployeeInsuranceRate(historyId: string): JQueryPromise<model.HistoryUnemployeeInsuranceRateDto> {
            var dfd = $.Deferred<model.HistoryUnemployeeInsuranceRateDto>();
            nts.uk.request.ajax(paths.findHisotryUnemployeeInsuranceRate + "/" + historyId)
                .done(function(res: model.HistoryUnemployeeInsuranceRateDto) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        //Funtion connection service detail by historyId 
        export function detailHistoryUnemployeeInsuranceRate(historyId: string): JQueryPromise<model.UnemployeeInsuranceRateDto> {
            var dfd = $.Deferred<model.UnemployeeInsuranceRateDto>();
            nts.uk.request.ajax(paths.detailHistoryUnemployeeInsuranceRate + "/" + historyId)
                .done(function(res: model.UnemployeeInsuranceRateDto) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();

        }
        //Function connection service find All HistoryAccidentInsurance 
        export function findAllHistoryAccidentInsuranceRate(): JQueryPromise<Array<model.HistoryAccidentInsuranceRateDto>> {
            var dfd = $.Deferred<Array<model.HistoryAccidentInsuranceRateDto>>();
            nts.uk.request.ajax(paths.findAllHistoryAccidentInsuranceRate)
                .done(function(res: Array<model.HistoryAccidentInsuranceRateDto>) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        //Function connection service find HistoryAccidentInsurance
        export function findHistoryAccidentInsuranceRate(historyId: string): JQueryPromise<model.HistoryAccidentInsuranceRateDto> {
            var dfd = $.Deferred<model.HistoryAccidentInsuranceRateDto>();
            nts.uk.request.ajax(paths.findHistoryAccidentInsuranceRate + "/" + historyId)
                .done(function(res: model.HistoryAccidentInsuranceRateDto) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        //Function connection service detail HistoryAccidentInsurance
        export function detailHistoryAccidentInsuranceRate(historyId: string): JQueryPromise<model.AccidentInsuranceRateDto> {
            var dfd = $.Deferred<model.AccidentInsuranceRateDto>();
            nts.uk.request.ajax(paths.detailHistoryAccidentInsuranceRate + "/" + historyId)
                .done(function(res: model.AccidentInsuranceRateDto) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();

        }
        //Function convert Model => DTO (UnemployeeInsuranceRateItemSettingModel)
        export function convertUnemployeeInsuranceRateItemSettingModelDTO(unemployeeInsuranceRateItemSettingModel: viewmodel.UnemployeeInsuranceRateItemSettingModel)
            : model.UnemployeeInsuranceRateItemSettingDto {
            var unemployeeInsuranceRateItemSettingDto: model.UnemployeeInsuranceRateItemSettingDto;
            unemployeeInsuranceRateItemSettingDto = new model.UnemployeeInsuranceRateItemSettingDto(unemployeeInsuranceRateItemSettingModel.roundAtr(),
                unemployeeInsuranceRateItemSettingModel.rate());
            return unemployeeInsuranceRateItemSettingDto;
        }
        //Function convert Model => DTO (UnemployeeInsuranceRateItemModel)
        export function convertUnemployeeInsuranceRateItemModelDTO(careerGroup: number,
            unemployeeInsuranceRateItemModel: viewmodel.UnemployeeInsuranceRateItemModel):
            model.UnemployeeInsuranceRateItemDto {
            var unemployeeInsuranceRateItemDto: model.UnemployeeInsuranceRateItemDto;
            unemployeeInsuranceRateItemDto = new model.UnemployeeInsuranceRateItemDto(careerGroup,
                service.convertUnemployeeInsuranceRateItemSettingModelDTO(unemployeeInsuranceRateItemModel.companySetting),
                service.convertUnemployeeInsuranceRateItemSettingModelDTO(unemployeeInsuranceRateItemModel.personalSetting));
            return unemployeeInsuranceRateItemDto;
        }

        //Function convert Model => DTO (UnemployeeInsuranceRateModel)
        export function convertUnemployeeInsuranceRateModelDTO(unemployeeInsuranceRateModel: viewmodel.UnemployeeInsuranceRateModel,
            historyInsurance: model.HistoryUnemployeeInsuranceRateDto): model.UnemployeeInsuranceRateDto {
            var unemployeeInsuranceRateDto: model.UnemployeeInsuranceRateDto;
            unemployeeInsuranceRateDto = new model.UnemployeeInsuranceRateDto();
            unemployeeInsuranceRateDto.historyInsurance = historyInsurance;
            unemployeeInsuranceRateDto.companyCode = historyInsurance.companyCode;
            unemployeeInsuranceRateDto.rateItems = [];
            unemployeeInsuranceRateDto.rateItems.push(service.convertUnemployeeInsuranceRateItemModelDTO(model.CareerGroupDto.Agroforestry, unemployeeInsuranceRateModel.unemployeeInsuranceRateItemAgroforestryModel));
            unemployeeInsuranceRateDto.rateItems.push(service.convertUnemployeeInsuranceRateItemModelDTO(model.CareerGroupDto.Contruction, unemployeeInsuranceRateModel.unemployeeInsuranceRateItemContructionModel));
            unemployeeInsuranceRateDto.rateItems.push(service.convertUnemployeeInsuranceRateItemModelDTO(model.CareerGroupDto.Other, unemployeeInsuranceRateModel.unemployeeInsuranceRateItemOtherModel));
            return unemployeeInsuranceRateDto;
        }

        export module model {
            export class YearMonth {
                year: number;
                month: number;
                constructor(year: number, month: number) {
                    this.year = year;
                    this.month = month;
                }

            }
            export class MonthRange {
                startMonth: YearMonth;
                endMonth: YearMonth;
                constructor(startMonth: YearMonth, endMonth: YearMonth) {
                    this.startMonth = startMonth;
                    this.endMonth = endMonth;
                }
            }
            export class RoundingMethodDto {
                code: number;
                name: string;
                constructor(code: number, name: string) {
                    this.code = code;
                    this.name = name;
                }
            }
            export class UnemployeeInsuranceRateItemSettingDto {
                roundAtr: number;
                rate: number;
                constructor(roundAtr: number, rate: number) {
                    this.roundAtr = roundAtr;
                    this.rate = rate;
                }
            }
            export class UnemployeeInsuranceRateItemDto {
                careerGroup: number;
                companySetting: UnemployeeInsuranceRateItemSettingDto;
                personalSetting: UnemployeeInsuranceRateItemSettingDto;
                constructor(careerGroup: number, companySetting: UnemployeeInsuranceRateItemSettingDto,
                    personalSetting: UnemployeeInsuranceRateItemSettingDto) {
                    this.careerGroup = careerGroup;
                    this.companySetting = companySetting;
                    this.personalSetting = personalSetting;
                }
            }
            export class HistoryInsuranceDto {
                historyId: string;
                monthRage: MonthRange;
                startMonthRage: string;
                endMonthRage: string;
                inforMonthRage: string;
                constructor(historyId: string, monthRage: MonthRange, startMonthRage: string,
                    endMonthRage: string) {
                    this.historyId = historyId;
                    this.monthRage = monthRage;
                    this.startMonthRage = startMonthRage;
                    this.endMonthRage = endMonthRage;
                }
            }
            export class HistoryUnemployeeInsuranceDto extends HistoryInsuranceDto {

            }
            export class UnemployeeInsuranceRateDto {
                historyInsurance: HistoryUnemployeeInsuranceDto;
                companyCode: string;
                rateItems: UnemployeeInsuranceRateItemDto[];
            }

            export class HistoryAccidentInsuranceDto extends HistoryInsuranceDto {

            }
            export class AccidentInsuranceRateDto {
                historyId: string;
                companyCode: string;
                rateItems: InsuBizRateItemDto[];
            }
            export class InsuBizRateItemDto {
                /** The insu biz type. */
                insuBizType: number;
                /** The insu rate. */
                insuRate: number;
                /** The insu round. */
                insuRound: string;
                insuranceBusinessType: string;
                constructor(insuBizType: number, insuRate: number, insuRound: string, insuranceBusinessType: string) {
                    this.insuBizType = insuBizType;
                    this.insuRate = insuRate;
                    this.insuRound = insuRound;
                    this.insuranceBusinessType = insuranceBusinessType;
                }
            }
            export class InsuranceBusinessType {
                /** The biz order. */
                bizOrder: number;
                /** The biz name. */
                bizName: string;
                constructor(bizOrder: number, bizName: string) {
                    this.bizOrder = bizOrder;
                    this.bizName = bizName;
                }
            }
            export enum CareerGroupDto {
                Agroforestry = 0,
                Contruction = 1,
                Other = 2
            }
            export enum BusinessTypeEnumDto {
                /** The Biz 1 st. */
                Biz1St = 1,
                /** The Biz 2 nd. */
                Biz2Nd = 2,
                /** The Biz 3 rd. */
                Biz3Rd = 3,
                /** The Biz 4 th. */
                Biz4Th = 4,
                /** The Biz 5 th. */
                Biz5Th = 5,
                /** The Biz 6 th. */
                Biz6Th = 6,
                /** The Biz 7 th. */
                Biz7Th = 7,
                /** The Biz 8 th. */
                Biz8Th = 8,
                /** The Biz 9 th. */
                Biz9Th = 9,
                /** The Biz 10 th. */
                Biz10Th = 10

            }
            export enum TypeHistory {
                HistoryUnemployee = 1,
                HistoryAccident = 2
            }
            export enum TypeActionInsuranceRate {
                add = 1,
                update = 2
            }
        }
    }
}