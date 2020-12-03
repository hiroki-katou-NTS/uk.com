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

		WKP_DELETE_BASIC_SETTING: 'screen/at/kmk004/viewP/wkp/basicSetting/delete',
		EMP_DELETE_BASIC_SETTING: 'screen/at/kmk004/viewP/emp/basicSetting/delete',
		SHA_DELETE_BASIC_SETTING: 'screen/at/kmk004/viewP/sha/basicSetting/delete',

	};

	export interface IResponse {
		deforLaborTimeComDto: DeforLaborTimeComDto; //会社別変形労働法定労働時間
		settingDto: SettingDto; //会社別変形労働集計設定
	}

	export enum SCREEN_MODE {
		ADD = 1,
		UPDATE = 2
	}

	@bean()
	export class ViewModel extends ko.ViewModel {
		mode: KnockoutObservable<number> = ko.observable(SCREEN_MODE.ADD);
		title: KnockoutObservable<string> = ko.observable('');
		message: KnockoutObservable<string> = ko.observable('');

		itemListP3_3: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
		selectedP3_3: KnockoutObservable<number> = ko.observable(0);
		itemListP3_5: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
		itemListP3_7: KnockoutObservableArray<ItemModel> = ko.observableArray([]);

		visibleP6_3: KnockoutObservable<boolean> = ko.observable(false);
		requiredP3: KnockoutObservable<boolean> = ko.observable(false);

		screenData = new TransformScreenData();

		constructor(private params: IParam) {
			super();
			if(params){			
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
				vm.title(params.titleName);
			}
		}

		mounted() {
			const vm = this;
			if(vm.params)
			vm.loadData();
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

					if (vm.screenData.settingDto.settlementPeriod.period() === 1) {
						vm.selectedP3_3(0);
					} else {
						vm.selectedP3_3(1);
					}
				}).always(() => vm.$blockui("clear"));
			}

			//職場
			if (vm.params.sidebarType == 'Com_Workplace') {
				vm.$ajax(KMK004_P_API.WKP_GET_BASIC_SETTING + "/" + ko.toJS(vm.params.wkpId)).done((data: IResponse) => {
					vm.bindingData(data);
					vm.checkDisplay();

					if (vm.screenData.settingDto.settlementPeriod.period() === 1) {
						vm.selectedP3_3(0);
					} else {
						vm.selectedP3_3(1);
					}
				}).always(() => vm.$blockui("clear"));
			}

			//雇用
			if (vm.params.sidebarType == 'Com_Employment') {
				vm.$ajax(KMK004_P_API.EMP_GET_BASIC_SETTING + "/" + ko.toJS(vm.params.empCode)).done((data: IResponse) => {
					vm.bindingData(data);
					vm.checkDisplay();

					if (vm.screenData.settingDto.settlementPeriod.period() === 1) {
						vm.selectedP3_3(0);
					} else {
						vm.selectedP3_3(1);
					}
				}).always(() => vm.$blockui("clear"));
			
			}

			//社員
			if (vm.params.sidebarType == 'Com_Person') {
				vm.$ajax(KMK004_P_API.SHA_GET_BASIC_SETTING + "/" + ko.toJS(vm.params.empId)).done((data: IResponse) => {
					vm.bindingData(data);
					vm.checkDisplay();

					if (vm.screenData.settingDto.settlementPeriod.period() === 1) {
						vm.selectedP3_3(0);
					} else {
						vm.selectedP3_3(1);
					}
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
				vm.mode(SCREEN_MODE.UPDATE);
				vm.screenData.update(data);

			} else {
				vm.mode(SCREEN_MODE.ADD);
			}

			$('#inputP2_3').focus();

		}

		register() {
			const vm = this;

			if (vm.mode() == SCREEN_MODE.ADD) {
				vm.create();
			} else vm.update();
		}

		create() {
			const vm = this;
			vm.screenData.add(vm.params);

			//職場
			if (vm.params.sidebarType == 'Com_Workplace') {

				vm.$validate('.nts-editor').then((valid: boolean) => {
					if (!valid) {
						return;
					}

					vm.$ajax(KMK004_P_API.WKP_CREATE_BASIC_SETTING, ko.toJS(vm.screenData)).done(() => {
						vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
							vm.close();
						})

					}).fail((error) => {
						vm.$dialog.error(error);
					}).always(() => {
						vm.$blockui("clear");
					});
				});

			}

			//雇用
			if (vm.params.sidebarType == 'Com_Employment') {

				vm.$validate('.nts-editor').then((valid: boolean) => {
					if (!valid) {
						return;
					}
					vm.$ajax(KMK004_P_API.EMP_CREATE_BASIC_SETTING, ko.toJS(vm.screenData)).done(() => {
						vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
							vm.close();
						})
					}).fail((error) => {
						vm.$dialog.error(error);
					}).always(() => {
						vm.$blockui("clear");
					});

				});

			}

			//社員
			if (vm.params.sidebarType == 'Com_Person') {
				vm.$validate('.nts-editor').then((valid: boolean) => {
					if (!valid) {
						return;
					}
					vm.$ajax(KMK004_P_API.SHA_CREATE_BASIC_SETTING, ko.toJS(vm.screenData)).done(() => {
						vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
							vm.close();
						})
					}).fail((error) => {
						vm.$dialog.error(error);
					}).always(() => {
						vm.$blockui("clear");
					});
				});

			}
		}

		update() {
			const vm = this;
			vm.screenData.add(vm.params);

			//職場
			if (vm.params.sidebarType == 'Com_Company') {
				vm.$validate('.nts-editor').then((valid: boolean) => {
					if (!valid) {
						return;
					}
					vm.$ajax(KMK004_P_API.COM_UPDATE_BASIC_SETTING, ko.toJS(vm.screenData)).done(() => {
						vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
							vm.close();
						})
					}).fail((error) => {
						vm.$dialog.error(error);
					}).always(() => {
						vm.$blockui("clear");
					});

				});
			}

			//職場
			if (vm.params.sidebarType == 'Com_Workplace') {

				vm.$validate('.nts-editor').then((valid: boolean) => {
					if (!valid) {
						return;
					}
					vm.$ajax(KMK004_P_API.WKP_UPDATE_BASIC_SETTING, ko.toJS(vm.screenData)).done(() => {
						vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
							vm.close();
						})
					}).fail((error) => {
						vm.$dialog.error(error);
					}).always(() => {
						vm.$blockui("clear");
					});

				});

			}

			//雇用
			if (vm.params.sidebarType == 'Com_Employment') {

				vm.$validate('.nts-editor').then((valid: boolean) => {
					if (!valid) {
						return;
					}
					vm.$ajax(KMK004_P_API.EMP_UPDATE_BASIC_SETTING, ko.toJS(vm.screenData)).done(() => {
						vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
							vm.close();
						})
					}).fail((error) => {
						vm.$dialog.error(error);
					}).always(() => {
						vm.$blockui("clear");
					});
				});
			}

			//社員
			if (vm.params.sidebarType == 'Com_Person') {

				vm.$validate('.nts-editor').then((valid: boolean) => {
					if (!valid) {
						return;
					}

					vm.$ajax(KMK004_P_API.SHA_UPDATE_BASIC_SETTING, ko.toJS(vm.screenData)).done(() => {
						vm.$dialog.info({ messageId: "Msg_15" }).then(() => {
							vm.close();
						})
					}).fail((error) => {
						vm.$dialog.error(error);
					}).always(() => {
						vm.$blockui("clear");
					});
				});
			}
		}

		close() {
			const vm = this;
			vm.$window.close();
		}

		removeData() {
			const vm = this;
			vm.screenData.add(vm.params);

			//職場
			if (vm.params.sidebarType == 'Com_Workplace') {
				vm.$dialog.confirm({ messageId: "Msg_18" }).then((result: 'no' | 'yes' | 'cancel') => {
					if (result === 'yes') {
						vm.$blockui("invisible");
						vm.$ajax(KMK004_P_API.WKP_DELETE_BASIC_SETTING, ko.toJS(vm.screenData)).done(() => {
							vm.$dialog.info({ messageId: "Msg_16" }).then(() => {
								vm.close();
							})

						}).fail((error) => {
							vm.$dialog.error(error);
						}).always(() => {
							vm.$blockui("clear");

						});

					} else {
						$('#inputP2_3').focus();
					}
				});
			}

			//雇用
			if (vm.params.sidebarType == 'Com_Employment') {
				vm.$dialog.confirm({ messageId: "Msg_18" }).then((result: 'no' | 'yes' | 'cancel') => {
					if (result === 'yes') {
						vm.$blockui("invisible");
						vm.$ajax(KMK004_P_API.EMP_DELETE_BASIC_SETTING, ko.toJS(vm.screenData)).done(() => {
							vm.$dialog.info({ messageId: "Msg_16" }).then(() => {
								vm.close();
							})

						}).fail((error) => {
							vm.$dialog.error(error);
						}).always(() => {
							vm.$blockui("clear");

						});

					} else {
						$('#inputP2_3').focus();
					}
				});

			}

			//社員
			if (vm.params.sidebarType == 'Com_Person') {
				vm.$dialog.confirm({ messageId: "Msg_18" }).then((result: 'no' | 'yes' | 'cancel') => {
					if (result === 'yes') {
						vm.$blockui("invisible");
						vm.$ajax(KMK004_P_API.SHA_DELETE_BASIC_SETTING, ko.toJS(vm.screenData)).done(() => {
							vm.$dialog.info({ messageId: "Msg_16" }).then(() => {
								vm.close();
							})

						}).fail((error) => {
							vm.$dialog.error(error);
						}).always(() => {
							vm.$blockui("clear");

						});

					} else {
						$('#inputP2_3').focus();
					}
				});

			}

		}
	}

	export class TransformScreenData {
		deforLaborTimeComDto = new DeforLaborTimeCom();
		settingDto = new Setting();
		sidebarType: SIDEBAR_TYPE;
		wkpId: string;
		empCode: string;
		empId: string;
		titleName: string;
		constructor() { }
		update(param: IResponse) {
			this.deforLaborTimeComDto.update(param.deforLaborTimeComDto);
			this.settingDto.update(param.settingDto);
		}

		add(param: IParam) {
			this.sidebarType = param.sidebarType;
			this.wkpId = param.wkpId;
			this.empCode = param.empCode;
			this.empId = param.empId;
			this.titleName = param.titleName;
		}

	}

	//会社別変形労働法定労働時間
	export class DeforLaborTimeCom {
		weeklyTime = new Weekly();
		dailyTime = new Daily();
		constructor() { }
		update(param: DeforLaborTimeComDto) {
			this.weeklyTime.update(param.weeklyTime);
			this.dailyTime.update(param.dailyTime);
		}
	}

	//週単位
	export class Weekly {
		time: KnockoutObservable<number> = ko.observable(0);
		constructor() { }
		update(param: WeeklyTime) {
			this.time(param.time);
		}
	}

	//日単位
	export class Daily {
		time: KnockoutObservable<number> = ko.observable(0);
		constructor() { }
		update(param: DailyTime) {
			this.time(param.time);
		}
	}

	//会社別変形労働集計設定
	export class Setting {
		aggregateTimeSet = new TimeSet(); //集計時間設定
		excessOutsideTimeSet = new TimeSet(); //時間外超過設定
		settlementPeriod = new Settlement(); //清算期間
		constructor() { }
		update(param: SettingDto) {
			this.aggregateTimeSet.update(param.aggregateTimeSet);
			this.excessOutsideTimeSet.update(param.excessOutsideTimeSet);
			this.settlementPeriod.update(param.settlementPeriod);
		}
	}

	//清算期間
	export class Settlement {
		startMonth = ko.observable(0);
		period = ko.observable(0);
		repeatAtr = ko.observable(false);
		constructor() { }
		update(param: SettlementPeriod) {
			this.startMonth(param.startMonth);
			this.period(param.period);
			this.repeatAtr(param.repeatAtr);
		}
	}

	//時間外超過設定
	export class TimeSet {
		legalOverTimeWork: KnockoutObservable<boolean> = ko.observable(false);
		legalHoliday: KnockoutObservable<boolean> = ko.observable(false);
		constructor() { }
		update(param: ExcessOutsideTimeSetReg) {
			this.legalOverTimeWork(param.legalOverTimeWork);
			this.legalHoliday(param.legalHoliday);
		}
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

	class ItemModel {
		code: string;
		name: string;

		constructor(code: string, name: string) {
			this.code = code;
			this.name = name;
		}
	}
}
