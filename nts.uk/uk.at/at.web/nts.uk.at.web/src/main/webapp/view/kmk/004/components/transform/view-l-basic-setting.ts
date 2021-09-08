/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004 {
	import Setting = nts.uk.at.view.kmk004.p.Setting;
	import SCREEN_MODE = nts.uk.at.view.kmk004.p.SCREEN_MODE;
	import IParam = nts.uk.at.view.kmk004.p.IParam;
	import DeforLaborTimeComDto = nts.uk.at.view.kmk004.p.DeforLaborTimeComDto;
	import SettlementPeriod = nts.uk.at.view.kmk004.p.SettlementPeriod;
	import SettingDto = nts.uk.at.view.kmk004.p.SettingDto;
	import SIDEBAR_TYPE = nts.uk.at.view.kmk004.p.SIDEBAR_TYPE;
	import DeforLaborTimeCom = nts.uk.at.view.kmk004.p.DeforLaborTimeCom;
	import ExcessOutsideTimeSetReg = nts.uk.at.view.kmk004.p.ExcessOutsideTimeSetReg;

	export const KMK004_API = {
		COM_INIT_SCREEN: 'screen/at/kmk004/viewL/initScreen',
		WKP_INIT_SCREEN: 'screen/at/kmk004/viewM/initScreen',
		EMP_INIT_SCREEN: 'screen/at/kmk004/viewN/initScreen',
		COM_GET_BASIC_SETTING: 'screen/at/kmk004/viewL/getBasicSetting',
		WKP_GET_BASIC_SETTING: 'screen/at/kmk004/viewM/getBasicSetting',
		EMP_GET_BASIC_SETTING: 'screen/at/kmk004/viewN/getBasicSetting',
		SHA_GET_BASIC_SETTING: 'screen/at/kmk004/viewO/getBasicSetting',
		WKP_SELECT: 'screen/at/kmk004/viewM/selectWkp',
		EMP_SELECT: 'screen/at/kmk004/viewN/selectEmp',
		SHA_SELECT: 'screen/at/kmk004/viewO/selectSha',
	}

	//Init Screen L Response
	export interface InitScreenLResponse {
		usageUnitSetting: UsageUnitSetting; //利用単位の設定を取得すs
		deforLaborMonthTimeComDto: DeforLaborMonthTimeComDto; //会社別基本設定（変形労働）を表示する
	}

	export interface UsageUnitSetting {
		workPlace: boolean; //職場
		employment: boolean; //雇用
		employee: boolean; //社員
	}

	export interface DeforLaborMonthTimeComDto {
		deforLaborTimeComDto: DeforLaborTimeComDto; //会社別変形労働法定労働時間
		comDeforLaborMonthActCalSetDto: ComDeforLaborMonthActCalSetDto; //会社別変形労働集計設定
		yearDto: YearDto; //会社別年度リストを表示する
	}

	export interface ComDeforLaborMonthActCalSetDto {
		comId: String; //会社 ID
		aggregateTimeSet: ExcessOutsideTimeSetReg; //集計時間設定
		excessOutsideTimeSet: ExcessOutsideTimeSetReg; //時間外超過設定
		settlementPeriod: SettlementPeriod; //清算期間
	}

	export interface YearDto {
		year: number; //年度
	}

	//Init Screen M Response
	export interface InitScreenMResponse {
		wkpId: WorkplaceIdDto; //職場 ID
		selectWorkplaceDeforDto: SelectWorkplaceDeforDto; //職場を選択する
	}

	export interface WorkplaceIdDto {
		workplaceId: String; //職場 ID
	}

	export interface SelectWorkplaceDeforDto {
		deforLaborMonthTimeWkpDto: DeforLaborMonthTimeWkpDto; //職場別基本設定（変形労働）を表示する
		yearDto: YearDto; //職場別年度リストを表示する
	}

	export interface DeforLaborMonthTimeWkpDto {
		deforLaborTimeWkpDto: DeforLaborTimeWkpDto; //職場別変形労働法定労働時間
		wkpDeforLaborMonthActCalSetDto: WkpDeforLaborMonthActCalSetDto; //職場別変形労働集計設定
	}

	export interface DeforLaborTimeWkpDto extends DeforLaborTimeComDto {
		wkpId: String; //職場 ID
	}

	export interface WkpDeforLaborMonthActCalSetDto extends SettingDto {
		wkpId: String; //職場 ID
	}

	//Init Screen N Response
	export interface InitScreenNResponse {
		employmentCodeDto: EmploymentCodeDto; //雇用リスト
		selectEmploymentDeforDto: SelectEmploymentDeforDto; //雇用を選択する
	}

	export interface EmploymentCodeDto {
		employmentCode: String; //雇用コード
	}

	export interface SelectEmploymentDeforDto {
		deforLaborMonthTimeEmpDto: DeforLaborMonthTimeEmpDto;　//雇用別基本設定（変形労働）を表示する
		yearDto: YearDto; //雇用別年度リストを表示する
	}

	export interface DeforLaborMonthTimeEmpDto {
		deforLaborTimeEmpDto: DeforLaborTimeEmpDto; //雇用別変形労働法定労働時間
		empDeforLaborMonthActCalSetDto: EmpDeforLaborMonthActCalSetDto; //雇用別変形労働集計設定
	}

	export interface DeforLaborTimeEmpDto extends DeforLaborTimeComDto {
		empCd: String; //雇用コード
	}

	export interface EmpDeforLaborMonthActCalSetDto extends SettingDto {
		empCd: String; //雇用コード
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

		updateInitBasicSetting(param: any, sidebarType: String) {
			switch (sidebarType) {
				case 'Com_Company':
					this.deforLaborTimeComDto.update(param.deforLaborMonthTimeComDto.deforLaborTimeComDto);
					this.settingDto.update(param.deforLaborMonthTimeComDto.comDeforLaborMonthActCalSetDto);
					break;

				case 'Com_Workplace':
					this.deforLaborTimeComDto.update(param.selectWorkplaceDeforDto.deforLaborMonthTimeWkpDto.deforLaborTimeWkpDto);
					this.settingDto.update(param.selectWorkplaceDeforDto.deforLaborMonthTimeWkpDto.wkpDeforLaborMonthActCalSetDto);
					break;

				case 'Com_Employment':
					this.deforLaborTimeComDto.update(param.selectEmploymentDeforDto.deforLaborMonthTimeEmpDto.deforLaborTimeEmpDto);
					this.settingDto.update(param.selectEmploymentDeforDto.deforLaborMonthTimeEmpDto.empDeforLaborMonthActCalSetDto);
					break;
			}

		}

		updateSelectBasicSetting(param: any, sidebarType: String) {
			switch (sidebarType) {
				case 'Com_Company':
					break;

				case 'Com_Workplace':
					this.deforLaborTimeComDto.update(param.deforLaborMonthTimeWkpDto.deforLaborTimeWkpDto);
					this.settingDto.update(param.deforLaborMonthTimeWkpDto.wkpDeforLaborMonthActCalSetDto);
					break;

				case 'Com_Employment':
					this.deforLaborTimeComDto.update(param.deforLaborMonthTimeEmpDto.deforLaborTimeEmpDto);
					this.settingDto.update(param.deforLaborMonthTimeEmpDto.empDeforLaborMonthActCalSetDto);
					break;
					
				case 'Com_Person':
					this.deforLaborTimeComDto.update(param.deforLaborMonthTimeShaDto.deforLaborTimeShaDto);
					this.settingDto.update(param.deforLaborMonthTimeShaDto.shaDeforLaborMonthActCalSetDto);
					break;
			}

		}

		updateReloadBasicSetting(param: any, sidebarType: String) {
			switch (sidebarType) {
				case 'Com_Company':
					this.deforLaborTimeComDto.update(param.deforLaborTimeComDto);
					this.settingDto.update(param.comDeforLaborMonthActCalSetDto);
					break;

				case 'Com_Workplace':
					this.deforLaborTimeComDto.update(param.deforLaborTimeWkpDto);
					this.settingDto.update(param.wkpDeforLaborMonthActCalSetDto);
					break;

				case 'Com_Employment':
					this.deforLaborTimeComDto.update(param.deforLaborTimeEmpDto);
					this.settingDto.update(param.empDeforLaborMonthActCalSetDto);
					break;

				case 'Com_Person':
					this.deforLaborTimeComDto.update(param.deforLaborTimeShaDto);
					this.settingDto.update(param.shaDeforLaborMonthActCalSetDto);
					break;
			}
		}

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
                            <div class="content1" data-bind="i18n: txtRepeatAtr "></div>
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
                            <div class="content1" data-bind="i18n: aggLegalOverTimeWork "></div>
                        </td>
                        <td>
                            <div class="content1" data-bind="i18n: aggLegalHoliday "></div>
                        </td>
                        <td>
                            <div class="content1" data-bind="i18n: excLegalOverTimeWork "></div>
                        </td>
                        <td colspan="2">
                            <div class="content1" data-bind="i18n: excLegalHoliday "></div>
                        </td>
                    </tr> 
                </tbody>
            </table>
        </div>
        <style type="text/css" rel="stylesheet">
					.content1 { white-space: nowrap; }
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
		isLoadData: KnockoutObservable<boolean>;
		screenData = new TransformScreenData();
		txtRepeatAtr: KnockoutObservable<String> = ko.observable('KMK004_297');
		aggLegalOverTimeWork: KnockoutObservable<String> = ko.observable('KMK004_300');
		aggLegalHoliday: KnockoutObservable<String> = ko.observable('KMK004_302');
		excLegalOverTimeWork: KnockoutObservable<String> = ko.observable('KMK004_300');
		excLegalHoliday: KnockoutObservable<String> = ko.observable('KMK004_302');

		constructor(private params: IParam) {
			super();
			const vm = this;

			vm.initData();
			vm.isLoadData = vm.params.isLoadData; 
			vm.isLoadData.subscribe((value: boolean) => {
				if (value) {
					vm.reloadData();
					vm.isLoadData(false);
				}
			});
		}

		mounted() {
			const vm = this;
			switch (vm.params.sidebarType) {
				//会社
				case 'Com_Company':
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
		}


		initData() {
			const vm = this;
			vm.$blockui("grayout");

			switch (vm.params.sidebarType) {

				//会社
				case 'Com_Company':
					vm.$ajax(KMK004_API.COM_INIT_SCREEN).done((data: InitScreenLResponse) => {
						vm.bindingInitData(data);
					}).always(() => vm.$blockui("clear"));
					break;

				//職場	
				case 'Com_Workplace':
					vm.$ajax(KMK004_API.WKP_INIT_SCREEN).done((data: InitScreenMResponse) => {
						vm.bindingInitData(data);
					}).always(() => vm.$blockui("clear"));
					break;

				//雇用
				case 'Com_Employment':
					vm.$ajax(KMK004_API.EMP_INIT_SCREEN).done((data: InitScreenNResponse) => {
						vm.bindingInitData(data);
					}).always(() => vm.$blockui("clear"));
					break;

				//社員
				case 'Com_Person':
					break;
			}

		}

		loadData() {
			const vm = this;
			vm.$blockui("grayout");

			switch (vm.params.sidebarType) {

				//会社
				case 'Com_Company':
					break;

				//職場	
				case 'Com_Workplace':
					vm.$ajax(KMK004_API.WKP_SELECT + "/" + vm.params.wkpId()).done((data: any) => {
						vm.bindingSelectData(data);
					}).always(() => vm.$blockui("clear"));
					break;

				//雇用
				case 'Com_Employment':
					vm.$ajax(KMK004_API.EMP_SELECT + "/" + vm.params.empCode()).done((data: any) => {
						vm.bindingSelectData(data);
					}).always(() => vm.$blockui("clear"));
					break;

				//社員
				case 'Com_Person':
					vm.$ajax(KMK004_API.SHA_SELECT + "/" + vm.params.empId()).done((data: any) => {
						vm.bindingSelectData(data);
					}).always(() => vm.$blockui("clear"));
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

		bindingInitData(data: any) {
			const vm = this;
			switch (vm.params.sidebarType) {
				//会社
				case 'Com_Company':
					if (data.deforLaborMonthTimeComDto.deforLaborTimeComDto != null && data.deforLaborMonthTimeComDto.comDeforLaborMonthActCalSetDto != null) {
						vm.visibleL4(true);
						vm.mode(SCREEN_MODE.UPDATE);
						vm.screenData.updateInitBasicSetting(data, 'Com_Company');
						vm.txtRepeatAtr(vm.screenData.settingDto.settlementPeriod.repeatAtr() == true ? 'KMK004_296' : 'KMK004_297' );
						vm.aggLegalOverTimeWork(vm.screenData.settingDto.aggregateTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.aggLegalHoliday(vm.screenData.settingDto.aggregateTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302' );
						vm.excLegalOverTimeWork(vm.screenData.settingDto.excessOutsideTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.excLegalHoliday(vm.screenData.settingDto.excessOutsideTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302');
					} else {
						vm.visibleL4(false);
					}
					break;

				//職場
				case 'Com_Workplace':
					if (data.selectWorkplaceDeforDto.deforLaborMonthTimeWkpDto.deforLaborTimeWkpDto != null && data.selectWorkplaceDeforDto.deforLaborMonthTimeWkpDto.wkpDeforLaborMonthActCalSetDto != null) {
						vm.visibleL4(true);
						vm.mode(SCREEN_MODE.UPDATE);
						vm.screenData.updateInitBasicSetting(data, 'Com_Workplace');
						vm.txtRepeatAtr(vm.screenData.settingDto.settlementPeriod.repeatAtr() == true ? 'KMK004_296' : 'KMK004_297' );
						vm.aggLegalOverTimeWork(vm.screenData.settingDto.aggregateTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.aggLegalHoliday(vm.screenData.settingDto.aggregateTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302' );
						vm.excLegalOverTimeWork(vm.screenData.settingDto.excessOutsideTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.excLegalHoliday(vm.screenData.settingDto.excessOutsideTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302');
					} else {
						vm.visibleL4(false);
					}
					break;

				//雇用
				case 'Com_Employment':
					if (data.selectEmploymentDeforDto.deforLaborMonthTimeEmpDto.deforLaborTimeEmpDto != null && data.selectEmploymentDeforDto.deforLaborMonthTimeEmpDto.empDeforLaborMonthActCalSetDto != null) {
						vm.visibleL4(true);
						vm.mode(SCREEN_MODE.UPDATE);
						vm.screenData.updateInitBasicSetting(data, 'Com_Employment');
						vm.txtRepeatAtr(vm.screenData.settingDto.settlementPeriod.repeatAtr() == true ? 'KMK004_296' : 'KMK004_297' );
						vm.aggLegalOverTimeWork(vm.screenData.settingDto.aggregateTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.aggLegalHoliday(vm.screenData.settingDto.aggregateTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302' );
						vm.excLegalOverTimeWork(vm.screenData.settingDto.excessOutsideTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.excLegalHoliday(vm.screenData.settingDto.excessOutsideTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302');
					} else {
						vm.visibleL4(false);
					}
					break;

				//社員
				case 'Com_Person':
					break;

			}

		}

		bindingSelectData(data: any) {
			const vm = this;
			switch (vm.params.sidebarType) {
				//会社
				case 'Com_Company':
					break;

				//職場
				case 'Com_Workplace':
					if (data.deforLaborMonthTimeWkpDto.deforLaborTimeWkpDto != null && data.deforLaborMonthTimeWkpDto.wkpDeforLaborMonthActCalSetDto != null) {
						vm.visibleL4(true);
						vm.mode(SCREEN_MODE.UPDATE);
						vm.screenData.updateSelectBasicSetting(data, 'Com_Workplace');
						vm.txtRepeatAtr(vm.screenData.settingDto.settlementPeriod.repeatAtr() == true ? 'KMK004_296' : 'KMK004_297' );
						vm.aggLegalOverTimeWork(vm.screenData.settingDto.aggregateTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.aggLegalHoliday(vm.screenData.settingDto.aggregateTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302' );
						vm.excLegalOverTimeWork(vm.screenData.settingDto.excessOutsideTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.excLegalHoliday(vm.screenData.settingDto.excessOutsideTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302');
					} else {
						vm.visibleL4(false);
					}
					break;

				//雇用
				case 'Com_Employment':
					if (data.deforLaborMonthTimeEmpDto.deforLaborTimeEmpDto != null && data.deforLaborMonthTimeEmpDto.empDeforLaborMonthActCalSetDto != null) {
						vm.visibleL4(true);
						vm.mode(SCREEN_MODE.UPDATE);
						vm.screenData.updateSelectBasicSetting(data, 'Com_Employment');
						vm.txtRepeatAtr(vm.screenData.settingDto.settlementPeriod.repeatAtr() == true ? 'KMK004_296' : 'KMK004_297' );
						vm.aggLegalOverTimeWork(vm.screenData.settingDto.aggregateTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.aggLegalHoliday(vm.screenData.settingDto.aggregateTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302' );
						vm.excLegalOverTimeWork(vm.screenData.settingDto.excessOutsideTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.excLegalHoliday(vm.screenData.settingDto.excessOutsideTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302');
					} else {
						vm.visibleL4(false);
					}
					break;

				//社員
				case 'Com_Person':
					if (data.deforLaborMonthTimeShaDto.deforLaborTimeShaDto != null && data.deforLaborMonthTimeShaDto.shaDeforLaborMonthActCalSetDto != null) {
						vm.visibleL4(true);
						vm.mode(SCREEN_MODE.UPDATE);
						vm.screenData.updateSelectBasicSetting(data, 'Com_Person');
						vm.txtRepeatAtr(vm.screenData.settingDto.settlementPeriod.repeatAtr() == true ? 'KMK004_296' : 'KMK004_297' );
						vm.aggLegalOverTimeWork(vm.screenData.settingDto.aggregateTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.aggLegalHoliday(vm.screenData.settingDto.aggregateTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302' );
						vm.excLegalOverTimeWork(vm.screenData.settingDto.excessOutsideTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.excLegalHoliday(vm.screenData.settingDto.excessOutsideTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302');
					} else {
						vm.visibleL4(false);
					}
				
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
						vm.txtRepeatAtr(vm.screenData.settingDto.settlementPeriod.repeatAtr() == true ? 'KMK004_296' : 'KMK004_297' );
						vm.aggLegalOverTimeWork(vm.screenData.settingDto.aggregateTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.aggLegalHoliday(vm.screenData.settingDto.aggregateTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302' );
						vm.excLegalOverTimeWork(vm.screenData.settingDto.excessOutsideTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.excLegalHoliday(vm.screenData.settingDto.excessOutsideTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302');
					} else {
						vm.visibleL4(false);
					}
					break;

				case 'Com_Workplace':
					if (data.deforLaborTimeWkpDto != null && data.wkpDeforLaborMonthActCalSetDto != null) {
						vm.visibleL4(true);
						vm.mode(SCREEN_MODE.UPDATE);
						vm.screenData.updateReloadBasicSetting(data, 'Com_Workplace');
						vm.txtRepeatAtr(vm.screenData.settingDto.settlementPeriod.repeatAtr() == true ? 'KMK004_296' : 'KMK004_297' );
						vm.aggLegalOverTimeWork(vm.screenData.settingDto.aggregateTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.aggLegalHoliday(vm.screenData.settingDto.aggregateTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302' );
						vm.excLegalOverTimeWork(vm.screenData.settingDto.excessOutsideTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.excLegalHoliday(vm.screenData.settingDto.excessOutsideTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302');
					} else {
						vm.visibleL4(false);
					}
					break;
				case 'Com_Employment':
					if (data.deforLaborTimeEmpDto != null && data.empDeforLaborMonthActCalSetDto != null) {
						vm.visibleL4(true);
						vm.mode(SCREEN_MODE.UPDATE);
						vm.screenData.updateReloadBasicSetting(data, 'Com_Employment');
						vm.txtRepeatAtr(vm.screenData.settingDto.settlementPeriod.repeatAtr() == true ? 'KMK004_296' : 'KMK004_297' );
						vm.aggLegalOverTimeWork(vm.screenData.settingDto.aggregateTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.aggLegalHoliday(vm.screenData.settingDto.aggregateTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302' );
						vm.excLegalOverTimeWork(vm.screenData.settingDto.excessOutsideTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.excLegalHoliday(vm.screenData.settingDto.excessOutsideTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302');
					} else {
						vm.visibleL4(false);
					}
					break;

				case 'Com_Person':
					if (data.deforLaborTimeShaDto != null && data.shaDeforLaborMonthActCalSetDto != null) {
						vm.visibleL4(true);
						vm.mode(SCREEN_MODE.UPDATE);
						vm.screenData.updateReloadBasicSetting(data, 'Com_Person');
						vm.txtRepeatAtr(vm.screenData.settingDto.settlementPeriod.repeatAtr() == true ? 'KMK004_296' : 'KMK004_297' );
						vm.aggLegalOverTimeWork(vm.screenData.settingDto.aggregateTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.aggLegalHoliday(vm.screenData.settingDto.aggregateTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302' );
						vm.excLegalOverTimeWork(vm.screenData.settingDto.excessOutsideTimeSet.legalOverTimeWork() == true ? 'KMK004_299' : 'KMK004_300' );
						vm.excLegalHoliday(vm.screenData.settingDto.excessOutsideTimeSet.legalHoliday() == true ? 'KMK004_301' : 'KMK004_302');
					} else {
						vm.visibleL4(false);
					}
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
