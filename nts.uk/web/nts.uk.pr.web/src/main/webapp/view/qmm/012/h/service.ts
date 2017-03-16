module qmm012.h {
    export module service {
        var paths: any = {
            findItemSalaryPeriod: "pr/core/itemsalaryperiod/find",
        }
        export function findItemSalaryPeriod(itemCode): JQueryPromise<model.ItemSalaryPeriod> {
            var dfd = $.Deferred<model.ItemSalaryPeriod>();
            nts.uk.request.ajax(paths.findItemSalaryPeriod + "/" + itemCode)
                .done(function(res: model.ItemSalaryPeriod) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        export module model {
            export class ItemSalaryPeriod {
                itemCode: string;
                periodAtr: number;
                strY: number;
                endY: number;
                cycleAtr: number;
                cycle01Atr: number;
                cycle02Atr: number;
                cycle03Atr: number;
                cycle04Atr: number;
                cycle05Atr: number;
                cycle06Atr: number;
                cycle07Atr: number;
                cycle08Atr: number;
                cycle09Atr: number;
                cycle10Atr: number;
                cycle11Atr: number;
                cycle12Atr: number;
                constructor(
                    itemCode: string,
                    periodAtr: number,
                    strY: number,
                    endY: number,
                    cycleAtr: number,
                    cycle01Atr: number,
                    cycle02Atr: number,
                    cycle03Atr: number,
                    cycle04Atr: number,
                    cycle05Atr: number,
                    cycle06Atr: number,
                    cycle07Atr: number,
                    cycle08Atr: number,
                    cycle09Atr: number,
                    cycle10Atr: number,
                    cycle11Atr: number,
                    cycle12Atr: number) {
                    this.itemCode = itemCode;
                    this.periodAtr = periodAtr;
                    this.strY = strY;
                    this.endY = endY;
                    this.cycleAtr = cycleAtr;
                    this.cycle01Atr = cycle01Atr;
                    this.cycle02Atr = cycle02Atr;
                    this.cycle03Atr = cycle03Atr;
                    this.cycle04Atr = cycle04Atr;
                    this.cycle05Atr = cycle05Atr;
                    this.cycle06Atr = cycle06Atr;
                    this.cycle07Atr = cycle07Atr;
                    this.cycle08Atr = cycle08Atr;
                    this.cycle09Atr = cycle09Atr;
                    this.cycle10Atr = cycle10Atr;
                    this.cycle11Atr = cycle11Atr;
                    this.cycle12Atr = cycle12Atr;
                }
            }
        }
    }
}