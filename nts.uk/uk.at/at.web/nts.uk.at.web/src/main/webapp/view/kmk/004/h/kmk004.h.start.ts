module nts.uk.at.view.kmk004.h {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        _.defer(() => {
            $('#combo-box').focus();
        });
    });
}