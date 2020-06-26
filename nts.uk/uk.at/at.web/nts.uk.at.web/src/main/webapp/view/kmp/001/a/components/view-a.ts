/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

const template = `
<div id="com-ccg001"></div>
<div class="sidebar-content-header">
	<span class="title" data-bind="text: $i18n('KMP001_1')"></span>
	<button data-bind="text: $i18n('KMP001_4')"></button>
	<button class="proceed" data-bind="text: $i18n('KMP001_5')"></button>
	<button class="danger" data-bind="text: $i18n('KMP001_6')"></button>
	<button data-bind="text: $i18n('KMP001_7'), click: showDiaLog"></button>
</div>
<div class="view-kmp">
	<div class="list-component float-left viewa">
		<div class="caret-right caret-background bg-green" style="padding: 10px;">
			<table id="list_employee" data-bind="ntsGridList: {
						height: 483,
						options: employees,
						optionsValue: 'code',
						columns: [
				            { headerText: $i18n('KMP001_8'), prop: 'code', width: 112 },
				            { headerText: $i18n('KMP001_9'), prop: 'name', width: 110 },
				            { headerText: $i18n('KMP001_10'), prop: 'joinDate', width: 110 },
	 						{ headerText: $i18n('KMP001_11'), prop: 'config', width: 70, template: '<div style=\\'text-align: center\\'>$\{config}</div>' }
				        ],
						multiple: false,
						enable: true,
						value: model.code
					}">
			</table>
		</div>
	</div>
	<div class="float-left model-component" data-bind="component: { name: 'editor-area', params: { model: model } }"></div>
<div>
`;

interface Params {

}

@component({
	name: 'view-a',
	template
})
class ViewA extends ko.ViewModel {
	public employees: KnockoutObservableArray<IModel> = ko.observableArray([]);

	public model: Model = new Model();

	created() {
		const vm = this;

		vm.model.code
			.subscribe((c: string) => {
				const employees: IModel[] = ko.toJS(vm.employees);

				const current = _.find(employees, e => e.code === c);

				if (current) {
					vm.model.updateWithoutCode(current);
				} else {
					// reset data ve mode them moi
				}
			});
	}

	mounted() {
		const vm = this;
		const dataFormate = 'YYYY/MM/DD';

		$('#com-ccg001')
			.ntsGroupComponent({
				/** Common properties */
				systemType: 2,
				showEmployeeSelection: true,
				showQuickSearchTab: true,
				showAdvancedSearchTab: true,
				showBaseDate: true,
				showClosure: false,
				showAllClosure: false,
				showPeriod: false,
				periodFormatYM: true,
				maxPeriodRange: 'oneMonth',

				/** Required parameter */
				baseDate: ko.observable(moment().format(dataFormate)),
				periodStartDate: ko.observable(moment.utc('1900/01/01', dataFormate).format(dataFormate)),
				periodEndDate: ko.observable(moment.utc('9999/12/31', dataFormate).format(dataFormate)),
				inService: true,
				leaveOfAbsence: true,
				closed: true,
				retirement: true,

				/** Quick search tab options */
				showAllReferableEmployee: true,
				showOnlyMe: true,
				showSameDepartment: true,
				showSameDepartmentAndChild: true,
				showSameWorkplace: true,
				showSameWorkplaceAndChild: true,

				/** Advanced search properties */
				showEmployment: true,
				showDepartment: true,
				showWorkplace: true,
				showClassification: true,
				showJobTitle: true,
				showWorktype: false,
				isMutipleCheck: true,

				/**
				* Self-defined function: Return data from CCG001
				* @param: data: the data return from CCG001
				*/			
				returnDataFromCcg001: function(data: any) {
					const employees = data.listEmployee
						.map(m => ({
							code: m.employeeCode,
							name: m.employeeName,
							joinDate: null,
							entireDate: null,
							cardNos: [{
								checked: false,
								no: '000001'
							}, {
								checked: false,
								no: '000002'
							}, {
								checked: false,
								no: '000003'
							}, {
								checked: false,
								no: '000004'
							}, {
								checked: false,
								no: '000005'
							}, {
								checked: false,
								no: '000006'
							}, {
								checked: false,
								no: '000007'
							}],
							config: '○'
						}));

					// xu ly lay casc thong tin lien quan toi code o day
					
					// debugger;

					vm.employees(employees);
				}
			});
	}

	public showDiaLog() {
		const vm = this;

		vm.$window
			.modal('/view/kmp/001/d/index.xhtml')
			.then(() => {

			});
	}
}

