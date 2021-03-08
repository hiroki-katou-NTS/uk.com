module nts.uk.at.view.kdp010.b {
    import kdp = nts.uk.at.view.kdp010;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        __viewContext.viewModel = {
            tabView: new kdp.viewmodel.TabScreenModel(),
            viewmodelB: new kdp.b.viewmodel.ScreenModel(),
            viewmodelE: new kdp.e.viewmodel.ScreenModel()
        };
        
        if(__viewContext.viewModel.tabView.currentTab() == 'B'){
            __viewContext.viewModel.viewmodelB.start();
        }

        __viewContext.bind(__viewContext.viewModel);

  });
}
