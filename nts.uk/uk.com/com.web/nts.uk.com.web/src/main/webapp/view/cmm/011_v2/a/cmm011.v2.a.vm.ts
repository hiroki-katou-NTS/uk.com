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
        selectedInfor: KnockoutObservable<WkpDepInformation>;

        constructor() {
            let self = this;
            if (!_.isEmpty(queryString.items)) {
                self.initMode = Number(queryString.items["initmode"]);
            }
            self.configuration = ko.observable(new WkpDepConfig(null, null, null));
            self.items = ko.observableArray([]);
            self.selectedId = ko.observable(null);
            self.selectedInfor = ko.observable(new WkpDepInformation(null));
            self.selectedId.subscribe(value => {
                self.selectedInfor().id(value);
                self.selectedInfor().code(value);
                self.selectedInfor().name(value);
                self.selectedInfor().dispName(value);
                self.selectedInfor().genericName(value);
                self.selectedInfor().hierarchyCode(value);
                self.selectedInfor().externalCode(value);
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

        getAllWkpDepInfor(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            service.getAllWkpDepInfor(self.initMode, self.configuration().historyId).done((data) => {
                if (_.isEmpty(data)) {
                    dfd.resolve();
                    info("Msg_373").then(() => {
//                        self.openWkpDepCreateDialog();
                    });
                } else {
                    // hien thi len cay
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
                }
            });
        }

        openWkpDepCreateDialog() {
            let self = this;
            modal("/view/cmm/011_v2/d/index.xhtml").onClosed(() => {

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

        constructor(id: string, code: string, name: string, hierarchyCode: string, children: Array<WkpDepNode>) {
            var self = this;
            self.id = id;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.hierarchyCode = hierarchyCode;
            self.children = children;
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
