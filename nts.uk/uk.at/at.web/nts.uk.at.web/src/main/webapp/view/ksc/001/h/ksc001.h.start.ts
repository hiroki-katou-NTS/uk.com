module nts.uk.at.view.ksc001.h {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.at.view.ksc001.h.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if (screenModel.controlExecution()) {
                $('#execution-number').focus();
            }
            else {
                if (screenModel.controlError()) {
                    $('#error-number').focus();
                }
                else {
                    $('#btn-close').focus();
                }
            }
        });
    });
}