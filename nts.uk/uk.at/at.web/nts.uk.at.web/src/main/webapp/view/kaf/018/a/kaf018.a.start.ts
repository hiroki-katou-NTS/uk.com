module nts.uk.at.view.kaf018.a.start {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {
        __viewContext.transferred.ifPresent(data => {
            nts.uk.ui.windows.setShared("KAF018AInput", data);
        });
        let screenModel = new kaf018.a.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            nts.uk.ui.block.clear();
            _.defer(() => {
                $('#combo-box input').focus();
            });
        })
    });
}