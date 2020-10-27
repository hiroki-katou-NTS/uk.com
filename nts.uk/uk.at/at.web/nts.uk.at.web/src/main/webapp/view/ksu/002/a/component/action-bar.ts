/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.ui.at.ksu002.a {
	import k = nts.uk.ui.at.kcp013.shared;

	const template = `
	<div class="btn-action">
		<div class="cf">
			<button class="small btn-copy" data-bind="
					i18n: 'KSU002_10',
					css: {
						active: ko.unwrap($component.data.mode) === 'copy'
					},
					timeClick: -1,
					attr: {
						tabindex: $$tabindex
					},
					click: function() { $component.data.mode('copy'); },
					enable: $component.$validate.valid()
				"></button>
			<button class="small btn-edit" data-bind="
					i18n: 'KSU002_11',
					css: {
						active: ko.unwrap($component.data.mode) === 'edit'
					},
					timeClick: -1,
					attr: {
						tabindex: $$tabindex
					},
					click: function() { $component.data.mode('edit'); }
				"></button>
		</div>
		<div class="cf">
			<button class="small btn-undo" data-bind="
					icon: 44,
					enable: $component.data.clickable.undo(),
					timeClick: -1,
					click: function() { $component.data.clickBtn.apply($vm, ['undo']); },
					attr: {
						tabindex: $$tabindex
					}
				"></button>
			<button class="small btn-redo" data-bind="
					icon: 154,
					enable: $component.data.clickable.redo(),
					timeClick: -1,
					click: function() { $component.data.clickBtn.apply($vm, ['redo']); },
					attr: {
						tabindex: $$tabindex
					}
				"></button>
			<button class="small btn-help" data-bind="
					i18n: 'KSU002_27',
					attr: {
						tabindex: $$tabindex
					}
				"></button>
		</div>
	</div>
	<div class="component-action">
		<div>
			<label data-bind="i18n: 'KSU002_12'"></label>
	        <div data-bind="
				attr: {
					tabindex: $$tabindex
				},
				ntsComboBox: {
					width: '430px',
					name: $component.$i18n('KSU002_12'),
					value: $component.workTypeData.selected,
					options: $component.workTypeData.dataSources,
					optionsValue: 'workTypeCode',
					enable: ko.unwrap($component.data.mode) === 'copy',
					editable: false,
					selectFirstIfNull: true,
					visibleItemsCount: 10,
					columns: [
						{ prop: 'workTypeCode', length: 4 },
						{ prop: 'name', length: 10 },
						{ prop: 'memo', length: 10 },
					]
				}"></div>
		</div>
		<div>
			<label data-bind="i18n: 'KSU002_13'"></label>
			<div data-bind="
					kcp013: $component.workTimeData.selected,
					dataSources: $component.workTimeData.dataSources,
					filter: ko.observable(true),
					show-mode: ko.observable(3),
					disabled: $component.workTimeData.disabled,
					tabindex: $$tabindex,
					width: 520,
					workplace-id: $component.data.workplaceId
				"></div>
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
		    padding-right: 15px;
			border-right: 2px solid #ccc;
		}
		.action-bar .btn-action>div:first-child {
			margin-bottom: 5px;
		}
		.action-bar .btn-action>div>button {
			float: left;
		    width: 50px;
			white-space: pre-line;
			padding: 1px 10px;
		}
		.action-bar .btn-action>div>button:not(:first-child) {
			margin-left: 5px;
		}
		.action-bar .btn-action>div:first-child>button {
			height: 40px;
		}
		.action-bar .btn-action>div:last-child>button {
			height: 29px
		}
		.action-bar .btn-action>div:last-child>button.btn-help {
			width: 30px;
			height: 24px;
			margin-top: 5px;
		}
		.action-bar .component-action {
			float: left;
			padding-left: 15px;			
		}
		.action-bar .component-action>div:first-child {
			padding-top: 4px;
			padding-bottom: 4px;
			margin-bottom: 5px;
		}
		.action-bar .component-action>div>label {
			min-width: 100px;
			line-height: 32px;
			display: block;
			float: left;
		}
		.action-bar .btn-action .btn-copy.active,
		.action-bar .btn-action .btn-edit.active {
			color: #fff;
			background-color: #007fff;
		}
		.action-bar .btn-action .btn-copy.active:focus,
		.action-bar .btn-action .btn-edit.active:focus {
			box-shadow: 0 3px rgba(0, 0, 0, 0.4);
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
		init(element: HTMLElement, valueAccessor: () => KnockoutObservable<null | WorkData>, allBindingsAccessor: KnockoutAllBindingsAccessor, viewModel: any, bindingContext: KnockoutBindingContext): void | { controlsDescendantBindings: boolean; } {
			const name = COMPONENT_NAME;
			const selected = valueAccessor();
			const tabIndex = element.getAttribute('tabindex') || '1';
			const mode = allBindingsAccessor.get('mode');
			const clickable = allBindingsAccessor.get('clickable');
			const clickBtn = allBindingsAccessor.get('click-btn');
			const workplaceId = allBindingsAccessor.get('workplace-id');

			const params = { selected, clickable, clickBtn, tabIndex, mode, workplaceId };
			const component = { name, params };

			element.classList.add('cf');
			element.classList.add('action-bar');
			element.removeAttribute('tabindex');

			const childContext = bindingContext
				.extend({
					$$tabindex: tabIndex
				});

			ko.applyBindingsToNode(element, { component }, childContext);

			return { controlsDescendantBindings: true };
		}
	}

	@component({
		name: COMPONENT_NAME,
		template
	})
	export class ActionBarComponent extends ko.ViewModel {
		workTypeData!: {
			selected: KnockoutObservable<string>;
			dataSources: KnockoutObservableArray<WorkTypeModel>;
		};

		workTimeData!: {
			selected: KnockoutObservable<string>;
			dataSources: KnockoutObservableArray<k.WorkTimeModel>;
			disabled: KnockoutComputed<boolean>;
		};

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
					},
					mode: ko.observable('copy'),
					workplaceId: ko.observable(''),
					selected: ko.observable(null)
				};
			}

			const { selected, clickable, clickBtn, mode, workplaceId } = vm.data;

			if (selected === undefined) {
				vm.data.selected = ko.observable(null);
			}

			if (mode === undefined) {
				vm.data.mode = ko.observable('edit');
			}

			if (clickBtn === undefined) {
				vm.data.clickBtn = () => { };
			}

			if (clickable === undefined) {
				vm.data.clickable = {
					redo: ko.computed(() => true),
					undo: ko.computed(() => true)
				};
			}

			if (workplaceId === undefined) {
				vm.data.workplaceId = ko.observable('');
			}

			const { redo, undo } = vm.data.clickable;

			if (!redo) {
				vm.data.clickable.redo = ko.computed(() => true);
			}

			if (!undo) {
				vm.data.clickable.undo = ko.computed(() => true);
			}

			const selectedWtime: KnockoutObservable<string> = ko.observable('');
			const dataSourcesWtime: KnockoutObservableArray<k.WorkTimeModel> = ko.observableArray([]);

			const selectedWtype: KnockoutObservable<string> = ko.observable('');
			const dataSourcesWtype: KnockoutObservableArray<WorkTypeModel> = ko.observableArray([]);

			vm.workTypeData = {
				selected: selectedWtype,
				dataSources: dataSourcesWtype
			};

			vm.workTimeData = {
				selected: selectedWtime,
				dataSources: dataSourcesWtime,
				disabled: ko.computed({
					read: () => {
						const wtypec = ko.unwrap(selectedWtype);
						const wtyped = ko.unwrap(dataSourcesWtype);
						const wtype = _.find(wtyped, w => w.workTypeCode === wtypec);

						return vm.data.mode() === 'edit' || !wtype || wtype.type === WORKTYPE_SETTING.NOT_REQUIRED;
					},
					owner: vm
				}),
			};

			selectedWtype
				.subscribe((wtypec) => {
					const wtyped = ko.unwrap(dataSourcesWtype);
					const wtype = _.find(wtyped, w => w.workTypeCode === wtypec);

					if (wtype && wtype.type === WORKTYPE_SETTING.NOT_REQUIRED) {
						selectedWtime('none');
					}
				});

			ko.computed({
				read: () => {
					const { data, } = vm;
					const d = ko.unwrap(data.mode) === 'copy';

					const wtypec = ko.unwrap(selectedWtype);
					const wtyped = ko.unwrap(dataSourcesWtype);

					const wtimec = ko.unwrap(selectedWtime);
					const wtimed = ko.unwrap(dataSourcesWtime);

					if (!d) {
						data.selected(null);
					} else {
						const wtime = _.find(wtimed, w => w.id === wtimec);
						const wtype = _.find(wtyped, w => w.workTypeCode === wtypec);

						if (!wtype || !wtime) {
							data.selected(null);
						} else {
							const noD = ['none', 'deferred'].indexOf(wtimec) > -1;

							data.selected({
								wtype: {
									code: wtypec,
									name: wtype.name,
									type: wtype.type
								},
								wtime: {
									code: wtimec,
									name: wtime.name,
									value: {
										begin: noD ? null : wtime.tzStart1,
										finish: noD ? null : wtime.tzEnd1
									}
								}
							});
						}
					}
				},
				owner: vm
			});
		}

		created() {
			const vm = this;

			vm.$ajax('at', API.WTYPE)
				.then((response: WorkTypeResponse[]) => {
					vm.workTypeData
						.dataSources(response.map((m) => ({
							...m.workTypeDto,
							type: m.workTimeSetting,
							memo: vm.$i18n(m.workTypeDto.memo)
						})));
				});
		}

		mounted() {
			const vm = this;

			$(vm.$el).find('[data-bind]').removeAttr('data-bind');
		}
	}

	export type EDIT_MODE = 'edit' | 'copy';
	export type WTIME_CODE = 'none' | 'deferred' | string;

	interface Parameter {
		selected: KnockoutObservable<null | WorkData>;
		mode: KnockoutObservable<EDIT_MODE>;
		tabIndex: string;
		clickable: {
			undo: KnockoutComputed<boolean>;
			redo: KnockoutComputed<boolean>;
		};
		clickBtn: (btn: 'undo' | 'redo') => void;
		workplaceId: KnockoutObservable<string>;
	}

	interface WorkTypeResponse {
		workStyle: number;
		workTimeSetting: WORKTYPE_SETTING;
		workTypeDto: WorkType;
	}

	interface WorkType {
		memo: string;
		name: string;
		workTypeCode: string;
	}

	interface WorkTypeModel extends WorkType {
		type: WORKTYPE_SETTING;
		memo: string;
	}

	export enum WORKTYPE_SETTING {
		REQUIRED = 0,
		OPTIONAL = 1,
		NOT_REQUIRED = 2
	}

	export interface WorkData {
		wtype: {
			code: string;
			name: string;
			type: WORKTYPE_SETTING;
		};
		wtime: {
			code: WTIME_CODE;
			name: string;
			value: {
				begin: number | null;
				finish: number | null;
			}
		};
	}
}