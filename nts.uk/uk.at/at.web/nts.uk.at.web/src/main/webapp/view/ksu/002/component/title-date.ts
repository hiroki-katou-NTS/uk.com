/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {

	const template = `
		<div class="title-date">
			<div class="backgroud" >
				<div class="content">
					<table>
						<tbody>
							<tr>
								<td>
									<div data-bind="ntsDatePicker: { 
										value: yearMonth,
										dateFormat: 'yearmonth' ,
									 	valueFormat: 'YYYYMM',
								  		fiscalMonthsMode: true,
						 				defaultClass: 'round-orange',
										showJumpButtons: true  }">
									</div>
									<span class="label-1" class="label" data-bind="i18n: 'KSU002_23'"></span>
									<span class="label-2" class="label" data-bind="i18n: 'KSU002_7'"></span>
								</td>
								<td>
									<div data-bind="ntsDatePicker: { value: yearMonth, dateFormat: 'yearmonth' , valueFormat: 'YYYYMM',  fiscalMonthsMode: true, defaultClass: 'round-orange' }">
									</div>
									<span class="label-1" class="label" data-bind="i18n: 'KSU002_6'"></span>
									<span class="label-2" class="label" data-bind="i18n: 'KSU002_7'"></span>
								</td>
								<td>
									<div class="cf" data-bind="ntsSwitchButton: {
										$i18n: 'KSU002_6',
										options: roundingRules,
										optionsValue: 'code',
										optionsText: 'name',
										value: selectedRuleCode }"></div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<style type="text/css" rel="stylesheet">
            .title-date .backgroud {
				border: 1px solid #cccccc;
				background: #edfac2;
				padding: 6px;
				margin: 10px;
				width: 615px;
			}
			
			.title-date .backgroud .content .label-1 {
				margin-left: 10px;
			}
			
			.title-date .backgroud .content .label-2 {
				margin-left: -6px;
				margin-right: 12px;
			}
			
        </style>
        <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
    `;

	interface Params {

	}

	@component({
		name: 'title-date',
		template
	})
	export class TitleDateComponent extends ko.ViewModel {

		public yearMonth: KnockoutObservable<number> = ko.observable(1);
		public selectedRuleCode: KnockoutObservable<number> = ko.observable(-1);
		roundingRules: KnockoutObservableArray<any>;

		created(params: Params) {
			const vm = this;

			vm.roundingRules = ko.observableArray([
				{ code: 1, name: vm.$i18n('KSU002_8') },
				{ code: 2, name: vm.$i18n('KSU002_9') }
			]);
			
			vm.selectedRuleCode(1);
		}

		mounted() {
		}
	}
}

class SwitchType {
	code: number;
	name: string;

	constructor(code: number, name: string) {
		const mock = new ko.ViewModel();

		if (name) {
			this.name = mock.$i18n(name);
		}
	}
}

