module qmm012.i {
    export module service {
        var paths: any = {
            findAllItemSalaryBD: "pr/core/itemsalarybd/find",
            findAllItemDeductBD: "pr/core/itemdeductbd/find",
        }
        export function findItemSalaryBD(itemCode): JQueryPromise<Array<model.ItemBD>> {
            var dfd = $.Deferred<Array<model.ItemBD>>();
            nts.uk.request.ajax(paths.findAllItemSalaryBD + "/" + itemCode)
                .done(function(res: Array<model.ItemBD>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        export function findAllItemDeductBD(itemCode): JQueryPromise<Array<model.ItemBD>> {
            var dfd = $.Deferred<Array<model.ItemBD>>();
            nts.uk.request.ajax(paths.findAllItemDeductBD + "/" + itemCode)
                .done(function(res: Array<model.ItemBD>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        export module model {
            export class ItemBD {
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