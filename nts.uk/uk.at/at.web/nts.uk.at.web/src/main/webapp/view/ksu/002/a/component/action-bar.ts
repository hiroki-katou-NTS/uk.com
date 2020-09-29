/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {
	const template = `
	<div class="btn-action">
		<div class="cf">
			<button class="small btn-copy" data-bind="
					i18n: 'KSU002_10',
					timeClick: -1
				"></button>
			<button class="small btn-edit" data-bind="
					i18n: 'KSU002_11',
					timeClick: -1
				"></button>
		</div>
		<div class="cf">
			<button class="small btn-undo" data-bind="
					icon: 999,
					enable: $component.data.clickable.undo,
					timeClick: -1,
					click: function() { $component.data.clickBtn.apply($vm, ['undo']); }
				"></button>
			<button class="small btn-redo" data-bind="
					icon: 999,
					enable: $component.data.clickable.redo,
					timeClick: -1,
					click: function() { $component.data.clickBtn.apply($vm, ['redo']); }
				"></button>
			<button class="small btn-help" data-bind="i18n: 'KSU002_27'"></button>
		</div>
	</div>
	<div class="component-action">
		<div>
			<label data-bind="i18n: 'KSU002_12'"></label>
	        <div data-bind="ntsComboBox: {
					width: '250px',
					name: $component.$i18n('KSU002_12'),
					value: ko.observable(''),
					options: ko.observableArray([]),
					optionsValue: 'id',
					optionsText: 'title',
					editable: false,
					selectFirstIfNull: true,
					columns: [
						{ prop: 'title', length: 10 },
					]
				}"></div>
		</div>
		<div>
			<label data-bind="i18n: 'KSU002_13'"></label>
	        <div data-bind="ntsComboBox: {
	            width: '500px',
	            name: $component.$i18n('KSU002_12'),
	            value: ko.observable(''),
	            options: ko.observableArray([]),
	            optionsValue: 'id',
	            optionsText: 'title',
	            editable: false,
	            selectFirstIfNull: true,
	            columns: [
	                { prop: 'title', length: 10 },
	            ]}"></div>
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

	@handler({
		bindingName: COMPONENT_NAME,
		validatable: true,
		virtual: false
	})
	export class ActionBarComponentBindingHandler implements KnockoutBindingHandler {
		init(element: any, valueAccessor: () => any, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
			const name = COMPONENT_NAME;

			const clickable = allBindingsAccessor.get('clickable');
			const clickBtn = allBindingsAccessor.get('click-btn');

			const params = { clickable, clickBtn };
			const component = { name, params };

			element.classList.add('cf');
			element.classList.add('action-bar');

			ko.applyBindingsToNode(element, { component }, bindingContext);

			return { controlsDescendantBindings: true };
		}
	}

	@component({
		name: COMPONENT_NAME,
		template
	})
	export class ActionBarComponent extends ko.ViewModel {
		constructor(private data: Parameter) {
			super();

			const vm = this;

			if (!data) {
				vm.data = {
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

		}

		mounted() {
			const vm = this;

			$(vm.$el).find('[data-bind]').removeAttr('data-bind');
		}
	}

	interface Parameter {
		clickable: {
			undo: KnockoutComputed<boolean>;
			redo: KnockoutComputed<boolean>;
		};
		clickBtn: (btn: 'undo' | 'redo') => void;
	}
}