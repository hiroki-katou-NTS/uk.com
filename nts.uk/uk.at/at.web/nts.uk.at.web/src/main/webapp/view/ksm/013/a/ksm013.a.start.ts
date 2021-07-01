module nts.uk.at.view.ksm013.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        }).then(function() {
            setTimeout(() => { $('[tabindex= 5]').focus(); }, 1);
        });
    });
}