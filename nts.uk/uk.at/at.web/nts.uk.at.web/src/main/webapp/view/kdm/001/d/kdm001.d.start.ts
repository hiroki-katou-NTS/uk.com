module nts.uk.at.view.kdm001.d {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.at.view.kdm001.d.viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
            _.defer(() => {$('#D4').focus()});
    });
}