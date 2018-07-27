module nts.uk.at.view.kdl001.test {
    export module viewmodel {
        export class ScreenModel {
            selectedCodeList: string;
            selectedCodeAbleList: string;
            isSelection: KnockoutObservable<boolean> = ko.observable(false);
            constructor(){
                var self = this;
                self.selectedCodeList = ko.observableArray('');   
                self.selectedCodeAbleList = ko.observableArray(''); 
            }
            singleSelect(){
                var self = this;
                nts.uk.ui.windows.setShared('kml001multiSelectMode', false);
                let ableCodeList = _.split(self.selectedCodeAbleList(), ',');
                let codeList = _.split(self.selectedCodeList(), ','); 
                let isSelectionA = self.isSelection();
                nts.uk.ui.windows.setShared('kml001selectAbleCodeList', nts.uk.util.isNullOrEmpty(ableCodeList[0])?[]:ableCodeList);
                nts.uk.ui.windows.setShared('kml001selectedCodeList', nts.uk.util.isNullOrEmpty(codeList[0])?[]:codeList);
                nts.uk.ui.windows.setShared('kml001isSelection', isSelectionA);
                nts.uk.ui.windows.sub.modal("/view/kdl/001/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {           
                        if(nts.uk.ui.windows.getShared("kml001selectedCodeList").length != 0){
                            self.selectedCodeList((<Array<string>>nts.uk.ui.windows.getShared("kml001selectedCodeList")[0]).toString());    
                        }
                });        
            }
            
            multiSelect(){
                var self = this;
                nts.uk.ui.windows.setShared('kml001multiSelectMode', true);
                let ableCodeList = _.split(self.selectedCodeAbleList(), ',');
                let codeList = _.split(self.selectedCodeList(), ',');
                let isSelectionA = self.isSelection(); 
                nts.uk.ui.windows.setShared('kml001selectAbleCodeList', nts.uk.util.isNullOrEmpty(ableCodeList[0])?[]:ableCodeList);
                nts.uk.ui.windows.setShared('kml001selectedCodeList', nts.uk.util.isNullOrEmpty(codeList[0])?[]:codeList);
                nts.uk.ui.windows.setShared('kml001isSelection', isSelectionA);
                nts.uk.ui.windows.sub.modal("/view/kdl/001/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                    if(nts.uk.ui.windows.getShared("kml001selectedCodeList").length != 0){
                        self.selectedCodeList((<Array<string>>nts.uk.ui.windows.getShared("kml001selectedCodeList")).toString()); 
                    }    
                });        
            }
            
            add1000Sample(){
                nts.uk.ui.block.invisible();
                var self = this;
                let arrayData = [];
                for(let a=65;a<=74;a++){
                    for(let b=65;b<=74;b++){
                        for(let c=65;c<=74;c++){
                            arrayData.push(String.fromCharCode(a)+String.fromCharCode(b)+String.fromCharCode(c));            
                        }    
                    }    
                }
                self.selectedCodeAbleList(arrayData.toString());
                nts.uk.ui.block.clear();
            }
            
            add10000Sample(){
                nts.uk.ui.block.invisible();
                var self = this;
                let arrayData = [];
                for(let a=65;a<=87;a++){
                    for(let b=65;b<=87;b++){
                        for(let c=65;c<=87;c++){
                            arrayData.push(String.fromCharCode(a)+String.fromCharCode(b)+String.fromCharCode(c));            
                        }    
                    }    
                }
                self.selectedCodeAbleList(arrayData.toString());
                nts.uk.ui.block.clear();
            }
            
            removeSample(){
                var self = this;
                self.selectedCodeList('');
                self.selectedCodeAbleList('');
            }
        }
    }
}