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
		<div id="list-employee"></div>
	</div>
	<div class="float-left model-component" data-bind="component: { name: 'editor-area', params: { model: model } }"></div>
	<div class="float-left model-component">
		<div>
			<table>
				<tbody>
					<tr>
						<td>
							<div data-bind="ntsFormLabel: {constraint: constraints, required: true, text: $i18n('KMP001_22') }"></div>
						</td>
						<td>
							
						<td>
					</tr>
				</tbody>
			</table>
		</div>
		<table id="stamp-card-list" data-bind="ntsGridList: {								
			height: 116,
			dataSource: itemsStamCard,
			primaryKey: 'stampNumber',
			columns: [
			            { headerText: $i18n('KMP001_32'), prop: 'stampNumber', width: 200 }
			        ],
			multiple: true 	,
			value: currentCodes
		}"></table>
	</div>
<div>
`;

interface Params {

}

const KMP001A_API = {
	GET_STAMPCARDDIGIT: 'screen/pointCardNumber/getStampCardDigit',
	GET_STATUS_SETTING: 'screen/pointCardNumber/getStatusEmployeeSettingStampCard',
	GET_INFOMAITON_EMPLOYEE: 'screen/pointCardNumber/getEmployeeInfoCardNumber'
};

@component({
	name: 'view-a',
	template
})
class ViewA extends ko.ViewModel {

	listComponentOption: IModel;
	selectedCode: KnockoutObservable<string>;
	multiSelectedCode: KnockoutObservableArray<string>;
	isShowAlreadySet: KnockoutObservable<boolean>;
	isDialog: KnockoutObservable<boolean>;
	isShowNoSelectRow: KnockoutObservable<boolean>;
	isMultiSelect: KnockoutObservable<boolean>;
	isShowWorkPlaceName: KnockoutObservable<boolean>;
	isShowSelectAllButton: KnockoutObservable<boolean>;
	disableSelection: KnockoutObservable<boolean>;

	public employees: KnockoutObservableArray<IModel> = ko.observableArray([]);
	public model: Model = new Model();
	public settings: KnockoutObservableArray<ISetting> = ko.observableArray([]);
	public employeeIds: KnockoutObservableArray<string> = ko.observableArray([]);
	public baseDate: KnockoutObservable<string> = ko.observable('');
	public itemsStamCard: KnockoutObservableArray<IStampCard> = ko.observableArray([]);
	public currentCodes: KnockoutObservableArray<string> = ko.observableArray([]);
	public constraints: KnockoutObservable<string> = ko.observable('10');

	created() {
		const vm = this;
		
		vm.model.code
			.subscribe((c: string) => {
				const employees: IModel[] = ko.toJS(vm.employees);
				const current = _.find(employees, e => e.code === c);
				
				if (current) {
					vm.$ajax(KMP001A_API.GET_INFOMAITON_EMPLOYEE + "/" + ko.toJS(current.employeeId) + "/" + ko.toJS(current.affiliationId) + "/" + ko.toJS(vm.baseDate))
					.then((data: IModel[]) => {
						vm.model.update(ko.toJS(data));
						console.log(data);
						vm.itemsStamCard(ko.toJS(data.stampCardDto));
						console.log(ko.toJS(vm.itemsStamCard));
					});
				} else {
					// reset data ve mode them moi
				}
			});

		vm.employees
			.subscribe(() => {
				vm.$blockui("invisible")
				vm.$ajax(KMP001A_API.GET_STATUS_SETTING, ko.toJS(vm.employeeIds))
					.then((data: IEmployeeId[]) => {
						const employees: IModel[] = ko.toJS(vm.employees);
						const modelSetting = new Setting();
						for (var i = 0; i < data.length; i++) {
							 const setting = _.find(employees, e => e.employeeId === data[i].employee);
							
							if (setting){
								modelSetting.code(setting.code);
								modelSetting.isAlreadySetting(true);
								vm.settings.push(ko.toJS(modelSetting));
							}
						}
					}).then(() => {
						vm.$blockui("clear");
					});
			})
	}

	mounted() {
		const vm = this;
		const dataFormate = 'YYYY/MM/DD';

		$('#list-employee').ntsListComponent(
			{
				isShowAlreadySet: true, //設定済表示
				isMultiSelect: false,
				listType: 4,
				employeeInputList: vm.employees,
				selectType: 1,
				selectedCode: vm.model.code,
				isShowNoSelectRow: false, //未選択表示
				alreadySettingList: vm.settings,
				isShowWorkPlaceName: true,  //職場表示
				isShowSelectAllButton: false,  //全選択表示
				isSelectAllAfterReload: true,
				disableSelection: false,
				maxRows: 20,
				maxWidth: 450
			}
		);

		$('#com-ccg001')
			.ntsGroupComponent({
				/** Common properties */
				systemType: 2, //システム区分	
				showEmployeeSelection: true,
				showQuickSearchTab: true, //クイック検索
				showAdvancedSearchTab: true,
				showBaseDate: true, //基準日利用
				showClosure: false, //就業締め日利用
				showAllClosure: false, //全締め表示
				showPeriod: false, //対象期間利用
				periodFormatYM: true, //対象期間精度
				maxPeriodRange: 'oneMonth', //最長期間

				/** Required parameter */
				baseDate: ko.observable(moment().format(dataFormate)),
				periodStartDate: ko.observable(moment.utc('1900/01/01', dataFormate).format(dataFormate)),
				periodEndDate: ko.observable(moment.utc('9999/12/31', dataFormate).format(dataFormate)),
				inService: true,
				leaveOfAbsence: true,
				closed: true,
				retirement: true,

				/** Quick search tab options */
				showAllReferableEmployee: true, //参照可能な社員すべて
				showOnlyMe: true, //自分だけ
				showSameDepartment: true,
				showSameDepartmentAndChild: true,
				showSameWorkplace: true, //同じ職場の社員
				showSameWorkplaceAndChild: true, //同じ職場とその配下の社員

				/** Advanced search properties */
				showEmployment: true, //雇用条件
				showDepartment: true,
				showWorkplace: true, //職場条件
				showClassification: true, //分類条件
				showJobTitle: true, //職位条件
				showWorktype: false, //勤種条件
				isMutipleCheck: true, //選択モード

				/**
				* Self-defined function: Return data from CCG001
				* @param: data: the data return from CCG001
				*/
				returnDataFromCcg001: function(data: any) {
					vm.baseDate(moment.utc(data.baseDate, "YYYY/MM/DD").format("YYYY-MM-DD"));

					for (var i = 0; i < data.listEmployee.length; i++) {
						vm.employeeIds.push(data.listEmployee[i].employeeId);
					}
					
					const employees = data.listEmployee
						.map(m => ({
							affiliationId: m.affiliationId,
							code: m.employeeCode,
							name: m.employeeName,
							employeeId: m.employeeId
						}));
				
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
					<div id="td-bottom" data-bind="text: $i18n('KMP001_16')"></div>
				</td>
				<td class="label-column-a-right">
					<div id="td-bottom" data-bind="text: model.workplaceName"></div>
				</td>
			</tr>
			<tr>
				<td class="label-column-a-left">
					<div id="td-bottom" data-bind="text: $component.$i18n('KMP001_9')"></div>
				</td>
				<td class="label-column-a-right">
					<div id="td-bottom" data-bind="text: model.businessName"></div>
				</td>
			</tr>
			<tr>
				<td class="label-column-a-left">
					<div id="td-bottom" data-bind="text: $component.$i18n('KMP001_20')"></div>
				</td>
				<td class="label-column-a-right">
					<div id="td-bottom" data-bind="text: model.entryDate"></div>
				</td>
			</tr>
			<tr>
				<td class="label-column-a-left">
					<div id="td-bottom" data-bind="text: $component.$i18n('KMP001_21')"></div>
				</td>
				<td class="label-column-a-right">
					<div id="td-bottom" data-bind="text: model.retiredDate"></div>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<!--
<div>
	<div style="margin-left: 70px; margin-top: 30px">
		<div data-bind="component: { name: 'card-list', params: { model: model } }"></div>
	</div>
</div>
-->`

