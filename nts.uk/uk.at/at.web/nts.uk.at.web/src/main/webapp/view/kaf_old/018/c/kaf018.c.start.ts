module nts.uk.at.view.kaf018_old.c {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {
        __viewContext.transferred.ifPresent(data => {
            nts.uk.ui.windows.setShared("KAF018C_PARAMS", data);
        });
        let screenModel = new kaf018_old.c.viewmodel.ScreenModel();        
        screenModel.startPage().done(function(){
            __viewContext.bind(screenModel);
            nts.uk.ui.block.clear();
            _.defer(() => {
                $('#extable').focus();
            });     
        })
    });
}