module nts.uk.at.view.kmk004.d {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            screenModel.postBindingProcess();
            $('#worktimeYearPicker').focus();
            
            $("#sidebar").ntsSideBar("active", 2);
        });
    });
}