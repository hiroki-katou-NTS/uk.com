module nts.uk.at.view.ksu001.jc {
    import getShare = nts.uk.ui.windows.getShared;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        $('input#pasteShiftNameInput').focus();
    });   
}