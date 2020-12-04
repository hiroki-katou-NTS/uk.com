module nts.uk.at.view.ksu003.a {
	__viewContext.ready(function() {
		let screenModel = new viewmodel.ScreenModel();
		screenModel.startPage().done(function() {
			__viewContext.bind(screenModel);
			nts.uk.ui.block.grayout();
			$(window).resize(function() {
				screenModel.setPositionButonDownAndHeightGrid();
			});
			nts.uk.ui.block.clear();
		});
		initEvent();
		initEvent2();
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

	function initEvent2(): void {
		//click btnA5
		$('#A3_4').ntsPopup({
			position: {
				my: 'left top',
				at: 'left bottom+3',
				of: $('#note-sort'),
			},
			showOnStart: false,
			dismissible: false
		});

		$('#note-sort').click(function() {
			$('#A3_4').ntsPopup("toggle");
		});

		$(".ui-igcombo-list").click(function() {
			$('#A3_4').ntsPopup("hide");
		});


	}
	$(window).resize(function() {
		let self = this;
		if (window.innerHeight < 700) {
			$("#note-sort").css({ "margin-left": 1022 + 'px !important' });
			$(".close").css({ "margin-right": 45 + 'px !important' });
			$("#note-color").css({ "margin-right": 57 + 'px !important' });
		} else {
			$("#note-sort").css({ "margin-left": 1043 + 'px ' });
			$("hr-row2").css({"width" : 1237 + 'px'});
		}
	});
}