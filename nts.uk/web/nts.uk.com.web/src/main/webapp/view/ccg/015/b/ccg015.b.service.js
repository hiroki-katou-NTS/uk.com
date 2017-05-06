var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var ccg015;
                (function (ccg015) {
                    var b;
                    (function (b) {
                        var service;
                        (function (service) {
                            var servicePath = {
                                loadMyPageSetting: "/mypage/getMyPageSetting",
                                updateMyPageSetting: "/mypage/updateMyPageSetting"
                            };
                            function loadMyPageSetting(CompanyId) {
                                var self = this;
                                var dfd = $.Deferred();
                                var path = servicePath.loadMyPageSetting + "/" + CompanyId;
                                nts.uk.request.ajax(path).done(function (data) {
                                    dfd.resolve(data);
                                });
                                return dfd.promise();
                            }
                            service.loadMyPageSetting = loadMyPageSetting;
                            function updateMyPageSetting(data) {
                                return nts.uk.request.ajax(servicePath.updateMyPageSetting, data);
                            }
                            service.updateMyPageSetting = updateMyPageSetting;
                        })(service = b.service || (b.service = {}));
                    })(b = ccg015.b || (ccg015.b = {}));
                })(ccg015 = view.ccg015 || (view.ccg015 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=ccg015.b.service.js.map