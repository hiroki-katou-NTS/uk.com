/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />


const template = `
					<div class="sidebar-content-header">
						<!-- ko component: {
											    name: "sidebar-button",
											    params: {
														screenData:screenData ,
														screenMode:screenMode 
												}
											} -->
						<!-- /ko -->
					</div>
					
					<div id="com-ccg001"></div>
					<div style="margin: 10px 0px 0px 75px; 
								display:flex;
								height: calc(100vh - 153px);
    							overflow: hidden scroll;">
					
						<div style="display:inline-block"> 
								<div id="employee-list"></div>
						</div>
					
						<div id="right-layout" >
						
							<div style="padding-bottom: 20px;display:inline-block;">
								<label id="flex-title" data-bind="i18n:'KMK004_268'"></label>
								<hr style="width: 518px;
								    text-align: left;
								    margin-left: 0;"  />
								<label id="selected-employee" data-bind="i18n:screenData().selectedName"></label>
								<div style="margin-top: 10px;" data-bind="component: {
									name: 'basic-settings-company',
									params: {
												screenData:screenData,
												screenMode:screenMode
											}
									}">
							</div>
							
							<div style="margin-top:15px; display: inline-block;" data-bind="component: {
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

const BASE_J_URL = 'screen/at/kmk004/j/';

const API_J_URL = {
	START_PAGE: BASE_J_URL + 'init-screen',
	CHANGE_YEAR: BASE_J_URL + 'change-year/',
	CHANGE_SHAID: BASE_J_URL + 'change-shaid/',
	REGISTER: BASE_J_URL + 'register',
	UPDATE: BASE_J_URL + 'update',
	DELETE: BASE_J_URL + 'delete/',
	AFTER_COPY: BASE_J_URL + 'after-copy/',
	CHANGE_SETTING: BASE_J_URL + 'change-setting/',
};

@component({
	name: 'screen-j-component',
	template
})

class ScreenJComponent extends ko.ViewModel {

	screenData: KnockoutObservable<FlexScreenData> = ko.observable(new FlexScreenData());

	screenMode = '';

	employeeList: KnockoutObservableArray<EmployeeModel> = ko.observableArray([]);

	alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);

	created(params: any) {
		let vm = this;

		vm.screenMode = params.screenMode;
		
		vm.screenData().initDumpData(params.startYM());
		
		vm.regSelectedYearEvent();

		vm.startPage();

		vm.initCCG001();

	}

	regSelectedYearEvent() {
		const vm = this;

		vm.screenData().selectedYear.subscribe((yearInput) => {

			if (!yearInput || !vm.screenData().selected()) {
				_.forEach(vm.screenData().monthlyWorkTimeSetComs(), (item) => {
					item.laborTime().checkbox(false);
					item.laborTime().legalLaborTime(null);
					item.laborTime().weekAvgTime(null);
					item.laborTime().withinLaborTime(null);
				});
				return;
			}

			let year = Number(yearInput);

			//check if data has in unsave list => bind data from that
			let unsaveItem = _.find(vm.screenData().unSaveSetComs, ['year', year]);

			if (unsaveItem) {
				let isChanged = vm.screenData().saveToUnSaveList();
				if (isChanged) { vm.screenData().setUpdateYear(vm.screenData().serverData.year); }
				vm.screenData().serverData = unsaveItem;
				vm.screenData().monthlyWorkTimeSetComs(_.map(unsaveItem.data, (item: IMonthlyWorkTimeSetCom) => { return new MonthlyWorkTimeSetCom(item); }));
			} else {
				let isChanged = vm.screenData().saveToUnSaveList();
				if (isChanged) { vm.screenData().setUpdateYear(vm.screenData().serverData.year); }
				//else load data from sever

				let selectedEmp = _.find(vm.employeeList(), ['code', vm.screenData().selected()]);
				vm.$blockui('invisible');
				vm.$ajax(API_J_URL.CHANGE_YEAR + selectedEmp.id + '/' + year).done((data) => {
					vm.screenData().setYM(year, data);
				}).always(() => { vm.$blockui('clear'); });

			}

		});
	}

	startPage() {
		const vm = this;

		vm.$blockui('invisible')
			.then(() => vm.$ajax(API_J_URL.START_PAGE))
			.done((data) => {
				vm.screenData().getFlexPredWorkTime(data.flexBasicSetting.flexPredWorkTime);
			})
			.always(() => vm.$blockui('clear'));
	}

	mounted() {
		const vm = this;
		vm.initEmployeeList();
		$("#com-ccg001").focus();
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
						vm.screenData().selected(listEmployee[0].code);
						$("#year-list").focus();
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
			selectedCode: vm.screenData().selected,
			isDialog: false,
			isShowNoSelectRow: false,
			alreadySettingList: vm.screenData().alreadySettingList,
			isShowWorkPlaceName: true,
			isShowSelectAllButton: false,
			disableSelection: false,
			maxRows: 12
		});

		vm.regSelectedEvent();

		vm.$blockui("hide");
		vm.screenData().selected.valueHasMutated();
	}

	regSelectedEvent() {
		const vm = this;

		vm.screenData().selected.subscribe((scd) => {

			if (!scd) {
				return;
			}

			let selectedEmp = _.find(vm.employeeList(), ['code', scd]);


			vm.$blockui('invisible')
				.then(() => vm.$ajax(API_J_URL.CHANGE_SHAID + selectedEmp.id))
				.done((data) => {
					vm.screenData().yearList(_.chain(data.yearList).map((item: any) => { return new YearItem(item); }).orderBy(['year'], ['desc']).value());
					vm.screenData().serverYears(data.yearList);
					vm.screenData().unSaveSetComs = [];
					data.yearList.reverse();
					if (vm.screenData().selectedYear() == data.yearList[0]) {
						vm.screenData().selectedYear.valueHasMutated();
					} else {
						vm.screenData().selectedYear(data.yearList[0]);
					}

					vm.screenData().comFlexMonthActCalSet(data.flexBasicSetting.flexMonthActCalSet);
				})
				.always(() => vm.$blockui('clear'));

			vm.setSelectedName(scd);


		});

	}

	setSelectedName(scd: string) {
		const vm = this;

		let datas: Array<EmployeeModel> = vm.employeeList(),

			selectedItem: EmployeeModel = _.find(datas, ['code', scd]);

		vm.screenData().selectedName(selectedItem ? selectedItem.name : '')

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

