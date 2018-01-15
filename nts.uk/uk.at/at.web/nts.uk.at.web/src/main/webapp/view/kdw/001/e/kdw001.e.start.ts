module nts.uk.at.view.kdw001.e {
    import shareModel = nts.uk.at.view.kdw001.share.model;

    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kdw001.e.viewmodel.ScreenModel();
        var params: shareModel.executionProcessingCommand = nts.uk.ui.windows.getShared("KDWL001E");
        screenModel.startPage(params).done(function() {
            __viewContext.bind(screenModel);
        });
    });
}