const editorTemplate = `
<div>
	<table class="layout-grid">
		<tbody>
			<tr>
				<td class="label-column-a-left">
					<div id="td-bottom" data-bind="text: $i18n('KMP001_8')"></div>
				</td>
				<td class="label-column-a-right">
					<div id="td-bottom" data-bind="text: model.code"></div>
				</td>
			</tr>
			<tr>
				<td class="label-column-a-left">
					<div id="td-bottom" data-bind="text: $component.$i18n('KMP001_9')"></div>
				</td>
				<td class="label-column-a-right">
					<div id="td-bottom" data-bind="text: model.name"></div>
				</td>
			</tr>
			<tr>
				<td class="label-column-a-left">
					<div id="td-bottom" data-bind="text: $component.$i18n('KMP001_20')"></div>
				</td>
				<td class="label-column-a-right">
					<div id="td-bottom" data-bind="text: model.joinDate"></div>
				</td>
			</tr>
			<tr>
				<td class="label-column-a-left">
					<div id="td-bottom" data-bind="text: $component.$i18n('KMP001_21')"></div>
				</td>
				<td class="label-column-a-right">
					<div id="td-bottom" data-bind="text: model.retireDate"></div>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<div style="margin-top: 127px">
	<table class="layout-grid">
	<!-- ko if: !ko.toJS(model.cardNos).length -->
	<tbody>
		<tr>
			<td>
				<div data-bind="ntsFormLabel: { constraint: 'Chung', required: true, text: $i18n('KMP001_22') }"></div>
			</td>
			<td>
				<input data-bind="ntsTextEditor: { value: ko.observable(''), enabled: false }" />
			<td>
		</tr>
	</tbody>
	<!-- /ko -->
	<!-- ko if: !!ko.toJS(model.cardNos).length -->
	<tbody data-bind="foreach: model.cardNos">
		<!-- ko if: $index() === $component.model.selectedCardNo() -->
		<tr>
			<td>
				<div data-bind="ntsFormLabel: {constraint: 'Chung', required: true, text: $i18n('KMP001_22') }"></div>
			</td>
			<td>
				<input data-bind="ntsTextEditor: { value: no }" />
			<td>
		</tr>
		<!-- /ko -->
	</tbody>
	<!-- /ko -->
	</table>
	<div style="margin-left: 70px; margin-top: 30px">
		<div data-bind="component: { name: 'card-list', params: model }"></div>
	</div>
</div>`

@component({
	name: 'editor-area',
	template: editorTemplate
})
class RightPanelComponent extends ko.ViewModel {
	model!: Model;

	created(params: any) {
		const vm = this;

		vm.model = params.model;
	}

	mounted() {
		const vm = this;

		_.extend(window, { vm });
	}
}

@component({
	name: 'card-list',
	template: '<div></div>'
})
class CardListComponent extends ko.ViewModel {
	cardNos!: KnockoutObservableArray<CardNo>;
	selectedCardNo!: KnockoutObservable<number>;

	created(params: Model) {
		this.cardNos = params.cardNos;
		this.selectedCardNo = params.selectedCardNo;
	}

	mounted() {
		const vm = this;
		const row = 4;

		const $grid = $(vm.$el)
			.igGrid({
				columns: [
					{ headerText: vm.$i18n('KMP001_31'), key: "checked", dataType: "boolean", width: 50, template: `<input type="checkbox" value="" />` },
					{ headerText: vm.$i18n('KMP001_32'), key: "no", dataType: "string", width: 200 }
				],
				height: `${24 + (23 * row)}px`,
				dataSource: [],
				cellClick: function(evt, ui) {
					vm.selectedCardNo(ui.rowIndex);
				}
			});

		ko.computed(() => {
			const cardNos = ko.unwrap(vm.cardNos);

			$grid.igGrid('option', 'dataSource', ko.toJS(cardNos));
		});
	}
}


interface ICardNo {
	checked: boolean;
	no: string;
}

interface IModel {
	code: string;
	name: string;
	joinDate: Date;
	retireDate: Date;
	cardNos: ICardNo[];
	config: string;
}

class CardNo {
	checked: KnockoutObservable<boolean> = ko.observable(false);
	no: KnockoutObservable<string> = ko.observable('');

	constructor(params?: ICardNo) {
		const model = this;

		model.update(params);
	}

	public update(params?: ICardNo) {
		const model = this;

		if (params) {
			model.checked(!!params.checked);
			model.no(`${params.no}`);
		}
	}
}

class Model {
	code: KnockoutObservable<string> = ko.observable('');
	name: KnockoutObservable<string> = ko.observable('');
	joinDate: KnockoutObservable<Date | null> = ko.observable(null);
	retireDate: KnockoutObservable<Date | null> = ko.observable(null);

	cardNos: KnockoutObservableArray<CardNo> = ko.observableArray([]);

	selectedCardNo: KnockoutObservable<number> = ko.observable(0);

	config: KnockoutObservable<string> = ko.observable('○');

	constructor() {
		const model = this;
	}

	public update(params?: IModel) {
		const self = this;

		if (params) {
			self.code(params.code);

			self.updateWithoutCode(params);
		}
	}

	public updateWithoutCode(params?: IModel) {
		const self = this;

		if (params) {
			self.name(params.name);

			self.joinDate(params.joinDate);
			self.retireDate(params.retireDate);

			self.cardNos(params.cardNos.map(m => new CardNo(m)));

			self.config(params.config);
		}
	}
}