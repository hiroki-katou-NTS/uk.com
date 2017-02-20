var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm002;
                (function (qmm002) {
                    var b;
                    (function (b) {
                        var service;
                        (function (service) {
                            var paths = {
                                getBankList: ""
                            };
                            function getBankList() {
                                var dfd = $.Deferred();
                                var result = [
                                    new b.viewmodel.Node('0001', 'Hanoi Vietnam', []),
                                    new b.viewmodel.Node('0003', 'Bangkok Thailand', []),
                                    new b.viewmodel.Node('0004', 'Tokyo Japan', []),
                                    new b.viewmodel.Node('0005', 'Jakarta Indonesia', []),
                                    new b.viewmodel.Node('0002', 'Seoul Korea', []),
                                    new b.viewmodel.Node('0006', 'Paris France', []),
                                    new b.viewmodel.Node('0007', 'United States', [
                                        new b.viewmodel.Node('0008', 'Washington US', []),
                                        new b.viewmodel.Node('0009', 'Newyork US', [])]),
                                    new b.viewmodel.Node('0010', 'Beijing China', []),
                                    new b.viewmodel.Node('0011', 'London United Kingdom', []),
                                    new b.viewmodel.Node('0012', 'dfsdfs', [])
                                ];
                                dfd.resolve(result);
                                return dfd.promise();
                            }
                            service.getBankList = getBankList;
                        })(service = b.service || (b.service = {}));
                    })(b = qmm002.b || (qmm002.b = {}));
                })(qmm002 = view.qmm002 || (view.qmm002 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
