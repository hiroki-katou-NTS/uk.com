module nts.uk.pr.view.qmm019.c {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);

            setTimeout(function(){
                $("#C1_7").focus();
            }, 200);
        });
    });
}