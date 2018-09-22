module nts.uk.pr.view.qmm008.i {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.pr.view.qmm008.i.viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            $("#I1_5").focus();
        });   
      });

}