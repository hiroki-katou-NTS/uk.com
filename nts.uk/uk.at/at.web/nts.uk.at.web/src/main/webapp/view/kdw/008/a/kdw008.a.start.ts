module nts.uk.at.view.kdw008.a {
    __viewContext.ready(function() {
        let dataShare: any;
        this.transferred.ifPresent(data => {
            dataShare = data;
        });
        
        let screenModel = new viewmodel.ScreenModel(dataShare);
        if (dataShare.mobile) {
            screenModel.startMobilePage().done(function() {
                __viewContext.bind(screenModel);
            });
        } else {
            screenModel.startPage().done(function() {
                __viewContext.bind(screenModel);
            });
        }

    });
}