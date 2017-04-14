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
                            var paths = {
                                updateHealthInsuranceAvgearn: "ctx/pr/core/insurance/social/healthavgearn/update",
                                findHealthInsuranceAvgEarn: "ctx/pr/core/insurance/social/healthavgearn/find",
                            };
                            function updateHealthInsuranceAvgearn(list, officeCode) {
                                var dfd = $.Deferred();
                                var data = { listHealthInsuranceAvgearnDto: list.listHealthInsuranceAvgearnDto,
                                    historyId: list.historyId,
                                    officeCode: officeCode };
                                nts.uk.request.ajax(paths.updateHealthInsuranceAvgearn, data).done(function () {
                                    return dfd.resolve();
                                });
                                return dfd.promise();
                            }
                            service.updateHealthInsuranceAvgearn = updateHealthInsuranceAvgearn;
                            function findHealthInsuranceAvgEarn(historyId) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findHealthInsuranceAvgEarn + '/' + historyId)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
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