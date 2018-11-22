module nts.uk.pr.view.qmm019.m {
    import block = nts.uk.ui.block;
    __viewContext.ready(function() {
        block.invisible();
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#M1_2_container").focus();
        }).always(() => {
            block.clear();
        });
    });
}