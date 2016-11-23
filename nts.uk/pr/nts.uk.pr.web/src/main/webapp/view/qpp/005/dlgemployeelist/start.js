/* global ko, nts, _, rootPath */
window.startPage = function() {

	// Screen model binding.
	var screenModel = new ScreenModel(carePlanCode, carePlanName, subjects);

	// binding screen Model
	ko.applyBindings(screenModel);

	// trigger start function
	screenModel.start();
};
