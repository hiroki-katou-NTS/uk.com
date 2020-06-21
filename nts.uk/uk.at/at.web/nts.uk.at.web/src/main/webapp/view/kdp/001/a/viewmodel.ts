/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

const requestUrl = {
	getEmployeeStampData: 'at/record/stamp/employment_system/get_employee_stamp_data',
	confirmUseOfStampInput: 'at/record/stamp/employment_system/confirm_use_of_stamp_input',
	registerStampInput: 'at/record/stamp/employment_system/register_stamp_input',
	getSettingStampInput: 'at/record/stamp/employment_system/get_setting_stamp_input',
	getOmissionContents: 'at/record/stamp/employment_system/get_omission_contents',
	getStampToSuppress: 'at/record/stamp/employment_system/get_stamp_to_suppress'
}

const stampTypes = [
	{ text: "KDP002_120", name: "氏名選択" },
	{ text: "KDP002_120", name: "指認証打刻" },
	{ text: "KDP002_120", name: "ICカード打刻" },
	{ text: "KDP002_120", name: "個人打刻" },
	{ text: "KDP002_120", name: "ポータル打刻" },
	{ text: "KDP002_120", name: "スマホ打刻" },
	{ text: "KDP002_120", name: "タイムレコーダー打刻" },
	{ text: "KDP002_121", name: "テキスト受入" },
	{ text: "KDP002_122", name: "リコー複写機打刻" }
]

const notUseMessage = [
	{ text: "Msg_1644", value: 1 },
	{ text: "Msg_1645", value: 2 },
	{ text: "Msg_1619", value: 3 }
]
@bean()
class KDP001AViewModel extends ko.ViewModel {

	systemDate: KnockoutObservable<any> = ko.observable(moment.utc());
	screenMode: KnockoutObservable<any> = ko.observable();
	stampDatas: KnockoutObservableArray<IStampInfoDisp> = ko.observableArray([
		{
			stampDatetime: moment.utc().toDate(),
			stampAtr: '予約',
			stamp: {
				relieve: { stampMeans: { value: 1, name: '指認証打刻' } }
			}
		},
		{
			stampDatetime: moment.utc().toDate(),
			stampAtr: '予約',
			stamp: {
				relieve: { stampMeans: { value: 2, name: 'ICカード打刻' } }
			}
		},
		{
			stampDatetime: moment.utc().toDate(),
			stampAtr: '予約',
			stamp: {
				relieve: { stampMeans: { value: 3, name: '個人打刻' } }
			}
		},
		{
			stampDatetime: moment.utc().toDate(),
			stampAtr: '予約',
			stamp: {
				relieve: { stampMeans: { value: 4, name: 'ポータル打刻' } }
			}
		},
		{
			stampDatetime: moment.utc().toDate(),
			stampAtr: '予約',
			stamp: {
				relieve: { stampMeans: { value: 5, name: 'リコー複写機打刻' } }
			}
		},
		{
			stampDatetime: moment.utc().toDate(),
			stampAtr: '予約',
			stamp: {
				relieve: { stampMeans: { value: 6, name: 'スマホ打刻 ' } }
			}
		},
		{
			stampDatetime: moment.utc().toDate(),
			stampAtr: '予約',
			stamp: {
				relieve: { stampMeans: { value: 7, name: 'テキスト受入' } }
			}
		},
		{
			stampDatetime: moment.utc().toDate(),
			stampAtr: '予約',
			stamp: {
				relieve: { stampMeans: { value: 8, name: '氏名選択' } }
			}
		}]);
	usedSatus: KnockoutObservable<number> = ko.observable(0);
	settingDateTimeColor: KnockoutObservable<any> = ko.observable({
		textColor: '#ccccff',
		backgroundColor: '#0033cc'
	});
	countTime: KnockoutObservable<number> = ko.observable(0);
	settingCountTime: KnockoutObservable<number> = ko.observable(60);
	buttonSetting: KnockoutObservable<StampToSuppress> = ko.observable(new StampToSuppress());
	buttons: KnockoutObservableArray<IButtonSettingsDto> = ko.observableArray([
		{
			buttonPositionNo: 1,
			buttonDisSet: {

				buttonNameSet: {
					textColor: '#f3f3f3',
					buttonName: 'test'
				},
				/** 背景色 */
				backGroundColor: '#3e7db6'
			}
		},
		{
			buttonPositionNo: 2,
			buttonDisSet: {

				buttonNameSet: {
					textColor: '#f3f3f3',
					buttonName: 'test2'
				},
				/** 背景色 */
				backGroundColor: '#3e7db6'
			}
		},
		{
			buttonPositionNo: 3,
			buttonDisSet: {

				buttonNameSet: {
					textColor: '#f3f3f3',
					buttonName: 'test3'
				},
				/** 背景色 */
				backGroundColor: '#3e7db6'
			}
		},
		{
			buttonPositionNo: 4,
			buttonDisSet: {

				buttonNameSet: {
					textColor: '#f3f3f3',
					buttonName: 'test4'
				},
				/** 背景色 */
				backGroundColor: '#3e7db6'
			}
		}
	]);



