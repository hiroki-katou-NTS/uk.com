module qmm012.b {
    export module service {
        var paths: any = {
            findAllItemMaster: "pr/core/item/findAllItemMaster",
            findItemperiod: "pr/core/itemperiod/find",
            deleteItemMaster: "pr/core/item/delete",
        }

        export function findAllItemMaster(): JQueryPromise<Array<model.ItemMasterModel>> {
            var dfd = $.Deferred<Array<model.ItemMasterModel>>();
            nts.uk.request.ajax(paths.findAllItemMaster)
                .done(function(res: Array<model.ItemMasterModel>) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        export function findItemperiod(categoryAtr, itemCode): JQueryPromise<model.ItemPeriodModel> {
            var dfd = $.Deferred<model.ItemPeriodModel>();
            nts.uk.request.ajax(paths.findItemperiod + "/" + categoryAtr + "/" + itemCode)
                .done(function(res: model.ItemPeriodModel) {
                    dfd.resolve(res);
                })
                .fail(function(res) {
                    dfd.reject(res);
                })
            return dfd.promise();
        }

        export function deleteItemMaster(ItemMaster: model.ItemMasterModel): JQueryPromise<any> {
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
        export module model {
            export class ItemMasterModel {
                itemCode: string;
                itemName: string;
                categoryAtrValue: number;
                categoryAtrName: String;
                itemAbName: string;
                itemAbNameO: string;
                itemAbNameE: string;
                displaySet: number;
                uniteCode: string;
                zeroDisplaySet: number;
                itemDisplayAtr: number;
                constructor(
                    itemCode: string,
                    itemName: string,
                    categoryAtrValue: number,
                    categoryAtrName: String,
                    itemAbName: string,
                    itemAbNameO: string,
                    itemAbNameE: string,
                    displaySet: number,
                    uniteCode: string,
                    zeroDisplaySet: number,
                    itemDisplayAtr: number
                ) {
                    this.itemCode = itemCode;
                    this.itemName = itemName;
                    this.categoryAtrValue = categoryAtrValue;
                    this.categoryAtrName = categoryAtrName;
                    this.itemAbName = itemAbName;
                    this.itemAbNameO = itemAbNameO;
                    this.itemAbNameE = itemAbNameE;
                    this.displaySet = displaySet;
                    this.uniteCode = uniteCode;
                    this.zeroDisplaySet = itemDisplayAtr;
                }
            }

            export class ItemPeriodModel {
                itemClass: number;
                itemCode: string;
                StartYear: number;
                ExpYear: number;
                cycleAtr: number;
                cycle1Atr: number;
                cycle2Atr: number;
                cycle3Atr: number;
                cycle4Atr: number;
                cycle5Atr: number;
                cycle6Atr: number;
                cycle7Atr: number;
                cycle8Atr: number;
                cycle9Atr: number;
                cycle10Atr: number;
                cycle11Atr: number;
                cycle12Atr: number;
                constructor(
                    itemClass: number,
                    itemCode: string,
                    StartYear: number,
                    ExpYear: number,
                    cycleAtr: number,
                    cycle1Atr: number,
                    cycle2Atr: number,
                    cycle3Atr: number,
                    cycle4Atr: number,
                    cycle5Atr: number,
                    cycle6Atr: number,
                    cycle7Atr: number,
                    cycle8Atr: number,
                    cycle9Atr: number,
                    cycle10Atr: number,
                    cycle11Atr: number,
                    cycle12Atr: number
                ) {
                    this.itemClass = itemClass;
                    this.itemCode = itemCode;
                    this.StartYear = StartYear;
                    this.ExpYear = ExpYear;
                    this.cycleAtr = cycleAtr;
                    this.cycle1Atr = cycle1Atr;
                    this.cycle2Atr = cycle2Atr;
                    this.cycle3Atr = cycle3Atr;
                    this.cycle4Atr = cycle4Atr;
                    this.cycle5Atr = cycle5Atr;
                    this.cycle6Atr = cycle6Atr;
                    this.cycle7Atr = cycle7Atr;
                    this.cycle8Atr = cycle8Atr;
                    this.cycle9Atr = cycle9Atr;
                    this.cycle10Atr = cycle10Atr;
                    this.cycle11Atr = cycle11Atr;
                    this.cycle12Atr = cycle12Atr

                }
            }
            export class categoryAtr {
                value: number;
                name: string;

                constructor(
                    value: number,
                    name: string) {
                    this.value = value;
                    this.name = name;
                }
            }
        }
    }

}



