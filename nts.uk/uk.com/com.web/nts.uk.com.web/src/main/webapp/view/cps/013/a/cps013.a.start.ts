module nts.uk.com.view.cps013.a {  
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        nts.uk.ui.block.invisible();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        }).always(() => {
            nts.uk.ui.block.clear();
        });
    });
}