module nts.uk.at.view.kdw006.h {  
    import kdw = nts.uk.at.view.kdw006.h;
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        let screenModel = new kdw.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}