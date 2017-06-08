module ccg030.a.viewmodel {
    import windows = nts.uk.ui.windows;
    import errors = nts.uk.ui.errors;
    import util = nts.uk.util;
    import text = nts.uk.text;

    export class ScreenModel {
        // list FlowMenu
        listFlowMenu: KnockoutObservableArray<any>;
        columns: KnockoutObservableArray<any>;
        selectedFlowMenuCD: KnockoutObservable<string>;
        // Details FlowMenu 
        selectedFlowMenu: KnockoutObservable<model.FlowMenu>;
        tempFileID: KnockoutObservable<string>;
        isCreate: KnockoutObservable<boolean>;
        isDelete: KnockoutObservable<boolean>;
        enableDownload: KnockoutObservable<boolean>;
        // Message
        listMessage: KnockoutObservableArray<ItemMessage>;

        constructor() {
            var self = this;
            // list
            self.listFlowMenu = ko.observableArray([]);
            self.selectedFlowMenuCD = ko.observable(null);
            self.selectedFlowMenuCD.subscribe((value) => {
                self.findFlowMenu(value);
            });
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("CCG030_19"), key: 'defClassAtr', width: 40, columnCssClass: 'halign-center', template: '{{if ${defClassAtr} == 1 }}<i class="icon icon-dot "></i>{{/if}}' },
                { headerText: nts.uk.resource.getText("CCG030_9"), key: 'topPageCode', width: 45 },
                { headerText: nts.uk.resource.getText("CCG030_10"), key: 'topPageName', width: 260 }
            ]);
            // Details
            self.selectedFlowMenu = ko.observable(new model.FlowMenu());
            self.tempFileID = ko.observable('');
            self.isCreate = ko.observable(null);
            self.isDelete = ko.observable(false);
            self.isCreate.subscribe((value) => {
                self.changeMode(value);
            });
            // Enable
            self.enableDownload = ko.observable(true);
            // Message
            self.listMessage = ko.observableArray([]);
        }

        /** Start page */
        startPage(): JQueryPromise<any> {
            var self = this;
            nts.uk.ui.block.invisible();
            var dfd = self.reloadData();
            dfd.done(() => {
                nts.uk.ui.block.clear();
                self.selectFlowMenuByIndex(0);
            });

            return dfd;
        }

        /** Creat new FlowMenu */
        createNewFlowMenu() {
            var self = this;
            $(".nts-input").ntsError("clear");
            self.isCreate(true);
            self.selectedFlowMenuCD(null);
            self.selectedFlowMenu(new model.FlowMenu("", "", "", "", "", 0, 1, 1));
        }

        /** Click Registry button */
        registryFlowMenu() {
            var self = this;
            self.selectedFlowMenu().topPageCode(text.padLeft($("#inpCode").val(), '0', 4));
            var flowMenu = ko.mapping.toJS(self.selectedFlowMenu);
            var topPageCode = flowMenu.topPageCode;
            $(".nts-input").trigger("validate");
            if (util.isNullOrEmpty(self.selectedFlowMenu().fileID()))
                $('#file_upload').ntsError('set', '選択されていないファイル');
            if (!errors.hasError()) {
                nts.uk.ui.block.invisible();
                if (self.isCreate() === true) {
                    service.createFlowMenu(flowMenu).done((data) => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        self.reloadData().done(() => {
                            self.selectFlowMenuByIndexByCode(topPageCode);
                        });
                    }).fail((res) => {
                        nts.uk.ui.dialog.alertError(nts.uk.resource.getMessage({ messageId: "Msg_3" }));
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                }
                else {
                    service.updateFlowMenu(flowMenu).done((data) => {
                        self.reloadData();
                        _.defer(() => { $("#inpName").focus(); });
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                }
            }
        }

        //蜑企勁繝懊ち繝ｳ繧呈款縺呎凾
        deleteNewFlowMenu() {
            var self = this;
            if (self.selectedFlowMenuCD() !== null) {
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(function() {
                    nts.uk.ui.block.invisible();
                    service.deleteFlowMenu(self.selectedFlowMenu().toppagePartID())
                        .done(() => {
                            var index = _.findIndex(self.listFlowMenu(), ['titleMenuCD', self.selectedFlowMenu().topPageCode()]);
                            index = _.min([self.listFlowMenu().length - 2, index]);
                            self.reloadData().done(() => {
                                self.selectFlowMenuByIndex(index);
                            });
                            nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                            errors.clearAll();
                        }).fail((res) => {
                            nts.uk.ui.dialog.alertError({ messageId: "Msg_76" });
                        }).always(() => {
                            nts.uk.ui.block.clear();
                        });
                });
            }
        }

        /** Upload File */
        uploadFile(): void {
            var self = this;
            if (self.isCreate() === true) {
                self.uploadFileProcess();
            }
            else {
                service.getFlowMenuById(self.selectedFlowMenu().toppagePartID()).done(function(res) {
                    if (res.defClassAtr === 1) {
                        nts.uk.ui.dialog.alert({ messageId: "Msg_84" });
                    }
                    else {
                        self.uploadFileProcess();
                    }
                });
            }
        }

        private uploadFileProcess(): void {
            var self = this;
            var option = {
                stereoType: "flowmenu",
                onSuccess: function() { },
                onFail: function() { }
            }
            /*if (!util.isNullOrEmpty(self.selectedFlowMenu().toppagePartID())) {
                self.deleteFile();
            }*/
            $("#file_upload").ntsFileUpload(option).done(function(res) {
                self.tempFileID(res[0].id);
                self.selectedFlowMenu().fileID(res[0].id);
                self.selectedFlowMenu().fileName(res[0].originalName);
                self.isDelete(true);
                errors.clearAll();
            }).fail(function(err) {
                nts.uk.ui.dialog.alertError({ messageId: err.messageId });
            });
        }

        deleteButtonClick(): void {
            var self = this;
            var toppagePartID = self.selectedFlowMenu().toppagePartID();
            if (toppagePartID) {
                service.getFlowMenuById(self.selectedFlowMenu().toppagePartID()).done(function(res) {
                    if (res.defClassAtr === 1) {
                        nts.uk.ui.dialog.alert({ messageId: "Msg_83" });
                    }
                    else {
                        self.tempFileID(self.selectedFlowMenu().fileID());
                        self.selectedFlowMenu().fileID('');
                        self.selectedFlowMenu().fileName('');
                        self.isDelete(false);
                    }
                })
            } else {
                self.tempFileID(self.selectedFlowMenu().fileID());
                self.selectedFlowMenu().fileID('');
                self.selectedFlowMenu().fileName('');
                self.isDelete(false);
            }
        }

        private deleteFile(): void {
            var self = this;
            nts.uk.ui.block.invisible();
            service.deleteFile(self.tempFileID()).done((data) => {
                self.selectedFlowMenu().fileID('');
                self.selectedFlowMenu().fileName('');
            }).fail(function(error) {
                nts.uk.ui.dialog.alertError({ messageId: error.messageId });
            }).always(() => {
                nts.uk.ui.block.clear();
            });
        }

        downloadFile(): void {
            var self = this;
            nts.uk.request.specials.donwloadFile(self.selectedFlowMenu().fileID());
        }
        /** Close Dialog */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

        // Open ccg030 B Dialog
        open030B_Dialog() {
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.setShared("flowmenu", this.selectedFlowMenu(), false);
            nts.uk.ui.windows.sub.modal("/view/ccg/030/b/index.xhtml", { title: nts.uk.resource.getText("CCG030_4"), dialogClass: "no-close" }).onClosed(() => {
                nts.uk.ui.block.clear();    
            });
        }

        /** Find Current FlowMenu by ID */
        private findFlowMenu(flowmenuCD: string): void {
            var self = this;
            $(".nts-input").ntsError("clear");
            var selectedFlowmenu = _.find(self.listFlowMenu(), ['topPageCode', flowmenuCD]);
            if (selectedFlowmenu !== undefined) {
                self.selectedFlowMenu(new model.FlowMenu(selectedFlowmenu.toppagePartID,
                    selectedFlowmenu.topPageCode, selectedFlowmenu.topPageName,
                    selectedFlowmenu.fileID, selectedFlowmenu.fileName, selectedFlowmenu.defClassAtr,
                    selectedFlowmenu.widthSize, selectedFlowmenu.heightSize));
                self.isCreate(false);
                if (!util.isNullOrEmpty(selectedFlowmenu.fileID))
                    self.isDelete(true);
                else
                    self.isDelete(false);
            }
            else {
                self.selectedFlowMenu(new model.FlowMenu("", "", "", "", "", 0, 1, 1));
                self.isCreate(true);
                self.isDelete(false);
            }
        }

        //蛻晄悄繝�繝ｼ繧ｿ蜿門ｾ怜�ｦ逅�
        private reloadData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            /** Get list FlowMenu*/
            service.fillAllFlowMenu().done(function(listFlowMenu: Array<any>) {
                listFlowMenu = _.orderBy(listFlowMenu, ["topPageCode"], ["asc"]);
                self.listFlowMenu(listFlowMenu);
                if (listFlowMenu.length > 0) {
                    self.isCreate(false);
                    _.defer(() => { $("#inpName").focus(); });
                }
                else {
                    self.findFlowMenu(null);
                    self.isCreate(true);
                }
                dfd.resolve();
            }).fail(function(error) {
                dfd.fail();
                alert(error.message);
            });
            return dfd.promise();
        }

        /** Init Mode */
        private changeMode(isCreate: boolean) {
            var self = this;
            $(".nts-input").ntsError("clear");
            if (isCreate === true) {
                self.selectedFlowMenuCD(null);
                self.selectedFlowMenu(new model.FlowMenu("", "", "", "", "", 0, 1, 1));
                _.defer(() => { $("#inpCode").focus(); });
            }
        }

        /** Select FlowMenu by Code: Create & Update case*/
        private selectFlowMenuByIndexByCode(topPageCode: string) {
            this.selectedFlowMenuCD(topPageCode);
        }

        /** Select FlowMenu by Index: Start & Delete case */
        private selectFlowMenuByIndex(index: number) {
            var self = this;
            var selectFlowMenuByIndex = _.nth(self.listFlowMenu(), index);
            if (selectFlowMenuByIndex !== undefined)
                self.selectedFlowMenuCD(selectFlowMenuByIndex.topPageCode);
            else
                self.selectedFlowMenuCD(null);
        }

        private messName(messCode: string): string {
            var self = this;
            var Msg = _.find(self.listMessage(), function(mess) {
                return mess.messCode === messCode;
            })
            return Msg.messName;
        }
    }

    export module model {
        export class FlowMenu {
            toppagePartID: KnockoutObservable<string>;
            topPageCode: KnockoutObservable<string>;
            topPageName: KnockoutObservable<string>;
            fileID: KnockoutObservable<string>;
            fileName: KnockoutObservable<string>;
            defClassAtr: KnockoutObservable<number>;
            widthSize: KnockoutObservable<number>;
            heightSize: KnockoutObservable<number>;
            "type": number;
            constructor(toppagePartID: string, topPageCode: string, topPageName: string, fileID: string, fileName: string, defClassAtr: number, widthSize: number, heightSize: number) {
                this.toppagePartID = ko.observable(toppagePartID);
                this.fileID = ko.observable(fileID);
                this.fileName = ko.observable(fileName);
                this.defClassAtr = ko.observable(defClassAtr);
                this.topPageCode = ko.observable(topPageCode);
                this.topPageName = ko.observable(topPageName || "");
                this.widthSize = ko.observable(widthSize);
                this.heightSize = ko.observable(heightSize);
                this.type = 2;
            }
        }
    }

    export class ItemMessage {
        messCode: string;
        messName: string;
        constructor(messCode: string, messName: string) {
            this.messCode = messCode;
            this.messName = messName;
        }
    }
}