module nts.uk.at.view.kal003.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            _.defer(() => {
                if (screenModel.screenMode() == nts.uk.at.view.kal003.share.model.SCREEN_MODE.UPDATE) {
                    $("#A3_4").focus();
                } else {
                    $("#A3_2").focus();
                }
            });
        });
    });
}