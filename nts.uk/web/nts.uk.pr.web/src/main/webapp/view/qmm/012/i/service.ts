module qmm012.i {
    export module service {
        var paths: any = {
            findAllItemSalaryBD: "pr/core/itemsalarybd/find",
            findAllItemDeductBD: "pr/core/itemdeductbd/find",
            updateItemSalaryBD: "pr/core/itemsalarybd/update",
            updateItemDeductBD: "pr/core/itemdeductbd/update",
            addItemSalaryBD: "pr/core/itemsalarybd/add",
            addItemDeductBD: "pr/core/itemdeductbd/add",
            deleteItemSalaryBD: "pr/core/itemsalarybd/delete",
            deleteItemDeductBD: "pr/core/itemdeductbd/delete",
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

        export function deleteItemBD(ItemMaster: qmm012.b.service.model.ItemMaster, itemBD: model.ItemBD): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            let categoryAtr = ItemMaster.categoryAtr;
            let itemCode = ItemMaster.itemCode;
            if (categoryAtr == 0) {
                deleteItemSalaryBD(itemBD).done(function(res: any) {
                    dfd.resolve(res);
                });
            }
            else {
                deleteItemDeductBD(itemBD).done(function(res: any) {
                    dfd.resolve(res);
                });
            }
            return dfd.promise();
        }
        export function addItemBD(ItemMaster: qmm012.b.service.model.ItemMaster, itemBD: model.ItemBD): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            let categoryAtr = ItemMaster.categoryAtr;
            let itemCode = ItemMaster.itemCode;
            if (categoryAtr == 0) {
                addItemSalaryBD(itemBD).done(function(res: any) {
                    dfd.resolve(res);
                });
            }
            else {
                addItemDeductBD(itemBD).done(function(res: any) {
                    dfd.resolve(res);
                });
            }
            return dfd.promise();
        }

        function deleteItemDeductBD(itemBD: model.ItemBD): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.deleteItemDeductBD, itemBD)
                .done(function(res: any) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        function deleteItemSalaryBD(itemBD: model.ItemBD): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.deleteItemSalaryBD, itemBD)
                .done(function(res: any) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        function addItemDeductBD(itemBD: model.ItemBD): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.addItemDeductBD, itemBD)
                .done(function(res: any) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        function addItemSalaryBD(itemBD: model.ItemBD): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.addItemSalaryBD, itemBD)
                .done(function(res: any) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        export function updateItemBD(ItemMaster: qmm012.b.service.model.ItemMaster, itemBD: model.ItemBD): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            let categoryAtr = ItemMaster.categoryAtr;
            let itemCode = ItemMaster.itemCode;
            if (categoryAtr == 0) {
                updateItemSalaryBD(itemBD).done(function(res: any) {
                    dfd.resolve(res);
                });
            }
            else {
                updateItemDeductBD(itemBD).done(function(res: any) {
                    dfd.resolve(res);
                });
            }
            return dfd.promise();
        }
        function updateItemDeductBD(itemBD: model.ItemBD): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.updateItemDeductBD, itemBD)
                .done(function(res: any) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        function updateItemSalaryBD(itemBD: model.ItemBD): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.updateItemSalaryBD, itemBD)
                .done(function(res: any) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        export module model {
            export class ItemBD {
                itemCd: string;
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
                    itemCd: string,
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
                    this.itemCd = itemCd,
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