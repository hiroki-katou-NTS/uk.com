module qmm012.b {
    export module service {
        var paths: any = {
            findAllItemMaster: "pr/core/item/findAllItemMaster",
            deleteItemMaster: "pr/core/item/delete",
            addItemMaster: "pr/core/item/add",
            updateItemMaster: "pr/core/item/update",
            findItemByCategoryAndCode: "pr/core/item/find",
        }

        export function findAllItemMaster(ctgAtr, dispSet): JQueryPromise<Array<model.ItemMaster>> {
            var dfd = $.Deferred<Array<model.ItemMaster>>();
            nts.uk.request.ajax(paths.findAllItemMaster + "/" + ctgAtr + "/" + dispSet)
                .done(function(res: Array<model.ItemMaster>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        export function findItemByCategoryAndCode(ctgAtr, itemCode): JQueryPromise<model.ItemMaster> {
            var dfd = $.Deferred<model.ItemMaster>();
            nts.uk.request.ajax(paths.findItemByCategoryAndCode + "/" + ctgAtr + "/" + itemCode)
                .done(function(res: model.ItemMaster) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }



        export function deleteItemMaster(ItemMaster: model.ItemMaster): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.deleteItemMaster, ItemMaster)
                .done(function(res: any) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        export function addItemMaster(ItemMaster: model.ItemMaster): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.addItemMaster, ItemMaster)
                .done(function(res: any) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }
        export function updateItemMaster(ItemMaster: model.ItemMaster): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            nts.uk.request.ajax(paths.updateItemMaster, ItemMaster)
                .done(function(res: any) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }



        export module model {
            export class ItemMaster {
                itemSalary: qmm012.c.service.model.ItemSalary;
                itemDeduct: qmm012.d.service.model.ItemDeduct;
                itemAttend: qmm012.e.service.model.ItemAttend;
                itemPeriod: qmm012.h.service.model.ItemPeriod;
                itemBDs: Array<qmm012.i.service.model.ItemBD>;
                itemCode: string;
                itemName: string;
                categoryAtr: number;
                categoryAtrName: string;
                itemAbName: string;
                itemAbNameO: string;
                itemAbNameE: string;
                displaySet: number;
                uniteCode: string;
                zeroDisplaySet: number;
                itemDisplayAtr: number;
                fixAtr: number;
                constructor(
                    itemCode: string,
                    itemName: string,
                    categoryAtr: number,
                    categoryAtrName: string,
                    itemAbName: string,
                    itemAbNameO: string,
                    itemAbNameE: string,
                    displaySet: number,
                    uniteCode: string,
                    zeroDisplaySet: number,
                    itemDisplayAtr: number,
                    fixAtr: number
                ) {
                    this.itemCode = itemCode;
                    this.itemName = itemName;
                    this.categoryAtr = categoryAtr;
                    this.categoryAtrName = categoryAtrName;
                    this.itemAbName = itemAbName;
                    this.itemAbNameO = itemAbNameO;
                    this.itemAbNameE = itemAbNameE;
                    this.displaySet = displaySet;
                    this.uniteCode = uniteCode;
                    this.zeroDisplaySet = zeroDisplaySet;
                    this.itemDisplayAtr = itemDisplayAtr;
                    this.fixAtr = fixAtr;
                }
            }

        }
    }

}



