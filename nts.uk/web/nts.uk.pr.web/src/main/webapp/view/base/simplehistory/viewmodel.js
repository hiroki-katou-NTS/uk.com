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
                            /**
                             * Base screen model for simple history.
                             */
                            var ScreenBaseModel = (function () {
                                /**
                                 * Constructor.
                                 */
                                function ScreenBaseModel(options) {
                                    var self = this;
                                    // Set.
                                    self.options = options;
                                    self.service = options.service;
                                    // Init.
                                    self.isNewMode = ko.observable(true);
                                    self.masterHistoryDatasource = ko.observableArray([]);
                                    // On searched result.
                                    self.igGridSelectedHistoryUuid = ko.observable(undefined);
                                    self.selectedHistoryUuid = ko.observable(undefined);
                                    self.selectedNode = ko.observable(undefined);
                                    self.isClickHistory = ko.observable(false);
                                    // Can update history flag.
                                    self.canUpdateHistory = ko.computed(function () {
                                        return self.selectedNode() && self.selectedHistoryUuid() != undefined;
                                    });
                                    self.canAddNewHistory = ko.computed(function () {
                                        return self.selectedNode() != null;
                                    });
                                    self.igGridSelectedHistoryUuid.subscribe(function (id) {
                                        // Not select.
                                        if (!id) {
                                            self.selectedNode(undefined);
                                            return;
                                        }
                                        var selectedNode = self.getNode(id);
                                        // History node.
                                        if (!selectedNode.isMaster) {
                                            self.isNewMode(false);
                                            self.selectedHistoryUuid(selectedNode.id);
                                            self.onSelectHistory(id);
                                        }
                                        else {
                                            // Parent node.
                                            self.onSelectMaster(id);
                                        }
                                        self.selectedNode(selectedNode);
                                    });
                                    // On new mode.
                                    self.isNewMode.subscribe(function (val) {
                                        if (val) {
                                            self.onRegistNew();
                                        }
                                    });
                                }
                                /**
                                 * Start page.
                                 */
                                ScreenBaseModel.prototype.startPage = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    $.when(self.loadMasterHistory(), self.start()).done(function (res1, res2) {
                                        if (!self.masterHistoryList || self.masterHistoryList.length == 0) {
                                            // Set new mode.
                                            self.isNewMode(true);
                                            self.onRegistNew();
                                        }
                                        else {
                                            // Not new mode and select first history.
                                            self.isNewMode(false);
                                            if (self.masterHistoryDatasource()[0].childs &&
                                                self.masterHistoryDatasource()[0].childs.length > 0) {
                                                self.igGridSelectedHistoryUuid(self.masterHistoryDatasource()[0].childs[0].id);
                                            }
                                            else {
                                                self.igGridSelectedHistoryUuid(self.masterHistoryDatasource()[0].id);
                                            }
                                        }
                                        // resole.
                                        dfd.resolve();
                                    }).fail(dfd.fail);
                                    return dfd.promise();
                                };
                                /**
                                 * Load master history.
                                 */
                                ScreenBaseModel.prototype.loadMasterHistory = function () {
                                    var self = this;
                                    var dfd = $.Deferred();
                                    self.service.loadMasterModelList().done(function (res) {
                                        var nodeList = _.map(res, function (master) {
                                            // Current node.
                                            var masterNode = {
                                                id: master.code,
                                                searchText: master.code + ' ' + master.name,
                                                nodeText: master.code + ' ' + master.name,
                                                isMaster: true,
                                                data: master
                                            };
                                            // Child node.
                                            var masterChild = _.map(master.historyList, function (history) {
                                                var node = {
                                                    id: history.uuid,
                                                    searchText: '',
                                                    nodeText: nts.uk.time.formatYearMonth(history.start) + '~' + nts.uk.time.formatYearMonth(history.end),
                                                    isMaster: false,
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
                                /**
                                 * Start registr new.
                                 */
                                ScreenBaseModel.prototype.registBtnClick = function () {
                                    var self = this;
                                    self.isNewMode(true);
                                    // Clear select history uuid.
                                    self.igGridSelectedHistoryUuid(undefined);
                                };
                                /**
                                 * Save current master and history data.
                                 */
                                ScreenBaseModel.prototype.saveBtnClick = function () {
                                    var self = this;
                                    self.onSave().done(function (uuid) {
                                        self.loadMasterHistory().done(function () {
                                            self.igGridSelectedHistoryUuid(uuid);
                                        });
                                    }).fail(function () {
                                        // Do nothing.
                                    });
                                };
                                /**
                                 * Open add new hisotry dialog.
                                 */
                                ScreenBaseModel.prototype.addNewHistoryBtnClick = function () {
                                    var self = this;
                                    var currentNode = self.selectedNode();
                                    var latestNode = currentNode.isMaster ? _.head(currentNode.childs) : _.head(currentNode.parent.childs);
                                    var newHistoryOptions = {
                                        name: self.options.functionName,
                                        master: currentNode.isMaster ? currentNode.data : currentNode.parent.data,
                                        lastest: latestNode ? latestNode.data : undefined,
                                        // Copy.
                                        onCopyCallBack: function (data) {
                                            self.service.createHistory(data.masterCode, data.startYearMonth, true)
                                                .done(function (h) { return self.reloadMasterHistory(h.uuid); });
                                        },
                                        // Init.
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
                                /**
                                 * Open update history btn.
                                 */
                                ScreenBaseModel.prototype.updateHistoryBtnClick = function () {
                                    var self = this;
                                    var currentNode = self.getNode(self.selectedHistoryUuid());
                                    var newHistoryOptions = {
                                        name: self.options.functionName,
                                        master: currentNode.parent.data,
                                        history: currentNode.data,
                                        removeMasterOnLastHistoryRemove: self.options.removeMasterOnLastHistoryRemove,
                                        // Delete callback.
                                        onDeleteCallBack: function (data) {
                                            self.service.deleteHistory(data.masterCode, data.historyId).done(function () {
                                                self.reloadMasterHistory(null);
                                            });
                                        },
                                        // Update call back.
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
                                /**
                                 * Reload master history then set.
                                 */
                                ScreenBaseModel.prototype.reloadMasterHistory = function (uuid) {
                                    var self = this;
                                    self.loadMasterHistory().done(function () {
                                        self.selectedHistoryUuid(undefined);
                                        if (uuid) {
                                            self.igGridSelectedHistoryUuid(uuid);
                                        }
                                        else {
                                            if (self.masterHistoryList.length > 0) {
                                                if (!_.isEmpty(self.masterHistoryList[0].historyList)) {
                                                    self.igGridSelectedHistoryUuid(self.masterHistoryList[0].historyList[0].uuid);
                                                }
                                            }
                                        }
                                    });
                                };
                                /**
                                 * Internal start for each page.
                                 * Override if you need to do any thing.
                                 */
                                ScreenBaseModel.prototype.start = function () {
                                    var dfd = $.Deferred();
                                    dfd.resolve();
                                    return dfd.promise();
                                };
                                /**
                                 * On select master data.
                                 */
                                ScreenBaseModel.prototype.onSelectMaster = function (code) {
                                    // Override your self if need.
                                };
                                /**
                                 * Get node using id.
                                 */
                                ScreenBaseModel.prototype.getNode = function (id) {
                                    var self = this;
                                    var nodeList = _.flatMap(self.masterHistoryDatasource(), function (node) {
                                        var newArr = new Array();
                                        newArr.push(node);
                                        if (node.childs) {
                                            newArr = newArr.concat(node.childs);
                                        }
                                        return newArr;
                                    });
                                    return _.first(_.filter(nodeList, function (node) {
                                        return node.id == id;
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
