module nts.uk.at.view.kdl001.test {
    export module viewmodel {
        export class ScreenModel {
            selectedCodeList: string;
            selectedCodeAbleList: string;
            constructor(){
                var self = this;
                self.selectedCodeList = ko.observableArray('');   
                self.selectedCodeAbleList = ko.observableArray('');   
            }
            singleSelect(){
                var self = this;
                nts.uk.ui.windows.setShared('kml001multiSelectMode', false);
                nts.uk.ui.windows.setShared('kml001selectAbleCodeList', _.split(self.selectedCodeAbleList(), ','));
                nts.uk.ui.windows.setShared('kml001selectedCodeList', _.split(self.selectedCodeList(), ','));
                nts.uk.ui.windows.sub.modal("/view/kdl/001/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                    if(nts.uk.ui.windows.getShared("kml001selectedCodeList")!=null){
                        self.selectedCodeList((<Array<string>>nts.uk.ui.windows.getShared("kml001selectedCodeList")[0]).toString());    
                    }
                });        
            }
            
            multiSelect(){
                var self = this;
                nts.uk.ui.windows.setShared('kml001multiSelectMode', true);
                nts.uk.ui.windows.setShared('kml001selectAbleCodeList', _.split(self.selectedCodeAbleList(), ','));
                nts.uk.ui.windows.setShared('kml001selectedCodeList', _.split(self.selectedCodeList(), ','));
                nts.uk.ui.windows.sub.modal("/view/kdl/001/a/index.xhtml", { title: "割増項目の設定", dialogClass: "no-close" }).onClosed(function() {
                    if(nts.uk.ui.windows.getShared("kml001selectedCodeList")!=null){
                        self.selectedCodeList((<Array<string>>nts.uk.ui.windows.getShared("kml001selectedCodeList")).toString()); 
                    }    
                });        
            }
            
            addSample(){
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
            
            removeSample(){
                var self = this;
                self.selectedCodeList('');
                self.selectedCodeAbleList('');
            }
        }
    }
}