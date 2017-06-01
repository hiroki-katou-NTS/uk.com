var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm008;
                (function (qmm008) {
                    var h;
                    (function (h) {
                        var service;
                        (function (service) {
                            /**
                             *  Service paths
                             */
                            var paths = {
                                updateHealthInsuranceAvgearn: "ctx/pr/core/insurance/social/healthavgearn/update",
                                findHealthInsuranceAvgEarn: "ctx/pr/core/insurance/social/healthavgearn/find",
                                recalculateHealthInsuranceAvgearn: "ctx/pr/core/insurance/social/healthavgearn/recalculate"
                            };
                            /**
                             *  Save List Health Insurance Average Earn
                             */
                            function updateHealthInsuranceAvgearn(list, officeCode) {
                                var data = {
                                    listHealthInsuranceAvgearnDto: list.listHealthInsuranceAvgearnDto,
                                    historyId: list.historyId,
                                    officeCode: officeCode
                                };
                                return nts.uk.request.ajax(paths.updateHealthInsuranceAvgearn, data);
                            }
                            service.updateHealthInsuranceAvgearn = updateHealthInsuranceAvgearn;
                            // Re-calculate values
                            function recalculateHealthInsuranceAvgearn(historyId) {
                                var data = {
                                    historyId: historyId,
                                };
                                return nts.uk.request.ajax(paths.recalculateHealthInsuranceAvgearn, data);
                            }
                            service.recalculateHealthInsuranceAvgearn = recalculateHealthInsuranceAvgearn;
                            /**
                             *  Find list HealthInsuranceAvgEarn by historyId
                             */
                            function findHealthInsuranceAvgEarn(historyId) {
                                return nts.uk.request.ajax(paths.findHealthInsuranceAvgEarn + '/' + historyId);
                            }
                            service.findHealthInsuranceAvgEarn = findHealthInsuranceAvgEarn;
                        })(service = h.service || (h.service = {}));
                    })(h = qmm008.h || (qmm008.h = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm008.h.service.js.map