	created(params: any) {

		let vm = this;

		let url = $(location).attr('search');
		let urlParam: string = url.split("=")[1];

		vm.screenMode(!!urlParam ? urlParam : null);

		vm.$ajax(requestUrl.confirmUseOfStampInput, { stampMeans: 4 }).then((result) => {

			vm.usedSatus(result.used);
			vm.systemDate(moment.utc(result.systemDate));


			vm.$ajax(requestUrl.getSettingStampInput).then((setting: IStampSetting) => {

				if (!!setting) {
					let buttons: Array<IButtonSettingsDto> = [];

					if (_.has(setting, 'portalStampSettings.buttonSettings')) {
						buttons = _.chain(setting.portalStampSettings.buttonSettings)
							.uniqBy('buttonPositionNo')
							.filter(function(o) { return 1 <= o.buttonPositionNo && o.buttonPositionNo <= 4; })
							.sortBy(['buttonPositionNo'])
							.value();
					}

					(!vm.showButtonGoOutAndBack() && buttons.length > 2) ? buttons.splice(2) : buttons.splice(4);

					vm.buttons(buttons);

					if (_.has(setting, 'portalStampSettings.displaySettingsStampScreen.settingDateTimeColor')) {
						vm.settingDateTimeColor(setting.portalStampSettings.displaySettingsStampScreen.settingDateTimeColor);
					}

					if (_.has(setting, 'portalStampSettings.displaySettingsStampScreen.resultDisplayTime')) {
						vm.settingCountTime(setting.portalStampSettings.displaySettingsStampScreen.resultDisplayTime);
					}

					setInterval(() => {
						if (vm.countTime() == vm.settingCountTime()) {
							vm.systemDate(moment.utc());

							this.$ajax(requestUrl.getStampToSuppress).then((data) => {
								vm.buttonSetting(new StampToSuppress(data));
								vm.countTime(0);
							});

							console.log('Reset Time');
						} else {
							vm.systemDate(vm.systemDate().add(1, 'seconds'));
							vm.countTime(vm.countTime() + 1);
							console.log('Add Time: ' + vm.countTime());
						}
					}, 1000);
				}
			});

			let query = { startDate: moment().utc().add(-3, 'days').format("YYYY/MM/DD"), endDate: moment().utc().format("YYYY/MM/DD") };


			vm.$ajax(requestUrl.getEmployeeStampData, query).then((data: Array<Array<IStampInfoDisp>>) => {

				let items = _.flatMap(data, 'listStampInfoDisp');

				items = _.sortBy(items, [function(o) { return moment.utc(o.stampDatetime); }]).reverse();

				vm.stampDatas(items || []);

				if (vm.stampDatas().length && !vm.isScreenCD()) {

					if (vm.screenMode() == 'a' || vm.screenMode() == 'b') {

						$("#fixed-table").ntsFixedTable({ height: 53, width: 215 });
					} else {

						if (!vm.screenMode()) {

							$("#fixed-table").ntsFixedTable({ height: 89, width: 280 });
						}
					}

				}
			});

			this.$ajax(requestUrl.getStampToSuppress).then((data) => {
				vm.buttonSetting(new StampToSuppress(data));
			});
		});
	}

	public checkEnableButton(data: IButtonSettingsDto) {
		let vm = this,
			buttonNo = data.buttonPositionNo,
			setting = vm.buttonSetting();

		if (buttonNo == 1) {
			return setting.goingToWork();
		}
		if (buttonNo == 2) {
			return setting.departure();
		}
		if (buttonNo == 3) {
			return setting.goOut();
		}
		if (buttonNo == 4) {
			return setting.turnBack();
		}
		return false;

	}

	public getNotUseMessage() {
		let vm = this,
			messageItem = _.find(notUseMessage, ['value', vm.usedSatus()]);

		return messageItem ? this.$i18n.message(messageItem.text, [this.$i18n.text('KDP001_1')]) : '';

	}

	mounted() {
		const vm = this;
	}

	public getStampTime(data: IStampInfoDisp) {
		let vm = this,
			character = '';

		if (_.has(data, 'stamp.relieve.stampMeans')) {

			let item = _.find(stampTypes, ['name', data.stamp.relieve.stampMeans]);
			character = item ? vm.$i18n.text(item.text) : '';
		}

		return character + ' ' + moment.utc(data.stampDatetime).format("HH:mm");
	}

	public convertToShortMDW(date: Date) {
		return date ? moment.utc(date).format("MM/DD(ddd)") : '';
	}

	public getSystemDate() {
		let vm = this;

		return vm.systemDate().format("YYYY/MM/DD(ddd)");
	}

	public getSystemTime() {
		let vm = this,
			time = vm.systemDate().format("HH:mm");


		return time;
	}

