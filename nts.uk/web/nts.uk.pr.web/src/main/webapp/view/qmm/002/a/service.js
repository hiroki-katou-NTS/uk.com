var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm002_1;
                (function (qmm002_1) {
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var paths = {
                                getBankList: ""
                            };
                            function getBankList() {
                                var dfd = $.Deferred();
                                var result = [
                                    new a.viewmodel.Node('0001', 'Hanoi Vietnam', []),
                                    new a.viewmodel.Node('0003', 'Bangkok Thailand', []),
                                    new a.viewmodel.Node('0004', 'Tokyo Japan', []),
                                    new a.viewmodel.Node('0005', 'Jakarta Indonesia', []),
                                    new a.viewmodel.Node('0002', 'Seoul Korea', []),
                                    new a.viewmodel.Node('0006', 'Paris France', []),
                                    new a.viewmodel.Node('0007', 'United States', [
                                        new a.viewmodel.Node('0008', 'Washington US', []),
                                        new a.viewmodel.Node('0009', 'Newyork US', [])]),
                                    new a.viewmodel.Node('0010', 'Beijing China', []),
                                    new a.viewmodel.Node('0011', 'London United Kingdom', []),
                                    new a.viewmodel.Node('0012', '', [])
                                ];
                                dfd.resolve(result);
                                return dfd.promise();
                            }
                            service.getBankList = getBankList;
                        })(service = a.service || (a.service = {}));
                    })(a = qmm002_1.a || (qmm002_1.a = {}));
                })(qmm002_1 = view.qmm002_1 || (view.qmm002_1 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
