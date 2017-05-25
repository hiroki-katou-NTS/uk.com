module nts.uk.at.view.kml001.d {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kml001.d.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        if(screenModel.isUpdate()) $('#startDateInput').focus();
    });
}