@component({
	name: 'editor-area',
	template: editorTemplate
})
class RightPanelComponent extends ko.ViewModel {
	model!: Model;

	constraints: KnockoutObservable<String> = ko.observable('');

	created(params: any) {
		const vm = this;

		vm.model = params.model;

		vm.$ajax(KMP001A_API.GET_STAMPCARDDIGIT)
			.then((data: string) => {
				vm.constraints(data);
			});
	}

	mounted() {
		const vm = this;

		_.extend(window, { vm });
	}
}

/*@component({
	name: 'card-list',
	template: '<div></div>'
})
class CardListComponent extends ko.ViewModel {
	model!: Model;
	stampCard!: KnockoutObservableArray<StampCard>;

	created(params: any) {
		const vm = this;
		
		vm.model = params.model;
	}

	mounted() {
		const vm = this;
		const row = 4;
		
		console.log(vm.model);

		const $grid = $(vm.$el)
			.igGrid({
				columns: [
					{ headerText: vm.$i18n('KMP001_31'), key: "stampCardId", dataType: "string", width: 50, template: `<input type="checkbox" value="" />` },
					{ headerText: vm.$i18n('KMP001_22'), key: "stampNumber", dataType: "string", width: 200 }
				],
				height: `${24 + (23 * row)}px`,
				dataSource: [],
				cellClick: function(evt, ui) {
					//vm.selectedCardNo(ui.rowIndex);
				}
			});

		ko.computed(() => {
			const stampCard = ko.unwrap(vm.stampCard);

			$grid.igGrid('option', 'dataSource', ko.toJS(stampCard));
		});
	}
}*/


