var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qmm016;
                (function (qmm016) {
                    var service;
                    (function (service) {
                        var Service = (function () {
                            function Service() {
                            }
                            Service.prototype.loadMasterModelList = function () {
                                var dfd = $.Deferred();
                                dfd.resolve([{
                                        code: '001',
                                        name: 'Name 001',
                                        historyList: [{
                                                uuid: 'uuid001',
                                                start: 201601,
                                                end: 201602
                                            }, {
                                                uuid: 'uuid002',
                                                start: 201603,
                                                end: 201604
                                            }]
                                    }]);
                                return dfd.promise();
                            };
                            return Service;
                        }());
                        service.Service = Service;
                        service.instance = new Service();
                    })(service = qmm016.service || (qmm016.service = {}));
                })(qmm016 = view.qmm016 || (view.qmm016 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
