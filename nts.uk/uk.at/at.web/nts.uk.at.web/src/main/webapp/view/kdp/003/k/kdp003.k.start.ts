module kdp003.k {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(function() {
        var screenModel = new vm.ViewModel();
        screenModel.start().done(() => {

            __viewContext.bind(screenModel);
            $('#tree-grid').ntsTreeComponent(screenModel.treeGrid).done(() => {
                $('#tree-grid').focusTreeGridComponent();
                screenModel.jsonData(JSON.stringify($('#tree-grid').getDataList(), undefined, 10));
                screenModel.isBindingTreeGrid(true);
                screenModel.getSelectedData();
            });
        });
    });
}



