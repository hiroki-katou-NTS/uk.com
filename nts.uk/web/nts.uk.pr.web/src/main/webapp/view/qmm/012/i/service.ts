module qmm012.i {
    export module service {
        var paths: any = {
            findItemSalaryBD: "pr/core/itemsalarybd/find",
        }
        export function findItemSalaryBD(itemCode): JQueryPromise<Array<model.ItemSalaryBD>> {
            var dfd = $.Deferred<Array<model.ItemSalaryBD>>();
            nts.uk.request.ajax(paths.findItemSalaryBD + "/" + itemCode)
                .done(function(res: Array<model.ItemSalaryBD>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        export module model {
            export class ItemSalaryBD {
                itemBreakdownCd: string;
                itemBreakdownName: string;
                itemBreakdownAbName: string;
                uniteCd: string;
                zeroDispSet: number;
                itemDispAtr: number;
                errRangeLowAtr: number;
                errRangeLow: number;
                errRangeHighAtr: number;
                errRangeHigh: number;
                alRangeLowAtr: number;
                alRangeLow: number;
                alRangeHighAtr: number;
                alRangeHigh: number;
                constructor(
                    itemBreakdownCd: string,
                    itemBreakdownName: string,
                    itemBreakdownAbName: string,
                    uniteCd: string,
                    zeroDispSet: number,
                    itemDispAtr: number,
                    errRangeLowAtr: number,
                    errRangeLow: number,
                    errRangeHighAtr: number,
                    errRangeHigh: number,
                    alRangeLowAtr: number,
                    alRangeLow: number,
                    alRangeHighAtr: number,
                    alRangeHigh: number
                ) {
                    this.itemBreakdownCd = itemBreakdownCd;
                    this.itemBreakdownName = itemBreakdownName;
                    this.itemBreakdownAbName = itemBreakdownAbName;
                    this.uniteCd = uniteCd;
                    this.zeroDispSet = zeroDispSet;
                    this.itemDispAtr = itemDispAtr;
                    this.errRangeLowAtr = errRangeLowAtr;
                    this.errRangeLow = errRangeLow;
                    this.errRangeHighAtr = errRangeHighAtr;
                    this.errRangeHigh = errRangeHigh;
                    this.alRangeLowAtr = alRangeLowAtr;
                    this.alRangeLow = alRangeLow;
                    this.alRangeHighAtr = alRangeHighAtr;
                    this.alRangeHigh = alRangeHigh;

                }
            }
        }

    }
}