module nts.uk.at.view.kaf001.b {
    let transferData: any;
    __viewContext.ready(function() {
        transferData = __viewContext.transferred.value;
        var screenModel = new nts.uk.at.view.kaf001.b.viewmodel.ScreenModel(transferData);
        __viewContext.bind(screenModel);  
    });
}
