module nts.uk.pr.view.qmm002.c.viewmodel {
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
        sourceBankBranchList: KnockoutObservableArray<any>;
        selectedSourceBranchCodes: KnockoutObservableArray<any>;
        headers: any;
        desBankBranchList: KnockoutObservableArray<any>;
        selectedDesBranchCode: KnockoutObservable<any>;
        
        constructor() {
            var self = this;
            self.headers = ko.observableArray(["Item Value Header"]);
            self.sourceBankBranchList = ko.observableArray([]);
            self.selectedSourceBranchCodes = ko.observableArray(null);
            self.desBankBranchList = ko.observableArray([]);
            self.selectedDesBranchCode = ko.observable(null);
        }
        
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.getAllBank().done((data: Array<any>) => {
                if (_.isEmpty(data)) {
                    alertError({messageId: "Msg_672"});
                } else {
                    block.invisible();
                    service.getAllBankBranch(_.map(data, b => b.code)).done((branchData: Array<any>) => {
                        if (_.isEmpty(branchData)) {
                            let displayList = _.map(data, b => {
                                return new Node(b.code, b.code, b.name, []);
                            });
                            self.sourceBankBranchList(displayList);
                            self.desBankBranchList(displayList)
                        } else {
                            let displayList = _.map(data, b => {
                                let lstBr = _.filter(branchData, br => { return br.bankCode == b.code; }).map(br => { return new Node(br.id, br.code, br.name, [])});
                                return new Node(b.code, b.code, b.name, lstBr);
                            });
                            self.sourceBankBranchList(displayList);
                            self.desBankBranchList(displayList)
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

        close() {
            nts.uk.ui.windows.close();
        }

        execute() {
            let self = this;
//            let newDS = self.dataSource();
//            newDS.push(self.selectedBank());
//            self.dataSource(newDS);
//            let newDL = self.bankList();
//            newDL.push({code: self.selectedBank().code(), name: self.selectedBank().name(), displayText: self.selectedBank().code() + " " + self.selectedBank().name()});
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
    
}
