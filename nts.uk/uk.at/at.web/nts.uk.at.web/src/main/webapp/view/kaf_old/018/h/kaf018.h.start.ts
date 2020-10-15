module nts.uk.at.view.kaf018_old.h {
    let __viewContext: any = window['__viewContext'] || {};
    __viewContext.ready(() => {
        let screenModel = new kaf018_old.h.viewmodel.ScreenModel();        
        screenModel.startPage().done(function(){
            __viewContext.bind(screenModel);          
             $("#H3_1_1").focus(); 
        })
    });
}