module nts.uk.pr.view.ccg015.c {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel(nts.uk.ui.windows.getShared("topPageCode"),nts.uk.ui.windows.getShared("topPageName"),nts.uk.ui.windows.getShared("layoutId"));
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            $("#inp-code").focus();
        });
    });
}