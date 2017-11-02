module nts.uk.com.view.cdl009.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        // Load Component
        // $('#workplace-component').ntsTreeComponent(screenModel.treeGrid)
//        $.when($('#workplace-component').ntsTreeComponent(screenModel.treeGrid), $('#emp-component').ntsListComponent(screenModel.listComponentOpt))
        $('#workplace-component').ntsTreeComponent(screenModel.treeGrid)
        .done(function() {
            $('#emp-component').ntsListComponent(screenModel.listComponentOpt).done(function(){
                $('#workplace-component').focus();
            });
//            $('#workplace-component').focusTreeGridComponent();
            
            // Check init selected code.
            if (screenModel.isMultiSelect()) {
                var selectedData: string[] = [];
                for (var code of screenModel.multiSelectedTree()) {
                    if (screenModel.checkExistWorkplace(code, $("#workplace-component").getDataList())) {
                        selectedData.push(code);
                    }
                }
                screenModel.multiSelectedTree(selectedData);
            } else {
                if (!screenModel.checkExistWorkplace(screenModel.selectedWorkplace(), $("#workplace-component").getDataList())) {
                    screenModel.selectedWorkplace(null);
                }
            }
        });
    });
}
