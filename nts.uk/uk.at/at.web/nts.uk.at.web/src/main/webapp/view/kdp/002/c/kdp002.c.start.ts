module nts.uk.at.view.kdp002.c {
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
        });
    });
}