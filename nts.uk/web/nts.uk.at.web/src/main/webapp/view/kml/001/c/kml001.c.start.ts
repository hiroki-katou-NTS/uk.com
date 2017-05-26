module nts.uk.at.view.kml001.c {
    __viewContext.ready(function() {
        var screenModel = new nts.uk.at.view.kml001.c.viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $('#startDateInput-input').focus();
    });
}
