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
	{ text: "KDP002_121", name: "スマホ打刻" },
	{ text: "KDP002_122", name: "タイムレコーダー打刻" }
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

const DEFAULT_GRAY = '#E8E9EB';
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
	resultDisplayTime: KnockoutObservable<number> = ko.observable(10);

	buttonSetting: KnockoutObservable<IStampToSuppress> = ko.observable({
		goingToWork: true,
		departure: true,
		goOut: true,
		turnBack: true
	} as IStampToSuppress);

	stampResultDisplay: KnockoutObservable<IStampResultDisplay> = ko.observable({
		companyId: "000000000000-000",
		displayItemId: [653, 651, 652],
		notUseAttr: 0
	});
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

	constructor() {
		super();
		let vm = this;
		vm.buttonSetting.subscribe((data: StampToSuppress) => {
			let vm = this;
		});
	}

	created(params: any) {

		let vm = this;

		let url = $(location).attr('search');
		let urlParam: string = url.split("=")[1];

		vm.screenMode(!!urlParam ? urlParam : null);
		this.$blockui("invisible");
		vm.$ajax(requestUrl.confirmUseOfStampInput, { stampMeans: 4 }).then((result) => {
			this.$blockui("clear");
			vm.usedSatus(result.used);
			vm.systemDate(moment(vm.$date.now()));

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

					if (_.has(setting, 'stampResultDisplayDto')) {
						vm.stampResultDisplay(setting.stampResultDisplayDto);
					}

					if (_.has(setting, 'portalStampSettings.displaySettingsStampScreen.settingDateTimeColor')) {
						vm.settingDateTimeColor(setting.portalStampSettings.displaySettingsStampScreen.settingDateTimeColor);
					}

					if (_.has(setting, 'portalStampSettings.displaySettingsStampScreen.serverCorrectionInterval')) {
						vm.settingCountTime(setting.portalStampSettings.displaySettingsStampScreen.serverCorrectionInterval);
					}
					if (_.has(setting, 'portalStampSettings.displaySettingsStampScreen.resultDisplayTime')) {
						vm.resultDisplayTime(setting.portalStampSettings.displaySettingsStampScreen.resultDisplayTime);
					}





					setInterval(() => {
						if (vm.countTime() == vm.settingCountTime()) {
							vm.systemDate(moment(vm.$date.now()));

							this.$ajax(requestUrl.getStampToSuppress).then((data: IStampToSuppress) => {

								vm.buttonSetting(data);

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

			this.$blockui("invisible");
			vm.$ajax(requestUrl.getEmployeeStampData).then((data: Array<Array<IStampInfoDisp>>) => {
				this.$blockui("clear");
				let items = _(data).flatMap('stampRecords').value() as any[];

				items = _.orderBy(items, ['stampTimeWithSec'], ['desc']);

				vm.stampDatas(items || []);

				if (!vm.isScreenCD()) {

					if (vm.screenMode() == 'a' || vm.screenMode() == 'b') {

						$("#fixed-table").ntsFixedTable({ height: 53, width: 215 });
					} else {

						if (!vm.screenMode()) {

							$("#fixed-table").ntsFixedTable({ height: 85, width: 200 });
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

		let day = moment.utc(data.stampTimeWithSec).day(),

			dayColor = _.find(daysColor, ['day', day]);

		return dayColor ? dayColor.color : '#000000';
	}

	public getStampToSuppress() {
		let vm = this;
		vm.$blockui("invisible");
		this.$ajax(requestUrl.getStampToSuppress).then((data: IStampToSuppress) => {
			vm.$blockui("clear");
			vm.buttonSetting(data);
		});
	}

	public toTopPage() {
		let vm = this;
		vm.$jump('com', '/view/ccg/008/a/index.xhtml');
	}

	public getNotUseMessage() {
		let vm = this,
			messageItem = _.find(notUseMessage, ['value', vm.usedSatus()]);

		return messageItem ? this.$i18n.message(messageItem.text, [this.$i18n.text('KDP001_1')]) : '';

	}

	public getStampTime(data: IStampInfoDisp) {

		return data.stampHow + ' ' + moment.utc(data.stampTimeWithSec).format("HH:mm");
	}

	public convertToShortMDW(data: IStampInfoDisp) {

		return _.has(data, 'stampTimeWithSec') ? moment.utc(data.stampTimeWithSec).format("MM/DD(ddd)") : '';
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



	public getBGButton(data: IButtonSettingsDto) {
		let vm = this,
			buttonNo = data.buttonPositionNo,
			setting = vm.buttonSetting(),
			color = DEFAULT_GRAY,
			item: IButtonSettingsDto = _.find(vm.buttons(), ['buttonPositionNo', data.buttonPositionNo]);
		if (!!item) {
			color = item.buttonDisSet.backGroundColor;
		}

		if (buttonNo == 1) {
			return setting.goingToWork ? DEFAULT_GRAY : color;
		}
		if (buttonNo == 2) {
			return setting.departure ? DEFAULT_GRAY : color;
		}
		if (buttonNo == 3) {
			return setting.goOut ? DEFAULT_GRAY : color;
		}
		if (buttonNo == 4) {
			return setting.turnBack ? DEFAULT_GRAY : color;
		}
		return false;

	}

	public stamp(vm: KDP001AViewModel, data) {

		let cmd: IRegisterStampInputCommand = {
			datetime: vm.systemDate().format('YYYY/MM/DD HH:mm:ss'),
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
				case 3:
				case 4:
					vm.openDialogB(result, data.buttonPositionNo);
					break;

				case 2: {
					if (vm.stampResultDisplay().notUseAttr === 1) {
						vm.openDialogC(result, data.buttonPositionNo);
					} else {
						vm.openDialogB(result, data.buttonPositionNo);
					}
					break;
				}
			}
		}).fail((error) => {
			this.$dialog.alert({ messageId: error.messageId });
		}).always(() => {
			this.$blockui("clear");
		});
	}

	public openDialogB(dateParam, buttonDisNo) {

		let vm = this;
		nts.uk.ui.windows.setShared("resultDisplayTime", vm.resultDisplayTime());

		nts.uk.ui.windows.setShared("infoEmpToScreenB", {
			employeeId: vm.$user.employeeId,
			employeeCode: vm.$user.employeeCode,
			mode: Mode.Personal,
		});
		nts.uk.ui.windows.sub.modal('/view/kdp/002/b/index.xhtml').onClosed(function(): any {
			vm.$blockui("invisible");
			vm.$ajax(requestUrl.getOmissionContents, { pageNo: 1, buttonDisNo: buttonDisNo }).then((res) => {
				if (res && res.dailyAttdErrorInfos && res.dailyAttdErrorInfos.length > 0) {

					vm.$window.storage('KDP010_2T', res);

					nts.uk.ui.windows.sub.modal('/view/kdp/002/t/index.xhtml').onClosed(function(): any {

						let returnData = nts.uk.ui.windows.getShared('KDP010_T');
						if (!returnData.isClose && returnData.errorDate) {

							let transfer = returnData.btn.transfer;
							vm.$jump(returnData.btn.screen, transfer);
						}

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

	public openDialogC(dateParam, buttonDisNo) {
		let vm = this;

		nts.uk.ui.windows.setShared('KDP010_2C', vm.stampResultDisplay().displayItemId);

		nts.uk.ui.windows.setShared("infoEmpToScreenC", {
			employeeId: vm.$user.employeeId,
			employeeCode: vm.$user.employeeCode,
			mode: Mode.Personal,
			stampDate: dateParam
		});

		nts.uk.ui.windows.sub.modal('/view/kdp/002/c/index.xhtml').onClosed(function(): any {
			vm.$blockui("invisible");
			vm.$ajax(requestUrl.getOmissionContents, { pageNo: 1, buttonDisNo: buttonDisNo }).then((res) => {
				if (res && res.dailyAttdErrorInfos && res.dailyAttdErrorInfos.length > 0) {

					vm.$window.storage('KDP010_2T', res);

					nts.uk.ui.windows.sub.modal('/view/kdp/002/t/index.xhtml').onClosed(function(): any {
						let returnData = nts.uk.ui.windows.getShared('KDP010_T');
						if (!returnData.isClose && returnData.errorDate) {

							let transfer = returnData.btn.transfer;
							vm.$jump(returnData.btn.screen, transfer);
						}

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
		vm.$blockui("invisible");
		vm.$ajax(requestUrl.getEmployeeStampData).then((data: Array<Array<IStampInfoDisp>>) => {
			vm.$blockui("clear");
			let items = _(data).flatMap('stampRecords').value() as any[];

			items = _.orderBy(items, ['stampTimeWithSec'], ['desc']);

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

	public getTextAlign(data: IStampInfoDisp) {

		const {
			WORK,
			WORK_STRAIGHT,
			WORK_EARLY,
			WORK_BREAK,
			DEPARTURE,
			DEPARTURE_BOUNCE,
			DEPARTURE_OVERTIME,
			OUT,
			RETURN,
			GETTING_STARTED,
			DEPAR,
			TEMPORARY_WORK,
			TEMPORARY_LEAVING,
			START_SUPPORT,
			END_SUPPORT,
			WORK_SUPPORT,
			START_SUPPORT_EARLY_APPEARANCE,
			START_SUPPORT_BREAK,
			RESERVATION,
			CANCEL_RESERVATION
		} = ContentsStampType;

		const LEFT_ALIGNS = [
			WORK,
			WORK_STRAIGHT,
			WORK_EARLY,
			WORK_BREAK,
			GETTING_STARTED,
			TEMPORARY_WORK,
			START_SUPPORT,
			WORK_SUPPORT,
			START_SUPPORT_EARLY_APPEARANCE,
			START_SUPPORT_BREAK, 
			RESERVATION,
			CANCEL_RESERVATION
		];

		const RIGHT_ALIGNS = [
			DEPARTURE,
			DEPARTURE_BOUNCE,
			DEPARTURE_OVERTIME,
			DEPAR,
			TEMPORARY_LEAVING,
			END_SUPPORT
		];




		if (LEFT_ALIGNS.indexOf(data.correctTimeStampValue) > -1) {
			return 'left';
		}

		if (RIGHT_ALIGNS.indexOf(data.correctTimeStampValue) > -1) {
			return 'right';
		}

		return 'center';
	}

}

enum ContentsStampType {
	/** 1: 出勤 */
	WORK = 1,

	/** 2: 出勤＋直行 */
	WORK_STRAIGHT = 2,

	/** 3: 出勤＋早出 */
	WORK_EARLY = 3,

	/** 4: 出勤＋休出 */
	WORK_BREAK = 4,

	/** 5: 退勤 */
	DEPARTURE = 5,

	/** 6: 退勤＋直帰 */
	DEPARTURE_BOUNCE = 6,

	/** 7: 退勤＋残業 */
	DEPARTURE_OVERTIME = 7,

	/** 8: 外出 */
	OUT = 8,

	/** 9: 戻り */
	RETURN = 9,

	/** 10: 入門 */
	GETTING_STARTED = 10,

	/** 11: 退門 */
	DEPAR = 11,

	/** 12: 臨時出勤 */
	TEMPORARY_WORK = 12,

	/** 13: 臨時退勤 */
	TEMPORARY_LEAVING = 13,

	/** 14: 応援開始 */
	START_SUPPORT = 14,

	/** 15: 応援終了 */
	END_SUPPORT = 15,

	/** 16: 出勤＋応援 */
	WORK_SUPPORT = 16,

	/** 17: 応援開始＋早出 */
	START_SUPPORT_EARLY_APPEARANCE = 17,

	/** 18: 応援開始＋休出 */
	START_SUPPORT_BREAK = 18,

	/** 19: 予約 */
	RESERVATION = 19,

	/** 20: 予約取消  */
	CANCEL_RESERVATION = 20
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

interface IStampResultDisplay {
	companyId: string;
	displayItemId: Array<number>;
	notUseAttr: number
}

interface IStampSetting {
	portalStampSettings: IPortalStampSettingsDto;
	stampResultDisplayDto: IStampResultDisplay;
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
	stampTimeWithSec: Date;
	/**
	 * 打刻区分
	 */
	stampAtr: string;
	/**
	 * 打刻
	 */
	stamp: IStamp;

	correctTimeStampValue: number;

	stampHow: number;
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