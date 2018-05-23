module nts.uk.at.view.kaf018.b {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {
        __viewContext.transferred.ifPresent(data => {
            nts.uk.ui.windows.setShared("KAF018BInput", data);
        });
        let screenModel = new kaf018.b.viewmodel.ScreenModel();        
        screenModel.startPage().done(function(){
            $("#fixed-table").focus(); 
            __viewContext.bind(screenModel);          
        })
    });
}