module nts.uk.pr.view.base.simplehistory {
    export module viewmodel {
        
        /**
         * Simple history screen options.
         */
        export interface SimpleHistoryScreenOptions<M extends model.MasterModel<H>, H extends model.HistoryModel> {
            functionName: string;
            removeMasterOnLastHistoryRemove?: boolean;
            service: service.Service<M, H>
        }
        
        /**
         * Base screen model for simple history.
         */
        export abstract class ScreenBaseModel<M extends model.MasterModel<H>, H extends model.HistoryModel> {
            // Service.
            service: service.Service<M, H>;

            // Whether or not in new mode.
            isNewMode: KnockoutObservable<boolean>;
            
            // Master history.
            masterHistoryList: Array<M>;
            
            // Data source.
            masterHistoryDatasource: KnockoutObservableArray<Node>;
            
            // Selected hsitory uuid.
            selectedHistoryUuid: KnockoutObservable<string>;

            // Flag
            private canUpdateHistory: KnockoutObservable<boolean>;
            private canAddNewHistory: KnockoutObservable<boolean>;
            private options: SimpleHistoryScreenOptions<M, H>;

            /**
             * Constructor.
             */
            constructor(options: SimpleHistoryScreenOptions<M, H>) {
                var self = this;

                // Set.
                self.options = options;
                self.service = options.service;

                // Init.
                self.isNewMode = ko.observable(true);
                self.masterHistoryDatasource = ko.observableArray([]);
                self.selectedHistoryUuid = ko.observable(undefined);

                // Can update history flag.
                self.canUpdateHistory = ko.computed(() => {
                    return self.selectedHistoryUuid() != null && self.getCurrentHistoryNode() != null;
                })
                self.canAddNewHistory = ko.computed(() => {
                    return self.selectedHistoryUuid() != null && self.getCurrentHistoryNode() != null;
                })

                // On history select.
                self.selectedHistoryUuid.subscribe((id) => {
                    if (id && id.length == 36) {
                        self.isNewMode(false);
                        self.onSelectHistory(id);
                    }
                })
                
                // On new mode.
                self.isNewMode.subscribe(val => {
                    if (val) {
                        self.onRegistNew();
                    }
                })
            }

            /**
             * Start page.
             */
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                $.when(self.loadMasterHistory(), self.start()).done((res1, res2) => {
                    if (!self.masterHistoryList || self.masterHistoryList.length == 0) {
                        // Set new mode.
                        self.isNewMode(true);
                        self.onRegistNew();
                    } else {
                        // Not new mode and select first history.
                        self.isNewMode(false);
                        self.selectedHistoryUuid(self.masterHistoryDatasource()[0].childs[0].id);
                    }
                    
                    // resole.
                    dfd.resolve();
                }).fail(dfd.fail);

                return dfd.promise();
            }

            /**
             * Load master history.
             */
            loadMasterHistory(): JQueryPromise<Array<M>> {
                var self = this;
                var dfd = $.Deferred();
                
                self.service.loadMasterModelList().done(res => {
                    var nodeList = _.map(res, master => {
                        // Current node.
                        var masterNode:Node = {
                            id: master.code,
                            nodeText: master.code + ' ' + master.name,
                            nodeType: 0,
                            data: master
                        };

                        // Child node.
                        var masterChild = _.map(master.historyList, history => {
                            var node:Node = {
                                id: history.uuid,
                                nodeText: nts.uk.time.formatYearMonth(history.start) + '~' + nts.uk.time.formatYearMonth(history.end),
                                nodeType: 1,
                                parent: masterNode,
                                data: history
                            };
                            return node;
                        })

                        masterNode.childs = masterChild;
                        return masterNode;
                    })

                    self.masterHistoryList = res;
                    self.masterHistoryDatasource(nodeList);
                    dfd.resolve();
                })

                return dfd.promise();
            }

            /**
             * Start registr new.
             */
            registBtnClick(): void {
                var self = this;
                self.isNewMode(true);

                // Clear select history uuid.
                self.selectedHistoryUuid(undefined);
            }

            /**
             * Save current master and history data.
             */
            saveBtnClick(): void {
                var self = this;
                self.onSave().done((uuid) => {
                    self.loadMasterHistory().done(() => {
                        self.selectedHistoryUuid(uuid);
                    })
                }).fail(() => {
                    // Do nothing.
                })
            }

