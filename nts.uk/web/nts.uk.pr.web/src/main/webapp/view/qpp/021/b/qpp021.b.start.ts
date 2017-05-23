module  nts.uk.pr.view.qpp021.b {
    __viewContext.ready(function() {
        let screenModel = new  nts.uk.pr.view.qpp021.b.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            //
        });
    });
}