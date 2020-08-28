module nts.uk.at.view.kdp003.a {
    import blockUI = nts.uk.ui.block;
    
    __viewContext.ready(function() {
        blockUI.grayout();
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(){
            __viewContext.bind(screenModel);
            $('#btnExportExcel').focus();
            screenModel.executeComponent().done(() => {
                blockUI.clear();
            });
        });
    });
}
