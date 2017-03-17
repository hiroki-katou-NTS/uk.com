var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp007;
                (function (qpp007) {
                    var j;
                    (function (j) {
                        var service;
                        (function (service) {
                            var paths = {
                                findSalaryAggregateItem: "ctx/pr/report/salary/aggregate/item/findSalaryAggregateItem",
                                importData: "ctx/pr/core/insurance/labor/importser/importData"
                            };
                            function findSalaryAggregateItem(salaryAggregateItemFindDto) {
                                return nts.uk.request.ajax(paths.findSalaryAggregateItem, salaryAggregateItemFindDto);
                            }
                            service.findSalaryAggregateItem = findSalaryAggregateItem;
                        })(service = j.service || (j.service = {}));
                    })(j = qpp007.j || (qpp007.j = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp007.j.service.js.map