module nts.uk.pr.view.qmm020.m {
    __viewContext.ready(function () {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.initScreen().done(() => {
            $('#single-list_container').focus();
        });
        __viewContext.bind(screenModel);
    });
}