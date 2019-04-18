module nts.uk.com.view.cmm011.v2.d.viewmodel {
    import getText = nts.uk.resource.getText;
    import modal = nts.uk.ui.windows.sub.modal;
    import info = nts.uk.ui.dialog.info;
    import block = nts.uk.ui.block;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        itemList: KnockoutObservableArray<any>;
        createMethod: KnockoutObservable<number> = ko.observable(null);

        id: KnockoutObservable<string> = ko.observable(null);
        code: KnockoutObservable<string> = ko.observable(null);
        name: KnockoutObservable<string> = ko.observable(null);
        displayName: KnockoutObservable<string> = ko.observable(null);
        genericName: KnockoutObservable<string> = ko.observable(null);
        externalCode: KnockoutObservable<string> = ko.observable(null);
        hierarchyCode: KnockoutObservable<string> = ko.observable(null);

        selectedHistoryId: string;
        selectedCode: KnockoutObservable<string> = ko.observable(null);
        selectedName: KnockoutObservable<string> = ko.observable(null);
        selectedHierarchyCode: KnockoutObservable<string> = ko.observable(null);
        itemsCount: KnockoutObservable<number> = ko.observable(0);
        screenMode: number;
        hierarchyNumber: number = 999;
        hierarchyLevel: number = 10;
        items: Array<any> = [];
        listHierarchyChange: Array<any> = [];

        constructor() {
            let self = this, params = nts.uk.ui.windows.getShared("CMM011AParams");
            if (params) {
                self.screenMode = params.initMode;
                self.selectedCode(params.selectedCode);
                self.selectedName(params.selectedName);
                self.selectedHierarchyCode(params.selectedHierarchyCode);
                self.hierarchyLevel = params.selectedHierarchyCode.length / 3;
                self.hierarchyNumber = Number(self.selectedHierarchyCode().substring(3 * (self.hierarchyLevel - 1)));
                self.selectedHistoryId = params.history;
                self.items = params.items;
                self.itemsCount(self.items.length);
            }
            self.itemList = ko.observableArray([
                new BoxModel(CreationType.CREATE_ON_TOP,
                    self.screenMode == SCREEN_MODE.WORKPLACE ? getText('CMM011_211') : getText('CMM011_311'),
                    self.hierarchyNumber < 999
                ),
                new BoxModel(CreationType.CREATE_BELOW,
                    self.screenMode == SCREEN_MODE.WORKPLACE ? getText('CMM011_212') : getText('CMM011_312'),
                    self.hierarchyNumber < 999
                ),
                new BoxModel(CreationType.CREATE_TO_CHILD,
                    self.screenMode == SCREEN_MODE.WORKPLACE ? getText('CMM011_213') : getText('CMM011_313'),
                    self.hierarchyLevel < 10
                )
            ]);
            self.code.subscribe(value => {
                if (value)
                    self.checkInputCode();
            });
            self.name.subscribe(value => {
                if (_.isEmpty(value)) 
                    return;
                if (_.isEmpty(self.displayName())) {
                    self.displayName(value);
                }
                if (_.isEmpty(self.genericName())) {
                    self.genericName(self.getGenericName(value));
                }
//                $(".nts-input").trigger("validate");
            });
            self.createMethod.subscribe(value => {
                let items = _.cloneDeep(self.items);
                if (items.length == 0) {
                    self.hierarchyCode("001");
                } else {
                    let currentHierarchyCode = self.selectedHierarchyCode();
                    if (self.createMethod() == CreationType.CREATE_TO_CHILD) {
                        let newHCode = currentHierarchyCode + "001";
                        self.hierarchyCode(newHCode);
                    } else {
                        let parentCode = currentHierarchyCode.substring(0, (self.hierarchyLevel - 1) * 3);
                        let newItems = [];
                        for (let i = 1; i < self.hierarchyLevel; i++) {
                            let hCode = currentHierarchyCode.substring(0, 3 * i);
                            let node = _.find(items, i => i.hierarchyCode == hCode);
                            items = node.children;
                        }
                        let currIndex = _.findIndex(items, i => i.hierarchyCode == currentHierarchyCode);
                        switch (self.createMethod()) {
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
                        self.generateHierarchyCode(newItems, parentCode);
                        self.hierarchyCode(newItems[currIndex].hierarchyCode);
                    }
                }
            });
        }

        checkInputCode() {
            let self = this;
            block.invisible();
            service.checkCode(self.screenMode, self.selectedHistoryId, self.code()).done(checkResult => {
                if (checkResult.usedInThePast) {
                    let params = {
                        initMode: self.screenMode,
                        listDuplicate: checkResult.listDuplicatePast
                    };
                    setShared("CMM011DParams", params);
                    modal("/view/cmm/011_v2/c/index.xhtml").onClosed(() => {
                        let result = getShared("CMM011CParams");
                        if (result) {
                            if (result.targetId && result.historyId) {
                                
                            } else {
                                // NEW MODE
                            }
                        } else {
                            self.code(null);
                        }
                    });
                } else {
                    // NEW MODE
                }
            }).fail(error => {
                alertError(error).then(() => {
                    if (error.messageId == "Msg_3") {
                        self.code(null);
                    }
                });
            }).always(() => {
                block.clear();
            });
        }
        
        register() {
            let self = this;
            $(".nts-input").trigger("validate");
            if (nts.uk.ui.errors.hasError()) 
                return;
            block.invisible();
            let command = {
                initMode: self.screenMode,
                historyId: self.selectedHistoryId,
                id: self.id(),
                code: self.code(),
                name: self.name(),
                dispName: self.displayName(),
                genericName: self.genericName(),
                externalCode: self.externalCode(),
                hierarchyCode: self.hierarchyCode(), 
                listHierarchyChange: self.listHierarchyChange
            };
            service.registerWkpDepInfor(command).done((id) => {
                info({ messageId: "Msg_15" }).then(() => {
                    setShared("CreatedWorkplace", { created: true });
                    nts.uk.ui.windows.close();
                });
            }).fail(error => {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }

        cancel() {
            nts.uk.ui.windows.close();
        }
        
        generateHierarchyCode(items: Array<any>, parentHierarchyCode: string) {
            let self = this;
            items.forEach((node, index) => {
                let hCode = ++index + "";
                if (hCode.length == 1) hCode = "00"+ hCode;
                if (hCode.length == 2) hCode = "0"+ hCode;
                let newHierarchyCode = parentHierarchyCode + hCode;
                if (node.hierarchyCode != newHierarchyCode) {
                    node.hierarchyCode = newHierarchyCode;
                    self.listHierarchyChange = self.listHierarchyChange.filter(i => i.id != node.id);
                    self.listHierarchyChange.push({ id: node.id, hierarchyCode: node.hierarchyCode });
                }
                self.generateHierarchyCode(node.children, node.hierarchyCode);
            })
        }
        
        getGenericName(value: string): string {
            let self = this, result = "";
            let currentHierarchyCode = self.hierarchyCode();
            if (currentHierarchyCode) {
                let level = currentHierarchyCode.length/3;
                let items = _.cloneDeep(self.items);
                for (let i = 1; i < level; i++) {
                    let hCode = currentHierarchyCode.substring(0, 3*i);
                    let node = _.find(items, i => i.hierarchyCode == hCode);
                    result = result + node.name + " ";
                    items = node.children;
                }
            }
            return _.isEmpty(result) ? value : result + " " + value;
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

    class BoxModel {
        id: number;
        name: string;
        enable: KnockoutObservable<boolean>;

        constructor(id, name, enable) {
            var self = this;
            self.id = id;
            self.name = name;
            self.enable = ko.observable(enable);
        }
    }

    class RegisterInfor {
        id: string;
        code: string;
        name: string;
        displayName: string;
        genericName: string;
        externalCode: string;
        hierarchyCode: string;

        constructor(id: string, code: string, name: string, displayName: string, genericName: string, externalCode: string, hierarchyCode: string) {
            this.id = id;
            this.code = code;
            this.name = name;
            this.displayName = displayName;
            this.genericName = genericName;
            this.externalCode = externalCode;
            this.hierarchyCode = hierarchyCode;
        }
    }
}