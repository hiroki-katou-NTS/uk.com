module qmm012.i {
    export module service {
        var paths: any = {
            findAllItemSalaryBD: "pr/core/itemsalarybd/find",
            findAllItemDeductBD: "pr/core/itemdeductbd/find"
        }
        function findAllItemSalaryBD(itemCode): JQueryPromise<Array<model.ItemBD>> {
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
        export function findAllItemBD(ItemMaster: qmm012.b.service.model.ItemMaster): JQueryPromise<Array<model.ItemBD>> {
            var dfd = $.Deferred<Array<model.ItemBD>>();
            let categoryAtr = ItemMaster.categoryAtr;
            let itemCode = ItemMaster.itemCode;
            if (categoryAtr == 0) {
                findAllItemSalaryBD(itemCode).done(function(itemBDs: Array<model.ItemBD>) {
                    dfd.resolve(itemBDs);
                });
            } else {
                findAllItemDeductBD(itemCode).done(function(itemBDs: Array<model.ItemBD>) {
                    dfd.resolve(itemBDs);
                });
            }
            return dfd.promise();
        }
        function findAllItemDeductBD(itemCode): JQueryPromise<Array<model.ItemBD>> {
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

                itemBreakdownCode: string;
                itemBreakdownName: string;
                itemBreakdownAbName: string;
                uniteCode: string;
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

                    itemBreakdownCode: string,
                    itemBreakdownName: string,
                    itemBreakdownAbName: string,
                    uniteCode: string,
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
                    this.itemBreakdownCode = itemBreakdownCode;
                    this.itemBreakdownName = itemBreakdownName;
                    this.itemBreakdownAbName = itemBreakdownAbName;
                    this.uniteCode = uniteCode;
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