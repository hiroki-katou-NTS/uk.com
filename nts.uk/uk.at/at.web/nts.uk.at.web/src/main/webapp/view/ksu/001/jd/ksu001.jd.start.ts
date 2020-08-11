module nts.uk.at.view.ksu001.jd {
    import getShare = nts.uk.ui.windows.getShared;

    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {

        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
            $('#combo-box-des').focus();
        });

    });
}