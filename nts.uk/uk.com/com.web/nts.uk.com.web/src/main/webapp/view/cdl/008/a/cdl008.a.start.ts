module nts.uk.com.view.cdl008.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $('#workplaceList').ntsTreeComponent(screenModel.workplaces).done(function() {
            $('#workplaceList').focusTreeGridComponent();
            // Check selected code.
            if (screenModel.isMultipleSelect && screenModel.selectedMulWorkplace().length > 0) {
                let selectedCodes = $('#workplaceList').find('#multiple-tree-grid').igTreeGrid("selectedRows");
                let selectedCodesExist = selectedCodes.filter(item => item.index > -1).map(item => item.id);
                screenModel.selectedMulWorkplace(selectedCodesExist);
                return;
            }
            if (!screenModel.selectedSelWorkplace()) {
                return;
            }
            let selectedCode = $('#workplaceList').find('#single-tree-grid').igTreeGrid("selectedRow").id || null;
            screenModel.selectedSelWorkplace(selectedCode);
        });
    });
}