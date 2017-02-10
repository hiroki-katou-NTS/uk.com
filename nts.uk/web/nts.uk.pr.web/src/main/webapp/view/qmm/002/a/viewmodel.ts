module nts.uk.pr.view.qmm002_1.a {
    export module viewmodel {

        export class ScreenModel {

            currentEra: any;
            lst_001: any;
            lst_002: any;
            filteredData: any;
            nodeParent: KnockoutObservable<BankInfo>;
            singleSelectedCode: any;
            selectedCodes: any;
            parentNode: KnockoutObservable<BankInfo>;
            A_INP_002: any;
            A_INP_006: any;
            A_INP_003: any;
            A_INP_004: any;
            A_INP_005: any;
            isCreated: any;


            constructor() {
                var self = this;
                self.lst_001 = ko.observableArray([]);
                self.filteredData = ko.observableArray([]);
                self.singleSelectedCode = ko.observable(null);
                self.selectedCodes = ko.observableArray([])
                self.currentEra = ko.observable();
                self.nodeParent = ko.observable(null);
                self.lst_002 = ko.observableArray([]);
                self.isCreated = ko.observable(false);


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

                self.A_INP_002 = {
                    value: ko.observable(''),
                    constraint: 'ResidenceCode',
                    option: ko.mapping.fromJS(new nts.uk.ui.option.MultilineEditorOption({
                        resizeable: true,
                        placeholder: "",
                        width: "250px",
                        textalign: "left"
                    })),
                    required: ko.observable(true),
                    enable: ko.observable(true),
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

                self.currentEra = ko.observable(''),
                    self.singleSelectedCode.subscribe(function(codeChanged) {
                        var x = self.getNode(codeChanged, undefined);
                        if (x.parentCode !== null) {
                            self.currentEra(x);
                            self.nodeParent(self.getNode(codeChanged, x.parentCode));
                        } else {
                            self.nodeParent(x);
                            self.currentEra(new BankInfo());
                        }

                        self.A_INP_003.value(self.currentEra().code);
                        self.A_INP_004.value(self.currentEra().name);
                        self.A_INP_005.value(self.currentEra().nameKata);
                        self.A_INP_006.value(self.currentEra().memo);
                        self.A_INP_003.enable(false);
                        self.isCreated(false);
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
                    self.singleSelectedCode(self.lst_001()[0].code);
                });
            }

            OpenBdialog(): any {
                var self = this;
                nts.uk.ui.windows.sub.modal("/view/qmm/002/b/index.xhtml", { title: "銀行の登録　＞　一括削除" });
                nts.uk.ui.windows.setShared('listItem', self.lst_001());
            }

            OpenCdialog(): any {
                var self = this;
                nts.uk.ui.windows.sub.modal("/view/qmm/002/c/index.xhtml", { title: "銀行の登録　＞　銀行の統合" });
                nts.uk.ui.windows.setShared('listItem', self.lst_001());
            }

            OpenDdialog(): any {
                var self = this;
                nts.uk.ui.windows.sub.modal("/view/qmm/002/d/index.xhtml", { title: "銀行の登録　＞　銀行の追加" }).onClosed(() => {
                    self.getBankList();
                });
            }

            addBranch(): any {
                var self = this;
                var branchInfo = {
                    bankCode: self.nodeParent().code,
                    branchCode: self.A_INP_003.value(),
                    branchName: self.A_INP_004.value(),
                    branchKnName: self.A_INP_005.value(),
                    memo: self.A_INP_006.value()
                };

                service.addBank(self.isCreated(), branchInfo).done(function() {
                    // reload tree
                    self.getBankList();
                }).fail(function(error) {
                    alert(error.message);
                })
            }

            getNode(codeNew, parentId): BankInfo {
                var self = this;
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

            //            deleteItem() {
            //                var self = this;
            //                debugger
            //                self.lst_001.remove(self.currentEra());
            //                var i = self.nodeParent().childs.indexOf(self.currentEra());
            //                if (i != -1) {
            //                    self.nodeParent().childs.splice(i, 1);
            //                    var tempNodeParent = self.nodeParent();
            //                    var tempLst001 = self.lst_001();
            //                    self.nodeParent(tempNodeParent);
            //        self.lst_001(tempLst001)
            //                }
            //                console.log(self.lst_001());
            //            }

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
                    dfd.resolve(list001);
                }).fail(function(res) {
                    // error
                });

                return dfd.promise();
            }

            removeBranch() {
                var self = this;
                service.removeBranch(self.nodeParent().code, self.A_INP_003.value()).done(function() {
                    // reload tree
                    self.getBankList();
                    self.cleanBranch();
                }).fail(function(error) {
                    alert(error.message);
                })
            };

            cleanBranch() {
                var self = this;
                self.A_INP_003.value('');
                self.A_INP_004.value('');
                self.A_INP_005.value('');
                self.A_INP_006.value('');
                self.A_INP_003.enable(true);
                self.isCreated(true);
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

            constructor(treeCode?: string,  code?: string, name?: string, nameKata?: string, memo?: string, childs?: Array<BankInfo>, parentCode?: string) {
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

        export class Node {
            code: string;
            name: string;
            nodeText: string;
            childs: any;
            constructor(code: string, name: string, childs: Array<Node>) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = self.code + ' ' + self.name;
                self.childs = childs;
            }
        }
    }

}