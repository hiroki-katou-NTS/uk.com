module nts.uk.com.view.ccg.share.dialog.ccg {

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
            constructor(){
                var self = this;
                self.baseDate = ko.observable(new Date());
                self.selectedCodeWorkplace = ko.observableArray([]);
                self.baseDate(nts.uk.ui.windows.getShared('baseDate'));
                self.selectedCodeWorkplace(nts.uk.ui.windows.getShared('selectedCodeWorkplace'));
                self.workplaces = {
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    treeType: TreeType.WORK_PLACE,
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    isShowSelectButton: true,
                    selectedWorkplaceId: self.selectedCodeWorkplace,
                    baseDate: self.baseDate,
                    isDialog: true
                }
                 $('#workplaceList').ntsTreeComponent(self.workplaces);        
            }
            
            
            saveWorkplace() :void {
                var self = this;
                nts.uk.ui.windows.setShared('selectedCodeWorkplace', self.selectedCodeWorkplace());
                nts.uk.ui.windows.close();    
            }
        }
    }
}