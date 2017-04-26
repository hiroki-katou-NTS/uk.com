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
                                var data = {
                                    listPensionAvgearnDto: list.listPensionAvgearnDto,
                                    historyId: list.historyId,
                                    officeCode: officeCode
                                };
                                return nts.uk.request.ajax(paths.updatePensionAvgearn, data);
                            }
                            service.updatePensionAvgearn = updatePensionAvgearn;
                            function recalculatePensionAvgearn(historyId) {
                                var data = {
                                    historyId: historyId,
                                };
                                return nts.uk.request.ajax(paths.recalculatePensionAvgearn, data);
                            }
                            service.recalculatePensionAvgearn = recalculatePensionAvgearn;
                            function findPensionAvgearn(id) {
                                return nts.uk.request.ajax(paths.findPensionAvgearn + '/' + id);
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