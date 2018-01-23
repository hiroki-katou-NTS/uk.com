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
        enablePreview: KnockoutObservable<boolean>;
        // Message
        listMessage: KnockoutObservableArray<ItemMessage>;
        //old file name
        oldFileName: KnockoutObservable<string>;

        constructor() {
            var self = this;
            // list
            self.oldFileName = ko.observable("未設定");
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
            // Enable
            self.enableDownload = ko.observable(true);
            self.enablePreview = ko.observable(true);
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
            errors.clearAll();
            self.isCreate(true);
            self.isDelete(false);
            self.selectedFlowMenuCD(null);
            self.selectedFlowMenu(new model.FlowMenu("", "", "", "", "未設定", 0, 4, 4));
            self.focusToInput();
        }

        /** Click Registry button */
        registryFlowMenu() {
            var self = this;
            $(".nts-input").trigger("validate");
            if (util.isNullOrEmpty(self.selectedFlowMenu().fileID()))
                $('#file_upload').ntsError('set', '選択されていないファイル');
            if (!errors.hasError()) {
                self.selectedFlowMenu().topPageCode(text.padLeft($("#inpCode").val(), '0', 4));
                var flowMenu = ko.mapping.toJS(self.selectedFlowMenu);
                var topPageCode = flowMenu.topPageCode;
                nts.uk.ui.block.invisible();
                if (self.isCreate() === true) {
                    service.createFlowMenu(flowMenu).done((data) => {
                        self.reloadData().done(() => {
                            self.selectFlowMenuByCode(topPageCode);
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                                self.focusToInput();
                            });
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
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(() => {
                            self.focusToInput();
                        });
                    }).always(() => {
                        nts.uk.ui.block.clear();
                    });
                }
            }
        }

        /** Delete FlowMenu */
        deleteFlowMenu() {
            var self = this;
            if (self.selectedFlowMenuCD() !== null) {
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(function() {
                    nts.uk.ui.block.invisible();
                    service.deleteFlowMenu(self.selectedFlowMenu().toppagePartID())
                        .done(() => {
                            var index = _.findIndex(self.listFlowMenu(), ['topPageCode', self.selectedFlowMenu().topPageCode()]);
                            index = _.min([self.listFlowMenu().length - 2, index]);
                            self.reloadData().done(() => {
                                self.selectFlowMenuByIndex(index);
                                nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(() => {
                                    self.focusToInput();
                                    errors.clearAll();
                                });
                            });
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
            
            nts.uk.ui.block.invisible();
            if (self.isCreate() === true) {
                self.uploadFileProcess();
            }
            else {
                service.getFlowMenuById(self.selectedFlowMenu().toppagePartID()).done(function(res) {
                    if (res.defClassAtr === 1) {
                        nts.uk.ui.dialog.alert({ messageId: "Msg_84" });
                        nts.uk.ui.block.clear();
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
            
            nts.uk.ui.block.invisible();
            
            $("#file_upload").ntsFileUpload(option).done(function(res) {
                self.tempFileID(res[0].id);
                self.selectedFlowMenu().fileID(res[0].id);
                self.selectedFlowMenu().fileName(res[0].originalName.length === 0 ? '未設定' : res[0].originalName);
                self.isDelete(true);
                errors.clearAll();
            }).fail(function(err) {
                self.selectedFlowMenu().fileName("");
                self.selectedFlowMenu().fileName(self.oldFileName().length === 0 ? '未設定' : self.oldFileName());
                nts.uk.ui.dialog.alertError(err.message);
            }).always(() => {
                nts.uk.ui.block.clear();
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
                        self.selectedFlowMenu().fileName('未設定');
                        self.isDelete(false);
                    }
                })
            } else {
                self.tempFileID(self.selectedFlowMenu().fileID());
                self.selectedFlowMenu().fileID('');
                self.selectedFlowMenu().fileName('未設定');
                self.isDelete(false);
            }
        }

        private deleteFile(): void {
            var self = this;
            nts.uk.ui.block.invisible();
            service.deleteFile(self.tempFileID()).done((data) => {
                self.selectedFlowMenu().fileID('');
                self.selectedFlowMenu().fileName('未設定');
            }).fail(function(error) {
                nts.uk.ui.dialog.alertError(error.message);
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

        /** Open ccg030 B Dialog */
        open030B_Dialog() {
            nts.uk.ui.block.invisible();
            nts.uk.ui.windows.setShared("flowmenu", ko.mapping.toJS(this.selectedFlowMenu()), false);
            nts.uk.ui.windows.setShared("fileID", this.selectedFlowMenu().fileID(), false);
            nts.uk.ui.windows.sub.modal("/view/ccg/030/b/index.xhtml", { title: nts.uk.resource.getText("CCG030_4"), dialogClass: "no-close" }).onClosed(() => {
                nts.uk.ui.block.clear();
            });
        }

        /** Find Current FlowMenu by ID */
        private findFlowMenu(flowmenuCD: string): void {
            var self = this;
            if (nts.uk.ui._viewModel !== undefined)
                errors.clearAll();
            var selectedFlowmenu = _.find(self.listFlowMenu(), ['topPageCode', flowmenuCD]);
            if (selectedFlowmenu !== undefined) {
                self.selectedFlowMenu(new model.FlowMenu(selectedFlowmenu.toppagePartID,
                    selectedFlowmenu.topPageCode, selectedFlowmenu.topPageName,
                    selectedFlowmenu.fileID,
                    nts.uk.text.isNullOrEmpty(selectedFlowmenu.fileName) ? '未設定' : selectedFlowmenu.fileName,
                    selectedFlowmenu.defClassAtr,
                    selectedFlowmenu.widthSize, selectedFlowmenu.heightSize));
                if(flowmenuCD !== null){
                    self.oldFileName(selectedFlowmenu.fileName);
                }
                self.isCreate(false);
                self.focusToInput();
                if (!util.isNullOrEmpty(selectedFlowmenu.fileID))
                    self.isDelete(true);
                else
                    self.isDelete(false);
            }
            else {
                self.selectedFlowMenu(new model.FlowMenu("", "", "", "", "未設定", 0, 4, 4));
                self.isCreate(true);
                self.isDelete(false);
            }
        }

        /** Reload Data */
        private reloadData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            // Get list FlowMenu
            service.fillAllFlowMenu().done(function(listFlowMenu: Array<any>) {
                listFlowMenu = _.orderBy(listFlowMenu, ["topPageCode"], ["asc"]);
                self.listFlowMenu(listFlowMenu);
                if (listFlowMenu.length > 0) {
                    self.isCreate(false);
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

        /** Select FlowMenu by Code: Create & Update case*/
        private selectFlowMenuByCode(topPageCode: string) {
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

        /** Focus to input */
        focusToInput() {
            if (this.isCreate() == true) {
                $("#inpCode").focus();
            }
            else {
                $("#inpName").focus();
            }
        }
    }

    export module model {
        
        export interface FlowMenuDto {
            toppagePartID: string;
            topPageCode: string;
            topPageName?: string;
            fileID: string;
            fileName: string;
            defClassAtr: number;
            widthSize: number;
            heightSize: number;
            "type": number;
        }
        
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
            
            fromDTO(dto: FlowMenuDto) {
                this.toppagePartID = ko.observable(dto.toppagePartID);
                this.fileID = ko.observable(dto.fileID);
                this.fileName = ko.observable(dto.fileName);
                this.defClassAtr = ko.observable(dto.defClassAtr);
                this.topPageCode = ko.observable(dto.topPageCode);
                this.topPageName = ko.observable(dto.topPageName || "");
                this.widthSize = ko.observable(dto.widthSize);
                this.heightSize = ko.observable(dto.heightSize);
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