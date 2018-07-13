module nts.uk.com.view.cdl008.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $('#workplaceList').ntsTreeComponent(screenModel.workplaces).done(function() {
            $('#workplaceList').focusTreeGridComponent();
            // Check selected code.
            if (screenModel.isMultipleSelect && screenModel.selectedMulWorkplace().length > 0) {
                var selectedCodes = $('#workplaceList').find('#multiple-tree-grid').igTreeGrid("selectedRows");
                var selectedCodesExist = selectedCodes.filter(item => item.index > -1).map(item => item.id);
                screenModel.selectedMulWorkplace(selectedCodesExist);
                return;
            }
            if (!screenModel.selectedSelWorkplace()) {
                return;
            }
            var selectedCode = $('#workplaceList').find('#single-tree-grid').igTreeGrid("selectedRows");
            screenModel.selectedSelWorkplace(selectedCode[0].index > -1 ? selectedCode[0].id : null);
        });
    });
}