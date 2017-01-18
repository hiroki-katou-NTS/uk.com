module nts.uk.pr.view.qmm010.b {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.pr.view.qmm010.b.viewmodel.ScreenModel();
        screenModel.startPage().done(function(res: any) {
            __viewContext.bind(res);
        });
    });
}