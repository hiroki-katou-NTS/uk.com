module nts.uk.at.view.kmk004.f {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        //        screenModel.startPage().done(function() {
        __viewContext.bind(screenModel);
        _.defer(() => {
            $('#combo-box').focus();
        });
        //        });
    });
}