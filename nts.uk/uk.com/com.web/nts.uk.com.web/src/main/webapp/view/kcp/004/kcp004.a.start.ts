module kcp004.a {
    __viewContext.ready(function () {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $('#tree-multiple').ntsTreeComponent(screenModel.treeComponentOptionMultiple);
        $('#tree').ntsTreeComponent(screenModel.treeComponentOption);
        $('#tree-multiple-already-disp-none').ntsTreeComponent(screenModel.treeComponentOptionMultipleNoDispAlready);
        $('#tree-already-disp-none').ntsTreeComponent(screenModel.treeComponentOptionNoDispAlready);
    });
}