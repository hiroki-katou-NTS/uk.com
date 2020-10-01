/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.cmm024.f {

	import service = nts.uk.com.view.cmm024.a.service;
	import EmployeeDto = nts.uk.com.view.cmm024.a.service.EmployeeDto;
	//import UnitModel = kcp.share.tree.UnitModel;
	import TreeComponentOption = kcp.share.tree.TreeComponentOption;
	import StartMode = kcp.share.tree.StartMode;
	import SelectType = kcp.share.tree.SelectionType;
	import UnitAlreadySettingModel = kcp.share.tree.UnitAlreadySettingModel;
	import SystemType = kcp.share.tree.SystemType;

	@bean()
	class ViewModel extends ko.ViewModel {

		itemsSwap: KnockoutObservableArray<EmployeeDto> = ko.observableArray([]);
		columns: KnockoutObservableArray<nts.uk.ui.NtsGridListColumn>;
		multiSelectedId: KnockoutObservable<any>;
		baseDate: KnockoutObservable<Date>;
		alreadySettingList: KnockoutObservableArray<UnitAlreadySettingModel>;
		treeGrid: TreeComponentOption;
		//getShare & setShare
		workplaceId: KnockoutObservable<string> = ko.observable(null);
		currentCodeListSwap: KnockoutObservableArray<EmployeeDto> = ko.observableArray([]);
		oldCodeListSwap: KnockoutObservableArray<EmployeeDto> = ko.observableArray([]);

		constructor(params: any) {
			// start point of object
			super();
			let vm = this;

			vm.columns = ko.observableArray([
				{ headerText: vm.$i18n('CMM024_50'), key: 'employeeCode', width: 120, formatter: _.escape },
				{ headerText: vm.$i18n('CMM024_51'), key: 'employeeName', width: 140, formatter: _.escape },
				{ headerText: '', key: 'employeeId', hidden: true, formatter: _.escape },
			]);

			vm.baseDate = ko.observable(new Date());
			vm.multiSelectedId = ko.observableArray([]);
			vm.alreadySettingList = ko.observableArray([]);
			vm.treeGrid = {
				isShowAlreadySet: false,
				isMultipleUse: false,
				isMultiSelect: false,
				startMode: StartMode.WORKPLACE,
				selectedId: vm.multiSelectedId,
				baseDate: vm.baseDate,
				selectType: SelectType.SELECT_FIRST_ITEM, //3
				isShowSelectButton: true,
				isDialog: true,
				alreadySettingList: vm.alreadySettingList,
				maxRows: 10,
				tabindex: 1,
				systemType: SystemType.EMPLOYMENT, //2
			};
			//$('#tree-grid').ntsTreeComponent(vm.treeGrid);
		}

		created(params: any) {
			// start point of object
			let vm = this;
		}

		mounted() {
			// raise event when view initial success full
			let vm = this,
				selectType: number = 0;

			$('#tree-grid').ntsTreeComponent(vm.treeGrid);

			//get share
			vm.$window.storage('workPlaceCodeList').then((data) => {

				let codeList: Array<any> = [];
				//remove if employeeCode is null / empty
				data.codeList && data.codeList.map((item) => {
					if (!nts.uk.util.isNullOrEmpty(item.employeeCode)
						&& item.employeeCode != '-1') {
						codeList.push(item);
					}
				});

				vm.currentCodeListSwap(codeList);
				vm.oldCodeListSwap(codeList);
				/*vm.workplaceId(data.workplaceId);
				vm.multiSelectedId().push(data.workplaceId);
				 selectType = (!nts.uk.util.isNullOrEmpty(vm.workplaceId()))
					? SelectType.SELECT_BY_SELECTED_CODE
					: SelectType.SELECT_FIRST_ITEM;
				vm.treeGrid.selectType = selectType;*/

			});

			vm.multiSelectedId.subscribe((workplaceId) => {
				vm.getEmployeesFromCompanyWorkplace(workplaceId);
			});

		}


		getEmployeesFromCompanyWorkplace(wpId: string) {

			let vm = this,
				employees: Array<EmployeeDto> = [],
				params = { workplaceId: wpId, baseDate: vm.baseDate };

			vm.$blockui('show');
			if (!nts.uk.util.isNullOrEmpty(wpId)) {
				service.getEmployeesListByWorkplace(ko.toJS(params))
					.done((response) => {
						if (!nts.uk.util.isNullOrEmpty(response)) {
							response.forEach((element) => {
								employees.push(new EmployeeDto(
									element.empCd, element.empName,
									element.empId, element.empId
								)); //'基本給　給本給本'
							});
						}

						//clear emplyees list
						vm.itemsSwap([]);
						vm.itemsSwap(employees);
						vm.$blockui('hide');

					})
					.always(() => vm.$blockui('hide'));
			}
		}


		/**
		 * Process
		 * */
		proceed() {
			let vm = this;

			if (!nts.uk.ui.errors.hasError()) {
				vm.$window.storage('newWorkPlaceCodeList', {
					workplaceId: vm.multiSelectedId()[0],
					codeList: vm.currentCodeListSwap()
				});
				vm.$window.close();
				return false;
			}
		}

		/**
		 * Cancel and close window
		 * */
		cancel() {
			let vm = this;
			vm.$window.storage('newWorkPlaceCodeList', null);
			vm.$window.close();
			return false;
		}
	}
}