module nts.uk.at.view.kdm001.i {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.at.view.kdm001.i.viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
            _.defer(() => {$('#I4').focus()});
    });
}