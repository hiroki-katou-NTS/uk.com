__viewContext.ready(function() {
    var screenModel = new cmm001.a.ViewModel();
    nts.uk.ui.confirmSave(screenModel.dirtyObject);
    screenModel.start(undefined)
    __viewContext.bind(screenModel);
   
});
