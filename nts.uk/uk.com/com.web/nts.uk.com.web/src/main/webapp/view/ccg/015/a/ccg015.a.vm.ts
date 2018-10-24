module nts.uk.com.view.ccg015.a {
    export module viewmodel {
        import commonModel = ccg.model;
        import TopPageItemDto = ccg015.a.service.model.TopPageItemDto;
        import TopPageDto = ccg015.a.service.model.TopPageDto;
        import block = nts.uk.ui.block;
        export class ScreenModel {
            listTopPage: KnockoutObservableArray<Node>;
            toppageSelectedCode: KnockoutObservable<string>;
            topPageModel: KnockoutObservable<TopPageModel>;
            columns: KnockoutObservable<any>;
            isNewMode: KnockoutObservable<boolean>;
            languageListOption: KnockoutObservableArray<ItemCbbModel>;
            languageSelectedCode: KnockoutObservable<string>;
            listLinkScreen: KnockoutObservableArray<any>;

            isProcess: KnockoutObservable<boolean>;
            breakNewMode: boolean;
            constructor() {
                var self = this;
                var linkIconImage = "data:image/png;base64,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";
                self.listTopPage = ko.observableArray<Node>([]);
                self.toppageSelectedCode = ko.observable(null);
                self.topPageModel = ko.observable(new TopPageModel());
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("CCG015_11"), width: "50px", key: 'code', dataType: "string", hidden: false },
                    { headerText: nts.uk.resource.getText("CCG015_12"), width: "260px", key: 'nodeText', dataType: "string", formatter: _.escape }
                ]);
                self.isNewMode = ko.observable(true);
                self.toppageSelectedCode.subscribe(function(selectedTopPageCode: string) {
                    if (nts.uk.text.isNullOrEmpty(selectedTopPageCode)) {
                        self.isNewMode(true);
                        self.newTopPage();
                    }
                    else {
                        service.loadDetailTopPage(selectedTopPageCode).done(function(data: TopPageDto) {
                            self.loadTopPageItemDetail(data);
                            $('.save-error').ntsError('clear');
                        });
                        self.isNewMode(false);
                        self.breakNewMode = false;
                        $("#inp_name").focus();
                    }
                });
                self.languageListOption = ko.observableArray([
                    new ItemCbbModel("0", "日本語"),
                    new ItemCbbModel("1", "英語"),
                    new ItemCbbModel("2", "ベトナム語")
                ]);
                self.languageSelectedCode = ko.observable("0");
                self.listLinkScreen = ko.observableArray([
                    { icon: linkIconImage, text: nts.uk.resource.getText("CCG015_17"), action: function(evt, ui) { nts.uk.request.jump("/view/ccg/011/a/index.xhtml"); } },
                    { icon: linkIconImage, text: nts.uk.resource.getText("CCG015_18"), action: function(evt, ui) { nts.uk.request.jump("/view/ccg/014/a/index.xhtml"); } },
                    { icon: linkIconImage, text: nts.uk.resource.getText("CCG015_19"), action: function(evt, ui) { nts.uk.request.jump("/view/ccg/018/a/index.xhtml"); } },
                ]);

                self.isProcess = ko.observable(false);
                self.breakNewMode = false;
                //end constructor

                $("#preview-iframe").on("load", function() {
                    if (self.isNewMode() == true)
                        $("#inp_code").focus();
                    else
                        $("#inp_name").focus();
                });

            }

            start(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                self.loadTopPageList().done(function() {
                    dfd.resolve();
                });
                return dfd.promise();
            }

            private loadTopPageList(): JQueryPromise<void> {
                var self = this;
                var dfd = $.Deferred<void>();
                self.listTopPage([]);
                service.loadTopPage().done(function(data: Array<TopPageItemDto>) {
                    //if data # empty
                    if (data.length > 0) {
                        data.forEach(function(item, index) {
                            self.listTopPage.push(new Node(item.topPageCode, item.topPageName, null));
                        });
                        if (self.listTopPage().length > 0) {
                            //focus first item
                            self.toppageSelectedCode(self.listTopPage()[0].code);
                        }
                    }
                    else {
                        self.topPageModel(new TopPageModel());
                        self.isNewMode(true);
                        $("#inp_code").focus();
                        self.changePreviewIframe(null);
                    }
                    dfd.resolve();
                });
                return dfd.promise();
            }

            //load top page Item 
            private loadTopPageItemDetail(data: TopPageDto) {
                var self = this;
                self.topPageModel().topPageCode(data.topPageCode);
                self.topPageModel().topPageName(data.topPageName);
                self.topPageModel().layoutId(data.layoutId);
                self.changePreviewIframe(data.layoutId);
            }

            private collectData(): TopPageDto {
                var self = this;
                //mock data
                var data: TopPageDto = { topPageCode: self.topPageModel().topPageCode(), topPageName: self.topPageModel().topPageName(), languageNumber: 0, layoutId: self.topPageModel().layoutId() };
                return data;
            }

            private saveTopPage() {
                var self = this;
                $('.nts-input').ntsEditor('validate');
                if (!$('.nts-input').ntsError('hasError')) {
                    //check update or create
                    self.isProcess(true);
                    block.invisible();
                    if (self.isNewMode()) {
                        service.registerTopPage(self.collectData()).done(function() {
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                                self.isProcess(false);
                            });
                            self.loadTopPageList().done(function() {
                                self.toppageSelectedCode(self.collectData().topPageCode);
                            });
                        }).fail(function(res) {
                            nts.uk.ui.dialog.alertError({ messageId: res.messageId, messageParams: res.parameterIds }).then(() => {
                                self.isProcess(false);
                            });
                        }).always(()=>{
                            block.clear();
                        });
                    }
                    else {
                        service.updateTopPage(self.collectData()).done(function() {
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                                self.isProcess(false);
                            });
                            self.loadTopPageList().done(function() {
                                self.toppageSelectedCode(self.collectData().topPageCode);
                            });
                        }).always(function(){
                            block.clear();    
                        });
                    }
                }
            }

            private openMyPageSettingDialog() {
                nts.uk.ui.windows.sub.modal("/view/ccg/015/b/index.xhtml");
            }

            private copyTopPage() {
                var self = this;
                nts.uk.ui.windows.setShared('topPageCode', self.topPageModel().topPageCode());
                nts.uk.ui.windows.setShared('topPageName', self.topPageModel().topPageName());
                nts.uk.ui.windows.setShared('layoutId', self.topPageModel().layoutId());
                nts.uk.ui.windows.sub.modal("/view/ccg/015/c/index.xhtml").onClosed(() => {
                    var codeOfNewTopPage = nts.uk.ui.windows.getShared("codeOfNewTopPage");
                    self.loadTopPageList().done(() => {
                        self.toppageSelectedCode(codeOfNewTopPage);
                    });
                });

            }
            private openFlowMenuSettingDialog() {
                var self = this;
                nts.uk.ui.windows.setShared('topPageCode', self.topPageModel().topPageCode());
                nts.uk.ui.windows.setShared('topPageName', self.topPageModel().topPageName());
                nts.uk.ui.windows.sub.modal("/view/ccg/030/a/index.xhtml");
            }

            private openLayoutSettingDialog() {
                var self = this;
                var layoutId = self.topPageModel().layoutId();
                var topPageCode = self.topPageModel().topPageCode();
                var transferData: commonModel.TransferLayoutInfo = { parentCode: topPageCode, layoutID: layoutId, pgType: 0 };
                nts.uk.ui.windows.setShared('layout', transferData);
                nts.uk.ui.windows.sub.modal("/view/ccg/031/a/index.xhtml").onClosed(() => {
                    let returnInfo: commonModel.TransferLayoutInfo = nts.uk.ui.windows.getShared("layout");
                    self.topPageModel().layoutId(returnInfo.layoutID);
                    self.changePreviewIframe(returnInfo.layoutID);
                });
            }

            private newTopPage() {
                var self = this;
                self.topPageModel(new TopPageModel());
                self.isNewMode(true);
                self.breakNewMode = true;
                self.toppageSelectedCode("");
                if (nts.uk.ui.errors.hasError()) {
                    nts.uk.ui.errors.clearAll();
                }
                //wait clear error
                self.changePreviewIframe(null);
                $("#preview-iframe").trigger("load");
            }

            private removeTopPage() {
                var self = this;
                nts.uk.ui.dialog.confirm(nts.uk.resource.getMessage("Msg_18")).ifYes(function() {
                    var removeCode = self.toppageSelectedCode();
                    var removeIndex = self.getIndexOfRemoveItem(removeCode);
                    var listLength = self.listTopPage().length;
                    service.deleteTopPage(self.toppageSelectedCode()).done(function() {
                        //delete success
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function() {
                            //remove follow
                            self.loadTopPageList().done(function() {
                                var lst = self.listTopPage();
                                if (lst.length > 0) {
                                    if (removeIndex < listLength - 1) {
                                        self.toppageSelectedCode(lst[removeIndex].code);
                                    }
                                    else {
                                        self.toppageSelectedCode(lst[removeIndex - 1].code);
                                    }
                                }
                            });
                        });
                    }).fail();
                }).ifNo(function() {
                });
            }

            //for frame review layout
            private changePreviewIframe(layoutID: string): void {
                $("#preview-iframe").attr("src", "/nts.uk.com.web/view/ccg/common/previewWidget/index.xhtml?layoutid=" + layoutID);
            }

            private getIndexOfRemoveItem(code: string): number {
                var self = this;
                var ind = 0;
                self.listTopPage().forEach(function(item, index) {
                    if (item.code == code)
                        ind = index;
                });
                return ind;
            }
        }

        export class Node {
            code: string;
            name: string;
            nodeText: string;
            custom: string;
            childs: Array<Node>;
            constructor(code: string, name: string, childs: Array<Node>) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = name;
                self.childs = childs;
                self.custom = 'Random' + new Date().getTime();
            }
        }

        export class TopPageModel {
            topPageCode: KnockoutObservable<string>;
            topPageName: KnockoutObservable<string>;
            placement: KnockoutObservableArray<PlacementModel>;
            layoutId: KnockoutObservable<string>;
            constructor() {
                this.topPageCode = ko.observable('');
                this.topPageName = ko.observable('');
                this.placement = ko.observableArray([]);
                this.layoutId = ko.observable('');
            }
        }

        export class PlacementModel {
            row: KnockoutObservable<number>;
            column: KnockoutObservable<number>;
            topPagePart: KnockoutObservable<TopPagePartModel>;
            constructor() {
                this.row = ko.observable(0);
                this.column = ko.observable(0);
                this.topPagePart = ko.observable(new TopPagePartModel());
            }
        }

        export class TopPagePartModel {
            topPagePartType: KnockoutObservable<number>;
            topPagePartCode: KnockoutObservable<string>;
            topPagePartName: KnockoutObservable<string>;
            width: KnockoutObservable<number>;
            height: KnockoutObservable<number>;
            constructor() {
                this.topPagePartType = ko.observable(0);
                this.topPagePartCode = ko.observable("");
                this.topPagePartName = ko.observable("");
                this.width = ko.observable(0);
                this.height = ko.observable(0);
            }
        }

        export class ItemCbbModel {
            code: string;
            name: string;
            label: string;
            constructor(code: string, name: string) {
                this.code = code;
                this.name = name;
            }
        }

    }
}