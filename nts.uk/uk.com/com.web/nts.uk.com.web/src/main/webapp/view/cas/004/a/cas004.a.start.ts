module nts.uk.com.view.cas004.a {
    __viewContext.ready(function() {
        let screenModel = new viewModel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $(".input-lead").css({"width":"150"});
            $(".input-date").css({"width":"105"});
            $(".input-email").css({"width":"420"});
        });
    });
}