interface IStampCard {
	stampCardId: string;
	stampNumber: string;
}

interface IModel {
	code: string;
	affiliationId: string;
	birthDay: Date;
	businessName: string;
	employeeCode: string;
	employeeId: string;
	entryDate: Date;
	gender: number;
	pid: string;
	retiredDate: Date;
	stampCard: IStampCard;
	workplaceId: string;
	workplaceName: string;
}

class StampCard {
	stampCardId: KnockoutObservable<string> = ko.observable('');
	stampNumber: KnockoutObservable<string> = ko.observable('');

	constructor(params?: IStampCard) {
		const model = this;

		model.update(params);
	}

	public update(params?: IStampCard) {
		const model = this;

		if (params) {
			model.stampCardId(params.stampCardId);
			model.stampNumber(params.stampNumber);
		}
	}
}

class Model {
	code: KnockoutObservable<string> = ko.observable('');
	affiliationId:  KnockoutObservable<string> = ko.observable('');
	birthDay: KnockoutObservable<Date | null> = ko.observable(null);
	businessName: KnockoutObservable<string> = ko.observable('');
	employeeCode: KnockoutObservable<string> = ko.observable('');
	employeeId: KnockoutObservable<string> = ko.observable('');
	entryDate: KnockoutObservable<Date | null> = ko.observable(null);
	gender: KnockoutObservable<number> = ko.observable(0);
	pid: KnockoutObservable<string> = ko.observable('');
	retiredDate: KnockoutObservable<Date | null> = ko.observable(null);
	stampCard: KnockoutObservableArray<StampCard> = ko.observableArray([]);
	workplaceId: KnockoutObservable<string> = ko.observable('');
	workplaceName: KnockoutObservable<string> = ko.observable('');

	public update(params?: IModel) {
		const self = this;

		if (params) {
			self.employeeId(params.employeeId);

			self.update(params);
		}
	}

	public update(params?: IModel) {
		const self = this;

		if (params) {
			self.birthDay(params.birthDay);
			self.businessName(params.businessName);
			self.employeeCode(params.employeeCode);
			self.entryDate(params.entryDate);
			self.gender(params.gender);
			self.pid(params.pid);
			self.retiredDate(params.retiredDate);
			self.workplaceId(params.workplaceId);
			self.workplaceName(params.workplaceName);

			self.stampCard(params.stampCard);
		}
	}
}

interface ISetting {
	code: string;
	isAlreadySetting: boolean;
}

class Setting {
	code: KnockoutObservable<string> = ko.observable('');
	isAlreadySetting: KnockoutObservable<boolean> = ko.observable(true);
	
	constructor(params?: ISetting) {
		const seft = this;

		if (params) {
			seft.code(params.code);
			seft.update(params);
		}
	}

	update(params: ISetting) {
		const seft = this;

		seft.isAlreadySetting(params.isAlreadySetting);
	}
}

interface IEmployeeId {
	employee: string;
}

