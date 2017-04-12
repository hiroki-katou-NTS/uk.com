var qmm012;
(function (qmm012) {
    var d;
    (function (d) {
        var service;
        (function (service) {
            var paths = {
                findItemDeduct: "pr/core/itemdeduct/find",
            };
            function findItemDeduct(itemCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findItemDeduct + "/" + itemCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findItemDeduct = findItemDeduct;
            var model;
            (function (model) {
                var ItemDeduct = (function () {
                    function ItemDeduct(deductAtr, errRangeLowAtr, errRangeLow, errRangeHighAtr, errRangeHigh, alRangeLowAtr, alRangeLow, alRangeHighAtr, alRangeHigh, memo) {
                        this.deductAtr = deductAtr,
                            this.errRangeLowAtr = errRangeLowAtr,
                            this.errRangeLow = errRangeLow,
                            this.errRangeHighAtr = errRangeHighAtr,
                            this.errRangeHigh = errRangeHigh,
                            this.alRangeLowAtr = alRangeLowAtr,
                            this.alRangeLow = alRangeLow,
                            this.alRangeHighAtr = alRangeHighAtr,
                            this.alRangeHigh = alRangeHigh,
                            this.memo = memo;
                    }
                    return ItemDeduct;
                }());
                model.ItemDeduct = ItemDeduct;
            })(model = service.model || (service.model = {}));
        })(service = d.service || (d.service = {}));
    })(d = qmm012.d || (qmm012.d = {}));
})(qmm012 || (qmm012 = {}));
