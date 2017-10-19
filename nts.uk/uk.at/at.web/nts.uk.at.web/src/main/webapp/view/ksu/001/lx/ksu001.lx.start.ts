module nts.uk.at.view.ksu001.lx {
    let __viewContext: any = window["__viewContext"] || {};
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if (screenModel.isCreated()) {
                $("#input-teamCode").focus();
            } else {
                $("#input-teamName").focus();
            }
        });

    });
}