module nts.uk.at.view.kdm001.l {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.at.view.kdm001.l.viewmodel.ScreenModel();
        screenModel.startPage().done(() => {
            __viewContext.bind(screenModel);
            _.defer(() => {$('#L8_2').focus()});
        });
    });
}
