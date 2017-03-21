var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp018;
                (function (qpp018) {
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var servicePath = {
                                findAllInsuranceOffice: "screen/pr/QPP018/findAllOffice",
                                saveAsPdf: "screen/pr/QPP018/saveAsPdf"
                            };
                            function findAllInsuranceOffice() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(servicePath.findAllInsuranceOffice).done(function (res) {
                                    var list = _.map(res, function (item) {
                                        return new model.InsuranceOffice(item.code, item.name);
                                    });
                                    dfd.resolve(list);
                                });
                                return dfd.promise();
                            }
                            service.findAllInsuranceOffice = findAllInsuranceOffice;
                            function saveAsPdf(command) {
                                return nts.uk.request.exportFile(servicePath.saveAsPdf, command);
                            }
                            service.saveAsPdf = saveAsPdf;
                            var model;
                            (function (model) {
                                var InsuranceOffice = (function () {
                                    function InsuranceOffice(code, name) {
                                        var self = this;
                                        self.code = code;
                                        self.name = name;
                                    }
                                    return InsuranceOffice;
                                }());
                                model.InsuranceOffice = InsuranceOffice;
                            })(model = service.model || (service.model = {}));
                        })(service = a.service || (a.service = {}));
                    })(a = qpp018.a || (qpp018.a = {}));
                })(qpp018 = view.qpp018 || (view.qpp018 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
