module nts.uk.at.view.ksm011.e {
    export module viewmodel {
        export class ScreenModel {
            //tree-grid
            itemsTree: KnockoutObservableArray<Node>;
            selectedCodeTree: KnockoutObservableArray<Node>;
            treeColumns: any;
            
            constructor() {
                let self = this;
                
                //Tree grid
                self.itemsTree = ko.observableArray([]);
                self.selectedCodeTree = ko.observableArray([]);
                
                self.treeColumns = ko.observableArray([
                    { headerText: nts.uk.resource.getText("KSM011_71"), key: "code", hidden: true },
                    { headerText: nts.uk.resource.getText("KSM011_71"), key: "name" }
                ]);
                
                self.selectedCodeTree(nts.uk.ui.windows.getShared("KSM011_A_DATA_SELECTED"));
            }
            /**
             * start page  
             */
            public startPage(): JQueryPromise<any> {
                let self = this,
                dfd = $.Deferred();
                
                service.buildTreeShiftCondition().done(function(itemsTree) {
                    self.itemsTree(itemsTree);
                    dfd.resolve(itemsTree);
                });

                dfd.resolve();
                return dfd.promise();
            }
            
            /**
             * Pass Data to A
             */
            public passData(): void {
                let self = this;
                
                if(self.selectedCodeTree().length === 0) {
                    nts.uk.ui.dialog.alertError({ messageId: "Msg_718" });
                    return;
                } else {
                    nts.uk.ui.windows.setShared("KSM011_E_DATA", self.selectedCodeTree());
                    nts.uk.ui.windows.close();
                }
            }
            
            /**
             * Close dialog
             */
            public closeDialog(): void {
                nts.uk.ui.windows.close();
            }
        }
        
        class Node {
            code: string;
            name: string;
            nodeText: string;
            position: number;
            childs: Array<Child>;
            
            constructor(code: string, name: string, childs: Array<Child>) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = self.name;
                self.position = 0;
                self.childs = childs;
            }
        }
        
        class Child {
            code: string;
            name: string;
            nodeText: string;
            position: number;
            
            constructor(code: string, name: string) {
                var self = this;
                self.code = code;
                self.name = name;
                self.nodeText = self.name;
                self.position = 1;
            }
        }
    }
}