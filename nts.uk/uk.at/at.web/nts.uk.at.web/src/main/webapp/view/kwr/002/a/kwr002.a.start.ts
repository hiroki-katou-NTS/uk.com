module nts.uk.com.view.kwr002.a {
    import blockUI = nts.uk.ui.block;
    import viewModel = nts.uk.com.view.kwr002.a;
    __viewContext.ready(function() {
        let mainTab = new viewModel.viewModel.ScreenModel();
      
        mainTab.start_page().done(function(screenModel: any) {
            __viewContext.bind(mainTab);
        });
    });
}