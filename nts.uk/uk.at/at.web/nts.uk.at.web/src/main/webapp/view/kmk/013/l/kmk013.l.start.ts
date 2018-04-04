module nts.uk.at.view.kmk013.l {
    import blockUI = nts.uk.ui.block;
    
    __viewContext.ready(function() {
        var screenModel = new l.viewmodel.ScreenModel();
        blockUI.grayout();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            blockUI.clear();
            $( "#l2_7" ).focus();
        });
    });
}
