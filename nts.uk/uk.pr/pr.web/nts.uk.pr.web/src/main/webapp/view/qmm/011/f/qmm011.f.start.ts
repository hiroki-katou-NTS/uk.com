module nts.uk.pr.view.qmm011.f {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
            __viewContext.bind(screenModel);
        if(screenModel.canDelete()){
            $('#F1_6').focus();
        }else{
            $('#F1_7').focus();
        }
    });
}
