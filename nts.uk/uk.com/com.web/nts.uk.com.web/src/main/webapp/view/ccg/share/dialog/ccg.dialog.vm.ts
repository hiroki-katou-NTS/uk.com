module nts.uk.com.view.ccg.share.dialog.ccg {

    import ListType = kcp.share.list.ListType;
    import SelectType = kcp.share.list.SelectType;
    import ComponentOption = kcp.share.list.ComponentOption;
    import TreeComponentOption = kcp.share.tree.TreeComponentOption;
    //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
    import TreeType = kcp.share.tree.TreeType;
//    import StartMode = kcp.share.tree.StartMode;
    //end

    export module viewmodel {
        /**
        * Screen Model.
        */
        export class ScreenModel {
            selectedCodeWorkplace: KnockoutObservableArray<string>;
            baseDate: KnockoutObservable<Date>;
            workplaces: TreeComponentOption;
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
//            startMode: StartMode;
            //end
            constructor(){
                var self = this;
                self.baseDate = ko.observable(new Date());
                self.selectedCodeWorkplace = ko.observableArray([]);
                self.baseDate(nts.uk.ui.windows.getShared('baseDate'));
                self.selectedCodeWorkplace(nts.uk.ui.windows.getShared('selectedCodeWorkplace'));
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
//                self.startMode = nts.uk.ui.windows.getShared('startMode');
                //end
                self.workplaces = {
                    isShowAlreadySet: false,
                    isMultiSelect: true,
                    //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                    treeType: TreeType.WORK_PLACE,
                    selectedWorkplaceId: self.selectedCodeWorkplace,                   
//                    startMode: self.startMode,
//                    selectedId: self.selectedCodeWorkplace,
                    //end
                    selectType: SelectType.SELECT_BY_SELECTED_CODE,
                    isShowSelectButton: true,
                    baseDate: self.baseDate,
                    isDialog: true
                };
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