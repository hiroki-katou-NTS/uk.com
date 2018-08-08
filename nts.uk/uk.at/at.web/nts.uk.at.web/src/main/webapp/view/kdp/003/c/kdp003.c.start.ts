module nts.uk.at.view.kdp003.c {
    __viewContext.ready(function() {
        let dataShare: any;
        this.transferred.ifPresent(data => {
            dataShare = data;
            let screenModel = new viewmodel.ScreenModel(dataShare);
            screenModel.startPage().done(function() {
                __viewContext.bind(screenModel);
               
                _.defer(() => screenModel.setInitialFocusAndTabindex());
            });
            });
        });
    }
