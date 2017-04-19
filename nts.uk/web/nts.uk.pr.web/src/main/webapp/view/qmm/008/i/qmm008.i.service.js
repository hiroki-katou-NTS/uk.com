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
                    var i;
                    (function (i) {
                        var service;
                        (function (service) {
                            var paths = {
                                updatePensionAvgearn: "ctx/pr/core/insurance/social/pensionavgearn/update",
                                findPensionAvgearn: "ctx/pr/core/insurance/social/pensionavgearn/find",
                                recalculatePensionAvgearn: "ctx/pr/core/insurance/social/pensionavgearn/recalculate"
                            };
                            function updatePensionAvgearn(list, officeCode) {
                                var dfd = $.Deferred();
                                var data = {
                                    listPensionAvgearnDto: list.listPensionAvgearnDto,
                                    historyId: list.historyId,
                                    officeCode: officeCode
                                };
                                nts.uk.request.ajax(paths.updatePensionAvgearn, data).done(function () {
                                    return dfd.resolve();
                                });
                                return dfd.promise();
                            }
                            service.updatePensionAvgearn = updatePensionAvgearn;
                            function recalculatePensionAvgearn(historyId) {
                                var dfd = $.Deferred();
                                var data = {
                                    historyId: historyId,
                                };
                                nts.uk.request.ajax(paths.recalculatePensionAvgearn, data).done(function (res) {
                                    dfd.resolve(res);
                                }).fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.recalculatePensionAvgearn = recalculatePensionAvgearn;
                            function findPensionAvgearn(id) {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findPensionAvgearn + '/' + id)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findPensionAvgearn = findPensionAvgearn;
                        })(service = i.service || (i.service = {}));
                    })(i = qmm008.i || (qmm008.i = {}));
                })(qmm008 = view.qmm008 || (view.qmm008 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qmm008.i.service.js.map