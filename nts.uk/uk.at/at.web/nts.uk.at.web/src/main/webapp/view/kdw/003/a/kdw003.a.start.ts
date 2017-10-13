module nts.uk.at.view.kdw003.a {
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kdw003.a.viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            screenModel.loadKcp009();
            __viewContext.bind(screenModel);
        });
    });
}