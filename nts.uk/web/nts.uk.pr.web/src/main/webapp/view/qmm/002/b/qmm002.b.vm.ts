module nts.uk.pr.view.qmm002.b {
    export module viewmodel {
        export class ScreenModel {
            lst_001: any;
            lst_002: any;
            selectedCodes: any;

            constructor() {
                var self = this;
                self.lst_001 = ko.observableArray([]);
                self.lst_002 = ko.observableArray([]);
                self.selectedCodes = ko.observableArray([]);
                self.selectedCodes.subscribe(function(val) {

                });
            }

            startPage() {
                var self = this;
                debugger;
                var list = nts.uk.ui.windows.getShared('listItem');
                self.lst_001(list);
            }

            close() {
                var self = this;
                nts.uk.ui.windows.close();
            }

            btn_001() {
                var self = this;        
                var keyBank = [];
                _.forEach(self.selectedCodes(), function(item) {
                    var code = item.split('-');
                    var bankCode = code[0];
                    var branchId = self.getNode(item, bankCode);
                    keyBank.push({
                        bankCode: bankCode,
                        branchId: branchId
                    });
                });

                var data =
                    {
                        bank: keyBank,
                    };
                service.removeBank(data).done(function() {
                    self.close();
                });
            }

            getNode(codeNew, parentId): String {
                var self = this;
                debugger;
                self.lst_002(nts.uk.util.flatArray(self.lst_001(), "childs"))
                var node = _.find(self.lst_002(), function(item: BankInfo) {
                    return item.treeCode == codeNew;
                });

//                if (parentId !== undefined) {
//                    node = _.find(self.lst_002(), function(item: BankInfo) {
//                        return item.treeCode == node.parentCode;
//                    });
//                }

                return node.branchId;
            }
        }

        export class BankInfo {
            treeCode: string;
            code: string;
            branchId: string;
            name: string;
            displayName: string;
            nameKata: string;
            memo: string;
            childs: Array<BankInfo>;
            parentCode: string;

            constructor(treeCode?: string, code?: string, branchId?: string, name?: string, nameKata?: string, memo?: string, childs?: Array<BankInfo>, parentCode?: string) {
                var self = this;
                self.treeCode = treeCode;
                self.code = code;
                self.branchId = branchId;
                self.name = name;
                self.displayName = self.code + "  " + self.name;
                self.nameKata = nameKata;
                self.memo = memo;
                self.childs = childs;
                self.parentCode = parentCode;
            }
        }
    }
}