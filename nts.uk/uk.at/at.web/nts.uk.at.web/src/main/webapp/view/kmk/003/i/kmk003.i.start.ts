module nts.uk.at.view.kmk003.i {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            if (screenModel.isFlow()) {
                $('#lateStampExactlyTimeIsLateEarly').focus();
            } else {
                $('#delFromEmTime').focus();
            }
        });
    });
}