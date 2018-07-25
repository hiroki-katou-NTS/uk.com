module nts.uk.at.view.kwr001.b {
    
    import blockUI = nts.uk.ui.block;
    
    __viewContext.ready(function() {
        blockUI.grayout();
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
                $("#multi-list-div").focus();
                blockUI.clear();
        });
    });
}
