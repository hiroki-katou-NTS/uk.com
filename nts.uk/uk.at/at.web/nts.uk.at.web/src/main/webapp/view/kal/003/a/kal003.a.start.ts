module nts.uk.at.view.kal003.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            _.defer(() => {
                if (screenModel.screenMode() == nts.uk.at.view.kal003.share.model.SCREEN_MODE.UPDATE) {
                    setTimeout(function() { $("#A3_4").focus(); }, 500);
                } else {
                    $("#A3_2").focus();
                }
            });
        });
    });
}