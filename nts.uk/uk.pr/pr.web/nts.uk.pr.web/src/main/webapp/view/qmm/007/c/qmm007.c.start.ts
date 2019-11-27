module nts.uk.pr.view.qmm007.c {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        __viewContext.bind(screenModel);
        if(screenModel.isFirst()){
            $('#C1_6').focus();
        } else {
            $('#C1_9').focus();
        }
    });
}