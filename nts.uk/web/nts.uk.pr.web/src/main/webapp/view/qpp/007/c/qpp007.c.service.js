var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var qpp007;
                (function (qpp007) {
                    var c;
                    (function (c) {
                        var service;
                        (function (service) {
                            var paths = {
                                save: "ctx/pr/report/salary/outputsetting/save",
                                remove: "ctx/pr/report/salary/outputsetting/remove",
                                findOutputSettingDetail: "ctx/pr/report/salary/outputsetting/find",
                                findAllOutputSettings: "ctx/pr/report/salary/outputsetting/findall",
                            };
                            function save(data) {
                                return nts.uk.request.ajax(paths.save, data);
                            }
                            service.save = save;
                            function remove(code) {
                                return nts.uk.request.ajax(paths.remove, { code: code });
                            }
                            service.remove = remove;
                            function findOutputSettingDetail(id) {
                                return nts.uk.request.ajax(paths.findOutputSettingDetail + "/" + id);
                            }
                            service.findOutputSettingDetail = findOutputSettingDetail;
                            function findAllOutputSettings() {
                                return nts.uk.request.ajax(paths.findAllOutputSettings);
                            }
                            service.findAllOutputSettings = findAllOutputSettings;
                        })(service = c.service || (c.service = {}));
                    })(c = qpp007.c || (qpp007.c = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp007.c.service.js.map