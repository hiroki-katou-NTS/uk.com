module nts.uk.at.view.ksm015.c {
	__viewContext.ready(function() {
		
		 var viewModel = {
            viewmodelC: new viewmodel.ScreenModel(),
            viewmodelD: new nts.uk.at.view.ksm015.d.viewmodel.ScreenModel()
        };
		
		viewModel.viewmodelC.startPage().done(function() {
			__viewContext.bind(viewModel);
			viewModel.viewmodelC.reCalGridWidth();
			$('#add-new-shift').focus();
			$('#add-new-shift').focus();
			viewModel.viewmodelD.startPage().done(function() {
			$('#add-new-shift').focus();
			$('#add-new-shift').focus();
			$('#cre-shift').focus();
			$('#cre-shift').focus();
			});
        });
			$('#add-new-shift').focus();
			$('#add-new-shift').focus();
	/*	$(window).resize(function () {
			viewModel.viewmodelC.reCalGridWidth();
			viewModel.viewmodelD.reCalGridWidth();
		});*/
		
	});
}

