module nts.uk.pr.view.qmm011.a {
    export module service {

        var paths: any = {
            findAllHisotryUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/findallHistory",
            findHisotryUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/findHistory",
            detailHistoryUnemployeeInsuranceRate: "pr/insurance/labor/unemployeerate/detailHistory",
            findAllHistoryAccidentInsuranceRate: "pr/insurance/labor/accidentrate/findallHistory",
            findHistoryAccidentInsuranceRate: "pr/insurance/labor/accidentrate/findHistory",
            detailHistoryAccidentInsuranceRate: "pr/insurance/labor/accidentrate/detailHistory"
        };

        //Function connection service FindAll Labor Insurance Office
        export function findAllHisotryUnemployeeInsuranceRate(): JQueryPromise<Array<any>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.findAllHisotryUnemployeeInsuranceRate)
                .done(function(res: Array<any>) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        export function findHisotryUnemployeeInsuranceRate(historyId: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.findHisotryUnemployeeInsuranceRate + "/" + historyId)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        export function detailHistoryUnemployeeInsuranceRate(historyId: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.detailHistoryUnemployeeInsuranceRate + "/" + historyId)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();

        }

        export function findAllHistoryAccidentInsuranceRate(): JQueryPromise<Array<any>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.findAllHistoryAccidentInsuranceRate)
                .done(function(res: Array<any>) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        export function findHistoryAccidentInsuranceRate(historyId: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.findHistoryAccidentInsuranceRate + "/" + historyId)
                .done(function(res: any) {
                    dfd.resolve(res);
                    //xyz
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        export function detailHistoryAccidentInsuranceRate(historyId: string): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.detailHistoryAccidentInsuranceRate + "/" + historyId)
                .done(function(res: any) {
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
            export class RoundingMethod {
                code: string;
                name: string;
                constructor(code: string, name: string) {
                    this.code = code;
                    this.name = name;
                }
            }
            export class UnemployeeInsuranceRateItemSetting {
                roundAtr: string;
                rate: number;
                constructor(roundAtr: string, rate: number) {
                    this.roundAtr = roundAtr;
                    this.rate = rate;
                }
            }
            export class UnemployeeInsuranceRateItem {
                careerGroup: string;
                companySetting: UnemployeeInsuranceRateItemSetting;
                personalSetting: UnemployeeInsuranceRateItemSetting;
                constructor(careerGroup: string, companySetting: UnemployeeInsuranceRateItemSetting,
                    personalSetting: UnemployeeInsuranceRateItemSetting) {
                    this.careerGroup = careerGroup;
                    this.companySetting = companySetting;
                    this.personalSetting = personalSetting;
                }
            }
            export class HistoryInsuranceRateDto {
                historyId: string;
                companyCode: string;
                monthRage: MonthRange;
                startMonthRage: string;
                endMonthRage: string;
                inforMonthRage: string;
                constructor(historyId: string, companyCode: string, monthRage: MonthRange, startMonthRage: string,
                    endMonthRage: string) {
                    this.historyId = historyId;
                    this.companyCode = companyCode;
                    this.monthRage = monthRage;
                    this.startMonthRage = startMonthRage;
                    this.endMonthRage = endMonthRage;
                }
            }
            export class HistoryUnemployeeInsuranceRateDto extends HistoryInsuranceRateDto {

            }
            export class UnemployeeInsuranceRateDto {
                historyId: string;
                companyCode: string;
                rateItems: UnemployeeInsuranceRateItem[];
            }

            export class HistoryAccidentInsuranceRateDto extends HistoryInsuranceRateDto {

            }
            export class AccidentInsuranceRateDto {
                historyId: string;
                companyCode: string;
                rateItems: InsuBizRateItem[];
            }
            export class InsuBizRateItem {
                /** The insu biz type. */
                insuBizType: number;
                /** The insu rate. */
                insuRate: number;
                /** The insu round. */
                insuRound: string;
                constructor(insuBizType: number, insuRate: number, insuRound: string) {
                    this.insuBizType = insuBizType;
                    this.insuRate = insuRate;
                    this.insuRound = insuRound;
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
            export enum CareerGroup {
                Agroforestry = "Agroforestry",
                Contruction = "Contruction",
                Other = "Other"
            }
            export enum BusinessTypeEnum {
                /** The Biz 1 st. */
                Biz1St = "Biz1St",
                /** The Biz 2 nd. */
                Biz2Nd = "Biz2Nd",
                /** The Biz 3 rd. */
                Biz3Rd = "Biz3Rd",
                /** The Biz 4 th. */
                Biz4Th = "Biz4Th",
                /** The Biz 5 th. */
                Biz5Th = "Biz5Th",
                /** The Biz 6 th. */
                Biz6Th = "Biz6Th",
                /** The Biz 7 th. */
                Biz7Th = "Biz7Th",
                /** The Biz 8 th. */
                Biz8Th = "Biz8Th",
                /** The Biz 9 th. */
                Biz9Th = "Biz9Th",
                /** The Biz 10 th. */
                Biz10Th = "Biz10Th"

            }
            export enum TypeHistory {
                HistoryUnemployee = 1,
                HistoryAccident = 2
            }
        }
    }
}