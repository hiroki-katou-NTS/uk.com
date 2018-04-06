module nts.uk.at.view.kmk004.c {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $('#worktimeYearPicker').focus();
            
            screenModel.postBindingProcess();
            $("#sidebar").ntsSideBar("active", 1);
        });
    });
}