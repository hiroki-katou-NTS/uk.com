module nts.uk.com.view.cmf001.d {
    __viewContext.ready(function() {
        this.transferred.ifPresent(data => {
            console.log(data);
            var screenModel = new viewmodel.ScreenModel(data);
            //screenModel.startPage().done(function() {
                __viewContext.bind(screenModel);
    //            _.defer(() => {
    //                if (screenModel.screenMode() == nts.uk.at.view.kal003.share.model.SCREEN_MODE.UPDATE) {
    //                    $("#A3_4").focus();
    //                } else {
    //                    $("#A3_2").focus();
    //                }
    //            });
            //});
        });
        
    });
}