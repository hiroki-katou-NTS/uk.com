module nts.uk.pr.view.qmm011.a {
    export module service {
        export module model {
            export function convertdata(yearmonth: YearMonth): string {
                var viewmonth = '';
                if (yearmonth.month < 0) {
                    viewmonth = '0' + yearmonth.month;
                } else {
                    viewmonth = '' + yearmonth.month;
                }
                return '' + yearmonth.year + '/' + viewmonth;
            }
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
        }
    }
}