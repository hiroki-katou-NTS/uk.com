var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm011;
                (function (qmm011) {
                    var e;
                    (function (e) {
                        var service;
                        (function (service) {
                            var paths = {
                                updateInsuranceBusinessType: "pr/insurance/labor/businesstype/update"
                            };
                            //Function update 
                            function updateInsuranceBusinessType(insuranceBusinessType) {
                                //set up respone
                                var dfd = $.Deferred();
                                //set up data request
                                var data = { insuranceBusinessType: insuranceBusinessType };
                                //call service server
                                nts.uk.request.ajax(paths.updateInsuranceBusinessType, data)
                                    .done(function (res) {
                                    dfd.resolve(res);
                                })
                                    .fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.updateInsuranceBusinessType = updateInsuranceBusinessType;
                        })(service = e.service || (e.service = {}));
                    })(e = qmm011.e || (qmm011.e = {}));
                })(qmm011 = view.qmm011 || (view.qmm011 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
