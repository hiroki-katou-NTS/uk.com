var nts;
(function (nts) {
    var uk;
    (function (uk) {
        var pr;
        (function (pr) {
            var view;
            (function (view) {
                var base;
                (function (base) {
                    var simlehistory;
                    (function (simlehistory) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenBaseModel = (function () {
                                function ScreenBaseModel(service) {
                                    var self = this;
                                    self.service = service;
                                    self.isNewMode = ko.observable(true);
                                    self.masterHistoryDatasource = ko.observableArray([]);
                                    self.selectedHistoryUuid = ko.observable(undefined);
                                    self.canUpdateHistory = ko.computed(function () {
                                        return self.selectedHistoryUuid() != null;
                                    });
                                    self.canAddNewHistory = ko.computed(function () {
                                        return self.selectedHistoryUuid() != null;
                                    });
                                }
                                ScreenBaseModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    $.when(self.loadMasterHistory(), self.start()).done(function (res1, res2) {
                                        if (!self.masterHistoryList || self.masterHistoryList.length == 0) {
                                            self.isNewMode(true);
                                        }
                                        else {
                                            self.isNewMode(false);
                                        }
                                        dfd.resolve();
                                    }).fail(dfd.fail);
                                    return dfd.promise();
                                };
                                ScreenBaseModel.prototype.loadMasterHistory = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.service.loadMasterModelList().done(function (res) {
                                        var nodeList = _.map(res, function (master) {
                                            var masterNode = {
                                                id: master.code,
                                                nodeText: master.code + ' ' + master.name,
                                                nodeType: 0,
                                                data: master
                                            };
                                            var masterChild = _.map(master.historyList, function (history) {
                                                var node = {
                                                    id: history.uuid,
                                                    nodeText: history.start + '~' + history.end,
                                                    nodeType: 1,
                                                    parent: masterNode,
                                                    data: history
                                                };
                                                return node;
                                            });
                                            masterNode.childs = masterChild;
                                            return masterNode;
                                        });
                                        self.masterHistoryList = res;
                                        self.masterHistoryDatasource(nodeList);
                                        dfd.resolve();
                                    });
                                    return dfd.promise();
                                };
                                ScreenBaseModel.prototype.start = function () {
                                    var dfd = $.Deferred();
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                return ScreenBaseModel;
                            }());
                            viewmodel.ScreenBaseModel = ScreenBaseModel;
                        })(viewmodel = simlehistory.viewmodel || (simlehistory.viewmodel = {}));
                    })(simlehistory = base.simlehistory || (base.simlehistory = {}));
                })(base = view.base || (view.base = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=viewmodel.js.map