module nts.uk.pr.view.qmm002_1.a {
    export module viewmodel {
        
        export class ScreenModel {
            lst_001: any;
            filteredData: any;
            singleSelectedCode: any;
            selectedCodes: any;
            
            constructor() {
                var self = this;
                self.lst_001 = ko.observableArray([]);
                self.filteredData = ko.observableArray([]);
                self.singleSelectedCode = ko.observable(null);
                self.selectedCodes = ko.observableArray([])
            }
            
            startPage() {
                var self = this;
                
                service.getBankList().done(function(result) {
                    self.lst_001(result);
                    self.filteredData(nts.uk.util.flatArray(result,"childs"))
                });
            }
        }
        
        export class BankInfo {
            code: string;
            name: string;
            nameKata: string;
            memo: string;
            
            constructor(code: string, name: string, nameKata: string, memo: string) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nameKata = nameKata;
                self.memo = memo;
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