module ccg013.g {
    __viewContext.ready(function() {
        var screenModel = new ccg013.g.viewmodel.ScreenModel();
        screenModel.start();
        __viewContext.bind(screenModel);
        $("#profile-input").focus();
    });
}