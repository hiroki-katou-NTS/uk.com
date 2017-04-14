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
                                findAllAggregateItems: "ctx/pr/report/salary/aggregate/item/findall"
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
                            function findAllAggregateItems() {
                                return nts.uk.request.ajax(paths.findAllAggregateItems);
                            }
                            service.findAllAggregateItems = findAllAggregateItems;
                            function findAllMasterItems() {
                                var dfd = $.Deferred();
                                var masterItems = [];
                                for (var i = 1; i < 15; i++) {
                                    masterItems.push({ code: 'MI' + i, name: '基本給' + i, paymentType: 'Salary', taxDivision: 'Payment' });
                                }
                                for (var i = 1; i < 15; i++) {
                                    masterItems.push({ code: 'M0' + i, name: '基本給' + i, paymentType: 'Salary', taxDivision: 'Deduction' });
                                }
                                return dfd.resolve(masterItems).promise();
                            }
                            service.findAllMasterItems = findAllMasterItems;
                        })(service = c.service || (c.service = {}));
                    })(c = qpp007.c || (qpp007.c = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=qpp007.c.service.js.map