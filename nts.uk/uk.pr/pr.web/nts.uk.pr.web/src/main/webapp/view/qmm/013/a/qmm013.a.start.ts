module nts.uk.pr.view.qmm013.a {
    __viewContext.ready(function() {
        let screenModel = new viewModel.ScreenModel();
        
        screenModel.loadListData().done(function() {
            if(screenModel.unitPriceNameList().length > 0) {
                screenModel.currentCode(screenModel.unitPriceNameList()[0].code);
            }

            __viewContext.bind(screenModel);

            if(screenModel.checkCreate()) {
                $("#A3_2").focus();
            } else {
                $("#A3_3").focus();
            }
        });
    });
}
