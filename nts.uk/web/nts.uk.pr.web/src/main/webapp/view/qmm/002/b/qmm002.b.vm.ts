module nts.uk.pr.view.qmm002.b {
    export module viewmodel {
        export class ScreenModel {
            lst_001: any;
            selectedCodes: any;

            constructor() {
                var self = this;
                self.lst_001 = ko.observableArray([]);
                self.selectedCodes = ko.observableArray([]);
                self.selectedCodes.subscribe(function(val) {
                    console.log(val);
                });
            }

            startPage() {
                var self = this;
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
                    var branchCode = code[1];
                    keyBank.push({
                        bankCode: bankCode,
                        branchCode: branchCode
                    });
                });
                
                var data =
                {
                    bank: keyBank,
                };
                service.removeBank(data).done(function(){
                    self.close();
                });
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