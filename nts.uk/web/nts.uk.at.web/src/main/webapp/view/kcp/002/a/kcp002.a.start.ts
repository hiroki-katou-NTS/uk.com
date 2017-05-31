module nts.uk.at.view.kcp002.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(res: viewmodel.ScreenModel) {
            __viewContext.bind(res);
        });
    });
}