/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

import IDisplayFlexBasicSettingByCompanyDto = nts.uk.at.kmk004.components.flex.IDisplayFlexBasicSettingByCompanyDto;

const template = `
	<div   style="margin-top: 10px; margin-bottom:15px;"  >
		<div data-bind="ntsFormLabel: {inline:true , text: $i18n('KMK004_229')} "></div>
		<button data-bind="enable:enableKButton() == true,click: openKDialog , i18n:getTextKButton()" ></button>
	</div>
	<div data-bind="visible:screenData().flexMonthActCalSet() != null" class="div_line" 
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

		let selected = vm.screenMode == 'Com_Person' ? _.find($('#employee-list').getDataList(), ['code', vm.screenData().selected()]).id : vm.screenData().selected();

		vm.$window.modal('/view/kmk/004/k/index.xhtml', { screenMode: vm.screenMode, title: vm.screenData().selectedName(), selected: selected }).then(() => {
			vm.reloadAfterChangeSetting();
		});
	}

	getTextKButton() {
		const vm = this;
		if (vm.screenMode == 'Com_Company') {
			return 'KMK004_231';
		}

		if (vm.screenMode == 'Com_Workplace') {
			return vm.screenData().flexMonthActCalSet() == null ? 'KMK004_338' : 'KMK004_339';
		}

		if (vm.screenMode == 'Com_Employment') {
			return vm.screenData().flexMonthActCalSet() == null ? 'KMK004_340' : 'KMK004_341';
		}

		if (vm.screenMode == 'Com_Person') {
			return vm.screenData().flexMonthActCalSet() == null ? 'KMK004_342' : 'KMK004_343';
		}
	}




	enableKButton() {
		const vm = this;
		if (vm.screenMode == 'Com_Company') {
			return true;
		}

		return vm.screenData().selectedName() != null;
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
		vm.$ajax(url).done((setting: any) => {
			vm.setAlreadySettingList(setting.alreadySettings);
			vm.screenData().flexMonthActCalSet(vm.screenMode == 'Com_Company' ? setting.comFlexMonthActCalSet : setting.flexMonthActCalSet);
			vm.screenData().comFlexMonthActCalSet(setting.comFlexMonthActCalSet);
		}).always(() => { vm.$blockui('clear'); });

	}

	setAlreadySettingList(data: Array<string>) {
		const vm = this;
		if (vm.screenMode == 'Com_Workplace')
			vm.screenData().alreadySettingList(_.map(data, (item) => { return { workplaceId: item, isAlreadySetting: true } }));

		if (vm.screenMode == 'Com_Employment')
			vm.screenData().alreadySettingList(_.map(data, (item) => { return { code: item, isAlreadySetting: true } }));

		if (vm.screenMode == 'Com_Person')
			vm.screenData().alreadySettingList(
				_.map(data, (selectedId) => {
					let emp: any = _.find($('#employee-list').getDataList(), ['id', selectedId]);
					if (!emp) {
						return { code: null, isAlreadySetting: false };
					}
					return { code: emp.code, isAlreadySetting: true };
				})
			);
	}

	getStartMonth() {
		const vm = this;
		if (!vm.screenData().flexMonthActCalSet()) {
			return '';
		}
		return vm.screenData().flexMonthActCalSet().insufficSet.startMonth + '月';
	}

	getStartPeriod() {
		const vm = this;
		if (!vm.screenData().flexMonthActCalSet()) {
			return '';
		}
		return vm.screenData().flexMonthActCalSet().insufficSet.period + 'ヵ月'
	}

	getSettlePeriodText() {
		const vm = this;
		if (!vm.screenData().flexMonthActCalSet()) {
			return '';
		}
		return vm.$i18n.text(_.find(__viewContext.enums.SettlePeriod, ['value', vm.screenData().flexMonthActCalSet().insufficSet.settlePeriod]).name);
	}

	getCarryforwardSetText() {
		const vm = this;
		if (!vm.screenData().flexMonthActCalSet()) {
			return '';
		}
		return vm.$i18n.text(_.find(__viewContext.enums.CarryforwardSetInShortageFlex, ['value', vm.screenData().flexMonthActCalSet().insufficSet.carryforwardSet]).name);
	}

	getAggrMethodText() {
		const vm = this;
		if (!vm.screenData().flexMonthActCalSet()) {
			return '';
		}
		return vm.$i18n.text(_.find(__viewContext.enums.FlexAggregateMethod, ['value', vm.screenData().flexMonthActCalSet().aggrMethod]).name);
	}

	getReferenceText() {
		const vm = this;
		if (!vm.screenData().flexMonthActCalSet()) {
			return '';
		}

		return vm.$i18n.text(vm.screenData().flexMonthActCalSet().withinTimeUsageAttr == true ? 'KMK004_288' : 'KMK004_289');
	}

	getIncludeOverTimeText() {
		const vm = this;
		if (!vm.screenData().flexMonthActCalSet()) {
			return '';
		}
		return vm.$i18n.text(vm.screenData().flexMonthActCalSet().flexTimeHandle.includeOverTime == true ? "KMK004_283" : "KMK004_260");
	}

	getIncludeIllegalHdwk() {
		const vm = this;
		if (!vm.screenData().flexMonthActCalSet()) {
			return '';
		}
		return vm.$i18n.text(vm.screenData().flexMonthActCalSet().flexTimeHandle.includeIllegalHdwk == true ? "KMK004_284" : "KMK004_337");
	}

	getAggregateSetText() {
		const vm = this;
		if (!vm.screenData().flexMonthActCalSet()) {
			return '';
		}
		return vm.$i18n.text(_.find(__viewContext.enums.AggregateSetting, ['value', vm.screenData().flexMonthActCalSet().legalAggrSet.aggregateSet]).name);
	}

}

interface IParam {
	screenData: KnockoutObservable<FlexScreenData>;
	screenMode: string;
}



