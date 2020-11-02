module nts.uk.at.view.ksu003.a {
	__viewContext.ready(function() {
		let screenModel = new viewmodel.ScreenModel();
		nts.uk.ui.block.grayout();
		screenModel.startPage().done(function() {
			__viewContext.bind(screenModel);
			$(window).resize(function() {
				screenModel.setPositionButonDownAndHeightGrid ();
			});
			nts.uk.ui.block.clear();
		});
		initEvent()
	});
	function initEvent(): void {
		//click btnA5
		$('#A5_1').ntsPopup({
			position: {
				my: 'left top',
				at: 'left bottom+3',
				of: $('#note')
			}
		});

		$('#note').click(function() {
			$('#A5_1').ntsPopup("toggle");
		});
	}
	/*	$(window).resize(function () {
				$("#extable").exTable("setHeight", 525);
						let heightExtable = $("#extable").height();
						let margintop = 525 - 52;
						$(".toDown").css({ "margin-top": margintop + 'px' });
			});*/
}