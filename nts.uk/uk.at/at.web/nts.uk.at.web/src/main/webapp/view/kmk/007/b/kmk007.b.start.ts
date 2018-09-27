module nts.uk.at.view.kmk007.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        nts.uk.ui.block.invisible();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        }).always(() => {
            nts.uk.ui.block.clear();
        });
    });
}