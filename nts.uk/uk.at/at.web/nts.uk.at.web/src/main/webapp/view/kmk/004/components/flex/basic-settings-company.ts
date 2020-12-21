/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

import IDisplayFlexBasicSettingByCompanyDto = nts.uk.at.kmk004.components.flex.IDisplayFlexBasicSettingByCompanyDto;

const template = `
	<div   style="margin-top: 10px; margin-bottom:15px;"  >
		<div data-bind="ntsFormLabel: {inline:true} , i18n: 'KMK004_229'"></div>
		<button data-bind="click: openKDialog , i18n: 'KMK004_231'" ></button>
	</div>
	<div data-bind="visible:screenData().comFlexMonthActCalSet() != null" class="div_line" 
		style="
		width: auto;
		border-radius: 15px;
	    border: 2px solid #B1B1B1;
	    padding: 15px;
		margin-left:15px;
		">
		<table class="basic-settings">
			<tr class="bg-green">
				<th data-bind="i18n: 'KMK004_254'"></th>
				<th data-bind="i18n: 'KMK004_255'"></th>
				<th data-bind="i18n: 'KMK004_256'"></th>
				<th data-bind="i18n: 'KMK004_257'"></th>
			</tr>
			<tr>
				<td data-bind="text:getSettlePeriodText()"></td>
				<td data-bind="text:getStartMonth()"></td>
				<td data-bind="text:getStartPeriod()"></td>
				<td data-bind="text:getCarryforwardSetText()"></td>
			</tr>
		</table >
		<div style="width:auto; margin-top: -1px;">
			<table style="width: 100%;" class="basic-settings">
				<tr class="bg-green">
					<th data-bind="i18n: 'KMK004_258'"></th>
					<th data-bind="i18n: 'KMK004_262' , visible: screenMode== 'Com_Company' "></th>
				</tr>
				<tr>
					<td data-bind="text:getAggrMethodText()"></td>
					<td data-bind="text:getReferenceText(), visible: screenMode== 'Com_Company'"></td>
				</tr>
			</table>
			<table style="width: 100%;" class="basic-settings">
				<tr class="bg-green">
					<th colspan='2' data-bind="i18n: 'KMK004_259'"></th>
					<th data-bind="i18n: 'KMK004_261'"></th>
				</tr>
				<tr>
					<td data-bind="text:getIncludeOverTimeText()"></td>
					<td data-bind="text:getIncludeIllegalHdwk()"></td>
					<td data-bind="text:getAggregateSetText()"></td>
				</tr>
			</table>
		</div>
	</div>
	`;

@component({
	name: 'basic-settings-company',
	template
})

class BasicSettingsCompany extends ko.ViewModel {

	screenData: KnockoutObservable<FlexScreenData>;
	screenMode: string = '';

	created(params: IParam) {
		const vm = this;
		vm.screenData = params.screenData;
		vm.screenMode = params.screenMode;
	}

	openKDialog() {

		const vm = this;

		if (vm.screenMode == 'Com_Company') {
			vm.screenData().selectedName(vm.screenMode);
		}

		vm.$window.modal('/view/kmk/004/k/index.xhtml', { screenMode: vm.screenMode, title: vm.screenData().selectedName() }).then(() => {
			vm.reloadAfterChangeSetting();
		});
	}

	reloadAfterChangeSetting() {
		const vm = this;
		let url;

		if (vm.screenMode == 'Com_Company') {
			url = API_G_URL.CHANGE_SETTING;
		}

		if (vm.screenMode == 'Com_Workplace') {
			url = API_H_URL.CHANGE_SETTING + vm.screenData().selected();
		}

		if (vm.screenMode == 'Com_Employment') {
			url = API_I_URL.CHANGE_SETTING + vm.screenData().selected();
		}

		if (vm.screenMode == 'Com_Person') {

			let selectedEmp: any = _.find($('#employee-list').getDataList(), ['code', vm.screenData().selected()]);

			url = API_J_URL.CHANGE_SETTING + selectedEmp.id;
		}

		vm.$blockui('invisible');
		vm.$ajax(url).done((setting: IDisplayFlexBasicSettingByCompanyDto) => {
			vm.screenData().comFlexMonthActCalSet(setting.flexMonthActCalSet);
			vm.screenData().getFlexPredWorkTime(setting.flexPredWorkTime);
		}).always(() => { vm.$blockui('clear'); });

	}

	getStartMonth() {
		const vm = this;
		if (!vm.screenData().comFlexMonthActCalSet()) {
			return '';
		}
		return vm.screenData().comFlexMonthActCalSet().insufficSet.startMonth + '月';
	}

	getStartPeriod() {
		const vm = this;
		if (!vm.screenData().comFlexMonthActCalSet()) {
			return '';
		}
		return vm.screenData().comFlexMonthActCalSet().insufficSet.period + 'ヵ月'
	}

	getSettlePeriodText() {
		const vm = this;
		if (!vm.screenData().comFlexMonthActCalSet()) {
			return '';
		}
		return vm.$i18n.text(_.find(__viewContext.enums.SettlePeriod, ['value', vm.screenData().comFlexMonthActCalSet().insufficSet.settlePeriod]).name);
	}

	getCarryforwardSetText() {
		const vm = this;
		if (!vm.screenData().comFlexMonthActCalSet()) {
			return '';
		}
		return vm.$i18n.text(_.find(__viewContext.enums.CarryforwardSetInShortageFlex, ['value', vm.screenData().comFlexMonthActCalSet().insufficSet.carryforwardSet]).name);
	}

	getAggrMethodText() {
		const vm = this;
		if (!vm.screenData().comFlexMonthActCalSet()) {
			return '';
		}
		return vm.$i18n.text(_.find(__viewContext.enums.FlexAggregateMethod, ['value', vm.screenData().comFlexMonthActCalSet().aggrMethod]).name);
	}

	getReferenceText() {
		const vm = this;
		if (!vm.screenData().getFlexPredWorkTime()) {
			return '';
		}
		return vm.$i18n.text(_.find(__viewContext.enums.ReferencePredTimeOfFlex, ['value', vm.screenData().getFlexPredWorkTime().reference]).name);
	}

	getIncludeOverTimeText() {
		const vm = this;
		if (!vm.screenData().comFlexMonthActCalSet()) {
			return '';
		}
		return vm.$i18n.text(vm.screenData().comFlexMonthActCalSet().flexTimeHandle.includeOverTime == true ? "KMK004_283" : "KMK004_260");
	}

	getIncludeIllegalHdwk() {
		const vm = this;
		if (!vm.screenData().comFlexMonthActCalSet()) {
			return '';
		}
		return vm.$i18n.text(vm.screenData().comFlexMonthActCalSet().flexTimeHandle.includeIllegalHdwk == true ? "KMK004_284" : "KMK004_337");
	}

	getAggregateSetText() {
		const vm = this;
		if (!vm.screenData().comFlexMonthActCalSet()) {
			return '';
		}
		return vm.$i18n.text(_.find(__viewContext.enums.AggregateSetting, ['value', vm.screenData().comFlexMonthActCalSet().legalAggrSet.aggregateSet]).name);
	}

}

interface IParam {
	screenData: KnockoutObservable<FlexScreenData>;
	screenMode: string;
}



