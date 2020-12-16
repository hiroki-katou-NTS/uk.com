/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004 {
	import TransformScreenData = nts.uk.at.view.kmk004.p.TransformScreenData;
	import SCREEN_MODE = nts.uk.at.view.kmk004.p.SCREEN_MODE;
	import IParam = nts.uk.at.view.kmk004.p.IParam;
	import DeforLaborTimeComDto = nts.uk.at.view.kmk004.p.DeforLaborTimeComDto;
	import SettingDto = nts.uk.at.view.kmk004.p.SettingDto;

	export const KMK004_API = {
		COM_INIT_SCREEN: 'screen/at/kmk004/viewL/initScreen',
		WKP_INIT_SCREEN: 'screen/at/kmk004/viewM/initScreen',
		EMP_INIT_SCREEN: 'screen/at/kmk004/viewN/initScreen',
		COM_GET_BASIC_SETTING: 'screen/at/kmk004/viewL/getBasicSetting',
		WKP_GET_BASIC_SETTING: 'screen/at/kmk004/viewM/getBasicSetting',
		EMP_GET_BASIC_SETTING: 'screen/at/kmk004/viewN/getBasicSetting',
		SHA_GET_BASIC_SETTING: 'screen/at/kmk004/viewO/getBasicSetting'
	}

	export interface SettingResponse {
		deforLaborMonthTimeComDto: DeforLaborMonthTimeComDto;
	}

	export interface DeforLaborMonthTimeComDto {
		deforLaborTimeComDto: DeforLaborTimeComDto; //会社別変形労働法定労働時間
		comDeforLaborMonthActCalSetDto: SettingDto; //会社別変形労働集計設定
	}

	export interface EmpSettingResponse {
		employmentCds: EmploymentCds;
		selectEmploymentDeforDto: SelectEmploymentDeforDto;
	}

	export interface EmploymentCds {
		employmentCode: String;
	}

	export interface SelectEmploymentDeforDto {
		deforLaborMonthTimeWkpDto: DeforLaborMonthTimeComDto;
		years: Years;
	}

	export interface Years {
		years: number;
	}

	const template = `
        <div class="table-view" data-bind="visible: visibleL4 ">
            <table>
                <tbody>
                    <tr>
                        <th>
                            <div class="content1" data-bind="i18n: 'KMK004_213'"></div>
                        </th>
                        <th>
                            <div class="content1" data-bind="i18n: 'KMK004_214'"></div>
                        </th>
						<th>
                            <div class="content1" data-bind="i18n: 'KMK004_293'"></div>
                        </th>
						<th>
                            <div class="content1" data-bind="i18n: 'KMK004_294'"></div>
                        </th>
						<th>
                            <div class="content1" data-bind="i18n: 'KMK004_295'"></div>
                        </th>
                    </tr>
                    <tr>
                       <td>
                            <div class="content1" data-bind="text: toHHMM(screenData.deforLaborTimeComDto.dailyTime.time())"></div>
                        </td>
                        <td>
                            <div class="content1" data-bind="text: toHHMM(screenData.deforLaborTimeComDto.weeklyTime.time())"></div>
                        </td>
						<td>
                            <div class="content1" data-bind="text: screenData.settingDto.settlementPeriod.period() + 'ヵ月'"></div>
                        </td>
						<td>
                            <div class="content1" data-bind="text: screenData.settingDto.settlementPeriod.startMonth() + '月'"></div>
                        </td>
						<td>
                            <div class="content1" data-bind="i18n: screenData.settingDto.settlementPeriod.repeatAtr() == true ? 'KMK004_296' : 'KMK004_297' "></div>
                        </td>
                    </tr>
                    <tr>
                        <th colspan="2">
                            <div data-bind="i18n: 'KMK004_215'"></div>
                        </th>
 						<th colspan="3">
                            <div data-bind="i18n: 'KMK004_220'"></div>
                        </th>
                    </tr>
					
                    <tr>
                        <td>
                            <div class="content1" data-bind="i18n:(screenData.settingDto.aggregateTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300') "></div>
                        </td>
                        <td>
                            <div class="content1" data-bind="i18n: screenData.settingDto.aggregateTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302' "></div>
                        </td>
                        <td>
                            <div class="content1" data-bind="i18n: screenData.settingDto.excessOutsideTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' "></div>
                        </td>
                        <td colspan="2">
                            <div class="content1" data-bind="i18n: screenData.settingDto.excessOutsideTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302' "></div>
                        </td>
                    </tr> 
                </tbody>
            </table>
        </div>
        <style type="text/css" rel="stylesheet">
            .table-view {
                padding: 15px;
                border: 2px solid #B1B1B1;
                border-radius: 15px;
				width: fit-content;
				margin: 15px 0px 15px 15px;
            }

            .table-view th {
                background: #E0F59E;
 				border: solid grey 1px;
				padding: 3px 10px;
				text-align: center;
            }
			
			.table-view th:nth-child(1) {
				width: 134px;
			}
			
			.table-view th:nth-child(2) {
				width: 134px;
			}
			
			.table-view th:nth-child(3) {
				width: 134px;
			}
			
			.table-view th:nth-child(4) {
				width: 67px;
			}
			
			.table-view th:nth-child(5) {
				width: 60px;
			}
			
            .table-view tr, .table-view td {
                border: solid grey 1px;
				padding: 3px 10px;
              }
            
        </style>
        <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
    `;

	@component({
		name: 'view-l-basic-setting',
		template
	})

	class BasicSetting extends ko.ViewModel {
		mode: KnockoutObservable<number> = ko.observable(SCREEN_MODE.ADD);
		visibleL4: KnockoutObservable<boolean> = ko.observable(false);

		screenData = new TransformScreenData();

		isLoadData: KnockoutObservable<boolean>;

		constructor(private params: IParam) {
			super();
			const vm = this;

			switch (vm.params.sidebarType) {
				//会社
				case 'Com_Company':
					vm.loadData();
					break;

				//職場
				case 'Com_Workplace':
					vm.params.wkpId.subscribe((data: any) => {
						if (data != '') {
							vm.loadData();
						}
					});
					break;

				//雇用
				case 'Com_Employment':
					vm.params.empCode.subscribe((data: any) => {
						if (data != '') {
							vm.loadData();
						}
					});
					break;

				//社員
				case 'Com_Person':
					vm.params.empId.subscribe((data: any) => {
						if (data != '') {
							vm.loadData();
						}
					});
					break;
			}

			vm.isLoadData = vm.params.isLoadData;
			vm.isLoadData.subscribe((value: boolean) => {
				if (value) {
					vm.reloadData();
					vm.isLoadData(false);
				}
			});

		}

		mounted() {
		}

		loadData() {
			const vm = this;
			vm.$blockui("grayout");

			switch (vm.params.sidebarType) {

				//会社
				case 'Com_Company':
					vm.$ajax(KMK004_API.COM_INIT_SCREEN).done((data: SettingResponse) => {
						vm.bindingData(data);
					}).always(() => vm.$blockui("clear"));
					break;

				//職場	
				case 'Com_Workplace':
					vm.$ajax(KMK004_API.WKP_INIT_SCREEN).done((data: SettingResponse) => {
						vm.bindingData(data);
					}).always(() => vm.$blockui("clear"));
					break;

				//雇用
				case 'Com_Employment':
					vm.$ajax(KMK004_API.EMP_INIT_SCREEN).done((data: SettingResponse) => {
						vm.bindingData(data);
					}).always(() => vm.$blockui("clear"));
					break;

				//社員
				case 'Com_Person':
					break;
			}

		}

		reloadData() {
			const vm = this;
			vm.$blockui("grayout");

			switch (vm.params.sidebarType) {

				//会社
				case 'Com_Company':
					vm.$ajax(KMK004_API.COM_GET_BASIC_SETTING).done((data: DeforLaborMonthTimeComDto) => {
						vm.bindingReloadData(data);
					}).always(() => vm.$blockui("clear"));
					break

				//職場
				case 'Com_Workplace':
					vm.$ajax(KMK004_API.WKP_GET_BASIC_SETTING + "/" + vm.params.wkpId()).done((data: DeforLaborMonthTimeComDto) => {
						vm.bindingReloadData(data);
					}).always(() => vm.$blockui("clear"));
					break

				//雇用
				case 'Com_Employment':
					vm.$ajax(KMK004_API.EMP_GET_BASIC_SETTING + "/" + vm.params.empCode()).done((data: DeforLaborMonthTimeComDto) => {
						vm.bindingReloadData(data);
					}).always(() => vm.$blockui("clear"));
					break

				//社員
				case 'Com_Person':
					vm.$ajax(KMK004_API.SHA_GET_BASIC_SETTING + "/" + vm.params.empId()).done((data: DeforLaborMonthTimeComDto) => {
						vm.bindingReloadData(data);
					}).always(() => vm.$blockui("clear"));
					break
			}

		}

		bindingData(data: any) {
			const vm = this;
			switch (vm.params.sidebarType) {
				//会社
				case 'Com_Company':
					if (data.deforLaborMonthTimeComDto.deforLaborTimeComDto != null && data.deforLaborMonthTimeComDto.comDeforLaborMonthActCalSetDto != null) {
						vm.visibleL4(true);
						vm.mode(SCREEN_MODE.UPDATE);
						vm.screenData.updateBasicSetting(data, 'Com_Company');
					} else {
						vm.visibleL4(false);
					}
					break;

				//職場
				case 'Com_Workplace':
					break;

				//雇用
				case 'Com_Employment':
					if (data.selectEmploymentDeforDto.deforLaborMonthTimeEmpDto.deforLaborTimeEmpDto != null && data.selectEmploymentDeforDto.deforLaborMonthTimeEmpDto.empDeforLaborMonthActCalSetDto != null) {
						vm.visibleL4(true);
						vm.mode(SCREEN_MODE.UPDATE);
						vm.screenData.updateBasicSetting(data, 'Com_Employment');
					} else {
						vm.visibleL4(false);
					}
					break;

				//社員
				case 'Com_Person':
					break;

			}

		}

		bindingReloadData(data: any) {
			const vm = this;

			switch (vm.params.sidebarType) {
				case 'Com_Company':
					if (data.deforLaborTimeComDto != null && data.comDeforLaborMonthActCalSetDto != null) {
						vm.visibleL4(true);
						vm.mode(SCREEN_MODE.UPDATE);
						vm.screenData.updateReloadBasicSetting(data, 'Com_Company');
					} else {
						vm.visibleL4(false);
					}
					break;
					
				case 'Com_Workplace':
					break;
					
				case 'Com_Employment':
					if (data.deforLaborTimeEmpDto != null && data.empDeforLaborMonthActCalSetDto != null) {
						vm.visibleL4(true);
						vm.mode(SCREEN_MODE.UPDATE);
						vm.screenData.updateReloadBasicSetting(data, 'Com_Employment');
					} else {
						vm.visibleL4(false);
					}
					break;

				case 'Com_Person':
					break;
			}
		}

		toHHMM(min_num: number) {
			var hours = Math.floor(min_num / 60);
			var minutes = Math.floor(min_num - (hours * 60));
			var hoursStr = String(hours);
			var minutesStr = String(minutes);

			if (hours < 10) { hoursStr = "0" + hours; }
			if (minutes < 10) { minutesStr = "0" + minutes; }

			return hoursStr + ':' + minutesStr;
		}

	}

}
