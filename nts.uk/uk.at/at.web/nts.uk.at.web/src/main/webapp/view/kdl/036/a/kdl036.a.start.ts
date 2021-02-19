module nts.uk.at.view.kdl036.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if (screenModel.substituteWorkInfoList().length > 0) {
                $(".ntsCheckBox")[0].focus();
            }
            // $("#legendBtn").html(nts.uk.resource.getText("KDL036_5"));
        });
    });
}
