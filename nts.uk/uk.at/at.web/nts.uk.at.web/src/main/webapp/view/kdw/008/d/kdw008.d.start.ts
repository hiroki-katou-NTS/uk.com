module nts.uk.at.view.kdw008.d {
    import getShared = nts.uk.ui.windows.getShared;
    __viewContext.ready(function() {

        let dataShare: any;
        this.transferred.ifPresent(data => {
            dataShare = data;
        });

        let screenModel = new viewmodel.ScreenModel(dataShare);
        __viewContext.bind(screenModel);
    });
}