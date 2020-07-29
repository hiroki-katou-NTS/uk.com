module nts.uk.at.view.ksm015.c {
	__viewContext.ready(function() {
		
		 var viewModel = {
            viewmodelC: new viewmodel.ScreenModel(),
            viewmodelD: new nts.uk.at.view.ksm015.d.viewmodel.ScreenModel()
        };
		
		viewModel.viewmodelC.startPage().done(function() {
			__viewContext.bind(viewModel);
			viewModel.viewmodelC.reCalGridWidth();
			viewModel.viewmodelD.reCalGridWidth();
			viewModel.viewmodelD.startPage().done(function() {
			
			});
        });
		$(window).resize(function () {
			viewModel.viewmodelC.reCalGridWidth();
			viewModel.viewmodelD.reCalGridWidth();
		});
		
	});
}

