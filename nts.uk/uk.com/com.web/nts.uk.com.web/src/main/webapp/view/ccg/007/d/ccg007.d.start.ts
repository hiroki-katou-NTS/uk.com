module nts.uk.pr.view.ccg007.d {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            $('#employee-code-inp').focus();
        });
    });
}