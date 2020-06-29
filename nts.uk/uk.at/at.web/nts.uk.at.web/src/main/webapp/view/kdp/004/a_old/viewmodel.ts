/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

const url = {
	startPage: 'at/record/stamp/management/personal/startPage',
	getStampData: 'at/record/stamp/management/personal/stamp/getStampData',
	getTimeCard: 'at/record/stamp/management/personal/stamp/getTimeCard',
	stampInput: 'at/record/stamp/management/personal/stamp/input',
	getError: 'at/record/stamp/management/personal/getDailyError'
}

@bean()
class KDP004AViewModel extends ko.ViewModel {
	systemDate: KnockoutObservable<any> = ko.observable(moment.utc());
	
	stampSetting: KnockoutObservable<StampSetting> = ko.observable();
	
	stampToSuppress: KnockoutObservable<StampToSuppress> = ko.observable();
	
	stampResultDisplay: KnockoutObservable<IStampResultDisplay> = ko.observable();

	stampTab: KnockoutObservable<StampTab> = ko.observable(new StampTab());

	isUsed: KnockoutObservable<boolean> = ko.observable(true);

	layoutsSetting: KnockoutObservable<any> = ko.observable(
		{ layout1Title: ko.observable('layout-1'), layout1Visible: ko.observable(true) }
	);

	dateText() {
		let vm = this;
		return vm.systemDate().format("YYYY/MM/DD(dddd)");


	}

	public getNotUseMessage() {
		let vm = this;

		return vm.$i18n.message('Msg_1619', [this.$i18n.text('KDP002_3')]);

	}



	timeText() {
		let vm = this;
		return vm.systemDate().format("HH:ss");
	}

	created(params: any) {
		let vm = this;
		this.$ajax(url.startPage).then((res) => {

			vm.stampSetting(res.stampSetting);
			vm.stampTab().bindData(res.stampSetting.pageLayouts);
			let stampToSuppress = res.stampToSuppress;
			stampToSuppress.isUse = res.stampSetting.buttonEmphasisArt;
			vm.stampToSuppress(stampToSuppress);
			vm.stampResultDisplay(res.stampResultDisplay);

		});
	}

	public getPageLayout(pageNo: number) {
		let self = this;
		let layout = _.find(self.stampTab().layouts(), (ly) => { return ly.pageNo === pageNo });

		if (layout) {
			let btnSettings = layout.buttonSettings;
			btnSettings.forEach(btn => {
				btn.onClick = self.clickBtn1;
			});
			layout.buttonSettings = btnSettings;
		}

		return layout;
	}

	public clickBtn1(vm, layout) {
		let button = this;
		nts.uk.request.syncAjax("com", "server/time/now/").done((res) => {
			let data = {
				datetime: moment.utc(res).format('YYYY/MM/DD HH:mm:ss'),
				authcMethod: 0,
				stampMeans: 3,
				reservationArt: button.btnReservationArt,
				changeHalfDay: button.changeHalfDay,
				goOutArt: button.goOutArt,
				setPreClockArt: button.setPreClockArt,
				changeClockArt: button.changeClockArt,
				changeCalArt: button.changeCalArt
			};

			service.stampInput(data).done((res) => {
				if (vm.stampResultDisplay().notUseAttr == 1 && (button.changeClockArt == 1 || button.changeClockArt == 9)) {
					vm.openScreenC(button, layout);
				} else {
					vm.openScreenB(button, layout);
				}
			}).fail((res) => {
				nts.uk.ui.dialog.alertError({ messageId: res.messageId });
			});
		});
	}
}


class StampTab {
	tabs: KnockoutObservableArray<any> = ko.observableArray([]);
	selectedTab: KnockoutObservable<string> = ko.observable('tab-1');
	stampPageComment: KnockoutObservable<string> = ko.observable('');
	stampPageCommentColor: KnockoutObservable<string> = ko.observable('');
	layouts: KnockoutObservableArray<PageLayout> = ko.observableArray([]);

	constructor() {
		let self = this;
		self.selectedTab.subscribe((val) => {
			let stampTab = _.find(self.tabs(), (tab) => { return tab.id == val });
			if (stampTab) {
				self.stampPageComment(stampTab.stampPageComment);
				self.stampPageCommentColor(stampTab.color);
			}
		});
	}

	public bindData(layouts: Array<PageLayout>) {
		let self = this;
		let tabs = [];
		self.layouts(layouts);
		for (let idx = 1; idx <= 5; idx++) {
			let layout = _.find(layouts, (ly) => { return ly.pageNo === idx });
			if (layout) {
				tabs.push({
					id: 'tab-' + idx,
					title: layout ? layout.stampPageName : '',
					content: layout ? '.tab-content-' + layout.pageNo : '',
					stampPageComment: layout.stampPageComment,
					color: layout.stampPageCommentColor,
					enable: ko.observable(true),
					visible: ko.observable(true)
				});
			}
		};
		self.tabs(tabs);
		self.selectedTab('tab-' + layouts[0].pageNo);
		self.selectedTab.valueHasMutated();
	}

}

