/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004 {
	import IResponse = nts.uk.at.view.kmk004.p.IResponse;
	import TransformScreenData = nts.uk.at.view.kmk004.p.TransformScreenData;
	import KMK004_P_API = nts.uk.at.view.kmk004.p.KMK004_P_API;
	import SCREEN_MODE = nts.uk.at.view.kmk004.p.SCREEN_MODE;
	import IParam = nts.uk.at.view.kmk004.p.IParam;

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

		constructor(private params: IParam) {
			super();
			const vm = this;
			vm.params = {sidebarType : 'Com_Company', wkpId: '', empCode :'', empId: '', titleName:'', deforLaborTimeComDto: null, settingDto: null}
			
		}

		mounted() {
			const vm = this;
			vm.loadData();
		}

		loadData() {
			const vm = this;
			vm.$blockui("grayout");

			//会社
			if (vm.params.sidebarType == 'Com_Company') {
				vm.$ajax(KMK004_P_API.COM_GET_BASIC_SETTING, ).done((data: IResponse) => {
					vm.bindingData(data);

				}).always(() => vm.$blockui("clear"));
			}
	
			//職場
			if (vm.params.sidebarType == 'Com_Workplace') {
				vm.$ajax(KMK004_P_API.WKP_GET_BASIC_SETTING + "/" + ko.toJS(vm.params.wkpId)).done((data: IResponse) => {
					vm.bindingData(data);
				}).always(() => vm.$blockui("clear"));

			}

			//雇用
			if (vm.params.sidebarType == 'Com_Employment') {
				vm.$ajax(KMK004_P_API.EMP_GET_BASIC_SETTING + "/" + ko.toJS(vm.params.empCode)).done((data: IResponse) => {
					vm.bindingData(data);
				}).always(() => vm.$blockui("clear"));

			}

			//社員
			if (vm.params.sidebarType == 'Com_Person') {
				vm.$ajax(KMK004_P_API.SHA_GET_BASIC_SETTING + "/" + ko.toJS(vm.params.empId)).done((data: IResponse) => {
					vm.bindingData(data);
				}).always(() => vm.$blockui("clear"));
			}

		}

		bindingData(data: IResponse) {
			const vm = this;
			if (data.deforLaborTimeComDto != null && data.settingDto != null) {
				vm.visibleL4(true);
				vm.mode(SCREEN_MODE.UPDATE);
				vm.screenData.update(data);
			} else {
				vm.visibleL4(false);
			}
		}
		
		toHHMM(min_num: number) {
		    var hours   = Math.floor(min_num / 60);
		    var minutes = Math.floor(min_num - (hours * 60));
			var hoursStr = String(hours);
			var minutesStr =String(minutes);
			
		    if (hours   < 10) {hoursStr   = "0" + hours;}
		    if (minutes < 10) {minutesStr = "0" + minutes;}
		    
		    return hoursStr +':' + minutesStr;
		}
		
	}

}
