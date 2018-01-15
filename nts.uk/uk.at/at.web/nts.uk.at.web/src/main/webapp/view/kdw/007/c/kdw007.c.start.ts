module nts.uk.at.view.kdw007.c {
    __viewContext.ready(function() {
        let param = nts.uk.ui.windows.getShared("KDW007Params");
        var screenModel = new nts.uk.at.view.kdw007.c.viewmodel.ScreenModel(param);
        __viewContext.bind(screenModel);
    });
}