module nts.uk.at.view.kwr006.c {
    import blockUI = nts.uk.ui.block;
    __viewContext.ready(function() {
         blockUI.grayout();
        var screenModel = new c.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if (_.isEmpty(screenModel.selectedCodeC2_3()) || _.isNull(screenModel.selectedCodeC2_3())) {
                $('#C3_2').focus();
            } else {
                $('#C3_3').focus();
            }
            blockUI.clear();
        });
    });
}