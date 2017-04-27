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
                            /**
                             *  Service paths
                             */
                            var paths = {
                                save: "ctx/pr/report/salary/outputsetting/save",
                                remove: "ctx/pr/report/salary/outputsetting/remove",
                                findOutputSettingDetail: "ctx/pr/report/salary/outputsetting/find",
                                findAllOutputSettings: "ctx/pr/report/salary/outputsetting/findall",
                                findAllAggregateItems: "ctx/pr/report/salary/aggregate/item/findall"
                            };
                            /**
                             *  Update
                             */
                            function save(data) {
                                return nts.uk.request.ajax(paths.save, data);
                            }
                            service.save = save;
                            /**
                             *  Delete
                             */
                            function remove(code) {
                                return nts.uk.request.ajax(paths.remove, { code: code });
                            }
                            service.remove = remove;
                            /**
                             *  Find outputSetting detail
                             */
                            function findOutputSettingDetail(id) {
                                return nts.uk.request.ajax(paths.findOutputSettingDetail + "/" + id);
                            }
                            service.findOutputSettingDetail = findOutputSettingDetail;
                            /**
                             *  Find all outputSettings
                             */
                            function findAllOutputSettings() {
                                return nts.uk.request.ajax(paths.findAllOutputSettings);
                            }
                            service.findAllOutputSettings = findAllOutputSettings;
                            /**
                             *  Find all aggregateItems.
                             */
                            function findAllAggregateItems() {
                                return nts.uk.request.ajax(paths.findAllAggregateItems);
                            }
                            service.findAllAggregateItems = findAllAggregateItems;
                            /**
                             * Find all masterItems.
                             */
                            function findAllMasterItems() {
                                var dfd = $.Deferred();
                                // mock data
                                var masterItems = [];
                                for (var i = 1; i <= 9; i++) {
                                    masterItems.push({ code: 'F00' + i, name: '基本給' + i, category: 'Payment' });
                                }
                                for (var i = 1; i <= 9; i++) {
                                    masterItems.push({ code: 'F10' + i, name: '基本給' + i, category: 'Deduction' });
                                }
                                for (var i = 1; i <= 9; i++) {
                                    masterItems.push({ code: 'F20' + i, name: '基本給' + i, category: 'Attendance' });
                                }
                                for (var i = 1; i <= 9; i++) {
                                    masterItems.push({ code: 'F30' + i, name: '基本給' + i, category: 'ArticleOthers' });
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
