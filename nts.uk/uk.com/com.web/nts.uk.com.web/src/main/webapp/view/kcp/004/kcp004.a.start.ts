module kcp004.a {
    __viewContext.ready(function () {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $('#tree-grid').ntsTreeComponent(screenModel.treeGrid).done(() => {
            $('#tree-grid').focusComponent();
            screenModel.jsonData(JSON.stringify($('#tree-grid').getDataList(), undefined, 10));
            screenModel.getSelectedData();
        });
    });
}