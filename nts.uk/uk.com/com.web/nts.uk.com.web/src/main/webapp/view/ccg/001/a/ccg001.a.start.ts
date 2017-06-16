module nts.uk.com.view.ccg001.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $(".accordion").accordion({
                active: false,
                collapsible: true
            });
        });
    });
}