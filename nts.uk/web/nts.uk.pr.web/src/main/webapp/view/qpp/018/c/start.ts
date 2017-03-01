module nts.uk.pr.view.qpp018.c {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.pr.view.qpp018.c.viewmodel.ScreenModel();
        screenModel.start().done(data => {
            __viewContext.bind(data);
        });
    });
}