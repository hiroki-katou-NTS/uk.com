module nts.uk.at.view.kmk013.e {
    __viewContext.ready(function() {
        var screenModel = new e.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if ($('.unit-combo-box').length > 0) $('.unit-combo-box')[0].focus();
        })
    });
}