module nts.uk.at.view.kwr006.a {
    import blockUI = nts.uk.ui.block;
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        blockUI.grayout();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            screenModel.executeBindingComponent();
            $("#combo-box").ntsEditor("validate");
            blockUI.clear();
        });
    });


}