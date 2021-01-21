module nts.uk.at.view.kaf018_old.f {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {
        __viewContext.transferred.ifPresent(data => {
            nts.uk.ui.windows.setShared("KAF018F_PARAMS", data);
        });
        let screenModel = new kaf018_old.f.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        })
    });
}