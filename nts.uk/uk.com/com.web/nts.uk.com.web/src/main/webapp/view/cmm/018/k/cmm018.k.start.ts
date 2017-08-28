module cmm008.k{
    __viewContext.ready(function () {
        var screenModel = new cmm018.k.viewmodel.ScreenModel();
        //screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        var self = screenModel;
            $('#tree-grid').ntsTreeComponent(self.treeGrid);
            $('#component-items-list').ntsListComponent(self.listComponentOption);
        //});
    });
}