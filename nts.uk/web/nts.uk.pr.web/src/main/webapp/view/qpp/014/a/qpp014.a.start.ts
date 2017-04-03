module qpp014.a {
    __viewContext.ready(function() {
        var screenModel = new qpp014.a.viewmodel.ScreenModel();

        screenModel.startPage().done(function() {
             $('body').css('visibility','visible');
            __viewContext.bind(screenModel);
        });
    });
}