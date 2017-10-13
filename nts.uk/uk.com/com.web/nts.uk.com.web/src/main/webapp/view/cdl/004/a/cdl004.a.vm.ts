module nts.uk.com.view.cdl004.a {

    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    import ComponentOption = kcp.share.list.ComponentOption;

    export module viewmodel {
        /**
        * Screen Model.
        */
        export class ScreenModel {
            selectedMulJobtitle: KnockoutObservableArray<string>;
            selectedSelJobtitle: KnockoutObservable<string>;
            baseDate: KnockoutObservable<Date>;
            jobtitles: ComponentOption;
            isMultiple: boolean;
            isShowNoSelectRow: boolean;
            constructor(){
                var self = this;
                self.selectedMulJobtitle = ko.observableArray([]);
                self.selectedSelJobtitle = ko.observable('');
                self.isMultiple = false;
                self.isShowNoSelectRow = false;
                self.baseDate = ko.observable(new Date());
                var inputCDL004 = nts.uk.ui.windows.getShared('inputCDL004');
                if(inputCDL004){
                    self.isMultiple = inputCDL004.isMultiple;
                    self.baseDate(inputCDL004.baseDate);
                    self.isShowNoSelectRow = inputCDL004.showNoSelection;
                    if (self.isMultiple) {
                        self.selectedMulJobtitle(inputCDL004.canSelected);
                    }   
                    else {
                        self.selectedSelJobtitle(inputCDL004.canSelected);
                    } 
                }
                
                self.jobtitles = {
                    isShowAlreadySet: false,
                    baseDate: self.baseDate,
                    isMultiSelect: self.isMultiple,
                    listType: ListType.JOB_TITLE,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    isShowNoSelectRow:  self.isShowNoSelectRow,
                    selectedCode: null,
                    isDialog: true,
                    maxRows: 12,
                    tabindex: 1
                }
                if (self.isMultiple) {
                    self.jobtitles.selectedCode = self.selectedMulJobtitle;
                }
                else {
                    self.jobtitles.selectedCode = self.selectedSelJobtitle;
                }
            }
            
            /**
             * function on click button selected job title
             */
            private selectedJobtitle() :void {
                var self = this;
                var selectedCode: any = null;
                if(self.isMultiple){
                    selectedCode = self.selectedMulJobtitle();
                    if(!selectedCode || selectedCode.length == 0){
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_642" }).then(() => nts.uk.ui.windows.close());
                        return;    
                    }    
                } else {
                    selectedCode = self.selectedSelJobtitle();
                    var isNoSelectRowSelected = $("#jobtitle").isNoSelectRowSelected();
                    if (!selectedCode && !isNoSelectRowSelected) {
                        // Check if selected No select Row.
                        nts.uk.ui.dialog.alertError({ messageId: "Msg_641" }).then(() => nts.uk.ui.windows.close());
                        return;
                    }
                }

                nts.uk.ui.windows.setShared('outputCDL004', { selectedCode: selectedCode });
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