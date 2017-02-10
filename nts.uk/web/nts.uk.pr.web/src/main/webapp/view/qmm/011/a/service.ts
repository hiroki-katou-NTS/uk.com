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
            detailHistoryAccidentInsuranceRate: "pr/insurance/labor/accidentrate/history/detail",
            addAccidentInsuranceRate: "pr/insurance/labor/accidentrate/add",
            updateAccidentInsuranceRate: "pr/insurance/labor/accidentrate/update",
            findAllInsuranceBusinessType: "pr/insurance/labor/businesstype/findall"
        };

        //Function connection service add Unemployee Insurance Rate
        export function addUnemployeeInsuranceRate(unemployeeInsuranceRateModel: viewmodel.UnemployeeInsuranceRateModel,
            historyUnemployeeInsuranceRateDto: model.HistoryUnemployeeInsuranceDto, companyCode: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = {
                unemployeeInsuranceRate: service.convertUnemployeeInsuranceRateModelDTO(unemployeeInsuranceRateModel,
                    historyUnemployeeInsuranceRateDto),
                companyCode: companyCode
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
        export function updateUnemployeeInsuranceRate(unemployeeInsuranceRateModel: viewmodel.UnemployeeInsuranceRateModel,
            historyUnemployeeInsuranceRateDto: model.HistoryUnemployeeInsuranceDto, companyCode: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = {
                unemployeeInsuranceRate: service.convertUnemployeeInsuranceRateModelDTO(unemployeeInsuranceRateModel,
                    historyUnemployeeInsuranceRateDto),
                companyCode: companyCode
            };
            nts.uk.request.ajax(paths.updateUnemployeeInsuranceRate, data)
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
        export function findAllHisotryUnemployeeInsuranceRate(): JQueryPromise<Array<model.HistoryUnemployeeInsuranceDto>> {
            var dfd = $.Deferred<Array<model.HistoryUnemployeeInsuranceDto>>();
            nts.uk.request.ajax(paths.findAllHisotryUnemployeeInsuranceRate)
                .done(function(res: Array<model.HistoryUnemployeeInsuranceDto>) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        //Function connection service Find History By historyId
        export function findHisotryUnemployeeInsuranceRate(historyId: string): JQueryPromise<model.HistoryUnemployeeInsuranceDto> {
            var dfd = $.Deferred<model.HistoryUnemployeeInsuranceDto>();
            nts.uk.request.ajax(paths.findHisotryUnemployeeInsuranceRate + "/" + historyId)
                .done(function(res: model.HistoryUnemployeeInsuranceDto) {
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
        //Function connection service add Accident Insurance Rate
        export function addAccidentInsuranceRate(accidentInsuranceRateModel: viewmodel.AccidentInsuranceRateModel,
            historyAccidentInsuranceDto: model.HistoryAccidentInsuranceDto, companyCode: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = {
                accidentInsuranceRate: service.convertAccidentInsuranceRateModelDTO(accidentInsuranceRateModel,
                    historyAccidentInsuranceDto),
                companyCode: companyCode
            };
            nts.uk.request.ajax(paths.addAccidentInsuranceRate, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }


        //Function connection service update Accident Insurance Rate
        export function updateAccidentInsuranceRate(accidentInsuranceRateModel: viewmodel.AccidentInsuranceRateModel,
            historyAccidentInsuranceDto: model.HistoryAccidentInsuranceDto, companyCode: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            var data = {
                accidentInsuranceRate: service.convertAccidentInsuranceRateModelDTO(accidentInsuranceRateModel,
                    historyAccidentInsuranceDto),
                companyCode: companyCode
            };
            nts.uk.request.ajax(paths.updateAccidentInsuranceRate, data)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        //Function connection service find All HistoryAccidentInsurance 
        export function findAllHistoryAccidentInsuranceRate(): JQueryPromise<Array<model.HistoryAccidentInsuranceDto>> {
            var dfd = $.Deferred<Array<model.HistoryAccidentInsuranceDto>>();
            nts.uk.request.ajax(paths.findAllHistoryAccidentInsuranceRate)
                .done(function(res: Array<model.HistoryAccidentInsuranceDto>) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        //Function connection service find HistoryAccidentInsurance
        export function findHistoryAccidentInsuranceRate(historyId: string): JQueryPromise<model.HistoryAccidentInsuranceDto> {
            var dfd = $.Deferred<model.HistoryAccidentInsuranceDto>();
            nts.uk.request.ajax(paths.findHistoryAccidentInsuranceRate + "/" + historyId)
                .done(function(res: model.HistoryAccidentInsuranceDto) {
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
            historyInsurance: model.HistoryUnemployeeInsuranceDto): model.UnemployeeInsuranceRateDto {
            var unemployeeInsuranceRateDto: model.UnemployeeInsuranceRateDto;
            unemployeeInsuranceRateDto = new model.UnemployeeInsuranceRateDto();
            unemployeeInsuranceRateDto.historyInsurance = historyInsurance;
            unemployeeInsuranceRateDto.version = unemployeeInsuranceRateModel.version();
            unemployeeInsuranceRateDto.rateItems = [];
            unemployeeInsuranceRateDto.rateItems.push(service.convertUnemployeeInsuranceRateItemModelDTO(model.CareerGroupDto.Agroforestry, unemployeeInsuranceRateModel.unemployeeInsuranceRateItemAgroforestryModel));
            unemployeeInsuranceRateDto.rateItems.push(service.convertUnemployeeInsuranceRateItemModelDTO(model.CareerGroupDto.Contruction, unemployeeInsuranceRateModel.unemployeeInsuranceRateItemContructionModel));
            unemployeeInsuranceRateDto.rateItems.push(service.convertUnemployeeInsuranceRateItemModelDTO(model.CareerGroupDto.Other, unemployeeInsuranceRateModel.unemployeeInsuranceRateItemOtherModel));
            return unemployeeInsuranceRateDto;
        }
        //Function convert Model => DTO (InsuBizRateItemModel) 
        export function convertInsuBizRateItemModelDTO(insuBizType: number, accidentInsuranceRateDetailModel: viewmodel.AccidentInsuranceRateDetailModel)
            : model.InsuBizRateItemDto {
            var insuBizRateItemDto: model.InsuBizRateItemDto;
            insuBizRateItemDto = new model.InsuBizRateItemDto(insuBizType,
                accidentInsuranceRateDetailModel.insuRate(), accidentInsuranceRateDetailModel.insuRound(),
                accidentInsuranceRateDetailModel.insuranceBusinessType());
            return insuBizRateItemDto;
        }
        //Function convert Model => DTO (AccidentInsuranceModel)
        export function convertAccidentInsuranceRateModelDTO(accidentInsuranceRateModel: viewmodel.AccidentInsuranceRateModel,
            historyInsurance: model.HistoryAccidentInsuranceDto): model.AccidentInsuranceRateDto {
            var accidentInsuranceRateDto: model.AccidentInsuranceRateDto;
            accidentInsuranceRateDto = new model.AccidentInsuranceRateDto();
            accidentInsuranceRateDto.historyInsurance = historyInsurance;
            accidentInsuranceRateDto.version = accidentInsuranceRateModel.version();
            accidentInsuranceRateDto.rateItems = [];
            accidentInsuranceRateDto.rateItems.push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz1St, accidentInsuranceRateModel.accidentInsuranceRateBiz1StModel));
            accidentInsuranceRateDto.rateItems.push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz2Nd, accidentInsuranceRateModel.accidentInsuranceRateBiz2NdModel));
            accidentInsuranceRateDto.rateItems.push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz3Rd, accidentInsuranceRateModel.accidentInsuranceRateBiz3RdModel));
            accidentInsuranceRateDto.rateItems.push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz4Th, accidentInsuranceRateModel.accidentInsuranceRateBiz4ThModel));
            accidentInsuranceRateDto.rateItems.push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz5Th, accidentInsuranceRateModel.accidentInsuranceRateBiz5ThModel));
            accidentInsuranceRateDto.rateItems.push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz6Th, accidentInsuranceRateModel.accidentInsuranceRateBiz6ThModel));
            accidentInsuranceRateDto.rateItems.push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz7Th, accidentInsuranceRateModel.accidentInsuranceRateBiz7ThModel));
            accidentInsuranceRateDto.rateItems.push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz8Th, accidentInsuranceRateModel.accidentInsuranceRateBiz8ThModel));
            accidentInsuranceRateDto.rateItems.push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz9Th, accidentInsuranceRateModel.accidentInsuranceRateBiz9ThModel));
            accidentInsuranceRateDto.rateItems.push(service.convertInsuBizRateItemModelDTO(model.BusinessTypeEnumDto.Biz10Th, accidentInsuranceRateModel.accidentInsuranceRateBiz10ThModel));
            return accidentInsuranceRateDto;
        }
        //Function find all Insurance Business Type
        export function findAllInsuranceBusinessType(companyCode: string): JQueryPromise<model.InsuranceBusinessTypeUpdateDto> {
            var dfd = $.Deferred<model.InsuranceBusinessTypeUpdateDto>();
            nts.uk.request.ajax(paths.findAllInsuranceBusinessType + "/" + companyCode)
                .done(function(res: model.InsuranceBusinessTypeUpdateDto) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
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
                startMonthRage: string;
                endMonthRage: string;
                inforMonthRage: string;
                constructor(historyId: string, startMonthRage: string,
                    endMonthRage: string) {
                    this.historyId = historyId;
                    this.startMonthRage = startMonthRage;
                    this.endMonthRage = endMonthRage;
                }
            }
            export class HistoryUnemployeeInsuranceDto extends HistoryInsuranceDto {

            }
            export class UnemployeeInsuranceRateDto {
                historyInsurance: HistoryUnemployeeInsuranceDto;
                rateItems: UnemployeeInsuranceRateItemDto[];
                version: number;
            }

            export class HistoryAccidentInsuranceDto extends HistoryInsuranceDto {

            }
            export class AccidentInsuranceRateDto {
                historyInsurance: HistoryUnemployeeInsuranceDto;
                rateItems: InsuBizRateItemDto[];
                version: number;
            }
            export class InsuBizRateItemDto {
                /** The insu biz type. */
                insuBizType: number;
                /** The insu rate. */
                insuRate: number;
                /** The insu round. */
                insuRound: number;
                /** The insurance business type. */
                insuranceBusinessType: string;
                constructor(insuBizType: number, insuRate: number, insuRound: number, insuranceBusinessType: string) {
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
            export class InsuranceBusinessTypeUpdateDto {
                bizNameBiz1St: string;
                /** The biz name biz 2 nd. */
                bizNameBiz2Nd: string;
                /** The biz name biz 3 rd. */
                bizNameBiz3Rd: string;
                /** The biz name biz 4 th. */
                bizNameBiz4Th: string;
                /** The biz name biz 5 th. */
                bizNameBiz5Th: string;
                /** The biz name biz 6 th. */
                bizNameBiz6Th: string;
                /** The biz name biz 7 th. */
                bizNameBiz7Th: string;
                /** The biz name biz 8 th. */
                bizNameBiz8Th: string;
                /** The biz name biz 9 th. */
                bizNameBiz9Th: string;
                /** The biz name biz 10 th. */
                bizNameBiz10Th: string;
                /** version*/
                version: number;
                constructor(bizNameBiz1St: string, bizNameBiz2Nd: string, bizNameBiz3Rd: string, bizNameBiz4Th: string, bizNameBiz5Th: string,
                    bizNameBiz6Th: string, bizNameBiz7Th: string, bizNameBiz8Th: string, bizNameBiz9Th: string, bizNameBiz10Th: string, version: number) {
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
            }
        }
    }
}