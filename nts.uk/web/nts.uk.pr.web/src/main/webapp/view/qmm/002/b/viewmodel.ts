module nts.uk.pr.view.qmm002.b {
    export module viewmodel {
         export class ScreenModel {
            lst_001: any;
            selectedCodes: any;
            
            constructor() {
                var self = this;
                self.lst_001 = ko.observableArray([]);
                self.selectedCodes = ko.observableArray([]);
                self.selectedCodes.subscribe(function(val){
                    console.log(val);
                });
                console.log(nts.uk.ui.windows.getShared('listItem'));
            }
            
            startPage() {
                var self = this;
                
                service.getBankList().done(function(result) {
                    self.lst_001(result);
                });
            }
             
            
            excute() {
                var self = this;
                nts.uk.ui.windows.setShared("data", self.selectedCodes());
                nts.uk.ui.windows.close();
            }
             
            close() {
                var self = this;
                nts.uk.ui.windows.close();    
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