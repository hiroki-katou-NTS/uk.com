module nts.uk.com.view.cmf005.f {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $("#F10_1").focus();
        $("#F10_2").hide();
    });
}