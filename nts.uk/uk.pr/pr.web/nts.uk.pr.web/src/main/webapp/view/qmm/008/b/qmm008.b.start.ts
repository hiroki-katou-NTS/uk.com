module nts.uk.pr.view.qmm008.b{
    __viewContext.ready(function() {
        let screenModel = new nts.uk.pr.view.qmm008.b.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#C1_5").focus();
        });  
        
    });
}
