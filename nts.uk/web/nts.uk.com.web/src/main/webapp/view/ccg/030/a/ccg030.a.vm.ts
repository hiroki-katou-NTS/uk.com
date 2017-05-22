module ccg030.a.viewmodel {
    import windows = nts.uk.ui.windows;
    import errors = nts.uk.ui.errors;
    import util = nts.uk.util;

    export class ScreenModel {
        // list FlowMenu
        listFlowMenu: KnockoutObservableArray<any>;
        columns: KnockoutObservableArray<any>;
        selectedFlowMenuCD: KnockoutObservable<string>;
        // Details FlowMenu
        selectedFlowMenu: KnockoutObservable<model.FlowMenu>;
        isCreate: KnockoutObservable<boolean>;
        enableDeleteFile: KnockoutObservable<boolean>;
        enableDownload: KnockoutObservable<boolean>;
        // Message
        listMessage: KnockoutObservableArray<ItemMessage>;

        constructor() {
            var self = this;
            // list
            self.listFlowMenu = ko.observableArray([]);
            self.selectedFlowMenuCD = ko.observable("");
            self.selectedFlowMenuCD.subscribe((value) => {
                self.findFlowMenu(value);
            });
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("CCG030_19"), key: 'defClassAtr', width: 40, columnCssClass: 'halign-center', template: '{{if ${defClassAtr} == 1 }}<i class="icon icon-dot "></i>{{/if}}' },
                { headerText: nts.uk.resource.getText("CCG030_9"), key: 'topPageCode', width: 60 },
                { headerText: nts.uk.resource.getText("CCG030_10"), key: 'topPageName', width: 260 }
            ]);
            // Details
            self.selectedFlowMenu = ko.observable(null);
            self.isCreate = ko.observable(null);
            self.isCreate.subscribe((value) => {
                self.changeInitMode(value);
            });
            // Enable
            self.enableDeleteFile = ko.observable(true);
            self.enableDownload = ko.observable(true);
            // Message
            self.listMessage = ko.observableArray([]);
        }

        /** Start page */
        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = self.reloadData();
            dfd.done(() => {
                self.selectFlowMenuByIndex(0);
            });

            return dfd;
        }

        /** Creat new FlowMenu */
        createNewFlowMenu() {
            var self = this;
            self.isCreate(true);
        }
        
        /** Click Registry button */
        registryFlowMenu() {
            var self = this;
            var flowMenu = ko.mapping.toJS(self.selectedFlowMenu);
            var topPageCode = flowMenu.topPageCode;
            $(".nts-input").trigger("validate");
            if (util.isNullOrEmpty(self.selectedFlowMenu().fileID())
                $('#file_upload').ntsError('set', 'Chﾆｰa ch盻肱 file');
            _.delay(() => {
                if (!errors.hasError()) {
                    if (self.isCreate() === true) {
                        service.createFlowMenu(flowMenu).done((data) => {
                            nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("Msg_15"));
                            self.reloadData().done(() => {
                                self.selectFlowMenuByIndexByCode(topPageCode);
                            });
                        }).fail((res) => {
                            nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("Msg_3"));
                        });
                    }
                    else {
                        service.updateFlowMenu(flowMenu).done((data) => {
                            self.reloadData();
                            nts.uk.ui.dialog.alert("Msg_15");
                        });
                    }
//                    $("#file_upload").ntsFileUpload({stereoType:"any"}).done(function(res: Array<string>) {
//                        console.log(res);
//                    }).fail(function(err) {
//                        console.log(err);
//                    });
                    
                    $("#file_upload").ntsFileUpload({stereoType:"any"}).done(function(res) {
    console.log(res);
}).fail(function(err) {
    console.log(err);
});   
                    
//execute function when upload sucessfully for fail
var option = {
    stereoType:"flowmenu",//required
    onSuccess: function(){},//optional
    onFail: function(){}//optional
}
$("#file_upload").ntsFileUpload(option).done(function(res) {
    console.log(res);
}).fail(function(err) {
    console.log(err);
});   
                                    
            
                }
            }, 100);
        }

        //蜑企勁繝懊ち繝ｳ繧呈款縺呎凾
        deleteNewFlowMenu() {
            var self = this;
            if (self.selectedFlowMenuCD() !== null) {
                nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage("Msg_18")).ifYes(function() {
                    service.deleteFlowMenu(self.selectedFlowMenu().toppagePartID())
                    .done(() => {
                        var index = _.findIndex(self.listFlowMenu(), ['titleMenuCD', self.selectedFlowMenu().topPageCode()]);
                        index = _.min([self.listFlowMenu().length - 2, index]);
                        self.reloadData().done(() => {
                            self.selectFlowMenuByIndex(index);
                        });
                        nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("Msg_16"));
                    }).fail((res) => {                        
                        nts.uk.ui.dialog.alert(nts.uk.resource.getMessage("Msg_76"));
                    });
                });
            }
        }
        
        /** Upload File */
        uploadFile(): void {
            var self = this;
            $("#file_upload").ntsFileUpload({stereoType:"flowmenu"}).done(function(res) {
                console.log(res);
            }).fail(function(err) {
                console.log(err);
            });
            $('#file_upload').ntsError('clear');
            self.selectedFlowMenu().fileID(nts.uk.util.randomId());
        }

        /** Close Dialog */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }

        // Open ccg030 B Dialog
        open030B_Dialog() {
            nts.uk.ui.windows.setShared("flowmenu", this.selectedFlowMenu(), false);
            nts.uk.ui.windows.sub.modal("/view/ccg/030/b/index.xhtml", { title: '繝励Ξ繝薙Η繝ｼ', dialogClass: "no-close" });
        }

        /** Find Current FlowMenu by ID */
        private findFlowMenu(flowmenuCD: string): void {
            var self = this;
            var selectedFlowmenu = _.find(self.listFlowMenu(), ['topPageCode', flowmenuCD]);
            if (selectedFlowmenu !== undefined) {
                self.selectedFlowMenu(new model.FlowMenu(selectedFlowmenu.toppagePartID,
                    selectedFlowmenu.topPageCode, selectedFlowmenu.topPageName,
                    selectedFlowmenu.fileID, selectedFlowmenu.fileName, selectedFlowmenu.defClassAtr,
                    selectedFlowmenu.widthSize, selectedFlowmenu.heightSize));
            }
            else {
                self.selectedFlowMenu(new model.FlowMenu("", "", "", "", "", 0, 1, 1));
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
        private changeInitMode(isCreate: boolean) {
            var self = this;
            if (isCreate === true) {
                self.selectedFlowMenuCD(null);
                _.defer(() => { $("#inpCode").focus(); });
            }
            else {
                _.defer(() => { errors.clearAll(); });
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

//        //list  message
//        private initListMessage(): any {
//            var self = this;
//            self.listMessage.push(new ItemMessage("Msg_76", "譌｢螳壹ヵ繝ｭ繝ｼ繝｡繝九Η繝ｼ縺ｯ蜑企勁縺ｧ縺阪∪縺帙ｓ縲�"));
//            self.listMessage.push(new ItemMessage("Msg_3", "蜈･蜉帙＠縺溘さ繝ｼ繝峨�ｯ縲∵里縺ｫ逋ｻ骭ｲ縺輔ｌ縺ｦ縺�縺ｾ縺吶��"));
//            self.listMessage.push(new ItemMessage("Msg_18", "驕ｸ謚樔ｸｭ縺ｮ繝�繝ｼ繧ｿ繧貞炎髯､縺励∪縺吶°�ｼ�"));
//            self.listMessage.push(new ItemMessage("Msg_15", "逋ｻ骭ｲ縺励∪縺励◆縲�"));
//            self.listMessage.push(new ItemMessage("AL002", "繝�繝ｼ繧ｿ繧貞炎髯､縺励∪縺吶��\r\n繧医ｍ縺励＞縺ｧ縺吶°�ｼ�"));
//            self.listMessage.push(new ItemMessage("ER026", "譖ｴ譁ｰ蟇ｾ雎｡縺ｮ繝�繝ｼ繧ｿ縺悟ｭ伜惠縺励∪縺帙ｓ縲�"));
//        }

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