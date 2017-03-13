module nts.uk.pr.view.qmm002.a {
    export module viewmodel {
        export class ScreenModel {
            confirmDirty: boolean = false;
            currentNode: any;
            lst_001: KnockoutObservableArray<any>;
            lst_002: KnockoutObservableArray<any>;
            filteredData: any;
            nodeParent: KnockoutObservable<BankInfo>;
            singleSelectedCode: KnockoutObservable<any>;
            selectedCodes: any;
            parentNode: KnockoutObservable<BankInfo>;

            A_INP_006: any;
            A_INP_003: any;
            A_INP_004: any;
            A_INP_005: any;
            isCreated: any;
            index: any;
            indexlast_c_node: any;
            dirty1: nts.uk.ui.DirtyChecker;
            dirty2: nts.uk.ui.DirtyChecker;
            dirty3: nts.uk.ui.DirtyChecker;
            dirty4: nts.uk.ui.DirtyChecker;

            messages: KnockoutObservable<any>;

            constructor() {
                var self = this;
                self.dirty1 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                self.dirty2 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                self.dirty3 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                self.dirty4 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                self.lst_001 = ko.observableArray([]);
                self.filteredData = ko.observableArray([]);
                self.singleSelectedCode = ko.observable('');
                self.selectedCodes = ko.observableArray([])
                self.currentNode = ko.observable();
                self.nodeParent = ko.observable(null);
                self.lst_002 = ko.observableArray([]);
                self.isCreated = ko.observable(false);
                self.index = ko.observable();
                self.indexlast_c_node = ko.observable();
                self.messages = ko.observableArray([
                    { messageId: "ER001", message: "には未入力の必須項目の名称を表記" },
                    { messageId: "ER005", message: "入力した＊は既に存在しています。\r\n＊を確認してください。" },
                    { messageId: "ER008", message: "選択された＊は使用されているため削除できません。" },
                    { messageId: "AL001", message: "変更された内容が登録されていません。\r\nよろしいですか。" },
                    { messageId: "AL002", message: "データを削除します。\r\nよろしいですか？" }
                ]);

                self.A_INP_005 = {
                    value: ko.observable(''),
                    constraint: 'BankBranchNameKana',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "",
                        width: "180px",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(),
                    readonly: ko.observable(false)
                };


                self.A_INP_006 = {
                    value: ko.observable(''),
                    constraint: 'Memo',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                        resizeable: true,
                        placeholder: "",
                        width: "400px",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };

                self.currentNode = ko.observable(''),
                    self.singleSelectedCode.subscribe(function(codeChanged) {
                        self.lst_002(nts.uk.util.flatArray(self.lst_001(), "childs"))
                        var parentCode = null;
                        var childCode = null;
                        var check = self.singleSelectedCode().includes("-");
                        if (check) {
                            var codes = self.singleSelectedCode().split("-");
                            parentCode = codes[0];
                            childCode = codes[1];
                        } else {
                            parentCode = self.singleSelectedCode();
                        }
                        var node = _.find(self.lst_002(), function(item: BankInfo) {
                            return item.treeCode == parentCode;
                        });

                        var indexParen = _.findIndex(self.lst_002(), function(item: BankInfo) {
                            return item.treeCode == parentCode;
                        });

                        self.indexlast_c_node(node.childs.length + indexParen);

                        var isParentNode = self.singleSelectedCode().includes("-");
                        if (!isParentNode) {
                            var index = _.findIndex(self.lst_001(), function(item: BankInfo) {
                                return item.treeCode == codeChanged;
                            });
                            self.index(index);

                        } else {
                            var index = _.findIndex(self.lst_002(), function(item: BankInfo) {
                                return item.treeCode == codeChanged;
                            });
                            self.index(index);
                        }
                        if (!self.checkDirty()) {
                            var x = self.getNode(codeChanged, undefined);
                            if (x.parentCode !== null) {
                                self.currentNode(x);
                                self.nodeParent(self.getNode(codeChanged, x.parentCode));
                            } else {
                                self.nodeParent(x);
                                self.currentNode(new BankInfo());
                            }
                            self.A_INP_003.value(self.currentNode().code);
                            self.A_INP_004.value(self.currentNode().name);
                            self.A_INP_005.value(self.currentNode().nameKata);
                            self.A_INP_006.value(self.currentNode().memo);
                            self.isCreated(false);
                            self.A_INP_003.enable(false);
                            if (self.currentNode().treeCode == undefined) {
                                self.dirty1 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                                self.dirty2 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                                self.dirty3 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                                self.dirty4 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                            } else {
                                self.dirty1 = new nts.uk.ui.DirtyChecker(self.A_INP_003.value);
                                self.dirty2 = new nts.uk.ui.DirtyChecker(self.A_INP_004.value);
                                self.dirty3 = new nts.uk.ui.DirtyChecker(self.A_INP_005.value);
                                self.dirty4 = new nts.uk.ui.DirtyChecker(self.A_INP_006.value);
                            }
                        } else {
                            if (self.confirmDirty) {
                                self.confirmDirty = false;
                                return;
                            }
                            nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function() {
                                var x = self.getNode(codeChanged, undefined);
                                if (x.parentCode !== null) {
                                    self.currentNode(x);
                                    self.nodeParent(self.getNode(codeChanged, x.parentCode));
                                } else {
                                    self.nodeParent(x);
                                    self.currentNode(new BankInfo());
                                }
                                self.A_INP_003.value(self.currentNode().code);
                                self.A_INP_004.value(self.currentNode().name);
                                self.A_INP_005.value(self.currentNode().nameKata);
                                self.A_INP_006.value(self.currentNode().memo);
                                self.isCreated(false);
                                self.A_INP_003.enable(false);
                                if (self.currentNode().treeCode == undefined) {
                                    self.dirty1 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                                    self.dirty2 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                                    self.dirty3 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                                    self.dirty4 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                                } else {
                                    self.dirty1 = new nts.uk.ui.DirtyChecker(self.A_INP_003.value);
                                    self.dirty2 = new nts.uk.ui.DirtyChecker(self.A_INP_004.value);
                                    self.dirty3 = new nts.uk.ui.DirtyChecker(self.A_INP_005.value);
                                    self.dirty4 = new nts.uk.ui.DirtyChecker(self.A_INP_006.value);
                                }
                            }).ifNo(function() {
                                self.confirmDirty = true;
                                self.singleSelectedCode(self.currentNode().treeCode);
                            })
                        }
                    });

                self.A_INP_003 = {
                    value: ko.observable(''),
                    constraint: 'BankBranchCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "",
                        width: "45px",
                        textalign: "left"
                    })),
                    required: ko.observable(false),
                    enable: ko.observable(false),
                    readonly: ko.observable(false)
                };

                self.A_INP_004 = {
                    value: ko.observable(''),
                    constraint: 'BankBranchName',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: "text",
                        placeholder: "",
                        width: "180px",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(true),
                    readonly: ko.observable(false)
                };
            }

            startPage() {
                var self = this;
                $.when(self.getBankList()).done(function() {
                    self.singleSelectedCode(self.lst_001()[0].treeCode);
                });
            }

            OpenBdialog(): any {
                var self = this;
                if (!self.checkDirty()) {
                    nts.uk.ui.windows.sub.modal("/view/qmm/002/b/index.xhtml", { title: "銀行の登録　＞　一括削除" }).onClosed(() => {
                        self.getBankList();
                        self.singleSelectedCode(self.lst_001()[0].code);
                    });
                    nts.uk.ui.windows.setShared('listItem', self.lst_001());
                }
                else {
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function() {
                        nts.uk.ui.windows.sub.modal("/view/qmm/002/b/index.xhtml", { title: "銀行の登録　＞　一括削除" }).onClosed(() => {
                            self.getBankList();
                            self.singleSelectedCode(self.lst_001()[0].code);
                        });
                        nts.uk.ui.windows.setShared('listItem', self.lst_001());
                    })
                }
            }

            OpenCdialog(): any {
                var self = this;
                if (!self.checkDirty()) {
                    debugger;
                    nts.uk.ui.windows.sub.modal("/view/qmm/002/c/index.xhtml", { title: "銀行の登録　＞　銀行の統合" });
                    nts.uk.ui.windows.setShared('listItem', self.lst_001());
                }
                else {
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function() {
                        nts.uk.ui.windows.sub.modal("/view/qmm/002/c/index.xhtml", { title: "銀行の登録　＞　銀行の統合" });
                        nts.uk.ui.windows.setShared('listItem', self.lst_001());
                    })
                }
            }

            OpenDdialog(): any {
                var self = this;
                if (!self.checkDirty()) {
                    nts.uk.ui.windows.sub.modal("/view/qmm/002/d/index.xhtml", { title: "銀行の登録　＞　銀行の追加" }).onClosed(() => {
                        self.getBankList();
                    });
                }
                else {
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function() {
                        nts.uk.ui.windows.sub.modal("/view/qmm/002/d/index.xhtml", { title: "銀行の登録　＞　銀行の追加" }).onClosed(() => {
                            self.getBankList();
                        });
                    })
                }
            }

            addBranch(): any {
                var self = this;
                self.confirmDirty = true;
                var branchInfo = {
                    bankCode: self.nodeParent().code,
                    branchCode: self.A_INP_003.value(),
                    branchName: self.A_INP_004.value(),
                    branchKnName: self.A_INP_005.value(),
                    memo: self.A_INP_006.value()
                };

                service.addBank(self.isCreated(), branchInfo).done(function() {
                    // reload tree
                    self.getBankList().done(function() {
                        var treecode = branchInfo.bankCode + "-" + branchInfo.branchCode;
                        self.singleSelectedCode(treecode);
                    });
                }).fail(function(error) {
                    var messageList = self.messages();
                    if (error.messageId == messageList[0].messageId) { // ER001
                        $('#A_INP_003').ntsError('set', messageList[0].message);
                        $('#A_INP_004').ntsError('set', messageList[0].message);
                    } else if (error.messageId == messageList[1].messageId) { // ER005 {
                        $('#A_INP_003').ntsError('set', messageList[1].message);
                        nts.uk.ui.dialog.alert(messageList[1].message);
                    }
                })
            }

            getNode(codeNew, parentId): BankInfo {
                var self = this;
                self.clearError();
                self.lst_002(nts.uk.util.flatArray(self.lst_001(), "childs"))
                var node = _.find(self.lst_002(), function(item: BankInfo) {
                    return item.treeCode == codeNew;
                });

                if (parentId !== undefined) {
                    node = _.find(self.lst_002(), function(item: BankInfo) {
                        return item.treeCode == node.parentCode;
                    });
                }

                return node;
            }

            checkDirty(): boolean {
                var self = this;
                if (self.dirty1.isDirty() || self.dirty2.isDirty() || self.dirty3.isDirty() || self.dirty4.isDirty()) {
                    return true;
                } else {
                    return false;
                }
            }


            getBankList(): any {
                var self = this;
                var dfd = $.Deferred();
                service.getBankList().done(function(data) {
                    var list001: Array<BankInfo> = [];
                    _.forEach(data, function(itemBank) {
                        var childs = _.map(itemBank.bankBranch, function(item) {
                            return new BankInfo(itemBank.bankCode + "-" + item["bankBranchCode"], item["bankBranchCode"], item["bankBranchName"], item["bankBranchNameKana"], item["memo"], null, itemBank.bankCode);
                        });

                        list001.push(new BankInfo(itemBank.bankCode, itemBank.bankCode, itemBank.bankName, itemBank.bankNameKana, itemBank.memo, childs, null));
                    });
                    self.lst_001(list001);
                    self.lst_002(nts.uk.util.flatArray(self.lst_001(), "childs"))
                    dfd.resolve(list001);
                }).fail(function(res) {
                    // error
                    dfd.reject(res);
                });

                return dfd.promise();
            }

            removeBranch() {
                var self = this;
                self.confirmDirty = true;
                self.clearError();
                nts.uk.ui.dialog.confirm(self.messages()[4].message).ifYes(function() {
                    var parentCode = null;
                    var childCode = null;
                    var check = self.singleSelectedCode().includes("-");
                    if (check) {
                        var codes = self.singleSelectedCode().split("-");
                        parentCode = codes[0];
                        childCode = codes[1];
                    } else {
                        parentCode = self.singleSelectedCode();
                    }

                    service.removeBank(!check, parentCode, childCode).done(function() {
                        // reload tree
                        self.cleanBranch();
                        self.getBankList().done(function() {
                            var code = "";
                            var index = self.index() - 1;
                            if (index < 0) {
                                index = 0;
                            }
                            // parent
                            if (!check) {
                                code = self.lst_001()[index].treeCode;
                            }
                            else {
                                if (self.index() == self.indexlast_c_node()) {
                                    code = self.lst_002()[index].treeCode;
                                }
                                else {
                                    code = self.lst_002()[self.index()].treeCode;
                                }
                            }
                            self.dirty1 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                            self.dirty2 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                            self.dirty3 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                            self.dirty4 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                            self.singleSelectedCode(code);
                        });
                    }).fail(function(error) {
                        var messageList = self.messages();
                        if (error.messageId == messageList[2].messageId) { // ER008
                            nts.uk.ui.dialog.alert(messageList[2].message)
                        }
                    })
                });
            };

            cleanBranch() {
                var self = this;
                if (!self.checkDirty()) {
                    self.dirty1 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                    self.dirty2 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                    self.dirty3 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                    self.dirty4 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                    self.A_INP_003.value(null);
                    self.A_INP_004.value(null);
                    self.A_INP_005.value(null);
                    self.A_INP_006.value(null);
                    self.A_INP_003.enable(true);
                    self.isCreated(true);
                }
                else {
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function() {
                        self.dirty1 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                        self.dirty2 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                        self.dirty3 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                        self.dirty4 = new nts.uk.ui.DirtyChecker(ko.observable(""));
                        self.A_INP_003.value(null);
                        self.A_INP_004.value(null);
                        self.A_INP_005.value(null);
                        self.A_INP_006.value(null);
                        self.A_INP_003.enable(true);
                        self.isCreated(true);
                    })
                }
            }

            clearError() {
                $('#A_INP_003').ntsError('clear');
                $('#A_INP_004').ntsError('clear');
            }

            jumpToQmm006A() {
                var self = this;
                if (!self.checkDirty()) {
                    nts.uk.request.jump("/view/qmm/006/a/index.xhtml");
                }
                else {
                    nts.uk.ui.dialog.confirm("変更された内容が登録されていません。\r\n よろしいですか。").ifYes(function() {
                        nts.uk.request.jump("/view/qmm/006/a/index.xhtml");
                    })
                }
            }
        }


        export class BankInfo {
            treeCode: string;
            code: string;
            name: string;
            nameKata: string;
            memo: string;
            childs: Array<BankInfo>;
            parentCode: string;

            constructor(treeCode?: string, code?: string, name?: string, nameKata?: string, memo?: string, childs?: Array<BankInfo>, parentCode?: string) {
                var self = this;
                self.treeCode = treeCode;
                self.code = code;
                self.name = name;
                self.nameKata = nameKata;
                self.memo = memo;
                self.childs = childs;
                self.parentCode = parentCode;
            }
        }
    }

}