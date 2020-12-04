/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.view.kmk004.l {
	import IParam = nts.uk.at.view.kmk004.p.IParam;
	import tree = kcp.share.tree;

	const template = `
		<div class="sidebar-content-header">
		<div class="title" data-bind="i18n: 'Com_Employment'"></div>
		<a class="goback" data-bind="ntsLinkButton: { jump: '/view/kmk/004/a/index.xhtml' },i18n: 'KMK004_224'"></a>
		<button class="proceed" data-bind="i18n: 'KMK004_225'"></button>
		<button data-bind="visible: true, i18n: 'KMK004_226'"></button>
		<button class="danger" data-bind="i18n: 'KMK004_227'"></button>
	</div>
	
	<div class="view-n">
	<table>
		<tr>
			<td id="view-n-left-side">
				<div id="empt-list-setting"></div>
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
								params: params
							}"></div>
						</div>
						<div data-bind="ntsFormLabel: {inline: true}, i18n: 'KMK004_232'"></div>
					</div>
					<div class="content">
						<button id = "btn_year" data-bind="i18n: 'KMK004_233'"></button>
							<div class="div_row"> 
								<div class= "box-year" data-bind="component: {
									name: 'box-year',
									params:{ 
										selectedYear: selectedYear,
										change: changeYear
									}
								}"></div>
								
								<div id= class= "time-work" data-bind="component: {
									name: 'time-work',
									params:{
										selectedYear: selectedYear,
										change: changeYear,
										checkEmployee: checkEmployee
									}
								}"></div>
							</div>
					</div>
				</div>
			</td>
		</tr>
	</table>
	`;

	export class ListType {
		static EMPLOYMENT = 1;
		static Classification = 2;
		static JOB_TITLE = 3;
		static EMPLOYEE = 4;
	}

	interface UnitModel {
		code: string;
		name?: string;
		workplaceName?: string;
		isAlreadySetting?: boolean;
	}

	interface UnitAlreadySettingModel {
		code: string;
		isAlreadySetting: boolean;
	}

	@component({
		name: 'view-n',
		template
	})

	export class ViewNComponent extends ko.ViewModel {
		listComponentOption: any;
		selectedCode: KnockoutObservable<string>;
		multiSelectedCode: KnockoutObservableArray<string>;
		isShowAlreadySet: KnockoutObservable<boolean>;
		alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
		isDialog: KnockoutObservable<boolean>;
		isShowNoSelectRow: KnockoutObservable<boolean>;
		isMultiSelect: KnockoutObservable<boolean>;
		employeeList: KnockoutObservableArray<UnitModel>;

		isDisplayClosureSelection: KnockoutObservable<boolean>;
		isDisplayFullClosureOption: KnockoutObservable<boolean>;
		closureSelectionType: KnockoutObservable<number>;
		selectClosureTypeList: KnockoutObservableArray<any>;
		currentItemName: KnockoutObservable<string>;
		
		public selectedYear: KnockoutObservable<number | null> = ko.observable(null);
		public changeYear: KnockoutObservable<boolean> = ko.observable(true);
		public checkEmployee: KnockoutObservable<boolean> = ko.observable(false);
		public existYear: KnockoutObservable<boolean> = ko.observable(false);

		constructor(private params: IParam){
			super();
		}
		
		created() {
			let vm = this;
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
			vm.isDisplayClosureSelection = ko.observable(true);
			vm.isDisplayFullClosureOption = ko.observable(false);
			vm.closureSelectionType = ko.observable(1);
			vm.selectClosureTypeList = ko.observableArray([
				{ code: 1, name: 'Select Full Closure option' },
				{ code: 2, name: 'Select by selected closure code' },
				{ code: 3, name: 'Nothing (Select first option)' },
			]);

			vm.listComponentOption = {
				isShowAlreadySet: vm.isShowAlreadySet(),
				isMultiSelect: vm.isMultiSelect(),
				listType: ListType.EMPLOYMENT,
				selectType: tree.SelectionType.SELECT_BY_SELECTED_CODE,
				selectedCode: vm.selectedCode,
				isDialog: vm.isDialog(),
				isShowNoSelectRow: vm.isShowNoSelectRow(),
				alreadySettingList: vm.alreadySettingList,
				maxRows: 12,
				isDisplayClosureSelection: vm.isDisplayClosureSelection(),
			};

			vm.employeeList = ko.observableArray<UnitModel>([]);
			vm.currentItemName = ko.observable('');
			vm.params = {sidebarType : "Com_Employment", wkpId: '', empCode :'', empId: '', titleName:'', deforLaborTimeComDto: null, settingDto: null}
			vm.selectedYear
			.subscribe(() => {
				if(vm.selectedYear != null) {
					vm.existYear(true);
				}
			});
		}

		mounted() {
			const vm = this;
			$('#empt-list-setting').ntsListComponent(vm.listComponentOption).done(() => {
				vm.employeeList($('#empt-list-setting').getDataList());
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
						vm.currentItemName(selectedItem.name);
						vm.params.empCode = newValue;
						vm.params.titleName = vm.currentItemName();
					}
				});
				vm.selectedCode.valueHasMutated();
			});
		}
		
		openViewP() {
			let vm = this;
			vm.$window.modal('at', '/view/kmk/004/p/index.xhtml', vm.params)
		}
	}
}