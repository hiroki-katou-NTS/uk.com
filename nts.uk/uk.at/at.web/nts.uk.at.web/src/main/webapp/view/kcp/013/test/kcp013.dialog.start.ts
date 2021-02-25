module nts.uk.at.view.kcp013.dialog.test {
	__viewContext.ready(function() {
		var screenModel  = new viewmodel.ScreenModel();
		screenModel.startPage().done(function() {
			__viewContext.bind(screenModel)
		})
	})
}