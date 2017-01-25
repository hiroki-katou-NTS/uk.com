module qmm012.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        screenModel.start();
    });
}

function OpenModeSubWindow(url, option) {
    nts.uk.ui.windows.sub.modal(url, option);
}
function closeDialog() {
    nts.uk.ui.windows.close();
}
