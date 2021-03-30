module nts.uk.at.view.ksp001.d {

    __viewContext.ready(function() {
        let screenModel = new nts.uk.at.view.ksp001.d.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $($('div.first-swBtn')[0]).focus();
        }); 
    });
}