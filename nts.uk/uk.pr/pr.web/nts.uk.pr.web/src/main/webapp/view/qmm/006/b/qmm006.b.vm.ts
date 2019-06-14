module nts.uk.pr.view.qmm006.b.viewmodel {
    import block = nts.uk.ui.block;
    import getText = nts.uk.resource.getText;
    import alertError = nts.uk.ui.dialog.alertError;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;

    export class ScreenModel {
        
        bankBranchList: KnockoutObservableArray<Node>;
        selectedCode: KnockoutObservable<string>;
        headers: any;
        
        listNodes: Array<any> = [];

        constructor() {
            let self = this;
            self.headers = ko.observableArray([getText("QMM002_11")]);
            self.bankBranchList = ko.observableArray([]);
            self.selectedCode = ko.observable("");
        }
        
        startPage(): JQueryPromise<any> {
            let self = this, dfd = $.Deferred();
            block.invisible();
            service.getAllBank().done((data: Array<any>) => {
                if (_.isEmpty(data)) {
                    dfd.resolve();
                } else {
                    block.invisible();
                    service.getAllBankBranch(_.map(data, b => b.code)).done((branchData: Array<any>) => {
                        let displayList = _.map(data, b => {
                            let lstBr = _.filter(branchData, br => { return br.bankCode == b.code; }).map(br => { 
                                let child = new Node(br.id, br.code, br.name, []);
                                self.listNodes.push(child);
                                return child;
                            });
                            let parent = new Node(b.code, b.code, b.name, lstBr);
                            self.listNodes.push(parent);
                            return parent;
                        });
                        self.bankBranchList(displayList);
                        let selected = getShared("QMM006BParam");
                        if (selected) self.selectedCode(selected);
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
        
        select() {
            let self = this;
            let selected: Node = _.find(self.listNodes, r => {return r.id == self.selectedCode()});
            let bank = _.find(self.bankBranchList(), b => {
                return b.children.length > 0 && b.children.indexOf(selected) > -1
            });
            setShared("QMM006BResult", _.isEmpty(selected) ? null : {
                branchId: selected.id, 
                bankCode: bank.code, 
                bankName: bank.name, 
                branchCode: selected.code, 
                branchName: selected.name
            });
            nts.uk.ui.windows.close();
        }

        cancel() {
            nts.uk.ui.windows.close();
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
    
}