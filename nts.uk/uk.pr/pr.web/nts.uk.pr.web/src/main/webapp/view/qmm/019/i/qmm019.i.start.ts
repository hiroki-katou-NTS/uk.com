module nts.uk.pr.view.qmm019.i {
    import block = nts.uk.ui.block;
    __viewContext.ready(function () {
        block.invisible();
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function () {
            __viewContext.bind(screenModel);
            $("#I1_2_container").focus();
        }).always(() => {
            block.clear();
        });
    });
}