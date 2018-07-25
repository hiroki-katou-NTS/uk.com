module nts.uk.com.view.kal004.d {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $("#start-month input").focus();

    });
}