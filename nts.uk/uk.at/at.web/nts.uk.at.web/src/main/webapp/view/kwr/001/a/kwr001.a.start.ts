module nts.uk.at.view.kwr001.a {
    import blockUI = nts.uk.ui.block;
    
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        blockUI.grayout();
        screenModel.startPage().done(function(){
            __viewContext.bind(screenModel);
            screenModel.executeBindingComponent();
        });
    });
}
 