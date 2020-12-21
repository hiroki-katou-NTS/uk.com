/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.l {
	import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;
	import IParam = nts.uk.at.view.kmk004.p.IParam;
	import SIDEBAR_TYPE = nts.uk.at.view.kmk004.p.SIDEBAR_TYPE;
	import IYear = nts.uk.at.view.kmk004.components.transform.IYear;
	const DATE_FORMAT = 'YYYY/MM/DD';

	const template = `
		<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Person'"></div>
		<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button class="proceed" data-bind="enable: existYear, i18n: 'KMK004_225'"></button>
		<button data-bind="visible: true, i18n: 'KMK004_226'"></button>
		<button class="danger" data-bind="enable: existYear, i18n: 'KMK004_227'"></button>
	</div>
	
	<div class="view-o">
	<div id="com-ccg001"></div>
	<table>
		<tr>
			<td id="view-o-left-side">
				<div id="com-kcp005"></div>
			</td>
			
			<td id="right-side">
				<div class="view-l-common">
					<div class="header-l">
						<div id="title", data-bind="i18n: 'KMK004_307'"></div>
						<hr></hr>
						<div class="selected-item">
							<label data-bind= "text: currentItemName"></label>
						</div>
						
						<div class="header_title">
							<div data-bind="ntsFormLabel: {inline: true}, i18n: 'KMK004_229'"></div>
							<button data-bind="i18n: 'KMK004_338', click: openViewP"></button>
						</div>
						<div class="header_content">
							<div data-bind="component: {
								name: 'view-l-basic-setting',
								params: paramL 
							}"></div>
						</div>
						<div data-bind="ntsFormLabel: {inline: true}, i18n: 'KMK004_232'"></div>
					</div>
					<div class="content">
						<div class="div_row"> 
							<div class= "box-year" data-bind="component: {
									name: 'box-year',
									params:{ 
										selectedYear: selectedYear,
										type: type,
										selectedId: selectedId
									}
								}"></div>
								
								<div id= class= "time-work" data-bind="component: {
									name: 'time-work',
									params:{
										selectedYear: selectedYear,
										years: years,
										checkEmployee: true
									}
								}"></div>
							</div>
					</div>
				</div>
			</td>
		</tr>
	</table>
	`;

	interface UnitModel {
		id?: string;
		code: string;
		name?: string;
		workplaceName?: string;
		isAlreadySetting?: boolean;
		optionalColumn?: any;
	}

	interface UnitAlreadySettingModel {
		id: string;
		isAlreadySetting: boolean;
	}

	@component({
		name: 'view-o',
		template
	})

	export class ViewOComponent extends ko.ViewModel {
		ccg001ComponentOption: GroupOption;

		//KCP005
		listComponentOption: any;
		selectedCode: KnockoutObservable<string>;
		multiSelectedCode: KnockoutObservableArray<string>;
		isShowAlreadySet: KnockoutObservable<boolean>;
		alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel> = ko.observableArray([]);
		isDialog: KnockoutObservable<boolean>;
		isShowNoSelectRow: KnockoutObservable<boolean>;
		isMultiSelect: KnockoutObservable<boolean>;
		isShowWorkPlaceName: KnockoutObservable<boolean>;
		isShowSelectAllButton: KnockoutObservable<boolean>;
		disableSelection: KnockoutObservable<boolean>;

		employeeList: KnockoutObservableArray<UnitModel> = ko.observableArray([]);
		currentItemName: KnockoutObservable<string>;
		public selectedYear: KnockoutObservable<number | null> = ko.observable(null);
		public existYear: KnockoutObservable<boolean> = ko.observable(false);
		public years: KnockoutObservableArray<IYear> = ko.observableArray([]);
		public type: SIDEBAR_TYPE = 'Com_Person';
		public selectedId: KnockoutObservable<string> = ko.observable('');
		paramL: IParam;
		isLoadData: KnockoutObservable<boolean> = ko.observable(false);
		//isLoadInitData: KnockoutObservable<boolean>;
		btn_text: KnockoutObservable<string> = ko.observable('');

		constructor(private params: IParam) {
			super();
			}

		created() {
			const vm = this;
			vm.ccg001ComponentOption = {
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
				baseDate: ko.observable(moment().format(DATE_FORMAT)),
				periodStartDate: ko.observable(new Date()),
				periodEndDate: ko.observable(new Date()),
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

				returnDataFromCcg001: function(data: any) {
					const employees = data.listEmployee
						.map((m: any) => ({
							workplaceName: m.affiliationName,
							code: m.employeeCode,
							name: m.employeeName,
							id: m.employeeId
						}));

					vm.employeeList(employees);
				}
					
			}

			//KCP005
			vm.selectedCode = ko.observable('1');
			vm.multiSelectedCode = ko.observableArray(['0', '1', '4']);
			vm.isShowAlreadySet = ko.observable(true);
			vm.isDialog = ko.observable(false);
			vm.isShowNoSelectRow = ko.observable(false);
			vm.isMultiSelect = ko.observable(false);
			vm.isShowWorkPlaceName = ko.observable(true);
			vm.isShowSelectAllButton = ko.observable(false);
			vm.disableSelection = ko.observable(false);
			vm.currentItemName = ko.observable('');
			vm.alreadySettingList = ko.observableArray([
				{ id: '1', isAlreadySetting: true },
				{ id: '2', isAlreadySetting: true }
			]);

			vm.listComponentOption = {
				isShowAlreadySet: vm.isShowAlreadySet(),
				isMultiSelect: vm.isMultiSelect(),
				listType: ListType.EMPLOYEE,
				employeeInputList: vm.employeeList,
				selectType: SelectType.SELECT_FIRST_ITEM,
				selectedCode: vm.selectedCode,
				isDialog: vm.isDialog(),
				isShowNoSelectRow: vm.isShowNoSelectRow(),
				alreadySettingList: vm.alreadySettingList,
				isShowWorkPlaceName: vm.isShowWorkPlaceName(),
				isShowSelectAllButton: vm.isShowSelectAllButton(),
				disableSelection: vm.disableSelection()
			};
			vm.paramL = {isLoadData: vm.isLoadData, sidebarType: "Com_Person", wkpId: ko.observable(''), empCode: ko.observable(''), empId: ko.observable(''), titleName: '', deforLaborTimeComDto: null, settingDto: null };
			vm.selectedYear
				.subscribe(() => {
					if (vm.selectedYear != null) {
						vm.existYear(true);
					}
				});

		}

		mounted() {
			let vm = this;
			$('#com-kcp005').ntsListComponent(vm.listComponentOption);
			$('#com-ccg001').ntsGroupComponent(vm.ccg001ComponentOption).done(() => {

				vm.selectedCode.subscribe((newValue) => {
					let selectedItem: UnitModel = _.find(vm.employeeList(), ['code', newValue]);
					vm.currentItemName(selectedItem.code + " " + selectedItem.name);
					vm.paramL.empId(newValue);
					vm.paramL.titleName = vm.currentItemName();
					vm.selectedId(newValue);
				});
			});
		}

		openViewP() {
			let vm = this;
			vm.$window.modal('at', '/view/kmk/004/p/index.xhtml', ko.toJS(vm.paramL)).then(() => {
				vm.isLoadData(true);
			});
		}
	}


}