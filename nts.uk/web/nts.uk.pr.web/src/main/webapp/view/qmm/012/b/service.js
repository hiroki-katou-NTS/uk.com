var qmm012;
(function (qmm012) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var paths = {
                findAllItemMaster: "pr/core/item/findAllItemMaster",
                findItemperiod: "pr/core/itemperiod/find",
                deleteItemMaster: "pr/core/item/delete",
            };
            function findAllItemMaster() {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findAllItemMaster)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findAllItemMaster = findAllItemMaster;
            function findItemperiod(categoryAtr, itemCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findItemperiod + "/" + categoryAtr + "/" + itemCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findItemperiod = findItemperiod;
            function deleteItemMaster(ItemMaster) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.deleteItemMaster, ItemMaster)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.deleteItemMaster = deleteItemMaster;
            var model;
            (function (model) {
                var ItemMasterModel = (function () {
                    function ItemMasterModel(itemCode, itemName, categoryAtr, categoryAtrName, itemAbName, itemAbNameO, itemAbNameE, displaySet, uniteCode, zeroDisplaySet, itemDisplayAtr, fixAtr) {
                        this.itemCode = itemCode;
                        this.itemName = itemName;
                        this.categoryAtr = categoryAtr;
                        this.categoryAtrName = categoryAtrName;
                        this.itemAbName = itemAbName;
                        this.itemAbNameO = itemAbNameO;
                        this.itemAbNameE = itemAbNameE;
                        this.displaySet = displaySet;
                        this.uniteCode = uniteCode;
                        this.zeroDisplaySet = itemDisplayAtr;
                        this.fixAtr = fixAtr;
                    }
                    return ItemMasterModel;
                }());
                model.ItemMasterModel = ItemMasterModel;
                var ItemPeriodModel = (function () {
                    function ItemPeriodModel(itemClass, itemCode, StartYear, ExpYear, cycleAtr, cycle1Atr, cycle2Atr, cycle3Atr, cycle4Atr, cycle5Atr, cycle6Atr, cycle7Atr, cycle8Atr, cycle9Atr, cycle10Atr, cycle11Atr, cycle12Atr) {
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
                        this.cycle12Atr = cycle12Atr;
                    }
                    return ItemPeriodModel;
                }());
                model.ItemPeriodModel = ItemPeriodModel;
                var categoryAtr = (function () {
                    function categoryAtr(value, name) {
                        this.value = value;
                        this.name = name;
                    }
                    return categoryAtr;
                }());
                model.categoryAtr = categoryAtr;
            })(model = service.model || (service.model = {}));
        })(service = b.service || (b.service = {}));
    })(b = qmm012.b || (qmm012.b = {}));
})(qmm012 || (qmm012 = {}));
//# sourceMappingURL=service.js.map