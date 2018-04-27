module nts.uk.com.view.cmf005.c {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
          screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            $('#combo-box').focus();
        });
    });    
}