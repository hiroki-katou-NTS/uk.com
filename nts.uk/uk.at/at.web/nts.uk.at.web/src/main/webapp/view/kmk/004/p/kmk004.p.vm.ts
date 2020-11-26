/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.p {

	export interface IParam extends IResponse {
		sidebarType: SIDEBAR_TYPE;
		wkpId: string;
		empCode: string;
		empId: string;
		titleName: string;
	}

	export type SIDEBAR_TYPE = null | 'Com_Company' | 'Com_Workplace' | 'Com_Employment' | 'Com_Person';

	export const KMK004_P_API = {
		COM_GET_BASIC_SETTING: 'screen/at/kmk004/viewP/com/basicSetting',
		WKP_GET_BASIC_SETTING: 'screen/at/kmk004/viewP/wkp/basicSetting',
		EMP_GET_BASIC_SETTING: 'screen/at/kmk004/viewP/emp/basicSetting',
		SHA_GET_BASIC_SETTING: 'screen/at/kmk004/viewP/sha/basicSetting',

		WKP_CREATE_BASIC_SETTING: 'screen/at/kmk004/viewP/wkp/basicSetting/add',
		EMP_CREATE_BASIC_SETTING: 'screen/at/kmk004/viewP/emp/basicSetting/add',
		SHA_CREATE_BASIC_SETTING: 'screen/at/kmk004/viewP/sha/basicSetting/add',

		COM_UPDATE_BASIC_SETTING: 'screen/at/kmk004/viewP/com/basicSetting/update',
		WKP_UPDATE_BASIC_SETTING: 'screen/at/kmk004/viewP/wkp/basicSetting/update',
		EMP_UPDATE_BASIC_SETTING: 'screen/at/kmk004/viewP/emp/basicSetting/update',
		SHA_UPDATE_BASIC_SETTING: 'screen/at/kmk004/viewP/sha/basicSetting/update',

		COM_DELETE_BASIC_SETTING: 'screen/at/kmk004/viewP/com/basicSetting/delete',
		WKP_DELETE_BASIC_SETTING: 'screen/at/kmk004/viewP/wkp/basicSetting/delete',
		EMP_DELETE_BASIC_SETTING: 'screen/at/kmk004/viewP/emp/basicSetting/delete',
		SHA_DELETE_BASIC_SETTING: 'screen/at/kmk004/viewP/sha/basicSetting/delete',

	};

	export interface IResponse {
		deforLaborTimeComDto: DeforLaborTimeComDto; //会社別変形労働法定労働時間
		settingDto: SettingDto; //会社別変形労働集計設定
	}

	//会社別変形労働法定労働時間
	export interface DeforLaborTimeComDto {
		weeklyTime: WeeklyTime; //週単位
		dailyTime: DailyTime; //日単位
	}

	//週単位
	export interface WeeklyTime {
		time: number; //週単位.時間
	}

	//日単位
	export interface DailyTime {
		time: number; //日単位.時間
	}

	//会社別変形労働集計設定
	export interface SettingDto {
		aggregateTimeSet: ExcessOutsideTimeSetReg; //集計時間設定
		excessOutsideTimeSet: ExcessOutsideTimeSetReg; //時間外超過設定
		settlementPeriod: SettlementPeriod; //清算期間
	}

	//時間外超過設定
	export interface ExcessOutsideTimeSetReg {
		legalOverTimeWork: boolean; //法定内残業を含める
		legalHoliday: boolean; //法定外休出を含める
	}

	//清算期間
	export interface SettlementPeriod {
		startMonth: number; //開始月
		period: number; //期間
		repeatAtr: boolean; //繰り返し区分
	}

	enum SCREEN_MODE {
		ADD = 1,
		UPDATE = 2
	}

	@bean()
	export class ViewModel extends ko.ViewModel {
		mode: KnockoutObservable<number> = ko.observable(SCREEN_MODE.ADD);
		title: KnockoutObservable<string> = ko.observable('');
		message: KnockoutObservable<string> = ko.observable('');
		day: KnockoutObservable<number> = ko.observable(0);
		week: KnockoutObservable<number> = ko.observable(0);

		itemListP3_3: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
		selectedP3_3: KnockoutObservable<number> = ko.observable(0);

		itemListP3_5: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
		selectedP3_5: KnockoutObservable<number> = ko.observable(1);
		requiredP3: KnockoutObservable<boolean> = ko.observable(false);

		itemListP3_7: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
		selectedP3_7: KnockoutObservable<number> = ko.observable(1);

		checkedP3_8: KnockoutObservable<boolean> = ko.observable(false);
		checkedP4_2: KnockoutObservable<boolean> = ko.observable(false);
		checkedP4_3: KnockoutObservable<boolean> = ko.observable(false);
		checkedP5_2: KnockoutObservable<boolean> = ko.observable(false);
		checkedP5_3: KnockoutObservable<boolean> = ko.observable(false);

		visibleP6_3: KnockoutObservable<boolean> = ko.observable(false);

		constructor(private params: IParam) {
			super();
			var vm = this;

			vm.itemListP3_3 = ko.observableArray<ItemModel>([
				new ItemModel('0', vm.$i18n("KMK004_313")), //単月
				new ItemModel('1', vm.$i18n("KMK004_314")) //複数月
			]);

			let tg = [], tg1 = [];
			for (let i = 1; i <= 12; i++) {
				tg.push(new ItemModel(i.toString(), i.toString() + '月'));
				tg1.push(new ItemModel(i.toString(), i.toString() + 'ヶ月'));
			}
			vm.itemListP3_5(tg);
			vm.itemListP3_7(tg1);
			vm.title(vm.params.titleName);

		}

		mounted() {
			const vm = this;
			vm.loadData();

		/*	switch (vm.mode()) {

				case SCREEN_MODE.ADD:
					vm.visibleP6_3(false);

					if (vm.params.sidebarType == 'Com_Workplace') {
						vm.message(vm.$i18n("KMK004_344"));

					} else if (vm.params.sidebarType == 'Com_Employment') {
						vm.message(vm.$i18n("KMK004_345"));

					} else if (vm.params.sidebarType == 'Com_Person') {
						vm.message(vm.$i18n("KMK004_346"));

					} else vm.message('');

					break;

				case SCREEN_MODE.UPDATE:
					vm.message('');
					vm.visibleP6_3(true);

					if (vm.params.sidebarType == 'Com_Company') {
						vm.title('会社');
						vm.visibleP6_3(false);
					}

					break;
			}*/
		}

		loadData() {
			const vm = this;
			vm.$blockui("grayout");

			//会社
			if (vm.params.sidebarType == 'Com_Company') {
				vm.mode(SCREEN_MODE.UPDATE);
				vm.$ajax(KMK004_P_API.COM_GET_BASIC_SETTING).done((data: IResponse) => {
					vm.bindingData(data);
					vm.checkDisplay();
				}).always(() => vm.$blockui("clear"));
			}

			//職場
			if (vm.params.sidebarType == 'Com_Workplace') {
				vm.$ajax(KMK004_P_API.WKP_GET_BASIC_SETTING + "/" + ko.toJS(vm.params.wkpId)).done((data: IResponse) => {
					vm.bindingData(data);
					vm.checkDisplay();
				}).always(() => vm.$blockui("clear"));
			}

			//雇用
			if (vm.params.sidebarType == 'Com_Employment') {
				vm.$ajax(KMK004_P_API.EMP_GET_BASIC_SETTING + "/" + ko.toJS(vm.params.empCode)).done((data: IResponse) => {
					vm.bindingData(data);
					vm.checkDisplay();
				}).always(() => vm.$blockui("clear"));
			}

			//社員
			if (vm.params.sidebarType == 'Com_Person') {
				vm.$ajax(KMK004_P_API.SHA_GET_BASIC_SETTING + "/" + ko.toJS(vm.params.empId)).done((data: IResponse) => {
					vm.bindingData(data);
					vm.checkDisplay();
				}).always(() => vm.$blockui("clear"));
			}
		}

		checkDisplay() {
			const vm = this;
			switch (vm.mode()) {

				case SCREEN_MODE.ADD:
					vm.visibleP6_3(false);

					if (vm.params.sidebarType == 'Com_Workplace') {
						vm.message(vm.$i18n("KMK004_344"));

					} else if (vm.params.sidebarType == 'Com_Employment') {
						vm.message(vm.$i18n("KMK004_345"));

					} else if (vm.params.sidebarType == 'Com_Person') {
						vm.message(vm.$i18n("KMK004_346"));

					} else vm.message('');

					break;

				case SCREEN_MODE.UPDATE:
					vm.message('');
					vm.visibleP6_3(true);

					if (vm.params.sidebarType == 'Com_Company') {
						vm.title('会社');
						vm.visibleP6_3(false);
					}

					break;
			}
		}

		bindingData(data: IResponse): void {
			const vm = this;
			if (data.deforLaborTimeComDto != null && data.settingDto != null) {
				let deforLaborTimeEmp = data.deforLaborTimeComDto;
				let setting = data.settingDto;

				vm.mode(SCREEN_MODE.UPDATE);
				vm.day(deforLaborTimeEmp.dailyTime.time);
				vm.week(deforLaborTimeEmp.weeklyTime.time);
				vm.selectedP3_3(setting.settlementPeriod.period == 1 ? 0 : 1);
				vm.selectedP3_5(setting.settlementPeriod.startMonth);
				vm.requiredP3(setting.settlementPeriod.period == 1 ? false : true);
				vm.selectedP3_7(setting.settlementPeriod.period);
				vm.checkedP3_8(setting.settlementPeriod.repeatAtr);
				vm.checkedP4_2(setting.aggregateTimeSet.legalOverTimeWork);
				vm.checkedP4_3(setting.aggregateTimeSet.legalHoliday);
				vm.checkedP5_2(setting.excessOutsideTimeSet.legalOverTimeWork);
				vm.checkedP5_3(setting.excessOutsideTimeSet.legalHoliday);
			} else {
				vm.mode(SCREEN_MODE.ADD);
			}

			$('#inputP2_3').focus();

		}

		register() {
			const vm = this;

			if (vm.mode() == SCREEN_MODE.ADD) {
				vm.create();

			}

			if (vm.mode() == SCREEN_MODE.UPDATE) {
				vm.update();
			}

		}

		create() {
			const vm = this;
			//職場
			if (vm.params.sidebarType == 'Com_Workplace') {


			}

			//雇用
			if (vm.params.sidebarType == 'Com_Employment') {


			}

			//社員
			if (vm.params.sidebarType == 'Com_Person') {


			}
		}

		update() {
			const vm = this;
			//職場
			if (vm.params.sidebarType == 'Com_Company') {
				vm.$ajax(KMK004_P_API.COM_UPDATE_BASIC_SETTING, vm.params).done(() => {
					vm.$dialog.info({ messageId: "Msg_15" });
				}).fail((error) => {
					vm.$dialog.error(error);
				}).always(() => {
					vm.$blockui("clear");
				});
			}

			//職場
			if (vm.params.sidebarType == 'Com_Workplace') {


			}

			//雇用
			if (vm.params.sidebarType == 'Com_Employment') {


			}

			//社員
			if (vm.params.sidebarType == 'Com_Person') {


			}
		}

		close() {
			const vm = this;
			vm.$window.close();
		}

		delete() {
			const vm = this;
		}
	}

	class ItemModel {
		code: string;
		name: string;

		constructor(code: string, name: string) {
			this.code = code;
			this.name = name;
		}
	}
}
