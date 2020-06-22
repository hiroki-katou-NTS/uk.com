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

const Mode = {
	Personal: 1, // 個人
	Shared: 2  // 共有 
}

const daysColor = [
	{ day: 0, color: '#FF0000' },
	{ day: 6, color: '#0000FF' }
]
@bean()
class KDP001AViewModel extends ko.ViewModel {

	systemDate: KnockoutObservable<any> = ko.observable(moment.utc());
	screenMode: KnockoutObservable<any> = ko.observable();
	stampDatas: KnockoutObservableArray<IStampInfoDisp> = ko.observableArray([]);
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
		this.$blockui("invisible");
		vm.$ajax(requestUrl.confirmUseOfStampInput, { stampMeans: 4 }).then((result) => {
			this.$blockui("clear");
			vm.usedSatus(result.used);
			vm.systemDate(moment.utc(result.systemDate));

			this.$blockui("invisible");
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

							}).always(() => {
								vm.countTime(0);
							});

						} else {

							vm.systemDate(vm.systemDate().add(1, 'seconds'));
							vm.countTime(vm.countTime() + 1);

						}
					}, 1000);
				}
			}).always(() => {
				this.$blockui("clear");
			});;

			let query = { startDate: moment().utc().add(-3, 'days').format("YYYY/MM/DD"), endDate: moment().utc().format("YYYY/MM/DD") };
			this.$blockui("invisible");
			vm.$ajax(requestUrl.getEmployeeStampData, query).then((data: Array<Array<IStampInfoDisp>>) => {
				this.$blockui("clear");
				let items = _.flatMap(data, 'listStampInfoDisp') as any[];

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
			}).always(() => {
				this.$blockui("clear");
			});;
			vm.getStampToSuppress();

		}).always(() => {
			this.$blockui("clear");
		});
	}

	public getDateColor(data: IStampInfoDisp) {

		let day = moment.utc(data.stampDatetime).day(),

			dayColor = _.find(daysColor, ['day', day]);

		return dayColor ? dayColor.color : '#000000';
	}

	public getStampToSuppress() {
		let vm = this;
		vm.$blockui("invisible");
		this.$ajax(requestUrl.getStampToSuppress).then((data) => {
			vm.$blockui("clear");
			vm.buttonSetting(new StampToSuppress(data));
		});
	}

	public toTopPage() {
		let vm = this;
		vm.$jump('com', '/view/ccg/008/a/index.xhtml');
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

	public getStampTime(data: IStampInfoDisp) {
		let vm = this,
			character = '';

		if (_.has(data, 'stamp.relieve.stampMeans')) {

			let item = _.find(stampTypes, ['name', data.stamp.relieve.stampMeans]);
			character = item ? vm.$i18n.text(item.text) : '';
		}

		return character + ' ' + moment.utc(data.stampDatetime).format("HH:mm");
	}

	public convertToShortMDW(data: IStampInfoDisp) {

		return _.has(data, 'stampDatetime') ? moment.utc(data.stampDatetime).format("MM/DD(ddd)") : '';
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

	public stamp(vm: KDP001AViewModel, data) {

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

		this.$blockui("invisible");
		this.$ajax(requestUrl.registerStampInput, cmd).then((result) => {
			switch (data.buttonPositionNo) {
				case 1:
				case 2:
					vm.reLoadStampDatas();
					vm.getStampToSuppress();
					break;
				case 3:
					vm.openDialogC(result);
					break;
				case 4:
					vm.openDialogB(result);
					break;
			}
		}).fail((error) => {
			this.$dialog.alert({ messageId: error.messageId });
		}).always(() => {
			this.$blockui("clear");
		});
	}

	public openDialogB(dateParam) {

		let vm = this;

		nts.uk.ui.windows.setShared("infoEmpToScreenB", {
			employeeId: vm.$user.employeeId,
			employeeCode: vm.$user.employeeCode,
			mode: Mode.Personal,
		});
		nts.uk.ui.windows.sub.modal('/view/kdp/002/b/index.xhtml').onClosed(function(): any {
			vm.$blockui("invisible");
			vm.$ajax(requestUrl.getOmissionContents, { pageNo: 4, buttonDisNo: 4 }).then((res) => {
				if (res && res.dailyAttdErrorInfos && res.dailyAttdErrorInfos.length > 0) {

					vm.$window.storage('KDP010_2T', res);

					vm.$window.modal('/view/kdp/002/t/index.xhtml').then(function(): any {

						vm.$window.storage('KDP010_T')
							.then((returnData: any) => {
								if (!returnData.isClose && returnData.errorDate) {

									let transfer = returnData.btn.transfer;
									nts.uk.request.jump(returnData.btn.screen, transfer);
								}
							});
						vm.reLoadStampDatas();
						vm.getStampToSuppress();
					});
				} else {
					vm.reLoadStampDatas();
					vm.getStampToSuppress();
				}
			}).always(() => {
				vm.$blockui("hide");
			});
		});
	}

	public openDialogC(dateParam) {
		let vm = this;

		nts.uk.ui.windows.setShared('KDP010_2C', []);

		nts.uk.ui.windows.setShared("infoEmpToScreenC", {
			employeeId: vm.$user.employeeId,
			employeeCode: vm.$user.employeeCode,
			mode: Mode.Personal,
			stampDate: dateParam
		});

		vm.$window.modal('/view/kdp/002/c/index.xhtml').then(function(): any {
			vm.$blockui("invisible");
			vm.$ajax(requestUrl.getOmissionContents, { pageNo: 4, buttonDisNo: 3 }).then((res) => {
				if (res && res.dailyAttdErrorInfos && res.dailyAttdErrorInfos.length > 0) {

					vm.$window.storage('KDP010_2T', res);

					vm.$window.modal('/view/kdp/002/t/index.xhtml').then(function(): any {

						vm.$window.storage('KDP010_T')
							.then((returnData: any) => {
								if (!returnData.isClose && returnData.errorDate) {

									let transfer = returnData.btn.transfer;
									nts.uk.request.jump(returnData.btn.screen, transfer);
								}
							});
						vm.reLoadStampDatas();
						vm.getStampToSuppress();
					});
				} else {
					vm.reLoadStampDatas();
					vm.getStampToSuppress();
				}
			}).always(() => {
				vm.$blockui("clear");
			});
		});
	}

	public reLoadStampDatas() {
		let vm = this;
		let query = { startDate: moment().utc().add(-3, 'days').format("YYYY/MM/DD"), endDate: moment().utc().format("YYYY/MM/DD") };
		vm.$blockui("invisible");
		vm.$ajax(requestUrl.getEmployeeStampData, query).then((data: Array<Array<IStampInfoDisp>>) => {
			vm.$blockui("clear");
			let items = _.flatMap(data, 'listStampInfoDisp') as any[];

			items = _.sortBy(items, [function(o) { return moment.utc(o.stampDatetime); }]).reverse();

			vm.stampDatas(items || []);
		}).always(() => {
			vm.$blockui("clear");
		});;
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

	goingToWork: KnockoutObservable<boolean> = ko.observable(true);
	departure: KnockoutObservable<boolean> = ko.observable(true);
	goOut: KnockoutObservable<boolean> = ko.observable(true);
	turnBack: KnockoutObservable<boolean> = ko.observable(true);
	constructor(data?) {
		if (data) {
			this.goingToWork(data.goingToWork || true);
			this.goOut(data.goOut || true);
			this.departure(data.departure || true);
			this.turnBack(data.turnBack || true);
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