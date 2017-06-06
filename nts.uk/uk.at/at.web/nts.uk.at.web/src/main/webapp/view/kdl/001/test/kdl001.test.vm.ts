module nts.uk.at.view.kdl001.test {
    export module viewmodel {
        export class ScreenModel {
            selectedCode: KnockoutObservable<string>;
            selectedCodeList: KnockoutObservableArray<string>;
            constructor(){
                var self = this;
                self.selectedCode = ko.observable('K28');
                self.selectedCodeList = ko.observableArray(['K28']);   
            }
            singleSelect(){
                var self = this;
                nts.uk.ui.windows.setShared('multiSelectMode', false);
                nts.uk.ui.windows.setShared('selectAbleCodeList', [
                    "00A","00B","00C","K28"
                ]);
                nts.uk.ui.windows.setShared('selectedCode', self.selectedCode());
                nts.uk.ui.windows.sub.modal("/view/kdl/001/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                    self.selectedCode(nts.uk.ui.windows.getShared("selectedCode"));    
                });        
            }
            
            multiSelect(){
                var self = this;
                nts.uk.ui.windows.setShared('multiSelectMode', true);
                nts.uk.ui.windows.setShared('selectAbleCodeList', [
                    "00A","00B","00C","K28"
                ]);
                nts.uk.ui.windows.setShared('selectedCodeList', self.selectedCodeList());
                nts.uk.ui.windows.sub.modal("/view/kdl/001/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                   self.selectedCodeList(nts.uk.ui.windows.getShared("selectedCodeList"));     
                });        
            }
        }
    }
}