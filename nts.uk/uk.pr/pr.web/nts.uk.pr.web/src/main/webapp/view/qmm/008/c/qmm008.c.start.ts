module nts.uk.pr.view.qmm008.c {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.pr.view.qmm008.c.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#B1_5").focus();
        });   
      });

}