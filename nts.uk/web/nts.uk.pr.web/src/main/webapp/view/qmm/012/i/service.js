var qmm012;
(function (qmm012) {
    var i;
    (function (i) {
        var service;
        (function (service) {
            var paths = {
                findAllItemSalaryBD: "pr/core/itemsalarybd/find",
                findAllItemDeductBD: "pr/core/itemdeductbd/find"
            };
            function findAllItemSalaryBD(itemCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findAllItemSalaryBD + "/" + itemCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            function findAllItemBD(ItemMaster) {
                var dfd = $.Deferred();
                var categoryAtr = ItemMaster.categoryAtr;
                var itemCode = ItemMaster.itemCode;
                if (categoryAtr == 0) {
                    findAllItemSalaryBD(itemCode).done(function (itemBDs) {
                        dfd.resolve(itemBDs);
                    });
                }
                else {
                    findAllItemDeductBD(itemCode).done(function (itemBDs) {
                        dfd.resolve(itemBDs);
                    });
                }
                return dfd.promise();
            }
            service.findAllItemBD = findAllItemBD;
            function findAllItemDeductBD(itemCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findAllItemDeductBD + "/" + itemCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            var model;
            (function (model) {
                var ItemBD = (function () {
                    function ItemBD(itemBreakdownCd, itemBreakdownName, itemBreakdownAbName, uniteCd, zeroDispSet, itemDispAtr, errRangeLowAtr, errRangeLow, errRangeHighAtr, errRangeHigh, alRangeLowAtr, alRangeLow, alRangeHighAtr, alRangeHigh) {
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
                    return ItemBD;
                }());
                model.ItemBD = ItemBD;
            })(model = service.model || (service.model = {}));
        })(service = i.service || (i.service = {}));
    })(i = qmm012.i || (qmm012.i = {}));
})(qmm012 || (qmm012 = {}));
//# sourceMappingURL=service.js.map