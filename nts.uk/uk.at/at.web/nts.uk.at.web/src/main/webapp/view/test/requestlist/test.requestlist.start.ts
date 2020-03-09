module nts.uk.at.view.test.requestlist {
	__viewContext.ready(function() {

        var screenModel = new nts.uk.at.view.test.requestlist.viewmodel.ScreenModel();
        screenModel.start().done(function() {
            __viewContext.bind(screenModel);
            
        });
    });
}