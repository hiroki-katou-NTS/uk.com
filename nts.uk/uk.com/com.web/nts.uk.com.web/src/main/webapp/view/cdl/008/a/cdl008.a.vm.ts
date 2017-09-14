module nts.uk.com.view.cdl008.a {

    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    import ComponentOption = kcp.share.list.ComponentOption;
    import TreeComponentOption = kcp.share.tree.TreeComponentOption;
    import TreeType = kcp.share.tree.TreeType;

    export module viewmodel {
        /**
        * Screen Model.
        */
        export class ScreenModel {
            selectedCodeWorkplace: KnockoutObservableArray<string>;
            baseDate: KnockoutObservable<Date>;
            workplaces: TreeComponentOption;
            isMultiple: boolean;
            constructor(){
                var self = this;
                self.baseDate = ko.observable(new Date());
                self.selectedCodeWorkplace = ko.observableArray([]);
                self.isMultiple = false;
                var inputCDL008 = nts.uk.ui.windows.getShared('inputCDL008');
                if(inputCDL008){
                    self.baseDate(inputCDL008.baseDate);
                    self.isMultiple = inputCDL008.isMultiple;
                    self.selectedCodeWorkplace(inputCDL008.canSelected);    
                }
                
                self.workplaces = {
                    isShowAlreadySet: false,
                    isMultiSelect: self.isMultiple,
                    treeType: TreeType.WORK_PLACE,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    isShowSelectButton: true,
                    selectedWorkplaceId: self.selectedCodeWorkplace,
                    baseDate: self.baseDate,
                    isDialog: true,
                    maxRows : 12
                }
            }
            
            /**
             * function on click button selected workplace
             */
            private selectedWorkplace() :void {
                var self = this;
                nts.uk.ui.windows.setShared('outputCDL008', { selectedCode: self.selectedCodeWorkplace() });
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