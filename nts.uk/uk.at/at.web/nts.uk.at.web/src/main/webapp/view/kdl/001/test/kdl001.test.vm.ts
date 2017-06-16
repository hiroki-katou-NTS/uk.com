module nts.uk.at.view.kdl001.test {
    export module viewmodel {
        export class ScreenModel {
            selectedCode: KnockoutObservable<string>;
            selectedCodeList: KnockoutObservableArray<string>;
            constructor(){
                var self = this;
                self.selectedCode = ko.observable('1');
                self.selectedCodeList = ko.observableArray(['1','2']);   
            }
            singleSelect(){
                var self = this;
                nts.uk.ui.windows.setShared('multiSelectMode', false);
                nts.uk.ui.windows.setShared('selectAbleCodeList', [
                    "1"
                ]);
                nts.uk.ui.windows.setShared('selectedCode', self.selectedCode());
                nts.uk.ui.windows.sub.modal("/view/kdl/001/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                    if(nts.uk.ui.windows.getShared("selectedCodeList")!=null){
                        self.selectedCode(nts.uk.ui.windows.getShared("selectedCodeList")[0]);    
                    }
                });        
            }
            
            multiSelect(){
                var self = this;
                nts.uk.ui.windows.setShared('multiSelectMode', true);
                nts.uk.ui.windows.setShared('selectAbleCodeList', [
                    "1","2"
                ]);
                nts.uk.ui.windows.setShared('selectedCodeList', self.selectedCodeList());
                nts.uk.ui.windows.sub.modal("/view/kdl/001/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                    if(nts.uk.ui.windows.getShared("selectedCodeList")!=null){
                        self.selectedCodeList(nts.uk.ui.windows.getShared("selectedCodeList")); 
                    }    
                });        
            }
        }
    }
}