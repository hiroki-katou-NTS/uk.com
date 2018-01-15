module nts.uk.at.view.kmk005.d {
    __viewContext.ready(function() {
        var screenModel = new d.viewmodel.ScreenModel();
         screenModel.startPage();
       // screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
       // });
    });
}
