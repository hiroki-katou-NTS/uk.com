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

        constructor() {
            let self = this;
            if (!_.isEmpty(queryString.items)) {
                self.initMode = Number(queryString.items["initmode"]);
            }
            self.configuration = ko.observable(new WkpDepConfig(null, null, null));
            self.items = ko.observableArray([]);
            self.selectedId = ko.observable(null);
            self.selectedId.subscribe(value => {
                nts.uk.ui.errors.clearAll();
                block.invisible();
                service.getWkpDepInforById(self.initMode, self.configuration().historyId, value).done(res => {
                    self.selectedCode(res.code);
                    self.selectedName(res.name);
                    self.selectedDispName(res.dispName);
                    self.selectedGenericName(res.genericName);
                    self.selectedHierarchyCode(res.hierarchyCode);
                    self.selectedExternalCode(res.externalCode);
                }).fail((error) => {
                    alertError(error);
                }).always(() => {
                    block.clear();
                });
            });
            self.selectedName.subscribe(value => {
                if (_.isEmpty(value)) 
                    return;
                if (_.isEmpty(self.selectedDispName())) {
                    self.selectedDispName(value);
                }
                if (_.isEmpty(self.selectedGenericName())) {
                    self.selectedGenericName(self.getGenericName(value, self.items()));
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
                        if (self.items().length > 0)
                            self.selectedId(self.items()[0].id);
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

        getAllWkpDepInfor(): JQueryPromise<any> {
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
                            if (self.items().length > 0)
                                self.selectedId(self.items()[0].id);
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
                    self.getAllWkpDepInfor().always(() => {
                        block.clear();
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
                    selectedName: self.selectedName()
                };
                setShared("CMM011AParams", params);
                modal("/view/cmm/011_v2/d/index.xhtml").onClosed(() => {
                    
                });
            }).fail((error) => {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }

        moveLeft() {
            let self = this;
        }

        moveRight() {
            let self = this;
        }

        moveUp() {
            let self = this;
        }

        moveDown() {
            let self = this;
        }
        
        getGenericName(value: string, items: Array<WkpDepNode>): string {
            let self = this, result = "";
            items.some(function(element, index) {
                if (element.id == self.selectedId()) {
                    return true;
                } else {
                    result += element.name;
                    if (_.isEmpty(element.children))
                        return false;
                    result = self.getGenericName(result, element.children);
                }
            });
            return result + " " + value;
        }

    }

    enum SCREEN_MODE {
        WORKPLACE = 0,
        DEPARTMENT = 1
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
