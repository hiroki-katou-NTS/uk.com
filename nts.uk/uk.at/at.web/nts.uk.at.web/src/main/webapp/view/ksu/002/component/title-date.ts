/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {

	const template = `
		<div data-bind="ntsDatePicker: { 
			value: yearMonth,
			dateFormat: 'yearmonth' ,
		 	valueFormat: 'YYYYMM',
	  		fiscalMonthsMode: true,
			defaultClass: 'round-orange',
			showJumpButtons: true  }">
		</div>
		
		<div class="title-label">
			<span data-bind="i18n: 'KSU002_23'"></span>
			<span data-bind="i18n: 'KSU002_7'"></span>
		</div>
		
        <div data-bind="ntsComboBox: {
            width: '200px',
            name: $component.$i18n('KSU002_22'),
            value: ko.observable(''),
            options: ko.observableArray([]),
            optionsValue: 'id',
            optionsText: 'title',
            editable: false,
            selectFirstIfNull: true,
            columns: [
                { prop: 'title', length: 10 },
            ]}"></div>

		<div class="title-label">
			<span data-bind="i18n: 'KSU002_6'"></span>
			<span data-bind="i18n: 'KSU002_7'"></span>
		</div>

		<div class="cf" data-bind="ntsSwitchButton: {
			$i18n: 'KSU002_6',
			options: roundingRules,
			optionsValue: 'code',
			optionsText: 'name',
			value: selectedRuleCode }"></div>
					
		<style type="text/css" rel="stylesheet">
            .title-date {
				margin: 5px 0;
				border: 1px solid #cccccc;
				background-color: #edfac2;
				border-radius: 5px;
				padding: 6px;
				width: 615px;
			}
			.title-date>div {
				float: left;
				display: block;
			}
			.title-date>div.title-label {
				padding: 0 10px;
				line-height: 32px;
			}
        </style>
        <style type="text/css" rel="stylesheet" data-bind="html: $component.style"></style>
    `;

	interface Params {

	}
	
	const COMPONENT_NAME = 'title-date';
	
    @handler({
        bindingName: COMPONENT_NAME,
        validatable: true,
        virtual: false
    })
    export class TitleDateComponentBindingHandler implements KnockoutBindingHandler {
        init(element: any, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
            const name = COMPONENT_NAME;
            const baseDate = valueAccessor();
            const mode = allBindingsAccessor.get('mode');

            element.classList.add('cf');
            element.classList.add('title-date');

            ko.applyBindingsToNode(element, { component: { name: name, params: { mode: mode, baseDate: baseDate } } }, bindingContext);

            return { controlsDescendantBindings: true };
        }
    }

	@component({
		name: COMPONENT_NAME,
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

