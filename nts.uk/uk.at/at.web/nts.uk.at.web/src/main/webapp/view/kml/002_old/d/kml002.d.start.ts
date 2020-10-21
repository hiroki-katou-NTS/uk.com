module nts.uk.at.view.kml002.d {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            $(".ntsSearchBox.nts-editor.ntsSearchBox_Component").focus();
        });
    });
}    