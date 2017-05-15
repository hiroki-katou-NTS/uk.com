module nts.uk.pr.view.qpp021.f {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(res) {
            __viewContext.bind(res);
        });
    });
}