/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.l {
	import tree = kcp.share.tree;
	import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;
	import IParam = nts.uk.at.view.kmk004.p.IParam;
	import SIDEBAR_TYPE = nts.uk.at.view.kmk004.p.SIDEBAR_TYPE;
	import IYear = nts.uk.at.view.kmk004.components.transform.IYear;

	const template = `
		<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Person'"></div>
		<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button class="proceed" data-bind="i18n: 'KMK004_225'"></button>
		<button data-bind="visible: true, i18n: 'KMK004_226'"></button>
		<button class="danger" data-bind="i18n: 'KMK004_227'"></button>
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
										param: param,
										type: type,
										years: years
									}
								}"></div>
								
								<div id= class= "time-work" data-bind="component: {
									name: 'time-work',
									params:{
										selectedYear: selectedYear,
										years: years
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
		alreadySettingList: KnockoutObservableArray<tree.UnitAlreadySettingModel>;
		isDialog: KnockoutObservable<boolean>;
		isShowNoSelectRow: KnockoutObservable<boolean>;
		isMultiSelect: KnockoutObservable<boolean>;
		isShowWorkPlaceName: KnockoutObservable<boolean>;
		isShowSelectAllButton: KnockoutObservable<boolean>;
		disableSelection: KnockoutObservable<boolean>;

		employeeList: KnockoutObservableArray<UnitModel>;
		currentItemName: KnockoutObservable<string>;

		public selectedYear: KnockoutObservable<number | null> = ko.observable(null);
		public existYear: KnockoutObservable<boolean> = ko.observable(false);
		public years: KnockoutObservableArray<IYear> = ko.observableArray([]);
		public type: SIDEBAR_TYPE = 'Com_Workplace';
		public param: KnockoutObservable<string> = ko.observable('');
		paramL: IParam;
		isLoadData: KnockoutObservable<boolean> = ko.observable(false);
		
		
		constructor(private params: IParam){
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
				baseDate: moment().toISOString(),
				/*	periodStartDate: periodStartDate,
					periodEndDate: periodEndDate,*/
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
			}

			//KCP005
			//vm.baseDate = ko.observable(new Date());
			vm.selectedCode = ko.observable('1');
			vm.multiSelectedCode = ko.observableArray(['0', '1', '4']);
			vm.isShowAlreadySet = ko.observable(true);
			vm.alreadySettingList = ko.observableArray([
				{ code: '1', isAlreadySetting: true },
				{ code: '2', isAlreadySetting: true }
			]);
			vm.isDialog = ko.observable(false);
			vm.isShowNoSelectRow = ko.observable(false);
			vm.isMultiSelect = ko.observable(false);
			vm.isShowWorkPlaceName = ko.observable(true);
			vm.isShowSelectAllButton = ko.observable(false);
			vm.disableSelection = ko.observable(false);
			vm.currentItemName = ko.observable('');

			this.employeeList = ko.observableArray<UnitModel>([
				{ id: '1a', code: 'bdb8fb2a-35b5-47c5-8828-74c4fdde4c7a', name: '日通　社員1', workplaceName: 'HN' },
				{ id: '2b', code: 'ae7fe82e-a7bd-4ce3-adeb-5cd403a9d570', name: '日通　社員2', workplaceName: 'HN' },
				{ id: '3c', code: '07b39c1f-c02e-4354-aa9d-650b0597a0e7', name: '日通　社員3', workplaceName: 'HN' },
				{ id: '4d', code: '02a5ab6b-ebff-41ff-91ec-ed1adfa93b9f', name: '日通　社員4', workplaceName: 'HN' },
				{ id: '58', code: '000000000005', name: '日通　社員5', workplaceName: 'HN' },
				{ id: '6f', code: '000000000006', name: '日通　社員6', workplaceName: 'HN' },
				{ id: '7g', code: '000000000007', name: '日通　社員7', workplaceName: 'HN' },
				{ id: '8h', code: '000000000008', name: '日通　社員8', workplaceName: 'HN' },
				{ id: '9i', code: '000000000009', name: '日通　社員9', workplaceName: 'HN' },
			]);
			
			vm.listComponentOption = {
				isShowAlreadySet: vm.isShowAlreadySet(),
				isMultiSelect: vm.isMultiSelect(),
				listType: ListType.EMPLOYEE,
				employeeInputList: vm.employeeList,
				selectType: tree.SelectionType.SELECT_BY_SELECTED_CODE,
				selectedCode: vm.selectedCode,
				isDialog: vm.isDialog(),
				isShowNoSelectRow: vm.isShowNoSelectRow(),
				alreadySettingList: vm.alreadySettingList,
				isShowWorkPlaceName: vm.isShowWorkPlaceName(),
				isShowSelectAllButton: vm.isShowSelectAllButton(),
				disableSelection: vm.disableSelection()
			};
			vm.paramL = {isLoadData: vm.isLoadData, sidebarType : "Com_Person", wkpId: ko.observable(''), empCode: ko.observable(''), empId: ko.observable(''), titleName: '', deforLaborTimeComDto: null, settingDto: null};
			vm.selectedYear
			.subscribe(() => {
				if(vm.selectedYear != null) {
					vm.existYear(true);
				}
			});
			
			vm.selectedCode.subscribe((newValue) => {
				let selectedItem : UnitModel = _.find(vm.employeeList(), ['code', newValue]);
				vm.currentItemName(selectedItem.code + " " + selectedItem.name);
				vm.paramL.empId(newValue);
				vm.paramL.titleName = vm.currentItemName();
				vm.param(newValue);
			});
			
		}

		mounted() {
			let vm = this;
			$('#com-kcp005').ntsListComponent(vm.listComponentOption);
			$('#com-ccg001').ntsGroupComponent(vm.ccg001ComponentOption).done(() => {
				if (vm.employeeList().length > 0) 
					vm.selectedCode(vm.employeeList()[0].code);
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