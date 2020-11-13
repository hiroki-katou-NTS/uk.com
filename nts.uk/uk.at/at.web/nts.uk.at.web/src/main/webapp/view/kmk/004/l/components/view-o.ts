/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.l {
	import tree = kcp.share.tree;
	import GroupOption = nts.uk.com.view.ccg.share.ccg.service.model.GroupOption;

	const template = `
		<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Company'"></div>
		<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button class="proceed" data-bind="i18n: 'KMK004_225'"></button>
		<button class="danger" data-bind="i18n: 'KMK004_227'"></button>
	</div>
	
	<div class="view-o">
	<table>
		<tr>
			<td>
				<div id="com-ccg001"></div>
			</td>
			<td id="view-o-left-side">
				<div id="com-kcp005"></div>
			</td>
			
			<td id="right-side">
				<div class="view-l">
					<div class="header-l">
						<div id="title", data-bind="i18n: 'KMK004_307'"></div>
						<hr></hr>
						<div>
							<label data-bind= "text: currentItemName"></label>
						</div>
						
						<div class="header_title">
							<div data-bind="ntsFormLabel: {}, i18n: 'KMK004_229'"></div>
							<button data-bind="i18n: 'KMK004_338'"></button>
						</div>
						<div class="header_content">
							<div data-bind="visible: false, component: {
								name: 'view-l-basic-setting',
								params:{ 
								}
							}"></div>
						</div>
						<div data-bind="ntsFormLabel: {}, i18n: 'KMK004_232'"></div>
					</div>
					<div class="content">
						<button id = "btn_year" data-bind="i18n: 'KMK004_233'"></button>
						<table id = "btm_area">
							<tr>
								<td>
									<div data-bind="component: {
										name: 'view-l-listbox'
									}"></div>
								</td>
								<td>
									<div data-bind="component: {
										name: 'view-l-times-table'
									}"></div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</td>
		</tr>
	</table>
	`;

	interface Params {

	}

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


		created(params: Params) {
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
				{ id: '1a', code: '000000000001', name: '日通　社員1', workplaceName: 'HN' },
				{ id: '2b', code: '000000000002', name: '日通　社員2', workplaceName: 'HN' },
				{ id: '3c', code: '000000000003', name: '日通　社員3', workplaceName: 'HN' },
				{ id: '4d', code: '000000000004', name: '日通　社員4', workplaceName: 'HN' },
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
		}

		mounted() {
			let vm = this;
			$('#com-ccg001').ntsGroupComponent(vm.ccg001ComponentOption).done(() => {
			});

			$('#com-kcp005').ntsListComponent(vm.listComponentOption).done(() => {
				if (vm.employeeList().length > 0) {
					vm.selectedCode(vm.employeeList()[0].code);
				}
				
				vm.selectedCode.subscribe((newValue) => {
					if (nts.uk.text.isNullOrEmpty(newValue) || newValue == "undefined") {
						vm.currentItemName("");
						return;
					}
					
					let selectedItem = _.find(vm.employeeList(), emp => {
						return emp.code == newValue;
					});
					
					if (selectedItem) {
						vm.currentItemName(selectedItem.code + " " + selectedItem.name);
					}
				});
				vm.selectedCode.valueHasMutated();
			});

		}
	}


}