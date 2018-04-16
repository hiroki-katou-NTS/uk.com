module nts.uk.at.view.kmk013.q {
    import blockUI = nts.uk.ui.block;
    
    __viewContext.ready(function() {
        var screenModel = new q.viewmodel.ScreenModel();
        blockUI.grayout();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            blockUI.clear();
            $( "#tab-panel" ).focus();
        });
    });
}
