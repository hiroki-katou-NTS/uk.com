module nts.uk.at.view.ksc001.g {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.at.view.ksc001.g.viewmodel.ScreenModel();
        
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            screenModel.loadGridTable(screenModel);
            $('#daterangepicker').find('input')[0].focus();
        });
    });
}
