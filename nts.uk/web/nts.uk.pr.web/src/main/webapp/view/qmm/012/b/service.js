var qmm012;
(function (qmm012) {
    var b;
    (function (b) {
        var service;
        (function (service) {
            var paths = {
                findAllItemMaster: "pr/core/item/findAllItemMaster",
                deleteItemMaster: "pr/core/item/delete",
                addItemMaster: "pr/core/item/add",
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
            function addItemMaster(ItemMaster) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.addItemMaster, ItemMaster)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addItemMaster = addItemMaster;
            var model;
            (function (model) {
                var ItemMaster = (function () {
                    function ItemMaster(itemCode, itemName, categoryAtr, categoryAtrName, itemAbName, itemAbNameO, itemAbNameE, displaySet, uniteCode, zeroDisplaySet, itemDisplayAtr, fixAtr) {
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
                    return ItemMaster;
                }());
                model.ItemMaster = ItemMaster;
            })(model = service.model || (service.model = {}));
        })(service = b.service || (b.service = {}));
    })(b = qmm012.b || (qmm012.b = {}));
})(qmm012 || (qmm012 = {}));
