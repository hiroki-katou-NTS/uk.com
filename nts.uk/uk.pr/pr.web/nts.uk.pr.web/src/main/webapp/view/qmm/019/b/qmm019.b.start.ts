module nts.uk.pr.view.qmm019.b {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
        });

        setTimeout(function(){
            $(".ui-iggrid-headertext").addClass("limited-label");
            $("#B1_6").focus();
        }, 200);
    });
}