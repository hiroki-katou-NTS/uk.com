/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.b {

	const API = {
		DISPLAY_BASICSETTING: 'screen/at/kmk004/getDisplayBasicSetting',
		GET_SETTING_WORKPLACE: 'screen/at/kmk004/viewc/wkp/getBaseSetting',
		GET_SETTING_EMPLOYMENT: 'screen/at/kmk004/viewd/emp/getBaseSetting',
		GET_SETTING_EMPLOYEE: 'screen/at/kmk004/viewe/sha/getBaseSetting'
	}

	interface Params {
		type: SIDEBAR_TYPE;
		selectId: KnockoutObservable<string>;
		change: KnockoutObservable<string>;
		checkSeting?: KnockoutObservable<boolean>;
	}

	const template = `
	<!-- ko if: checkSeting -->
        <div class="table-view">
            <table>
                <tbody>
                    <tr class= "label-row">
                        <th>
                            <div class="content-tab" data-bind="i18n: 'KMK004_213'"></div>
                        </th>
                        <th>
                            <div class="content-tab" data-bind="i18n: 'KMK004_214'"></div>
                        </th>
                    </tr>
                    <tr>
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: tabSetting.daily"></div>
                        </td>
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: tabSetting.weekly"></div>
						</td>

						<td style="visibility: hidden; border:none"  ></td>
                    </tr>
                    <tr class= "label-row">
						<th colspan="3" style="border: 1px solid gray;">
						
                            <div data-bind="i18n: 'KMK004_215'"></div>
                        </th>
                    </tr>
                    <tr class= "label-row">
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: model.surcharge1"></div>
                        </td>
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: model.surcharge2"></div>
                        </td>
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: model.surcharge3"></div>
                        </td>
                    </tr>
                    <tr class= "label-row">
                        <th colspan="3">
                            <div data-bind="i18n: 'KMK004_220'"></div>
                        </th>
                    </tr>
                    <tr class= "label-row">
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: model.surchargeOvertime1"></div>
                        </td>
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: model.surchargeOvertime2"></div>
                        </td>
                        <td class="label-column">
                            <div class="content-tab" data-bind="i18n: model.surchargeOvertime3"></div>
                        </td>
                    </tr>
                </tbody>
            </table>
		</div>
		<!-- /ko -->
        <style type="text/css" rel="stylesheet">
						.content-tab { white-space: nowrap; }
            .table-view{
                padding: 15px;
                border: 2px solid #B1B1B1;
                border-radius: 15px;
				margin-left: 15px;
				margin-top: 15px;
            }

            .table-view table {
                border-collapse: collapse;
            }
            
            .table-view th {
                background: #E0F59E;
            }

            .table-view , th {
                border: 1px solid gray;
			}
			.table-view , td {
                border: 1px solid gray;
            }

            .table-view .label-column {
				border: 1px solid gray;
				width: 151px;
				max-width: 151px;
				min-width: 131px;
              }

			.table-view td, .table-view th{
				 padding: 3px 10px;
				text-align: center;
			}

        </style>
        <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
    `;

	@component({
		name: 'basic-setting',
		template
	})

	class Setting extends ko.ViewModel {
		public model = new DataModel();
		public tabSetting = new TabSetting();
		public type: SIDEBAR_TYPE;
		public selectId: KnockoutObservable<string> = ko.observable('');
		public change: KnockoutObservable<string> = ko.observable('');
		public checkSeting: KnockoutObservable<boolean> = ko.observable(true);

		created(params: Params) {
			const vm = this;
			vm.type = params.type;
			vm.selectId = params.selectId;
			vm.change = params.change;
			if (params.checkSeting) {
				vm.checkSeting = params.checkSeting;
			}
			if (vm.type === 'Com_Company') {
				vm.checkSeting(true);
			}

			vm.reloadData();

			vm.selectId
				.subscribe(() => {
					vm.reloadData();
				});

			vm.change
				.subscribe(() => {
					vm.reloadData();
				});
		}

		mounted() {
			const vm = this;
			vm.init();
		}

		init() {
			const vm = this;

			if (ko.unwrap(vm.tabSetting.deforWorkSurchargeWeekMonth)) {
				vm.model.surcharge1('KMK004_244');
			} else {
				vm.model.surcharge1('KMK004_245');
			}
			if (ko.unwrap(vm.tabSetting.deforWorkLegalOverTimeWork)) {
				vm.model.surcharge2('KMK004_216');
			} else {
				vm.model.surcharge2('KMK004_217');
			}

			if (ko.unwrap(vm.tabSetting.deforWorkLegalHoliday)) {
				vm.model.surcharge3('KMK004_218');
			} else {
				vm.model.surcharge3('KMK004_219');
			}

			if (ko.unwrap(vm.tabSetting.outsideSurchargeWeekMonth)) {
				vm.model.surchargeOvertime1('KMK004_250');
			} else {
				vm.model.surchargeOvertime1('KMK004_251');
			}

			if (ko.unwrap(vm.tabSetting.outsidedeforWorkLegalOverTimeWork)) {
				vm.model.surchargeOvertime2('KMK004_216');
			} else {
				vm.model.surchargeOvertime2('KMK004_217');
			}

			if (ko.unwrap(vm.tabSetting.outsidedeforWorkLegalHoliday)) {
				vm.model.surchargeOvertime3('KMK004_218');
			} else {
				vm.model.surchargeOvertime3('KMK004_219');
			}
		}

		reloadData() {
			const vm = this;
			switch (vm.type) {
				case 'Com_Company':
					vm.$blockui('invisible')
						.then(() => vm.$ajax(API.DISPLAY_BASICSETTING))
						.then((data: ITabSetting) => {
							vm.tabSetting.create(data);
							vm.init();
						})
						.then(() => vm.$blockui('clear'));
					break;
				case 'Com_Workplace':
					if (ko.unwrap(vm.selectId) !== '') {
						
						vm.$blockui('invisible')
							.then(() => vm.$ajax(API.GET_SETTING_WORKPLACE + "/" + ko.unwrap(vm.selectId)))
							.then((data: ITabSetting) => {
								if (data) {
									vm.tabSetting.create(data);
									vm.init();
									vm.checkSeting(true);
								} else {
									vm.checkSeting(false);
								}
								vm.tabSetting.create(data);
								vm.init();
							})
							.then(() => vm.$blockui('clear'));
					}
					break;
				case 'Com_Employment':
					if (ko.unwrap(vm.selectId) !== '') {
						vm.$blockui('invisible')
							.then(() => vm.$ajax(API.GET_SETTING_EMPLOYMENT + "/" + ko.unwrap(vm.selectId)))
							.then((data: ITabSetting) => {
								if (data) {
									vm.tabSetting.create(data);
									vm.init();
									vm.checkSeting(true);
								} else {
									vm.checkSeting(false);
								}
							})
							.then(() => vm.$blockui('clear'));
					}
					break;
				case 'Com_Person':
					if (ko.unwrap(vm.selectId) !== '') {
						vm.$blockui('invisible')
							.then(() => vm.$ajax(API.GET_SETTING_EMPLOYEE + "/" + ko.unwrap(vm.selectId)))
							.then((data: ITabSetting) => {
								if (data) {
									vm.tabSetting.create(data);
									vm.init();
									vm.checkSeting(true);
								} else {
									vm.checkSeting(false);
								}
							})
							.then(() => vm.$blockui('clear'));
					}
					break;
			}
		}
	}

	interface IDataModel {
		surcharge1: string;
		surcharge2: string;
		surcharge3: string;
		surchargeOvertime1: string;
		surchargeOvertime2: string;
		surchargeOvertime3: string;
	}

	class DataModel {
		surcharge1: KnockoutObservable<string> = ko.observable(''); //245
		surcharge2: KnockoutObservable<string> = ko.observable(''); //217
		surcharge3: KnockoutObservable<string> = ko.observable(''); //219
		surchargeOvertime1: KnockoutObservable<string> = ko.observable(''); //251
		surchargeOvertime2: KnockoutObservable<string> = ko.observable(''); //217
		surchargeOvertime3: KnockoutObservable<string> = ko.observable(''); //219

		public create(params?: IDataModel) {
			const self = this;

			if (params) {
				self.update(params);
			}
		}

		public update(params?: IDataModel) {
			const self = this;

			if (params) {
				self.surcharge1(params.surcharge1);
				self.surcharge2(params.surcharge2);
				self.surcharge3(params.surcharge3);
				self.surchargeOvertime1(params.surchargeOvertime1);
				self.surchargeOvertime2(params.surchargeOvertime2);
				self.surchargeOvertime3(params.surchargeOvertime3);
			}
		}
	}

	interface ITabSetting {
		// 日単位
		daily: number;
		// 週単位
		weekly: number;
		// 集計時間設定.週、月割増時間を集計する
		deforWorkSurchargeWeekMonth: boolean;
		// 集計時間設定.法定内残業を含める
		deforWorkLegalOverTimeWork: boolean;
		// 集計時間設定.法定外休出を含める
		deforWorkLegalHoliday: boolean;
		// 時間外超過設定.週、月割増時間を集計する
		outsideSurchargeWeekMonth: boolean
		// 時間外超過設定.法定内残業を含める
		outsidedeforWorkLegalOverTimeWork: boolean;
		// 時間外超過設定.法定外休出を含める
		outsidedeforWorkLegalHoliday: boolean;
	}

	class TabSetting {
		daily: KnockoutObservable<string> = ko.observable('0:00');
		weekly: KnockoutObservable<string> = ko.observable('0:00');
		deforWorkSurchargeWeekMonth: KnockoutObservable<boolean> = ko.observable(false);
		deforWorkLegalOverTimeWork: KnockoutObservable<boolean> = ko.observable(false);;
		deforWorkLegalHoliday: KnockoutObservable<boolean> = ko.observable(false);;
		outsideSurchargeWeekMonth: KnockoutObservable<boolean> = ko.observable(false);
		outsidedeforWorkLegalOverTimeWork: KnockoutObservable<boolean> = ko.observable(false);;
		outsidedeforWorkLegalHoliday: KnockoutObservable<boolean> = ko.observable(false);;

		public create(params?: ITabSetting) {
			const self = this;

			if (params) {
				var firstDaily = '';
				var lastDaily = params.daily % 60 + '';

				if (params.daily / 60 > 1) {
					firstDaily = Math.floor(params.daily / 60) + '';
				}

				if (lastDaily.length < 2) {
					lastDaily = '0' + lastDaily;
				}
				var firstWeekly = '';

				if (params.weekly / 60 > 1) {
					firstWeekly = Math.floor(params.weekly / 60) + '';
				}

				var lastWeekly = params.weekly % 60 + '';
				if (lastWeekly.length < 2) {
					lastWeekly = '0' + lastWeekly;
				}

				if (firstDaily === '') {
					firstDaily = '0';
				}

				if (firstWeekly === '') {
					firstWeekly = '0';
				}

				self.daily(firstDaily + ':' + lastDaily);
				self.weekly(firstWeekly + ':' + lastWeekly);
				self.deforWorkSurchargeWeekMonth(params.deforWorkSurchargeWeekMonth);
				self.deforWorkLegalOverTimeWork(params.deforWorkLegalOverTimeWork);
				self.deforWorkLegalHoliday(params.deforWorkLegalHoliday);
				self.outsideSurchargeWeekMonth(params.outsideSurchargeWeekMonth);
				self.outsidedeforWorkLegalOverTimeWork(params.outsidedeforWorkLegalOverTimeWork);
				self.outsidedeforWorkLegalHoliday(params.outsidedeforWorkLegalHoliday);
			}
		}
	}
}