	public stamp(vm, data) {
		
		let cmd: IRegisterStampInputCommand = {
			datetime: vm.systemDate(),
			buttonPositionNo: data.buttonPositionNo,
			refActualResults: {
				cardNumberSupport: null,
				workLocationCD: null,
				workTimeCode: null,
				overtimeDeclaration: null
			}

		};

		if (data.buttonPositionNo != 3 && data.buttonPositionNo != 4) {
			this.$ajax(requestUrl.registerStampInput, cmd).then(() => {

			});
		} else {
			switch (data.buttonPositionNo) {
				case 3:
					vm.openDialogC();
					break;
				case 4:
					vm.openDialogB();
					break;
			}

		}

	}

	public openDialogB() {

		let vm = this;

		nts.uk.ui.windows.sub.modal('/view/cps/002/b/index.xhtml').onClosed(function(): any {
			vm.$ajax(requestUrl.getEmployeeStampData).then((data) => {
				vm.stampDatas(data.listStampInfoDisp);

			});
		});
	}

	public openDialogC() {
		let vm = this;

		nts.uk.ui.windows.sub.modal('/view/cps/002/c/index.xhtml').onClosed(function(): any {
			vm.$ajax(requestUrl.getEmployeeStampData).then((data) => {
				vm.stampDatas(data.listStampInfoDisp);

			});
		});
	}

	public isScreenCD() {
		let vm = this;
		return vm.screenMode() == 'c' || vm.screenMode() == 'd';
	}

	public showButtonGoOutAndBack() {
		let vm = this;
		return vm.screenMode() == 'b' || vm.screenMode() == 'c';
	}

	public showTable() {
		let vm = this;
		console.log(vm.screenMode());
		return vm.screenMode() != 'a' && vm.screenMode() != 'b';
	}
	public isWidget() {
		let vm = this;
		return !vm.screenMode();
	}



}

interface IStampToSuppress {
	/**
	 * 出勤
	 */
	goingToWork: boolean;
	/**
	 * 退勤
	 */
	departure: boolean;
	/**
	 * 外出
	 */
	goOut: boolean;
	/**
	 * 戻り
	 */
	turnBack: boolean;
}

class StampToSuppress {

	goingToWork: KnockoutObservable<boolean> = ko.observable(false);
	departure: KnockoutObservable<boolean> = ko.observable(false);
	goOut: KnockoutObservable<boolean> = ko.observable(false);
	turnBack: KnockoutObservable<boolean> = ko.observable(false);
	constructor(data?) {
		if (data) {
			this.goingToWork(data.goingToWork || false);
			this.goOut(data.goOut || false);
			this.departure(data.departure || false);
			this.turnBack(data.turnBack || false);
		}
	}

}

interface IStampSetting {
	portalStampSettings: IPortalStampSettingsDto;
}

interface IPortalStampSettingsDto {

	// 打刻画面の表示設定
	displaySettingsStampScreen: IDisplaySettingsStampScreenDto;

	// 打刻ボタン設定
	buttonSettings: Array<IButtonSettingsDto>;
}

interface IDisplaySettingsStampScreenDto {
	resultDisplayTime: number
	serverCorrectionInterval: number
	/** 打刻画面の日時の色設定 */
	settingDateTimeColor: ISettingDateTimeColorOfStampScreenDto;
}

interface ISettingDateTimeColorOfStampScreenDto {
	/** 文字色 */
	textColor: string;

	/** 背景色 */
	backgroundColor: string;
}

interface IButtonSettingsDto {
	/** ボタン位置NO */
	buttonPositionNo: number;

	/** ボタンの表示設定 */
	buttonDisSet: IButtonDisSetDto;

}
interface IButtonDisSetDto {
	/** ボタン名称設定 */
	buttonNameSet: IButtonNameSetDto;

	/** 背景色 */
	backGroundColor: string;
}

interface IButtonNameSetDto {
	/** 文字色 */
	textColor: string;

	/** ボタン名称 */
	buttonName: string;
}
interface IStampInfoDisp {
	/**
	 * 打刻日時
	 */
	stampDatetime: Date;
	/**
	 * 打刻区分
	 */
	stampAtr: string;
	/**
	 * 打刻
	 */
	stamp: IStamp;
}

interface IStamp {
	/**
	 * 打刻する方法
	 */
	relieve: IRelieve;
}

interface IRelieve {
	/** 打刻手段*/
	stampMeans: IStampMeans;
}

interface IStampMeans {
	value: number;

	name: string;
}
interface IRegisterStampInputCommand {
	/**
	 * 打刻日時
	 */
	datetime: Date;

	/**
	 * ボタン位置NO
	 */

	buttonPositionNo: number;
	/**
	 * 実績への反映内容
	 */

	refActualResults: IRefectActualResultCommand;
}

interface IRefectActualResultCommand {

	cardNumberSupport: string;

	/**
	 * 打刻場所コード 勤務場所コード old
	 */

	workLocationCD: string;

	/**
	 * 就業時間帯コード
	 */

	workTimeCode: string;

	/**
	 * 時間外の申告
	 */

	overtimeDeclaration: IOvertimeDeclarationComamnd;
}

interface IOvertimeDeclarationComamnd {
	overTime: number;

	/**
	 * 時間外深夜時間
	 */
	overLateNightTime: number;
}