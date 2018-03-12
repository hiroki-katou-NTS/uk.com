module nts.uk.pr.view.ccg007.c {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            $('#company-code-inp').focus();
        });
    });
}