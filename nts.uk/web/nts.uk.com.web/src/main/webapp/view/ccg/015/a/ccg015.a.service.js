var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var com;
        (function (com) {
            var view;
            (function (view) {
                var ccg015;
                (function (ccg015) {
                    var a;
                    (function (a) {
                        var service;
                        (function (service) {
                            var servicePath = {
                                findAllTopPageItem: "/toppage/findAll",
                                getTopPageItemDetail: "/toppage/topPageDetail",
                                registerTopPage: "/toppage/create",
                                updateTopPage: "/toppage/update",
                                removeTopPage: "/toppage/remove"
                            };
                            function loadTopPage() {
                                var self = this;
                                var dfd = $.Deferred();
                                var path = servicePath.findAllTopPageItem;
                                nts.uk.request.ajax(path).done(function (data) {
                                    dfd.resolve(data);
                                });
                                return dfd.promise();
                            }
                            service.loadTopPage = loadTopPage;
                            function loadDetailTopPage(topPageCode) {
                                var self = this;
                                var dfd = $.Deferred();
                                var path = servicePath.getTopPageItemDetail + '/' + topPageCode;
                                nts.uk.request.ajax(path).done(function (data) {
                                    dfd.resolve(data);
                                });
                                return dfd.promise();
                            }
                            service.loadDetailTopPage = loadDetailTopPage;
                            function registerTopPage(data) {
                                return nts.uk.request.ajax(servicePath.registerTopPage, data);
                            }
                            service.registerTopPage = registerTopPage;
                            function updateTopPage(data) {
                                return nts.uk.request.ajax(servicePath.updateTopPage, data);
                            }
                            service.updateTopPage = updateTopPage;
                            function deleteTopPage(topPageItemCode) {
                                return nts.uk.request.ajax(servicePath.removeTopPage, topPageItemCode);
                            }
                            service.deleteTopPage = deleteTopPage;
                        })(service = a.service || (a.service = {}));
                    })(a = ccg015.a || (ccg015.a = {}));
                })(ccg015 = view.ccg015 || (view.ccg015 = {}));
            })(view = com.view || (com.view = {}));
        })(com = uk.com || (uk.com = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=ccg015.a.service.js.map