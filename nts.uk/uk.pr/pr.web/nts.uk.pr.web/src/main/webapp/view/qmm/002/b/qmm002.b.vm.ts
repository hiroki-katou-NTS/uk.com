module nts.uk.pr.view.qmm002.b.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        // tree grid variables
        bankBranchList: KnockoutObservableArray<any>;
        selectedBranchCodes: KnockoutObservableArray<any>;
        headers: any;
        constructor() {
            var self = this;
            self.headers = ko.observableArray([getText("QMM002_11")]);
            self.bankBranchList = ko.observableArray([]);
            self.selectedBranchCodes = ko.observableArray([]);
        }
        
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.getAllBank().done((data: Array<any>) => {
                if (_.isEmpty(data)) {
                    alertError({messageId: "Msg_672"});
                } else {
                    block.invisible();
                    self.bankBranchList(_.map(data, b => new Bank(b.code, b.name, b.kanaName, b.memo)));
                    service.getAllBankBranch(_.map(data, b => b.code)).done((branchData: Array<any>) => {
                        if (_.isEmpty(branchData)) {
                            let displayList = _.map(data, b => {
                                return new Node(b.code, b.code, b.name, []);
                            });
                            self.bankBranchList(displayList);
                        } else {
                            let displayList = _.map(data, b => {
                                let lstBr = _.filter(branchData, br => { return br.bankCode == b.code; }).map(br => { return new Node(br.id, br.code, br.name, [])});
                                return new Node(b.code, b.code, b.name, lstBr);
                            });
                            self.bankBranchList(displayList);
                        }
                        dfd.resolve();
                    }).fail(error => {
                        alertError(error);
                        dfd.reject();
                    }).always(() => {
                        block.clear();
                    });
                }
            }).fail(error => {
                alertError(error);
                dfd.reject();
            }).always(() => {
                block.clear();
            });
            return dfd.promise();
        }

        cancel() {
            nts.uk.ui.windows.close();
        }

        deleteList() {
            let self = this;
            confirm({ messageId: "Msg_18" }).ifYes(() => {
                block.invisible();
                service.deleteListBranch(self.selectedBranchCodes()).done(() => {
                    info({ messageId: "Msg_16" }).then(() => {
                        nts.uk.ui.windows.close();
                    });
                }).fail(error => {
                    alertError(error);
                }).always(() => {
                    block.clear();
                });
            }).ifNo(() => {
            });
        }
    }
    
    class Node {
        id: string;
        code: string;
        name: string;
        nodeText: string;
        children: any;
        constructor(id: string, code: string, name: string, children: Array<Node>) {
            var self = this;
            self.id = id;
            self.code = code;
            self.name = name;
            self.nodeText = self.code + ' ' + self.name;
            self.children = children;
        }
    }
    
    class Bank {
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        kanaName: KnockoutObservable<string>;
        memo: KnockoutObservable<string>;
        constructor(code: string, name: string, kana: string, memo: string) {
            this.code = ko.observable(code);
            this.name = ko.observable(name);
            this.kanaName = ko.observable(kana);
            this.memo = ko.observable(memo);
        }
    }
    
    class BankBranch {
        id: KnockoutObservable<string>;
        bankCode: KnockoutObservable<string>;
        code: KnockoutObservable<string>;
        name: KnockoutObservable<string>;
        kanaName: KnockoutObservable<string>;
        memo: KnockoutObservable<string>;
        constructor(id: string, bankCode: string, code: string, name: string, kana: string, memo: string) {
            this.id = ko.observable(id);
            this.bankCode = ko.observable(bankCode);
            this.code = ko.observable(code);
            this.name = ko.observable(name);
            this.kanaName = ko.observable(kana);
            this.memo = ko.observable(memo);
        }
    }
        
}
