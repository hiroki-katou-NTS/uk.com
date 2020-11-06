/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmk004.r {


	const API = {
	};

	@bean()
	export class ViewModel extends ko.ViewModel {
		param: IParam = {
			screenMode: 'WORK_PLACE',
			data: [],
			selectedCode: null
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
			let vm = this,
				listComponentOption = {
					isShowAlreadySet: false,
					isMultiSelect: false,
					isMultipleUse: true,
					listType: ListType.WORKPLACE,
					selectType: SelectType.SELECT_BY_SELECTED_CODE,
					selectedCode: vm.screenData().selectedCode,
					isDialog: true,
					isShowNoSelectRow: false,
					alreadySettingList: ko.observableArray([]),
					maxRows: 9
				};

			$('#work-place-list').ntsListComponent(listComponentOption);
		}

		initEmployment() {
			let vm = this,
				listComponentOption = {
					isShowAlreadySet: false,
					isMultiSelect: false,
					listType: ListType.EMPLOYMENT,
					selectType: SelectType.SELECT_BY_SELECTED_CODE,
					selectedCode: vm.screenData().selectedCode,
					isDialog: true,
					isShowNoSelectRow: false,
					alreadySettingList: ko.observableArray([]),
					maxRows: 9
				};

			$('#employment-list').ntsListComponent(listComponentOption);
		}

		initEmployee() {
			let vm = this;
			let listComponentOption = {
				isShowAlreadySet: false,
				isMultiSelect: false,
				listType: ListType.EMPLOYEE,
				employeeInputList: vm.screenData().data,
				selectType: SelectType.SELECT_BY_SELECTED_CODE,
				selectedCode: vm.screenData().selectedCode,
				isDialog: true,
				isShowNoSelectRow: false,
				alreadySettingList: ko.observableArray([]),
				isShowWorkPlaceName: false,
				isShowSelectAllButton: false,
				disableSelection: false,
				maxRows: 9
			};

			$('#employee-list').ntsListComponent(listComponentOption);

		}
	}

	export class ScreenData {
		data: KnockoutObservableArray<any> = ko.observableArray([]);
		selectedCode: KnockoutObservable<string> = ko.observable();
		constructor(param?: IParam) {
			if (param) {
				this.data(param.data);
				this.selectedCode(param.selectedCode);
			}
		}

	}

	interface IParam {
		screenMode: 'WORK_PLACE' | 'EMPLOYMENT' | 'EMPLOYEE';
		data: Array<any>;//EMPLOYEE = UnitModel
		selectedCode: string;
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
