module nts.uk.at.view.kaf018.d {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {
        __viewContext.transferred.ifPresent(data => {
            nts.uk.ui.windows.setShared("KAF018D_PARAMS", data);
        });
        let screenModel = new kaf018.d.viewmodel.ScreenModel();        
        screenModel.startPage().done(function(){
            __viewContext.bind(screenModel);          
            // $("#H3_1_1").focus(); 
        })
    });
}