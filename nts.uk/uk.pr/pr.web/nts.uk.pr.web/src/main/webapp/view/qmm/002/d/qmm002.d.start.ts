module nts.uk.pr.view.qmm002.d {
    __viewContext.ready(function() {
            var screenModel = new viewmodel.ScreenModel();
            screenModel.startPage().done(function() {
                __viewContext.bind(screenModel);
                if (screenModel.updateMode())
                    $("#D3_3").focus();
                else 
                    $("#D3_2").focus();
            });
    });
}