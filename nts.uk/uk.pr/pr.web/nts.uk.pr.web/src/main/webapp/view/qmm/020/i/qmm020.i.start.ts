module nts.uk.pr.view.qmm020.i {
    nts.uk.ui.block.invisible();
    __viewContext.ready(function () {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function () {
            __viewContext.bind(screenModel);
            nts.uk.ui.block.clear();
        });
    });
}