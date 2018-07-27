module nts.uk.com.view.cmf005.c {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $("#C2_1").find("input").first().focus();
    });
}