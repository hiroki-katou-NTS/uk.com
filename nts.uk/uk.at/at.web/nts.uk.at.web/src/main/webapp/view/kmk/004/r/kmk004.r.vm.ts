/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmk004.r {


	const API = {
	};

	@bean()
	export class ViewModel extends ko.ViewModel {
		param: IParam = {
			screenMode: 'WORK_PLACE',
			data: [],
			selectedCode: null,
			alreadySettingList: []
		};

		screenData: KnockoutObservable<ScreenData> = ko.observable(new ScreenData(this.param));

		getTitleText() {
			let vm = this;
			if (vm.param.screenMode === 'WORK_PLACE') {
				return vm.$i18n.text('KMK004_347');
			}
			if (vm.param.screenMode === 'EMPLOYMENT') {
				return vm.$i18n.text('KMK004_348');

			}
			if (vm.param.screenMode === 'EMPLOYEE') {
				return vm.$i18n.text('KMK004_349');
			}

		}

		register() {

		}

		close() {
			let vm = this;
			vm.$window.close();
		}


		created(param: IParam) {

			let vm = this;
			if (param) {
				vm.param = param;

				vm.screenData(new ScreenData(param));
			}

			if (vm.param.screenMode === 'WORK_PLACE') {
				vm.initWorkPlace();
			}

			if (vm.param.screenMode === 'EMPLOYMENT') {
				vm.initEmployment();
			}

			if (vm.param.screenMode === 'EMPLOYEE') {
				vm.initEmployee();
			}
		}

		initWorkPlace() {
			const vm = this,
				StartMode = {
					WORKPLACE: 0,
					DEPARTMENT: 1
				},
				SelectionType = {
					SELECT_BY_SELECTED_CODE: 1,
					SELECT_ALL: 2,
					SELECT_FIRST_ITEM: 3,
					NO_SELECT: 4
				};
				let wpOption = {
					isShowAlreadySet: false,
					isMultipleUse: true,
					isMultiSelect: false,
					startMode: StartMode.WORKPLACE,
					selectedId: vm.screenData().selectedCode,
					baseDate: ko.observable(new Date()),
					selectType: SelectionType.SELECT_FIRST_ITEM,
					isShowSelectButton: true,
					isDialog: true,
					alreadySettingList: vm.screenData().alreadySettingList,
					maxRows: 10,
					tabindex: 1,
					systemType: 2
				};

			$('#work-place-list').ntsTreeComponent(wpOption);
		}

		initEmployment() {
			let vm = this,
				empOption = {
					isShowAlreadySet: false,
					isMultiSelect: false,
					listType: ListType.EMPLOYMENT,
					selectType: SelectType.SELECT_BY_SELECTED_CODE,
					selectedCode: vm.screenData().selectedCode,
					isDialog: true,
					isShowNoSelectRow: false,
					alreadySettingList: vm.screenData().alreadySettingList,
					maxRows: 9
				};

			$('#employment-list').ntsListComponent(empOption);
		}

		initEmployee() {
			let vm = this;
			let employeeOption = {
				isShowAlreadySet: false,
				isMultiSelect: false,
				listType: ListType.EMPLOYEE,
				employeeInputList: vm.screenData().data,
				selectType: SelectType.SELECT_BY_SELECTED_CODE,
				selectedCode: vm.screenData().selectedCode,
				isDialog: true,
				isShowNoSelectRow: false,
				alreadySettingList: vm.screenData().alreadySettingList,
				isShowWorkPlaceName: false,
				isShowSelectAllButton: false,
				disableSelection: false,
				maxRows: 9
			};

			$('#employee-list').ntsListComponent(employeeOption);

		}
	}

	export class ScreenData {
		data: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedCode: KnockoutObservable<string> = ko.observable();
		alreadySettingList: KnockoutObservableArray<any> = ko.observableArray([]);
		constructor(param?: IParam) {
			if (param) {
				this.data(param.data);
				this.selectedCode(param.selectedCode);
				this.alreadySettingList(param.alreadySettingList);
			}
		}

	}

	interface IParam {
		screenMode: 'WORK_PLACE' | 'EMPLOYMENT' | 'EMPLOYEE';
		data: Array<any>;//EMPLOYEE = UnitModel
		selectedCode: string;
		alreadySettingList: Array<any>;
	}



	enum ListType {
		EMPLOYMENT = 1,
		Classification = 2,
		JOB_TITLE = 3,
		EMPLOYEE = 4,
		WORKPLACE = 5
	}

	export interface UnitModel {
		id?: string;
		code: string;
		name?: string;
		workplaceName?: string;
		isAlreadySetting?: boolean;
		optionalColumn?: any;
	}

	export class SelectType {
		static SELECT_BY_SELECTED_CODE = 1;
		static SELECT_ALL = 2;
		static SELECT_FIRST_ITEM = 3;
		static NO_SELECT = 4;
	}

	export interface UnitAlreadySettingModel {
		code: string;
		isAlreadySetting: boolean;
	}

}
