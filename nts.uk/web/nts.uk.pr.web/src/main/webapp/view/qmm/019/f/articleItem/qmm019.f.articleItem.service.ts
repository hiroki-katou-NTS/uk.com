module qmm019.f.articleItem.service {
    var paths = {
        getItemArticleRegInfo: "pr/core/item/find/{0}/{1}",
    };
    export function getItemArticleRegInfo(categoryAtr, itemCode): JQueryPromise<any> {
        let dfd = $.Deferred<any>();
        var _path = nts.uk.text.format(paths.getItemArticleRegInfo, categoryAtr, itemCode);
        nts.uk.request.ajax(_path).done(function(res) {
            dfd.resolve(res);
        })
            .fail(function(res) {
                dfd.reject(res.message);
            });
        return dfd.promise();
    }
    export module model {

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
            zeroDisplaySet: KnockoutObservable<any>;
            itemDisplayAtr: KnockoutObservable<number>;
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
        export class ItemRegInfo {
            itemMaster: KnockoutObservable<ItemMasterKo>;
            constructor(data) {
                this.itemMaster = ko.observable(new ItemMasterKo(data));

            }
        }
    }
}