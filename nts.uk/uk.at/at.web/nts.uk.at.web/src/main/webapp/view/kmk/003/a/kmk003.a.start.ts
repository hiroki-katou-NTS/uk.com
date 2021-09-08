module nts.uk.at.view.kmk003.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#search-daily-atr').focus();
            setTimeout(function () {
                $('.clear-icon').trigger("click");
                $('#tab-panel > .tabs-content').addClass('left-122');
            }, 100)
        });
    });
}