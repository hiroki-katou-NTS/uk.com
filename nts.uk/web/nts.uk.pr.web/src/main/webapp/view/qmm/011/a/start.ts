module nts.uk.pr.view.qmm011.a.start {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.pr.view.qmm011.a.viewmodel.ScreenModel();
        this.bind(screenModel);
    });
}