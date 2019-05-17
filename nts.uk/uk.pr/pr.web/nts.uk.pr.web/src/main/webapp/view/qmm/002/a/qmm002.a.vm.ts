module nts.uk.pr.view.qmm002.a.viewmodel {
    
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
        bankBranchList: KnockoutObservableArray<Node>;
        selectedCode: KnockoutObservable<string>;
        headers: any;
        totalBranches: KnockoutObservable<number> = ko.observable(0);
        totalBranchesDisplay: KnockoutObservable<string> = ko.observable(getText("QMM002_12", [0]));
        
        listBank: KnockoutObservableArray<Bank> = ko.observableArray([]);
        listBranch: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedBankBranch: KnockoutObservable<BankBranch> = ko.observable(new BankBranch("", "", "", "", "", ""));
        selectedBank: KnockoutObservable<Bank> = ko.observable(new Bank("", "", "", ""));
        updateMode: KnockoutObservable<boolean> = ko.observable(false);
        
        constructor() {
            var self = this;
            self.headers = ko.observableArray([getText("QMM002_11")]);
            self.bankBranchList = ko.observableArray([]);
            self.selectedCode = ko.observable("");
            self.selectedCode.subscribe((val: string) => {
                if (_.isEmpty(val)) {
                    self.selectedBankBranch(new BankBranch("", self.selectedBank().code(), "", "", "", ""));
                    self.updateMode(false);
                    $("#A3_9").focus();
                } else {
                    self.updateMode(true);
                    if (val.length > 4) { // bank branch selected
                        block.invisible();
                        service.getBankBranch(val).done(data =>{
                            let selectedBank = _.find(self.listBank(), b => {return b.code() == data.bankCode;});
                            self.selectedBank(selectedBank);
                            self.selectedBankBranch(new BankBranch(data.id, data.bankCode, data.code, data.name, data.kanaName, data.memo));
                            $("#A3_10").focus();
                        }).fail(error => {
                            alertError(error);
                        }).always(() => {
                            block.clear();
                        });
                    } else { // bank selected
                        let selectedBank = _.find(self.listBank(), b => {return b.code() == val;});
                        self.selectedBank(selectedBank);
                        self.selectedBankBranch(new BankBranch("", self.selectedBank().code(), "", "", "", ""));
                        if (self.listBranch().length == 0) {
                            self.updateMode(false);
                            $("#A3_9").focus();
                        }
                    }
                }
                _.defer(() => {
                    nts.uk.ui.errors.clearAll();
                });
            });
            self.totalBranches.subscribe(val => {
                self.totalBranchesDisplay(getText("QMM002_12", [val]));
            });
        }

        exportFile(){
            let self = this;
            nts.uk.ui.block.grayout();
            service.exportFile().done(()=>{

            }).fail((err)=>{
                if (err)
                    nts.uk.ui.dialog.alertError(err);
            }).always(()=>{
                nts.uk.ui.block.clear();
            });
        }
        
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            self.bankBranchList([]);
            self.listBank([]);
            self.selectedBank(new Bank("", "", "", ""));
            service.getAllBank().done((data: Array<any>) => {
                if (_.isEmpty(data)) {
                    dfd.resolve();
                } else {
                    block.invisible();
                    self.listBank(_.map(data, b => new Bank(b.code, b.name, b.kanaName, b.memo)));
                    self.getAllBranch(data).done(() => {
                        dfd.resolve();
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

        createNew() {
            let self = this;
            if (self.selectedCode() == self.selectedBank().code())
                self.selectedCode.valueHasMutated();
            else
                self.selectedCode(self.selectedBank().code());
            self.updateMode(false);
            $("#A3_9").focus();
        }

        register() {
            let self = this;
            if (self.updateMode() && self.selectedCode().length < 5)
                return;
            $(".nts-input").trigger("validate");
            if (!nts.uk.ui.errors.hasError()) {
                block.invisible();
                let command = ko.toJS(self.selectedBankBranch());
                service.registerBranch(command).done(data => {
                    self.getAllBranch(ko.toJS(self.listBank())).done(() => {
                        info({ messageId: "Msg_15" }).then(() => {
                            self.setSelectedCode(data);
                        });
                    });
                }).fail(error => {
                    alertError(error);
                }).always(() => {
                    block.clear();
                });
            }
        }

        deleteBranch() {
            let self = this;
            block.invisible();
            let nextSelectNodeId = self.getIdToSelectAfterDelete();
            service.checkBeforeDeleteBranch(self.selectedCode()).done(() => {
                confirm({ messageId: "Msg_18" }).ifYes(() => {
                    service.deleteBranch(self.selectedCode()).done(() => {
                        self.startPage().done(() => {
                            info({ messageId: "Msg_16" }).then(() => {
                                self.setSelectedCode(nextSelectNodeId);
                                if (self.listBank().length == 0) {
                                    self.openDialogQmm002d();
                                }
                            });
                        });
                    }).fail(error => {
                        alertError(error);
                    }).always(() => {
                        block.clear();
                    });
                }).ifNo(() => {
                });
            }).fail(error => {
                alertError(error);
            }).always(() => {
                block.clear();
            });
        }
        
        openDialogQmm002b() {
            let self = this;
            modal("/view/qmm/002/b/index.xhtml").onClosed(() => {
                let isCancel = getShared("QMM002BCancel");
                if (_.isEmpty(isCancel)) {
                    self.startPage().done(() => {
                        if (self.listBank().length == 0) {
                            self.openDialogQmm002d();
                        } else {
                            if (self.listBranch().length > 0)
                                self.setSelectedCode(self.listBranch()[0].id);
                            else
                                self.setSelectedCode(self.listBank()[0].code());
                        }
                    });
                }
            });
        }
        
        openDialogQmm002c() {
            let self = this;
            modal("/view/qmm/002/c/index.xhtml").onClosed(() => {
                self.selectedCode.valueHasMutated();
            });
        }
        
        openDialogQmm002d() {
            let self = this;
            modal("/view/qmm/002/d/index.xhtml").onClosed(() => {
                block.invisible();
                service.getAllBank().done((data: Array<any>) => {
                    if (_.isEmpty(data)) {
                        nts.uk.request.jumpToTopPage();
                    } else {
                        self.listBank(_.map(data, b => new Bank(b.code, b.name, b.kanaName, b.memo)));
                        self.getAllBranch(data).done(() => {
                            if (self.listBranch().length > 0)
                                self.setSelectedCode(self.listBranch()[0].id);
                            else
                                self.setSelectedCode(self.listBank()[0].code());
                        });
                    }
                }).fail(error => {
                    alertError(error);
                }).always(() => {
                    block.clear();
                });
            });
        }
        
        getAllBranch(data: Array<any>): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.getAllBankBranch(_.map(data, b => b.code)).done((branchData: Array<any>) => {
                self.listBranch(branchData);
                self.totalBranches(branchData.length);
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
            return dfd.promise();
        }
        
        getIdToSelectAfterDelete(): string {
            let self = this, nextSelectNodeId = "";
            let listNode = _.cloneDeep(self.bankBranchList());
            listNode.forEach(n => {
                if (!_.isEmpty(n.children)) {
                    n.children.forEach(c => {
                        listNode.push(c);
                    })
                }
            })
            let selectedNode: Node = _.find(listNode, n => {return n.id == self.selectedCode();});
            if (selectedNode.id != selectedNode.code) {
                // branch
                let currBankNode: Node = _.find(listNode, n => {return n.id == self.selectedBank().code();});
                if (currBankNode.children.length == 1) {
                    nextSelectNodeId = currBankNode.id;
                } else {
                    let currBranchIndex = _.findIndex(currBankNode.children, b => { return b.id == selectedNode.id; });
                    if (currBranchIndex == currBankNode.children.length - 1) { // last
                        nextSelectNodeId = currBankNode.children[currBranchIndex - 1].id;
                    } else {
                        nextSelectNodeId = currBankNode.children[currBranchIndex + 1].id;
                    }
                }
            } else {
                // bank
                if (self.listBank().length > 1) {
                    let currBankIndex = _.findIndex(self.listBank(), b => { return b.code() == self.selectedBank().code()});
                    if (currBankIndex == self.listBank().length - 1) { // last
                        nextSelectNodeId = self.listBank()[currBankIndex - 1].code();
                    } else {
                        nextSelectNodeId = self.listBank()[currBankIndex + 1].code();
                    }
                }
            }
            return nextSelectNodeId;
        }
        
        setSelectedCode(value: string) {
            let self = this;
            if (self.selectedCode() == value)
                self.selectedCode.valueHasMutated();
            else
                self.selectedCode(value);
        }

    }

    class Node {
        id: string;
        code: string;
        name: string;
        nodeText: string;
        children: Array<Node>;
        constructor(id: string, code: string, name: string, children: Array<Node>) {
            var self = this;
            self.id = id;
            self.code = code;
            self.name = name;
            self.nodeText = _.escape(self.code + ' ' + self.name);
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


