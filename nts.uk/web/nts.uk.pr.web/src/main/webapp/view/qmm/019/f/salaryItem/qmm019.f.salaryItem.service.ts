module qmm019.f.salaryItem.service {
    var paths = {
        getItemSalary: "pr/core/itemsalary/find/{0}",
        getItemSalaryRegInfo: "pr/core/itemsalary/findItemSalaryRegInfo/{0}",
    };
    export function getSalaryItem(itemCode): JQueryPromise<model.ItemSalaryModel> {
        let dfd = $.Deferred<model.ItemSalaryModel>();
        var _path = nts.uk.text.format(paths.getItemSalary, itemCode);
        nts.uk.request.ajax(_path).done(function(res: model.ItemSalaryModel) {
            dfd.resolve(res);
        })
            .fail(function(res) {
                dfd.reject(res);
            });
        return dfd.promise();

    }
    export function getItemSalaryRegInfo(itemCode): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        var _path = nts.uk.text.format(paths.getItemSalaryRegInfo, itemCode);
        nts.uk.request.ajax(_path).done(function(res) {
            dfd.resolve(res);
        })
            .fail(function(res) {
                dfd.reject(res.message);
            });
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
            limitMnyRefItemCode: string;
            limitMny: number;
        }
        export class ItemSalaryKo {
            taxAtr: KnockoutObservable<number>;
            socialInsAtr: KnockoutObservable<number>;
            laborInsAtr: KnockoutObservable<number>;
            fixPayAtr: KnockoutObservable<number>;
            applyForAllEmpFlg: KnockoutObservable<number>;
            applyForMonthlyPayEmp: KnockoutObservable<number>;
            applyForDaymonthlyPayEmp: KnockoutObservable<number>;
            applyForDaylyPayEmp: KnockoutObservable<number>;
            applyForHourlyPayEmp: KnockoutObservable<number>;
            avePayAtr: KnockoutObservable<number>;
            errRangeLowAtr: KnockoutObservable<number>;
            errRangeLow: KnockoutObservable<number>;
            errRangeHighAtr: KnockoutObservable<number>;
            errRangeHigh: KnockoutObservable<number>;
            alRangeLowAtr: KnockoutObservable<number>;
            alRangeLow: KnockoutObservable<number>;
            alRangeHighAtr: KnockoutObservable<number>;
            alRangeHigh: KnockoutObservable<number>;
            memo: KnockoutObservable<string>;
            limitMnyAtr: KnockoutObservable<number>;
            limitMnyRefItemCode: KnockoutObservable<string>;
            limitMny: KnockoutObservable<number>;
            constructor(data) {
                this.taxAtr = ko.observable(data.taxAtr);
                this.socialInsAtr = ko.observable(data.socialInsAtr);
                this.laborInsAtr = ko.observable(data.laborInsAtr);
                this.fixPayAtr = ko.observable(data.fixPayAtr);
                this.applyForAllEmpFlg = ko.observable(data.applyForAllEmpFlg);
                this.applyForMonthlyPayEmp = ko.observable(data.applyForMonthlyPayEmp);
                this.applyForDaymonthlyPayEmp = ko.observable(data.applyForDaymonthlyPayEmp);
                this.applyForDaylyPayEmp = ko.observable(data.applyForDaylyPayEmp);
                this.applyForHourlyPayEmp = ko.observable(data.applyForHourlyPayEmp);
                this.avePayAtr = ko.observable(data.avePayAtr);
                this.errRangeLowAtr = ko.observable(data.errRangeLowAtr);
                this.errRangeLow = ko.observable(data.errRangeLow);
                this.errRangeHighAtr = ko.observable(data.errRangeHighAtr);
                this.errRangeHigh = ko.observable(data.errRangeHigh);
                this.alRangeLowAtr = ko.observable(data.alRangeLowAtr);
                this.alRangeLow = ko.observable(data.alRangeLow);
                this.alRangeHighAtr = ko.observable(data.alRangeHighAtr);
                this.alRangeHigh = ko.observable(data.alRangeHigh);
                this.memo = ko.observable(data.memo);
                this.limitMnyAtr = ko.observable(data.limitMnyAtr);
                this.limitMnyRefItemCode = ko.observable(data.limitMnyRefItemCode);
                this.limitMny = ko.observable(data.limitMny);


            }
        }
        export class ItemMasterKo {
            itemCode: KnockoutObservable<string>;
            itemName: KnockoutObservable<string>;
            categoryAtr: KnockoutObservable<number>;
            categoryAtrName: KnockoutObservable<string>;
            itemAbName: KnockoutObservable<string>;
            itemAbNameO: KnockoutObservable<string>;
            itemAbNameE: KnockoutObservable<string>;
            displaySet: KnockoutObservable<number>;
            uniteCode: KnockoutObservable<string>;
            zeroDisplaySet: KnockoutObservable<number>;
            itemDisplayAtr: KnockoutObservable<number>;
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
        export class ItemSalaryBD {
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
            itemSalary: KnockoutObservable<ItemSalaryKo>;
            itemMaster: KnockoutObservable<ItemMasterKo>;
            itemSalaryBDList: KnockoutObservableArray<ItemSalaryBD>;
            constructor(data) {
                this.itemSalary = ko.observable(new ItemSalaryKo(data.itemSalary));
                this.itemMaster = ko.observable(new ItemMasterKo(data.itemMaster));
                let itemSalaryBDList = ko.observableArray([]);
                for (let item of data.itemSalaryBDList) {
                    itemSalaryBDList().push(item);
                }
                this.itemSalaryBDList = itemSalaryBDList;
            }
        }
    }

}