module nts.uk.com.view.cdl008.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
            $('#workplaceList').ntsTreeComponent(screenModel.workplaces).done(function() {
            $('#workplaceList').focusTreeGridComponent();
//        let id = screenModel.workplaces.startMode == 1 ? 'departmentList' : 'workplaceList';
//        $('#' + id).ntsTreeComponent(screenModel.workplaces).done(function() {
//            $('#' + id).focusTreeGridComponent();
         //end
            // Check selected code.

            if (screenModel.isMultipleSelect && screenModel.selectedMulWorkplace().length > 0) {
                //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
                let selectedCodes = $('#workplaceList').find('#multiple-tree-grid').igTreeGrid("selectedRows");
//                let selectedCodes = $('#' + id).find('#multiple-tree-grid-' + id).igTreeGrid("selectedRows");
                //end
                let selectedCodesExist = selectedCodes.filter(item => item.index > -1).map(item => item.id);
                screenModel.selectedMulWorkplace(selectedCodesExist);
                return;
            }
            if (!screenModel.selectedSelWorkplace()) {
                return;
            }
            //start CDL008,KCP004,CCG001: revertCode (職場・部門対応)
            let selectedCode = $('#workplaceList').find('#single-tree-grid').igTreeGrid("selectedRow").id || null;
//            let selectedCode = $('#' + id).find('#single-tree-grid-' + id).igTreeGrid("selectedRow").id || null;
            //end
            screenModel.selectedSelWorkplace(selectedCode);
        });
    });
}