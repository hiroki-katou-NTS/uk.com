module nts.uk.at.view.ktg002.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        var resultArea = $(".ktg002");
        screenModel.startPage().done(function() {
            setTimeout(function() {
            ko.applyBindings(screenModel, resultArea.children().get(0));
            }, 0);
        });
    });
}