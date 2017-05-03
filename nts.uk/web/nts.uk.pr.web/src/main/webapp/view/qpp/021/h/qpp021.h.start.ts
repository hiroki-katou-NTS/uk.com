module nts.uk.pr.view.qpp021.h {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(res: viewmodel.ScreenModel) {
            __viewContext.bind(res);
        });
    });
}