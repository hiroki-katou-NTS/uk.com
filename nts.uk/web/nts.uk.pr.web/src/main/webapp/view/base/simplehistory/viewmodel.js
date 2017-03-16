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
                    var simplehistory;
                    (function (simplehistory) {
                        var viewmodel;
                        (function (viewmodel) {
                            var ScreenBaseModel = (function () {
                                function ScreenBaseModel(options) {
                                    var self = this;
                                    self.options = options;
                                    self.service = options.service;
                                    self.isNewMode = ko.observable(true);
                                    self.masterHistoryDatasource = ko.observableArray([]);
                                    self.selectedHistoryUuid = ko.observable(undefined);
                                    self.canUpdateHistory = ko.computed(function () {
                                        return self.selectedHistoryUuid() != null && self.getCurrentHistoryNode() != null;
                                    });
                                    self.canAddNewHistory = ko.computed(function () {
                                        return self.selectedHistoryUuid() != null && self.getCurrentHistoryNode() != null;
                                    });
                                    self.igGridSelectedHistoryUuid = ko.observable('');
                                    self.isClickHistory = ko.observable(false);
                                    self.igGridSelectedHistoryUuid.subscribe(function (id) {
                                        if (id && id.length == 36) {
                                            self.isNewMode(false);
                                            self.onSelectHistory(id);
                                        }
                                        else {
                                            self.isClickHistory(false);
                                        }
                                    });
                                    self.selectedHistoryUuid.subscribe(function (id) {
                                        self.isNewMode(false);
                                        self.onSelectHistory(id);
                                    });
                                    self.isNewMode.subscribe(function (val) {
                                        if (val) {
                                            self.onRegistNew();
                                        }
                                    });
                                }
                                ScreenBaseModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    $.when(self.loadMasterHistory(), self.start()).done(function (res1, res2) {
                                        if (!self.masterHistoryList || self.masterHistoryList.length == 0) {
                                            self.isNewMode(true);
                                            self.onRegistNew();
                                        }
                                        else {
                                            self.isNewMode(false);
                                            if (self.masterHistoryDatasource()[0].childs.length > 0)
                                                self.selectedHistoryUuid(self.masterHistoryDatasource()[0].childs[0].id);
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
                                                searchText: master.code + ' ' + master.name,
                                                nodeText: master.code + ' ' + master.name,
                                                nodeType: 0,
                                                data: master
                                            };
                                            var masterChild = _.map(master.historyList, function (history) {
                                                var node = {
                                                    id: history.uuid,
                                                    searchText: '',
                                                    nodeText: nts.uk.time.formatYearMonth(history.start) + '~' + nts.uk.time.formatYearMonth(history.end),
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
                                ScreenBaseModel.prototype.registBtnClick = function () {
                                    var self = this;
                                    self.isNewMode(true);
                                    self.igGridSelectedHistoryUuid(undefined);
                                };
                                ScreenBaseModel.prototype.saveBtnClick = function () {
                                    var self = this;
                                    self.onSave().done(function (uuid) {
                                        self.loadMasterHistory().done(function () {
                                            self.selectedHistoryUuid(uuid);
                                        });
                                    }).fail(function () {
                                    });
                                };
                                ScreenBaseModel.prototype.addNewHistoryBtnClick = function () {
                                    var self = this;
                                    var currentNode = self.getCurrentHistoryNode();
                                    var newHistoryOptions = {
                                        name: self.options.functionName,
                                        master: currentNode.parent.data,
                                        lastest: currentNode.data,
                                        onCopyCallBack: function (data) {
                                            self.service.createHistory(data.masterCode, data.startYearMonth, true)
                                                .done(function (h) { return self.reloadMasterHistory(h.uuid); });
                                        },
                                        onCreateCallBack: function (data) {
                                            self.service.createHistory(data.masterCode, data.startYearMonth, false)
                                                .done(function (h) { return self.reloadMasterHistory(h.uuid); });
                                        }
                                    };
                                    nts.uk.ui.windows.setShared('options', newHistoryOptions);
                                    var ntsDialogOptions = { title: nts.uk.text.format('{0}の登録 > 履歴の追加', self.options.functionName),
                                        dialogClass: 'no-close' };
                                    nts.uk.ui.windows.sub.modal('/view/base/simplehistory/newhistory/index.xhtml', ntsDialogOptions);
                                };
                                ScreenBaseModel.prototype.updateHistoryBtnClick = function () {
                                    var self = this;
                                    var currentNode = self.getCurrentHistoryNode();
                                    var newHistoryOptions = {
                                        name: self.options.functionName,
                                        master: currentNode.parent.data,
                                        history: currentNode.data,
                                        removeMasterOnLastHistoryRemove: self.options.removeMasterOnLastHistoryRemove,
                                        onDeleteCallBack: function (data) {
                                            self.service.deleteHistory(data.masterCode, data.historyId).done(function () {
                                                self.reloadMasterHistory(null);
                                            });
                                        },
                                        onUpdateCallBack: function (data) {
                                            self.service.updateHistoryStart(data.masterCode, data.historyId, data.startYearMonth).done(function () {
                                                self.reloadMasterHistory(self.selectedHistoryUuid());
                                            });
                                        }
                                    };
                                    nts.uk.ui.windows.setShared('options', newHistoryOptions);
                                    var ntsDialogOptions = { title: nts.uk.text.format('{0}の登録 > 履歴の編集', self.options.functionName),
                                        dialogClass: 'no-close' };
                                    nts.uk.ui.windows.sub.modal('/view/base/simplehistory/updatehistory/index.xhtml', ntsDialogOptions);
                                };
                                ScreenBaseModel.prototype.reloadMasterHistory = function (uuid) {
                                    var self = this;
                                    self.loadMasterHistory().done(function () {
                                        if (uuid) {
                                            self.selectedHistoryUuid(uuid);
                                        }
                                        else {
                                            if (self.masterHistoryList.length > 0) {
                                                self.selectedHistoryUuid(self.masterHistoryList[0].historyList[0].uuid);
                                            }
                                        }
                                    });
                                };
                                ScreenBaseModel.prototype.start = function () {
                                    var dfd = $.Deferred();
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                ScreenBaseModel.prototype.getCurrentHistoryNode = function () {
                                    var self = this;
                                    var nodeList = _.flatMap(self.masterHistoryDatasource(), function (node) {
                                        return node.childs;
                                    });
                                    return _.first(_.filter(nodeList, function (node) {
                                        return node.id == self.selectedHistoryUuid()
                                            && self.selectedHistoryUuid().length > 4;
                                    }));
                                };
                                return ScreenBaseModel;
                            }());
                            viewmodel.ScreenBaseModel = ScreenBaseModel;
                        })(viewmodel = simplehistory.viewmodel || (simplehistory.viewmodel = {}));
                    })(simplehistory = base.simplehistory || (base.simplehistory = {}));
                })(base = view.base || (view.base = {}));
            })(view = pr.view || (pr.view = {}));
        })(pr = uk.pr || (uk.pr = {}));
    })(uk = nts.uk || (nts.uk = {}));
})(nts || (nts = {}));
//# sourceMappingURL=viewmodel.js.map