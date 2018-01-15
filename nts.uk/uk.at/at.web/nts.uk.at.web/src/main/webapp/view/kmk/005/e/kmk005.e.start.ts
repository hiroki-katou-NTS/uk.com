module nts.uk.at.view.kmk005.e {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kmk005.e.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("a#ui-id-1.ui-tabs-anchor").attr('tabindex', 1);
            $("a#ui-id-2.ui-tabs-anchor").attr('tabindex', 1);
        });
    });
}
