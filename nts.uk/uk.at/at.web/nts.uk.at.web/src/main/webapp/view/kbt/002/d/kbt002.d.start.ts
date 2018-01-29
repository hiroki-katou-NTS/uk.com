module nts.uk.at.view.kbt002.d {
    __viewContext.ready(function() {
        let screenModel = new viewmodel.ScreenModel();
        screenModel.start().done(function() {
            $.when(__viewContext.bind(screenModel)).done(()=>{
                $('#monthDaysList').focus();
            });
        });
    });
}