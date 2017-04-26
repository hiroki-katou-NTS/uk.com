var qmm012;
(function (qmm012) {
    var b;
    (function (b_1) {
        var service;
        (function (service) {
            var paths = {
                findAllItemMaster: "pr/core/item/findAllItemMaster",
                deleteItemMaster: "pr/core/item/delete",
                addItemMaster: "pr/core/item/add",
                updateItemMaster: "pr/core/item/update",
                findItemMaster: "pr/core/item/find"
            };
            function findAllItemMaster(ctgAtr, dispSet) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findAllItemMaster + "/" + ctgAtr + "/" + dispSet)
                    .done(function (res) {
                    function compare(a, b) {
                        var genreA = a.itemName.toUpperCase();
                        var genreB = b.itemName.toUpperCase();
                        var comparison = 0;
                        if (genreA > genreB) {
                            comparison = 1;
                        }
                        else if (genreA < genreB) {
                            comparison = -1;
                        }
                        return comparison;
                    }
                    var ItemList = [];
                    for (var _i = 0, _a = res.sort(compare); _i < _a.length; _i++) {
                        var ItemMaster = _a[_i];
                        ItemList.push(new service.model.ItemMaster(ItemMaster.itemCode, ItemMaster.itemName, ItemMaster.categoryAtr, ItemMaster.categoryAtrName, ItemMaster.itemAbName, ItemMaster.itemAbNameO, ItemMaster.itemAbNameE, ItemMaster.displaySet, ItemMaster.uniteCode, ItemMaster.zeroDisplaySet, ItemMaster.itemDisplayAtr, ItemMaster.fixAtr));
                    }
                    dfd.resolve(ItemList);
                })
                    .fail(function (res) {
                    dfd.reject(res.message);
                });
                return dfd.promise();
            }
            service.findAllItemMaster = findAllItemMaster;
            function findItemMaster(ctgAtr, itemCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findItemMaster + "/" + ctgAtr + "/" + itemCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res.message);
                });
                return dfd.promise();
            }
            service.findItemMaster = findItemMaster;
            function deleteItemMaster(ItemMaster) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.deleteItemMaster, ItemMaster)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res.message);
                });
                return dfd.promise();
            }
            service.deleteItemMaster = deleteItemMaster;
            function addItemMaster(ItemMaster) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.addItemMaster, ItemMaster)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res.message);
                });
                return dfd.promise();
            }
            service.addItemMaster = addItemMaster;
            function updateItemMaster(ItemMaster) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.updateItemMaster, ItemMaster)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res.message);
                });
                return dfd.promise();
            }
            service.updateItemMaster = updateItemMaster;
            var model;
            (function (model) {
                class ItemMaster {
                    constructor(itemCode, itemName, categoryAtr, categoryAtrName, itemAbName, itemAbNameO, itemAbNameE, displaySet, uniteCode, zeroDisplaySet, itemDisplayAtr, fixAtr) {
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
                        this.itemKey = itemCode + ";" + categoryAtr;
                    }
                }
                model.ItemMaster = ItemMaster;
            })(model = service.model || (service.model = {}));
        })(service = b_1.service || (b_1.service = {}));
    })(b = qmm012.b || (qmm012.b = {}));
})(qmm012 || (qmm012 = {}));
