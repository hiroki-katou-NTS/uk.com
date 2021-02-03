/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmk004.k {

	const BASE_URL = 'screen/at/kmk004/k/';

	const API = {
		DISPLAY_COM: BASE_URL + 'display-com',
		DISPLAY_WKP: BASE_URL + 'display-wkp/',
		DISPLAY_EMP: BASE_URL + 'display-emp/',
		DISPLAY_SHA: BASE_URL + 'display-sha/',
		UPDATE_COM: BASE_URL + 'update-com',
		UPDATE_WKP: BASE_URL + 'update-wkp',
		UPDATE_EMP: BASE_URL + 'update-emp',
		UPDATE_SHA: BASE_URL + 'update-sha',
		REGISTER_WKP: BASE_URL + 'register-wkp',
		REGISTER_EMP: BASE_URL + 'register-emp',
		REGISTER_SHA: BASE_URL + 'register-sha',
		DELETE_WKP: BASE_URL + 'delete-wkp',
		DELETE_EMP: BASE_URL + 'delete-emp',
		DELETE_SHA: BASE_URL + 'delete-sha',
	};

	@bean()
	export class ViewModel extends ko.ViewModel {

		screenData: KnockoutObservable<ScreenData> = ko.observable(new ScreenData());

		screenMode: 'Com_Company' | 'Com_Workplace' | 'Com_Employment' | 'Com_Person' = 'Com_Company';

		title: string = '';

		startMonthLst = ko.observableArray([]);

		selected: string = '';

		carryforwardSetInShortageFlex = ko.observableArray(__viewContext.enums.CarryforwardSetInShortageFlex);

		notSetting: KnockoutObservable<boolean> = ko.observable(true);

		created(param?: any) {
			const vm = this;
			vm.initMonthLst();
			if (param) {
				vm.screenMode = param.screenMode;
				vm.title = param.title;
				vm.selected = param.selected;
			}
			if (vm.screenMode !== 'Com_Company') {
				let windowSize = nts.uk.ui.windows.getSelf();
				windowSize.$dialog.dialog("option", "height", 670);
			}
			let START_URL;
			if (vm.screenMode == 'Com_Company')
				START_URL = API.DISPLAY_COM;
			if (vm.screenMode == 'Com_Workplace')
				START_URL = API.DISPLAY_WKP + vm.selected;
			if (vm.screenMode == 'Com_Employment')
				START_URL = API.DISPLAY_EMP + vm.selected;
			if (vm.screenMode == 'Com_Person')
				START_URL = API.DISPLAY_SHA + vm.selected;

			vm.$blockui('invisible');
			vm.$ajax(START_URL).done((data: IScreenData) => {

				vm.screenData().updateData(vm, data);
				vm.notSetting(vm.screenMode == 'Com_Company' ? data.comFlexMonthActCalSet == null : data.flexMonthActCalSet == null);
				$('#settlePeriod').focus();
			}).always(() => { vm.$blockui('clear'); });
		}

		initMonthLst() {
			const vm = this;
			let startMonthLst = [];
			for (let i = 1; i <= 12; i++) {
				startMonthLst.push(new MonthItem({ code: i, name: i.toString() + '月' }));
			}
			vm.startMonthLst(startMonthLst);
			vm.screenData().flexMonthActCalSet().insufficSet().startMonth(startMonthLst[0].code);
		}



		enableDeleteButton() {
			const vm = this;
			if (vm.screenMode == 'Com_Company') {
				return false;
			}
			return !vm.notSetting();
		}


		register() {
			const vm = this;

			if (vm.notSetting()) {
				vm.registerSetting();
			} else {
				vm.updateSetting();
			}
		}

		updateSetting() {
			const vm = this;

			let UPDATE_URL,
				cmd = ko.toJS(vm.screenData());
			cmd.flexMonthActCalSet.legalAggrSet.aggregateSet = cmd.flexMonthActCalSet.legalAggrSet.aggregateSet == true ? 1 : 0;

			if (vm.screenMode == 'Com_Company') {
				UPDATE_URL = API.UPDATE_COM;
			}

			if (vm.screenMode == 'Com_Workplace') {
				UPDATE_URL = API.UPDATE_WKP;
				cmd.flexMonthActCalSet.workplaceId = vm.selected;

			}
			if (vm.screenMode == 'Com_Employment') {
				cmd.flexMonthActCalSet.employmentCode = vm.selected;
				UPDATE_URL = API.UPDATE_EMP;
			}

			if (vm.screenMode == 'Com_Person') {
				cmd.flexMonthActCalSet.empId = vm.selected;
				UPDATE_URL = API.UPDATE_SHA;
			}

			vm.$blockui('invisible');
			vm.$ajax(UPDATE_URL, cmd).done(() => {
				vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
					vm.$window.close();
				});
			}).always(() => { vm.$blockui('clear'); });
		}

		registerSetting() {
			const vm = this;

			let REGISTER_URL,
				cmd = ko.toJS(vm.screenData());
			cmd.flexMonthActCalSet.legalAggrSet.aggregateSet = cmd.flexMonthActCalSet.legalAggrSet.aggregateSet == true ? 1 : 0;
			cmd.flexMonthActCalSet.withinTimeUsageAttr = cmd.flexMonthActCalSet.withinTimeUsageAttr == true ? 1 : 0;

			if (vm.screenMode == 'Com_Workplace') {
				REGISTER_URL = API.REGISTER_WKP;
				cmd.flexMonthActCalSet.workplaceId = vm.selected;

			}

			if (vm.screenMode == 'Com_Employment') {
				cmd.flexMonthActCalSet.employmentCode = vm.selected;
				REGISTER_URL = API.REGISTER_EMP;
			}

			if (vm.screenMode == 'Com_Person') {
				cmd.flexMonthActCalSet.empId = vm.selected;
				REGISTER_URL = API.REGISTER_SHA;
			}

			vm.$blockui('invisible');
			vm.$ajax(REGISTER_URL, cmd).done(() => {
				vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
					vm.$window.close();
				});
			}).always(() => { vm.$blockui('clear'); });
		}

		getMessage() {
			const vm = this
				, msgs = [
					{ mode: 'Com_Company', msg: '' },
					{ mode: 'Com_Workplace', msg: 'KMK004_344' },
					{ mode: 'Com_Employment', msg: 'KMK004_345' },
					{ mode: 'Com_Person', msg: 'KMK004_346' }];

			return vm.$i18n.text(_.find(msgs, ['mode', vm.screenMode]).msg);
		}

		close() {
			const vm = this;
			vm.$window.close();

		}

		remove() {
			const vm = this;
			vm.$dialog.confirm({ messageId: "Msg_18" }).then((result: 'no' | 'yes' | 'cancel') => {
				if (result === 'yes') {

					vm.deleteData();

				}
			});
		}

		deleteData() {
			const vm = this;

			let DELETE_URL;

			if (vm.screenMode == 'Com_Workplace') {
				DELETE_URL = API.DELETE_WKP;

			}
			if (vm.screenMode == 'Com_Employment') {
				DELETE_URL = API.DELETE_EMP;
			}

			if (vm.screenMode == 'Com_Person') {
				DELETE_URL = API.DELETE_SHA;
			}

			vm.$blockui('invisible');

			let cmd = vm.selected;

			vm.$ajax('at', DELETE_URL, cmd).done((data) => {
				vm.$dialog.info({ messageId: "Msg_16" }).then(() => {
					vm.$window.close();
				});
			}).always(() => { vm.$blockui("clear"); });

		}


		mounted() {

		}
	}

	class ScreenData {

		// フレックス勤務所定労働時間取得
		flexPredWorkTime: KnockoutObservable<GetFlexPredWorkTime> = ko.observable(new GetFlexPredWorkTime());
		// 会社別フレックス勤務集計方法
		flexMonthActCalSet: KnockoutObservable<FlexMonthActCalSet> = ko.observable(new FlexMonthActCalSet());

		updateData(vm: ViewModel, param: IScreenData) {
			this.flexMonthActCalSet().update(vm.screenMode == 'Com_Company' ? param.comFlexMonthActCalSet : param.flexMonthActCalSet);
			this.flexPredWorkTime().update(param.flexPredWorkTime);

		}
	}

	interface IShortageFlexSetting {

		/** 繰越設定 */
		carryforwardSet: number;
		/** 清算期間 */
		settlePeriod: number;
		/** 開始月 */
		startMonth: number;
		/** 期間 */
		period: number;
	}
	class ShortageFlexSetting {
		carryforwardSet: KnockoutObservable<number> = ko.observable(0);
		settlePeriod: KnockoutObservable<number> = ko.observable(0);
		startMonth: KnockoutObservable<number> = ko.observable(1);
		period: KnockoutObservable<number> = ko.observable(2);
		update(param: IShortageFlexSetting) {
			this.carryforwardSet(param.carryforwardSet);
			this.settlePeriod(param.settlePeriod);
			this.startMonth(param.startMonth);
			this.period(param.period);
		}
	}

	interface IScreenData {
		// フレックス勤務所定労働時間取得
		flexPredWorkTime: IGetFlexPredWorkTime;
		// 会社別フレックス勤務集計方法
		flexMonthActCalSet: IFlexMonthActCalSet;

		comFlexMonthActCalSet: IFlexMonthActCalSet;

	}

	interface IGetFlexPredWorkTime {
		/** 参照先 */
		reference: number;
	}

	interface IFlexMonthActCalSet {
		/** 集計方法 */
		aggrMethod: number;

		/** 不足設定 */
		insufficSet: IShortageFlexSetting;

		/** 法定内集計設定 */
		legalAggrSet: IAggregateTimeSetting;

		/** フレックス時間の扱い */
		flexTimeHandle: IFlexTimeHandle;

		withinTimeUsageAttr: boolean;

	}

	interface IAggregateTimeSetting {
		/** 集計設定 */
		aggregateSet: number;
	}

	class GetFlexPredWorkTime {
		reference: KnockoutObservable<number> = ko.observable(1);

		update(param: IGetFlexPredWorkTime) {
			if (param) {
				this.reference(param.reference);
			}
		}
	}

	class FlexMonthActCalSet {
		/** 集計方法 */
		aggrMethod: KnockoutObservable<number> = ko.observable(0);

		/** 不足設定 */
		insufficSet: KnockoutObservable<ShortageFlexSetting> = ko.observable(new ShortageFlexSetting());

		/** 法定内集計設定 */
		legalAggrSet: KnockoutObservable<AggregateTimeSetting> = ko.observable(new AggregateTimeSetting());

		/** フレックス時間の扱い */
		flexTimeHandle: KnockoutObservable<FlexTimeHandle> = ko.observable(new FlexTimeHandle());

		/** 所定労動時間使用区分 */
		withinTimeUsageAttr: KnockoutObservable<number> = ko.observable(1);

		update(param: IFlexMonthActCalSet) {
			if (param) {
				this.aggrMethod(param.aggrMethod);
				this.insufficSet().update(param.insufficSet);
				this.legalAggrSet().update(param.legalAggrSet);
				this.flexTimeHandle().update(param.flexTimeHandle);
				this.withinTimeUsageAttr(param.withinTimeUsageAttr == true ? 1 : 0);
			}

		}

	}

	class AggregateTimeSetting {
		aggregateSet: KnockoutObservable<boolean> = ko.observable(false);

		update(param: IAggregateTimeSetting) {
			this.aggregateSet(param.aggregateSet == 0 ? false : true);
		}

	}

	interface IFlexTimeHandle {
		/** 残業時間をフレックス時間に含める */
		includeOverTime: boolean;

		/** 法定外休出時間をフレックス時間に含める */
		includeIllegalHdwk: boolean;

	}

	class FlexTimeHandle {
		includeOverTime: KnockoutObservable<boolean> = ko.observable(false);

		includeIllegalHdwk: KnockoutObservable<boolean> = ko.observable(false);

		update(param: IFlexTimeHandle) {
			this.includeOverTime(param.includeOverTime);
			this.includeIllegalHdwk(param.includeIllegalHdwk);
		}

	}

	class MonthItem {
		code: number = 1;
		name: string = '';

		constructor(param: any) {
			this.code = param.code;
			this.name = param.name;
		}
	}
}
