module nts.uk.at.view.test.template {
    __viewContext.ready(function() {

        var screenModel = new nts.uk.at.view.test.template.viewmodel.ViewModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        });
    });
}