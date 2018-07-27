module nts.uk.at.view.kmk003.i {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if (screenModel.isFlow()) {
                _.defer(() => $('#lateStampExactlyTimeIsLateEarly').focus());
            } else {
                _.defer(() => $('#delFromEmTime').focus());
            }
        });
    });
}