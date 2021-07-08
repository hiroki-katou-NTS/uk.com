module nts.uk.at.view.kdp010.g {
	__viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
		screenModel.start().done(() => {
			__viewContext.bind(screenModel);	
		});
    });
}