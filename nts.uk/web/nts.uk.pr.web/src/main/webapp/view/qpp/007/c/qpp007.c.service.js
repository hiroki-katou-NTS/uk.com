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
                                remove: "ctx/pr/report/salary/outputsetting/create",
                                find: "ctx/pr/report/salary/outputsetting/find",
                            };
                            function save(data) {
                                return nts.uk.request.ajax(paths.save, data);
                            }
                            service.save = save;
                            function remove(id) {
                                return nts.uk.request.ajax(paths.remove, id);
                            }
                            service.remove = remove;
                            function find(id) {
                                return nts.uk.request.ajax(paths.find, id);
                            }
                            service.find = find;
                        })(service = c.service || (c.service = {}));
                    })(c = qpp007.c || (qpp007.c = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp007.c.service.js.map