var qmm012;
(function (qmm012) {
    var e;
    (function (e) {
        var service;
        (function (service) {
            var paths = {
                findItemAttend: "pr/core/itemattend/find",
            };
            function findItemAttend(itemCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findItemAttend + "/" + itemCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findItemAttend = findItemAttend;
            var model;
            (function (model) {
                var ItemAttend = (function () {
                    function ItemAttend(avePayAtr, itemAtr, errRangeLowAtr, errRangeLow, errRangeHighAtr, errRangeHigh, alRangeLowAtr, alRangeLow, alRangeHighAtr, alRangeHigh, workDaysScopeAtr, memo) {
                        this.avePayAtr = avePayAtr;
                        this.itemAtr = itemAtr;
                        this.errRangeLowAtr = errRangeLowAtr;
                        this.errRangeLow = errRangeLow;
                        this.errRangeHighAtr = errRangeHighAtr;
                        this.errRangeHigh = errRangeHigh;
                        this.alRangeLowAtr = alRangeLowAtr;
                        this.alRangeLow = alRangeLow;
                        this.alRangeHighAtr = alRangeHighAtr;
                        this.alRangeHigh = alRangeHigh;
                        this.workDaysScopeAtr = workDaysScopeAtr;
                        this.memo = memo;
                    }
                    return ItemAttend;
                }());
                model.ItemAttend = ItemAttend;
            })(model = service.model || (service.model = {}));
        })(service = e.service || (e.service = {}));
    })(e = qmm012.e || (qmm012.e = {}));
})(qmm012 || (qmm012 = {}));
//# sourceMappingURL=service.js.map