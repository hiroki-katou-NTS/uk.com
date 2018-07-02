module nts.uk.at.view.kaf005.a {
    let transferData: any;
    __viewContext.ready(function() {
        transferData = __viewContext.transferred.value;
        let screenModel = new nts.uk.at.view.kaf010.a.viewmodel.ScreenModel(transferData);
        __viewContext.bind(screenModel);
    });
}