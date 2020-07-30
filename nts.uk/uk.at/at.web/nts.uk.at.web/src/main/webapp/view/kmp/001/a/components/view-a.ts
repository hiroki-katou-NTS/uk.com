/// <reference path="../../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.at.view.kmp001.a {
	import share = nts.uk.at.view.kmp001;

	const template = `
		<div id="com-ccg001"></div>
		<div class="sidebar-content-header">
			<span class="title" data-bind="text: $i18n('KMP001_1')"></span>
			<button data-bind="text: $i18n('KMP001_4'), click: addNew, enable: mode() == 'update'"></button>
			<button class="proceed" data-bind="text: $i18n('KMP001_5'), click: addStampCard"></button>
			<button class="danger" data-bind="text: $i18n('KMP001_6'), click: deleteStampCard, enable: mode() == 'update'"></button>
			<!-- ko if: attendance -->
			<button data-bind="text: $i18n('KMP001_7'), click: showDiaLog"></button>
			<!-- /ko -->
		</div>
		<div class="view-kmp">
			<div class="list-component float-left viewa">
				<div id="list-employee"></div>
			</div>
			<div class="float-left model-component" 
				data-bind="component: { 
					name: 'editor-area', 
					params: { model: model }}"></div>
		<div>
`;

	const KMP001A_API = {
		GET_STAMPCARDDIGIT: 'screen/pointCardNumber/getStampCardDigit',
		GET_STATUS_SETTING: 'screen/pointCardNumber/getStatusEmployeeSettingStampCard',
		GET_INFOMAITON_EMPLOYEE: 'screen/pointCardNumber/getEmployeeInfoCardNumber',
		ADD: 'at/record/register-stamp-card/view-a/save',
		UPDATE: 'at/record/register-stamp-card/view-a/update',
		DELETE: 'at/record/register-stamp-card/view-a/delete'
	};

	@component({
		name: 'view-a',
		template
	})
	class ViewA extends ko.ViewModel {
		attendance: KnockoutObservable<boolean> = ko.observable(true);

		public employees: KnockoutObservableArray<IModel> = ko.observableArray([]);
		public model: share.Model = new share.Model();
		public settings: KnockoutObservableArray<ISetting> = ko.observableArray([]);
		public employeeIds: KnockoutObservableArray<string> = ko.observableArray([]);
		public baseDate: KnockoutObservable<string> = ko.observable('');
		public currentCodes: KnockoutObservableArray<string> = ko.observableArray([]);
		public mode: KnockoutObservable<MODE> = ko.observable('update');

		created() {
			const vm = this;

			vm.model.code
				.subscribe((c: string) => {
					const employees: IModel[] = ko.toJS(vm.employees);
					const current = _.find(employees, e => e.code === c);

					if (current) {
						vm.$ajax(KMP001A_API.GET_INFOMAITON_EMPLOYEE + "/" + ko.toJS(current.employeeId) + "/" + ko.toJS(current.affiliationId) + "/" + ko.toJS(vm.baseDate))
							.then((data: IModel) => {
								vm.model.update(ko.toJS(data));
								vm.model.employeeId(current.employeeId);
							});
					}
					vm.mode("update");
				});

			vm.employees
				.subscribe(() => {
					vm.reloadData(0);
				})
		}

		mounted() {
			const vm = this;
			vm.$errors('clear');
			const dataFormate = 'YYYY/MM/DD';

			if (!!vm.$user.role.attendance) {
				vm.attendance(true);
			} else {
				vm.attendance(false);
			}

			$('#list-employee').ntsListComponent(
				{
					isShowAlreadySet: true, //設定済表示
					isMultiSelect: false,
					listType: 4,
					employeeInputList: vm.employees,
					selectType: 1,
					selectedCode: vm.model.code,
					isShowNoSelectRow: false, //未選択表示
					alreadySettingList: vm.settings,
					isShowWorkPlaceName: true,  //職場表示
					isShowSelectAllButton: false,  //全選択表示
					isSelectAllAfterReload: true,
					disableSelection: false,
					maxRows: 20,
					maxWidth: 450
				} as any
			);

			$('#com-ccg001')
				.ntsGroupComponent({
					/** Common properties */
					systemType: 2, //システム区分	
					showEmployeeSelection: true,
					showQuickSearchTab: true, //クイック検索
					showAdvancedSearchTab: true,
					showBaseDate: true, //基準日利用
					showClosure: false, //就業締め日利用
					showAllClosure: false, //全締め表示
					showPeriod: false, //対象期間利用
					periodFormatYM: true, //対象期間精度
					maxPeriodRange: 'oneMonth', //最長期間

					/** Required parameter */
					baseDate: ko.observable(moment().format(dataFormate)),
					periodStartDate: ko.observable(moment.utc('1900/01/01', dataFormate).format(dataFormate)),
					periodEndDate: ko.observable(moment.utc('9999/12/31', dataFormate).format(dataFormate)),
					inService: true,
					leaveOfAbsence: true,
					closed: true,
					retirement: true,

					/** Quick search tab options */
					showAllReferableEmployee: true, //参照可能な社員すべて
					showOnlyMe: true, //自分だけ
					showSameDepartment: true,
					showSameDepartmentAndChild: true,
					showSameWorkplace: true, //同じ職場の社員
					showSameWorkplaceAndChild: true, //同じ職場とその配下の社員

					/** Advanced search properties */
					showEmployment: true, //雇用条件
					showDepartment: false,
					showWorkplace: true, //職場条件
					showClassification: true, //分類条件
					showJobTitle: true, //職位条件
					showWorktype: false, //勤種条件
					isMutipleCheck: true, //選択モード

					/**
					* Self-defined function: Return data from CCG001
					* @param: data: the data return from CCG001
					*/
					returnDataFromCcg001: function(data: any) {
						vm.baseDate(moment.utc(data.baseDate, "YYYY/MM/DD").format("YYYY-MM-DD"));

						for (var i = 0; i < data.listEmployee.length; i++) {
							vm.employeeIds.push(data.listEmployee[i].employeeId);
						}

						const employees = data.listEmployee
							.map(m => ({
								affiliationId: m.affiliationId,
								affiliationName: m.affiliationName,
								code: m.employeeCode,
								name: m.employeeName,
								employeeId: m.employeeId
							}));

						vm.employees(employees);
					}
				});

			vm.$errors('clear');

			vm.$nextTick(() => {
				vm.$errors('clear');
			})
		}

		showDiaLog() {
			const vm = this;

			vm.$window
				.modal('/view/kmp/001/d/index.xhtml')
				.then((data: any) => {
				});
		}

		addNew() {
			const vm = this;

			if (ko.unwrap(vm.model.code) != '') {
				vm.mode("new");
				vm.model.addNewStampCard();
			}
		}

		deleteStampCard() {
			const vm = this,
				model: IModel = ko.toJS(vm.model),
				checkeds = model.stampCardDto.filter((f) => f.checked),
				index = _.map(ko.unwrap(vm.employees), m => m.code).indexOf(model.code);

			if (checkeds != null) {
				const command = { employeeId: model.employeeId, cardNumbers: checkeds.map(m => m.stampNumber), cardId: checkeds.map(m => m.stampCardId) };

				vm.$ajax(KMP001A_API.DELETE, command)
					.then(() => vm.$dialog.info({ messageId: "Msg_16" }))
					.then(() => vm.reloadData(index))
					.then(() => vm.model.code.valueHasMutated())
					.always(() => vm.$blockui("clear"));
			}
		}

		addStampCard() {
			const vm = this,
				model: IModel = ko.toJS(vm.model),
				index = _.map(ko.unwrap(vm.employees), m => m.code).indexOf(model.code);;

			if (ko.unwrap(vm.model.code) != '') {
				const stamps: share.IStampCard = model.stampCardDto[0];
				const command = { employeeId: ko.toJS(model.employeeId), cardNumber: ko.toJS(stamps.stampNumber) };

				if (command.cardNumber == '') {
					vm.$dialog.info({ messageId: "Msg_1679" });
				} else {
					if (ko.toJS(vm.mode) == 'update') {
						vm.$blockui("invisible");

						vm.$ajax(KMP001A_API.UPDATE, command)
							.then(() => vm.$dialog.info({ messageId: 'Msg_15' }))
							.always(() => vm.$blockui("clear"));
					} else {
						vm.$blockui("invisible");

						vm.$ajax(KMP001A_API.ADD, command)
							.then(() => vm.$dialog.info({ messageId: 'Msg_15' }))
							.then(() => vm.reloadData(index))
							.then(() => vm.model.code.valueHasMutated())
							.always(() => vm.$blockui("clear"));
					}
				}
			}
		}

		reloadData(selectedIndex: number = 0) {
			const vm = this;

			vm.$blockui("invisible")
			vm.$ajax(KMP001A_API.GET_STATUS_SETTING, ko.toJS(vm.employeeIds))
				.then((data: IEmployeeId[]) => {
					const employees: IModel[] = ko.toJS(vm.employees);
					const modelSetting = new Setting();
					for (var i = 0; i < data.length; i++) {
						const setting = _.find(employees, e => e.employeeId === data[i].employee);

						if (setting) {
							modelSetting.code(setting.code);
							modelSetting.isAlreadySetting(true);
							vm.settings.push(ko.toJS(modelSetting));
						}
					}
					if (ko.toJS(vm.model.code) == '') {
						vm.model.code(employees[selectedIndex].code);
					}
				}).then(() => {
					vm.$blockui("clear");
				});
		}
	}
	
	interface IEmployeeId {
		employee: string;
	}

	type MODE = 'new' | 'update';
}