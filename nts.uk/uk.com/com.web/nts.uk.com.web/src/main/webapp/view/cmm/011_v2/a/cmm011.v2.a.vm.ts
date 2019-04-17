module nts.uk.com.view.cmm011.v2.a.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import queryString = nts.uk.request.location.current.queryString;

    const LENGTH_HIERARCHY_ORIGIN = 3;

    export class ScreenModel {

        initMode: number = SCREEN_MODE.WORKPLACE;
        isUpdateMode: boolean = false;
        configuration: KnockoutObservable<WkpDepConfig>;
        items: KnockoutObservableArray<WkpDepNode>;
        selectedId: KnockoutObservable<string>;
        selectedCode: KnockoutObservable<string> = ko.observable(null);
        selectedName: KnockoutObservable<string> = ko.observable(null);
        selectedDispName: KnockoutObservable<string> = ko.observable(null);
        selectedGenericName: KnockoutObservable<string> = ko.observable(null);
        selectedHierarchyCode: KnockoutObservable<string> = ko.observable(null);
        selectedExternalCode: KnockoutObservable<string> = ko.observable(null);
        isSynchronized: KnockoutObservable<boolean> = ko.observable(false);
        listHierarchyChange: Array<any> = [];

        constructor() {
            let self = this;
            if (!_.isEmpty(queryString.items)) {
                self.initMode = Number(queryString.items["initmode"]);
            }
            self.configuration = ko.observable(new WkpDepConfig(null, null, null));
            self.items = ko.observableArray([]);
            self.selectedId = ko.observable(null);
            self.selectedId.subscribe(value => {
                if (_.isEmpty(value)) {
                    self.selectedCode(null);
                    self.selectedDispName(null);
                    self.selectedGenericName(null);
                    self.selectedHierarchyCode(null);
                    self.selectedExternalCode(null);
                    self.selectedName(null);
                    nts.uk.ui.errors.clearAll();
                } else {
                    block.invisible();
                    service.getWkpDepInforById(self.initMode, self.configuration().historyId, value).done(res => {
                        self.selectedCode(res.code);
                        self.selectedDispName(res.dispName);
                        self.selectedGenericName(res.genericName);
                        self.selectedHierarchyCode(res.hierarchyCode);
                        self.selectedExternalCode(res.externalCode);
                        self.selectedName(res.name);
                        nts.uk.ui.errors.clearAll();
                    }).fail((error) => {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                }
            });
            self.selectedName.subscribe(value => {
                if (_.isEmpty(value)) 
                    return;
                if (_.isEmpty(self.selectedDispName())) {
                    self.selectedDispName(value);
                }
                if (_.isEmpty(self.selectedGenericName())) {
                    self.selectedGenericName(self.getGenericName(value));
                }
                $(".nts-input").trigger("validate");
            });
            service.getOperationRule().done(res => {
                self.isSynchronized(res.synchWkpDep);
            }).fail((error) => {
                alertError(error);
            });
        }

        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.getConfiguration(self.initMode).done((configuration) => {
                if (configuration) {
                    self.configuration(new WkpDepConfig(configuration.historyId, configuration.startDate, configuration.endDate));
                    self.getAllWkpDepInfor().done(() => {
                        dfd.resolve();
                    }).fail((error) => {
                        dfd.reject();
                    }).always(() => {
                        block.clear()
                    });
                } else {
                    dfd.resolve();
                    self.openConfigDialog();
                }
            }).fail((error) => {
                dfd.reject();
                alertError(error);
                block.clear();
            });
            return dfd.promise();
        }

        getAllWkpDepInfor(idToSelect?: string): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            service.getAllWkpDepInforTree(self.initMode, self.configuration().historyId).done((data) => {
                if (_.isEmpty(data)) {
                    dfd.resolve();
                    if (self.initMode == SCREEN_MODE.WORKPLACE) {
                        info("Msg_373").then(() => {
                            self.openWkpDepCreateDialog();
                        });
                    } else {
                        info("Msg_1503").then(() => {
                            self.openWkpDepCreateDialog();
                        });
                    }
                } else {
                    let listNode = _.map(data, i => {
                        return new WkpDepNode(i);
                    });
                    self.items(listNode);
                    if (idToSelect)
                        self.selectedId(idToSelect);
                    else if (self.items().length > 0)
                        self.selectedId(self.items()[0].id);
                    dfd.resolve();
                }
            }).fail((error) => {
                dfd.reject();
                alertError(error);
            }).always(() => {
                block.clear()
            });
            return dfd.promise();
        }
        
        registerMaster() {
            let self = this;
            $(".nts-input").trigger("validate");
            if (nts.uk.ui.errors.hasError()) 
                return;
            block.invisible();
            let command = {
                initMode: self.initMode,
                historyId: self.configuration().historyId,
                id: self.selectedId(),
                code: self.selectedCode(),
                name: self.selectedName(),
                dispName: self.selectedDispName(),
                genericName: self.selectedGenericName(),
                externalCode: self.selectedExternalCode(),
                hierarchyCode: self.selectedHierarchyCode(), 
                listHierarchyChange: self.listHierarchyChange
            };
            service.registerWkpDepInfor(command).done((id) => {
                info({ messageId: "Msg_15" }).then(() => {
                    self.getAllWkpDepInfor(id);
                });
            }).fail(error => {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }
        
        deleteMaster() {
            let self = this;
            confirm({ messageId: "Msg_18" }).ifYes(() => {
                block.invisible();
                let data = {
                    initMode: self.initMode,
                    historyId: self.configuration().historyId,
                    selectedWkpDepId: self.selectedId()
                };
                service.deleteWkpDepInfor(data).done(() => {
                    info({ messageId: "Msg_16" }).then(() => {
                        block.invisible();
                        self.getAllWkpDepInfor().done(() => {
                            
                        }).always(() => {
                            block.clear()
                        });
                    });
                }).fail(error => {
                    alertError(error);
                }).always(() => {
                    block.clear();
                });
            }).ifNo(() => {
            });
        }

        openConfigDialog() {
            let self = this,
                params = {
                    initMode: self.initMode,
                    historyId: self.configuration() ? self.configuration().historyId : null
                };
            setShared("CMM011AParams", params);
            modal("/view/cmm/011_v2/b/index.xhtml").onClosed(() => {
                let params = getShared("CMM011BParams");
                if (params) {
                    self.configuration().historyId = params.historyId;
                    self.configuration().startDate(params.startDate);
                    self.configuration().endDate(params.endDate);
                    block.invisible();
                    self.getAllWkpDepInfor().done(() => {
                        
                    }).always(() => {
                        block.clear()
                    });
                }
            });
        }

        openWkpDepCreateDialog() {
            let self = this;
            block.invisible();
            service.checkTotalWkpDepInfor(self.initMode, self.configuration().historyId).done(() => {
                let params = {
                    initMode: self.initMode,
                    selectedCode: self.selectedCode(),
                    selectedName: self.selectedName(),
                    history: self.configuration().historyId
                };
                setShared("CMM011AParams", params);
                modal("/view/cmm/011_v2/d/index.xhtml").onClosed(() => {
                    let value = getShared("CreatedWorkplaceCondition");
                    if (value) {
                        let newHCode = "", currentHierarchyCode = self.selectedHierarchyCode();
                        if (value == CreationType.CREATE_TO_CHILD) {
                            newHCode = currentHierarchyCode + "001";
                            self.selectedId(null);
                            self.selectedHierarchyCode(newHCode);
                        } else {
                            let level = currentHierarchyCode.length / 3;
                            let parentCode = currentHierarchyCode.substring(0, (level - 1) * 3);
                            let items = _.cloneDeep(self.items()), newItems = [];
                            for (let i = 1; i < level; i++) {
                                let hCode = currentHierarchyCode.substring(0, 3 * i);
                                let node = _.find(items, i => i.hierarchyCode == hCode);
                                items = node.children;
                            }
                            let currIndex = _.findIndex(items, i => i.hierarchyCode == currentHierarchyCode);
                            switch (value) {
                                case CreationType.CREATE_ON_TOP:
                                    items.forEach((item, index) => {
                                        if (index == currIndex) {
                                            newItems.push({ hierarchyCode: item.hierarchyCode, children: [] });
                                        }
                                        newItems.push(item);
                                    });
                                    break;
                                case CreationType.CREATE_BELOW:
                                    currIndex += 1;
                                    if (currIndex == items.length) {
                                        newItems = items;
                                        newItems.push({ hierarchyCode: "", children: [] });
                                    } else {
                                        items.forEach((item, index) => {
                                            if (index == currIndex) {
                                                newItems.push({ hierarchyCode: item.hierarchyCode, children: [] });
                                            }
                                            newItems.push(item);
                                        });
                                    }
                                    break;
                                default:
                                    break;
                            }
                            self.listHierarchyChange = [];
                            self.generateHierarchyCode(newItems, parentCode);
                            self.selectedId(null);
                            self.selectedHierarchyCode(newItems[currIndex].hierarchyCode);
                        }
                    }
                });
            }).fail((error) => {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }

        moveLeft() {
            let self = this;
            let node = $("#A4_1").ntsTreeDrag("getSelected");
            let target = $("#A4_1").ntsTreeDrag("getParent", node.data.id)
            if (target)
                $("#A4_1").ntsTreeDrag("moveNext", target.data.id, node.data.id);
        }

        moveRight() {
            let self = this;
            let node = $("#A4_1").ntsTreeDrag("getSelected");
            let target = $("#A4_1").ntsTreeDrag("getPrevious", node.data.id)
            if (target)
                $("#A4_1").ntsTreeDrag("moveInto", target.data.id, node.data.id);
        }

        moveUp() {
            let self = this;
            let node = $("#A4_1").ntsTreeDrag("getSelected");
            $("#A4_1").ntsTreeDrag("moveUp", node.id);
        }

        moveDown() {
            let self = this;
            let node = $("#A4_1").ntsTreeDrag("getSelected");
            $("#A4_1").ntsTreeDrag("moveDown", node.id);
        }
        
        getGenericName(value: string): string {
            let self = this, result = "";
            let currentHierarchyCode = self.selectedHierarchyCode();
            if (currentHierarchyCode) {
                let level = currentHierarchyCode.length/3;
                let items = self.items();
                for (let i = 1; i < level; i++) {
                    let hCode = currentHierarchyCode.substring(0, 3*i);
                    let node = _.find(items, i => i.hierarchyCode == hCode);
                    result = result + node.name + " ";
                    items = node.children;
                }
            }
            return _.isEmpty(result) ? value : result + " " + value;
        }
        
        generateHierarchyCode(items: Array<WkpDepNode>, parentHierarchyCode: string) {
            let self = this;
            items.forEach((node, index) => {
                let hCode = ++index + "";
                if (hCode.length == 1) hCode = "00"+ hCode;
                if (hCode.length == 2) hCode = "0"+ hCode;
                node.hierarchyCode = parentHierarchyCode + hCode;
                self.listHierarchyChange.push({ id: node.id, hierarchyCode: node.hierarchyCode });
                self.generateHierarchyCode(node.children, node.hierarchyCode);
            })
        }

    }

    enum SCREEN_MODE {
        WORKPLACE = 0,
        DEPARTMENT = 1
    }
    
    enum CreationType {
        CREATE_ON_TOP = 1,
        CREATE_BELOW = 2,
        CREATE_TO_CHILD = 3
    }

    class WkpDepNode {
        id: string;
        code: string;
        name: string;
        nodeText: string;
        hierarchyCode: string;
        children: Array<WkpDepNode>;

        constructor(param) {
            if (param) {
                this.id = param.id;
                this.code = param.code;
                this.name = param.name;
                this.nodeText = param.code + ' ' + param.name;
                this.hierarchyCode = param.hierarchyCode;
                this.children = _.isEmpty(param.children) ? [] : _.map(param.children, i => {return new WkpDepNode(i)});
            }
        }
    }

    class WkpDepConfig {
        historyId: string;
        startDate: KnockoutObservable<string>;
        endDate: KnockoutObservable<string>;

        constructor(histId: string, startDate: string, endDate: string) {
            this.historyId = histId;
            this.startDate = ko.observable(startDate);
            this.endDate = ko.observable(endDate);
        }

    }

    class WkpDepInformation {
        id: KnockoutObservable<string> = ko.observable(null);
        code: KnockoutObservable<string> = ko.observable(null);
        name: KnockoutObservable<string> = ko.observable(null);
        dispName: KnockoutObservable<string> = ko.observable(null);
        genericName: KnockoutObservable<string> = ko.observable(null);
        hierarchyCode: KnockoutObservable<string> = ko.observable(null);
        externalCode: KnockoutObservable<string> = ko.observable(null);

        constructor(params: any) {
            if (params) {
                this.id(params.id);
                this.code(params.code);
                this.name(params.name);
                this.dispName(params.dispName);
                this.genericName(params.genericName);
                this.hierarchyCode(params.hierarchyCode);
                this.externalCode(params.externalCode);
            }
        }
    }

}
