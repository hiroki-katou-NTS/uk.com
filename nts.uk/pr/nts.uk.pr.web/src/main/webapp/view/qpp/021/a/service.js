var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp021;
                (function (qpp021) {
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var servicePath = {
                                printService: "/file/paymentdata/print",
                            };
                            function print(personId, employeeCode) {
                                var dfd = $.Deferred();
                                var query = [{
                                        personId: personId,
                                        employeeCode: employeeCode
                                    }];
                                new nts.uk.ui.file.FileDownload("/file/paymentdata/print", query).print();
                                //            nts.uk.request.ajax(servicePath.printService, query).done(function(res: qpp021.a.viewmodel.PaymentDataResultViewModel) {
                                dfd.resolve();
                                //            }).fail(function(res) {
                                //               dfd.reject(res);
                                //            });
                                return dfd.promise();
                            }
                            service.print = print;
                        })(service = a.service || (a.service = {}));
                    })(a = qpp021.a || (qpp021.a = {}));
                })(qpp021 = view.qpp021 || (view.qpp021 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
