module nts.uk.at.view.kwr001.c {
    
    import blockUI = nts.uk.ui.block;
    
    __viewContext.ready(function() {
        var screenModel = new c.viewmodel.ScreenModel();
        blockUI.grayout();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if (_.isEmpty(screenModel.currentCodeList()) || _.isNull(screenModel.currentCodeList())) {
                $('#C3_2').focus();
            } else {
                $('#C3_3').focus();
            }
            blockUI.clear();
        });
    });
}
