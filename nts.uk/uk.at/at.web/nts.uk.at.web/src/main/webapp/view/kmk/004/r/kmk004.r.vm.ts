/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.at.kmk004.r {


	const API = {
		COPY_WKP: 'screen/at/kmk004/r/copy-wkp',
		COPY_EMP: 'screen/at/kmk004/r/copy-emp',
		COPY_SHA: 'screen/at/kmk004/r/copy-sha'
	};

	@bean()
	export class ViewModel extends ko.ViewModel {
		param: IParam = null;

		screenData: KnockoutObservable<ScreenData> = ko.observable(new ScreenData(this.param));

		getTitleText() {
			const vm = this;
			if (vm.param.screenMode === 'Com_Workplace') {
				return vm.$i18n.text('KMK004_347');
			}
			if (vm.param.screenMode === 'Com_Employment') {
				return vm.$i18n.text('KMK004_348');
			}
			if (vm.param.screenMode === 'Com_Person') {
				return vm.$i18n.text('KMK004_349');
			}

		}

		copy() {
			const vm = this;

			if (!vm.screenData().selecteds().length) {
				vm.$dialog.error({ messageId: 'Msg_1907' });
				return;
			}

			let ULR = '',
				cmd: ICopyCommand = {
					// 複写元職場ID
					copySource: vm.param.selected,
					// 勤務区分
					laborAttr: vm.param.laborAttr,
					// 年度
					year: vm.param.year,
					// 複写先職場ID（List）
					copyDestinations: ko.toJS(vm.screenData().selecteds())
				};

			if (vm.param.screenMode === 'Com_Workplace') {
				ULR = API.COPY_WKP;
			}
			if (vm.param.screenMode === 'Com_Employment') {
				ULR = API.COPY_EMP;
			}
			if (vm.param.screenMode === 'Com_Person') {
				ULR = API.COPY_SHA;
				cmd.copySource = _.find(vm.screenData().data(), ['code', vm.param.selected]).id;
				cmd.copyDestinations = _.map(cmd.copyDestinations, (code) => {
					let emp: any = _.find(vm.screenData().data(), ['code', code]);
					return emp.id;
				});
			}

			vm.$blockui('invisible')
				.then(() => vm.$ajax(ULR, cmd))
				.done(() => {
					vm.$window.close();
				})
				.always(() => vm.$blockui('clear'));
		}

		close() {
			const vm = this;
			vm.$window.close();
		}


		created(param: IParam) {

			const vm = this;
			if (param) {
				vm.param = param;

				vm.screenData(new ScreenData(param));
			}

			if (vm.param.screenMode === 'Com_Workplace') {
				vm.initWorkPlace();


			}

			if (vm.param.screenMode === 'Com_Employment') {
				vm.initEmployment();
			}

			if (vm.param.screenMode === 'Com_Person') {
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
				isMultiSelect: true,
				startMode: StartMode.WORKPLACE,
				selectedId: vm.screenData().selecteds,
				baseDate: ko.observable(new Date()),
				selectType: SelectionType.SELECT_BY_SELECTED_CODE,
				isShowSelectButton: true,
				isDialog: true,
				alreadySettingList: ko.observableArray([]),
				maxRows: 12,
				tabindex: 1,
				systemType: 2
			};
			let windowSize = nts.uk.ui.windows.getSelf();

			windowSize.$dialog.dialog("option", { width: 530, height: 570 });
			$(window).on("resize", function() {
				let windowSize = nts.uk.ui.windows.getSelf();
                if ( 570 - windowSize.$dialog.dialog().height() < 34 ) {
                    _.defer(() => { windowSize.$dialog.dialog( "option", { width: 530, height: 570 } ); } );
				}
			});
			$('#work-place-list').ntsTreeComponent(wpOption).done(() => {
				$('#work-place-list  .ntsSearchBox').focus();
			});
		}

		initEmployment() {
			const vm = this,
				empOption = {
					isShowAlreadySet: false,
					isMultiSelect: true,
					listType: ListType.EMPLOYMENT,
					selectType: SelectType.SELECT_BY_SELECTED_CODE,
					selectedCode: vm.screenData().selecteds,
					isDialog: true,
					isShowNoSelectRow: false,
					alreadySettingList: ko.observableArray([]),
					maxRows: 12
				};
			$('#employment-list').ntsListComponent(empOption);
		}

		initEmployee() {
			const vm = this,
				employeeOption = {
					isShowAlreadySet: false,
					isMultiSelect: true,
					listType: ListType.EMPLOYEE,
					employeeInputList: vm.screenData().data,
					selectType: SelectType.SELECT_BY_SELECTED_CODE,
					selectedCode: vm.screenData().selecteds,
					isDialog: true,
					isShowNoSelectRow: false,
					alreadySettingList: ko.observableArray([]),
					isShowWorkPlaceName: false,
					isShowSelectAllButton: false,
					disableSelection: false,
					maxRows: 12
				};

			$('#employee-list').ntsListComponent(employeeOption);
			let windowSize = nts.uk.ui.windows.getSelf();

			windowSize.$dialog.dialog("option", { width: 430, height: 530 });
			$(window).resize(function() {
				let windowSize = nts.uk.ui.windows.getSelf();
                if ( 530 - windowSize.$dialog.dialog().height() < 34 ) {
                    _.defer(() => { windowSize.$dialog.dialog( "option", { width: 430, height: 530 } ); } );
                }
			});
		}
	}

	interface ICopyCommand {
		// 複写元職場ID
		copySource: string;
		// 勤務区分
		laborAttr: number;
		// 年度
		year: number;
		// 複写先職場ID（List）
		copyDestinations: Array<string>;

	}

	export class ScreenData {
		data: KnockoutObservableArray<any> = ko.observableArray([]);
		selecteds: KnockoutObservableArray<string> = ko.observableArray([]);
		constructor(param?: IParam) {
			if (param) {
				this.data(param.data);
			}
		}

	}

	interface IParam {
		screenMode: 'Com_Workplace' | 'Com_Employment' | 'Com_Person';
		data: Array<any>;//EMPLOYEE = UnitModel
		selected: string;
		year: number;
		laborAttr: 0 | 1 | 2;
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
