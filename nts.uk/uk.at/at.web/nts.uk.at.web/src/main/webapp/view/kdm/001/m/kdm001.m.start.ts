module nts.uk.at.view.kdm001.m {
    __viewContext.ready(function() {
        let screenModel =  new nts.uk.at.view.kdm001.m.viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
            _.defer(() => {$('#M8_2').focus()});
        });
    });
}
