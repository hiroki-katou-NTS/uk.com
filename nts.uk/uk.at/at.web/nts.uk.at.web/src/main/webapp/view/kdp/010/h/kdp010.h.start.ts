module nts.uk.at.view.kdp010.h {
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
        });
    });
}