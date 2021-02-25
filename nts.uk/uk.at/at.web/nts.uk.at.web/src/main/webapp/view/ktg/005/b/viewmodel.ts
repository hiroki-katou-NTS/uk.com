/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />



module nts.uk.at.ktg005.b {
	const requestUrl = {
		startScreenB: 'screen/at/ktg/ktg005/start_screen_b',
		regSetting: 'screen/at/ktg/ktg005/reg_setting_info'
	}

	@bean()
	export class ViewModel extends ko.ViewModel {
		screenData: KnockoutObservable<ScreenData> = ko.observable(new ScreenData());
		constructor() {
			super();
		}

		created(params: any) {

			let vm = this;
			vm.$blockui("invisible");
			vm.$ajax(requestUrl.startScreenB).done((screenBResult: IStartScreenBResult) => {
				vm.screenData().updateData(screenBResult);
			}).fail((error) => {
				this.$dialog.alert({ messageId: error.messageId });
			}).always(() => {
				this.$blockui("clear");
			});

		}

		closeDialog() {
			let vm = this;
			vm.$window.close();
		}

		updateSetting() {
			let vm = this,
				data: IStartScreenBResult = ko.toJS(vm.screenData());
			data.appSettings = _.map(data.appSettings, x => { return { displayType: x.displayType === true ? 1 : 0, item: x.item }; });
			vm.$validate()
				.then((valid: boolean) => {
					if (!valid) {
						return;
					}
					vm.$blockui("invisible");
					vm.$ajax(requestUrl.regSetting, data).done(() => {
						vm.$window.close();
					}).fail((error) => {
						this.$dialog.alert({ messageId: error.messageId });
					}).always(() => {
						this.$blockui("clear");
					});
				});
		}

	}


	export class ScreenData {

		// 名称
		topPagePartName: KnockoutObservable<String> = ko.observable("");

		// 申請状況の詳細設定
		appSettings: KnockoutObservableArray<AppSetting> = ko.observableArray([]);

		constructor(param?: IStartScreenBResult) {
			if (param) {
				this.topPagePartName(param.topPagePartName);
				this.appSettings(_.chain(param.appSettings).sortBy(['item']).map(x => { return new AppSetting(x); }).value());
			}
		}

		updateData(param: IStartScreenBResult) {
			this.topPagePartName(param.topPagePartName);
			if (!param.appSettings.length) {
				param.appSettings = [{ displayType: 0, item: 0 },
				{ displayType: 0, item: 1 },
				{ displayType: 0, item: 2 },
				{ displayType: 0, item: 3 },
				{ displayType: 0, item: 4 }]
			}
			this.appSettings(_.chain(param.appSettings).sortBy(['item']).map(x => { return new AppSetting(x); }).value());
			$("#item_table").ntsFixedTable({ height: 164, width: 350 });
		}

	}

	export class AppSetting {
		// 表示区分
		displayType: KnockoutObservable<boolean>;
		// 項目
		item: KnockoutObservable<number>;

		text: KnockoutObservable<string>;
		constructor(param: IDetailSettingAppStatus) {
			this.displayType = ko.observable(param.displayType === 1 ? true : false);
			this.item = ko.observable(param.item);
			this.text = ko.observable(this.getText(param.item));
		}

		getText(itemType: number) {
			const itemText = [
				{ itemType: ApplicationStatusWidgetItem.NumberOfApprovedCases, text: 'KTG005_3' }
				, { itemType: ApplicationStatusWidgetItem.NumberOfUnApprovedCases, text: 'KTG005_4' }
				, { itemType: ApplicationStatusWidgetItem.NumberOfDenial, text: 'KTG005_5' }
				, { itemType: ApplicationStatusWidgetItem.NumberOfRemand, text: 'KTG005_2' }
				, { itemType: ApplicationStatusWidgetItem.ApplicationDeadlineForThisMonth, text: 'KTG005_6' }];
			let item = _.find(itemText, ['itemType', itemType]),
				vm = new ko.ViewModel();

			return item ? vm.$i18n.text(item.text) : "";
		}
	}

	enum ApplicationStatusWidgetItem {
		//承認された件数
		NumberOfApprovedCases = 0,
		//未承認件数
		NumberOfUnApprovedCases = 1,
		//否認された件数
		NumberOfDenial = 2,
		//差し戻し件数
		NumberOfRemand = 3,
		//今月の申請締め切り日
		ApplicationDeadlineForThisMonth = 4

	}

	export interface IStartScreenBResult {
		// 名称
		topPagePartName: string;

		// 申請状況の詳細設定
		appSettings: Array<IDetailSettingAppStatus>;
	}

	export interface IDetailSettingAppStatus {
		// 表示区分
		displayType: any;

		// 項目
		item: number;
	}
}

