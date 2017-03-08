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
                                getallInsuranceOffice: "ctx/pr/report/insurance/office/findAll",
                                saveAsPdf: "screen/pr/QPP018/saveAsPdf"
                            };
                            function getAllInsuranceOffice() {
                                return nts.uk.request.ajax(servicePath.getallInsuranceOffice);
                            }
                            service.getAllInsuranceOffice = getAllInsuranceOffice;
                            function saveAsPdf() {
                                return nts.uk.request.ajax(servicePath.saveAsPdf);
                            }
                            service.saveAsPdf = saveAsPdf;
                            var model;
                            (function (model) {
                                var InsuranceOffice = (function () {
                                    function InsuranceOffice() {
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
