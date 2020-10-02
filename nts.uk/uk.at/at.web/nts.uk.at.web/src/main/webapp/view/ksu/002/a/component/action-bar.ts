/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {
	const template = `
	<div class="btn-action">
		<div class="cf">
			<button class="small btn-copy" data-bind="
					i18n: 'KSU002_10',
					timeClick: -1,
					attr: {
						tabindex: $component.data.tabIndex
					}
				"></button>
			<button class="small btn-edit" data-bind="
					i18n: 'KSU002_11',
					timeClick: -1,
					attr: {
						tabindex: $component.data.tabIndex
					}
				"></button>
		</div>
		<div class="cf">
			<button class="small btn-undo" data-bind="
					icon: 44,
					enable: $component.data.clickable.undo,
					timeClick: -1,
					click: function() { $component.data.clickBtn.apply($vm, ['undo']); },
					attr: {
						tabindex: $component.data.tabIndex
					}
				"></button>
			<button class="small btn-redo" data-bind="
					icon: 154,
					enable: $component.data.clickable.redo,
					timeClick: -1,
					click: function() { $component.data.clickBtn.apply($vm, ['redo']); },
					attr: {
						tabindex: $component.data.tabIndex
					}
				"></button>
			<button class="small btn-help" data-bind="
					i18n: 'KSU002_27',
					attr: {
						tabindex: $component.data.tabIndex
					}
				"></button>
		</div>
	</div>
	<div class="component-action">
		<div>
			<label data-bind="i18n: 'KSU002_12'"></label>
	        <div data-bind="ntsComboBox: {
					width: '300px',
					name: $component.$i18n('KSU002_12'),
					value: $component.selectedWorkTypeCode,
					options: $component.workTypes,
					optionsValue: 'workTypeCode',
					editable: false,
					selectFirstIfNull: true,
					columns: [
						{ prop: 'workTypeCode', length: 4 },
						{ prop: 'name', length: 10 },
						{ prop: 'memo', length: 10 },
					]
				},
				attr: {
					tabindex: $component.data.tabIndex
				}"></div>
		</div>
		<div>
			<label data-bind="i18n: 'KSU002_13'"></label>
	        <div data-bind="component: {
					name: 'working-hours',
					params: {
						input: {
							workPlaceId: ko.observable(''),
							displayFormat: '',
							fillter: false,
							showNone: true,
							showDeferred: true,
							selectMultiple: false,
							initiallySelected: []
						},
						callback: function() { $component.workHourSelect.apply($component, [...arguments]) }
					}
				}"></div>
		</div>
	</div>
	<style type="text/css" rel="stylesheet">
		.action-bar {
			margin-bottom: 5px;
			padding: 6px;
			border-radius: 5px;
			display: inline-block;
			border: 1px solid #cccccc;
			background-color: rgb(219, 238, 244);
		}
		.action-bar .btn-action {
			float: left;
		    padding-right: 5px;
			border-right: 2px solid #ccc;
		}
		.action-bar .btn-action>div:first-child {
			margin-bottom: 3px;
		}
		.action-bar .btn-action>div>button {
			float: left;
		    width: 40px;
			white-space: pre-line;
		}
		.action-bar .btn-action>div>button:not(:first-child) {
			margin-left: 3px;
		}
		.action-bar .btn-action>div:first-child>button {
			height: 40px;
		}
		.action-bar .btn-action>div:last-child>button {
			height: 29px
		}
		.action-bar .btn-action>div:last-child>button.btn-help {
			width: 24px;
			height: 24px;
			margin-top: 5px;
		}
		.action-bar .component-action {
			float: left;
			padding-left: 5px;			
		}
		.action-bar .component-action>div:first-child {
			padding-top: 4px;
			padding-bottom: 4px;
			margin-bottom: 3px;
		}
		.action-bar .component-action>div>label {
			min-width: 100px;
			line-height: 32px;
			display: block;
			float: left;
		}
	</style>`;

	const COMPONENT_NAME = 'action-bar';

	const API = {
		WTYPE: '/screen/ksu/ksu002/getWorkType'
	};

	@handler({
		bindingName: COMPONENT_NAME,
		validatable: true,
		virtual: false
	})
	export class ActionBarComponentBindingHandler implements KnockoutBindingHandler {
		init(element: HTMLElement, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
			const name = COMPONENT_NAME;

			const tabIndex = element.getAttribute('tabindex') || '1';
			const clickable = allBindingsAccessor.get('clickable');
			const clickBtn = allBindingsAccessor.get('click-btn');

			const params = { clickable, clickBtn, tabIndex };
			const component = { name, params };

			element.classList.add('cf');
			element.classList.add('action-bar');
			element.removeAttribute('tabindex');

			ko.applyBindingsToNode(element, { component }, bindingContext);

			return { controlsDescendantBindings: true };
		}
	}

	@component({
		name: COMPONENT_NAME,
		template
	})
	export class ActionBarComponent extends ko.ViewModel {
		workTypes: KnockoutObservableArray<WorkType> = ko.observableArray([]);
		selectedWorkTypeCode: KnockoutObservable<string> = ko.observable('');

		constructor(private data: Parameter) {
			super();

			const vm = this;

			if (!data) {
				vm.data = {
					tabIndex: "1",
					clickBtn: () => { },
					clickable: {
						redo: ko.computed(() => true),
						undo: ko.computed(() => true)
					}
				};
			}

			const { clickable, clickBtn } = vm.data;

			if (!clickBtn) {
				vm.data.clickBtn = () => { };
			}

			if (!clickable) {
				vm.data.clickable = {
					redo: ko.computed(() => true),
					undo: ko.computed(() => true)
				};
			}

			const { redo, undo } = vm.data.clickable;

			if (!redo) {
				vm.data.clickable.redo = ko.computed(() => true);
			}

			if (!undo) {
				vm.data.clickable.undo = ko.computed(() => true);
			}
		}

		created() {
			const vm = this;

			vm.$ajax('at', API.WTYPE)
				.then((response: WorkType[]) => {
					vm.workTypes(response.map((m) => ({ ...m, memo: vm.$i18n(m.memo) })));
				});
		}

		mounted() {
			const vm = this;

			$(vm.$el).find('[data-bind]').removeAttr('data-bind');

			/* setTimeout(() => {
				$(vm.$el).find('working-hours [tabindex="0"]').attr('tabindex', vm.data.tabIndex);
			}, 1000); */
		}

		workHourSelect(input: any[], source: any[]) {
			console.log(arguments);
		}
	}

	interface Parameter {
		tabIndex: string;
		clickable: {
			undo: KnockoutComputed<boolean>;
			redo: KnockoutComputed<boolean>;
		};
		clickBtn: (btn: 'undo' | 'redo') => void;
	}

	interface WorkType {
		memo: string;
		name: string;
		workTypeCode: string;
	}
}