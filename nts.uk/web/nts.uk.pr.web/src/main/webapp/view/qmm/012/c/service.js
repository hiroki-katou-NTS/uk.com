var qmm012;
(function (qmm012) {
    var c;
    (function (c) {
        var service;
        (function (service) {
            var paths = {
                findItemSalary: "pr/core/itemsalary/find",
            };
            function findItemSalary(itemCode) {
                var dfd = $.Deferred();
                nts.uk.request.ajax(paths.findItemSalary + "/" + itemCode)
                    .done(function (res) {
                    dfd.resolve(res);
                })
                    .fail(function (res) {
                    dfd.reject(res);
                });
                return dfd.promise();
            }
            service.findItemSalary = findItemSalary;
            var model;
            (function (model) {
                var ItemSalaryModel = (function () {
                    function ItemSalaryModel(taxAtr, socialInsAtr, laborInsAtr, fixPayAtr, applyForAllEmpFlg, applyForMonthlyPayEmp, applyForDaymonthlyPayEmp, applyForDaylyPayEmp, applyForHourlyPayEmp, avePayAtr, errRangeLowAtr, errRangeLow, errRangeHighAtr, errRangeHigh, alRangeLowAtr, alRangeLow, alRangeHighAtr, alRangeHigh, memo, limitMnyAtr, limitMnyRefItemCd, limitMny) {
                        this.taxAtr = taxAtr;
                        this.socialInsAtr = socialInsAtr;
                        this.laborInsAtr = laborInsAtr;
                        this.fixPayAtr = fixPayAtr;
                        this.applyForAllEmpFlg = applyForAllEmpFlg;
                        this.applyForMonthlyPayEmp = applyForMonthlyPayEmp;
                        this.applyForDaymonthlyPayEmp = applyForDaymonthlyPayEmp;
                        this.applyForDaylyPayEmp = applyForDaylyPayEmp;
                        this.applyForHourlyPayEmp = applyForHourlyPayEmp;
                        this.avePayAtr = avePayAtr;
                        this.errRangeLowAtr = errRangeLowAtr;
                        this.errRangeLow = errRangeLow;
                        this.errRangeHighAtr = errRangeHighAtr;
                        this.errRangeHigh = errRangeHigh;
                        this.alRangeLowAtr = alRangeLowAtr;
                        this.alRangeLow = alRangeLow;
                        this.alRangeHighAtr = alRangeHighAtr;
                        this.alRangeHigh = alRangeHigh;
                        this.memo = memo;
                        this.limitMnyAtr = limitMnyAtr;
                        this.limitMnyRefItemCd = limitMnyRefItemCd;
                        this.limitMny = limitMny;
                    }
                    ;
                    return ItemSalaryModel;
                }());
                model.ItemSalaryModel = ItemSalaryModel;
            })(model = service.model || (service.model = {}));
        })(service = c.service || (c.service = {}));
    })(c = qmm012.c || (qmm012.c = {}));
})(qmm012 || (qmm012 = {}));
//# sourceMappingURL=service.js.map