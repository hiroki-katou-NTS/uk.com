interface StampSetting {
	buttonEmphasisArt: boolean;
	historyDisplayMethod: number;
	correctionInterval: number;
	textColor: string;
	backGroundColor: string;
	resultDisplayTime: number;
	pageLayouts: Array<PageLayout>;
}

interface PageLayout {
	pageNo: number;
	stampPageName: string;
	stampPageComment: string;
	stampPageCommentColor: string;
	buttonLayoutType: number;
	buttonSettings: Array<ButtonSetting>;
}

interface ButtonSetting {
	btnPositionNo: number;
	btnName: string;
	btnTextColor: string;
	btnBackGroundColor: string;
	btnReservationArt: number;
	changeHalfDay: boolean;
	goOutArt: number;
	setPreClockArt: number;
	changeClockArt: number;
	changeCalArt: number;
	usrArt: number;
	audioType: number;
	onClick: any;
}

interface ILoginInfo {
	employeeId: string;

}

interface IAuthResult {
	isSuccess: boolean;
	authType: number;
}


class Kdp004StampSetting {
	buttonEmphasisArt: boolean = true;
	historyDisplayMethod: number = 1;
	correctionInterval: number = 1;
	textColor: string = '#ffffff';
	backGroundColor: string = '#0D86D1';
	resultDisplayTime: number = 1;
	pageLayouts: Array<Kdp004PageLayout> = [new Kdp004PageLayout()];
}

class Kdp004PageLayout {
	pageNo: number = 1;
	stampPageName: string = '';
	stampPageComment: string = '';
	stampPageCommentColor: string = '';
	buttonLayoutType: number = 1;
	buttonSettings: Array<Kdp004ButtonSetting> = [new Kdp004ButtonSetting()];
}

class Kdp004ButtonSetting {
	btnPositionNo: number = 1;
	btnName: string = '';
	btnTextColor: string = '';
	btnBackGroundColor: string = '';
	btnReservationArt: number = 1;
	changeHalfDay: boolean = true;
	goOutArt: number = 1;
	setPreClockArt: number = 1;
	changeClockArt: number = 1;
	changeCalArt: number = 1;
	usrArt: number = 1;
	audioType: number = 1;
	onClick: any = null;

}

class StampTab {
	tabs: KnockoutObservableArray<NtsTabPanelModel> = ko.observableArray([]);
	selectedTab: KnockoutObservable<string> = ko.observable('');
	stampPageComment: KnockoutObservable<string> = ko.observable('');
	stampPageCommentColor: KnockoutObservable<string> = ko.observable('');
	layouts: KnockoutObservableArray<Kdp004PageLayout> = ko.observableArray([]);

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