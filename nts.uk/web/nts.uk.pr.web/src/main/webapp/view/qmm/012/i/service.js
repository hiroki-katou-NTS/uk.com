var qmm012;
(function (qmm012) {
    var i;
    (function (i) {
        var service;
        (function (service) {
            var paths = {
                findAllItemSalaryBD: "pr/core/itemsalarybd/find",
                findAllItemDeductBD: "pr/core/itemdeductbd/find",
                updateItemSalaryBD: "pr/core/itemsalarybd/update",
                updateItemDeductBD: "pr/core/itemdeductbd/update",
                addItemSalaryBD: "pr/core/itemsalarybd/add",
                addItemDeductBD: "pr/core/itemdeductbd/add",
            };
            function findItemSalaryBD(itemCode) {
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
            service.findItemSalaryBD = findItemSalaryBD;
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
            service.findAllItemDeductBD = findAllItemDeductBD;
            function addItemDeductBD(itemBD) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.addItemDeductBD, itemBD)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addItemDeductBD = addItemDeductBD;
            function addItemSalaryBD(itemBD) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.addItemSalaryBD, itemBD)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.addItemSalaryBD = addItemSalaryBD;
            function updateItemDeductBD(itemBD) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.updateItemDeductBD, itemBD)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateItemDeductBD = updateItemDeductBD;
            function updateItemSalaryBD(itemBD) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.updateItemSalaryBD, itemBD)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.updateItemSalaryBD = updateItemSalaryBD;
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
