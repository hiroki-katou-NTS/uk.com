module nts.uk.com.view.cmf001.f {
    __viewContext.ready(function() {
        __viewContext['screenModel'] = new viewmodel.ScreenModel();
        __viewContext['screenModel'].start().done(function() {
            __viewContext.bind(__viewContext['screenModel']);
            _.defer(() => {
                if (__viewContext['screenModel'].screenMode() == share.model.SCREEN_MODE.UPDATE) {
                    $('#F4_3').focus();
                } else {
                    $('#F4_2').focus();
                }
            });
        });
    });
}
