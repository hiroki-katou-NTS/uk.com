var qmm012;
(function (qmm012) {
    var h;
    (function (h) {
        var service;
        (function (service) {
            var paths = {
                findItemSalaryPeriod: "pr/core/itemsalaryperiod/find",
                findItemDeductPeriod: "pr/core/itemdeductperiod/find",
            };
            function findItemSalaryPeriod(itemCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findItemSalaryPeriod + "/" + itemCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findItemSalaryPeriod = findItemSalaryPeriod;
            function findItemDeductPeriod(itemCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findItemDeductPeriod + "/" + itemCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findItemDeductPeriod = findItemDeductPeriod;
            function findItemPeriod(ItemMaster) {
                var dfd = $.Deferred();
                var categoryAtr = ItemMaster.categoryAtr;
                var itemCode = ItemMaster.itemCode;
                if (categoryAtr == 0) {
                    service.findItemSalaryPeriod(itemCode).done(function (ItemPeriod) {
                        dfd.resolve(ItemPeriod);
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                }
                if (categoryAtr == 1) {
                    service.findItemDeductPeriod(itemCode).done(function (ItemPeriod) {
                        dfd.resolve(ItemPeriod);
                    }).fail(function (res) {
                        dfd.reject(res);
                    });
                }
                return dfd.promise();
            }
            service.findItemPeriod = findItemPeriod;
            var model;
            (function (model) {
                var ItemPeriod = (function () {
                    function ItemPeriod(itemCd, periodAtr, strY, endY, cycleAtr, cycle01Atr, cycle02Atr, cycle03Atr, cycle04Atr, cycle05Atr, cycle06Atr, cycle07Atr, cycle08Atr, cycle09Atr, cycle10Atr, cycle11Atr, cycle12Atr) {
                        this.itemCd = itemCd;
                        this.periodAtr = periodAtr;
                        this.strY = strY;
                        this.endY = endY;
                        this.cycleAtr = cycleAtr;
                        this.cycle01Atr = cycle01Atr;
                        this.cycle02Atr = cycle02Atr;
                        this.cycle03Atr = cycle03Atr;
                        this.cycle04Atr = cycle04Atr;
                        this.cycle05Atr = cycle05Atr;
                        this.cycle06Atr = cycle06Atr;
                        this.cycle07Atr = cycle07Atr;
                        this.cycle08Atr = cycle08Atr;
                        this.cycle09Atr = cycle09Atr;
                        this.cycle10Atr = cycle10Atr;
                        this.cycle11Atr = cycle11Atr;
                        this.cycle12Atr = cycle12Atr;
                    }
                    return ItemPeriod;
                }());
                model.ItemPeriod = ItemPeriod;
            })(model = service.model || (service.model = {}));
        })(service = h.service || (h.service = {}));
    })(h = qmm012.h || (qmm012.h = {}));
})(qmm012 || (qmm012 = {}));
//# sourceMappingURL=service.js.map