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
                                update: "ctx/pr/report/salary/outputsetting/update",
                                create: "ctx/pr/report/salary/outputsetting/create",
                                remove: "ctx/pr/report/salary/outputsetting/create",
                                find: "ctx/pr/report/salary/outputsetting/find",
                            };
                            function update(data) {
                                return nts.uk.request.ajax(paths.updateHealthInsuranceAvgearn, data);
                            }
                            service.update = update;
                            function create(data) {
                                return nts.uk.request.ajax(paths.updateHealthInsuranceAvgearn, data);
                            }
                            service.create = create;
                            function remove(id) {
                                return nts.uk.request.ajax(paths.updateHealthInsuranceAvgearn, id);
                            }
                            service.remove = remove;
                            function find(id) {
                                return nts.uk.request.ajax(paths.updateHealthInsuranceAvgearn, id);
                            }
                            service.find = find;
                        })(service = c.service || (c.service = {}));
                    })(c = qpp007.c || (qpp007.c = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
