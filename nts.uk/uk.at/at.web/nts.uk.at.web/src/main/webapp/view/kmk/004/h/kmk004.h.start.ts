module nts.uk.at.view.kmk004.h {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
//        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#combo-box').focus();
//        });
    });
}