module nts.uk.at.view.kmf002.a {
    import blockUI = nts.uk.ui.block;
    
    __viewContext.ready(function() {
        let mainTab = new viewmodel.ScreenModel();
        mainTab.startPage(0).done(function(screenModel){
            __viewContext.bind(screenModel);
            $ ("#managePubHD").focus();
        });
    });
}
