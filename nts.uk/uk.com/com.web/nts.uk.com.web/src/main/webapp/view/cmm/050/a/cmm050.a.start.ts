module nts.uk.com.view.cmm050.a {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function(){
              __viewContext.bind(screenModel);
            setTimeout(() => $('#A2_2').focus(), 500);
        });
    });
}
