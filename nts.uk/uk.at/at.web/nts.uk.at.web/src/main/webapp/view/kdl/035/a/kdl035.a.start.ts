module nts.uk.at.view.kdl035.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            // $("#legendBtn").html(nts.uk.resource.getText("KDL035_5"));
        });
    });
}
