module nts.uk.com.view.kal001.d {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
       screenModel.start().done(function(){
           __viewContext.bind(screenModel);
        });
        
    });
}