module qmm019.f.deductItem.service {
    var paths = {
        getItemDeduct: "pr/core/itemdeduct/find/{0}",
        getItemDeductRegInfo: "pr/core/itemdeduct/findItemDeductRegInfo/{0}",
    };
    export function getDeductItem(itemCode): JQueryPromise<model.DeductItemModel> {
        let dfd = $.Deferred<model.DeductItemModel>();
        var _path = nts.uk.text.format(paths.getItemDeduct, itemCode);
        nts.uk.request.ajax(_path).done(function(res: model.DeductItemModel) {
            dfd.resolve(res);
        })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();

    }
    export function getItemDeductRegInfo(itemCode): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        var _path = nts.uk.text.format(paths.getItemDeductRegInfo, itemCode);
        nts.uk.request.ajax(_path).done(function(res) {
            dfd.resolve(res);
        })
            .fail(function(res) {
                dfd.reject(res.message);
            });
        return dfd.promise();
    }
    export module model {
        export class DeductItemModel {
            deductAtr: number;
            errRangeLowAtr: number;
            errRangeLow: number;
            errRangeHighAtr: number;
            errRangeHigh: number;
            alRangeLowAtr: number;
            alRangeLow: number;
            alRangeHighAtr: number;
            alRangeHigh: number;
            memo: string;
        }
        export class ItemDeductKo {
            deductAtr: KnockoutObservable<any>;
            errRangeLowAtr: KnockoutObservable<any>;
            errRangeLow: KnockoutObservable<number>;
            errRangeHighAtr: KnockoutObservable<any>;
            errRangeHigh: KnockoutObservable<number>;
            alRangeLowAtr: KnockoutObservable<any>;
            alRangeLow: KnockoutObservable<number>;
            alRangeHighAtr: KnockoutObservable<any>;
            alRangeHigh: KnockoutObservable<number>;
            memo: KnockoutObservable<string>;
            constructor(data) {
                this.deductAtr = ko.observable(data.deductAtr);
                this.errRangeLowAtr = ko.observable(data.errRangeLowAtr);
                this.errRangeLow = ko.observable(data.errRangeLow);
                this.errRangeHighAtr = ko.observable(data.errRangeHighAtr);
                this.errRangeHigh = ko.observable(data.errRangeHigh);
                this.alRangeLowAtr = ko.observable(data.alRangeLowAtr);
                this.alRangeLow = ko.observable(data.alRangeHighAtr);
                this.alRangeHighAtr = ko.observable(data.itemCode);
                this.alRangeHigh = ko.observable(data.alRangeHigh);
                this.memo = ko.observable(data.memo);
            }
        }
        export class ItemMasterKo {
            itemCode: KnockoutObservable<string>;
            itemName: KnockoutObservable<string>;
            categoryAtr: KnockoutObservable<any>;
            categoryAtrName: KnockoutObservable<string>;
            itemAbName: KnockoutObservable<string>;
            itemAbNameO: KnockoutObservable<string>;
            itemAbNameE: KnockoutObservable<string>;
            displaySet: KnockoutObservable<number>;
            uniteCode: KnockoutObservable<string>;
            zeroDisplaySet: KnockoutObservable<any>;
            itemDisplayAtr: KnockoutObservable<any>;
            fixAtr: KnockoutObservable<any>;
            constructor(data) {
                this.itemCode = ko.observable(data.itemCode);
                this.itemName = ko.observable(data.itemName);
                this.categoryAtr = ko.observable(data.categoryAtr);
                this.categoryAtrName = ko.observable(data.categoryAtrName);
                this.itemAbName = ko.observable(data.itemAbName);
                this.itemAbNameO = ko.observable(data.itemAbNameO);
                this.itemAbNameE = ko.observable(data.itemAbNameE);
                this.displaySet = ko.observable(data.displaySet);
                this.uniteCode = ko.observable(data.uniteCode);
                this.zeroDisplaySet = ko.observable(data.zeroDisplaySet);
                this.itemDisplayAtr = ko.observable(data.itemDisplayAtr);
                this.fixAtr = ko.observable(data.fixAtr);
            }
        }
        export class ItemDeductBD {
            itemCode: string;
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
        }
        export class ItemRegInfo {
            itemDeduct: KnockoutObservable<ItemDeductKo>;
            itemMaster: KnockoutObservable<ItemMasterKo>;
            itemDeductBDList: KnockoutObservableArray<ItemDeductBD>;
            constructor(data) {
                this.itemDeduct = ko.observable(new ItemDeductKo(data.itemDeduct));
                this.itemMaster = ko.observable(new ItemMasterKo(data.itemMaster));
                let itemDeductBDList = ko.observableArray([]);
                for (let item of data.itemDeductBDList) {
                    itemDeductBDList().push(item);
                }
                this.itemDeductBDList = itemDeductBDList;
            }
        }
    }

}