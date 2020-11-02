module nts.uk.at.view.kmk008.b {
    __viewContext.ready(function() {
        let screenModels = new kmk008.b.viewmodel.ScreenModel();

		const vm = screenModels;
		_.extend(window, {vm});

		// const kmk008b = nts.uk.at.view.kmk008.b;
		// _.extend(window, {kmk008b});

        screenModels.startPage().done(function() {
            __viewContext.bind(screenModels);
        });

    });
}