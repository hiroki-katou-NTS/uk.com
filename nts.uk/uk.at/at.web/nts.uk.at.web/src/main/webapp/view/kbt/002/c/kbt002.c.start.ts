module nts.uk.at.view.kbt002.c {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        
        screenModel.start().done(function() {
            $.when(__viewContext.bind(screenModel)).done(()=>{
                $('#startDate').focus();
            });
        });
    });
}