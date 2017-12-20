module nts.uk.at.view.kmk012.e {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        //Run start() function when bind screenModel done
        screenModel.startPage();
    });
}