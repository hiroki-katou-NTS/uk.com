module nts.uk.at.view.kaf000.test{
    __viewContext.ready(function () {
        var screenModel = new kaf000.test.viewmodel.ScreenModel();
        //screenModel.start().done(function() {
            __viewContext.bind(screenModel);
        //});
    });
}