var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qet002;
                (function (qet002) {
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var servicePath = {
                                printService: "/screen/pr/QET002/print",
                            };
                            function print(query) {
                                var dfd = $.Deferred();
                                new nts.uk.ui.file.FileDownload("/screen/pr/QET002/print", query).print().done(function () {
                                    dfd.resolve();
                                }).fail(function (res) {
                                    dfd.reject(res);
                                });
                                return dfd.promise();
                            }
                            service.print = print;
                        })(service = a.service || (a.service = {}));
                    })(a = qet002.a || (qet002.a = {}));
                })(qet002 = view.qet002 || (view.qet002 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
