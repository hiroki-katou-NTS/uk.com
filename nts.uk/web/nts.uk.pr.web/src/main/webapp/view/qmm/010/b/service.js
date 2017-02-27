var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm010;
                (function (qmm010) {
                    var b;
                    (function (b) {
                        var service;
                        (function (service) {
                            var paths = {
                                findAllSocialInsuranceOffice: "pr/insurance/social/findall",
                            };
                            function findAllSocialInsuranceOffice() {
                                var dfd = $.Deferred();
                                nts.uk.request.ajax(paths.findAllSocialInsuranceOffice)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.findAllSocialInsuranceOffice = findAllSocialInsuranceOffice;
                            var model;
                            (function (model) {
                                var SocialInsuranceOfficeDto = (function () {
                                    function SocialInsuranceOfficeDto() {
                                    }
                                    return SocialInsuranceOfficeDto;
                                }());
                                model.SocialInsuranceOfficeDto = SocialInsuranceOfficeDto;
                            })(model = service.model || (service.model = {}));
                        })(service = b.service || (b.service = {}));
                    })(b = qmm010.b || (qmm010.b = {}));
                })(qmm010 = view.qmm010 || (view.qmm010 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
