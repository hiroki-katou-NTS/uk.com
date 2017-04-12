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
                        })(service = c.service || (c.service = {}));
                    })(c = qpp007.c || (qpp007.c = {}));
                })(qpp007 = view.qpp007 || (view.qpp007 = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
