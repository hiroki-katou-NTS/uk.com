module nts.uk.at.view.kdp005.h {
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        let screenModel = __viewContext.vm = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
    });
}