module nts.uk.at.view.kmk004.b {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#worktimeYearPicker').focus();
            
            $("#sidebar").ntsSideBar("active", 3);
            screenModel.postBindingProcess();
        });
    });
}