module nts.uk.pr.view.qmm007.a {
    export module service {
        var paths: any = {
            getPaymentDateProcessingList: "pr/proto/paymentdatemaster/processing/findall"
        }

        export function getPaymentDateProcessingList(): JQueryPromise<Array<any>> {
            var dfd = $.Deferred<Array<any>>();
            nts.uk.request.ajax(paths.getPaymentDateProcessingList)
                .done(function(res: Array<any>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        /**
        * Model namespace.
        */
        export module model {
            export class UnitPriceDto {
                code: string;
                name: string;
            }

            export class UnitPriceHistoryDto {
                unitPriceCode: number;
                unitPriceName: string;
                startDate: DateTimeDto;
                budget: number;
                fixPaySettingType: SettingType;
                fixPayAtr: ApplySetting;
                fixPayAtrMonthly: ApplySetting;
                fixPayAtrDayMonth: ApplySetting;
                fixPayAtrDaily: ApplySetting;
                fixPayAtrHourly: ApplySetting;
                memo: string;

                /*constructor(unitPriceCode: number,
                    startDate: DateTimeDto,
                    budget: number,
                    fixPaySettingType: SettingType,
                    fixPayAtr: ApplySetting,
                    fixPayAtrMonthly: ApplySetting,
                    fixPayAtrDayMonth: ApplySetting,
                    fixPayAtrDaily: ApplySetting,
                    fixPayAtrHourly: ApplySetting,
                    memo: string) {
                    this.unitPriceCode = unitPriceCode;
                    this.startDate = startDate;
                    this.budget = budget;
                    this.fixPaySettingType = fixPaySettingType;
                    this.fixPayAtr = fixPayAtr;
                    this.fixPayAtrMonthly = fixPayAtrMonthly;
                    this.fixPayAtrDayMonth = fixPayAtrDayMonth;
                    this.fixPayAtrDaily = fixPayAtrDaily;
                    this.fixPayAtrHourly = fixPayAtrHourly;
                }*/
            }

            export class DateTimeDto {
                year: number;
                month: number;
                day: number;
                hour: number;
                minute: number;

                /*constructor(date: Date) {
                    this.year = date.getFullYear();
                    this.month = date.getMonth();
                    this.day = date.getDate();
                    this.hour = date.getHours();
                    this.minute = date.getMinutes();
                }*/
            }

            export enum SettingType {
                Company = 0,
                Contract = 1
            }

            export enum ApplySetting {
                Apply = 1,
                NotApply = 0
            }

        }
    }
}
