module nts.uk.pr.view.qpp018.c {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(data => {
            nts.uk.ui.confirmSave(screenModel.dirty);
            __viewContext.bind(data);
        });
    });
}