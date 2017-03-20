module qmm012.c {
    export module service {
        var paths: any = {
            findItemSalary: "pr/core/itemsalary/find",
        }

        export function findItemSalary(itemCode): JQueryPromise<model.ItemSalaryModel> {
            var dfd = $.Deferred<model.ItemSalaryModel>();
            nts.uk.request.ajax(paths.findItemSalary + "/" + itemCode)
                .done(function(res: model.ItemSalaryModel) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        export module model {
            export class ItemSalaryModel {
                taxAtr: number;
                socialInsAtr: number;
                laborInsAtr: number;
                fixPayAtr: number;
                applyForAllEmpFlg: number;
                applyForMonthlyPayEmp: number;
                applyForDaymonthlyPayEmp: number;
                applyForDaylyPayEmp: number;
                applyForHourlyPayEmp: number;
                avePayAtr: number;
                errRangeLowAtr: number;
                errRangeLow: number;
                errRangeHighAtr: number;
                errRangeHigh: number;
                alRangeLowAtr: number;
                alRangeLow: number;
                alRangeHighAtr: number;
                alRangeHigh: number;
                memo: string;
                limitMnyAtr: number;
                limitMnyRefItemCd: string;
                limitMny: number;
                constructor(
                    taxAtr: number,
                    socialInsAtr: number,
                    laborInsAtr: number,
                    fixPayAtr: number,
                    applyForAllEmpFlg: number,
                    applyForMonthlyPayEmp: number,
                    applyForDaymonthlyPayEmp: number,
                    applyForDaylyPayEmp: number,
                    applyForHourlyPayEmp: number,
                    avePayAtr: number,
                    errRangeLowAtr: number,
                    errRangeLow: number,
                    errRangeHighAtr: number,
                    errRangeHigh: number,
                    alRangeLowAtr: number,
                    alRangeLow: number,
                    alRangeHighAtr: number,
                    alRangeHigh: number,
                    memo: string,
                    limitMnyAtr: number,
                    limitMnyRefItemCd: string,
                    limitMny: number

                ) {
                    this.taxAtr = taxAtr;
                    this.socialInsAtr = socialInsAtr;
                    this.laborInsAtr = laborInsAtr;
                    this.fixPayAtr = fixPayAtr;
                    this.applyForAllEmpFlg = applyForAllEmpFlg;
                    this.applyForMonthlyPayEmp = applyForMonthlyPayEmp;
                    this.applyForDaymonthlyPayEmp = applyForDaymonthlyPayEmp;
                    this.applyForDaylyPayEmp = applyForDaylyPayEmp;
                    this.applyForHourlyPayEmp = applyForHourlyPayEmp;
                    this.avePayAtr = avePayAtr;
                    this.errRangeLowAtr = errRangeLowAtr;
                    this.errRangeLow = errRangeLow;
                    this.errRangeHighAtr = errRangeHighAtr;
                    this.errRangeHigh = errRangeHigh;
                    this.alRangeLowAtr = alRangeLowAtr;
                    this.alRangeLow = alRangeLow;
                    this.alRangeHighAtr = alRangeHighAtr;
                    this.alRangeHigh = alRangeHigh;
                    this.memo = memo;
                    this.limitMnyAtr = limitMnyAtr;
                    this.limitMnyRefItemCd = limitMnyRefItemCd;
                    this.limitMny = limitMny;


                };
            }
        }
    }

}



