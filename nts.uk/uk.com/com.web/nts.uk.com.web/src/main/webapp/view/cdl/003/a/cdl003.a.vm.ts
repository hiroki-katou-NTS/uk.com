module nts.uk.com.view.cdl003.a {

    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    import ComponentOption = kcp.share.list.ComponentOption;

    export module viewmodel {
        /**
        * Screen Model.
        */
        export class ScreenModel {
            selectedMulClassification: KnockoutObservableArray<string>;
            selectedSelClassification: KnockoutObservable<string>;
            classifications: ComponentOption;
            isMultiple: boolean;
            isShowNoSelectRow: boolean;
            constructor(){
                var self = this;
                self.selectedMulClassification = ko.observableArray([]);
                self.selectedSelClassification = ko.observable('');
                self.isMultiple = false;
                self.isShowNoSelectRow = false;
                var inputCDL003 = nts.uk.ui.windows.getShared('inputCDL003');
                if(inputCDL003){
                    self.isMultiple = inputCDL003.isMultiple;
                    self.isShowNoSelectRow = inputCDL003.showNoSelection;
                    if (self.isMultiple) {
                        self.selectedMulClassification(inputCDL003.canSelected);
                    }   
                    else {
                        self.selectedSelClassification(inputCDL003.canSelected);
                    } 
                }
                
                self.classifications = {
                    isShowAlreadySet: false,
                    isMultiSelect: self.isMultiple,
                    listType: ListType.Classification,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    isShowNoSelectRow:  self.isShowNoSelectRow,
                    selectedCode: null,
                    isDialog: true,
                    maxRows: 12
                }
                if (self.isMultiple) {
                    self.classifications.selectedCode = self.selectedMulClassification;
                }
                else {
                    self.classifications.selectedCode = self.selectedSelClassification;
                }
            }
            
            /**
             * function on click button selected classification
             */
            private selectedClassification() :void {
                var self = this;
                if(self.isMultiple){
                    if(!self.selectedMulClassification() || self.selectedMulClassification().length == 0){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_641" });
                        return;    
                    }    
                }else {
                     if(!self.selectedSelClassification || !self.selectedSelClassification()){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_641" });
                        return;    
                    }      
                }
                
                var selectedCode : any = self.selectedMulClassification();
                if (!self.isMultiple) {
                    selectedCode = self.selectedSelClassification();
                }
                nts.uk.ui.windows.setShared('outputCDL003', { selectedCode: selectedCode });
                nts.uk.ui.windows.close();    
            }
            /**
             * close windows
             */
            private closeWindows(): void{
                nts.uk.ui.windows.close();  
            }
        }
    }
}