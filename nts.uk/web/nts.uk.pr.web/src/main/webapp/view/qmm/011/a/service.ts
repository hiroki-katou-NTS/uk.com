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
            
            export enum CareerGroup {
                /** The Agroforestry. */
                Agroforestry = 0,
                /** The Contruction. */
                Contruction = 1,
                /** The Other. */
                Other = 2
            }
        }
    }
}