            /**
             * Open add new hisotry dialog.
             */
            addNewHistoryBtnClick(): void {
                var self = this;
                var currentNode = self.getCurrentHistoryNode();
                var newHistoryOptions: newhistory.viewmodel.NewHistoryScreenOption = {
                    name: self.options.functionName,
                    master: currentNode.parent.data,
                    lastest: currentNode.data,
                    
                    // Copy.
                    onCopyCallBack: (data) => {
                        self.service.createHistory(data.masterCode, data.startYearMonth, true)
                            .done(h => self.reloadMasterHistory(h.uuid));
                    },

                    // Init.
                    onCreateCallBack: (data) => {
                        self.service.createHistory(data.masterCode, data.startYearMonth, false)
                            .done(h => self.reloadMasterHistory(h.uuid));
                    }
                };
                nts.uk.ui.windows.setShared('options', newHistoryOptions);
                var ntsDialogOptions = { title: nts.uk.text.format('{0}の登録 > 履歴の追加', self.options.functionName),
                        dialogClass: 'no-close' }; 
                nts.uk.ui.windows.sub.modal('/view/base/simplehistory/newhistory/index.xhtml', ntsDialogOptions);
            }

            /**
             * Open update history btn.
             */
            updateHistoryBtnClick(): void {
                var self = this;
                var currentNode = self.getCurrentHistoryNode();
                var newHistoryOptions: updatehistory.viewmodel.UpdateHistoryScreenOption = {
                    name: self.options.functionName,
                    master: currentNode.parent.data,
                    history: currentNode.data,
                    removeMasterOnLastHistoryRemove: self.options.removeMasterOnLastHistoryRemove,

                    // Delete callback.
                    onDeleteCallBack: (data) => {
                        self.service.deleteHistory(data.masterCode, data.historyId).done(() => {
                            self.reloadMasterHistory(null);
                        });
                    },

                    // Update call back.
                    onUpdateCallBack: (data) => {
                        self.service.updateHistoryStart(data.masterCode, data.historyId, data.startYearMonth).done(() => {
                            self.reloadMasterHistory(self.selectedHistoryUuid());
                        })
                    }
                };
                nts.uk.ui.windows.setShared('options', newHistoryOptions);
                var ntsDialogOptions = { title: nts.uk.text.format('{0}の登録 > 履歴の編集', self.options.functionName),
                        dialogClass: 'no-close' }; 
                nts.uk.ui.windows.sub.modal('/view/base/simplehistory/updatehistory/index.xhtml', ntsDialogOptions);
            }
            
            /**
             * Reload master history then set.
             */
            reloadMasterHistory(uuid: string) {
                var self = this;
                self.loadMasterHistory().done(() => {
                    if (uuid) {
                        self.selectedHistoryUuid(uuid);
                    } else {
                        if (self.masterHistoryList.length > 0) {
                            self.selectedHistoryUuid(self.masterHistoryList[0].historyList[0].uuid);
                        }
                    }
                })
            }
            
            /**
             * Internal start for each page.
             * Override if you need to do any thing.
             */
            start(): JQueryPromise<any> {
                var dfd = $.Deferred();
                dfd.resolve();                
                return dfd.promise();
            }
            
            /**
             * On select history.
             */
            abstract onSelectHistory(uuid: string): void;

            /**
             * On regist new.
             */
            abstract onRegistNew(): void;
            
            /**
             * On save click.
             * Do validate your self.
             * Set error your self.
             * @return resolve with uuid of saved history or failed with any. 
             */
            abstract onSave(): JQueryPromise<string>;

            /**
             * Get current history node.
             */
            private getCurrentHistoryNode(): Node {
                var self = this;
                var nodeList = _.flatMap(self.masterHistoryDatasource(), (node) => {
                    return node.childs;
                })
                return _.first(_.filter(nodeList, (node) => {
                    return node.id == self.selectedHistoryUuid()
                        && self.selectedHistoryUuid().length > 4;
                }));
            }
        }

        /**
         * Node interface.
         */
        export interface Node {
            id: string;
            nodeText: string;
            nodeType: number;
            parent?: Node;
            childs?: Node[];
            data?: any;
        }
    }
}