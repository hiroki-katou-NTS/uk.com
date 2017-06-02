module qmm019.f.attendItem.service {
    var paths = {
        getItemAttend: "pr/core/itemattend/find/{0}",
        getItemAttendRegInfo: "pr/core/itemattend/findItemAttendRegInfo/{0}",
    };
    export function getAttendItem(itemCode): JQueryPromise<model.AttendItemModel> {
        let dfd = $.Deferred<model.AttendItemModel>();
        var _path = nts.uk.text.format(paths.getItemAttend, itemCode);
        nts.uk.request.ajax(_path).done(function(res: model.AttendItemModel) {
            dfd.resolve(res);
        })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    export function getItemAttendRegInfo(itemCode): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        var _path = nts.uk.text.format(paths.getItemAttendRegInfo, itemCode);
        nts.uk.request.ajax(_path).done(function(res) {
            dfd.resolve(res);
        })
            .fail(function(res) {
                dfd.reject(res.message);
            });
        return dfd.promise();
    }
    export module model {
        export class AttendItemModel {
            avePayAtr: number;
            itemAtr: number;
            errRangeLowAtr: number;
            errRangeLow: number;
            errRangeHighAtr: number;
            errRangeHigh: number;
            alRangeLowAtr: number;
            alRangeLow: number;
            alRangeHighAtr: number;
            alRangeHigh: number;
            workDaysScopeAtr: number;
            memo: string;
        }
        export class ItemAttendKo {
            avePayAtr: KnockoutObservable<any>;
            itemAtr: KnockoutObservable<any>;
            errRangeLowAtr: KnockoutObservable<any>;
            errRangeLow: KnockoutObservable<number>;
            errRangeHighAtr: KnockoutObservable<any>;
            errRangeHigh: KnockoutObservable<number>;
            alRangeLowAtr: KnockoutObservable<any>;
            alRangeLow: KnockoutObservable<number>;
            alRangeHighAtr: KnockoutObservable<any>;
            alRangeHigh: KnockoutObservable<number>;
            workDaysScopeAtr: KnockoutObservable<any>;
            memo: KnockoutObservable<string>;
            constructor(data) {
                this.avePayAtr = ko.observable(data.avePayAtr);
                this.itemAtr = ko.observable(data.itemAtr.value);
                this.errRangeLowAtr = ko.observable(data.errRangeLowAtr);
                this.errRangeLow = ko.observable(data.errRangeLow);
                this.errRangeHighAtr = ko.observable(data.errRangeHighAtr);
                this.errRangeHigh = ko.observable(data.errRangeHigh);
                this.alRangeLowAtr = ko.observable(data.alRangeLowAtr);
                this.alRangeLow = ko.observable(data.alRangeLow);
                this.alRangeHighAtr = ko.observable(data.alRangeHighAtr);
                this.alRangeHigh = ko.observable(data.alRangeHigh);
                this.workDaysScopeAtr = ko.observable(data.workDaysScopeAtr);
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
            fixAtr: KnockoutObservable<number>;
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
        export class ItemRegInfo {
            itemAttend: KnockoutObservable<ItemAttendKo>;
            itemMaster: KnockoutObservable<ItemMasterKo>;
            constructor(data) {
                this.itemAttend = ko.observable(new ItemAttendKo(data.itemAttend));
                this.itemMaster = ko.observable(new ItemMasterKo(data.itemMaster));

            }
        }
    }
}