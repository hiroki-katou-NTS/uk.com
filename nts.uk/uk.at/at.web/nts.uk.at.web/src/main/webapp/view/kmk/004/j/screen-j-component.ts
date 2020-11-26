/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


const template = `
					<div class="sidebar-content-header">
								<div
									data-bind="component: {
								name: 'sidebar-button',
								params: {
											screenData:screenData ,
											screenMode:screenMode
										}
							}"></div>
					</div>
					<div id="com-ccg001"></div>
					<div style="padding: 10px 10px 10px 50px; display: flex;">
						<div style="display:inline-block"> 
							<div id="employee-list"></div>
						</div>
						<div style="display:inline-block">
							<label id="flex-title" data-bind="i18n:'KMK004_268'"></label>
							<hr/>
							<label id="selected-employee" data-bind="i18n:employeeName"></label>
							<div style="margin-top: 20px;" data-bind="component: {
								name: 'basic-settings-company',
								params: {
											screenData:screenData,
											screenMode:screenMode
										}
								}">
						</div>
						<div data-bind="component: {
								name: 'monthly-working-hours',
								params: {
											screenData:screenData,
											screenMode:screenMode
										}
								}">
						</div>
						</div>
					</div>
	`;
const COMPONENT_NAME = 'screen-j-component';

const API_URL = {
	START_PAGE: 'screen/at/kmk004/j/start-page'
};

@component({
	name: COMPONENT_NAME,
	template
})

class ScreenJComponent extends ko.ViewModel {

	screenData: KnockoutObservable<FlexScreenData> = ko.observable(new FlexScreenData());

	header = '';

	selectedCode: KnockoutObservable<string> = ko.observable();

	employeeName: KnockoutObservable<string> = ko.observable('');

	employeeList: KnockoutObservableArray<EmployeeModel> = ko.observableArray([]);

	alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);



	created(params: any) {
		let vm = this;

		vm.header = params.header;
		vm.initCCG001();

		/*	vm.$blockui('invisible')
				.then(() => vm.$ajax(API_URL.START_PAGE))
				.then((data: IScreenData) => {
					vm.screenData(new FlexScreenData(data));
				})
				.then(() => vm.$blockui('clear'));*/

	}

	mounted() {
		const vm = this;
		vm.initEmployeeList();

	}

	initCCG001() {
		const vm = this,
			date = moment(new Date()).toDate(),
			ccg001ComponentOption = {
				/** Common properties */
				systemType: 1,
				showEmployeeSelection: true,
				showQuickSearchTab: true,
				showAdvancedSearchTab: true,
				showBaseDate: true,
				showClosure: true,
				showAllClosure: true,
				showPeriod: true,
				periodFormatYM: false,

				/** Required parameter */
				baseDate: date,
				periodStartDate: date,
				periodEndDate: date,
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
				showWorktype: true,
				isMutipleCheck: true,

				/**
				* Self-defined function: Return data from CCG001
				* @param: data: the data return from CCG001
				*/
				returnDataFromCcg001: function(data: Ccg001ReturnedData) {
					let listEmployee = _.map(data.listEmployee, (item) => { return new EmployeeModel(item); });
					vm.employeeList(listEmployee);
					if (listEmployee.length) {
						vm.selectedCode(listEmployee[0].code);
					}
				}
			};

		$('#com-ccg001').ntsGroupComponent(ccg001ComponentOption);
	}

	initEmployeeList() {
		const vm = this,
			ListType = {
				EMPLOYMENT: 1, // 雇用
				Classification: 2, // 分類
				JOB_TITLE: 3, // 職位
				EMPLOYEE: 4 // 職場
			},
			SelectType = {
				SELECT_BY_SELECTED_CODE: 1,
				SELECT_ALL: 2,
				SELECT_FIRST_ITEM: 3,
				NO_SELECT: 4
			};
		vm.$blockui('grayout');

		$('#employee-list').ntsListComponent({
			isShowAlreadySet: true,
			isMultiSelect: false,
			listType: ListType.EMPLOYEE,
			employeeInputList: vm.employeeList,
			selectType: SelectType.SELECT_BY_SELECTED_CODE,
			selectedCode: vm.selectedCode,
			isDialog: true,
			isShowNoSelectRow: false,
			alreadySettingList: vm.alreadySettingList,
			isShowWorkPlaceName: false,
			isShowSelectAllButton: false,
			disableSelection: false
		});

		vm.selectedCode.subscribe((value) => {
			let datas: Array<EmployeeModel> = vm.employeeList(),

				selectedItem: EmployeeModel = _.find(datas, ['code', value]);

			vm.employeeName(selectedItem ? selectedItem.name : '');
		});
		vm.$blockui("hide");
		vm.selectedCode.valueHasMutated();
	}

}

class EmployeeModel {
	id: string;
	code: string;
	name: string;
	workplaceName: string;
	isAlreadySetting: boolean;
	optionalColumn?: any;

	constructor(param: EmployeeSearchDto, isAlreadySetting?: boolean) {
		this.id = param.employeeId;
		this.code = param.employeeCode;
		this.name = param.employeeName;
		this.workplaceName = param.affiliationName;
		this.isAlreadySetting = isAlreadySetting ? isAlreadySetting : false;
	}
}
interface Ccg001ReturnedData {
	baseDate: string; // 基準日
	closureId?: number; // 締めID
	periodStart: string; // 対象期間（開始)
	periodEnd: string; // 対象期間（終了）
	listEmployee: Array<EmployeeSearchDto>; // 検索結果
}


interface EmployeeSearchDto {
	employeeId: string;
	employeeCode: string;
	employeeName: string;
	affiliationId: string; // departmentId or workplaceId based on system type
	affiliationName: string; // departmentName or workplaceName based on system type
}

