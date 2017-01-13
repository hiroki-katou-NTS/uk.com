module nts.uk.pr.view.qmm011.a {
    export module service {
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
                code: number;
                name: string;
                constructor(code: number, name: string) {
                    this.code = code;
                    this.name = name;
                }
            }
            export class UnemployeeInsuranceRateItemSetting {
                roundAtr: number;
                rate: number;
                constructor(roundAtr: number, rate: number) {
                    this.roundAtr = roundAtr;
                    this.rate = rate;
                }
            }
            export class UnemployeeInsuranceRateItem {
                careerGroup: number;
                companySetting: UnemployeeInsuranceRateItemSetting;
                personalSetting: UnemployeeInsuranceRateItemSetting;
                constructor(careerGroup: number, companySetting: UnemployeeInsuranceRateItemSetting, personalSetting: UnemployeeInsuranceRateItemSetting) {
                    this.careerGroup = careerGroup;
                    this.companySetting = companySetting;
                    this.personalSetting = personalSetting;
                }
            }
            export class HistoryUnemployeeInsuranceRate {
                historyId: string;
                companyCode: string;
                monthRage: MonthRange;
                constructor(historyId: string, companyCode: string, monthRage: MonthRange) {
                    this.historyId = historyId;
                    this.companyCode = companyCode;
                    this.monthRage = monthRage;

                }
            }

            export class HistoryAccidentInsuranceRate {
                historyId: string;
                companyCode: string;
                monthRage: MonthRange;
                constructor(historyId: string, companyCode: string, monthRage: MonthRange) {
                    this.historyId = historyId;
                    this.companyCode = companyCode;
                    this.monthRage = monthRage;
                }
            }
            export class InsuBizRateItem {
                /** The insu biz type. */
                insuBizType: number;
                /** The insu rate. */
                insuRate: number;
                /** The insu round. */
                insuRound: number;
                constructor(insuBizType: number, insuRate: number, insuRound: number) {
                    this.insuBizType = insuBizType;
                    this.insuRate = insuRate;
                    this.insuRound = insuRound;
                }
            }
            export enum CareerGroup {
                /** The Agroforestry. */
                Agroforestry = 0,
                /** The Contruction. */
                Contruction = 1,
                /** The Other. */
                Other = 2
            }
            export enum BusinessTypeEnum {
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
        }
    }
}