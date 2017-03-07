module nts.uk.pr.view.base.simlehistory {
    export module viewmodel {
        /**
         * Base screen model for simple history.
         */
        export abstract class ScreenBaseModel<T extends model.MasterModel<V>, V extends model.HistoryModel> {
            // Service.
            service: service.Service<T, V>;

            // Whether or not in new mode.
            isNewMode: KnockoutObservable<boolean>;
            masterHistoryList: Array<T>;
            masterHistoryDatasource: KnockoutObservableArray<Node>
            selectedHistoryUuid: KnockoutObservable<string>;

            // Flag
            private canUpdateHistory: KnockoutObservable<boolean>;
            private canAddNewHistory: KnockoutObservable<boolean>;
            
            /**
             * Constructor.
             */
            constructor(service: service.Service<T, V>) {
                var self = this;

                // Set.
                self.service = service;

                // Init.
                self.isNewMode = ko.observable(true);
                self.masterHistoryDatasource = ko.observableArray([]);
                self.selectedHistoryUuid = ko.observable(undefined);
                
                // Can update history flag.
                self.canUpdateHistory = ko.computed(() => {
                    return self.selectedHistoryUuid() != null;
                })
                self.canAddNewHistory = ko.computed(() => {
                    return self.selectedHistoryUuid() != null;
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
                        self.isNewMode(true);
                    } else {
                        self.isNewMode(false);
                    }
                    
                    // resole.
                    dfd.resolve();
                }).fail(dfd.fail);

                return dfd.promise();
            }

            /**
             * Load master history.
             */
            loadMasterHistory(): JQueryPromise<Array<T>> {
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
                                nodeText: history.start + '~' + history.end,
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
             * Internal start for each page.
             * Override if you need to do any thing.
             */
            start(): JQueryPromise<any> {
                var dfd = $.Deferred();
                dfd.resolve();                
                return dfd.promise();
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