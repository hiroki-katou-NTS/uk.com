var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp005;
                (function (qpp005) {
                    var f;
                    (function (f) {
                        var service;
                        (function (service) {
                            var servicePath = {
                                getCommuteNotaxLimit: "pr/proto/paymentdata/findCommuteNotaxLimit/{0}/{1}",
                            };
                            function getCommuteNotaxLimit(personId, startYearmonth) {
                                var dfd = $.Deferred();
                                var _path = nts.uk.text.format(servicePath.getCommuteNotaxLimit, personId, startYearmonth);
                                nts.uk.request.ajax(_path).done(function (res) {
                                    dfd.resolve(res);
                                }).fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.getCommuteNotaxLimit = getCommuteNotaxLimit;
                        })(service = f.service || (f.service = {}));
                    })(f = qpp005.f || (qpp005.f = {}));
                })(qpp005 = view.qpp005 || (view.qpp005 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=service.js.map