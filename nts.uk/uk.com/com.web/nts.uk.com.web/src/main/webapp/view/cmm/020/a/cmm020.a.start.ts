module nts.uk.com.view.cmm020.a {
     __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start_page().done(function() {
            __viewContext.bind(screenModel);
            var do_focus = function() {
                var element = $("#single-list").find("tr").last();
                element.focus();
                screenModel.isFocus(true);
            };
            setTimeout(do_focus, 500);
        });
    });
}