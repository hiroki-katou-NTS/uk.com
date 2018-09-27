module nts.uk.at.view.kmk013.e {
    
    import blockUI = nts.uk.ui.block;
    
    __viewContext.ready(function() {
        blockUI.grayout();
        var screenModel = new e.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            blockUI.clear();
        })
    });
}