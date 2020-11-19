/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

	const template = `
	<div>
		<div data-bind="ntsFormLabel: {} , i18n: 'KMK004_229'"></div>
		<button data-bind="click: openKDialog , i18n: 'KMK004_231'" ></button>
	</div>
	<div class="div_line">
		<table class="basic-settings">
			<tr class="bg-green">
				<th data-bind="i18n: 'KMK004_254'"></th>
				<th data-bind="i18n: 'KMK004_255'"></th>
				<th data-bind="i18n: 'KMK004_256'"></th>
				<th data-bind="i18n: 'KMK004_257'"></th>
			</tr>
			<tr>
				<td data-bind="text:getSettlePeriodText()"></td>
				<td data-bind="text:screenData().comFlexMonthActCalSet().insufficSet.startMonth + '月'"></td>
				<td data-bind="text:screenData().comFlexMonthActCalSet().insufficSet.period + 'ヵ月'"></td>
				<td data-bind="tegetCarryforwardSetText()"></td>
			</tr>
		</table>
		<table style="width: 620px;" class="basic-settings">
			<tr class="bg-green">
				<th data-bind="i18n: 'KMK004_258'"></th>
				<th data-bind="i18n: 'KMK004_262'"></th>
			</tr>
			<tr>
				<td data-bind="text:getAggrMethodText()"></td>
				<td data-bind="text:getReferenceText()"></td>
			</tr>
		</table>
		<table style="width: 620px;" class="basic-settings">
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
	`;
	const COMPONENT_NAME = 'basic-settings-company';

	@component({
		name: COMPONENT_NAME,
		template
	})
	
	class BasicSettingsCompany extends ko.ViewModel {

		screenData: KnockoutObservable<FlexScreenData>;

		created(params: IParam) {
			let vm = this;
			vm.screenData = params.screenData;
		}

		openKDialog() {
			let vm = this;
			vm.$window.modal('/view/kmk/004/k/index.xhtml');
		}

		getSettlePeriodText() {
			let vm = this;
			return vm.$i18n.text(_.find(__viewContext.enums.SettlePeriod, ['value', vm.screenData().comFlexMonthActCalSet().insufficSet.settlePeriod]).name);
		}

		getCarryforwardSetText() {
			let vm = this
			return vm.$i18n.text(_.find(__viewContext.enums.CarryforwardSetInShortageFlex, ['value', vm.screenData().comFlexMonthActCalSet().insufficSet.carryforwardSet]).name);
		}

		getAggrMethodText() {
			let vm = this
			return vm.$i18n.text(_.find(__viewContext.enums.FlexAggregateMethod, ['value', vm.screenData().comFlexMonthActCalSet().aggrMethod]).name);
		}

		getReferenceText() {
			let vm = this
			return vm.$i18n.text(_.find(__viewContext.enums.ReferencePredTimeOfFlex, ['value', vm.screenData().getFlexPredWorkTime().reference]).name);
		}

		getIncludeOverTimeText() {
			let vm = this
			return vm.$i18n.text(vm.screenData().comFlexMonthActCalSet().flexTimeHandle.includeOverTime == true ? "KMK004_283" : "KMK004_260");
		}

		getIncludeIllegalHdwk() {
			let vm = this
			return vm.$i18n.text(vm.screenData().comFlexMonthActCalSet().flexTimeHandle.includeIllegalHdwk == true ? "KMK004_284" : "KMK004_337");
		}

		getAggregateSetText() {
			let vm = this
			return vm.$i18n.text(_.find(__viewContext.enums.AggregateSetting, ['value', vm.screenData().comFlexMonthActCalSet().legalAggrSet.aggregateSet]).name);
		}

	}

	interface IParam {
		screenData: KnockoutObservable<FlexScreenData>;
	}

	
	
