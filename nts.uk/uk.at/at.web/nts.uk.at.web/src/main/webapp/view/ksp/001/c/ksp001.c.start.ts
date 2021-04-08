module nts.uk.at.view.ksp001.c {
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
    });
}